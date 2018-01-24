package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;

public interface ResourceInfoSearch {
	List<ResourceInfo> searchTopicInfo(String keyWord,Integer page,Integer size);
	List<ResourceInfo> searchReleaseInfo(String keyWord,Integer page,Integer size);
}
