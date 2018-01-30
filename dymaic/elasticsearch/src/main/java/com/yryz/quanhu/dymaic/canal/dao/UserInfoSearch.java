package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.UserInfo;

/**
 * 复杂的查询  用ElasticsearchTemplate
 * @author jk
 * @param <UserSimpleVo>
 *
 */
public interface UserInfoSearch {
	List<UserInfo> search(String keyWord,Integer page,Integer size);


	/**
	 * 达人搜索
	 * @param tagId
	 * @param userId
	 *@param pageNo
	 * @param pageSize   @return
	 */
	List<UserInfo> searchStarUser(Long tagId, Long userId, Integer pageNo, Integer pageSize);
}
