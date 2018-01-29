package com.yryz.quanhu.user.dao;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserRelationEntity;
import com.yryz.quanhu.user.provider.UserRelationProvider;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.yryz.quanhu.user.contants.UserRelationConstant.NO;
import static com.yryz.quanhu.user.contants.UserRelationConstant.YES;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 19:42
 * Created by huangxy
 */
@Service
@Transactional
public class UserRelationCacheDao {

    private static final Logger logger = LoggerFactory.getLogger(UserRelationCacheDao.class);
    /**
     * 用户关系redis过期时间
     */
    @Value("${user.relation.expireDays}")
    private int expireDays;

    @Value("${user.relation.mq.direct.exchange}")
    private String mqDirectExchange;

    @Value("${user.relation.mq.queue}")
    private String mqQueue;

    public static String RELATION_KEY_PREFIX = "RELATION.";
    public static String RELATION_COUNT_KEY_PREFIX = "RELATION.COUNT.";

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRelationDao userRelationDao;

    @Reference
    private ImAPI imAPI;
    /**
     * 获取唯一关系
     * @param sourceUserId
     * @param targetUserId
     * @return
     */
    public UserRelationDto getCacheRelation(String sourceUserId, String targetUserId){

        UserRelationDto dto = new UserRelationDto();
        UserRelationEntity entity = null;
        /**
         * 获取唯一关系Key
         */
        String key = getCacheKey(sourceUserId,targetUserId);
        try{
            /**
             * 查询redis
             */
            RedisTemplate<String,UserRelationEntity> redisTemplate =
                    redisTemplateBuilder.buildRedisTemplate(UserRelationEntity.class);

            entity = redisTemplate.opsForValue().get(key);
            if(null != entity){
                BeanUtils.copyProperties(dto,entity);
                dto.setNewRecord(false);
                return dto;
            }
            /**
             * 查询数据库
             */
            dto = userRelationDao.selectByUser(UserRelationDto.class,sourceUserId,targetUserId);
            if(null != dto){
                dto.setNewRecord(false);
            }
            return dto;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public static String getCacheKey(String sourceUserId,String targetUserId){
        return RELATION_KEY_PREFIX+sourceUserId+"/"+targetUserId;
    }

    public static String getCacheTotalCountKey(String userId){
        return RELATION_COUNT_KEY_PREFIX+userId;
    }

    public UserRelationCountDto getCacheCount(String userId){
        /**
         * 查询redis中计数对象，
         */
        RedisTemplate<String,UserRelationCountDto> redisTemplate =
                redisTemplateBuilder.buildRedisTemplate(UserRelationCountDto.class);

        String key = getCacheTotalCountKey(userId);

        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 刷新缓存统计数据
     * @param sourceUserId
     * @param dto
     */
    public void refreshCacheCount(String sourceUserId,UserRelationCountDto dto){
        /**
         * 查询redis中计数对象，
         */
        RedisTemplate<String,UserRelationCountDto> redisTemplate =
                redisTemplateBuilder.buildRedisTemplate(UserRelationCountDto.class);

        String key = getCacheTotalCountKey(sourceUserId);

        redisTemplate.opsForValue().set(key,dto,expireDays,TimeUnit.DAYS);
    }

    public void refreshCacheRelation(UserRelationDto dto){

        RedisTemplate<String,UserRelationEntity> redisTemplate =
                redisTemplateBuilder.buildRedisTemplate(UserRelationEntity.class);

        /**
         * redis单向关系缓存
         */
        String sourceKey = getCacheKey(dto.getSourceUserId(),dto.getTargetUserId());

        /**
         * RedisTemplate 对象 统一存储 数据结构对象，不采用dto，需要转换
         */
        UserRelationEntity entity = new UserRelationEntity();
        BeanUtils.copyProperties(entity,dto);

        redisTemplate.opsForValue().set(sourceKey,entity,expireDays, TimeUnit.DAYS);
    }


    /**
     * 发生到MQ异步处理
     * @param userDto
     */
    public void sendMQ(UserRelationDto userDto){
        try {
            logger.info("sendMQ={}/{} start",userDto.getSourceUserId(),userDto.getTargetUserId());
            //转换消息
            String msg = MAPPER.writeValueAsString(userDto);
            logger.info("sendMQ.convert={}",msg);
            //发送消息
            rabbitTemplate.setExchange(mqDirectExchange);
            rabbitTemplate.setRoutingKey(mqQueue);

            rabbitTemplate.convertAndSend(msg);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }finally {
            logger.info("sendMQ={}/{} finish",userDto.getSourceUserId(),userDto.getTargetUserId());
        }
    }

    /**
     * 异步MQ处理
     * @param data
     */

    /**
     * QueueBinding: exchange和queue的绑定
     * Queue:队列声明
     * Exchange:声明exchange
     * key:routing-key
     * @param data
     */
    @RabbitListener(bindings = @QueueBinding(
            value= @Queue(value="${user.relation.mq.queue}",durable="true"),
            exchange=@Exchange(value="${user.relation.mq.direct.exchange}",ignoreDeclarationExceptions="true",type=ExchangeTypes.DIRECT),
            key="${user.relation.mq.queue}")
    )
    public void handleMessage(String data){
        logger.info("handleMessage={} start",data);
        try{
            //反序列化对象
            UserRelationDto relationDto = MAPPER.readValue(data,UserRelationDto.class);
            logger.info("handleMessage.convert={}",JSON.toJSON(relationDto));
            //数据库存储
            userRelationDao.update(relationDto);

            //同步调用第三方建立关系
            this.syncImRelation(relationDto);

            //统计数据
            logger.info("handleMessage.totalCount={} start",relationDto.getSourceUserId());
            UserRelationCountDto dto = this.forceTotalCount(relationDto.getSourceUserId());
            logger.info("handleMessage.totalCount={} finish",JSON.toJSON(dto));

            //刷新至缓存
            logger.info("handleMessage.refreshCache={} start",relationDto.getSourceUserId());
            this.refreshCacheCount(relationDto.getSourceUserId(),dto);
            logger.info("handleMessage.refreshCache={} finish",relationDto.getSourceUserId());

        }catch (Exception e){
            throw new RuntimeException(e);
        }finally {
            logger.info("handleMessage={} finish",data);
        }
    }

    public UserRelationCountDto forceTotalCount(String userId){

        UserRelationCountDto dto = new UserRelationCountDto();
        //粉丝
        long count = userRelationDao.selectTotalCount(userId,UserRelationConstant.STATUS.FANS.getCode());
        dto.setFansCount(count);
        //关注
        count = userRelationDao.selectTotalCount(userId,UserRelationConstant.STATUS.FOLLOW.getCode());
        dto.setFollowCount(count);
        //拉黑
        count = userRelationDao.selectTotalCount(userId,UserRelationConstant.STATUS.TO_BLACK.getCode());
        dto.setToBlackCount(count);
        //被拉黑
        count = userRelationDao.selectTotalCount(userId,UserRelationConstant.STATUS.FROM_BLACK.getCode());
        dto.setFromBlackCount(count);
        //好友
        count = userRelationDao.selectTotalCount(userId,UserRelationConstant.STATUS.FRIEND.getCode());
        dto.setFriendCount(count);

        return dto;
    }

    /**
     * 同步Im用户关系
     * 好友/黑名单
     *
     * 现dev阶段，im不通，不调用
     * @param dto
     * @return
     */
    public void syncImRelation(UserRelationDto dto){

        ImRelation im = new ImRelation();
        im.setUserId(dto.getSourceUserId());
        im.setTargetUserId(dto.getTargetUserId());

        //判断拉黑关系
        if(dto.getBlackStatus()==YES){
            im.setRelationType("1");
            im.setRelationValue("1");
            try{
                logger.info("syncImRelation.setSpecialRelation ={} start",JSON.toJSON(im));
//                imAPI.setSpecialRelation(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"添加黑名单失败");
            }finally {
                logger.info("syncImRelation.setSpecialRelation ={} finish",JSON.toJSON(im));
            }
        }else{
            im.setRelationType("1");
            im.setRelationValue("0");
            try{
                logger.info("syncImRelation.setSpecialRelation ={} start",JSON.toJSON(im));
//                imAPI.setSpecialRelation(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"取消黑名单失败");
            }finally {
                logger.info("syncImRelation.setSpecialRelation ={} finish",JSON.toJSON(im));
            }
        }

        //判断好友关系
        if(dto.getFriendStatus()==YES){
            try{
                logger.info("syncImRelation.addFriend ={} start",JSON.toJSON(im));
//                imAPI.addFriend(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"添加好友关系失败");
            }finally {
                logger.info("syncImRelation.addFriend ={} finish",JSON.toJSON(im));
            }
        }else{
            try{
                logger.info("syncImRelation.deleteFriend ={} start",JSON.toJSON(im));
                imAPI.deleteFriend(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"删除好友关系失败");
            }finally {
                logger.info("syncImRelation.deleteFriend ={} finish",JSON.toJSON(im));
            }
        }
    }
}
