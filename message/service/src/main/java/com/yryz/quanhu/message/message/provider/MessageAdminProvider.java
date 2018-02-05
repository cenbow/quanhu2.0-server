package com.yryz.quanhu.message.message.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.message.api.MessageAdminAPI;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.service.MessageAdminService;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageAdminProvider.class);

    @Autowired
    private MessageAdminService messageAdminService;

    @Override
    public Response<PageList<MessageAdminVo>> listAdmin(MessageAdminDto messageAdminDto) {
        try {
            return ResponseUtils.returnObjectSuccess(messageAdminService.listAdmin(messageAdminDto));
        } catch (QuanhuException e) {
            LOGGER.error("获取管理后台列表异常！", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("获取管理后台列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> push(MessageAdminVo messageAdminVo) {
        try {
            return ResponseUtils.returnObjectSuccess(messageAdminService.push(messageAdminVo));
        } catch (QuanhuException e) {
            LOGGER.error("推送管理后台消息异常！", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("推送管理后台消息异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> update(MessageAdminVo messageAdminVo) {
        try {
            return ResponseUtils.returnObjectSuccess(messageAdminService.update(messageAdminVo));
        } catch (QuanhuException e) {
            LOGGER.error("更新管理后台消息异常！", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("更新管理后台消息异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<MessageAdminVo> findOne(MessageAdminDto messageAdminDto) {
        try {
            return ResponseUtils.returnObjectSuccess(messageAdminService.findOne(messageAdminDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询管理后台消息详情异常！", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询管理后台消息详情异常！", e);
            return ResponseUtils.returnException(e);
        }
    }
}
