package com.yryz.quanhu.dymaic.canal.entity;

public class ResourceHeat {
	//资源ID
	private Long kid;
	//资源最终热度值
	private Long lastHeat;
	
	public Long getKid() {
		return kid;
	}
	public void setKid(Long kid) {
		this.kid = kid;
	}
	public Long getLastHeat() {
		return lastHeat;
	}
	public void setLastHeat(Long lastHeat) {
		this.lastHeat = lastHeat;
	}
}
