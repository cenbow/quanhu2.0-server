package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.Date;
/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:45
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class CoterieMemberInfo implements Serializable{
	private static final long serialVersionUID = -312906342632969011L;

	/**
     * 私圈ID
     */
    private String coterieId;
    
    /**
     * 私圈名称
     */
    private String coterieName;

    /**
     * 用户ID
     */
    private String custId;
    
    /**
     * 用户图标
     */
    private String custIcon;
    
    /**
     * 用户名称
     */
    private String custName;

    /**
     * 0未禁言，1禁言
     */
    private Byte banSpeak;

    /**
     * 加入时间
     */
    private Date createDate;
    
    /**
     * 加入方式：0免费，1收费
     */
    private Byte joinType;
    
    /**
     * 收费金额 
     */
    private Integer amount;
    
    /**
     * 手机号
     */
    private String phone;
    
    /**
     * 注册时间
     */
    private String registerDate;

	public String getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Byte getBanSpeak() {
		return banSpeak;
	}

	public void setBanSpeak(Byte banSpeak) {
		this.banSpeak = banSpeak;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCustIcon() {
		return custIcon;
	}

	public void setCustIcon(String custIcon) {
		this.custIcon = custIcon;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public Byte getJoinType() {
		return joinType;
	}

	public void setJoinType(Byte joinType) {
		this.joinType = joinType;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getCoterieName() {
		return coterieName;
	}

	public void setCoterieName(String coterieName) {
		this.coterieName = coterieName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(String registerDate) {
		this.registerDate = registerDate;
	}

	@Override
	public String toString() {
		return "CoterieMemberInfo [coterieId=" + coterieId + ", coterieName=" + coterieName + ", custId=" + custId
				+ ", custIcon=" + custIcon + ", custName=" + custName + ", banSpeak=" + banSpeak + ", createDate="
				+ createDate + ", joinType=" + joinType + ", amount=" + amount + ", phone=" + phone + ", registerDate="
				+ registerDate + "]";
	}
    
}
