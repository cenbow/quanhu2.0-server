package com.yryz.quanhu.user.service;

import java.util.List;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserTagDTO;

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
}
