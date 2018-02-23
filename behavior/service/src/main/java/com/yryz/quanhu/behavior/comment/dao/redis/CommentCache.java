package com.yryz.quanhu.behavior.comment.dao.redis;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/12
 * @description
 */
@Component
public class CommentCache {

    /**
     * 评论列表
     */
    private final static String COMMENT_LIST_KEY = "comment:list:";

    /**
     * 评论信息
     */
    private final static String COMMENT_INFO_KEY = "comment:info:";

    /**
     * 评论信息过期时间
     */
//    private final static int EXPIRE_DAY = 30;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    private static String commentListCacheKey(Comment comment) {
        StringBuffer cacheKey = new StringBuffer(COMMENT_LIST_KEY);
        return cacheKey.append(comment.getModuleEnum()).append(String.valueOf(comment.getResourceId())).toString();
    }

    private static String commentInfoCacheKey(Comment comment) {
        StringBuffer cacheKey = new StringBuffer(COMMENT_LIST_KEY);
        return cacheKey.append(comment.getKid()).toString();
    }

    /**
     * 添加评论
     */
    public void addCommentList(Comment comment) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String cacheKey = commentListCacheKey(comment);
        Long kid = comment.getKid();
        long createTime = comment.getCreateDate().getTime();
        redisTemplate.opsForZSet().add(cacheKey, kid, createTime);
    }


    /**
     * 获取评论列表
     */
    public Set<Long> rangeCommentList(Comment comment, int start, int limit) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        final String cacheKey = commentListCacheKey(comment);
        Set<Long> sendList = null;
        if (start <= 0) {
            sendList = redisTemplate.opsForZSet().reverseRange(cacheKey, 0, limit - 1);
        } else {
            sendList = redisTemplate.opsForZSet().reverseRange(cacheKey, start, start + limit - 1);
        }
        return sendList;
    }

    /**
     * 删除评论
     */
    public void delCommentList(Comment comment) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
        String cacheKey = commentListCacheKey(comment);
        Long kid = comment.getKid();
        redisTemplate.opsForZSet().remove(cacheKey, kid);
    }


    /**
     * 增加评论信息
     */
    public void addCommentInfo(Comment comment) {
        String cacheKey = commentInfoCacheKey(comment);
        RedisTemplate<String, Comment> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Comment.class);
        redisTemplate.opsForValue().set(cacheKey, comment);
    }



}
