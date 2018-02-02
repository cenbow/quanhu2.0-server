package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.StarInfoDTO;

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
	 */
	List<UserInfo> searchStarUser(StarInfoDTO starInfoDTO);

	/**
	 * 管理后台用户搜索
	 * @param adminUserDTO
	 * @return
	 */
	List<UserInfo> adminSearchUser(AdminUserInfoDTO adminUserDTO);
}
