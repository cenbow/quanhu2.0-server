package com.yryz.quanhu.message.message.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageType;
import com.yryz.common.message.MessageVo;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.message.message.constants.MessageContants;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.mongo.MessageMongo;
import com.yryz.quanhu.message.message.redis.MessageRedis;
import com.yryz.quanhu.message.message.service.MessageService;
import com.yryz.quanhu.message.message.vo.MessageStatusVo;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

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

    @Reference(check = false, timeout = 30000, lazy = true)
    private PushAPI pushAPI;

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

    @Override
    public Boolean sendMessage(MessageVo messageVo, boolean flag) {
        if (StringUtils.isBlank(messageVo.getMessageId())) {
            messageVo.setMessageId(IdGen.uuid());
        }

        messageMongo.save(messageVo);

        int itype = messageVo.getType() == null ? 0 : messageVo.getType().intValue();

        if (itype != MessageType.NOTICE_TYPE) { //非通知公告的未读数需要增加，通知公告类型采用群发机制，不处理
            messageRedis.addUnread(messageVo.getToCust(), getReadTypeString(messageVo));
        }

        if (flag) {
            pushMessage(messageVo);
        }
        return true;
    }

    private void pushMessage(MessageVo messageVo) {
        try {
            PushReqVo reqVo = new PushReqVo();
            List<String> custIds = new ArrayList<>();
            custIds.add(messageVo.getToCust());
            reqVo.setCustIds(custIds);
            reqVo.setNotification(messageVo.getTitle());
            int itype = messageVo.getType() == null ? 0 : messageVo.getType().intValue();
            if (itype == MessageType.NOTICE_TYPE) {
                reqVo.setPushType(PushReqVo.CommonPushType.BY_ALL);
            } else {
                reqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
            }
            reqVo.setMsg(GsonUtils.parseJson(messageVo));
            pushAPI.commonSendAlias(reqVo);
        } catch (Exception e) {
            LOGGER.error("[message] push message faild ...", e);
        }
    }

    private static String getReadTypeString(MessageVo messageVo) {
        if (messageVo == null) {
            throw QuanhuException.busiError("message can't be null");
        }
        Integer type = messageVo.getType();
        Integer label = messageVo.getLabel();
        if (type == null) {
            throw QuanhuException.busiError("message type can't be null");
        }
        if (label != null) {
            return type + "|" + label;
        } else {
            return type.toString();
        }
    }

    @Override
    public MessageVo get(String messageId) {
        return messageMongo.get(messageId);
    }
}
