package com.yryz.quanhu.behavior.like.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    @Reference(check = false)
    private CountApi countApi;

    @Reference(check = false)
    private UserApi userApi;

    @Override
    @Transactional
    public Response<Map<String, Object>> dian(Like like) {
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            like.setKid(idAPI.getSnowflakeId().getData());
            int isLikeCount = likeService.isLike(like);
            int tongCount=0;
            if (isLikeCount <= 0) {
                int dianCount = likeService.accretion(like);
                if (dianCount > 0) {
                    tongCount=1;
                    LikeVO likeVO = likeService.querySingleLiker(like);
                    if (null != likeVO) {
                        try{
                            UserSimpleVO userSimpleVO=userApi.getUserSimple(likeVO.getUserId()).getData();
                            if(null!=userSimpleVO){
                                map.put("userNickName", userSimpleVO.getUserNickName());
                                map.put("userImg", userSimpleVO.getUserImg());
                            }
                        }catch (Exception e){
                            logger.info("调用用户接口失败:"+e);
                        }
                        map.put("userId", likeVO.getUserId());
                        map.put("createDate", likeVO.getCreateDate());
                    }
                }
            } else {
                int count = likeService.cleanLike(like);
                tongCount=-1;
                if (count > 0) {
                    map.put("result", 1);
                } else {
                    map.put("result", 0);
                }
            }
            try{
                if(tongCount!=0){
                    Response<Object> response = countApi.commitCount(BehaviorEnum.Like,like.getResourceId(),"",Long.valueOf(tongCount));
                    if(response.getCode().equals("200")){
                        logger.info("进入统计系统成功");
                    }
                }
            }catch (Exception e){
                logger.info("进入统计系统失败:"+e);
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
            Response<Map<String,Long>> mapResponse=countApi.getCount("11",likeFrontDTO.getResourceId(),"");
            Long count=mapResponse.getData().get("likeCount");
            PageList<LikeVO> likeVOS=likeService.queryLikers(likeFrontDTO);
            for(LikeVO likeVO:likeVOS.getEntities()){
               UserSimpleVO userSimpleVO=userApi.getUserSimple(likeVO.getUserId()).getData();
               likeVO.setUserNickName(userSimpleVO.getUserNickName());
               likeVO.setUserImg(userSimpleVO.getUserImg());
            }
            likeVOS.setCount(count);
            return ResponseUtils.returnObjectSuccess(likeVOS);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }
}
