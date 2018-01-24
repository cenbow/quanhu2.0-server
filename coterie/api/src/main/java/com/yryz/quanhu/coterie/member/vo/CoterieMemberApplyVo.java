package com.yryz.quanhu.coterie.member.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberApplyVo implements Serializable{
	private static final long serialVersionUID = -2210849405370472836L;

	/**
     * 私圈ID
     */
    private String coterieId;

    /**
     * 用户信息
     */
//    private User user;
    
    /**
     * 0通过，1未通过,2待审批
     */
    private Byte status;

    /**
     * 加入理由
     */
    private String reason;

    /**
     * 加入时间
     */
    private Date createDate;

	public String getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
