package com.yryz.quanhu.message;

import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.message.message.api.MessageAPI;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/30 11:38
 * @Author: pn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageTest {

    @Autowired
    MessageAPI messageAPI;

    @Test
    public void sendMessage() {

        MessageVo messageVo = new MessageVo();
        messageVo.setToCust("727909974996672512");
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setType(MessageConstant.QUESTION_DELETE.getType());
        messageVo.setLabel(MessageConstant.QUESTION_DELETE.getLabel());
        messageVo.setContent(MessageConstant.QUESTION_DELETE.getContent());
        messageVo.setActionCode(MessageConstant.QUESTION_DELETE.getMessageActionCode());
        messageVo.setCreateTime(DateUtils.getDateTime());
        messageVo.setViewCode(MessageConstant.QUESTION_DELETE.getMessageViewCode());
        messageVo.setCoterieId("123456");
        messageVo.setCircleId("123456");
        messageVo.setResourceId("123456");
        messageVo.setModuleEnum("123456");
        Response<Boolean> booleanResponse = messageAPI.sendMessage(messageVo, true);
        Boolean aBoolean = ResponseUtils.getResponseNotNull(booleanResponse);
        System.out.println(aBoolean);
    }
}
