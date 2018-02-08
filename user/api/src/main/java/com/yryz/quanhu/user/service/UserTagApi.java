package com.yryz.quanhu.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.vo.UserTagVO;

public interface UserTagApi {
	/**
	 * 批量保存用户标签
	 * @param tagDTO
	 * @return
	 */
	public Response<Boolean> batchSaveUserTag(UserTagDTO tagDTO);
	
	/**
	 * 查询标签
	 * @param userId
	 * @param tagType
	 * @return
	 */
	public Response<List<String>> getTags(Long userId,Integer tagType);


	/**
	 * 批量获取用户标签信息
	 * @param userIds
	 * @return
	 */
	Response<Map<Long, List<UserTagVO>>> getUserTags(List<Long> userIds);

	/**
	 * 批量获取标签下用户数量
	 * @param tagIds
	 * @return
	 */
	Response<Map<String,Long>> getTagCountByUser(Set<String> tagIds);

}
