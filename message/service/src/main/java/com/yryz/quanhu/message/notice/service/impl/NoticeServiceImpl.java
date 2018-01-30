package com.yryz.quanhu.message.notice.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.yryz.common.exception.ParseDatesException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.*;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.*;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.message.message.api.MessageAPI;
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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

    @Value("${OSS_ENDPOINT}")
    private String endpoint;

    @Value("${OSS_ACCESSKEYID}")
    private String accessKeyId;

    @Value("${OSS_SECRETACCESSKEY}")
    private String secretAccessKey;

    @Value("${OSS_BUCKETNAME}")
    private String bucketName;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    //public static final String QH_NOTICE = "qh_notice";

    //redis key
    public static final String QH_NOTICE_ = "qh:notice:";

    public static final String QH_NOTICE_SEARCH_DATE = "qh:notice:date:";

    public static final String QH_NOTICE_LIST_ = "qh:notice:list:";

    public static final Integer EXPIRE_DAYS = 7;

    public static final Logger LOGGER = LoggerFactory.getLogger(NoticeServiceImpl.class);

    @Override
    public NoticeVo list(NoticeDto noticeDto) {
        try {
            if (StringUtils.isBlank(noticeDto.getSearchDate())) {
                throw QuanhuException.busiError("查询时间点为空！");
            }

            RedisTemplate<String, NoticeVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(NoticeVo.class);
            NoticeVo noticeVo = redisTemplate.opsForValue().get(QH_NOTICE_SEARCH_DATE + noticeDto.getSearchDate());
            if (noticeVo != null) {
                return noticeVo;
            }

            List<Notice> onlineList = noticeDao.selectOnlineList(noticeDto);
            List<Long> offlineList = noticeDao.selectOfflineList(noticeDto);
            NoticeVo noticeVo1 = new NoticeVo(onlineList, offlineList);
            if (onlineList != null && offlineList != null) {
                redisTemplate.opsForValue().set(QH_NOTICE_SEARCH_DATE + noticeDto.getSearchDate(), noticeVo1, EXPIRE_DAYS, TimeUnit.DAYS);
            }

            return noticeVo1;
        } catch (QuanhuException e) {
            LOGGER.error("查询列表异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询列表异常！", e);
            throw e;
        }
    }

    @Override
    public NoticeDetailVo detail(NoticeDto noticeDto) {
        try {
            if (noticeDto.getKid() == null) {
                throw QuanhuException.busiError("kid不能为空！");
            }

            RedisTemplate<String, NoticeDetailVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(NoticeDetailVo.class);
            NoticeDetailVo noticeDetailVo = redisTemplate.opsForValue().get(QH_NOTICE_ + noticeDto.getKid());
            if (noticeDetailVo != null) {
                return noticeDetailVo;
            }

            NoticeDetailVo detail = noticeDao.detail(noticeDto);
            if (detail != null) {
                redisTemplate.opsForValue().set(QH_NOTICE_ + noticeDto.getKid(), detail, EXPIRE_DAYS, TimeUnit.DAYS);
            }

            return detail;
        } catch (QuanhuException e) {
            LOGGER.error("查询详情异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询详情异常！", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Integer add(Notice notice) {
        try {
            notice.setNoticeStatus(NoticeConstants.NOTICE_DOWN);

            upload(notice);

            Response<Long> response = idAPI.getSnowflakeId();
            Long kid = ResponseUtils.getResponseData(response);
            notice.setKid(kid);

            Integer integer = noticeDao.add(notice);
            if (integer != null && integer == 1) {
                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setKid(kid);
                NoticeDetailVo detail = noticeDao.detail(noticeDto);
                if (detail != null) {
                    RedisTemplate<String, NoticeDetailVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(NoticeDetailVo.class);
                    deleteRedisList(redisTemplate, QH_NOTICE_LIST_ + "*");
                    deleteRedisList(redisTemplate, QH_NOTICE_SEARCH_DATE + "*");
                    redisTemplate.opsForValue().set(QH_NOTICE_ + kid, detail, EXPIRE_DAYS, TimeUnit.DAYS);
                }
            }

            return integer;
        } catch (QuanhuException e) {
            LOGGER.error("添加公告异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("添加公告异常！", e);
            throw e;
        }
    }

    private void deleteRedisList(RedisTemplate redisTemplate, String key) {
        Set<String> keys = redisTemplate.keys(key);
        if (CollectionUtils.isNotEmpty(keys)) {
            redisTemplate.delete(keys);
        }
    }

    @Override
    public PageList<NoticeAdminVo> listAdmin(NoticeAdminDto noticeAdminDto) {
        try {
            /*RedisTemplate<String, String> redisTemplate = redisTemplateBuilder.buildRedisTemplate(String.class);
            String s = redisTemplate.opsForValue().get(QH_NOTICE_LIST_ + noticeAdminDto.getPageNo() + ":" + noticeAdminDto.getPageSize());
            if (StringUtils.isNotBlank(s)) {
                return JSON.parseObject(s, new TypeReference<PageList<NoticeAdminVo>>() {});
            }*/

            PageHelper.startPage(noticeAdminDto.getPageNo(), noticeAdminDto.getPageSize());
            List<NoticeAdminVo> list = noticeDao.listAdmin(noticeAdminDto);
            PageList<NoticeAdminVo> pageList1 = new PageModel<NoticeAdminVo>().getPageList(list);
            /*if (CollectionUtils.isNotEmpty(list) && pageList1 != null) {
                String jsonString = JSON.toJSONString(pageList1);
                redisTemplate.opsForValue().set(QH_NOTICE_LIST_ + noticeAdminDto.getPageNo() + ":" + noticeAdminDto.getPageSize(), jsonString, EXPIRE_DAYS, TimeUnit.DAYS);
            }*/

            return pageList1;
        } catch (QuanhuException e) {
            LOGGER.error("查询公告列表异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询公告列表异常！", e);
            throw e;
        }
    }

    @Override
    public NoticeAdminVo detailAdmin(NoticeAdminDto noticeAdminDto) {
        try {
            if (noticeAdminDto.getKid() == null) {
                throw QuanhuException.busiError("kid为空！");
            }

            RedisTemplate<String, NoticeDetailVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(NoticeDetailVo.class);
            NoticeDetailVo noticeDetailVo = redisTemplate.opsForValue().get(QH_NOTICE_ + noticeAdminDto.getKid());
            if (noticeDetailVo != null) {
                NoticeAdminVo noticeAdminVo = new NoticeAdminVo();
                BeanUtils.copyProperties(noticeAdminVo, noticeDetailVo);
                return noticeAdminVo;
            }

            NoticeAdminVo noticeAdminVo = noticeDao.detailAdmin(noticeAdminDto);
            if (noticeAdminVo != null) {
                NoticeDetailVo noticeDetailVo2 = new NoticeDetailVo();
                BeanUtils.copyProperties(noticeDetailVo2, noticeAdminVo);
                redisTemplate.opsForValue().set(QH_NOTICE_ + noticeAdminDto.getKid(), noticeDetailVo2, EXPIRE_DAYS, TimeUnit.DAYS);
            }

            return noticeAdminVo;
        } catch (QuanhuException e) {
            LOGGER.error("查询公告详情异常！", e);
            throw e;
        } catch (ParseDatesException e) {
            LOGGER.error("查询公告详情异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("查询公告详情异常！", e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Integer update(Notice notice) {
        try {
            if (notice.getKid() == null) {
                throw QuanhuException.busiError("kid为空！");
            }

            upload(notice);
            int update = noticeDao.update(notice);
            if (update == 1) {
                RedisTemplate<String, NoticeDetailVo> redisTemplate = redisTemplateBuilder.buildRedisTemplate(NoticeDetailVo.class);
                deleteRedisList(redisTemplate, QH_NOTICE_LIST_ + "*");
                deleteRedisList(redisTemplate, QH_NOTICE_SEARCH_DATE + "*");

                NoticeDto noticeDto = new NoticeDto();
                noticeDto.setKid(notice.getKid());
                NoticeDetailVo noticeDetailVo = noticeDao.detail(noticeDto);
                if (noticeDetailVo != null) {
                    redisTemplate.opsForValue().set(QH_NOTICE_ + notice.getKid(), noticeDetailVo, EXPIRE_DAYS, TimeUnit.DAYS);
                }
            }

            if (notice.getNoticeStatus().equals(NoticeConstants.NOTICE_UP)) {
                messagePush(notice);
            }
            return update;
        } catch (QuanhuException e) {
            LOGGER.error("更新公告异常！", e);
            throw e;
        } catch (Exception e) {
            LOGGER.error("更新公告异常！", e);
            throw e;
        }
    }

    private void messagePush(Notice notice) {
        MessageVo messageVo = new MessageVo();
        // messageVo.setContent(notice.getContent());
        messageVo.setCreateTime(DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
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

        LOGGER.info("=================极光推送公告开始！=================");
        pushAPI.commonSendAlias(reqVo);
        LOGGER.info("=================极光推送公告完成！=================");

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
