package com.yryz.quanhu.message.push.web;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReceived;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.message.push.entity.PushReqVo.*;
import com.yryz.quanhu.message.push.exception.ServiceException;
import com.yryz.quanhu.message.push.service.PushService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 下午3:16:59
 * @Description 推送
 */
@Service
public class PushAPIimpl implements PushAPI {
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	PushService pushService;

	@Override
	public void sendByAlias(String userId, String notification, String msg) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(msg)) {
			throw ServiceException.paramsError("custId", "msg");
		}
		try {
			//旧的推送关闭通知
			notification = null;
			pushService.sendByAlias(userId, notification, msg);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			throw ServiceException.sysError();
		}

	}

	@Override
	public void sendByTag(String tag, String notification, String msg) {
		if (StringUtils.isEmpty(tag) || StringUtils.isEmpty(msg)) {
			throw ServiceException.paramsError("tag", "msg");
		}

		try {
			//旧的推送关闭通知
			notification = null;
			pushService.sendByTag(tag, notification, msg);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			throw ServiceException.sysError();
		}

	}

	@Override
	public void sendByAlias(List<String> userIds, String notification, String msg) {
		if (userIds == null || userIds.isEmpty() || StringUtils.isEmpty(msg)) {
			throw ServiceException.paramsError("userIds", "msg");
		}
		try {
			//旧的推送关闭通知
			notification = null;
			pushService.sendByAlias(userIds, notification, msg);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			throw ServiceException.sysError();
		}
	}

	@Override
	public void sendByTag(List<String> tags, String notification, String msg) {
		if (tags == null || tags.isEmpty() || StringUtils.isEmpty(msg)) {
			throw ServiceException.paramsError("tags", "msg");
		}
		try {
			//旧的推送关闭通知
			notification = null;
			pushService.sendByTag(tags, notification, msg);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			throw ServiceException.sysError();
		}

	}

	@Override
	public void commonSendAlias(PushReqVo reqVo) {
		if (reqVo == null || reqVo.getPushType() == null || StringUtils.isBlank(reqVo.getMsg())
				) {
			throw ServiceException.paramsError("reqVo", "pushType", "msg");
		}
		if ((reqVo.getPushType() == CommonPushType.BY_ALIAS
				|| reqVo.getPushType() == CommonPushType.BY_ALIASS)
				&& CollectionUtils.isEmpty(reqVo.getCustIds())) {
			throw ServiceException.paramsError("custIds");
		}
		if((reqVo.getPushType() == CommonPushType.BY_ALIAS_REGISTRATIONID
				|| reqVo.getPushType() == CommonPushType.BY_REGISTRATIONID
				|| reqVo.getPushType() == CommonPushType.BY_REGISTRATIONIDS) && CollectionUtils.isEmpty(reqVo.getRegistrationIds())){
			throw ServiceException.paramsError("registrationIds");
		}
		if ((reqVo.getPushType() == CommonPushType.BY_TAG || reqVo.getPushType() == CommonPushType.BY_TAGS)
				&& CollectionUtils.isEmpty(reqVo.getTags())) {
			throw ServiceException.paramsError("tags");
		}
		try {
			if(reqVo.getNotifyStatus() == null){
				reqVo.setNotifyStatus(false);
			}
			if(reqVo.getMsgStatus() == null){
				reqVo.setMsgStatus(true);
			}
			pushService.commonSendAlias(reqVo);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("[push message]",e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<PushReceived> getReceived(List<String> msgIds) {
		if(CollectionUtils.isEmpty(msgIds)){
			throw ServiceException.paramsError("msgIds");
		}
		try {
			return pushService.getPushReceived(msgIds);
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("[getReceived]",e);
			throw ServiceException.sysError();
		}
	}

}
