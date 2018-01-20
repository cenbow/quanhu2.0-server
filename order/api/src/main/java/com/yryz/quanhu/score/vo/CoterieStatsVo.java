/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月27日
 * Id: CoterieStatsVo.java, 2017年9月27日 下午3:25:04 Administrator
 */
package com.yryz.quanhu.score.vo;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年9月27日 下午3:25:04
 * @Description 私圈维度数据统计
 */
@SuppressWarnings("serial")
public class CoterieStatsVo implements Serializable {
	/**
	 * 私圈id
	 */
	private String coterieId;
	/**
	 * 圈子id
	 */
	private String circleId;
	/**
	 * 私圈人数
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
	public CoterieStatsVo() {
		super();
	}
	/**
	 * @param coterieId
	 * @exception 
	 */
	public CoterieStatsVo(String coterieId) {
		super();
		this.coterieId = coterieId;
	}
	public String getCoterieId() {
		return coterieId;
	}
	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}
	public String getCircleId() {
		return circleId;
	}
	public void setCircleId(String circleId) {
		this.circleId = circleId;
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
