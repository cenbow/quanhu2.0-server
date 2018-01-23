package com.yryz.quanhu.order.grow.type;

public enum GrowTypeEnum {
	
	Once("ONCE" , "一次性成长"),
	Pertime("PERTIME" ,"每次操作触发成长"),
	Loop("LOOP" , "条件循环成长");
	
	private String typeCode;
	
	private String typeDesp;
	
	GrowTypeEnum(String typeCode , String typeDesp){
		this.typeCode = typeCode;
		this.typeDesp = typeDesp;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeDesp() {
		return typeDesp;
	}

	public void setTypeDesp(String typeDesp) {
		this.typeDesp = typeDesp;
	}

}
