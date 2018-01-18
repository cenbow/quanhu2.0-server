package com.yryz.quanhu.dymaic.canal.entity;

/**
 *	字段的变更信息
 * @author jk
 */
public class CanalChangeInfo {
	//字段的名称
    private String name;
    //字段的值
    private String value;
    //该字段是否有变更
    private Object update;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getUpdate() {
		return update;
	}

	public void setUpdate(Object update) {
		this.update = update;
	}
    
}
