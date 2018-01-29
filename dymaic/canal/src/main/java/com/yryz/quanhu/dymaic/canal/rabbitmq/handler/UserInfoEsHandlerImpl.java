package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import com.yryz.quanhu.dymaic.canal.entity.UserTagInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.utils.CanalEntityParser;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;

/**
 * 利用MQ处理canal消息处理
 *
 * @author jk
 */
@Component
public class UserInfoEsHandlerImpl implements SyncHandler {
	private static Logger logger = LoggerFactory.getLogger(UserInfoEsHandlerImpl.class);
	@Resource
	private SyncExecutor syncExecutor;

	@Resource
	private UserRepository userRepository;
	
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;

	@PostConstruct
	private void register() {
		syncExecutor.register(this);
	}

	@Override
	public Boolean watch(CanalMsgContent msg) {
		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.QuanHuDb.TABLE_USER.equals(msg.getTableName())) {
			return true;
		}
		return false;
	}

	@Override
	public void handler(CanalMsgContent msg) {
//		boolean exists=elasticsearchTemplate.indexExists(UserInfo.class);
//		if(!exists){
//			elasticsearchTemplate.createIndex(UserInfo.class);
//		}

		if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.QuanHuDb.TABLE_USER.equals(msg.getTableName())) {
			doUserBaseInfo(msg);
		}
		/*if (CommonConstant.QuanHuDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.QuanHuDb.TABLE_USER_TAG.equals(msg.getTableName())) {
			doUserTagInfo(msg);
		}*/


	}

	private void doUserTagInfo(CanalMsgContent msg) {
		UserTagInfo tagInfoBefore = CanalEntityParser.parse(msg.getDataBefore(), UserTagInfo.class);
		UserTagInfo tagInfoAfter = CanalEntityParser.parse(msg.getDataAfter(), UserTagInfo.class);

		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<UserInfo> uinfo = userRepository.findById(tagInfoBefore.getUserId());
			if (uinfo.isPresent()) {
				UserInfo userInfo = uinfo.get();
				UserTagInfo userTagInfo = CanalEntityParser.parse(userInfo.getUserTagInfo(), msg.getDataAfter(), UserTagInfo.class);
				userInfo.setUserTagInfo(userTagInfo);
				userRepository.save(userInfo);
			} else {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserTagInfo(tagInfoAfter);
				userInfo.setUserId(tagInfoAfter.getUserId());
				userRepository.save(userInfo);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
//			userRepository.deleteById(tagInfoBefore.getUserId());
			Optional<UserInfo> uinfo = userRepository.findById(tagInfoBefore.getUserId());
			UserInfo userInfo = uinfo.get();
			userInfo.setUserTagInfo(null);
			userRepository.save(userInfo);
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<UserInfo> uinfo = userRepository.findById(tagInfoAfter.getUserId());
			if (!uinfo.isPresent()) {
				UserInfo userInfo = new UserInfo();
				userInfo.setUserTagInfo(tagInfoAfter);
				userInfo.setUserId(tagInfoAfter.getUserId());
				userRepository.save(userInfo);
			}
		}

	}

	private void doUserBaseInfo(CanalMsgContent msg) {
		UserBaseInfo uinfoBefore = CanalEntityParser.parse(msg.getDataBefore(),UserBaseInfo.class);
		UserBaseInfo uinfoAfter = CanalEntityParser.parse(msg.getDataAfter(),UserBaseInfo.class);
		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<UserInfo> uinfo = userRepository.findById(uinfoBefore.getUserId());
			if (uinfo.isPresent()) {
				UserInfo userInfo=uinfo.get();
				UserBaseInfo userBaseInfo = CanalEntityParser.parse(userInfo.getUserBaseInfo(), msg.getDataAfter(),UserBaseInfo.class);
				userInfo.setUserBaseInfo(userBaseInfo);
				userRepository.save(userInfo);
			} else {
				UserInfo userInfo=new UserInfo();
				userInfo.setUserBaseInfo(uinfoAfter);
				userInfo.setUserId(uinfoAfter.getUserId());
				userRepository.save(userInfo);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
			userRepository.deleteById(uinfoBefore.getUserId());
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<UserInfo> uinfo = userRepository.findById(uinfoAfter.getUserId());
			if (!uinfo.isPresent()) {
				UserInfo userInfo=new UserInfo();
				userInfo.setUserBaseInfo(uinfoAfter);
				userInfo.setUserId(uinfoAfter.getUserId());
				userRepository.save(userInfo);
			}
		}
	}

}
