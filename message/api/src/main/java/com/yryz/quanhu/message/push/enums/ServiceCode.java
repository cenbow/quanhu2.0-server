/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: CommonServiceType.java, 2017年12月4日 下午3:31:10 Administrator
 */
package com.yryz.quanhu.message.push.enums;

/**
 * 通用业务类型
 * @author suyongcheng
 * @version 1.0
 * @date 2017年12月4日 下午3:31:10
 * 1.注册、2.登录、3.找回密码等(图形验证码接口不用传）
 */
public enum ServiceCode {
	/** 注册*/
	register(0, "register"),
	/**登录 */
	Login(1,"Login"),
	/** 找回密码 */
	retrieve_password (2, "retrieve_password");
	private int type;

	private String name;

	ServiceCode(int type, String name) {
		this.type = type;
		this.name = name;
	}

	public int getType() {
		return this.type;
	}

	public String getName() {
		return this.name;
	}
	
	/**
	 * 根据业务类型值获取枚举对象
	 * @param type
	 * @return
	 */
	public static ServiceCode getEnumByType(int type){
		ServiceCode serviceType = null;
		switch (type) {
		case 0:
			serviceType = ServiceCode.register;			
			break;
		case 1:
			serviceType = ServiceCode.Login;			
			break;
		case 2:
			serviceType = ServiceCode.retrieve_password;			
			break;
		default:
			break;
		}
		return serviceType;
	}
}
