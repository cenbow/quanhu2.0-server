package com.yryz.quanhu.message.message.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.vo.MessageAdminVo; /**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 10:19
 * @Author: pn
 */
public interface MessageAdminService {

    void handleMessage(MessageAdminVo messageAdminVo);

    PageList<MessageAdminVo> listAdmin(MessageAdminDto messageAdminDto);

    Boolean push(MessageAdminVo messageAdminVo);
}
