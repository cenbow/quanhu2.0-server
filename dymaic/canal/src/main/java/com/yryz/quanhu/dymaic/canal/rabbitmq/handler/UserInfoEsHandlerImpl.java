package com.yryz.quanhu.dymaic.canal.rabbitmq.handler;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.elasticsearch.UserEsRepository;
import com.yryz.quanhu.dymaic.canal.entity.CanalChangeInfo;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;
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
	private UserEsRepository userEsRepository;

	@PostConstruct
	private void register() {
		syncExecutor.register(this);
	}

	@Override
	public Boolean watch(CanalMsgContent msg) {
		if (CommonConstant.UserDb.DB_NAME.equals(msg.getDbName())
				&& CommonConstant.UserDb.TABLE_USER.equals(msg.getTableName())) {
			return true;
		}
		return false;
	}

	@Override
	public void handler(CanalMsgContent msg) {
		UserInfo uinfoBefore = UserInfo.parse(msg.getDataBefore());
		UserInfo uinfoAfter = UserInfo.parse(msg.getDataAfter());

		if (CommonConstant.EventType.OPT_UPDATE.equals(msg.getEventType())) {
			Optional<UserInfo> uinfo = userEsRepository.findById((long) uinfoBefore.getId());
			if (uinfo.isPresent()) {
				UserInfo userInfo = UserInfo.parse(uinfo.get(), msg.getDataAfter());
				userEsRepository.save(userInfo);
			} else {
				// 先收到了update消息，后收到insert消息
				userEsRepository.save(uinfoAfter);
			}
		} else if (CommonConstant.EventType.OPT_DELETE.equals(msg.getEventType())) {
			userEsRepository.deleteById((long) uinfoBefore.getId());
		} else if (CommonConstant.EventType.OPT_INSERT.equals(msg.getEventType())) {
			// 先执行了update则不执行insert
			Optional<UserInfo> uinfo = userEsRepository.findById((long) uinfoAfter.getId());
			if (!uinfo.isPresent()) {
				userEsRepository.save(uinfoAfter);
			}
		}
	}

}
