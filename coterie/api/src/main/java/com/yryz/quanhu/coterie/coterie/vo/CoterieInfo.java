package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:34
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@SuppressWarnings("serial")
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
     * 圈子名称
     */
    private String circleName;

    /**
     * 姓名
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
     * 状态：0待审核，1审批通过，2审批未通过，3上架，4下架
     */
    private Byte status;

    /**
     * 创建时间
     */
    private Date createDate;
    
    /**
     * 新成员数量
     */
    private Integer newMemberNum;
    /**
     * 私圈最大能拥有的成员数
     */
    private Integer maxMemberNum=500;
    
    /**
     * 圈子工程名（路由用）
     */
    private String circleRoute;
    
    /**
     * 是否达人，0否，1是
     */
    private String isExpert;
    
    /**
	 * 热度值
	 */
	private Long heat;
	
	/**
	 * 私圈文章最新发布时间
	 */
	private Date lastInfoTime;

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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntro() {
		return intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	public String getQrUrl() {
		return qrUrl;
	}

	public void setQrUrl(String qrUrl) {
		this.qrUrl = qrUrl;
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

	public Integer getNewMemberNum() {
		return newMemberNum;
	}

	public void setNewMemberNum(Integer newMemberNum) {
		this.newMemberNum = newMemberNum;
	}

	public String getCustIcon() {
		return custIcon;
	}

	public void setCustIcon(String custIcon) {
		this.custIcon = custIcon;
	}

	public Integer getMaxMemberNum() {
		return maxMemberNum;
	}

	public void setMaxMemberNum(Integer maxMemberNum) {
		this.maxMemberNum = maxMemberNum;
	}

	public String getCircleName() {
		return circleName;
	}

	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	public String getCircleRoute() {
		return circleRoute;
	}

	public void setCircleRoute(String circleRoute) {
		this.circleRoute = circleRoute;
	}

	public String getIsExpert() {
		return isExpert;
	}

	public void setIsExpert(String isExpert) {
		this.isExpert = isExpert;
	}

	public Long getHeat() {
		return heat;
	}

	public void setHeat(Long heat) {
		this.heat = heat;
	}

	public Date getLastInfoTime() {
		return lastInfoTime;
	}

	public void setLastInfoTime(Date lastInfoTime) {
		this.lastInfoTime = lastInfoTime;
	}

	@Override
	public String toString() {
		return "CoterieInfo [coterieId=" + coterieId + ", ownerId=" + ownerId + ", custIcon="   + ", circleName=" + circleName + ", ownerName=" + ownerName + ", ownerIntro=" + ownerIntro
				+ ", icon=" + icon + ", name=" + name + ", intro=" + intro + ", qrUrl=" + qrUrl + ", joinFee=" + joinFee
				+ ", consultingFee=" + consultingFee + ", joinCheck=" + joinCheck + ", memberNum=" + memberNum
				+ ", status=" + status + ", createDate=" + createDate + ", newMemberNum=" + newMemberNum
				+ ", maxMemberNum=" + maxMemberNum + ", circleRoute=" + circleRoute + ", isExpert=" + isExpert
				+ ", heat=" + heat + ", lastInfoTime=" + lastInfoTime + "]";
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
