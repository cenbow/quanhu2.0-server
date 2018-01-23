package com.yryz.quanhu.dymaic.service;

import java.util.List;

import com.yryz.quanhu.dymaic.vo.UserSimpleVO;

public interface ElasticsearchService {
	List<UserSimpleVO> searchUser(String keyWord);
}
