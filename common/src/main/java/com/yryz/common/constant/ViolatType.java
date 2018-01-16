/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年11月10日
 * Id: ViolatType.java, 2017年11月10日 上午10:58:52 Administrator
 */
package com.yryz.common.constant;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年11月10日 上午10:58:52
 * @Description 违规操作类型
 */
public enum ViolatType {
	/** 警告 */
	WARN(0),
	/** 禁言 */
	NOTALK(1),
	/** 冻结 */
	FREEZE(2),
	/** 解除禁言 */
	ALLTAIK(3),
	/** 解冻 */
	NOFREEZE(4);
	
	private int type;
	
	ViolatType(int type) {
		this.type = type;
	}
	public int getType(){
		return this.type;
	}
}
