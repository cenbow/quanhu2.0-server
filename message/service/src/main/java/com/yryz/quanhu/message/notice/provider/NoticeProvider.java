package com.yryz.quanhu.message.notice.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.notice.api.NoticeAPI;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.service.NoticeService;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeProvider.class);

    @Autowired
    private NoticeService noticeService;

    @Override
    public Response<NoticeVo> list(NoticeDto noticeDto) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.list(noticeDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询公告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询公告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<NoticeDetailVo> detail(NoticeDto noticeDto) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.detail(noticeDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询公告详情异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询公告详情异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> add(Notice notice) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.add(notice));
        } catch (QuanhuException e) {
            LOGGER.error("添加公告详情异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("添加公告详情异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<NoticeAdminVo>> listAdmin(NoticeAdminDto noticeAdminDto) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.listAdmin(noticeAdminDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询公告列表异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询公告列表异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<NoticeAdminVo> detailAdmin(NoticeAdminDto noticeAdminDto) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.detailAdmin(noticeAdminDto));
        } catch (QuanhuException e) {
            LOGGER.error("查询公告详情异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("查询公告详情异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> update(Notice notice) {
        try {
            return ResponseUtils.returnObjectSuccess(noticeService.update(notice));
        } catch (QuanhuException e) {
            LOGGER.error("更新公告异常", e);
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            LOGGER.error("更新公告异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}
