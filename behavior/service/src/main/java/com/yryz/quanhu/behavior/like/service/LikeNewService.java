package com.yryz.quanhu.behavior.like.service;

import java.util.List;
import java.util.Map;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;
import com.yryz.quanhu.behavior.like.vo.LikeVO;

public interface LikeNewService {
	int accretion(Like like);

    int isLike(Like like);

    int cleanLike(Like like);

    PageList<LikeInfoVO> listLike(LikeFrontDTO likeFrontDTO);

    LikeVO querySingleLiker(Like like);

    Map<String,Integer> getLikeFlagBatch(List<Long> resourceIds, long userId);
}
