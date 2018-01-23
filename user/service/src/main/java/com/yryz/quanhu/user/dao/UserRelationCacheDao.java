package com.yryz.quanhu.user.dao;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserRelationEntity;
import com.yryz.quanhu.user.service.UserRelationApi;
import org.apache.commons.collections.CollectionUtils;
import org.bson.BSONObject;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
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

    /**
     * 用户关系redis过期时间
     */
    @Value("${user.relation.expireDays}")
    private int expireDays;

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
             * 查询mongo
             */

            //查询条件
            Query query = new Query();
            query.addCriteria(Criteria.where("sourceUserId").is(sourceUserId));
            query.addCriteria(Criteria.where("targetUserId").is(targetUserId));
            query.fields().exclude("_id");

            //查询
            List<UserRelationDto> array = this.convertArray(mongoTemplate.find(query,LinkedHashMap.class,TABLE_NAME));
            if(null!=array&&array.size()>0){
                BeanUtils.copyProperties(dto,array.get(0));
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
        return "relation/"+sourceUserId+"/"+targetUserId;
    }

    public static String getCacheTotalKey(String userId){
        return "total/"+userId;
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
            //转换消息
            String msg = MAPPER.writeValueAsString(userDto);
            //发送消息
//            rabbitTemplate.convertAndSend(msg);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        userRelationDao.update(userDto);
    }

    /**
     * 异步MQ处理
     * @param data
     */
    public void handleMessage(String data){
        System.out.println("hello Fanout Message:" + data);
        try{
            //反序列化对象
            UserRelationDto relationDto = MAPPER.readValue(data,UserRelationDto.class);
            //数据库存储
            userRelationDao.update(relationDto);
            //同步调用第三方建立关系

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }



    public PageList<UserRelationDto> selectByPage(int currentPage,int pageSize,String sourceUserId,UserRelationConstant.STATUS status) {
        Query query = this.buildQuery(sourceUserId,status);
        query.skip(currentPage*pageSize);
        query.limit(pageSize);

        query.fields().exclude("_id");  //屏蔽mongo自生产的ObjectId,防止反序列化异常


        /**
         * 查询数据
         */
        List<LinkedHashMap> resultArray = mongoTemplate.find(query,LinkedHashMap.class,TABLE_NAME);

        /**
         * 查询总数
         */
        long count = mongoTemplate.count(query,TABLE_NAME);

        PageList pageList = new PageList();
        pageList.setEntities(this.convertArray(resultArray));
        pageList.setPageSize(resultArray.size());
        pageList.setCurrentPage(currentPage);
        pageList.setCount(count);

        return pageList;

    }

    public List<UserRelationDto> selectBy(String sourceUserId, String[] targetUserIds) {
        Query query = new Query();

        query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
        query.addCriteria(Criteria.where("target_user_id").in(targetUserIds));

        return this.convertArray(mongoTemplate.find(query,LinkedHashMap.class,TABLE_NAME));
    }

    public List<UserRelationDto> selectBy(String[] targetUserIds,String sourceUserId) {
        Query query = new Query();

        query.addCriteria(Criteria.where("source_user_id").is(targetUserIds));
        query.addCriteria(Criteria.where("target_user_id").is(sourceUserId));

        return this.convertArray(mongoTemplate.find(query,LinkedHashMap.class,TABLE_NAME));
    }

    public List<UserRelationDto> selectBy(String sourceUserId, UserRelationConstant.STATUS status){
        Query query = this.buildQuery(sourceUserId,status);
        return this.convertArray(mongoTemplate.find(query,LinkedHashMap.class,TABLE_NAME));
    }


    public long totalByFollow(String userId){
        //关注数
        Query query = this.buildQuery(userId, UserRelationConstant.STATUS.FOLLOW);
        return mongoTemplate.count(query,TABLE_NAME);
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
    public UserRelationCountDto totalBy(String userId) {

        UserRelationCountDto dto = new UserRelationCountDto();

        /**
         * 查询mongo进行统计
         */

        //关注数
        Query query = this.buildQuery(userId, UserRelationConstant.STATUS.FOLLOW);
        long count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFollowCount(count);

        //黑名单数
        query = this.buildQuery(userId, UserRelationConstant.STATUS.TO_BLACK);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setToBlackCount(count);

        //好友数
        query = this.buildQuery(userId, UserRelationConstant.STATUS.FRIEND);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFriendCount(count);

        //粉丝数
        query = this.buildQuery(userId, UserRelationConstant.STATUS.FANS);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFansCount(count);

        //被拉黑数
        query = this.buildQuery(userId, UserRelationConstant.STATUS.FROM_BLACK);
        count = mongoTemplate.count(query,TABLE_NAME);
        dto.setFromBlackCount(count);

        return dto;
    }


    public String [] buildTargetUserIds(List<UserRelationDto> queryArray,UserRelationConstant.STATUS status){
        String [] targetUserIds = new String[queryArray.size()];

        if(UserRelationConstant.STATUS.FANS == status||UserRelationConstant.STATUS.FROM_BLACK == status) {
            //粉丝，或者被拉黑，则以用户维度查询
            for (int i = 0 ; i < queryArray.size() ; i++){
                targetUserIds[i]=String.valueOf(queryArray.get(i).getSourceUserId());
            }
        }else{
            //其他以目标维度查询
            for (int i = 0 ; i < queryArray.size() ; i++){
                targetUserIds[i]=String.valueOf(queryArray.get(i).getTargetUserId());
            }
        }

        return targetUserIds;
    }

    private Query buildQuery(String sourceUserId,UserRelationConstant.STATUS status){

        Query query = new Query();
        if(UserRelationConstant.STATUS.FANS == status){                      //粉丝

            query.addCriteria(Criteria.where("target_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("follow_status").is(UserRelationConstant.YES));

        }else if(UserRelationConstant.STATUS.FOLLOW == status){              //关注

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("follow_status").is(UserRelationConstant.YES));

        }else if(UserRelationConstant.STATUS.FRIEND == status){              //好友

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("friend_status").is(UserRelationConstant.YES));

        }else if(UserRelationConstant.STATUS.TO_BLACK == status){            //拉黑

            query.addCriteria(Criteria.where("source_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("black_status").is(UserRelationConstant.YES));

        }else if(UserRelationConstant.STATUS.FROM_BLACK == status){          //被拉黑

            query.addCriteria(Criteria.where("target_user_id").is(sourceUserId));
            query.addCriteria(Criteria.where("black_status").is(UserRelationConstant.YES));
        }

        return query;
    }

    private List<UserRelationDto> convertArray(List<LinkedHashMap> listMap){
        if(CollectionUtils.isEmpty(listMap)){
            return null;
        }
        List<UserRelationDto> newArray = new ArrayList<>();
        for(int i = 0 ; i < listMap.size(); i++){
            newArray.add(this.convertBean(listMap.get(i)));
        }
        return newArray;
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private UserRelationDto convertBean(LinkedHashMap map){
        if(map==null){
            return null;
        }
        UserRelationDto dto = new UserRelationDto();
        try {
            dto.setId((Long) map.get("id"));
            dto.setKid((Long) map.get("kid"));
//            dto.setCreateDate(simpleDateFormat.parse(String.valueOf(map.get("create_date"))));
            dto.setCreateUserId(Long.parseLong(String.valueOf(map.get("create_user_id"))));
//            dto.setLastUpdateDate(simpleDateFormat.parse(String.valueOf(map.get("last_update_date"))));
            dto.setLastUpdateUserId(Long.parseLong(String.valueOf(map.get("last_update_user_id"))));
            dto.setFollowStatus(Integer.parseInt(String.valueOf(map.get("follow_status"))));
            dto.setFriendStatus(Integer.parseInt(String.valueOf(map.get("friend_status"))));
            dto.setBlackStatus(Integer.parseInt(String.valueOf(map.get("black_status"))));


            dto.setSourceUserId(String.valueOf(map.get("source_user_id")));
            dto.setTargetUserId(String.valueOf(map.get("target_user_id")));
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
        return dto;
    }

}
