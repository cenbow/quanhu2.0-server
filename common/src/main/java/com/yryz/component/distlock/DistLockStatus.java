package com.yryz.component.distlock;

public enum DistLockStatus {

	Trying("trying", "尝试获取锁"),

	Locked("locked", "成功获取锁"),

	Error("error", "获取锁失败");

	private String status;

	private String statusDesp;

	DistLockStatus(String status, String statusDesp) {
		this.status = status;
		this.statusDesp = statusDesp;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesp() {
		return statusDesp;
	}

	public void setStatusDesp(String statusDesp) {
		this.statusDesp = statusDesp;
	}
}
