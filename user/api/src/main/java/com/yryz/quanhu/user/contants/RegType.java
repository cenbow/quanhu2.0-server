/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: RegType.java, 2018年1月5日 上午10:11:27 Administrator
 */
package com.yryz.quanhu.user.contants;

/**
 * 注册类型
 *
 */
public enum RegType{
	/** 微信 */
	WEIXIN(10,"Weixin"),
	/** 微博 */
	SINA(11,"sina"),
	/** qq */
	QQ(12,"QQ"),
	/** 手机号 */
	PHONE(13,"Phone"),
	/** 邮箱 */
	EMAIL(14,"email"),
	/** 微信客户端WAP授权 */
	WEIXIN_OAUTH(15,"WeixinOauth");
	
	private int type;
	private String text;
	RegType(int type,String text) {
		this.type = type;
		this.text = text;
	}
	public int getType(){
		return this.type;
	}
	public String getText(){
		return this.text;
	}
	public static RegType getEnumByTye(int type){
		RegType regType = RegType.PHONE;
		switch (type) {
		case 10:
			regType = RegType.WEIXIN;
			break;
		case 11:
			regType = RegType.SINA;
			break;
		case 12:
			regType = RegType.QQ;
			break;
		case 13:
			regType = RegType.PHONE;
			break;
		case 15:
			regType = RegType.WEIXIN_OAUTH;
			break;
		default:
			break;
		}
		return regType;
	}
	
	/**
	 * 根据注册类型别名得到注册类型枚举对象
	 * @param text
	 * @return
	 */
	public static RegType getEnumByText(String text){
		RegType regType = null;
		switch (text) {
		case "Weixin":
		case "weixin":	
			regType = RegType.WEIXIN;
			break;
		case "sina":
		case "weibo":
			regType = RegType.SINA;
			break;
		case "QQ":
		case "qq":
			regType = RegType.QQ;
			break;
		case "Phone":
			regType = RegType.PHONE;
			break;
		case "Weixin_Oauth":
			regType = RegType.WEIXIN_OAUTH;
			break;
		default:
			break;
		}
		return regType;
	}
}
