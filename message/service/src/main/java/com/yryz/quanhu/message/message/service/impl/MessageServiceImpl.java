package com.yryz.quanhu.message.message.service.impl;

import com.yryz.quanhu.message.message.constants.MessageContants;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.mongo.MessageMongo;
import com.yryz.quanhu.message.message.redis.MessageRedis;
import com.yryz.quanhu.message.message.service.MessageService;
import com.yryz.quanhu.message.message.vo.MessageStatusVo;
import com.yryz.quanhu.message.message.vo.MessageVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:55
 * @Author: pn
 */
@Service
public class MessageServiceImpl implements MessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageServiceImpl.class);

    @Autowired
    private MessageRedis messageRedis;

    @Autowired
    private MessageMongo messageMongo;

    @Override
    public Map<String, String> getUnread(Long userId) {
        Map<String, String> map = messageRedis.getUnread(String.valueOf(userId));
        if (map == null) {
            map = new HashMap<>();
        }
        map.forEach((readType, val) -> {
            for (int i = 0; i < 10; i++) {
                if (!messageRedis.clearUnread(String.valueOf(userId), readType)) { //如果失败，则重复执行
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        LOGGER.error("线程中断异常！", e);
                    }
                } else {
                    break;
                }
            }
        });
        return map;
    }

    @Override
    public MessageStatusVo clearUnread(MessageDto messageDto) {
        for (int i = 0; i < 10; i++) {
            if (!messageRedis.clearUnread(String.valueOf(messageDto.getUserId()), messageDto.getReadType())) { //如果失败，则重复执行
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    LOGGER.error("线程中断异常！", e);
                }
            } else {
                break;
            }
        }

        return new MessageStatusVo(true);
    }

    @Override
    public List<MessageVo> getList(MessageDto messageDto) {
        return messageMongo.getList(messageDto);
    }

    /**
     * 消息总览
     *
     * @param userId
     * @return
     */
    @Override
    public Map<Integer, MessageVo> getMessageCommon(Long userId) {
        Map<Integer, MessageVo> map = new LinkedHashMap<>();
        MessageDto messageDto = new MessageDto();
        messageDto.setStart(0);
        messageDto.setLimit(1);

        //1.通知公告
        messageDto.setType(MessageContants.NOTICE_TYPE);
        mongGetListAndSetMap(map, messageDto, MessageContants.NOTICE_TYPE);

        messageDto.setUserId(userId);

        //2.系统消息
        messageDto.setType(MessageContants.SYSTEM_TYPE);
        mongGetListAndSetMap(map, messageDto, MessageContants.SYSTEM_TYPE);

        //3.账户与安全
        messageDto.setType(MessageContants.ORDER_TYPE);
        mongGetListAndSetMap(map, messageDto, MessageContants.ORDER_TYPE);

        //4.互动消息
        messageDto.setType(MessageContants.INTERACTIVE_TYPE);
        mongGetListAndSetMap(map, messageDto, MessageContants.INTERACTIVE_TYPE);

        //5.推荐与活动
        messageDto.setType(MessageContants.RECOMMEND_TYPE);
        mongGetListAndSetMap(map, messageDto, MessageContants.RECOMMEND_TYPE);

        return map;
    }

    private void mongGetListAndSetMap(Map<Integer, MessageVo> map, MessageDto messageDto, Integer messageType) {
        List<MessageVo> list = messageMongo.getList(messageDto);
        if (!CollectionUtils.isEmpty(list)) {
            map.put(messageType, list.get(0));
        }
    }
}
