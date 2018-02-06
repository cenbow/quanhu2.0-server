/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: ActivityThreeBody.java, 2017年9月12日 下午4:53:33 yehao
 */
package com.yryz.common.message;

/**
 * @author yehao
 * @version 1.0
 * @date 2017年9月12日 下午4:53:33
 * @Description 活动与推荐扩展展示3
 */
public class ActivityCoterieBody implements Body{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6783353201229424226L;
	
	/**
	 * 圈子ID
	 */
	private String circleId;
	/**
	 * 圈子名称
	 */
	private String circleName;
	
	/**
	 * 私圈ID
	 */
	private String coterieId;
	
	/**
	 * 私圈名称
	 */
	private String coterieName;
	
	/**
	 * 圈主名
	 */
	private String ownerName;
	
	/**
	 * 个人简介
	 */
	private String ownerIntro;
	
	/**
	 * 私圈简介
	 */
	private String intro;

	/**
	 * 加入私圈价格
	 */
	private Integer joinFee;
	
	/**
	 * 私圈成员数
	 */
	private Integer memberNum;

	/**
	 * 功能id
	 */
	private String moduleEnum;

	public String getModuleEnum() {
		return moduleEnum;
	}

	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}

	/**
	 * 
	 * @exception 
	 */
	public ActivityCoterieBody() {
		super();
	}

	/**
	 * @param circleId
	 * @param circleName
	 * @param coterieId
	 * @param coterieName
	 * @param ownerName
	 * @param intro
	 * @param joinFee
	 * @param memberNum
	 * @exception 
	 */
	public ActivityCoterieBody(String circleId, String circleName, String coterieId, String coterieName, String ownerName,
			String intro, Integer joinFee, Integer memberNum) {
		super();
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.ownerName = ownerName;
		this.intro = intro;
		this.joinFee = joinFee;
		this.memberNum = memberNum;
	}

	public ActivityCoterieBody(String circleId, String circleName, String coterieId, String coterieName, String ownerName, String ownerIntro, String intro, Integer joinFee, Integer memberNum, String moduleEnum) {
		this.circleId = circleId;
		this.circleName = circleName;
		this.coterieId = coterieId;
		this.coterieName = coterieName;
		this.ownerName = ownerName;
		this.ownerIntro = ownerIntro;
		this.intro = intro;
		this.joinFee = joinFee;
		this.memberNum = memberNum;
		this.moduleEnum = moduleEnum;
	}

	/**
	 * @return the circleId
	 */
	public String getCircleId() {
		return circleId;
	}

	/**
	 * @param circleId the circleId to set
	 */
	public void setCircleId(String circleId) {
		this.circleId = circleId;
	}

	/**
	 * @return the circleName
	 */
	public String getCircleName() {
		return circleName;
	}

	/**
	 * @param circleName the circleName to set
	 */
	public void setCircleName(String circleName) {
		this.circleName = circleName;
	}

	/**
	 * @return the coterieId
	 */
	public String getCoterieId() {
		return coterieId;
	}

	/**
	 * @param coterieId the coterieId to set
	 */
	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	/**
	 * @return the coterieName
	 */
	public String getCoterieName() {
		return coterieName;
	}

	/**
	 * @param coterieName the coterieName to set
	 */
	public void setCoterieName(String coterieName) {
		this.coterieName = coterieName;
	}

	/**
	 * @return the ownerName
	 */
	public String getOwnerName() {
		return ownerName;
	}

	/**
	 * @param ownerName the ownerName to set
	 */
	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
	
	/**
	 * @return the ownerIntro
	 */
	public String getOwnerIntro() {
		return ownerIntro;
	}

	/**
	 * @param ownerIntro the ownerIntro to set
	 */
	public void setOwnerIntro(String ownerIntro) {
		this.ownerIntro = ownerIntro;
	}

	/**
	 * @return the intro
	 */
	public String getIntro() {
		return intro;
	}

	/**
	 * @param intro the intro to set
	 */
	public void setIntro(String intro) {
		this.intro = intro;
	}

	/**
	 * @return the joinFee
	 */
	public Integer getJoinFee() {
		return joinFee;
	}

	/**
	 * @param joinFee the joinFee to set
	 */
	public void setJoinFee(Integer joinFee) {
		this.joinFee = joinFee;
	}

	/**
	 * @return the memberNum
	 */
	public Integer getMemberNum() {
		return memberNum;
	}

	/**
	 * @param memberNum the memberNum to set
	 */
	public void setMemberNum(Integer memberNum) {
		this.memberNum = memberNum;
	}

	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
