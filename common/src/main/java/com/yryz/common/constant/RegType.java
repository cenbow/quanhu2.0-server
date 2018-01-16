/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: RegType.java, 2018年1月5日 上午10:11:27 Administrator
 */
package com.yryz.common.constant;

	/**
	 * 注册类型
	 *
	 */
	public enum RegType{
		/** 微信 */
		WEIXIN(1,"Weixin"),
		/** 微博 */
		SINA(2,"sina"),
		/** qq */
		QQ(3,"QQ"),
		/** 手机号 */
		PHONE(4,"Phone");
		
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
			case 1:
				regType = RegType.WEIXIN;
				break;
			case 2:
				regType = RegType.SINA;
				break;
			case 3:
				regType = RegType.QQ;
				break;
			case 4:
				regType = RegType.PHONE;
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
			case "Sina":
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
			default:
				break;
			}
			return regType;
		}
}
