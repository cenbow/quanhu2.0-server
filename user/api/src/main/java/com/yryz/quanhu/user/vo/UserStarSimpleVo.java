package com.yryz.quanhu.user.vo;

import java.io.Serializable;
/**
 * 达人认证简化信息
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class UserStarSimpleVo implements Serializable {
	/**
	 * 行业以及领域
	 */
	private String tradeField;


	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 推荐语
	 */
	private String recommendDesc;


	public String getRecommendDesc() {
		return recommendDesc;
	}

	public void setRecommendDesc(String recommendDesc) {
		this.recommendDesc = recommendDesc;
	}

	public String getTradeField() {
		return tradeField;
	}

	public void setTradeField(String tradeField) {
		this.tradeField = tradeField == null ? "" : tradeField;
	}


	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}


}
