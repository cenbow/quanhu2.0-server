package com.yryz.quanhu.coterie.member.entity;

import com.yryz.common.entity.BaseEntity;
import com.yryz.common.entity.GenericEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberApply extends GenericEntity implements Serializable {
	private static final long serialVersionUID = -2210849405370472836L;

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
     * 加入理由
     */
    private String reason;

	/**
	 * 审批时间
	 */
	private Date processTime;

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

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Integer getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(Integer delFlag) {
		this.delFlag = delFlag;
	}
}
