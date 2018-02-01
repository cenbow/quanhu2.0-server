/**
 * 
 */
package com.yryz.quanhu.user.dto;

import java.io.Serializable;

import com.yryz.quanhu.user.contants.RegType;

/**
 * web第三方登录dto
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class WebThirdLoginDTO implements Serializable {
	/**
	 * 登录类型 {@link RegType.text}
	 */
	private String loginType;
	/**
	 * 登录成功后的重定向地址
	 */
	private String returnUrl;
	/**
	 * 应用id
	 */
	private String appId;
	/**
	 * 授权后第三方识别码
	 */
	private String code;
	/**
	 * 第三方扩展字段
	 */
	private String state;
	/**
	 * 活动渠道码
	 */
	private String activityChannelCode;
	public String getLoginType() {
		return loginType;
	}
	public void setLoginType(String loginType) {
		this.loginType = loginType;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getActivityChannelCode() {
		return activityChannelCode;
	}
	public void setActivityChannelCode(String activityChannelCode) {
		this.activityChannelCode = activityChannelCode;
	}
	public WebThirdLoginDTO() {
		super();
	}
	/**
	 * 第三方登录
	 * @param loginType
	 * @param returnUrl
	 * @param appId
	 * @param activityChannelCode
	 */
	public WebThirdLoginDTO(String loginType, String returnUrl, String appId, String activityChannelCode) {
		super();
		this.loginType = loginType;
		this.returnUrl = returnUrl;
		this.appId = appId;
		this.activityChannelCode = activityChannelCode;
	}
	/**
	 * 第三方登录回调
	 * @param appId
	 * @param code
	 * @param state
	 */
	public WebThirdLoginDTO(String appId, String code, String state) {
		super();
		this.appId = appId;
		this.code = code;
		this.state = state;
	}
	@Override
	public String toString() {
		return "WebThirdLoginDTO [loginType=" + loginType + ", returnUrl=" + returnUrl + ", appId=" + appId + ", code="
				+ code + ", state=" + state + ", activityChannelCode=" + activityChannelCode + "]";
	}
}
