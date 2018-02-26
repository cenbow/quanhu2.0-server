package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
import java.util.List;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:51:00
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
@SuppressWarnings("serial")
public class CoterieSearchParam implements Serializable{
	private static final long serialVersionUID = 7520811012527140155L;

	/**
	 * 审核状态 ：10待审核，11审批通过，12审批未通过
	 */
	private Byte status;

	/**
	 * 上下架状态 ：10上架，11下架
	 */
	private Byte shelveFlag;

	/**
	 * 是否推荐，10否，11是
	 */
	private Byte recommend;
	
	/**
	 * 手机号
	 */
	private String phone;

	/**
	 * 私圈名称
	 */
	private String name;
	
	/**
	 * 圈主名称
	 */
	@Deprecated
	private String ownerName;

	/**
	 * 圈主ID
	 */
	private String ownerId;
	
	/**
	 * 申请开时间   起 ,格式yyyy-MM-dd
	 */
	private String startTime;
	
	/**
	 * 申请时间 止,格式yyyy-MM-dd
	 */
	private String endTime;

	/**
	 * 页码 ，默认为1
	 */
	private Integer pageNum;
	
	/**
	 * 每页记录数，默认为10
	 */
	private Integer pageSize;
	
	/**
	 * 值：字段  默认 create_date
	 */
	private String sortValue = "create_date";

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortValue() {
		return sortValue;
	}

	public void setSortValue(String sortValue) {
		this.sortValue = sortValue;
	}

	public Byte getRecommend() {
		return recommend;
	}

	public void setRecommend(Byte recommend) {
		this.recommend = recommend;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Byte getShelveFlag() {
		return shelveFlag;
	}

	public void setShelveFlag(Byte shelveFlag) {
		this.shelveFlag = shelveFlag;
	}
}
