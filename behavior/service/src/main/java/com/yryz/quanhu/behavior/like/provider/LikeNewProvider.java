package com.yryz.quanhu.behavior.like.provider;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.common.utils.Assert;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Sets;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.common.manager.UserRemote;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.behavior.like.dto.LikeFrontDTO;
import com.yryz.quanhu.behavior.like.entity.Like;
import com.yryz.quanhu.behavior.like.service.LikeNewService;
import com.yryz.quanhu.behavior.like.vo.LikeInfoVO;
import com.yryz.quanhu.behavior.like.vo.LikeVO;
import com.yryz.quanhu.user.vo.UserSimpleNoneOtherVO;

@Service(interfaceClass = LikeApi.class)
public class LikeNewProvider implements LikeApi{
	private static final Logger logger = LoggerFactory.getLogger(LikeNewProvider.class);
	
	@Autowired
	private LikeNewService likeService;
	@Autowired
	private UserRemote userService;
	
	@Override
	public Response<Map<String, Object>> dian(Like like) {
		try {
			Assert.notNull(like, "参数为空");
			Assert.notNull(like.getResourceId(),"资源id为空");
			
			if(like.getUserId() == 0l){
				throw QuanhuException.busiError("用户id为空");
			}
            Map<String, Object> map = new HashMap<String, Object>();
            int result = likeService.accretion(like);
            map.put("result", result);
    		
            LikeInfoVO infoVO = new LikeInfoVO();
			infoVO.setResourceId(like.getResourceId());
			Map<String,UserSimpleNoneOtherVO> userMap = userService.getUserInfo(Sets.newHashSet(String.valueOf(like.getUserId())));
			UserSimpleNoneOtherVO otherVO = userMap.get(String.valueOf(like.getUserId()));
			infoVO.setCreateTime(System.currentTimeMillis());
			infoVO.setUser(otherVO == null ? new UserSimpleNoneOtherVO() : otherVO);
			
			map.put("likeInfo", infoVO);
        	
			map.put("likeFlag", likeService.isLike(like));
            return ResponseUtils.returnObjectSuccess(map);
        } catch (QuanhuException e) {
            logger.error("[like_dian]", e);
            return ResponseUtils.returnException(e);
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
			if(likeFrontDTO.getCurrentPage() < 1){
				likeFrontDTO.setCurrentPage(1);
			}
			if(likeFrontDTO.getPageSize() < 0 || likeFrontDTO.getPageSize() > 100){
				likeFrontDTO.setPageSize(10);
			}
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
			return ResponseUtils.returnObjectSuccess((long)likeService.isLike(like));
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
