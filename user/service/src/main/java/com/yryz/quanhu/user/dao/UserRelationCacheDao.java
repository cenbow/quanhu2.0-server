package com.yryz.quanhu.user.dao;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.support.id.api.IdAPI;
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

    private static final String TABLE_NAME = "qh_user_relation";
    /**
     * 用户关系redis过期时间
     */
    @Value("${user.relation.expireDays}")
    private int expireDays;

    @Value("${user.relation.redis.mapping.pefix}")
    public String relation_prefix;

    @Value("${user.relation.redis.count.pefix}")
    public String relation_count_prefix;

    @Value("${user.relation.mq.queue}")
    private String mqQueue;

    @Value("${user.relation.mq.direct.exchange}")
    private String mqDirectExchange;




    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRelationDao userRelationDao;

    @Reference(check = false)
    private ImAPI imAPI;

    @Reference(check = false)
    private IdAPI idAPI;

    @Reference(check = false)
    private EventAPI eventAPI;

    @Reference(check = false)
    private DymaicService dymaicService;

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
            dto = userRelationDao.selectUser(UserRelationDto.class,sourceUserId,targetUserId);
            if(null != dto){
                dto.setNewRecord(false);
            }
            return dto;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public String getCacheKey(String sourceUserId,String targetUserId){
        return relation_prefix+"."+sourceUserId+"."+targetUserId;
    }

    public String getCacheTotalCountKey(String userId){
        return relation_count_prefix+"."+userId;
    }

    public UserRelationCountDto getCacheTotalCount(String userId){
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
        logger.info("refreshCacheCount={}/{} start",key,JSON.toJSON(dto));
        redisTemplate.opsForValue().set(key,dto,expireDays,TimeUnit.DAYS);
        logger.info("refreshCacheCount={} finish",sourceUserId);

    }

    public void removeCacheRelation(UserRelationDto dto){

        String key = getCacheKey(dto.getSourceUserId(),dto.getTargetUserId());
        RedisTemplate<String,UserRelationEntity> redisTemplate =
                redisTemplateBuilder.buildRedisTemplate(UserRelationEntity.class);

        redisTemplate.delete(key);

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

        logger.info("refreshCacheRelation={}/{} start",sourceKey,JSON.toJSON(dto));
        redisTemplate.opsForValue().set(sourceKey,entity,expireDays, TimeUnit.DAYS);
        logger.info("refreshCacheRelation={}/{} finish",sourceKey);

    }

    /**
     * 发生到MQ异步处理
     * @param userDto
     */
    public void sendMQ(UserRelationDto userDto){

        try {
            logger.info("sendMQ={} start",JSON.toJSON(userDto));
            //转换消息
            String msg = MAPPER.writeValueAsString(userDto);
            logger.info("sendMQ.convert={}",msg);

            //发送消息
            rabbitTemplate.setExchange(mqDirectExchange);
            rabbitTemplate.setRoutingKey(mqQueue);

            rabbitTemplate.convertAndSend(msg);

            //刷新缓存
            this.refreshCacheRelation(userDto);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }finally {
            logger.info("sendMQ={} finish",JSON.toJSON(userDto));
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
        UserRelationDto relationDto = null;
        try{
            //反序列化对象
            relationDto = MAPPER.readValue(data,UserRelationDto.class);
            logger.info("handleMessage.convert={}",JSON.toJSON(relationDto));

            //数据库存储
            if(relationDto.getKid() == null){
                relationDto.setKid(idAPI.getKid(TABLE_NAME).getData());
                relationDto.setCreateUserId(Long.parseLong(relationDto.getSourceUserId()));
                relationDto.setLastUpdateUserId(Long.parseLong(relationDto.getSourceUserId()));
                relationDto.setVersion(0);
                relationDto.setDelFlag(10);
                logger.info("insert database start");
                //userRelationDao.insert(relationDto);
                logger.info("insert database finish");
            }else{
                logger.info("update database start");
                userRelationDao.update(relationDto);
                logger.info("update database finish");
            }

            /**
             * 第三方同步 (串行执行）
             */

            //同步调用第三方建立关系
            this.syncImRelation(relationDto);

            //删除动态
            this.syncDynamicTimeLine(relationDto);

            //统计数据
            UserRelationCountDto dto = this.selectTotalCount(relationDto.getSourceUserId());

            //添加积分成长值
            this.syncScoreEvent(relationDto,dto);

            //刷新至缓存
            this.refreshCacheCount(relationDto.getSourceUserId(),dto);

        }catch (Exception e){
            logger.error(e.getMessage(),e);

            /**
             * 删除缓存
             */
            logger.warn("removeCacheRelation start");
            this.removeCacheRelation(relationDto);
            logger.warn("removeCacheRelation finish");

            //继续抛出异常，进行数据库回滚
            //throw new RuntimeException(e);
        }finally {
            logger.info("handleMessage={} finish",data);
        }
    }


    /**
     * 动态时间线
     * @param dto
     */
    public void syncDynamicTimeLine(UserRelationDto dto){

        int status = dto.getRelationStatus();
        /**
         * 非关注，和好友时，删除自己动态的内容
         */
        if(status != UserRelationConstant.STATUS.FOLLOW.getCode()
                && status != UserRelationConstant.STATUS.FRIEND.getCode()){

            logger.info("syncDynamicTimeLine {}/{} start",dto.getSourceUserId(),dto.getTargetUserId());

            Response<Boolean> rpc = dymaicService.shuffleTimeLine(
                    Long.parseLong(dto.getSourceUserId()),Long.parseLong(dto.getTargetUserId()));
            logger.info("syncDynamicTimeLine rpc={}",JSON.toJSON(rpc));
            logger.info("syncDynamicTimeLine {}/{} finish",dto.getSourceUserId(),dto.getTargetUserId());
        }
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    /**
     * 积分事件
     * @param dto
     * @param countDto
     */
    public void syncScoreEvent(UserRelationDto dto,UserRelationCountDto countDto){

        EventInfo info = new EventInfo();

        info.setCreateTime(simpleDateFormat.format(new Date()));
        info.setUserId(dto.getSourceUserId());
        if(countDto.getFollowCount() == 30){          //达到30人，增加50积分
            info.setEventCode("38");                      //事件编号
        }else if(countDto.getFollowCount() == 20){    //达到20人，增加30积分
            info.setEventCode("31");                      //事件编号
        }else{
            return;
        }

        logger.info("syncScoreEvent：{} start",JSON.toJSON(info));
        eventAPI.commit(info);
        logger.info("syncScoreEvent：{} finish",JSON.toJSON(info));

    }

    public UserRelationCountDto selectTotalCount(String targetUserId){

        List<Map<String,Object>> listMaps = userRelationDao.selectTotalCount(targetUserId);
        /**
         * 创建对象
         */
        UserRelationCountDto dto = new UserRelationCountDto();
        for(int i = 0 ; i < listMaps.size() ; i++){

            Map<String,Object> map = listMaps.get(i);
            int status = (int) map.get("relationStatus");
            long count = (long) map.get("totalCount");
            if(status==UserRelationConstant.STATUS.FANS.getCode()){         //粉丝
                dto.setFansCount(count);
            }
            if(status==UserRelationConstant.STATUS.FOLLOW.getCode()){       //关注
                dto.setFollowCount(count);
            }
            if(status==UserRelationConstant.STATUS.TO_BLACK.getCode()){     //拉黑
                dto.setToBlackCount(count);
            }
            if(status==UserRelationConstant.STATUS.FROM_BLACK.getCode()){   //被拉黑
                dto.setFromBlackCount(count);
            }
            if(status==UserRelationConstant.STATUS.BOTH_BLACK.getCode()){    //互相拉黑
                dto.setBothBlackCount(count);
            }
            if(status==UserRelationConstant.STATUS.FRIEND.getCode()){        //好友
                dto.setFriendCount(count);
            }
        }
        /**
         * 好友数据，归并到关注，和粉丝中，互相拉黑数据，归并到拉黑，被拉黑中
         */
        dto.setTargetUserId(targetUserId);
        dto.setFansCount(dto.getFansCount()+dto.getFriendCount());
        dto.setFollowCount(dto.getFollowCount()+dto.getFriendCount());
        dto.setToBlackCount(dto.getToBlackCount()+dto.getBothBlackCount());
        dto.setFromBlackCount(dto.getFromBlackCount()+dto.getBothBlackCount());
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
        int status = dto.getRelationStatus();

        //判断拉黑关系
        if(status == UserRelationConstant.STATUS.BOTH_BLACK.getCode()
                ||status == UserRelationConstant.STATUS.TO_BLACK.getCode()
                ||status == UserRelationConstant.STATUS.FROM_BLACK.getCode()){

            im.setRelationType("1");
            im.setRelationValue("1");
            try{
                logger.info("syncImRelation.setSpecialRelation ={} start",JSON.toJSON(im));
                imAPI.setSpecialRelation(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"添加黑名单失败",e);
            }finally {
                logger.info("syncImRelation.setSpecialRelation ={} finish",JSON.toJSON(im));
            }
        }else{
            im.setRelationType("1");
            im.setRelationValue("0");
            try{
                logger.info("syncImRelation.setSpecialRelation ={} start",JSON.toJSON(im));
                imAPI.setSpecialRelation(im);
            }catch (Exception e){
                logger.warn("取消黑名单失败",e);
            }finally {
                logger.info("syncImRelation.setSpecialRelation ={} finish",JSON.toJSON(im));
            }
        }

        //判断好友关系
        if(status == UserRelationConstant.STATUS.FRIEND.getCode()){
            try{
                logger.info("syncImRelation.addFriend ={} start",JSON.toJSON(im));
                imAPI.addFriend(im);
            }catch (Exception e){
                throw new QuanhuException("","[IM]"+e.getMessage(),"添加好友关系失败",e);
            }finally {
                logger.info("syncImRelation.addFriend ={} finish",JSON.toJSON(im));
            }
        }else{
            try{
                logger.info("syncImRelation.deleteFriend ={} start",JSON.toJSON(im));
                imAPI.deleteFriend(im);
            }catch (Exception e){
                logger.warn("删除好友关系失败",e);
            }finally {
                logger.info("syncImRelation.deleteFriend ={} finish",JSON.toJSON(im));
            }
        }
    }
}
