package com.yryz.quanhu.user.dao;

import com.yryz.quanhu.user.dto.UserRelationDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 19:42
 * Created by huangxy
 */
@Service
public class UserRelationCacheDao {


    private static final String TABLE_NAME = "user_relation";


    @Resource
    private RedisTemplate<String,UserRelationDto> redisTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private UserRelationDao userRelationDao;

    /**
     * 获取唯一关系
     * @param userSource
     * @param userTarget
     * @return
     */
    public UserRelationDto getUserRelation(String userSourceId,String userTargetId){

        UserRelationDto dto = null;
        /**
         * 获取唯一关系Key
         */
        String key = getCacheKey(userSourceId,userTargetId);
        try{
            /**
             * 查询redis
             */
            dto = redisTemplate.opsForValue().get(key);
            if(null != dto){
                return dto;
            }
            /**
             * 查询mongo
             */

            //查询条件
            Query query = new Query();
            query.addCriteria(Criteria.where("userSourceId").is(userSourceId));
            query.addCriteria(Criteria.where("userTargetId").is(userTargetId));
            //查询
            List<UserRelationDto> array = mongoTemplate.find(query,UserRelationDto.class,TABLE_NAME);
            if(null!=array&&array.size()>0){
                return array.get(0);
            }
            //查询数据库
            UserRelationDto _dto = new UserRelationDto();

            userRelationDao.getList(_dto);


        }catch (Exception e){
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String getCacheKey(String userSourceId,String userTargetId){
        return userSourceId+":"+userTargetId;
    }

}
