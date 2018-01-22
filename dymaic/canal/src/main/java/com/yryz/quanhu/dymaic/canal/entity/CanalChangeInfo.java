package com.yryz.quanhu.dymaic.canal.entity;

import java.util.List;
import java.util.Map;

import org.springframework.util.CollectionUtils;

import com.google.common.collect.Maps;

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
    private Boolean update;

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

	public Boolean getUpdate() {
		return update;
	}

	public void setUpdate(Boolean update) {
		this.update = update;
	}
	
	public static Map<String, CanalChangeInfo> toMap(List<CanalChangeInfo> list) {
		Map<String, CanalChangeInfo> infoMap = Maps.newHashMap();
		if (CollectionUtils.isEmpty(list)) {
			return infoMap;
		}
		for (int i = 0; i < list.size(); i++) {
			CanalChangeInfo changeInfo = list.get(i);
			infoMap.put(changeInfo.getName().toLowerCase().replace("_", "").replace("-", ""), changeInfo);
		}
		return infoMap;
	}
}
