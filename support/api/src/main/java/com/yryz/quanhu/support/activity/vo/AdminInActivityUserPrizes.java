package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;

public class AdminInActivityUserPrizes extends ActivityUserPrizes {
	
	private static final long serialVersionUID = -8368309859190029643L;

	/*活动标题*/
	private String activetyTitle;
	
	/*用户昵称*/
	private String nickName;
	
	/*比较时间开始*/
	private	String beginDate;
	
	/*比较结束时间*/
	private String	endDate;
	
	/*默认分页参数*/
	private Integer pageNo=1;
	
	/*默认分页参数*/
	private Integer pageSize=10;
	
	/*是否分页,默认分页*/
	private	Boolean	page=true;

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getActivetyTitle() {
		return activetyTitle;
	}

	public void setActivetyTitle(String activetyTitle) {
		this.activetyTitle = activetyTitle;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Boolean getPage() {
		return page;
	}

	public void setPage(Boolean page) {
		this.page = page;
	}
}
