/*
 * Coterie.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.dymaic.vo;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;

/**
 * 私圈
 * 
 * @author jk
 * @version 1.0 2017-08-23
 * @since 1.0
 */
public class CoterieInfoVo implements Serializable {
	private static final long serialVersionUID = -4602962422209178907L;

	@ApiModelProperty("私圈id")
	private Long kid;
	
	@ApiModelProperty("私圈名称")
	private String coterieName;
	
	@ApiModelProperty("封面图")
	private String icon;

	@ApiModelProperty("私圈简介")
	private String intro;

	@ApiModelProperty("圈主id")
	private String ownerId;

	@ApiModelProperty("圈主姓名")
	private String ownerName;

	@ApiModelProperty("个人简介")
	private String ownerIntro;

	@ApiModelProperty("加入私圈金额(悠然币)，0表示免费")
	private Integer joinFee;

	@ApiModelProperty("咨询费，0表示免费")
	private Integer consultingFee;

	@ApiModelProperty("成员加入是否需要审核（10不审核，11审核）")
	private Byte joinCheck;

	@ApiModelProperty("成员数量")
	private Integer memberNum;

	@ApiModelProperty("审核状态")
	private Byte state;

	@ApiModelProperty("审批时间")
	private Date processTime;
	
	@ApiModelProperty("审批人id")
	private Long auditUserId;
	
	@ApiModelProperty("审批备注")
	private String auditRemark;
	
	@ApiModelProperty("热度值")
	private Long heat;
	
	@ApiModelProperty("上下架状态(10：上架,11：下架)")
	private Byte shelveFlag;
	
	@ApiModelProperty("删除状态(10：未删除,11：删除)")
	private Byte deleted;
	
	@ApiModelProperty("创建时间")
	private Date createDate;

	@ApiModelProperty("更新时间")
	private Date lastUpdateDate;
	
	@ApiModelProperty("版本")
	private Integer revision;
	
	@ApiModelProperty("备用")
	private Byte isExpert;

	@ApiModelProperty("是否推荐，10否，11是")
	private Byte recommend;
	
	@ApiModelProperty("圈主最后访问时间")
	private Date masterLastViewTime;
	
	@ApiModelProperty("圈主信息")
	private UserSimpleVo user;
	
	public Long getKid() {
		return kid;
	}

	public void setKid(Long kid) {
		this.kid = kid;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId == null ? null : ownerId.trim();
	}


	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName == null ? null : ownerName.trim();
	}

	public String getOwnerIntro() {
		return ownerIntro;
	}

	public void setOwnerIntro(String ownerIntro) {
		this.ownerIntro = ownerIntro == null ? null : ownerIntro.trim();
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon == null ? null : icon.trim();
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
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

	public Byte getJoinCheck() {
		return joinCheck;
	}

	public void setJoinCheck(Byte joinCheck) {
		this.joinCheck = joinCheck;
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
	}

	public Long getHeat() {
		return heat;
	}

	public void setHeat(Long heat) {
		this.heat = heat;
	}

	public Byte getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(Byte isExpert) {
		this.isExpert = isExpert;
	}

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
	}

	public String getCoterieName() {
		return coterieName;
	}

	public void setCoterieName(String coterieName) {
		this.coterieName = coterieName;
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public Long getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Long auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public Byte getShelveFlag() {
		return shelveFlag;
	}

	public void setShelveFlag(Byte shelveFlag) {
		this.shelveFlag = shelveFlag;
	}

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
	}

	public Date getMasterLastViewTime() {
		return masterLastViewTime;
	}

	public void setMasterLastViewTime(Date masterLastViewTime) {
		this.masterLastViewTime = masterLastViewTime;
	}

	public UserSimpleVo getUser() {
		return user;
	}

	public void setUser(UserSimpleVo user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "CoterieInfoVo [kid=" + kid + ", coterieName=" + coterieName + ", icon=" + icon + ", intro=" + intro
				+ ", ownerId=" + ownerId + ", ownerName=" + ownerName + ", ownerIntro=" + ownerIntro + ", joinFee="
				+ joinFee + ", consultingFee=" + consultingFee + ", joinCheck=" + joinCheck + ", memberNum=" + memberNum
				+ ", state=" + state + ", processTime=" + processTime + ", auditUserId=" + auditUserId
				+ ", auditRemark=" + auditRemark + ", heat=" + heat + ", shelveFlag=" + shelveFlag + ", deleted="
				+ deleted + ", createDate=" + createDate + ", lastUpdateDate=" + lastUpdateDate + ", revision="
				+ revision + ", isExpert=" + isExpert + ", recommend=" + recommend + ", masterLastViewTime="
				+ masterLastViewTime + ", user=" + user + "]";
	}
}