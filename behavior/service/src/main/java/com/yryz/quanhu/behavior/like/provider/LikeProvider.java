package com.yryz.quanhu.behavior.like.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeService;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Autowired
    private EventAPI eventAPI;

    @Override
    @Transactional
    public Response<Map<String, Object>> dian(Like like) {
        RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
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
                    try{
                        redisTemplate.opsForValue().set("LIKE:"+like.getResourceId()+"_"+like.getUserId(),11L);
                    }catch (Exception e){
                        logger.info("同步点赞数据到redis出现异常:"+e);
                    }
                    //对接积分系统
                    like.getModuleEnum();





                }
            } else {
                int count = likeService.cleanLike(like);
                tongCount=-1;
                if (count > 0) {
                    map.put("result", 1);
                    try{
                        redisTemplate.delete("LIKE:"+like.getResourceId()+"_"+like.getUserId());
                    }catch (Exception e){
                        logger.info("从redis中清掉点赞数据出现异常:"+e);
                    }

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
               if(null != userSimpleVO){
                   likeVO.setUserNickName(userSimpleVO.getUserNickName());
                   likeVO.setUserImg(userSimpleVO.getUserImg());
               }else{
                   break;
               }
            }
            likeVOS.setCount(count);
            return ResponseUtils.returnObjectSuccess(likeVOS);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> isLike(Like like) {
        try{
            return ResponseUtils.returnObjectSuccess(likeService.isLike(like));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Long> getLikeFlag(Map<String, Object> map) {
        try{
            RedisTemplate<String, Long> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Long.class);
            Long likeFlag=0L;
            if(null!=redisTemplate){
                likeFlag = redisTemplate.opsForValue().get("LIKE:"+map.get("resourceId")+"_"+map.get("userId"));
                if(null==likeFlag){
                    likeFlag=10L;
                }
            }else{
                Like like=new Like();
                if(null!=map||map.size()>0){
                    like.setResourceId(Long.valueOf(map.get("resourceId").toString()));
                    like.setUserId(Long.valueOf(map.get("userId").toString()));
                }
                likeFlag = Long.valueOf(likeService.isLike(like));
            }
            return ResponseUtils.returnObjectSuccess(likeFlag);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }

    }

}
