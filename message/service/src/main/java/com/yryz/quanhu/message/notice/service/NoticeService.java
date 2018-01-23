package com.yryz.quanhu.message.notice.service;

import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:13
 * @Author: pn
 */
public interface NoticeService {

    NoticeVo list(NoticeDto noticeDto);

    NoticeDetailVo detail(NoticeDto noticeDto);
}
