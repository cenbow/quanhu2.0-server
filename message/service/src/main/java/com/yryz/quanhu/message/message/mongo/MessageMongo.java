package com.yryz.quanhu.message.message.mongo;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.message.message.dto.MessageDto;
import com.yryz.quanhu.message.message.vo.MessageVo;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:55
 * @Author: pn
 */
@Repository
public class MessageMongo extends AbsBaseMongoDAO<MessageVo> {

    public static final Logger LOGGER = LoggerFactory.getLogger(MessageMongo.class);

    public static final String COLLECTION_NAME = "messageVo";

    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<MessageVo> getList(MessageDto messageDto) {
        try {
            Query query = new Query();
            if (messageDto.getType() != null && messageDto.getType() >= 0) {
                query.addCriteria(Criteria.where("type").is(messageDto.getType()));
            }

            if (messageDto.getLabel() != null && messageDto.getLabel() >= 0) {
                query.addCriteria(Criteria.where("label").is(messageDto.getLabel()));
            }

            if (messageDto.getUserId() != null && messageDto.getUserId() > 0) {
                query.addCriteria(Criteria.where("toCust").is(String.valueOf(messageDto.getUserId())));
            }

            if (StringUtils.isNotBlank(messageDto.getMessageId())) {
                query.addCriteria(Criteria.where("messageId").is(messageDto.getMessageId()));
            }

            query.with(new Sort(Sort.Direction.DESC, "createTime")).skip(messageDto.getStart()).limit(messageDto.getLimit());

            return mongoTemplate.find(query, MessageVo.class, COLLECTION_NAME);
        } catch (Exception e) {
            LOGGER.error("【MessageMongo】 查询列表失败！");
            throw new QuanhuException(ExceptionEnum.MONGO_EXCEPTION);
        }
    }

    public MessageVo get(String messageId) {
        try {
            Query query = new Query();
            query.addCriteria(Criteria.where("messageId").is(messageId));
            return mongoTemplate.findOne(query, MessageVo.class, COLLECTION_NAME);
        } catch (Exception e) {
            LOGGER.error("【MessageMongo】 查询单一消息失败！");
            throw new QuanhuException(ExceptionEnum.MONGO_EXCEPTION);
        }
    }
}
