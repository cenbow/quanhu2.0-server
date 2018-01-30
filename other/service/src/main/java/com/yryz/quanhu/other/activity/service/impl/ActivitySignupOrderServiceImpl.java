package com.yryz.quanhu.other.activity.service.impl;

import java.text.DecimalFormat;
import java.util.UUID;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.message.SystemBody;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.other.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.other.activity.dao.ActivityRecordDao;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityRecord;
import com.yryz.quanhu.other.activity.service.ActivitySignUpService;
import com.yryz.quanhu.other.activity.vo.ActivityInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;

/**
* @Title: ActivitySignupOrderServiceImpl.java
* @Package com.yryz.operation.modules.activity.signup.service.impl
* @author wangheng
* @date 2017年9月21日 下午6:09:27
*/
@Service("activitySignupOrderServiceImpl")
public class ActivitySignupOrderServiceImpl implements IOrderNotifyService {

	@Autowired
	private ActivityInfoDao activityInfoDao;

	@Autowired
	private ActivitySignUpService activitySignUpService;

	@Autowired
	private ActivityRecordDao activityRecordDao;
	@Reference
	MessageAPI messageAPI;

    private static final Logger logger = LoggerFactory.getLogger(ActivitySignupOrderServiceImpl.class);
	@Override
	public void notify(OutputOrder outputOrder) {
		Assert.notNull(outputOrder, "支付成功后，回调接口 outputOrder 为空！");
		ActivityRecord activityRecord = JSONObject.parseObject(outputOrder.getBizContent(), ActivityRecord.class);
		Assert.notNull(activityRecord, "支付成功后，回调接口 extJson 转换错误！");
		// 支付成功 将支付记录插入到报名支付记录表中 
		// 获取活动配置
		ActivityEnrolConfig activityEnrolConfig = activitySignUpService.getActivityEnrolConfigByActId(activityRecord.getActivityInfoId());

		// 报名类活动支付成功，将 待提交至 参与记录表数据 插入数据库；
		ActivityRecord ard =activityRecordDao.selectByCustIdAndActivityId(activityRecord.getCreateUserId(),activityRecord.getActivityInfoId());
		if(ard!=null){
			return;
		}
		// 支付成功 将报名信息插入 报名记录表
		activityRecordDao.insertByPrimaryKeySelective(activityRecord);
		// 更新主表的当前报名人人数信息
		ActivityInfo activityInfo = activityInfoDao.selectByPrimaryKey(activityRecord.getActivityInfoId());
		activityInfoDao.updateJoinCount(activityInfo.getKid(),activityEnrolConfig.getEnrolUpper());
		// 给付款用户推送 消费消息
		sendPrizesMessage(MessageConstant.ACTIVITY_JOIN_POST, activityRecord.getCreateUserId().toString(),activityRecord);
	}
	private void sendPrizesMessage(MessageConstant constant, String createUserId, ActivityRecord record) {
    	ActivityInfo activityInfo = activityInfoDao.selectByPrimaryKey(record.getActivityInfoId());
    	MessageVo messageVo = new MessageVo();
		messageVo.setMessageId(UUID.randomUUID().toString());
        messageVo.setActionCode(constant.getMessageActionCode());
        String content =null;
        if (StringUtils.isNotEmpty(activityInfo.getTitle())) {
            content = constant.getContent().replaceAll("\\{count\\}", activityInfo.getTitle());
        }
        if (record.getAmount()==null) {
        	record.setAmount(0);
        }
        DecimalFormat df = new DecimalFormat("#0.00");
        content = content.replaceAll("\\{count1\\}", df.format(record.getAmount()/100.00));
        messageVo.setContent(content);
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setLabel(constant.getLabel());
        messageVo.setType(constant.getType());
        messageVo.setTitle(constant.getTitle());
        messageVo.setToCust(createUserId);
        messageVo.setViewCode(constant.getMessageViewCode());
        
        SystemBody body = new SystemBody();
       body.setBodyTitle(activityInfo.getTitle());
		body.setBodyImg(activityInfo.getCoverPlan());
        //messageVo.setBody(body);
        logger.info("参加活动开始发送推送消息 :" + JSONObject.toJSONString(messageVo));
		messageAPI.sendMessage(messageVo, true);
		logger.info("参加活动成功发送推送消息 :" + JSONObject.toJSONString(messageVo));
	}

	@Override
	public String getModuleEnum() {
		return ModuleContants.ACTIVITY_ENUM;
	}

}