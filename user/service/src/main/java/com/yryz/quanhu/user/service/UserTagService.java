package com.yryz.quanhu.user.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.entity.UserTag;
import com.yryz.quanhu.user.vo.UserTagVO;

public interface UserTagService {
    /**
     * 用户标签删除
     *
     * @param userId
     * @return
     */
    int delete(Long userId, Integer tagType);

    /**
     * 用户标签写入
     *
     * @param record
     * @return
     */
    int insert(UserTag record);

    /**
     * 用户标签批量写入<br/>
     * 遍历用户选择的tag,筛选重复的tag,把库里面不存在的tag作为新的tag插入数据库,没有选择tag直接插入
     *
     * @param tagDTO
     */
    void batch(UserTagDTO tagDTO);

    /**
     * 用户标签查询
     *
     * @param userId
     * @param tagType
     * @return
     */
    List<String> getTagByUserId(Long userId, Integer tagType);

    Map<Long, List<UserTagVO>> getUserTags(List<Long> userIds);


    List<UserTagDTO> getUserTags(Long userId);

    List<UserTagDTO> getUserGroupConcatTags(Set<Long> userIds);

    Map<String, Long> getTagCountByUser(Set<String> tagIds);
}
