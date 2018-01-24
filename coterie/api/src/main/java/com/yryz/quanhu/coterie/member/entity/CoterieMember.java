package com.yryz.quanhu.coterie.member.entity;

import com.yryz.common.entity.GenericEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMember extends GenericEntity implements Serializable{
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
	 * 通过10，未通过20,待审批30
	 */
	private Integer memberStatus;

	/**
	 * 加入原因
	 */
	private String reason;

    /**
	 * 未禁言20，禁言10
     */
    private Integer banSpeak;

	/**
	 * 审批时间
	 */
	private Date processTime;

	/**
	 * 是否免费：是10，否20
	 */
	private Integer joinType;

	/**
	 * 金额
	 */
	private Long amount;

	/**
	 * 是否被踢：是10，否20
	 */
	private Integer kickStatus;

	/**
	 * 删除状态(正常10，已删除20)
	 */
	private Integer delFlag;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(Integer memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Integer getBanSpeak() {
		return banSpeak;
	}

	public void setBanSpeak(Integer banSpeak) {
		this.banSpeak = banSpeak;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Integer getJoinType() {
		return joinType;
	}

	public void setJoinType(Integer joinType) {
		this.joinType = joinType;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Integer getKickStatus() {
		return kickStatus;
	}

	public void setKickStatus(Integer kickStatus) {
		this.kickStatus = kickStatus;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
