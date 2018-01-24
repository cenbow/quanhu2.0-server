package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.entity.UserTag;

public interface UserTagService {
	/**
	 * 用户标签删除
	 * @param userId
	 * @return
	 */
    int delete(Long userId,Integer tagType);
    /**
     * 用户标签写入
     * @param record
     * @return
     */
    int insert(UserTag record);
    /**
     * 用户标签批量写入<br/>
     * 遍历用户选择的tag,筛选重复的tag,把库里面不存在的tag作为新的tag插入数据库,没有选择tag直接插入
     * @param tagDTO
     */
    void batch(UserTagDTO tagDTO);
}
