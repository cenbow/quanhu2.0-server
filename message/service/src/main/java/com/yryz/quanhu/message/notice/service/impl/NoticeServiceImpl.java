package com.yryz.quanhu.message.notice.service.impl;

import com.yryz.quanhu.message.notice.dao.NoticeDao;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.service.NoticeService;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:14
 * @Author: pn
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeDao noticeDao;

    @Override
    public NoticeVo list(NoticeDto noticeDto) {
        List<Notice> onlineList = noticeDao.selectOnlineList(noticeDto);
        List<Long> offlineList = noticeDao.selectOfflineList(noticeDto);
        return new NoticeVo(onlineList, offlineList);
    }

    @Override
    public NoticeDetailVo detail(NoticeDto noticeDto) {
        return noticeDao.detail(noticeDto);
    }
}
