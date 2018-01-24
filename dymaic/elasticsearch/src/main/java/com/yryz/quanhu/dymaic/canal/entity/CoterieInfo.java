/*
 * Coterie.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.dymaic.canal.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
/**
 * 私圈
 * 
 * @author jk
 * @version 1.0 2017-08-23
 * @since 1.0
 */
@Document(indexName = "quanhu-v2-coterieinfo", type = "coterieInfo", refreshInterval = "-1")
public class CoterieInfo implements Serializable {
	private static final long serialVersionUID = 6669520696671787176L;

	/**
	 * 私圈id
	 */
	@Id
	private Long kid;
	
	/**
	 * 私圈名称
	 */
	@Field(type = FieldType.text)
	private String coterieName;
	
	/**
	 * 封面图
	 */
	@Field(type = FieldType.text)
	private String icon;

	/**
	 * 圈子简介
	 */
	@Field(type = FieldType.text)
	private String intro;

	/**
	 * 私圈名片(二维码)
	 */
	@Field(type = FieldType.text)
	private String qrUrl;
	
	/**
	 * 用户ID
	 */
	@Field(type = FieldType.text)
	private String ownerId;

	/**
	 * 圈主姓名
	 */
	@Field(type = FieldType.text)
	private String ownerName;

	/**
	 * 个人简介
	 */
	@Field(type = FieldType.text)
	private String ownerIntro;

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
	private Byte joinCheck;

	/**
	 * 成员数量
	 */
	private Integer memberNum;

	/**
	 * 审核状态
	 */
	private Byte state;

	/**
	 * 审批时间
	 */
	private Date processTime;
	
	private Long auditUserId;
	
	@Field(type = FieldType.text)
	private String auditRemark;
	
	/**
	 * 热度值
	 */
	private Long heat;
	private Byte shelveFlag;
	
	/**
	 * 0:未删除，1：删除
	 */
	private Byte deleted;
	private Integer sort;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	private Long createUserId;

	/**
	 * 更新时间
	 */
	private Date lastUpdateDate;
	private Long lastUpdateUserId;
	
	@Field(type = FieldType.text)
	private String moduleEnum;
	
	@Field(type = FieldType.text)
	private String tenantId;
	
	private Integer revision;
	
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
	
	private Date masterLastViewTime;
	
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

	public Date getLastInfoTime() {
		return lastInfoTime;
	}

	public void setLastInfoTime(Date lastInfoTime) {
		this.lastInfoTime = lastInfoTime;
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

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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

	@Override
	public String toString() {
		return "CoterieInfo [kid=" + kid + ", coterieName=" + coterieName + ", icon=" + icon + ", intro=" + intro
				+ ", qrUrl=" + qrUrl + ", ownerId=" + ownerId + ", ownerName=" + ownerName + ", ownerIntro="
				+ ownerIntro + ", joinFee=" + joinFee + ", consultingFee=" + consultingFee + ", joinCheck=" + joinCheck
				+ ", memberNum=" + memberNum + ", state=" + state + ", processTime=" + processTime + ", auditUserId="
				+ auditUserId + ", auditRemark=" + auditRemark + ", heat=" + heat + ", shelveFlag=" + shelveFlag
				+ ", deleted=" + deleted + ", sort=" + sort + ", createDate=" + createDate + ", createUserId="
				+ createUserId + ", lastUpdateDate=" + lastUpdateDate + ", lastUpdateUserId=" + lastUpdateUserId
				+ ", moduleEnum=" + moduleEnum + ", tenantId=" + tenantId + ", revision=" + revision + ", isExpert="
				+ isExpert + ", recommend=" + recommend + ", lastInfoTime=" + lastInfoTime + ", masterLastViewTime="
				+ masterLastViewTime + "]";
	}
}