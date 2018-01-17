package com.yryz.quanhu.dymaic.canal.entity;

import java.util.List;

/**
 * Canal 内容
 * 
 * @author jk
 */
public class CanalMsgContent {

	private String binLogFile;
	private Long binlogOffset;
	private String dbName;
	private String tableName;
	private String eventType;
	private List<CanalChangeInfo> dataBefore;
	private List<CanalChangeInfo> dataAfter;

	public String getBinLogFile() {
		return binLogFile;
	}

	public void setBinLogFile(String binLogFile) {
		this.binLogFile = binLogFile;
	}

	public Long getBinlogOffset() {
		return binlogOffset;
	}

	public void setBinlogOffset(Long binlogOffset) {
		this.binlogOffset = binlogOffset;
	}

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

	public List<CanalChangeInfo> getDataBefore() {
		return dataBefore;
	}

	public void setDataBefore(List<CanalChangeInfo> dataBefore) {
		this.dataBefore = dataBefore;
	}

	public List<CanalChangeInfo> getDataAfter() {
		return dataAfter;
	}

	public void setDataAfter(List<CanalChangeInfo> dataAfter) {
		this.dataAfter = dataAfter;
	}

}
