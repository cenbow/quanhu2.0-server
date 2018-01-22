package com.yryz.quanhu.openapi.order.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * 现金订单信息
 * 
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class PayInfoDTO implements Serializable {

	/**
	 * 订单ID
	 */
	private String orderId;
	/**
	 * 客户ID
	 */
	private String custId;
	/**
	 * 支付通道：3,支付宝；4,微信；5，苹果内购
	 */
	private String orderChannel;
	/**
	 * 交易金额
	 */
	private Long cost;
	/**
	 * 实际金额
	 */
	private Long costTrue;
	/**
	 * 手续费
	 */
	private Long costFee;
	/**
	 * 币种156：人民币
	 * 
	 * 840：美元
	 * 
	 * 344：港币
	 * 
	 * 392：日元
	 * 
	 * 978：欧元
	 * 
	 * 458：马来西亚吉特
	 * 
	 * 032：澳元
	 * 
	 */
	private String currency;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品类型：1，充值；2，提现；3，手续费
	 */
	private String productType;
	/**
	 * 产品说明
	 */
	private String productDesc;
	/**
	 * 订单说明
	 */
	private String orderDesc;
	/**
	 * 订单状态 0:未生效 1:已生效
	 */
	private Integer orderState;
	/**
	 * 订单创建时间
	 */
	private Date createTime;
	/**
	 * 订单完成时间
	 */
	private Date completeTime;
	/**
	 * 备注信息
	 */
	private String remark;

	public PayInfoDTO(String orderId, String custId, String orderChannel, Long cost, Long costTrue, Long costFee,
			String currency, String productId, String productType, String productDesc, String orderDesc,
			Integer orderState, Date createTime, Date completeTime, String remark) {
		super();
		this.orderId = orderId;
		this.custId = custId;
		this.orderChannel = orderChannel;
		this.cost = cost;
		this.costTrue = costTrue;
		this.costFee = costFee;
		this.currency = currency;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.orderDesc = orderDesc;
		this.orderState = orderState;
		this.createTime = createTime;
		this.completeTime = completeTime;
		this.remark = remark;
	}

	public PayInfoDTO() {
		super();
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public String getOrderChannel() {
		return orderChannel;
	}

	public void setOrderChannel(String orderChannel) {
		this.orderChannel = orderChannel;
	}

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Long getCostTrue() {
		return costTrue;
	}

	public void setCostTrue(Long costTrue) {
		this.costTrue = costTrue;
	}

	public Long getCostFee() {
		return costFee;
	}

	public void setCostFee(Long costFee) {
		this.costFee = costFee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getOrderDesc() {
		return orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public Integer getOrderState() {
		return orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getCompleteTime() {
		return completeTime;
	}

	public void setCompleteTime(Date completeTime) {
		this.completeTime = completeTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "PayInfoVO [orderId=" + orderId + ", custId=" + custId + ", orderChannel=" + orderChannel + ", cost="
				+ cost + ", costTrue=" + costTrue + ", costFee=" + costFee + ", currency=" + currency + ", productId="
				+ productId + ", productType=" + productType + ", productDesc=" + productDesc + ", orderDesc="
				+ orderDesc + ", orderState=" + orderState + ", createTime=" + createTime + ", completeTime="
				+ completeTime + ", remark=" + remark + "]";
	}

}
