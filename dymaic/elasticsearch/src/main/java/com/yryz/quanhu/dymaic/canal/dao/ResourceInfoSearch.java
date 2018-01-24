package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;

public interface ResourceInfoSearch {
	List<ResourceInfo> searchTopic(String keyWord,Integer page,Integer size);
}
