package com.yryz.quanhu.coterie.coterie.vo;

import java.io.Serializable;
/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:51:37
 * @Description TODO (这里用一句话描述这个方法的作用)
 */
public class MemberSearchParam  implements Serializable{
	private static final long serialVersionUID = -7308160348168244062L;
	
	/**
	 * 私圈ID
	 */
	private String coterieId;
	
	/**
	 * 加入方式 0免费，1收费
	 */
	private Byte joinType;
	
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

	public String getCoterieId() {
		return coterieId;
	}

	public void setCoterieId(String coterieId) {
		this.coterieId = coterieId;
	}

	public Byte getJoinType() {
		return joinType;
	}

	public void setJoinType(Byte joinType) {
		this.joinType = joinType;
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

	@Override
	public String toString() {
		return "MemberSearchParam [coterieId=" + coterieId + ", joinType=" + joinType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", pageNum=" + pageNum + ", pageSize=" + pageSize + "]";
	}
	
}
