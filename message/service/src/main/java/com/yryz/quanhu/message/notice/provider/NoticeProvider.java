package com.yryz.quanhu.message.notice.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.message.notice.api.NoticeAPI;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.service.NoticeService;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:12
 * @Author: pn
 */
@Service(interfaceClass = NoticeAPI.class)
public class NoticeProvider implements NoticeAPI {

    @Autowired
    private NoticeService noticeService;

    @Override
    public Response<Notice> get(Long id) {
        return null;
    }

    @Override
    public Response<NoticeVo> detail(Long id) {
        return null;
    }

    @Override
    public Response<PageList<NoticeVo>> list(NoticeDto noticeDto) {
        return null;
    }
}
