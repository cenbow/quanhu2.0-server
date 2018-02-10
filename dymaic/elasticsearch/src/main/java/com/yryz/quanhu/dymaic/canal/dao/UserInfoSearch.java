package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.StarInfoDTO;
import com.yryz.quanhu.user.vo.UserInfoVO;

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
	PageList<UserInfoVO> adminSearchUser(AdminUserInfoDTO adminUserDTO);

	/**
	 * 管理后台达人搜索
	 * @param adminUserDTO
	 * @return
	 */
	PageList<UserInfoVO> searchStarUserForAdmin(AdminUserInfoDTO adminUserDTO);
}
