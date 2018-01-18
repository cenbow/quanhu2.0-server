package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.dao.UserRelationCacheDao;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserRelationApi;
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

    @Autowired
    private UserRelationCacheDao userRelationCacheDao;

    @Resource
    private RedisTemplate<String,UserRelationDto> redisTemplate;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public boolean setRelation(UserRelationDto dto) {
        try{
            /**
             * 用户单方操作时，不校验对方关系，
             * 更新缓存，及时响应结果信息
             */
            UserRelationDto sourceDto = userRelationCacheDao.getUserRelation("","");
            UserRelationDto targetDto = userRelationCacheDao.getUserRelation("","");
            if(dto == null){

            }
            /**
             * 判断操作条件是否成立
             * 关注，拉黑
             */

        }catch (Exception e){

        }
        return false;
    }

    public boolean checkRelation(UserRelationDto sourceDto, UserRelationDto targetDto, UserRelationApi.TYPE type){

        /**
         * 主要判断逻辑，判断对方是否拉黑
         */
        if(type == UserRelationApi.TYPE.FOLLOW){                //关注
            /**
             * 设置 target 的 黑名单    否
             * 设置 target 的 好友关系  判断target粉丝状态 是：是，否：否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   判断target粉丝状态 是：是，否：否
             * 设置 source 的 粉丝关系   是
             */

        }else if(type == UserRelationApi.TYPE.CANCEL_FOLLOW){   //取消关注
            /**
             * 设置 target 的 黑名单    否
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  保持不变
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

        }else if(type == UserRelationApi.TYPE.BLACK){           //拉黑
            /**
             * 设置 target 的 黑名单    是
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  否
             *
             * 设置 source 的 黑名单    是
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

        }else if(type == UserRelationApi.TYPE.CANCEL_BLACK){    //取消拉黑

            /**
             * 设置 target 的 黑名单    否
             * 设置 target 的 好友关系  否
             * 设置 target 的 粉丝关系  否
             *
             * 设置 source 的 黑名单    否
             * 设置 source 的 好友关系   否
             * 设置 source 的 粉丝关系   否
             */

        }else{
            return false;
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
