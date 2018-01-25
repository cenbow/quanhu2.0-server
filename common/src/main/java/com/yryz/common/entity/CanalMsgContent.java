package com.yryz.common.entity;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

/**
 * Canal 内容
 * 
 * @author jk
 */
public class CanalMsgContent {
	// 数据库名称 小写
	private String dbName;
	// 表名称 小写
	private String tableName;
	// 事件类型三种("update","delete","insert")
	private String eventType;
	// 变更前的数据
	private List<CanalChangeInfo> dataBefore;
	// 变更后的数据
	private List<CanalChangeInfo> dataAfter;

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public List<CanalChangeInfo> getDataAfter() {
		return dataAfter;
	}

	public void setDataAfter(List<CanalChangeInfo> dataAfter) {
		this.dataAfter = dataAfter;
	}

	public List<CanalChangeInfo> getDataBefore() {
		return dataBefore;
	}

	public void setDataBefore(List<CanalChangeInfo> dataBefore) {
		this.dataBefore = dataBefore;
	}

}
