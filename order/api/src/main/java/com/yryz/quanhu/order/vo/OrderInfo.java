package com.yryz.quanhu.order.vo;

import java.io.Serializable;

/**
 * 订单详情
 * 
 * @author yehao
 *
 */
@SuppressWarnings("serial")
public class OrderInfo implements Serializable {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 客户ID
	 */
	private String custId;
	/**
	 * 涉及金额
	 */
	private Long cost;
	/**
	 * 支付类型，1，加币；0，减币
	 */
	private Integer orderType;
	/**
	 * 订单类型，1，余额；2，积分；3，混合
	 */
	private Integer type;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 产品类型
	 */
	private Integer productType;
	/**
	 * 产品说明
	 */
	private String productDesc;
	/**
	 * 订单说明
	 */
	private String orderDesc;
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 订单状态 0：创建，1：成功
	 */
	private Integer orderState;			
	
	/**
	 * 回调地址 ,异步订单支付成功后，会通过此接口回调到orderInfo到发起服务器。回调格式为：msg=orderInfo(JSONString)
	 */
	private String callback;	
	
	/**
	 * 扩展参数，如果需要资金系统回调的时候透传一些参数，可以把数据放到此字段。
	 */
	private String bizContent;

	public OrderInfo() {
		super();
	}

	public OrderInfo(String orderId, Long cost, Integer orderType, Integer type, String productId, Integer productType,
			String productDesc, String orderDesc, String remark) {
		super();
		this.orderId = orderId;
		this.cost = cost;
		this.orderType = orderType;
		this.type = type;
		this.productId = productId;
		this.productType = productType;
		this.productDesc = productDesc;
		this.orderDesc = orderDesc;
		this.remark = remark;
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

	public Long getCost() {
		return cost;
	}

	public void setCost(Long cost) {
		this.cost = cost;
	}

	public Integer getOrderType() {
		return orderType;
	}

	public void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getProductType() {
		return productType;
	}

	public void setProductType(Integer productType) {
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * @return the orderState
	 */
	public Integer getOrderState() {
		return orderState;
	}

	/**
	 * @param orderState the orderState to set
	 */
	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	/**
	 * @return the callback
	 */
	public String getCallback() {
		return callback;
	}

	/**
	 * @param callback the callback to set
	 */
	public void setCallback(String callback) {
		this.callback = callback;
	}
	
	/**
	 * @return the bizContent
	 */
	public String getBizContent() {
		return bizContent;
	}

	/**
	 * @param bizContent the bizContent to set
	 */
	public void setBizContent(String bizContent) {
		this.bizContent = bizContent;
	}

	@Override
	public String toString() {
		return "OrderInfo [orderId=" + orderId + ", custId=" + custId + ", cost=" + cost + ", orderType=" + orderType
				+ ", type=" + type + ", productId=" + productId + ", productType=" + productType + ", productDesc="
				+ productDesc + ", orderDesc=" + orderDesc + ", remark=" + remark + "]";
	}

}
