package com.yryz.quanhu.other.activity.entity;

import java.util.Map;

public class ActivityInfoAndEnrolConfig extends ActivityInfo {

	private static final long serialVersionUID = 1126206832251240758L;
	/**
	 * 报名类型(11报名需支付货币 12报名需支付积分 13免费报名)
	 */
	private Integer signUpType;
	/**
	 * 货币/积分数量
	 */
    private Long amount;
    /**
	 * 报名上限
	 */
    private Integer enrolUpper;
    /**
	 * 手机号是否必填(10否 11是 12是否为空)
	 */
    private Integer phoneRequired;
    /**
	 * 配置数据
	 */
    private String configSources;
    
    private Map<String, String> sourceMap;
    
	/**
	 * @return the sourceMap
	 */
	public Map<String, String> getSourceMap() {
		return sourceMap;
	}
	/**
	 * @param sourceMap the sourceMap to set
	 */
	public void setSourceMap(Map<String, String> sourceMap) {
		this.sourceMap = sourceMap;
	}
	public Integer getSignUpType() {
		return signUpType;
	}
	public void setSignUpType(Integer signUpType) {
		this.signUpType = signUpType;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Integer getEnrolUpper() {
		return enrolUpper;
	}
	public void setEnrolUpper(Integer enrolUpper) {
		this.enrolUpper = enrolUpper;
	}
	public Integer getPhoneRequired() {
		return phoneRequired;
	}
	public void setPhoneRequired(Integer phoneRequired) {
		this.phoneRequired = phoneRequired;
	}
	public String getConfigSources() {
		return configSources;
	}
	public void setConfigSources(String configSources) {
		this.configSources = configSources;
	}
}
