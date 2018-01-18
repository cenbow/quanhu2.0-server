/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: EmailConfigVO.java, 2017年12月4日 下午5:03:32 Administrator
 */
package com.yryz.quanhu.basic.entity.config;

import java.io.Serializable;

/**
 * 邮件配置
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午5:03:32
 */
@SuppressWarnings("serial")
public class EmailConfigVO implements Serializable {
	/**
	 * 发送人邮件账号
	 */
	private String emailAccount;
	
	/**
	 * 发送人邮件密码
	 */
	private String emailPassword;
	
	/**
	 * smtp服务器地址
	 */
	private String host;
	
	/**
	 * 授权标识
	 */
	private String auth;
	
	/**
	 * 发件人描述
	 */
	private String mailName;
	/**
	 * 邮件模板内容
	 */
	private String content;
	public String getEmailAccount() {
		return emailAccount;
	}

	public void setEmailAccount(String emailAccount) {
		this.emailAccount = emailAccount;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getMailName() {
		return mailName;
	}

	public void setMailName(String mailName) {
		this.mailName = mailName;
	}
	
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * 
	 * @exception 
	 */
	public EmailConfigVO() {
		super();
	}

	/**
	 * @param emailAccount
	 * @param emailPassword
	 * @param host
	 * @param auth
	 * @param mailName
	 * @param content
	 * @exception 
	 */
	public EmailConfigVO(String emailAccount, String emailPassword, String host, String auth, String mailName,
			String content) {
		super();
		this.emailAccount = emailAccount;
		this.emailPassword = emailPassword;
		this.host = host;
		this.auth = auth;
		this.mailName = mailName;
		this.content = content;
	}

	/**
	 * @param emailAccount
	 * @param emailPassword
	 * @param host
	 * @param auth
	 * @param mailName
	 * @exception 
	 */
	public EmailConfigVO(String emailAccount, String emailPassword, String host, String auth, String mailName) {
		super();
		this.emailAccount = emailAccount;
		this.emailPassword = emailPassword;
		this.host = host;
		this.auth = auth;
		this.mailName = mailName;
	}

	@Override
	public String toString() {
		return "EmailConfigVO [emailAccount=" + emailAccount + ", emailPassword=" + emailPassword + ", host=" + host
				+ ", auth=" + auth + ", mailName=" + mailName + ", content=" + content + "]";
	}
	
	
}
