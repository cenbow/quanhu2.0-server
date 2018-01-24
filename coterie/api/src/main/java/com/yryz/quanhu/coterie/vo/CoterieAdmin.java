package com.yryz.quanhu.coterie.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:49:41
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@SuppressWarnings("serial")
public class CoterieAdmin implements Serializable{
	private static final long serialVersionUID = -3326016770793173068L;

	/**
     * 用户昵称
     */
    private String nickName;
    
    /**
     * 用户姓名
     */
    private String custName;
    
    /**
     * 用户ID
     */
    private String custId;
    
    /**
     * 用户介绍
     */
    private String ownerIntro;
    
    /**
     * 是否达人
     */
    private Byte isExpert;
    
    /**
     * 手机号
     */
    private String phone;
    
	/**
     * 私圈id
     */
    private String coterieId;
    
    /**
     * 圈子ID
     */
    private String circleId;
    
    /**
     * 私圈名称
     */
    private String name;
    
    /**
     * 私圈简介
     */
    private String intro;
    
    /**
     * 加入付费
     */
    private Integer joinFee;
    
    /**
     * 提问付费
     */
    private Integer consultingFee;
    
    /**
     * 0不审核，1审核
     */
    private Integer joinCheck;
    
    /**
     * 状态：0待审核，2审批未通过，3上架，4下架
     */
    private Byte status;
    
    /**
     * 是否推荐，0否，1是
     */
    private Byte recommend;
    
    /**
     * 私圈创建时间
     */
    private Date createDate;
    
    /**
     * 操作人
     */
    private String operName;
    
    /**
     * 操作时间
     */
    private Date operDate;
    
    /**
     * 操作说明
     */
    private String reason;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	public String getCircleId() {
		return circleId;
	}

	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Integer getJoinFee() {
		return joinFee;
	}

	public void setJoinFee(Integer joinFee) {
		this.joinFee = joinFee;
	}

	public Integer getConsultingFee() {
		return consultingFee;
	}

	public void setConsultingFee(Integer consultingFee) {
		this.consultingFee = consultingFee;
	}

	public String getCustId() {
		return custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	public Integer getJoinCheck() {
		return joinCheck;
	}

	public void setJoinCheck(Integer joinCheck) {
		this.joinCheck = joinCheck;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public Date getOperDate() {
		return operDate;
	}

	public void setOperDate(Date operDate) {
		this.operDate = operDate;
	}

	public Byte getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(Byte isExpert) {
		this.isExpert = isExpert;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getOwnerIntro() {
		return ownerIntro;
	}

	public void setOwnerIntro(String ownerIntro) {
		this.ownerIntro = ownerIntro;
	}

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
	}

	@Override
	public String toString() {
		return "CoterieAdmin [nickName=" + nickName + ", custName=" + custName + ", custId=" + custId + ", ownerIntro="
				+ ownerIntro + ", isExpert=" + isExpert + ", phone=" + phone + ", coterieId=" + coterieId
				+ ", circleId=" + circleId + ", name=" + name + ", intro=" + intro + ", joinFee=" + joinFee
				+ ", consultingFee=" + consultingFee + ", joinCheck=" + joinCheck + ", status=" + status
				+ ", recommend=" + recommend + ", createDate=" + createDate + ", operName=" + operName + ", operDate="
				+ operDate + ", reason=" + reason + "]";
	}
	
}
