package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;

/**
 * 苹果内购
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class IOSPayConfig implements Serializable {

	private String iosId;
	/**
	 * 金额
	 */
	private long cost;

	private long costTrue;
	/**
	 * 手续费
	 */
	private long costFee;

	public IOSPayConfig(String iosId, long cost, long costTrue, long costFee) {
		super();
		this.iosId = iosId;
		this.cost = cost;
		this.costTrue = costTrue;
		this.costFee = costFee;
	}

	public long getCostFee() {
		return costFee;
	}

	public void setCostFee(long costFee) {
		this.costFee = costFee;
	}

	public String getIosId() {
		return iosId;
	}

	public void setIosId(String iosId) {
		this.iosId = iosId;
	}

	public long getCost() {
		return cost;
	}

	public void setCost(long cost) {
		this.cost = cost;
	}

	public long getCostTrue() {
		return costTrue;
	}

	public void setCostTrue(long costTrue) {
		this.costTrue = costTrue;
	}

	@Override
	public String toString() {
		return "IOSPayConfig [iosId=" + iosId + ", cost=" + cost + ", costTrue=" + costTrue + ", costFee=" + costFee
				+ "]";
	}

}
