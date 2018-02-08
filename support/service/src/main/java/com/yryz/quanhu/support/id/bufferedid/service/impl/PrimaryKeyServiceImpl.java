package com.yryz.quanhu.support.id.bufferedid.service.impl;

import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.support.id.bufferedid.common.GenIdConstant;
import com.yryz.quanhu.support.id.bufferedid.entity.KeyBuffer;
import com.yryz.quanhu.support.id.bufferedid.entity.KeyInfo;
import com.yryz.quanhu.support.id.bufferedid.entity.PrimaryKey;
import com.yryz.quanhu.support.id.bufferedid.exception.LoadPrimaryKeyException;
import com.yryz.quanhu.support.id.bufferedid.dao.YryzPrimaryKeyDao;
import com.yryz.quanhu.support.id.bufferedid.service.PrimaryKeyService;
import com.yryz.quanhu.support.id.bufferedid.thread.ExpandThread;
import com.yryz.quanhu.support.id.bufferedid.thread.LoadPrepBuffThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Executor;

@Service
public class PrimaryKeyServiceImpl implements PrimaryKeyService {

	private static final Logger log = LoggerFactory.getLogger(PrimaryKeyServiceImpl.class);

	@Autowired
	@Qualifier("expandKeyExecutor")
	private Executor expandKeyExecutor;
	@Autowired
	@Qualifier("loadPrepBuffExecutor")
	private Executor loadPrepBuffExecutor;
	@Autowired
	private YryzPrimaryKeyDao primaryKeyDao;

	@Override
	public void asynExpandKey(KeyInfo keyInfo) {
		try {
			// 检查主缓冲区
			if (!keyInfo.getCurrBuff().hasNext()) {
				//主缓冲区使用预备缓冲区的数据
				expandKeyExecutor.execute(new ExpandThread(this, keyInfo));
			}
		} catch (Exception e) {
			log.warn("maybe expandKeyExecutor has be full,message:" + e.getMessage());
		}
		// 2、检查扩充预备缓冲区
		asynLoadPrepBuff(keyInfo);
	}

	@Override
	public void asynLoadPrepBuff(final KeyInfo keyInfo) {
		try {
			if (keyInfo.getPrepBuffs().isEmpty()) {
				loadPrepBuffExecutor.execute(new LoadPrepBuffThread(this, keyInfo));
			}
		} catch (Exception e) {
			log.warn("maybe loadPrepBuffexecutor has be full, message:" + e.getMessage());
		}
	}

	@Override
	public void syncExpandKey(final KeyInfo keyInfo) {
		// 如果没有可用的主键，则扩充
		if (!keyInfo.getCurrBuff().hasNext()) {
			synchronized (keyInfo.currBuffLock) {
				// 扩展主缓冲区
				keyInfo.getCurrBuff().copy(expandCurrBuff(keyInfo));
			}
		}
	}

	/**
	 * 扩展主缓冲区
	 * 
	 * @param keyInfo
	 */
	@Override
	public KeyBuffer expandCurrBuff(final KeyInfo keyInfo) {
		// 如果没有可用的主键，则扩充
		if (!keyInfo.getCurrBuff().hasNext()) {
			// 从预备缓冲区poll一个buff
			KeyBuffer buff = keyInfo.getPrepBuffs().poll();
			// 如果预备缓冲区拿出来为空，则从数据库拿取
			if (buff == null) {
				buff = loadBuff(keyInfo.getPrimaryKeyName());
			}
			return buff;
		} else {
			return keyInfo.getCurrBuff();
		}
	}

	@Override
	public void loadPrepBuff(KeyInfo keyInfo) {
		synchronized (keyInfo.prepBuffLock) {
			if (keyInfo.getPrepBuffs().isEmpty()) {
				KeyBuffer buff = loadBuff(keyInfo.getPrimaryKeyName());
				keyInfo.getPrepBuffs().add(buff);
			}
		}
	}

	/**
	 * 加载缓冲区
	 */
	public KeyBuffer loadBuff(String primaryKeyName) {
		KeyBuffer keyBuff = new KeyBuffer();
		PrimaryKey primary = loadPrimaryKey(primaryKeyName);
		keyBuff.setCurrent(primary.getCurrentValue());
		keyBuff.setMin(primary.getCurrentValue());
		keyBuff.setMax(primary.getCurrentValue() + primary.getStep());
		return keyBuff;
	}

	/**
	 * 读取缓冲区， 1、查询当前数据库的值 2、更新当前值，更新为当前值+步长，用当前值做乐观锁 3、如果更新失败(!=1)则重复
	 * 1、2步，重复GenIdConstant.RETRY_COUNT次 4、如果1、查询不到则抛异常 5、如果3次均更新失败则抛异常
	 * 
	 * @param primaryKeyName
	 * @return
	 */
	@Transactional
	private PrimaryKey loadPrimaryKey(String primaryKeyName) {
		try {

			PrimaryKey primaryKeyVo = primaryKeyDao.getPrimaryKey(primaryKeyName);
			// 如果数据库查不到则
			if (primaryKeyVo == null) {
				log.info("new primarykey request");
				primaryKeyVo = buildPrimaryKeyModel(primaryKeyName);
				primaryKeyDao.insertPrimaryKey(primaryKeyVo);
				//String msg = "primayKeyName：" + primaryKeyName + " undeclared，please check the db config";
				//throw new UndeclaredPrimaryKeyException(msg);
			}
			int result = primaryKeyDao.updatePrimaryKey(primaryKeyVo);
			log.info("update primarykey result:{}",String.valueOf(result));
			if (result == GenIdConstant.UPDATE_1) {
				return primaryKeyVo;
			}

		} catch (Exception e) {
			log.error("[PrimarykeyPersistenceDao.save]",e);
			throw new MysqlOptException(e);
		}

		// 多次重试还不能获取，则抛出异常
		throw new LoadPrimaryKeyException("Can't update primaryKey with retry:" + GenIdConstant.RETRY_COUNT);
	}

	private PrimaryKey buildPrimaryKeyModel(String primaryKeyName) {
		PrimaryKey primaryKey = new PrimaryKey();
		primaryKey.setPrimaryName(primaryKeyName);
		primaryKey.setCurrentValue(getSetInitial(GenIdConstant.DEFAULT_CODE_LENGTH));
		primaryKey.setStep(GenIdConstant.DEFAULT_PRIMARYKEY_STEP);
		return primaryKey;
	}

	private long getSetInitial(int index) {
		int pow = (int) Math.pow(10, index - 1);
		return (long) (Math.random() * pow + pow);
	}

}
