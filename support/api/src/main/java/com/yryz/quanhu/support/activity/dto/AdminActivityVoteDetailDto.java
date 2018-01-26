package com.yryz.quanhu.support.activity.dto;



import com.yryz.quanhu.support.activity.entity.ActivityVoteDetail;

import java.util.Date;
import java.util.List;

public class AdminActivityVoteDetailDto extends ActivityVoteDetail {
	
	private String queryCondition;
	private Integer startVote;
	private Integer endVote;
	private String phone;
	private String title;
	private String type;
	private Integer pageNo = 1;
	private Integer pageSize = 10;
    private Date beginCreateDate;	// 开始 参与时间   
  	private Date endCreateDate;		// 结束  参与时间
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(Date beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public Date getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(Date endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	/**
	 * @return the pageNo
	 */
	public Integer getPageNo() {
		return pageNo;
	}

	/**
	 * @param pageNo the pageNo to set
	 */
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	/**
	 * @return the pageSize
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	private String nickName;

	private List<String> custIds;

	public List<String> getCustIds() {
		return custIds;
	}

	public void setCustIds(List<String> custIds) {
		this.custIds = custIds;
	}


	public String getQueryCondition() {
		return queryCondition;
	}

	public void setQueryCondition(String queryCondition) {
		this.queryCondition = queryCondition;
	}


	public Integer getStartVote() {
		return startVote;
	}

	public void setStartVote(Integer startVote) {
		this.startVote = startVote;
	}

	public Integer getEndVote() {
		return endVote;
	}

	public void setEndVote(Integer endVote) {
		this.endVote = endVote;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
