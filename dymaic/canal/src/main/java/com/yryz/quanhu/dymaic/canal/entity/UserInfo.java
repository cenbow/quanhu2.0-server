package com.yryz.quanhu.dymaic.canal.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import com.google.common.collect.Maps;

/**
 * 用户搜索实体
 * 
 * @author jk
 */
@Document(indexName = "jk-search-test", type = "user", refreshInterval = "-1")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = -2312110729335920029L;

	@Id
	private Integer id;

	private Integer username;

	private String realName;

	private Integer status;

	private String createTime;

	private String updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUsername() {
		return username;
	}

	public void setUsername(Integer username) {
		this.username = username;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", realName=" + realName + ", status=" + status
				+ ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}
	
	public static UserInfo parse(List<CanalChangeInfo> changeInfoList) {
		Map<String,CanalChangeInfo> infoMap=CanalChangeInfo.toMap(changeInfoList);
		// TODO 需要根据实际表结构处理
		CanalChangeInfo idColumn = infoMap.get("id");
		CanalChangeInfo realNameColumn = infoMap.get("company");
		UserInfo userInfo = new UserInfo();
		if (idColumn != null) {
			userInfo.setId(Integer.valueOf(idColumn.getValue()));
		}
		if (realNameColumn != null) {
			userInfo.setRealName(realNameColumn.getValue());
		}
		return userInfo;
	}
	
	public static UserInfo parse(UserInfo uinfo,List<CanalChangeInfo> changeInfoList){
		UserInfo userInfo=new UserInfo();
		if(uinfo!=null){
			BeanUtils.copyProperties(uinfo, userInfo);
		}
		
		Map<String,CanalChangeInfo> infoMap=CanalChangeInfo.toMap(changeInfoList);
		//TODO 需要根据实际表结构处理
		CanalChangeInfo idColumn = infoMap.get("id");
		CanalChangeInfo realNameColumn = infoMap.get("company");
		if (idColumn != null) {
			userInfo.setId(Integer.valueOf(idColumn.getValue()));
		}
		if (realNameColumn != null && realNameColumn.getUpdate()) {
			userInfo.setRealName(realNameColumn.getValue());
		}
		return userInfo;
	}
}
