/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.yryz.quanhu.message.im.entity;

import java.util.Date;
import java.util.Set;

/**
 * 关系信息 实体类
 * @author Pxie
 * @version 2016-08-17
 */
public class RelationshipModel {
	private int id;
	private String aCustId;		// A用户id
	private String bCustId;		// B用户id
	private Date createTime;
	private Integer type;		// 好友类型 1:好友 2：黑名单 3:互为好友
	private String nameNotes;   // 好友备注
	
	/* extends */
	private Set<String> bCustIdSet;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getaCustId() {
		return aCustId;
	}
	public void setaCustId(String aCustId) {
		this.aCustId = aCustId;
	}
	public String getbCustId() {
		return bCustId;
	}
	public void setbCustId(String bCustId) {
		this.bCustId = bCustId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getNameNotes() {
		return nameNotes;
	}
	public void setNameNotes(String nameNotes) {
		this.nameNotes = nameNotes;
	}
	public Set<String> getbCustIdSet() {
		return bCustIdSet;
	}
	public void setbCustIdSet(Set<String> bCustIdSet) {
		this.bCustIdSet = bCustIdSet;
	}

	
}