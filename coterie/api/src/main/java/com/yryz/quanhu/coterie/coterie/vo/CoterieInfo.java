package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.Date;

import com.yryz.common.constant.ModuleContants;
import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:34
 */
public class CoterieInfo implements Serializable{
	private static final long serialVersionUID = 2137320647778802349L;

	@ApiModelProperty("私圈id")
    private Long coterieId;

	@ApiModelProperty("用户ID")
    private String ownerId;
    
	@ApiModelProperty("用户图标")
    private String custIcon;
    
	@ApiModelProperty("电话")
    private String phone;

	@ApiModelProperty("姓名")
	private String ownerName;

	@ApiModelProperty("个人简介")
    private String ownerIntro;

	@ApiModelProperty("私圈名称")
	private String name;
	
	@ApiModelProperty("封面图")
	private String icon;

	@ApiModelProperty("圈子简介")
	private String intro;

	@ApiModelProperty("加入私圈金额(悠然币)，0表示免费")
	private Integer joinFee;

	@ApiModelProperty("咨询费，0表示免费")
	private Integer consultingFee;

	@ApiModelProperty("成员加入是否需要审核（10不审核，11审核）")
	private Integer joinCheck;

	@ApiModelProperty("成员数量")
	private Integer memberNum;

	@ApiModelProperty("状态：10待审核，11审批通过，12审批未通过")
	private Byte status;
	
	@ApiModelProperty("审批时间")
	private Date processTime;

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

	@ApiModelProperty("预留字段")
	private Byte isExpert;

	@ApiModelProperty("预留字段")
	private Byte recommend;

	@ApiModelProperty("圈主最后访问时间")
	private Date masterLastViewTime;
	
	@ApiModelProperty("审批人")
	private Integer auditUserId;
	private String auditCreator;

	@ApiModelProperty("审批备注")
	private String auditRemark;

	@ApiModelProperty("红点10:显示，11:不显示")
	private Integer redDot;
	
	@ApiModelProperty("最大成员数")
	private Integer maxMemberNum=2000;

	@ApiModelProperty("圈主信息")
    private  User  user;

	/**
	 * 私圈的功能枚举
	 */
	private String moduleEnum = ModuleContants.COTERIE;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getOwnerIntro() {
		return ownerIntro;
	}

	public void setOwnerIntro(String ownerIntro) {
		this.ownerIntro = ownerIntro;
	}

	public String getCustIcon() {
		return custIcon;
	}

	public void setCustIcon(String custIcon) {
		this.custIcon = custIcon;
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

	public Integer getRedDot() {
		return redDot;
	}

	public void setRedDot(Integer redDot) {
		this.redDot = redDot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getMaxMemberNum() {
		return maxMemberNum;
	}

	public void setMaxMemberNum(Integer maxMemberNum) {
		this.maxMemberNum = maxMemberNum;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAuditCreator() {
		return auditCreator;
	}

	public void setAuditCreator(String auditCreator) {
		this.auditCreator = auditCreator;
	}

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
}

