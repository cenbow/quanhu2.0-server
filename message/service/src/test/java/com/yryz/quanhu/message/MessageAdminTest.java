package com.yryz.quanhu.message;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.message.*;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.message.message.api.MessageAdminAPI;
import com.yryz.quanhu.message.message.constants.MessageContants;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/2/1 11:53
 * @Author: pn
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MessageAdminTest {

    @Reference(check = false, timeout = 30000, retries = 0)
    private MessageAdminAPI messageAdminAPI;

    @Test
    public void push() {
        MessageAdminVo messageAdminVo = new MessageAdminVo();
        messageAdminVo.setMessageSource(MessageContants.MESSAGE_SOURCE_WRITE);
        messageAdminVo.setTitle("啊哈哈哈哈哈");
        messageAdminVo.setContent("测试没钱没钱没钱没钱吗mqmq");
        messageAdminVo.setImg("http://www.hao123.com");
        messageAdminVo.setLink("http://www.hao123.com");
        messageAdminVo.setPersistentType(MessageContants.PERSISTENT);
        messageAdminVo.setPushType(MessageContants.PUSH_TYPE_START);
        messageAdminVo.setType(MessageType.RECOMMEND_TYPE);
        messageAdminVo.setLabel(MessageLabel.RECOMMEND_CONTENT);
        messageAdminVo.setViewCode(MessageViewCode.RECOMMEND_MESSAGE_1);
        messageAdminVo.setViewCode(MessageActionCode.INNER_URL);
        messageAdminVo.setCreateTime(DateUtils.getDateTime());
        //messageAdminVo.setPushDate("2018-02-01 14:15:00");
        messageAdminVo.setPushDate(DateUtils.getDateTime());
        messageAdminVo.setDelFlag(MessageContants.DEL_FLAG_NOT_DELETE);

        List<String> list = new ArrayList<>();
        list.add("726907134491074560");
        messageAdminVo.setPushUserIds(list);
        Response<MessageAdminVo> booleanResponse = messageAdminAPI.push(messageAdminVo);
        System.out.println(ResponseUtils.getResponseNotNull(booleanResponse));
    }

}
