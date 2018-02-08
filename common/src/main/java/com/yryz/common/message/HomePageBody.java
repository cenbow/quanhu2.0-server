/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月18日
 * Id: HomePageBody.java, 2017年10月18日 上午11:41:33 yehao
 */
package com.yryz.common.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年10月18日 上午11:41:33
 * @Description 个人主页的BODY
 */
public class HomePageBody implements Body{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3082664056469603703L;

	/**
	 * 用户id
	 */
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	/**
	 *
	 * @exception
	 */
	public HomePageBody() {
		super();
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
