/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年8月10日
 * Id: PushConfigDTO.java, 2017年8月10日 上午11:52:31 Administrator
 */
package com.yryz.quanhu.message.push.entity;

import java.io.Serializable;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年8月10日 上午11:52:31
 * @Description 推送配置
 */
@SuppressWarnings("serial")
public class PushConfigDTO implements Serializable {
	/** 推送key*/
	private String pushKey ;
	
	/** 推送证书*/
	private String pushSecret;
	
	/** 推送类型 0:别名推送 1:设备号推送 */
	private Integer pushType;
	
	/** 推送渠道描述 */
	private String pushDesc;
	/** 推送环境 true:生产 false:测试 */
	private Boolean pushEvn;
	
	public String getPushKey() {
		return pushKey;
	}

	public void setPushKey(String pushKey) {
		this.pushKey = pushKey;
	}

	public String getPushSecret() {
		return pushSecret;
	}

	public void setPushSecret(String pushSecret) {
		this.pushSecret = pushSecret;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public String getPushDesc() {
		return pushDesc;
	}

	public void setPushDesc(String pushDesc) {
		this.pushDesc = pushDesc;
	}

	public Boolean isPushEvn() {
		return pushEvn;
	}

	public void setPushEvn(Boolean pushEvn) {
		this.pushEvn = pushEvn;
	}

	@Override
	public String toString() {
		return "PushConfigDTO [pushKey=" + pushKey + ", pushSecret=" + pushSecret + ", pushType=" + pushType
				+ ", pushDesc=" + pushDesc + ", pushEvn=" + pushEvn + "]";
	}
	
}
