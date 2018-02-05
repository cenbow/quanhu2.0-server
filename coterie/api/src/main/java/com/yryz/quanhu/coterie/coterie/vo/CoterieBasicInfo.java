package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:23
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class CoterieBasicInfo implements Serializable{
	private static final long serialVersionUID = 2264458786258618711L;
	/**
     * 私圈id,设置私圈信息的时候传
     */
    private Long coterieId;
    
	/**
     * 用户ID
     */
    private String ownerId;

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
     * 加入私圈 收费金额 0表示免费
     */
    private Integer joinFee;
    
    /**
     * 加入私圈 收费需要审核 0不审核，1审核
     */
    private Integer joinCheck;
    
    /**
     * 咨询费，0表示免费
     */
    private Integer consultingFee;
    
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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

	public Integer getJoinFee() {
		return joinFee;
	}

	public void setJoinFee(Integer joinFee) {
		this.joinFee = joinFee;
	}

	public Integer getJoinCheck() {
		return joinCheck;
	}

	public void setJoinCheck(Integer joinCheck) {
		this.joinCheck = joinCheck;
	}

	public Long getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(Long coterieId) {
		this.coterieId = coterieId;
	}

	public Integer getConsultingFee() {
		return consultingFee;
	}

	public void setConsultingFee(Integer consultingFee) {
		this.consultingFee = consultingFee;
	}

	@Override
	public String toString() {
		return "CoterieBasicInfo [coterieId=" + coterieId + ", ownerId=" + ownerId + ", icon=" + icon + ", name=" + name
				+ ", intro=" + intro + ", joinFee=" + joinFee + ", joinCheck=" + joinCheck + ", consultingFee="
				+ consultingFee + "]";
	}
}
