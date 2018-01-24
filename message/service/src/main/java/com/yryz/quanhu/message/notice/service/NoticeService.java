package com.yryz.quanhu.message.notice.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
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

    Integer add(Notice notice);

    PageList<NoticeAdminVo> listAdmin(NoticeAdminDto noticeAdminDto);

    NoticeAdminVo detailAdmin(NoticeAdminDto noticeAdminDto);

    Integer update(Notice notice);
}
