package com.yryz.quanhu.behavior.like.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeNewService;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;
import com.yryz.quanhu.behavior.like.vo.LikeVO;

//@Service(interfaceClass = LikeApi.class)
public class LikeNewProvider implements LikeApi{
	private static final Logger logger = LoggerFactory.getLogger(LikeNewProvider.class);
	
	@Autowired
	private LikeNewService likeService;
	
	@Override
	public Response<Map<String, Object>> dian(Like like) {
		try {
            Map<String, Object> map = new HashMap<String, Object>();
            int result = likeService.accretion(like);
            map.put("result", result);
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("[like_dian]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<PageList<LikeVO>> queryLikers(LikeFrontDTO likeFrontDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<PageList<LikeInfoVO>> listLike(LikeFrontDTO likeFrontDTO) {
		try {
            PageList<LikeInfoVO> likeVOS = likeService.listLike(likeFrontDTO);
            return ResponseUtils.returnObjectSuccess(likeVOS);
        } catch (Exception e) {
            logger.error("[like_listLike]", e);
            return ResponseUtils.returnException(e);
        }
    }

	@Override
	public Response<Integer> isLike(Like like) {
		try {
            return ResponseUtils.returnObjectSuccess(likeService.isLike(like));
        } catch (Exception e) {
            logger.error("[like_isLike]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<Long> getLikeFlag(Map<String, Object> map) {
		try {
			Like like = new Like();
			like.setResourceId(NumberUtils.createLong(map.get("resourceId").toString()));
			like.setUserId(NumberUtils.createLong(map.get("userId").toString()));
			return ResponseUtils.returnObjectSuccess(Long.valueOf(likeService.isLike(like)));
        } catch (Exception e) {
            logger.error("[like_getLikeFlag]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<Map<String, Integer>> getLikeFlagBatch(List<Long> resourceIds, long userId) {
		try{
            return ResponseUtils.returnObjectSuccess(likeService.getLikeFlagBatch(resourceIds,userId));
        }catch (Exception e){
            logger.error("[like_getLikeFlagBatch]", e);
            return ResponseUtils.returnException(e);
        }
	}
	
}
