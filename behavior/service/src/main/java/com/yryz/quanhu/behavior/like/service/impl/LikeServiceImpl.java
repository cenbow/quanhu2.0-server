package com.yryz.quanhu.behavior.like.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.like.dao.LikeDao;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public PageList<LikeVO> queryLikers(LikeFrontDTO likeFrontDTO) {
        Page<Like> page= PageHelper.startPage(likeFrontDTO.getCurrentPage(),likeFrontDTO.getPageSize());
        PageList pageList=new PageList();
        pageList.setCurrentPage(likeFrontDTO.getCurrentPage());
        pageList.setPageSize(likeFrontDTO.getPageSize());
        List<LikeVO> likeVOS=likeDao.queryLikers(likeFrontDTO);
        //pageList.setCount(Long.valueOf(likeVOS.size()));
        pageList.setEntities(likeVOS);
        return pageList;
    }

    @Override
    public LikeVO querySingleLiker(Like like) {
        return likeDao.querySingleLiker(like);
    }

    @Override
    public Map<String, Integer> getLikeFlagBatch(List<Long> resourceIds, long userId) {
        Map<String,Integer> maps=new HashMap<String, Integer>();
        if(null!=resourceIds&&resourceIds.size()>0){
            for(Long resourceId:resourceIds){
                Like like=new Like();
                like.setResourceId(resourceId);
                like.setUserId(userId);
                int count = likeDao.isLike(like);
                if(count>0){
                    maps.put(String.valueOf(resourceId),10);
                }else{
                    maps.put(String.valueOf(resourceId),11);
                }
            }
        }
        return maps;
    }
}
