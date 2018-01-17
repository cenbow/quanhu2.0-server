package com.yryz.quanhu.user.contants;

public enum ThirdType {
	/** 微信 */
	WEIXIN(1,"Weixin"),
	/** 微博 */
	SINA(2,"sina"),
	/** qq */
	QQ(3,"QQ"),
	/**
	 * im
	 */
	IM(4,"IM"),
	/**
	 * jpush
	 */
	JPUSH(5,"JIGUANG"),
	/**
	 * 
	 */
	SMS(6,"DAYU");
	private int type;
	
	private String label;
	
	ThirdType(int type,String label) {
		this.type = type;
		this.label = label;
	}
	public int getType(){
		return this.type;
	}
	public String getLabel(){
		return this.label;
	}
}
