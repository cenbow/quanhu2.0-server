package com.yryz.quanhu.coterie.member.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberVo implements Serializable{
	private static final long serialVersionUID = -312906342632969011L;

	/**
     * 私圈ID
     */
    private Long coterieId;
    
    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 0未禁言，1禁言
     */
    private Integer banSpeak;

    /**
     * 加入时间
     */
    private Date createDate;
    
    /**
     * 加入方式：0免费，1收费
     */
    private Integer joinType;
    
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

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}


	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getBanSpeak() {
		return banSpeak;
	}

	public void setBanSpeak(Integer banSpeak) {
		this.banSpeak = banSpeak;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}
}
