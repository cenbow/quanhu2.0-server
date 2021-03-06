/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月6日
 * Id: VerifyCodeConfigVO.java, 2017年12月6日 上午9:39:22 Administrator
 */
package com.yryz.common.config;

import java.io.Serializable;

import org.springframework.context.annotation.Configuration;

import com.yryz.common.utils.JsonUtils;

/**
 * 验证码配置
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月6日 上午9:39:22
 */
@SuppressWarnings("serial")
@Configuration
public class VerifyCodeConfigVO implements Serializable {
	/**
	 * 普通验证码过期时间 /秒
	 */
	private Integer normalCodeExpireTime = 600;
	
	/**
	 * 每个手机号或者ip普通验证码获取间隔时间/ip/phone /秒
	 */
	private Integer normalCodeDelayTime = 60;
	/**
	 * 普通验证码每个ip每天获取总量限制
	 */
	private Integer normalIpCodeTotal = 1000;
	/**
	 *  普通验证码每个手机号每天获取总量限制
	 */
	private Integer normalCodeTotal =10;
	
	/**
	 * 图形验证码过期时间 /秒
	 */
	private Integer imgCodeExpireTime = 300;
	
	/**
	 * 不需要使用图形验证码要发送普通验证码的次数
	 */
	private Integer imgCodeNumLimit = 3;
	
	/**
	 * 验证码位数
	 */
	private Integer codeNum = 4;
	/**
	 * ip风控开关
	 */
	private Boolean ipLimitFlag = true;
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

	public Integer getNormalIpCodeTotal() {
		return normalIpCodeTotal;
	}

	public void setNormalIpCodeTotal(Integer normalIpCodeTotal) {
		this.normalIpCodeTotal = normalIpCodeTotal;
	}

	public Boolean getIpLimitFlag() {
		return ipLimitFlag;
	}

	public void setIpLimitFlag(Boolean ipLimitFlag) {
		this.ipLimitFlag = ipLimitFlag;
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
	
	public static void main(String[] args){
		System.out.println(JsonUtils.toFastJson(new VerifyCodeConfigVO()));
	}
}
