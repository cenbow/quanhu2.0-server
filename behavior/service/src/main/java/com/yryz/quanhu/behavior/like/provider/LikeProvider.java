package com.yryz.quanhu.behavior.like.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.mongodb.Page;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.contants.LikeContants;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 15:03 2018/1/24
 */
@Service(interfaceClass = LikeApi.class)
public class LikeProvider implements LikeApi {

    private static final Logger logger = LoggerFactory.getLogger(LikeProvider.class);

    @Autowired
    private LikeService likeService;

    @Reference(check = false)
    private IdAPI idAPI;


    @Override
    public Response<Map<String, Object>> dian(Like like) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            like.setKid(idAPI.getKid(LikeContants.QH_LIKE).getData());
            int isLikeCount = likeService.isLike(like);
            if (isLikeCount <= 0) {
                int dianCount = likeService.accretion(like);
                if (dianCount > 0) {
                    LikeVO likeVO = likeService.querySingleLiker(like);
                    if (null != likeVO) {

                        map.put("userId", likeVO.getUserId());
                        //预留调用用户模块取数据用户ID取用户昵称头像

                        map.put("createDate", likeVO.getCreateDate());
                    }
                }
            } else {
                int count = likeService.cleanLike(like);
                if (count > 0) {
                    map.put("result", 1);
                } else {
                    map.put("result", 0);
                }
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO) {
        try {
            PageList<LikeVO> likeVOS=likeService.queryLikers(likeFrontDTO);
            for(LikeVO likeVO:likeVOS.getEntities()){
                //预留根据用户ID取用户昵称头像的问题
                likeVO.setUserNickName("天凉好个冻");
                likeVO.setUserImg("https://cdn-qa.yryz.com/pic/hwq/6932776fa931fa462bb85727d35fe5e7.png");
            }
            return ResponseUtils.returnObjectSuccess(likeVOS);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }
}
