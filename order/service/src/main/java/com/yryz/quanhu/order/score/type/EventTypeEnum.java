package com.yryz.quanhu.order.score.type;

/**
 * 事件作用类型枚举类，用于全局事件入口的事件分发路由
 * @author lsn
 *
 */
public enum EventTypeEnum {
	
	Score("1" , "积分"),
	Grow("2" , "成长"),
	Hot("3" , "热度");
	
	private String typeCode;
	
	private String typeName;
	
	private EventTypeEnum(String typeCode , String typeName) {
		this.typeCode = typeCode;
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	
}
