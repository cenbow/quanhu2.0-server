package com.yryz.quanhu.order.score.type;

public enum ScoreTypeEnum {
	
	Once("ONCE" , "一次性积分"),
	Pertime("PERTIME" ,"每次操作触发积分"),
	Loop("LOOP" , "条件循环积分"),
	Sign("SIGN" , "签到区间累加积分");
	
	private String typeCode;
	
	private String typeDesp;
	
	ScoreTypeEnum(String typeCode , String typeDesp){
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
