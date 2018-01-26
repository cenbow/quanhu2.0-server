package com.yryz.quanhu.coterie.member.vo;

import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberApplyVo implements Serializable{

	/**
     * 私圈ID
     */
    private Long coterieId;

	/**
	 * 加入时间
	 */
	private Date createDate;

	/**
	 * 加入理由
	 */
	private String reason;

	/**
	 * 0通过，1未通过,2待审批
	 */
	private Byte memberStatus;

    /**
     * 用户信息
     */
    private UserSimpleVO user;

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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public UserSimpleVO getUser() {
		return user;
	}

	public void setUser(UserSimpleVO user) {
		this.user = user;
	}

	public Byte getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(Byte memberStatus) {
		this.memberStatus = memberStatus;
	}
}
