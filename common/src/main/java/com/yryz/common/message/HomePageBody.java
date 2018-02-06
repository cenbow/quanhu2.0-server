/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月18日
 * Id: HomePageBody.java, 2017年10月18日 上午11:41:33 yehao
 */
package com.yryz.common.message;

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
	private Long userId;

	/**
	 * 功能id
	 */
	private String moduleEnum;

	/**
	 *
	 * @exception
	 */
	public HomePageBody() {
		super();
	}

	public HomePageBody(Long userId, String moduleEnum) {
		this.userId = userId;
		this.moduleEnum = moduleEnum;
	}

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
