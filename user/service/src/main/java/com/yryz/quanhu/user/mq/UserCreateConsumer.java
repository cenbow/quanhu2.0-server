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

import com.alibaba.dubbo.config.annotation.Reference;
import com.fasterxml.jackson.core.type.TypeReference;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.entity.UserOperateInfo;
import com.yryz.quanhu.user.service.UserOperateService;

@Service
public class UserCreateConsumer {
	private static final Logger logger = LoggerFactory.getLogger(UserCreateConsumer.class);
	@Reference
	private ImAPI imApi;
	@Autowired
	private UserOperateService operateService;
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
		logger.info("mq send");
		RegisterDTO registerDTO = JsonUtils.fromJson(data, new TypeReference<RegisterDTO>() {
		});
		// 创建运营信息
		if (StringUtils.isNotBlank(registerDTO.getUserRegInviterCode())) {
			operateService.save(
					new UserOperateInfo(724011759597371392l, registerDTO.getUserChannel(), registerDTO.getUserRegInviterCode()));
		}
		if (registerDTO.getRegLogDTO() != null) {
			registerDTO.getRegLogDTO().setUserId(724011759597371392l);
			operateService.saveRegLog(registerDTO.getRegLogDTO());
		}
	
	}
}
