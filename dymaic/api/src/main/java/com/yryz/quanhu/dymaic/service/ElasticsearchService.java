package com.yryz.quanhu.dymaic.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.StarInfoDTO;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserInfoVO;

public interface ElasticsearchService {
	/**
	 * 搜索用户信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	Response<PageList<UserSimpleVo>> searchUser(String keyWord,Integer page,Integer size);
	
	/**
     * 按标签搜索(达人)用户接口
     *
     */
    Response<PageList<StarInfoVO>> searchStarUser(StarInfoDTO starInfoDTO);
    
	/**
	 * 搜索话题帖子信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
    Response<PageList<ResourceInfoVo>> searchTopicInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索文章信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
    Response<PageList<ResourceInfoVo>> searchReleaseInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索私圈信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
    Response<PageList<CoterieInfoVo>> searchCoterieInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 全量重建用户es index
	 * 不能随便调用
	 */
	void rebuildUserInfo();
	
	/**
	 * 全量重建私圈es index
	 * 不能随便调用
	 */
	void rebuildCoterieInfo();
	
	/**
	 * 全量重建资源es index
	 * 不能随便调用
	 */
	void rebuildResourceInfo();




	/**
	 * 管理后台搜索用户
	 * @param
	 * @return
	 */
	Response<PageList<UserInfoVO>> adminSearchUser(AdminUserInfoDTO userInfoDTO);
}
