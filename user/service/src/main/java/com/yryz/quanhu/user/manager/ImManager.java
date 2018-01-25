package com.yryz.quanhu.user.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.ImUser;

@Service
public class ImManager {
	private static final Logger imLogger = LoggerFactory.getLogger("im.logger");
	private static final Logger LOGGER2 = LoggerFactory.getLogger(ImManager.class);
	@Reference
	private ImAPI imApi;
	
	public void addImUser(Long userId,String nickName,String headImg,String appId){
		try{
			ImUser imUser = new ImUser();
			imUser.setIconUrl(headImg);
			imUser.setNick(nickName);
			imUser.setUserId(userId.toString());
			imApi.addUser(imUser);
		}catch(Exception e){
			
		}
	}
}
