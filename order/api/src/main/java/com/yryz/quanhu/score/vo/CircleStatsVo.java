/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月27日
 * Id: CircleStatsVo.java, 2017年9月27日 下午3:31:33 Administrator
 */
package com.yryz.quanhu.score.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月27日 下午3:31:33
 * @Description 圈子维度数据统计
 */
@SuppressWarnings("serial")
public class CircleStatsVo implements Serializable {
	/**
	 * 圈子id
	 */
	private String circleId;
	/**
	 * 私圈数
	 */
	private int coterieNum = 0;
	/**
	 * 圈子总人数
	 */
	private int memberNum = 0;
	/**
	 * 资源总数
	 */
	private int resourceTotal = 0;
	/**
	 * 付费总计
	 */
	private long accountTotal = 0;
	
	/**
	 * 评论总数
	 */
	private int comment = 0;
	/**
	 * 点赞数
	 */
	private int good = 0;
	/**
	 * 分享数
	 */
	private int shared = 0;
	/**
	 * 收藏数
	 */
	private int collections = 0;
	/**
	 * 
	 * @exception 
	 */
	public CircleStatsVo() {
		super();
	}
	/**
	 * @param circleId
	 * @exception 
	 */
	public CircleStatsVo(String circleId) {
		super();
		this.circleId = circleId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}
	public int getCoterieNum() {
		return coterieNum;
	}
	public void setCoterieNum(int coterieNum) {
		this.coterieNum = coterieNum;
	}
	public int getMemberNum() {
		return memberNum;
	}
	public void setMemberNum(int memberNum) {
		this.memberNum = memberNum;
	}
	public int getResourceTotal() {
		return resourceTotal;
	}
	public void setResourceTotal(int resourceTotal) {
		this.resourceTotal = resourceTotal;
	}
	public long getAccountTotal() {
		return accountTotal;
	}
	public void setAccountTotal(long accountTotal) {
		this.accountTotal = accountTotal;
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
	public int getShared() {
		return shared;
	}
	public void setShared(int shared) {
		this.shared = shared;
	}
	public int getCollections() {
		return collections;
	}
	public void setCollections(int collections) {
		this.collections = collections;
	}
}
