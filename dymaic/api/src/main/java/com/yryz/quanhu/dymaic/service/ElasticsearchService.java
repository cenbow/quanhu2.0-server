package com.yryz.quanhu.dymaic.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ReleaseInfoVo;
import com.yryz.quanhu.dymaic.vo.TopicInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;

public interface ElasticsearchService {
	/**
	 * 搜索用户信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	List<UserSimpleVo> searchUser(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索文章信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<ReleaseInfoVo> searchReleaseInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索话题信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<TopicInfoVo> searchTopicInfo(String keyWord,Integer page,Integer size);
	
	/**
	 * 搜索私圈信息
	 * @param keyWord
	 * @param page
	 * @param size
	 * @return
	 */
	PageList<CoterieInfoVo> searchCoterieInfo(String keyWord,Integer page,Integer size);
}
