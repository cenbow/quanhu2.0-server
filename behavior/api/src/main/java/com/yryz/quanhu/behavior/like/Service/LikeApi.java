package com.yryz.quanhu.behavior.like.Service;

import com.yryz.common.response.Response;
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
     * @param like
     * @return
     */
    Response<List<LikeVO>> queryLikers(Like like);


}
