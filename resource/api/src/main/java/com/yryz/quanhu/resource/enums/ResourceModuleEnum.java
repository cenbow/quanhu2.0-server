/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月16日
 * Id: ResourceTypeEnum.java, 2018年1月16日 下午2:15:18 yehao
 */
package com.yryz.quanhu.resource.enums;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月16日 下午2:15:18
 * @Description 资源类型的枚举类
 */
public enum  ResourceModuleEnum {
	/**
	 * 私圈(1000)
	 */
	COTERIE ("1000","私圈"),

	/**
	 * 用户(1001)
	 */
	USER ("1001","用户"),

	/**
	 * 转发(1002)
	 */
	TRANSMIT ("1002","转发"),

	/**
	 * 文章(1003)
	 */
	RELEASE ("1003","文章"),

	/**
	 * 话题(1004)
	 */
	TOPIC ("1004","话题"),

	/**
	 * 帖子(1005)
	 */
	POSTS ("1005","帖子"),

	/**
	 * 问题(1006)
	 */
	QUESTION ("1006","问题"),

	/**
	 * 答案(1007)
	 */
	ANSWER ("1007","答案"),

	;
	private String code;
	private String name;

	ResourceModuleEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}


	public static ResourceModuleEnum get(String code){
		ResourceModuleEnum [] values = ResourceModuleEnum.values();
		for (int i = 0; i < values.length; i++) {
			ResourceModuleEnum _enum = values[i];
			if(_enum.getCode().equalsIgnoreCase(code)){
				return _enum;
			}
		}
		return null;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
}
