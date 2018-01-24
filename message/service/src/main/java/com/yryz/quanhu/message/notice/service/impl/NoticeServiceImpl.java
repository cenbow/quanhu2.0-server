package com.yryz.quanhu.message.notice.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageActionCode;
import com.yryz.common.message.MessageLabel;
import com.yryz.common.message.MessageType;
import com.yryz.common.message.MessageViewCode;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.IdGen;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.message.message.vo.MessageVo;
import com.yryz.quanhu.message.notice.constants.Constant;
import com.yryz.quanhu.message.notice.constants.NoticeConstants;
import com.yryz.quanhu.message.notice.dao.NoticeDao;
import com.yryz.quanhu.message.notice.dto.NoticeAdminDto;
import com.yryz.quanhu.message.notice.dto.NoticeDto;
import com.yryz.quanhu.message.notice.entity.Notice;
import com.yryz.quanhu.message.notice.service.NoticeService;
import com.yryz.quanhu.message.notice.utils.AdvertUtil;
import com.yryz.quanhu.message.notice.utils.OssManager;
import com.yryz.quanhu.message.notice.utils.ThreadPoolUtils;
import com.yryz.quanhu.message.notice.vo.NoticeAdminVo;
import com.yryz.quanhu.message.notice.vo.NoticeDetailVo;
import com.yryz.quanhu.message.notice.vo.NoticeVo;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
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

    @Reference(check = false, timeout = 30000, lazy = true)
    private IdAPI idAPI;

    @Reference(check = false, timeout = 30000, lazy = true)
    private MessageAPI messageAPI;

    @Reference(check = false, timeout = 30000, lazy = true)
    private PushAPI pushAPI;

    @Value("OSS_ENDPOINT")
    public String endpoint;

    @Value("OSS_ACCESSKEYID")
    public String accessKeyId;

    @Value("OSS_SECRETACCESSKEY")
    public String secretAccessKey;

    @Value("OSS_BUCKETNAME")
    public String bucketName;

    public static final String QH_NOTICE = "qh_notice";

    public static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);

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

    @Override
    @Transactional
    public Integer add(Notice notice) {
        notice.setNoticeStatus(NoticeConstants.NOTICE_DOWN);

        upload(notice);

        Response<Long> response = idAPI.getKid(QH_NOTICE);
        Long kid = ResponseUtils.checkResponseNotNull(response);
        notice.setKid(kid);

        return noticeDao.add(notice);
    }

    @Override
    public PageList<NoticeAdminVo> listAdmin(NoticeAdminDto noticeAdminDto) {
        PageHelper.startPage(noticeAdminDto.getPageNo(), noticeAdminDto.getPageSize());
        List<NoticeAdminVo> list = noticeDao.listAdmin(noticeAdminDto);
        return new PageModel<NoticeAdminVo>().getPageList(list);
    }

    @Override
    public NoticeAdminVo detailAdmin(NoticeAdminDto noticeAdminDto) {
        return noticeDao.detailAdmin(noticeAdminDto);
    }

    @Override
    @Transactional
    public Integer update(Notice notice) {
        upload(notice);
        int update = noticeDao.update(notice);
        if (notice.getNoticeStatus().equals(NoticeConstants.NOTICE_UP)) {
            messagePush(notice);
        }
        return update;
    }

    private void messagePush(Notice notice) {
        MessageVo messageVo = new MessageVo();
        // messageVo.setContent(notice.getContent());
        messageVo.setCreateTime(DateUtils.formatDate(new Date()));
        messageVo.setLabel(MessageLabel.NOTICE_ANNOUNCEMENT);
        messageVo.setMessageId(IdGen.uuid());
        messageVo.setTitle(notice.getTitle());
        messageVo.setType(MessageType.NOTICE_TYPE);
        messageVo.setImg(notice.getSmallPic());
        messageVo.setActionCode(MessageActionCode.NOTICE_URL);
        messageVo.setViewCode(MessageViewCode.NOTICE_MESSAGE);
        messageVo.setLink(notice.getContentPath());
        if (notice.getNoticeType().equals(Constant.NoticeLinkType.INNER.getType())) {
            messageVo.setActionCode(MessageActionCode.INNER_URL);
        }

        commitMessage(messageVo, false);

        PushReqVo reqVo = new PushReqVo();
        reqVo.setPushType(PushReqVo.CommonPushType.BY_ALL);
        reqVo.setMsg(JsonUtils.toFastJson(messageVo));
        pushAPI.commonSendAlias(reqVo);
    }

    private void upload(Notice notice) {
        if (StringUtils.isNotBlank(notice.getContent())) {
            OssManager ossManager = new OssManager(endpoint, accessKeyId, secretAccessKey, bucketName);
            String htmlUrl = AdvertUtil.getUploadNoticeHtmlUrl(notice, ossManager);
            if (StringUtils.isBlank(htmlUrl)) {
                LOGGER.error("上传公告HTML出错！");
                throw QuanhuException.busiError("上传公告HTML出错！");
            }
            notice.setContentPath(htmlUrl);
        }
    }

    /**
     * 发送消息
     *
     * @param messageVo
     * @param flag
     */
    private void commitMessage(MessageVo messageVo, boolean flag) {
        ThreadPoolUtils.newThread(new Runnable() {
            @Override
            public void run() {
                try {
                    messageAPI.sendMessage(messageVo, flag);
                } catch (RuntimeException e) {
                    LOGGER.error("[message notice ]", e);
                } catch (Exception e) {
                    LOGGER.error("[message notice ]", e);
                }
            }
        });
    }
}
