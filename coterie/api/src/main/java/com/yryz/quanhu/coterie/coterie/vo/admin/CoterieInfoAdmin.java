package com.yryz.quanhu.coterie.coterie.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieInfoAdmin implements Serializable{

	/**
	 * kid
	 */
	@ApiModelProperty("私圈ID")
	private Long coterieId;

	/**
	 * sql coterie_name
	 */
	@ApiModelProperty("私圈名称")
	private String name;

	@ApiModelProperty("封面图")
	private String icon;

	@ApiModelProperty("私圈简介")
	private String intro;

	@ApiModelProperty("圈主ID")
	private String ownerId;

	@ApiModelProperty("加入私圈金额(悠然币)，0表示免费")
	private Integer joinFee;

	@ApiModelProperty("咨询费，0表示免费")
	private Integer consultingFee;

	@ApiModelProperty("成员加入是否需要审核（10不审核，11审核）")
	private Integer joinCheck;

	@ApiModelProperty("成员数量")
	private Integer memberNum;

	/**
	 * sql state
	 */
	@ApiModelProperty("状态：10待审核，11审批通过，12审批未通过")
	private Byte status;

	@ApiModelProperty("审批时间")
	private Date processTime;

	@ApiModelProperty("审批人")
	private Integer auditUserId;

	@ApiModelProperty("审批备注")
	private String auditRemark;

	@ApiModelProperty("热度值")
	private Long heat;

	@ApiModelProperty("上下架状态10：上架,11：下架")
	private Integer shelveFlag;

	@ApiModelProperty("删除状态10：未删除,11：删除")
	private Byte deleted;

	@ApiModelProperty("创建时间")
	private Date createDate;

	@ApiModelProperty("更新时间")
	private Date lastUpdateDate;

	@ApiModelProperty("版本号")
	private Integer revision;

	@ApiModelProperty("是否达人 10 否, 11 是")
	private Byte isExpert;

	@ApiModelProperty("预留字段")
	private Byte recommend;

	@ApiModelProperty("圈主最后访问时间")
	private Date masterLastViewTime;

	@ApiModelProperty("红点 10:显示，11:不显示")
	private Integer redDot;

	@ApiModelProperty("历史私圈ID")
	private String historyCoterieId;

	@ApiModelProperty("最大成员数")
	private Integer maxMemberNum = 2000;

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public Date getProcessTime() {
		return processTime;
	}

	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
	}

	public Integer getAuditUserId() {
		return auditUserId;
	}

	public void setAuditUserId(Integer auditUserId) {
		this.auditUserId = auditUserId;
	}

	public String getAuditRemark() {
		return auditRemark;
	}

	public void setAuditRemark(String auditRemark) {
		this.auditRemark = auditRemark;
	}

	public Long getHeat() {
		return heat;
	}

	public void setHeat(Long heat) {
		this.heat = heat;
	}

	public Integer getShelveFlag() {
		return shelveFlag;
	}

	public void setShelveFlag(Integer shelveFlag) {
		this.shelveFlag = shelveFlag;
	}

	public Byte getDeleted() {
		return deleted;
	}

	public void setDeleted(Byte deleted) {
		this.deleted = deleted;
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

	public Integer getRevision() {
		return revision;
	}

	public void setRevision(Integer revision) {
		this.revision = revision;
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

	public Date getMasterLastViewTime() {
		return masterLastViewTime;
	}

	public void setMasterLastViewTime(Date masterLastViewTime) {
		this.masterLastViewTime = masterLastViewTime;
	}

	public Integer getRedDot() {
		return redDot;
	}

	public void setRedDot(Integer redDot) {
		this.redDot = redDot;
	}

	public String getHistoryCoterieId() {
		return historyCoterieId;
	}

	public void setHistoryCoterieId(String historyCoterieId) {
		this.historyCoterieId = historyCoterieId;
	}

	public Integer getMaxMemberNum() {
		return maxMemberNum;
	}

	public void setMaxMemberNum(Integer maxMemberNum) {
		this.maxMemberNum = maxMemberNum;
	}
}
