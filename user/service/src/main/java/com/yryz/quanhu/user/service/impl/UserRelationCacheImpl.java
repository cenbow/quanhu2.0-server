package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/18 15:27
 * Created by huangxy
 */
@Service
@Transactional
public class UserRelationCacheImpl implements UserRelationService{



    @Resource
    private RedisTemplate<String,UserRelationDto> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean setRelation(UserRelationDto dto) {


        String key = getCacheKey("","");
        try{
            /**
             * 查询redis是否存在
             * 查询mongo记录是否存在
             * 判断设置条件是否满足
             */
            dto = redisTemplate.opsForValue().get(key);
            if(dto == null){

                //查询条件
                Query query = new Query();
                query.addCriteria(Criteria.where("userSource").is(""));
                query.addCriteria(Criteria.where("userTarget").is(""));
                //查询
                List<UserRelationDto> array = mongoTemplate.find(query,UserRelationDto.class,TABLE_NAME);
                if(null!=array&&array.size()>0){
                    dto = array.get(0);
                }
            }
            /**
             * 添加到mq进行消峰
             */
            rabbitTemplate
        }catch (Exception e){

        }
        return false;
    }

    @Override
    public boolean checkRelation(UserRelationDto dto) {
        /**
         * 验证关系要保证准实时性(以当前数据库数据为准)
         */

        try{
            /**
             * 1,根据用户查询在redis中最后更新时间
             * 2,如果判断时间已过期,则查询数据库
             */


        }catch (Exception e){

        }

        return false;
    }

    @Override
    public boolean setRemarkName(UserRelationDto dto) {
        return false;
    }

    @Override
    public boolean recoverRemarkName(UserRelationDto dto) {
        return false;
    }

    @Override
    public List<UserRelationDto> selectBy(UserRelationDto dto) {
        return null;
    }

    @Override
    public PageList<UserRelationDto> selectByPage(UserRelationDto dto) {
        return null;
    }

    @Override
    public List<UserRelationDto> selectBy(String userSourceKid, String[] userTargetKids) {
        return null;
    }

    @Override
    public UserRelationCountDto totalBy(String userSourceKid) {
        return null;
    }

    public static String getCacheKey(String userSourceId,String userTargetId){
        return userSourceId+":"+userTargetId;
    }


}
