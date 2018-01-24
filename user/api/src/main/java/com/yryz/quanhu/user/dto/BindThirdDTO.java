package com.yryz.quanhu.user.dto;

import java.io.Serializable;
/**
 * 绑定第三方账户
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class BindThirdDTO implements Serializable {
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 开放id
	 */
	private String openId;
	/**
	 * 第三方令牌
	 */
	private String accessToken;
	/**
	 * 第三方类型
	 */
	private Integer thirdType;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public Integer getThirdType() {
		return thirdType;
	}
	public void setThirdType(Integer thirdType) {
		this.thirdType = thirdType;
	}
	public BindThirdDTO() {
		super();
	}
	public BindThirdDTO(Long userId, String appId, String openId, String accessToken, Integer thirdType) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.openId = openId;
		this.accessToken = accessToken;
		this.thirdType = thirdType;
	}
	public BindThirdDTO(Long userId, String appId, String openId, Integer thirdType) {
		super();
		this.userId = userId;
		this.appId = appId;
		this.openId = openId;
		this.thirdType = thirdType;
	}
	
}
