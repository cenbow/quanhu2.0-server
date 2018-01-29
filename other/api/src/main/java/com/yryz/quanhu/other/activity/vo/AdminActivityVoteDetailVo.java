package com.yryz.quanhu.other.activity.vo;


import com.yryz.quanhu.other.activity.entity.ActivityVoteDetail;

public class AdminActivityVoteDetailVo extends ActivityVoteDetail {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2745963432641701058L;

	private Integer userRollFlag;
	
	private Integer freeVote;

	private String voteProportion;

	private String userName;
	
	private String userImg;
	
	private String nickName;

	private String url;

	private String phone;

	private Integer totalCount;

	public Integer getUserRollFlag() {
		return userRollFlag;
	}

	public void setUserRollFlag(Integer userRollFlag) {
		this.userRollFlag = userRollFlag;
	}

	public Integer getFreeVote() {
		return freeVote;
	}

	public void setFreeVote(Integer freeVote) {
		this.freeVote = freeVote;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserImg() {
		return userImg;
	}

	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Integer getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	public String getVoteProportion() {
		return voteProportion;
	}

	public void setVoteProportion(String voteProportion) {
		this.voteProportion = voteProportion;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
