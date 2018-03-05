package com.yryz.quanhu.behavior.like.dao;

import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 14:02 2018/1/24
 */
@Mapper
public interface LikeDao {

    int accretion(Like like);

    int isLike(Like like);

    int cleanLike(Like like);
    
    int update(Like like);
    
    List<LikeVO> queryLikers(LikeFrontDTO likeFrontDTO);

    LikeVO querySingleLiker(Like like);
}
