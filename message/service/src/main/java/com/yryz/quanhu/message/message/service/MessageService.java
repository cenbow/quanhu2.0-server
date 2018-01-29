package com.yryz.quanhu.message.message.service;

import com.yryz.common.message.MessageVo;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.vo.MessageStatusVo;

import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:54
 * @Author: pn
 */
public interface MessageService {

    Map<String, String> getUnread(Long userId);

    MessageStatusVo clearUnread(MessageDto messageDto);

    List<MessageVo> getList(MessageDto messageDto);

    Map<Integer, MessageVo> getMessageCommon(Long userId);

    Boolean sendMessage(MessageVo messageVo, boolean flag);

    MessageVo get(String messageId);

}
