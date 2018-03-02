package com.yryz.quanhu.behavior.common.manager;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleNoneOtherVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * 用户服务远程代理
 * @author danshiyu
 *
 */
@Service
public class UserRemote {
	private static final Logger logger = LoggerFactory.getLogger(UserRemote.class);
	@Reference
	UserApi userApi;
	
	/**
	 * 获取用户信息
	 * @param userId
	 * @return
	 */
	public UserSimpleVO getUserInfo(Long userId){
		try {
			UserSimpleVO simpleVO = ResponseUtils.getResponseData(userApi.getUserSimple(userId));
			return simpleVO;
		} catch (Exception e) {
			logger.error("[getUserInfo]",e);
			UserSimpleVO simpleVO = new UserSimpleVO();
			simpleVO.setUserId(userId);
			simpleVO.setUserNickName("");
			return simpleVO;
		}
	}
	
	/**
	 * 获取用户信息
	 * @param userIds
	 * @return
	 */
	public Map<String,UserSimpleNoneOtherVO> getUserInfo(Set<String> userIds){
		try {
			Map<String,UserSimpleNoneOtherVO> map = ResponseUtils.getResponseData(userApi.getUserSimpleNoneOtherInfo(userIds));
			if(MapUtils.isEmpty(map)){
				map = new HashMap<>();
			}
			return map;
		} catch (Exception e) {
			logger.error("[getUserInfo]",e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}
}
