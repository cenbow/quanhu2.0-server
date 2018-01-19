/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月5日
 * Id: CommonSafeConfigVO.java, 2017年12月5日 下午3:19:05 Administrator
 */
package com.yryz.quanhu.message.entity.config;

import java.io.Serializable;

/**
 * 公共安全配置
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月5日 下午3:19:05
 */
@SuppressWarnings("serial")
public class IpLimitConfigVO implements Serializable {
	/**
	 * ip限制最大总量
	 */
	private Integer ipLimitMax;
	
	/**
	 * ip限制频率 /秒
	 */
	private Integer ipPerLimit;
	/**
	 * ip限制开关
	 */
	private Boolean ipLimitFlag;
	
	

	public Integer getIpLimitMax() {
		return ipLimitMax;
	}

	public void setIpLimitMax(Integer ipLimitMax) {
		this.ipLimitMax = ipLimitMax;
	}

	public Integer getIpPerLimit() {
		return ipPerLimit;
	}

	public void setIpPerLimit(Integer ipPerLimit) {
		this.ipPerLimit = ipPerLimit;
	}

	public Boolean getIpLimitFlag() {
		return ipLimitFlag;
	}

	public void setIpLimitFlag(Boolean ipLimitFlag) {
		this.ipLimitFlag = ipLimitFlag;
	}

	public IpLimitConfigVO() {
		super();
	}

	/**
	 * @param ipLimitMax
	 * @param ipPerLimit
	 * @param ipLimitFlag
	 * @exception 
	 */
	public IpLimitConfigVO(Integer ipLimitMax, Integer ipPerLimit, Boolean ipLimitFlag) {
		super();
		this.ipLimitMax = ipLimitMax;
		this.ipPerLimit = ipPerLimit;
		this.ipLimitFlag = ipLimitFlag;
	}

	@Override
	public String toString() {
		return "IpLimitConfigVO [ipLimitMax=" + ipLimitMax + ", ipPerLimit=" + ipPerLimit + ", ipLimitFlag="
				+ ipLimitFlag + "]";
	}

	

}
