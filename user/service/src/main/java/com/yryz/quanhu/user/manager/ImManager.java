package com.yryz.quanhu.user.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.constant.AppConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.ImUser;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.vo.UserSimpleVO;

@Service
public class ImManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ImManager.class);
	@Reference
	private ImAPI imApi;
	@Autowired
	private UserService userService;
	/**
	 * im新增用户信息
	 * @param userId
	 * @param nickName
	 * @param headImg
	 * @param appId
	 */
	public void addImUser(Long userId,String appId){
		ImUser imUser = null;
		try{
			imUser = new ImUser();
			UserSimpleVO simpleVO = userService.getUserSimple(userId);
			imUser.setIconUrl(simpleVO.getUserImg());
			imUser.setNick(simpleVO.getUserNickName());
			imUser.setUserId(userId.toString());
			RpcContext.getContext().set(AppConstants.APP_ID, appId);
			Response<Boolean> response = imApi.addUser(imUser);
			LOGGER.info("[user_im_add]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(response));
		}catch(Exception e){
			LOGGER.info("[user_im_add]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(e.getMessage()));
			LOGGER.error("[user_im_add]",e);
		}
	}
	
	/**
	 * im更新用户信息
	 * @param userId
	 * @param nickName
	 * @param headImg
	 * @param appId
	 */
	public void updateUser(Long userId,String nickName,String headImg,String appId){
		if(userId == null || (StringUtils.isBlank(headImg) && StringUtils.isBlank(nickName))){
			return;
		}
		ImUser imUser = null;
		try{
			imUser = new ImUser();
			imUser.setIconUrl(headImg);
			imUser.setNick(nickName);
			imUser.setUserId(userId.toString());
			RpcContext.getContext().set(AppConstants.APP_ID, appId);
			Response<Boolean> response = imApi.updateUser(imUser);
			LOGGER.info("[user_im_update]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(response));
		}catch(Exception e){
			LOGGER.info("[user_im_update]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(e.getMessage()));
			LOGGER.error("[user_im_update]",e);
		}
	}
	
	/**
	 * im发出下线消息
	 * @param userId
	 * @param nickName
	 * @param headImg
	 * @param appId
	 */
	public void block(Long userId,String appId){
		ImUser imUser = null;
		try{
			imUser = new ImUser();
			imUser.setUserId(userId.toString());
			RpcContext.getContext().set(AppConstants.APP_ID, appId);
			Response<Boolean> response = imApi.block(imUser);
			LOGGER.info("[user_im_block]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(response));
		}catch(Exception e){
			LOGGER.info("[user_im_block]:params:{},result:{}",JsonUtils.toFastJson(imUser),JsonUtils.toFastJson(e.getMessage()));
			LOGGER.error("[user_im_block]",e);
			throw QuanhuException.busiError(e.getMessage());
		}
	}
}
