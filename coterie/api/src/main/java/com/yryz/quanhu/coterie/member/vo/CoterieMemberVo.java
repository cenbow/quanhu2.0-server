package com.yryz.quanhu.coterie.member.vo;

import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieMemberVo implements Serializable {


	/**
	 * 收费金额
	 */
	private Long amount;

	/**
	 * 未禁言10，禁言11
	 */
	private Byte banSpeak;

	/**
	 * 私圈ID
	 */
	private Long coterieId;

	/**
	 * 加入时间
	 */
	private Date createDate;

	/**
	 * 加入方式：免费10，收费11
	 */
	private Byte joinType;

	/**
	 * 更新时间
	 */
	private Date lastUpdateDate;

	/**
	 * 用户相关信息
	 */
	private UserSimpleVO user;

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Byte getBanSpeak() {
		return banSpeak;
	}

	public void setBanSpeak(Byte banSpeak) {
		this.banSpeak = banSpeak;
	}

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

	public Byte getJoinType() {
		return joinType;
	}

	public void setJoinType(Byte joinType) {
		this.joinType = joinType;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public UserSimpleVO getUser() {
		return user;
	}

	public void setUser(UserSimpleVO user) {
		this.user = user;
	}
}

