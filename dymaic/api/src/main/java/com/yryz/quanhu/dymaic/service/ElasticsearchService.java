package com.yryz.quanhu.dymaic.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;

public interface ElasticsearchService {
	/**
	 * 搜索用户信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<UserSimpleVo> searchUser(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索话题帖子信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<ResourceInfoVo> searchTopicInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索文章信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<ResourceInfoVo> searchReleaseInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索私圈信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<CoterieInfoVo> searchCoterieInfo(String keyWord,Integer page,Integer size);
}
