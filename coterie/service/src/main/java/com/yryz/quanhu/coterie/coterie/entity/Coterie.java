/*
 * Coterie.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.coterie.coterie.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 私圈
 * 
 * @author jk
 * @version 1.0 2017-08-23
 * @since 1.0
 */
public class Coterie implements Serializable {
	private static final long serialVersionUID = 6669520696671787176L;

	/**
	 * 自增主键
	 */
	private Long id;

	/**
	 * 私圈id
	 */
	private Long coterieId;

	/**
	 * 用户ID
	 */
	private String ownerId;



	/**
	 * 圈主姓名
	 */
	private String ownerName;

	/**
	 * 个人简介
	 */
	private String ownerIntro;

	/**
	 * 封面图
	 */
	private String icon;

	/**
	 * 圈子名称
	 */
	private String name;

	/**
	 * 圈子简介
	 */
	private String intro;

	/**
	 * 私圈名片(二维码)
	 */
	private String qrUrl;

	/**
	 * 加入私圈金额(悠然币)，0表示免费
	 */
	private Integer joinFee;

	/**
	 * 咨询费，0表示免费
	 */
	private Integer consultingFee;

	/**
	 * 成员加入是否需要审核（0不审核，1审核）
	 */
	private Integer joinCheck;

	/**
	 * 成员数量
	 */
	private Integer memberNum;

	/**
	 * 状态：10待审核，11审批通过，12审批未通过，13上架，14下架
	 */
	private Byte status;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date lastUpdateDate;

	/**
	 * 审批时间
	 */
	private Date processTime;

	/**
	 * 0:未删除，1：删除
	 */
	private Byte deleted;

	/**
	 * 热度值
	 */
	private Long heat;

	/**
	 * 是否达人
	 */
	private Byte isExpert;

	/**
	 * 是否推荐
	 */
	private Byte recommend;

	/**
	 * 最后更新文章时间
	 */
	private Date lastInfoTime;
	private Integer shelveFlag;



	private Long auditUserId;
	private String auditRemark;
	private Integer sort;
	private Long createUserId;
	private Long lastUpdateUserId;
	private Integer masterLastViewTime;
	private String moduleEnum;







	public Integer getShelveFlag() {
		return shelveFlag;
	}
	public void setShelveFlag(Integer shelveFlag) {
		this.shelveFlag = shelveFlag;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId == null ? null : coterieId ;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro == null ? null : intro.trim();
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl == null ? null : qrUrl.trim();
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

	public Integer getJoinCheck() {
		return joinCheck;
	}

	public void setJoinCheck(Integer joinCheck) {
		this.joinCheck = joinCheck;
	}

	public Integer getMemberNum() {
		return memberNum;
	}

	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
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

	public Date getLastInfoTime() {
		return lastInfoTime;
	}

	public void setLastInfoTime(Date lastInfoTime) {
		this.lastInfoTime = lastInfoTime;
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

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Long getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getLastUpdateUserId() {
		return lastUpdateUserId;
	}

	public void setLastUpdateUserId(Long lastUpdateUserId) {
		this.lastUpdateUserId = lastUpdateUserId;
	}

	public Integer getMasterLastViewTime() {
		return masterLastViewTime;
	}

	public void setMasterLastViewTime(Integer masterLastViewTime) {
		this.masterLastViewTime = masterLastViewTime;
	}

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null){
			return false;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		Coterie other = (Coterie) obj;
		if (coterieId == null) {
			if (other.coterieId != null){
				return false;
			}
		} else if (!coterieId.equals(other.coterieId)){
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Coterie [id=" + id + ", coterieId=" + coterieId + ", ownerId=" + ownerId + ", ownerName=" + ownerName + ", ownerIntro=" + ownerIntro + ", icon=" + icon + ", name=" + name
				+ ", intro=" + intro + ", qrUrl=" + qrUrl + ", joinFee=" + joinFee + ", consultingFee=" + consultingFee
				+ ", joinCheck=" + joinCheck + ", memberNum=" + memberNum + ", status=" + status + ", createDate="
				+ createDate + ", lastUpdateDate=" + lastUpdateDate + ", processTime=" + processTime + ", deleted="
				+ deleted + ", heat=" + heat + ", isExpert=" + isExpert + ", recommend=" + recommend + ", lastInfoTime="
				+ lastInfoTime + "]";
	}
}