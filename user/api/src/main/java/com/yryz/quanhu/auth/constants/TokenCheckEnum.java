/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: TokenCheckEnum.java, 2018年1月5日 下午3:42:47 Administrator
 */
package com.yryz.quanhu.auth.constants;

/**
 * token检查结果
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 下午3:42:47
 */
public enum TokenCheckEnum {
	/**
     * token验证成功
     */
	SUCCESS(0),
	/**
	 * token验证失败(token错误或者互踢)
	 */
	ERROR(1),
	/**
	 * 没有token，后台被踢
	 */
	NO_TOKEN(2),
	/**
	 * 短token过期
	 */
	EXPIRE(3),
	/**
	 * 无效token
	 */
	INVALID(4);
	
	private int status;
	
	TokenCheckEnum(int status) {
		this.status = status;
	}
	public int getStatus(){
		return this.status;
	}
}
