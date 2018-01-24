package com.yryz.quanhu.user.dto;

import java.io.Serializable;

/**
 * 解绑第三方dto
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UnBindThirdDTO implements Serializable{
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 第三方id
	 */
	private String thirdId;
	/**
	 * 第三方类型
	 */
	private Integer type;
	/**
	 * 应用id
	 */
	private String appId;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getThirdId() {
		return thirdId;
	}
	public void setThirdId(String thirdId) {
		this.thirdId = thirdId;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public UnBindThirdDTO() {
		super();
	}
	public UnBindThirdDTO(Long userId, String thirdId, Integer type, String appId) {
		super();
		this.userId = userId;
		this.thirdId = thirdId;
		this.type = type;
		this.appId = appId;
	}
}
