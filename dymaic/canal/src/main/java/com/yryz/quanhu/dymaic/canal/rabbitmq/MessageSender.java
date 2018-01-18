package com.yryz.quanhu.dymaic.canal.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.quanhu.dymaic.canal.constant.AmqpConstant;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 发送消息
 *
 * @author bug1024
 * @date 2017-03-26
 */
@Service
public class MessageSender {

    private static Logger logger = LoggerFactory.getLogger(MessageSender.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
	private RabbitTemplate rabbitTemplate;

    public Boolean sendMessage(CanalMsgContent message){
        try {
            String msg = MAPPER.writeValueAsString(message);
            rabbitTemplate.setExchange(AmqpConstant.CANAL_DIRECT_EXCHANGE);
//            设置 routingKey为direct ，否则为fanout
//            rabbitTemplate.setRoutingKey(routingKey);
//            rabbitTemplate.setQueue(routingKey);
            rabbitTemplate.convertAndSend(msg);
            return true;
        } catch (JsonProcessingException e) {
            logger.warn("json encode failed", e);
            return false;
        } catch (Exception e) {
            logger.warn("send to mq failed", e);
            return false;
        }
    }

}
