package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:50:23
 */
public class CoterieBasicInfo implements Serializable{
	private static final long serialVersionUID = 2264458786258618711L;
	@ApiModelProperty("私圈id,修改私圈信息的时候传")
    private Long coterieId;
    
	@ApiModelProperty("圈主用户ID")
    private String ownerId;

	@ApiModelProperty("封面图")
    private String icon;

	@ApiModelProperty("圈子名称")
    private String name;

	@ApiModelProperty("圈子简介")
    private String intro;
    
	@ApiModelProperty("加入私圈 收费金额 0表示免费")
    private Integer joinFee;
    
	@ApiModelProperty("成员加入是否需要审核10不审核，11审核")
    private Integer joinCheck;
    
	@ApiModelProperty("咨询费，0表示免费")
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
