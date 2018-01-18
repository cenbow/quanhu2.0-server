/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年10月18日
 * Id: HomePageBody.java, 2017年10月18日 上午11:41:33 yehao
 */
package com.yryz.quanhu.basic.message;

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
	
	private String custId;

	/**
	 * 
	 * @exception 
	 */
	public HomePageBody() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the custId
	 */
	public String getCustId() {
		return custId;
	}

	/**
	 * @param custId the custId to set
	 */
	public void setCustId(String custId) {
		this.custId = custId;
	}
	
}
