package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;

public interface ReleaseInfoSearch {
	List<ReleaseInfo> search(String keyWord,Integer page,Integer size);
}
