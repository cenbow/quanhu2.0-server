package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

public interface CoterieInfoSearch {
	List<CoterieInfo> search(String keyWord,Integer page,Integer size);
}
