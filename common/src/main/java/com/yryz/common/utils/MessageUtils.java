/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017年9月11日
 * Id: QuanhuPush.java, 2017年9月11日 上午11:45:03 yehao
 */
package com.yryz.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.yryz.common.message.JumpDetails;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.mongodb.ReflectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月11日 上午11:45:03
 * @Description 圈乎消息
 */
public class MessageUtils {

    private static Logger logger = LoggerFactory.getLogger(MessageUtils.class);

    /*@Autowired
    private PushClient pushClient;

    @Autowired
    private MessageClient messageClient;*/

    /*public void sendMessage(MessageConstant constant, String custId, String msg, Object body) {
        MessageVo messageVo = this.buildMessage(constant, custId, msg, body);
        boolean flag = false;
        if (constant != MessageConstant.EDIT_PAY_PASSWORD) {
            flag = true;
        }
        logger.info("send message :" + JSONObject.toJSONString(messageVo));
        //sendMessage(messageVo, flag);
    }*/

    /**
     * @Title: buildMessage @Description: 构建消息公共部分 @author
     * wangheng @param @param mc @param @param custId @param @param
     * msg @param @param body @param @return @return MessageVo @throws
     */
    public static MessageVo buildMessage(MessageConstant mc, String userId, String msg, Object body) {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setActionCode(mc.getMessageActionCode());
        String content = mc.getContent();
        if (StringUtils.isNotEmpty(msg)) {
            String regex = "\\{count\\}";
            try {
                content = content.replaceAll(regex, msg);
            } catch (Exception e) {
                logger.error(content + " replaceAll Exception", e);
                regex = "{count}";
                if (content.indexOf(regex) > -1) {
                    content = StringUtils.substringBefore(content, regex) + msg
                            + StringUtils.substringAfter(content, regex);
                }
            }
        }
        messageVo.setContent(content);
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setLabel(mc.getLabel());
        messageVo.setType(mc.getType());
        messageVo.setTitle(mc.getTitle());
        messageVo.setToCust(userId);
        messageVo.setViewCode(mc.getMessageViewCode());
        messageVo.setBody(body);
        return messageVo;
    }

    public void buildMessageByJump(MessageConstant constant, String userId, String msg, Object body,
                                  JumpDetails jumpDetails, boolean flag) {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessageId(UUID.randomUUID().toString());
        messageVo.setActionCode(constant.getMessageActionCode());
        String content = constant.getContent();
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(msg)) {
            content = content.replaceAll("\\{count\\}", msg);
        }
        messageVo.setContent(content);
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setLabel(constant.getLabel());
        messageVo.setType(constant.getType());
        messageVo.setTitle(constant.getTitle());
        messageVo.setToCust(userId);
        messageVo.setViewCode(constant.getMessageViewCode());
        messageVo.setCircleRoute(jumpDetails.getCircleRoute());// 圈子名称,【私圈文章,话题,帖子,问题,活动必填】
        messageVo.setResourceId(jumpDetails.getResourceId());// 资源id,【私圈文章,话题,帖子,问题,活动必填】
        messageVo.setModuleEnum(jumpDetails.getModuleEnum());// 功能id,【私圈文章,话题,帖子,问题,活动必填】
        messageVo.setCoterieId(jumpDetails.getCoterieId());// 私圈id,【私圈文章,话题,帖子,问题,活动必填】

        messageVo.setCircleId(org.apache.commons.lang3.StringUtils.defaultString(String.valueOf(ReflectionUtils.invokeGetterMethod(body, "circleId"))));
        messageVo.setImg(org.apache.commons.lang3.StringUtils.defaultString(String.valueOf(ReflectionUtils.invokeGetterMethod(body, "bodyImg"))));
        messageVo.setBody(body);
        logger.info("send message :" + JSONObject.toJSONString(messageVo));
        //sendMessage(messageVo, flag);
    }

    public static MessageVo buildMessageForComment(MessageConstant constant, String userId, String msg, Object body,
                                      String resourceId, String moduleEnum, String circleId, String circleRoute, String coterieId, boolean flag) {
        MessageVo messageVo = new MessageVo();
        messageVo.setMessageId(UUID.randomUUID().toString());
        messageVo.setActionCode(constant.getMessageActionCode());
        String content = constant.getContent();
        if (StringUtils.isNotEmpty(msg)) {
            content = content.replaceAll("\\{count\\}", msg);
        }
        messageVo.setContent(content);
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setLabel(constant.getLabel());
        messageVo.setType(constant.getType());
        messageVo.setTitle(constant.getTitle());
        messageVo.setToCust(userId);
        messageVo.setViewCode(constant.getMessageViewCode());
        messageVo.setBody(body);
        messageVo.setResourceId(resourceId);
        messageVo.setModuleEnum(moduleEnum);
        messageVo.setCircleId(circleId);
        messageVo.setCircleRoute(circleRoute);
        messageVo.setCoterieId(coterieId);
        logger.info("send message :" + JSONObject.toJSONString(messageVo));
        return messageVo;
    }

    /**
     * 发送持久化消息
     *
     * @param message
     */
    /*public void sendMessage(MessageVo message, boolean flag) {
        if (null == message) {
            return;
        }

        if ("0".equals(message.getCoterieId())) {
            message.setCoterieId(null);
        }
        Object body = message.getBody();
        if (body != null) {
            Object coterieId = ReflectionUtils.invokeGetterMethod(body, "coterieId");
            if (coterieId != null && "0".equals(coterieId.toString())) {
                ReflectionUtils.invokeSetterMethod(body, "coterieId", "");
            }
        }
        logger.info("send message :" + JSON.toJSONString(message));
        messageClient.sendMessage(message, flag);
    }*/

    /**
     * 推送持久化消息
     *
     * @param
     */
    /*public void pushMessage(MessageVo message) {
        pushClient.sendByAlias(message.getToCust(), message.getTitle(), JSONObject.toJSONString(message));
    }*/

    public static void main(String[] args){
        String content = "xx{count}xx";
        String msg = "========";
        String regex = "{count}";
        if (content.indexOf(regex) > -1) {
            content = StringUtils.substringBefore(content, regex) + msg
                    + StringUtils.substringAfter(content, regex);
        }
        System.out.println(content);
    }
}
