package com.yryz.quanhu.behavior.comment.dao.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.yryz.common.context.Context;
import com.yryz.common.utils.StringUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/12
 * @description
 */
@Component
public class CommentCache {

	@Autowired
	private RedisTemplateBuilder redisTemplateBuilder;

	/**
	 * 评论保存
	 * 
	 * @param comment
	 */
	public void saveComment(Comment comment) {		
		final String key = CommentApi.getCommentKey(comment.getKid());
		RedisTemplate<String, Comment> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Comment.class);
		redisTemplate.opsForValue().set(key, comment);
		redisTemplate.expire(key, getExpireDay(), TimeUnit.DAYS);
	}

	/**
	 * 评论保存
	 * 
	 * @param comment
	 */
	public void saveComment(List<Comment> comments) {
		if (CollectionUtils.isEmpty(comments)) {
			return;
		}
		for (int i = 0; i < comments.size(); i++) {
			saveComment(comments.get(i));
		}
	}

	/**
	 * 删除评论
	 * 
	 * @param commentId
	 */
	public void deleteComment(Long commentId) {
		final String key = CommentApi.getCommentKey(commentId);
		RedisTemplate<String, Comment> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Comment.class);
		redisTemplate.delete(key);
	}

	/**
	 * 删除评论
	 * 
	 * @param commentId
	 */
	public void deleteComment(List<Long> commentIds) {
		if (CollectionUtils.isEmpty(commentIds)) {
			return;
		}
		for (int i = 0; i < commentIds.size(); i++) {
			deleteComment(commentIds.get(i));
		}
	}

	/**
	 * 评论查询
	 * 
	 * @param commentId
	 * @return
	 */
	public Comment getComment(Long commentId) {
		final String key = CommentApi.getCommentKey(commentId);
		RedisTemplate<String, Comment> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Comment.class);
		return redisTemplate.opsForValue().get(key);
	}

	/**
	 * 评论查询
	 * 
	 * @param commentId
	 * @return
	 */
	public List<Comment> getComment(Set<Long> commentIds) {
		if (CollectionUtils.isEmpty(commentIds)) {
			return null;
		}
		int length = commentIds.size();
		List<Comment> comments = new ArrayList<>(length);
		for (Iterator<Long> iterator = commentIds.iterator(); iterator.hasNext();) {
			Comment comment = getComment(iterator.next());
			if (comment != null) {
				comments.add(comment);
			}
		}
		return comments;
	}
	
	/**
	 * 查询评论回复总数
	 * @param commentId
	 * @return
	 */
	public Long getCommentReplyCount(Long commentId){
		String key = CommentApi.getCommentReplyListKey(commentId);
		RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
		return redisTemplate.opsForZSet().size(key);
	}
	
	/**
	 * 评论列表
	 * @param moduleEnum
	 * @param resourceId
	 * @param topCommentId
	 * @param start
	 * @param limit
	 * @return
	 */
	public List<Comment> listComment(Long resourceId,Long topCommentId, int start, int limit){
		Set<Long> commentIds = rangeCommentList(resourceId, topCommentId, start, limit);
		if(CollectionUtils.isEmpty(commentIds)){
			return null;
		}
		return getComment(commentIds);		
	}
	
	/**
	 * 添加评论列表
	 */
	public void addCommentList(Comment comment) {
		RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
		String key = CommentApi.getCommentListKey(comment.getResourceId());
		
		//回复列表
		if (comment.getTopId() != 0l) {
			key = CommentApi.getCommentReplyListKey(comment.getTopId());
		}
		Long kid = comment.getKid();
		long createTime = comment.getCreateDate().getTime();
		redisTemplate.opsForZSet().add(key, kid, createTime);
		redisTemplate.expire(key, getExpireDay(), TimeUnit.DAYS);
	}

	/**
	 * 添加评论列表
	 */
	public void addCommentList(List<Comment> comments) {
		if (CollectionUtils.isEmpty(comments)) {
			return;
		}
		for (int i = 0; i < comments.size(); i++) {
			addCommentList(comments.get(i));
		}
	}

	/**
	 * 获取评论列表
	 */
	private Set<Long> rangeCommentList(Long resourceId, Long topCommentId, int start, int limit) {
		String key = null;
		
		//查询评论列表
		if (resourceId != null && resourceId != 0l) {
			key = CommentApi.getCommentListKey(resourceId);
		}
		
		//查询回复列表
		if (topCommentId != null && topCommentId != 0l) {
			key = CommentApi.getCommentReplyListKey(topCommentId);
		}
		
		if(StringUtils.isEmpty(key)){
			return null;
		}
		RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);

		Set<Long> sendList = null;
		if (start <= 0) {
			sendList = redisTemplate.opsForZSet().reverseRange(key, 0, limit - 1);
		} else {
			sendList = redisTemplate.opsForZSet().reverseRange(key, start, start + limit - 1);
		}
		return sendList;
	}
	
	/**
	 * 删除整个回复列表
	 */
	public void delCommentReplyList(Long topCommentId) {
		String key = CommentApi.getCommentReplyListKey(topCommentId);
		RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
		redisTemplate.delete(key);
	}
	
	/**
	 * 删除评论列表
	 */
	public void delCommentList(Comment comment) {
		String key = CommentApi.getCommentListKey(comment.getResourceId());
		if (comment.getTopId() != 0l) {
			key = CommentApi.getCommentReplyListKey(comment.getTopId());
		}
		RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);

		Long kid = comment.getKid();
		redisTemplate.opsForZSet().remove(key, kid);
	}

	/**
	 * 删除评论列表
	 */
	public void delCommentList(List<Comment> comments) {
		if (CollectionUtils.isEmpty(comments)) {
			return;
		}
		for (int i = 0; i < comments.size(); i++) {
			delCommentList(comments.get(i));
		}
	}
	
	
	
	/**
	 * 获取评论过期时间
	 * 
	 * @return
	 */
	private Integer getExpireDay() {
		return NumberUtils.toInt(Context.getProperty("comment.expireDays"), 180);
	}
}
