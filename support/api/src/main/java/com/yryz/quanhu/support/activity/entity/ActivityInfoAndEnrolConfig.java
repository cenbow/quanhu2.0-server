package com.yryz.quanhu.support.activity.entity;

import java.util.Map;

public class ActivityInfoAndEnrolConfig extends ActivityInfo {

	private static final long serialVersionUID = 1126206832251240758L;
	/**
	 * 报名类型(1报名需支付货币 2报名需支付积分 3免费报名)
	 */
	private Byte signUpType;
	/**
	 * 货币/积分数量
	 */
    private Long amount;
    /**
	 * 报名上限
	 */
    private Integer enrolUpper;
    /**
	 * 手机号是否必填(0否 1是 2是否为空)
	 */
    private Byte phoneRequired;
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
	public Byte getSignUpType() {
		return signUpType;
	}
	public void setSignUpType(Byte signUpType) {
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
	public Byte getPhoneRequired() {
		return phoneRequired;
	}
	public void setPhoneRequired(Byte phoneRequired) {
		this.phoneRequired = phoneRequired;
	}
	public String getConfigSources() {
		return configSources;
	}
	public void setConfigSources(String configSources) {
		this.configSources = configSources;
	}
}
