/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: CustAccountStatus.java, 2017年11月10日 上午11:04:00 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 上午11:04:00
 * @Description 用户账户状态
 */
public enum CustAccountStatus {
	/** 正常 */
	NORMAL(0),
	/** 冻结 */
	FREEZE(1);
	private int status;
	
	CustAccountStatus(int status) {
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
