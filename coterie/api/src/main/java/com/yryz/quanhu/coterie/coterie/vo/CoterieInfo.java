package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:34
 */
public class CoterieInfo implements Serializable{
	private static final long serialVersionUID = 2137320647778802349L;

    /**
     * 私圈id
     */
    private Long coterieId;

    /**
     * 用户ID
     */
    private String ownerId;
    
    /**
     * 用户图标
     */
    private String custIcon;
    /**
     * 姓名
     */
    private String ownerName;

    /**
     * 个人简介
     */
    private String ownerIntro;
    
	/**
	 * 私圈名称
	 */
	private String name;
	
	/**
	 * 封面图
	 */
	private String icon;

	/**
	 * 圈子简介
	 */
	private String intro;

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
	 * 状态：10待审核，11审批通过，12审批未通过
	 */
	private Byte status;
	
	/**
	 * 审批时间
	 */
	private Date processTime;
	
	/**
	 * 热度值
	 */
	private Long heat;
	
	/**
	 * 上下架
	 */
	private Integer shelveFlag;
	
	/**
	 * 0:未删除，1：删除
	 */
	private Byte deleted;
	
	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 更新时间
	 */
	private Date lastUpdateDate;
	
	/**
	 * 版本号
	 */
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
	 * 圈主最后访问时间
	 */
	private Date masterLastViewTime;
	
	private Integer auditUserId;
	private String auditRemark;
	/**
	 * 10:显示，11:不显示
	 */
	private Integer redDot;
	
    private  User  user;
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

	@Override
	public String toString() {
		return "CoterieInfo [coterieId=" + coterieId + ", ownerId=" + ownerId + ", custIcon=" + custIcon
				+ ", ownerName=" + ownerName + ", ownerIntro=" + ownerIntro + ", name=" + name + ", icon=" + icon
				+ ", intro=" + intro + ", joinFee=" + joinFee + ", consultingFee=" + consultingFee + ", joinCheck="
				+ joinCheck + ", memberNum=" + memberNum + ", status=" + status + ", processTime=" + processTime
				+ ", heat=" + heat + ", shelveFlag=" + shelveFlag + ", deleted=" + deleted + ", createDate="
				+ createDate + ", lastUpdateDate=" + lastUpdateDate + ", revision=" + revision + ", isExpert="
				+ isExpert + ", recommend=" + recommend + ", masterLastViewTime=" + masterLastViewTime
				+ ", auditUserId=" + auditUserId + ", auditRemark=" + auditRemark + ", redDot=" + redDot + ", user="
				+ user + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CoterieInfo other = (CoterieInfo) obj;
		if (coterieId == null) {
			if (other.coterieId != null) {
				return false;
			}
		} else if (!coterieId.equals(other.coterieId)) {
			return false;
		}
		
		return true;
	}

}

