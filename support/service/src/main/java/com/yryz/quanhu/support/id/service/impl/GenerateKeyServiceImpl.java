package com.yryz.quanhu.support.id.service.impl;


import com.yryz.quanhu.support.id.common.GenIdConstant;
import com.yryz.quanhu.support.id.entity.KeyBuffer;
import com.yryz.quanhu.support.id.entity.KeyInfo;
import com.yryz.quanhu.support.id.exception.BaseGenKeyException;
import com.yryz.quanhu.support.id.exception.GenPrimaryKeyException;
import com.yryz.quanhu.support.id.service.GenerateKeyService;
import com.yryz.quanhu.support.id.service.PrimaryKeyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("generateKeyService")
public class GenerateKeyServiceImpl implements GenerateKeyService {
	private static final Logger logger = LoggerFactory.getLogger(GenerateKeyServiceImpl.class);

	@Autowired
	PrimaryKeyService primaryKeyService;

	@Override
	public long nextPrimaryKey(final KeyInfo keyInfo) {
		return nextPrimaryKey(keyInfo, GenIdConstant.INC_DEFAULT);
	}

	@Override
	public long nextPrimaryKey(final KeyInfo keyInfo, Integer inc) {
		long value = GenIdConstant.UNDER_LIMIT;
		// 修正步长
		if (inc == null || inc <= 0) {
			inc = GenIdConstant.INC_DEFAULT;
		}
		try {
			// 如果当前buff还有，锁住当前缓冲区后，则从当前buff拿取
			synchronized (keyInfo.currBuffLock) {
				// 设置步长
				keyInfo.getCurrBuff().setInc(inc);

				if (keyInfo.getCurrBuff().hasNext()) {
					value = keyInfo.getCurrBuff().nextValue();
				} else {
					// 如果用完了
					// 对所有缓冲区上锁
					// 则需要将下一个缓冲区替换成当前的
					// 取key
					// 同步扩展
					keyInfo.getCurrBuff().copy(
							primaryKeyService.expandCurrBuff(keyInfo));
					if (keyInfo.getCurrBuff().hasNext()) {
						value = keyInfo.getCurrBuff().nextValue();
					}
				}
			}

			// 异步检查是否扩充缓两个冲区
			primaryKeyService.asynExpandKey(keyInfo);
		} catch (BaseGenKeyException e) {
			throw e;
		} catch (Exception e) {
			throw new GenPrimaryKeyException("gen primary exception", e);
		}

		if (value <= GenIdConstant.UNDER_LIMIT) {
			throw new GenPrimaryKeyException("gen primary exception");
		}

		return value;
	}

	@Override
	public KeyInfo loadKeyInfo(final String primaryKeyName) {
		logger.info("starting loadKeyInfo...");
		// 设置主键缓冲区
		KeyBuffer keyBuff = primaryKeyService.loadBuff(primaryKeyName);
		KeyInfo keyInfo = new KeyInfo(primaryKeyName, keyBuff);
		// 设置预备缓冲区
		if (keyInfo.getPrepBuffs().isEmpty()) {
			KeyBuffer preBuffer = primaryKeyService.loadBuff(primaryKeyName);
			keyInfo.getPrepBuffs().add(preBuffer);
		}
		return keyInfo;
	}

}
