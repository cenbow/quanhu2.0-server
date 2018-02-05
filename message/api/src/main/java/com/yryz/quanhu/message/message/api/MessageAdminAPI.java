package com.yryz.quanhu.message.message.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 10:17
 * @Author: pn
 */
public interface MessageAdminAPI {

    /**
     * 获取消息列表
     *
     * @param messageAdminDto
     * @return
     */
    Response<PageList<MessageAdminVo>> listAdmin(MessageAdminDto messageAdminDto);

    /**
     * 推送消息
     *
     * @param messageAdminVo
     * @return
     */
    Response<MessageAdminVo> push(MessageAdminVo messageAdminVo);

    /**
     * 更新未发送消息
     *
     * @param messageAdminVo
     * @return
     */
    Response<MessageAdminVo> update(MessageAdminVo messageAdminVo);

    /**
     * 查询详情
     *
     * @param messageAdminDto
     * @return
     */
    Response<MessageAdminVo> findOne(MessageAdminDto messageAdminDto);

    /**
     *
     * @return
     */
    Response<List<MessageAdminVo>> startCheck();
}
