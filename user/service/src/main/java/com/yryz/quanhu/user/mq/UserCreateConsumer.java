package com.yryz.quanhu.user.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.context.Context;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.entity.UserOperateInfo;
import com.yryz.quanhu.user.manager.EventManager;
import com.yryz.quanhu.user.manager.ImManager;
import com.yryz.quanhu.user.manager.MessageManager;
import com.yryz.quanhu.user.service.UserOperateService;

/**
 * 用户附属信息创建以及连带业务执行
 * @author danshiyu
 *
 */
@Service
public class UserCreateConsumer {
	private static final Logger logger = LoggerFactory.getLogger(UserCreateConsumer.class);
	@Autowired
	private ImManager imManager;
	@Autowired
	private EventManager eventManager;
	@Autowired
	private UserOperateService operateService;
	@Autowired
	private MessageManager messageManager;
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=MqConstants.USER_CREATE_QUEUE,durable="true"),
			exchange=@Exchange(value=MqConstants.USER_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=MqConstants.USER_CREATE_QUEUE)
	)
	public void handleMessage(String data){
		if(logger.isDebugEnabled()){
			logger.debug("[user_reg_create]:params:{}",data);
		}
		RegisterDTO registerDTO = JsonUtils.fromJson(data, new TypeReference<RegisterDTO>() {
		});
		if(registerDTO == null || registerDTO.getRegLogDTO() == null || registerDTO.getRegLogDTO().getUserId() == null){
			return;
		}
		//用户注册信息创建
		createRegInfo(registerDTO);
		String appId = registerDTO.getRegLogDTO().getAppId();
		//创建im账号
		imManager.addImUser(registerDTO.getRegLogDTO().getUserId(), registerDTO.getRegLogDTO().getAppId());
		
		//圈乎用户才加积分和发消息
		if(StringUtils.isNotBlank(appId) && StringUtils.equals(appId, Context.getProperty(AppConstants.APP_ID))){
			//注册加积分，邀请好友加积分
			eventManager.register(registerDTO.getRegLogDTO().getUserId().toString(), registerDTO.getUserPhone(), registerDTO.getRegLogDTO().getRegType(), registerDTO.getUserRegInviterCode());
			//注册消息发送
			messageManager.register(registerDTO.getRegLogDTO().getUserId().toString(),registerDTO.getRegLogDTO().getAppId());
		}
		
	}
	
	/**
	 * 用户注册信息创建，更新邀请人数
	 * @param registerDTO
	 */
	public void createRegInfo(RegisterDTO registerDTO){
		try {
			// 创建运营信息
			operateService.save(
					new UserOperateInfo(registerDTO.getRegLogDTO().getUserId(), registerDTO.getUserChannel(), registerDTO.getUserRegInviterCode()));
			operateService.saveRegLog(registerDTO.getRegLogDTO());
			//更新邀请人数
			operateService.updateInviterNum(registerDTO.getUserRegInviterCode());
			logger.info("[user_reg_create]:params:{},result:success",JsonUtils.toFastJson(registerDTO));
		} catch (Exception e) {
			logger.error("[user_reg_create]",e);
			logger.info("[user_reg_create]:params:{},result:{}",JsonUtils.toFastJson(registerDTO),e.getMessage());
		}
	}
	
	
}
