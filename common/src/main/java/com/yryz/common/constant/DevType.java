/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月5日
 * Id: DevType.java, 2018年1月5日 上午10:13:29 Administrator
 */
package com.yryz.common.constant;

import org.apache.commons.lang3.StringUtils;;

/**
 * 用户使用设备类型
 * @author danshiyu
 * @version 1.0
 * @date 2018年1月5日 上午10:13:29
 */
public enum DevType {
	/** ios */
	IOS("11","APP"),
	/** android */
	ANDROID("12","APP"),
	/** wap */
	WAP("13","Wap"),
	/** web */
	WEB("14","Web");
	
	private String type;
	private String label;
	
	DevType(String type,String label) {
		this.type = type;
		this.label = label;		
	}
	
	public String getType(){
		return type;
	}
	public String getLabel(){
		return label;
	}
	
	/**
	 * 根据devType得到设备类型枚举<br/>
	 * type=3时，根据userAgent判断是否WAP
	 * @param type
	 * @return
	 */
	public static DevType getEnumByType(String type,String userAgent){
		DevType devType = null;
		if(StringUtils.isBlank(type)){
			type = DevType.IOS.getType();
		}
		switch(type){
		case "11":
			devType = DevType.IOS;
			break;
		case "12":
			devType = DevType.ANDROID;
			break;
		case "13":
			if(StringUtils.contains(userAgent, AppConstants.MOBILE)){
				devType = DevType.WAP;
			} else{
				devType = DevType.WEB;
			}
			break;
		default:
			break;
		}
		return devType;
	}
	
	/**
	 * 根据devType label得到设备类型枚举<br/>
	 * @param label
	 * @return
	 */
	public static DevType getEnumByLable(String label){
		DevType devType = DevType.IOS;
		if(StringUtils.isBlank(label)){
			label = DevType.IOS.getLabel();
		}
		switch(label){
		case "APP":
			devType = DevType.IOS;
			break;
		case "Wap":
			devType = DevType.WAP;
			break;
		case "Web":
			devType = DevType.WEB;
			break;
		default:
			break;
		}
		return devType;
	}
}
