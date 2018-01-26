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
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.manager.ImManager;

/**
 * 用户信息更新mq消费
 * @author danshiyu
 *
 */
@Service
public class UserUpdateConsumer {
	private static final Logger logger = LoggerFactory.getLogger(UserUpdateConsumer.class);
	@Autowired
	private ImManager imManager;
	/**
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * key:routing-key
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=MqConstants.USER_UPDATE_QUEUE,durable="true"),
			exchange=@Exchange(value=MqConstants.USER_DIRECT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
			key=MqConstants.USER_UPDATE_QUEUE)
	)
	public void handleMessage(String data){
		if(logger.isDebugEnabled()){
			logger.debug("[user_update]:params:{}",data);
		}
		UserBaseInfo baseInfo = JsonUtils.fromJson(data, new TypeReference<UserBaseInfo>() {
		});
		if(baseInfo == null || baseInfo.getUserId() == null){
			return;
		}
		//创建im账号
		imManager.updateUser(baseInfo.getUserId(), baseInfo.getUserNickName(), baseInfo.getUserNickName(), baseInfo.getAppId());
	}
}
