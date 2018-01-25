package com.yryz.quanhu.behavior.like.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeVO;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:51 2018/1/24
 */
public interface LikeService {

    int accretion(Like like);

    int isLike(Like like);

    int cleanLike(Like like);

    PageList<LikeVO> queryLikers(LikeFrontDTO likeFrontDTO);

    LikeVO querySingleLiker(Like like);
}
