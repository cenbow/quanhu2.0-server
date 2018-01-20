/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月20日
 * Id: EmailConfig.java, 2018年1月20日 下午12:26:49 yehao
 */
package com.yryz.quanhu.configuration;

import org.springframework.context.annotation.Configuration;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月20日 下午12:26:49
 * @Description 邮件配置
 */
@Configuration
public class EmailConfig {
	
	private String userAccount = "userAccount";
	
	private String userPassword = "userPassword";

	/**
	 * 
	 * @exception 
	 */
	public EmailConfig() {
		super();
	}

	/**
	 * @return the userAccount
	 */
	public String getUserAccount() {
		return userAccount;
	}

	/**
	 * @param userAccount the userAccount to set
	 */
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	/**
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	

}
