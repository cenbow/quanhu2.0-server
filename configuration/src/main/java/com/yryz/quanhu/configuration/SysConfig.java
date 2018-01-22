/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月20日
 * Id: SysConfig.java, 2018年1月20日 下午12:26:42 yehao
 */
package com.yryz.quanhu.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月20日 下午12:26:42
 * @Description 系统配置，用户使用的时候可以直接依赖configration，直接将该对象加入项目中即可使用,多级结构
 */
@Configuration
public class SysConfig {
	
	private EmailConfig email;
	
	Map<String, EmailConfig> map = new HashMap<>();
	
	private EmailConfig aaa;
	
	private EmailConfig bbb;

	/**
	 * @return the email
	 */
	public EmailConfig getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(EmailConfig email) {
		this.email = email;
	}
	
}
