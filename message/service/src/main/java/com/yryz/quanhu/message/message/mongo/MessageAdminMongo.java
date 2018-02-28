package com.yryz.quanhu.message.message.mongo;

import com.yryz.common.exception.QuanhuException;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.common.mongodb.Page;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.message.constants.MessageContants;
import com.yryz.quanhu.message.message.dto.MessageAdminDto;
import com.yryz.quanhu.message.message.vo.MessageAdminVo;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/31 10:20
 * @Author: pn
 */
@Service
public class MessageAdminMongo extends AbsBaseMongoDAO<MessageAdminVo> {

    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }


    public MessageAdminVo update(MessageAdminVo messageAdminVo) {
        Query query = new Query();
        Update update = new Update();
        if (StringUtils.isNotBlank(messageAdminVo.getMessageId())) {
            query.addCriteria(Criteria.where("messageId").is(messageAdminVo.getMessageId()));
        }

        if (messageAdminVo.getPushStatus() != null) {
            update.set("pushStatus", messageAdminVo.getPushStatus());
        }

        if (messageAdminVo.getDelFlag() != null) {
            update.set("delFlag", messageAdminVo.getDelFlag());
        }

        if (messageAdminVo.getMessageSource() != null) {
            update.set("messageSource", messageAdminVo.getMessageSource());
        }

        if (StringUtils.isNotBlank(messageAdminVo.getTitle())) {
            update.set("title", messageAdminVo.getTitle());
        }

        if (StringUtils.isNotBlank(messageAdminVo.getContent())) {
            update.set("content", messageAdminVo.getContent());
        }

        if (StringUtils.isNotBlank(messageAdminVo.getImg())) {
            update.set("img", messageAdminVo.getImg());
        }

        if (StringUtils.isNotBlank(messageAdminVo.getLink())) {
            update.set("link", messageAdminVo.getLink());
        }

        if (messageAdminVo.getPersistentType() != null) {
            update.set("persistentType", messageAdminVo.getPersistentType());
        }

        if (CollectionUtils.isNotEmpty(messageAdminVo.getPushUserIds())) {
            update.set("pushUserIds", messageAdminVo.getPushUserIds());
        }

        if (messageAdminVo.getPushType() != null) {
            update.set("pushType", messageAdminVo.getPushType());
        }

        if (messageAdminVo.getPushDate() != null) {
            update.set("pushDate", messageAdminVo.getPushDate());
        }
        if (org.apache.commons.lang.StringUtils.isNotBlank(messageAdminVo.getJpId())) {
            update.set("jpId", messageAdminVo.getJpId());
        }

        return this.update(query, update);
    }

    public PageList<MessageAdminVo> listAdmin(MessageAdminDto messageAdminDto) {
        Page page = new Page();
        page.setCurrentPage(messageAdminDto.getPageNo());
        page.setPageSize(messageAdminDto.getPageSize());

        Query query = new Query();
        if (StringUtils.isNotBlank(messageAdminDto.getTitle())) {
            query.addCriteria(Criteria.where("title").regex(".*?" + messageAdminDto.getTitle() + ".*"));
        }

        if (messageAdminDto.getPersistentType() != null) {
            query.addCriteria(Criteria.where("persistentType").is(messageAdminDto.getPersistentType()));
        }

        if (messageAdminDto.getPushStatus() != null) {
            query.addCriteria(Criteria.where("pushStatus").is(messageAdminDto.getPushStatus()));
        }

        if (StringUtils.isNotBlank(messageAdminDto.getStartDate())) {
            if (StringUtils.isNotBlank(messageAdminDto.getEndDate())) {
                query.addCriteria(Criteria.where("pushDate").gte(messageAdminDto.getStartDate()).lte(messageAdminDto.getEndDate()));
            } else {
                query.addCriteria(Criteria.where("pushDate").gte(messageAdminDto.getStartDate()));
            }
        } else if (StringUtils.isNotBlank(messageAdminDto.getEndDate())) {
            query.addCriteria(Criteria.where("pushDate").lte(messageAdminDto.getEndDate()));
        }

        /*if (StringUtils.isNotBlank(messageAdminDto.getEndDate())) {
            query.addCriteria(Criteria.where("pushDate").lte(messageAdminDto.getEndDate()));
        }*/

        query.addCriteria(Criteria.where("delFlag").is(MessageContants.DEL_FLAG_NOT_DELETE));
        query.with(new Sort(Sort.Direction.DESC, "pushDate"));

        Page page1 = this.findPage(page, query);

        PageList<MessageAdminVo> pageList = new PageList<>();
        pageList.setCount(Long.valueOf(page1.getCount()));
        pageList.setCurrentPage(messageAdminDto.getPageNo());
        pageList.setPageSize(messageAdminDto.getPageSize());
        pageList.setEntities((List<MessageAdminVo>) page1.getEntities());
        return pageList;
    }

    public MessageAdminVo findOne(MessageAdminDto messageAdminDto) {
        if (StringUtils.isBlank(messageAdminDto.getMessageId())) {
            throw QuanhuException.busiError("消息id不能为空！");
        }
        Query query = new Query();
        query.addCriteria(Criteria.where("messageId").is(messageAdminDto.getMessageId()));
        return this.findOne(query);
    }

    public List<MessageAdminVo> startCheck() {
        Query query = new Query();
        String dateTime = DateUtils.getDateTime();
        query.addCriteria(Criteria.where("delFlag").is(MessageContants.DEL_FLAG_NOT_DELETE))
                .addCriteria(Criteria.where("pushType").is(MessageContants.PUSH_TYPE_TIMING))
                .addCriteria(Criteria.where("pushDate").gt(dateTime))
                .addCriteria(Criteria.where("pushStatus").is(MessageContants.PUSH_STATUS_NOT));

        return this.find(query);
    }
}
