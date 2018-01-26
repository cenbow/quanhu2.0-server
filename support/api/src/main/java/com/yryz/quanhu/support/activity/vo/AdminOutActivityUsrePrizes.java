package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;

public class AdminOutActivityUsrePrizes extends ActivityUserPrizes {
	
	private static final long serialVersionUID = -2745779202211599111L;

	/*用户昵称*/
	private String custName;
	
	/*绑定手机号码*/
	private	String custPhone;
	

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}

	
	
}
