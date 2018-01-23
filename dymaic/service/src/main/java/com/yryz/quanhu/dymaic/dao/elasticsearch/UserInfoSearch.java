package com.yryz.quanhu.dymaic.dao.elasticsearch;

import java.util.List;
import java.util.Map;

import com.yryz.quanhu.dymaic.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVO;

/**
 * 复杂的查询  用ElasticsearchTemplate
 * @author jk
 * @param <UserSimpleVO>
 *
 */
public interface UserInfoSearch {
	List<UserInfo> search(String keyWord,Integer page,Integer size);
}
