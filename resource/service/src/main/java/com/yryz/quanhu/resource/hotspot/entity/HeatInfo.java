/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月25日
 * Id: HeatInfo.java, 2018年1月25日 下午1:42:28 yehao
 */
package com.yryz.quanhu.resource.hotspot.entity;

import java.util.Date;

import com.yryz.quanhu.resource.hotspot.enums.HeatInfoEnum;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月25日 下午1:42:28
 * @Description 保存热度
 */
public class HeatInfo {
	
	/**
	 * 对象
	 */
	private String objectId;
	
	/**
	 * 1,资源；2，人
	 */
	private String type;
	
	/**
	 * 初始热度
	 */
	private Long initHeat;
	
	/**
	 * 行为热度
	 */
	private Long behaviorHeat;
	
	/**
	 * 衰减
	 */
	private Long attenuation;
	
	/**
	 * 热度总额
	 */
	private Long heat;
	
	/**
	 * 创建时间
	 */
	private Long createTime;

	/**
	 * 更新时间
	 */
	private Long updateTime;
	
	/**
	 * 
	 * @exception 
	 */
	public HeatInfo() {
		super();
	}

	/**
	 * @return the objectId
	 */
	public String getObjectId() {
		return objectId;
	}

	/**
	 * @param objectId the objectId to set
	 */
	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * @return the initHeat
	 */
	public Long getInitHeat() {
		return initHeat;
	}

	/**
	 * @param initHeat the initHeat to set
	 */
	public void setInitHeat(Long initHeat) {
		this.initHeat = initHeat;
	}
	
	/**
	 * @return the behaviorHeat
	 */
	public Long getBehaviorHeat() {
		return behaviorHeat;
	}

	/**
	 * @param behaviorHeat the behaviorHeat to set
	 */
	public void setBehaviorHeat(Long behaviorHeat) {
		this.behaviorHeat = behaviorHeat;
	}

	/**
	 * @return the attenuation
	 */
	public Long getAttenuation() {
		return attenuation;
	}

	/**
	 * @param attenuation the attenuation to set
	 */
	public void setAttenuation(Long attenuation) {
		this.attenuation = attenuation;
	}
	
	/**
	 * @return the createTime
	 */
	public Long getCreateTime() {
		return createTime;
	}

	/**
	 * @param createTime the createTime to set
	 */
	public void setCreateTime(Long createTime) {
		this.createTime = createTime;
	}

	/**
	 * @return the updateTime
	 */
	public Long getUpdateTime() {
		return updateTime;
	}

	/**
	 * @param updateTime the updateTime to set
	 */
	public void setUpdateTime(Long updateTime) {
		this.updateTime = updateTime;
	}

	/**
	 * @return the heat
	 */
	public Long getHeat() {
		return heat;
	}

	/**
	 * @param heat the heat to set
	 */
	public void setHeat(Long heat) {
		this.heat = heat;
	}
	
	public Long countCommonHeat(){
		attenuation = 100L * getCount();
		return initHeat + behaviorHeat - attenuation;
	}
	
	/**
	 * 检查是否使用通用热度
	 * @return
	 */
	private boolean check(){
		long count = System.currentTimeMillis() - createTime;
		if(count > HeatInfoEnum.N * 24 * 60 * 60 * 1000){
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 统计热度
	 * @return
	 */
	public Long countHeat(){
		//用户只走通用类型
		if(HeatInfoEnum.HEAT_TYPE_USER.equals(this.type)){
			return countCommonHeat();
		//资源走两种规则，按天数来
		} else {
			if(check()){
				return countExponentHeat();
			} else {
				return countCommonHeat();
			}
		}
	}
	
	/**
	 * 获取到N
	 * @return
	 */
	private int getCount(){
		long count = System.currentTimeMillis() - createTime;
		return (int)(count/(24 * 60 * 60 * 1000));
	}
	
	public Long countExponentHeat(){
		return (long)((initHeat + behaviorHeat)/Math.pow(getCount(), 1.5));
	}
	
}
