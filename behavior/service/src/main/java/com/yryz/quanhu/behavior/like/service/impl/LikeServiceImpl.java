package com.yryz.quanhu.behavior.like.service.impl;

import com.yryz.quanhu.behavior.like.dao.LikeDao;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:52 2018/1/24
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDao likeDao;

    @Override
    public int accretion(Like like) {
        return likeDao.accretion(like);
    }

    @Override
    public int isLike(Like like) {
        return likeDao.isLike(like);
    }

    @Override
    public int cleanLike(Like like) {
        return likeDao.cleanLike(like);
    }

    @Override
    public List<LikeVO> queryLikers(Like like) {
        return likeDao.queryLikers(like);
    }

    @Override
    public LikeVO querySingleLiker(Like like) {
        return likeDao.querySingleLiker(like);
    }
}
