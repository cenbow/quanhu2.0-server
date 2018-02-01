package com.yryz.quanhu.message.message.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.message.api.MessageAdminAPI;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.service.MessageAdminService;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 10:18
 * @Author: pn
 */
@Service(interfaceClass = MessageAdminAPI.class)
public class MessageAdminProvider implements MessageAdminAPI {

    @Autowired
    private MessageAdminService messageAdminService;

    @Override
    public Response<PageList<MessageAdminVo>> listAdmin(MessageAdminDto messageAdminDto) {
        return ResponseUtils.returnObjectSuccess(messageAdminService.listAdmin(messageAdminDto));
    }

    @Override
    public Response<Boolean> push(MessageAdminVo messageAdminVo) {
        return ResponseUtils.returnObjectSuccess(messageAdminService.push(messageAdminVo));
    }
}
