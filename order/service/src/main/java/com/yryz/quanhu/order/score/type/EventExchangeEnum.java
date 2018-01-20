package com.yryz.quanhu.order.score.type;

public enum EventExchangeEnum {
	
	Fanout("fanout" , "扇形交换机"),
	Direct("redirect" , "直连交换机");
	
	private String exchangeName;
	
	private String exchangeDesp;
	
	EventExchangeEnum(String exchangeName , String exchangeDesp){
		this.exchangeName = exchangeName;
		this.exchangeDesp = exchangeDesp;
	}

	public String getExchangeName() {
		return exchangeName;
	}

	public void setExchangeName(String exchangeName) {
		this.exchangeName = exchangeName;
	}

	public String getExchangeDesp() {
		return exchangeDesp;
	}

	public void setExchangeDesp(String exchangeDesp) {
		this.exchangeDesp = exchangeDesp;
	}
	
}
