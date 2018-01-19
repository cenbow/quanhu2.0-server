/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月6日
 * Id: VerifyCodeConfigVO.java, 2017年12月6日 上午9:39:22 Administrator
 */
package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;

/**
 * 验证码配置
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月6日 上午9:39:22
 */
@SuppressWarnings("serial")
public class VerifyCodeConfigVO implements Serializable {
	/**
	 * 普通验证码过期时间 /秒
	 */
	private Integer normalCodeExpireTime;
	
	/**
	 * 普通验证码获取间隔时间 /秒
	 */
	private Integer normalCodeDelayTime;
	/**
	 * 普通验证码每天获取总量限制
	 */
	private Integer normalCodeTotal;
	
	/**
	 * 图形验证码过期时间 /秒
	 */
	private Integer imgCodeExpireTime;
	
	/**
	 * 不需要使用图形验证码要发送普通验证码的次数
	 */
	private Integer imgCodeNumLimit;
	
	/**
	 * 验证码位数
	 */
	private Integer codeNum;
	
	public Integer getNormalCodeExpireTime() {
		return normalCodeExpireTime;
	}

	public void setNormalCodeExpireTime(Integer normalCodeExpireTime) {
		this.normalCodeExpireTime = normalCodeExpireTime;
	}

	public Integer getNormalCodeDelayTime() {
		return normalCodeDelayTime;
	}

	public void setNormalCodeDelayTime(Integer normalCodeDelayTime) {
		this.normalCodeDelayTime = normalCodeDelayTime;
	}

	public Integer getImgCodeExpireTime() {
		return imgCodeExpireTime;
	}

	public void setImgCodeExpireTime(Integer imgCodeExpireTime) {
		this.imgCodeExpireTime = imgCodeExpireTime;
	}

	public Integer getImgCodeNumLimit() {
		return imgCodeNumLimit;
	}

	public void setImgCodeNumLimit(Integer imgCodeNumLimit) {
		this.imgCodeNumLimit = imgCodeNumLimit;
	}

	public Integer getCodeNum() {
		return codeNum;
	}

	public void setCodeNum(Integer codeNum) {
		this.codeNum = codeNum;
	}

	public Integer getNormalCodeTotal() {
		return normalCodeTotal;
	}

	public void setNormalCodeTotal(Integer normalCodeTotal) {
		this.normalCodeTotal = normalCodeTotal;
	}

	public VerifyCodeConfigVO() {
		super();
	}

	/**
	 * @param normalCodeExpireTime
	 * @param normalCodeDelayTime
	 * @param imgCodeExpireTime
	 * @param imgCodeNumLimit
	 * @exception 
	 */
	public VerifyCodeConfigVO(Integer normalCodeExpireTime, Integer normalCodeDelayTime, Integer imgCodeExpireTime,
			Integer imgCodeNumLimit) {
		super();
		this.normalCodeExpireTime = normalCodeExpireTime;
		this.normalCodeDelayTime = normalCodeDelayTime;
		this.imgCodeExpireTime = imgCodeExpireTime;
		this.imgCodeNumLimit = imgCodeNumLimit;
	}

	/**
	 * @param normalCodeExpireTime
	 * @param normalCodeDelayTime
	 * @param normalCodeTotal
	 * @param imgCodeExpireTime
	 * @param imgCodeNumLimit
	 * @param codeNum
	 * @exception 
	 */
	public VerifyCodeConfigVO(Integer normalCodeExpireTime, Integer normalCodeDelayTime, Integer normalCodeTotal,
			Integer imgCodeExpireTime, Integer imgCodeNumLimit, Integer codeNum) {
		super();
		this.normalCodeExpireTime = normalCodeExpireTime;
		this.normalCodeDelayTime = normalCodeDelayTime;
		this.normalCodeTotal = normalCodeTotal;
		this.imgCodeExpireTime = imgCodeExpireTime;
		this.imgCodeNumLimit = imgCodeNumLimit;
		this.codeNum = codeNum;
	}

	/**
	 * @param normalCodeExpireTime
	 * @param normalCodeDelayTime
	 * @param imgCodeExpireTime
	 * @param imgCodeNumLimit
	 * @param codeNum
	 * @exception 
	 */
	public VerifyCodeConfigVO(Integer normalCodeExpireTime, Integer normalCodeDelayTime, Integer imgCodeExpireTime,
			Integer imgCodeNumLimit, Integer codeNum) {
		super();
		this.normalCodeExpireTime = normalCodeExpireTime;
		this.normalCodeDelayTime = normalCodeDelayTime;
		this.imgCodeExpireTime = imgCodeExpireTime;
		this.imgCodeNumLimit = imgCodeNumLimit;
		this.codeNum = codeNum;
	}

	@Override
	public String toString() {
		return "VerifyCodeConfigVO [normalCodeExpireTime=" + normalCodeExpireTime + ", normalCodeDelayTime="
				+ normalCodeDelayTime + ", normalCodeTotal=" + normalCodeTotal + ", imgCodeExpireTime="
				+ imgCodeExpireTime + ", imgCodeNumLimit=" + imgCodeNumLimit + ", codeNum=" + codeNum + "]";
	}
	
}
