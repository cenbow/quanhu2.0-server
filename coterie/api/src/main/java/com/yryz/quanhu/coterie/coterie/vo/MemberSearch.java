package com.yryz.quanhu.coterie.coterie.vo;

/**
 * 成员搜索条件
 * @author jk
 *
 */
public class MemberSearch {
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
	 * 起始index
	 */
	private Integer start;
	
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

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "MemberSearch [coterieId=" + coterieId + ", joinType=" + joinType + ", startTime=" + startTime
				+ ", endTime=" + endTime + ", start=" + start + ", pageSize=" + pageSize + "]";
	}
}
