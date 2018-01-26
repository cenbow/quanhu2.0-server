package com.yryz.quanhu.support.activity.vo;


import com.yryz.quanhu.support.activity.entity.ActivityRecord;

import java.util.List;
import java.util.Map;


public class AdminActivityRecordVo extends ActivityRecord {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7392174853951271535L;
	/**
	 * 用户昵称
	 */
	private String nickName;
	/**
	 * 用户手机号码
	 */
	private String custPhone;
	
	private Integer pageNo=1;
	
	private Integer pageSize=10;
	
	private List<Map<String,String>> map;
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
    
	
	
	/**
	 * @return the map
	 */
	public List<Map<String,String>> getMap() {
		return map;
	}

	/**
	 * @param map the map to set
	 */
	public void setMap(List<Map<String,String>> map) {
		this.map = map;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getCustPhone() {
		return custPhone;
	}

	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
}
