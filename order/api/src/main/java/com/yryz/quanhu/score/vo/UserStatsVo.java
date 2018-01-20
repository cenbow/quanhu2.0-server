/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月27日
 * Id: UserStatsVo.java, 2017年9月27日 下午3:10:56 Administrator
 */
package com.yryz.quanhu.score.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月27日 下午3:10:56
 * @Description 用户维度统计对象
 */
@SuppressWarnings("serial")
public class UserStatsVo implements Serializable {
	/**
	 * 用户id	
	 */
	private String custId;
	/**
	 * 付费(消费金额)
	 */
	private long account = 0;
	/**
	 * 收入(账户积分)
	 */
	private long integral = 0;
	/**
	 * 事件积分
	 */
	private long score = 0 ;
	/**
	 * 等级
	 */
	private String level = "1";
	/**
	 * 创建私圈数
	 */
	private int createCoterie = 0;
	/**
	 * 加入私圈数
	 */
	private int joinCoterie = 0;
	/**
	 * 创建悬赏数
	 */
	private int createOffer = 0;
	/**
	 * 回答悬赏数
	 */
	private int replyOffer = 0;
	/**
	 * 发布文章数
	 */
	private int createInfo = 0;
	/**
	 * 发布帖子数
	 */
	private int createPosts = 0;
	/**
	 * 提问数
	 */
	private int createQuestion = 0;
	/**
	 * 发布问答数
	 */
	private int createFAQ = 0;
	/**
	 * 分享数
	 */
	private int shared = 0;
	/**
	 * 评论数
	 */
	private int comment = 0;
	/**
	 * 点赞数
	 */
	private int good = 0;
	/**
	 * 收藏数
	 */
	private int collections = 0;
	
	
	/**
	 * 
	 * @exception 
	 */
	public UserStatsVo() {
		super();
	}
	/**
	 * @param custId
	 * @exception 
	 */
	public UserStatsVo(String custId) {
		super();
		this.custId = custId;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public long getAccount() {
		return account;
	}
	public void setAccount(long account) {
		this.account = account;
	}
	public long getIntegral() {
		return integral;
	}
	public void setIntegral(long integral) {
		this.integral = integral;
	}
	public long getScore() {
		return score;
	}
	public void setScore(long score) {
		this.score = score;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public int getCreateCoterie() {
		return createCoterie;
	}
	public void setCreateCoterie(int createCoterie) {
		this.createCoterie = createCoterie;
	}
	public int getJoinCoterie() {
		return joinCoterie;
	}
	public void setJoinCoterie(int joinCoterie) {
		this.joinCoterie = joinCoterie;
	}
	public int getCreateOffer() {
		return createOffer;
	}
	public void setCreateOffer(int createOffer) {
		this.createOffer = createOffer;
	}
	public int getReplyOffer() {
		return replyOffer;
	}
	public void setReplyOffer(int replyOffer) {
		this.replyOffer = replyOffer;
	}
	public int getCreateInfo() {
		return createInfo;
	}
	public void setCreateInfo(int createInfo) {
		this.createInfo = createInfo;
	}
	public int getCreatePosts() {
		return createPosts;
	}
	public void setCreatePosts(int createPosts) {
		this.createPosts = createPosts;
	}
	public int getCreateQuestion() {
		return createQuestion;
	}
	public void setCreateQuestion(int createQuestion) {
		this.createQuestion = createQuestion;
	}
	public int getCreateFAQ() {
		return createFAQ;
	}
	public void setCreateFAQ(int createFAQ) {
		this.createFAQ = createFAQ;
	}
	public int getShared() {
		return shared;
	}
	public void setShared(int shared) {
		this.shared = shared;
	}
	public int getComment() {
		return comment;
	}
	public void setComment(int comment) {
		this.comment = comment;
	}
	public int getGood() {
		return good;
	}
	public void setGood(int good) {
		this.good = good;
	}
	public int getCollections() {
		return collections;
	}
	public void setCollections(int collections) {
		this.collections = collections;
	}
}
