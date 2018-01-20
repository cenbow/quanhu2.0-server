package com.yryz.quanhu.user.dao;

import com.alibaba.fastjson.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserRelationEntity;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.bson.BSONObject;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapreduce.GroupBy;
import org.springframework.data.mongodb.core.mapreduce.GroupByResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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

    private static final String TABLE_NAME = "qh_user_relation";

    @Value("${user.relation.expireDays}")
    private int expireDays;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRelationDao userRelationDao;

    /**
     * 获取唯一关系
     * @param sourceUserId
     * @param targetUserId
     * @return
     */
    public UserRelationDto getUserRelation(String sourceUserId,String targetUserId){

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
                return dto;
            }
            /**
             * 查询mongo
             */

            //查询条件
            Query query = new Query();
            query.addCriteria(Criteria.where("sourceUserId").is(sourceUserId));
            query.addCriteria(Criteria.where("targetUserId").is(targetUserId));
            query.fields().exclude("_id");

            //查询
            List<UserRelationEntity> array = mongoTemplate.find(query,UserRelationEntity.class,TABLE_NAME);
            if(null!=array&&array.size()>0){
                BeanUtils.copyProperties(dto,array.get(0));
                return dto;
            }

            /**
             * 查询数据库
             */
            return userRelationDao.selectByUser(UserRelationDto.class,sourceUserId,targetUserId);

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getCacheKey(String sourceUserId,String targetUserId){
        return "relation/"+sourceUserId+"/"+targetUserId;
    }

    public static String getCacheTotalKey(String userId){
        return "total/"+userId;
    }


    public void setUserRelation(UserRelationDto dto){

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

        /**
         * 有修改关系的时候，同步刷新统计信息
         */


    }

    /**
     * 发生到MQ异步处理
     * @param userDto
     */
    public void sendMQ(UserRelationDto userDto){
        userRelationDao.update(userDto);
    }


    public void handleMessage(String data){

        System.out.println("hello Fanout Message:" + data);
        try{
            //反序列化对象

            //数据库存储

        }catch (Exception e){

        }
    }

    public PageList<UserRelationDto> selectByPage(int currentPage,int pageSize,String sourceUserId,UserRelationApi.STATUS status) {
        Query query = this.buildQuery(sourceUserId,status);
        query.skip(currentPage*pageSize);
        query.limit(pageSize);
        query.fields().exclude("_id");

        /**
         * 查询数据
         */
        List<UserRelationDto> resultArray = mongoTemplate.find(query,UserRelationDto.class,TABLE_NAME);
        /**
         * 查询总数
         */
        long count = mongoTemplate.count(query,TABLE_NAME);

        PageList pageList = new PageList();
        pageList.setEntities(resultArray);
        pageList.setPageSize(resultArray.size());
        pageList.setCurrentPage(currentPage);
        pageList.setCount(count);

        return pageList;

    }

    public List<UserRelationDto> selectBy(String sourceUserId, String[] targetUserIds) {
        Query query = new Query();

        query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
        query.addCriteria(Criteria.where("target_user_id").in(targetUserIds));

        return mongoTemplate.find(query,UserRelationDto.class,TABLE_NAME);
    }

    public List<UserRelationDto> selectBy(String sourceUserId, UserRelationApi.STATUS status){
        Query query = this.buildQuery(sourceUserId,status);
        return mongoTemplate.find(query,UserRelationDto.class,TABLE_NAME);
    }

    /**
     * 统计用户关系数量
     * {
     *     粉丝数
     *     被拉黑
     *     关注数
     *     黑名单
     *     好友数
     * }
     * @param userSourceKid
     * @return
     */
    public UserRelationCountDto totalBy(String userKid) {

        UserRelationCountDto dto = new UserRelationCountDto();
//        /**
//         * 查询redis缓存是否为最新数据
//         *
//         * 不是则从mongo中统计，刷新至redis
//         */
//        String key = getCacheTotalKey(userKid);
//        RedisTemplate<String,UserRelationCountDto> redisTemplate =
//                redisTemplateBuilder.buildRedisTemplate(UserRelationCountDto.class);
//        dto = redisTemplate.opsForValue().get(key);
//        if(null != dto && !dto.isNewRecord()){
//            return dto;
//        }

        /**
         * 查询mongo进行统计
         */

        //关注数
        Query query = this.buildQuery(userKid, UserRelationApi.STATUS.FOLLOW);
        long count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFollowCount(count);

        //黑名单数
        query = this.buildQuery(userKid, UserRelationApi.STATUS.TO_BLACK);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setToBlackCount(count);

        //好友数
        query = this.buildQuery(userKid, UserRelationApi.STATUS.FRIEND);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFriendCount(count);

        //粉丝数
        query = this.buildQuery(userKid, UserRelationApi.STATUS.FANS);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFansCount(count);

        //被拉黑数
        query = this.buildQuery(userKid, UserRelationApi.STATUS.FROM_BLACK);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFromBlackCount(count);

//        /**
//         * 设置成新数据，刷新至redis
//         */
//        dto.setNewRecord(true);
//        redisTemplate.opsForValue().set(key,dto,expireDays);

        return dto;
    }


    private Query buildQuery(String sourceUserId,UserRelationApi.STATUS status){

        Query query = new Query();
        if(UserRelationApi.STATUS.FANS == status){                      //粉丝

            query.addCriteria(Criteria.where("target_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("follow_status").is(UserRelationApi.YES));

        }else if(UserRelationApi.STATUS.FOLLOW == status){              //关注

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("follow_status").is(UserRelationApi.YES));

        }else if(UserRelationApi.STATUS.FRIEND == status){              //好友

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("friend_status").is(UserRelationApi.YES));

        }else if(UserRelationApi.STATUS.TO_BLACK == status){            //拉黑

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("black_status").is(UserRelationApi.YES));

        }else if(UserRelationApi.STATUS.FROM_BLACK == status){          //被拉黑

            query.addCriteria(Criteria.where("target_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("black_status").is(UserRelationApi.YES));
        }

        return query;
    }

}
