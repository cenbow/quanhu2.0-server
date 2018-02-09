package com.yryz.quanhu.behavior.like.Service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeVO;

import java.util.List;
import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:50 2018/1/24
 */
public interface LikeApi {

    /**
     * desc:点赞/取消点赞
     * @param:like
     * @return:map
     **/
    Response<Map<String,Object>> dian(Like like);

    /**
     * desc:点赞列表
     * @paraml:ike
     * @return
     */
    Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO);

    /**
     * 是否点过赞
     * @param like
     * @return
     */
    Response<Integer> isLike(Like like);


    /**
     * 获取点赞的状态
     * @param map
     * @return
     */
    Response<Long> getLikeFlag(Map<String,Object> map);

    /**
     * 批量获取点赞状态
     * @param userId
     * @return
     */
    Response<Map<String,Integer>> getLikeFlagBatch(List<Long> resourceIds, long userId);

}
