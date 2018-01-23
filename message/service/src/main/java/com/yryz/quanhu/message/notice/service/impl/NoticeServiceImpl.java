package com.yryz.quanhu.message.notice.service.impl;

import com.yryz.quanhu.message.notice.dao.NoticeDao;
import com.yryz.quanhu.message.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:14
 * @Author: pn
 */
@Service
public class NoticeServiceImpl implements NoticeService{

    @Autowired
    private NoticeDao noticeDao;

}
