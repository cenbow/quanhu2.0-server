package com.yryz.quanhu.dymaic.canal.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * 用户搜索实体
 * 
 * @author jk
 */
@Document(indexName = "quanhu-v2-userinfo", type = "userInfo", refreshInterval = "-1")
public class UserInfo implements Serializable {
	private static final long serialVersionUID = -2312110729335920029L;

	@Id
	@JsonSerialize(using = ToStringSerializer.class)
	private Long userId;

	private UserBaseInfo userBaseInfo;

	/**
	 * 用户标签信息
	 */
	private UserTagInfo userTagInfo;
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public UserTagInfo getUserTagInfo() {
		return userTagInfo;
	}

	public void setUserTagInfo(UserTagInfo userTagInfo) {
		this.userTagInfo = userTagInfo;
	}

	public UserBaseInfo getUserBaseInfo() {
		return userBaseInfo;
	}
	public void setUserBaseInfo(UserBaseInfo userBaseInfo) {
		this.userBaseInfo = userBaseInfo;
	}
	
}
