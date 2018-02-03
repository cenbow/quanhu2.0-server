package com.yryz.quanhu.openapi.service;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.user.contants.TokenCheckEnum;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthApi;

@Service
public class AuthService {
	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);
	@Reference
	private AuthApi authAPi;
	
	/**
	 * 检查token
	 * @param request
	 */
	public void checkToken(HttpServletRequest request){
		RequestHeader header = WebUtil.getHeader(request);
		String userId = header.getUserId();
		String token = header.getToken();
		
		if(StringUtils.isEmpty(userId)){
			throw QuanhuException.busiError(ExceptionEnum.NEEDTOKEN);
		}
		if(StringUtils.isEmpty(token)){
			throw QuanhuException.busiError(ExceptionEnum.NEEDTOKEN);
		}
		String tokenUserId = StringUtils.split(token, "-")[0];
		if(StringUtils.isEmpty(tokenUserId)){
			throw QuanhuException.busiError(ExceptionEnum.NEEDTOKEN);
		}
		if(!StringUtils.equals(tokenUserId,userId)){
			throw QuanhuException.busiError(ExceptionEnum.NEEDTOKEN);
		}
		DevType devType = DevType.getEnumByType(header.getDevType(), header.getUserAgent());
		Integer checkEnum = TokenCheckEnum.SUCCESS.getStatus();
		try {
			if (devType != DevType.ANDROID && devType != DevType.IOS) {
				AuthTokenDTO tokenDTO = new AuthTokenDTO(NumberUtils.toLong(userId), devType, header.getAppId(),header.getToken());
				checkEnum = ResponseUtils.getResponseData(authAPi.checkToken(tokenDTO));
			} else {
				AuthRefreshDTO refreshDTO = new AuthRefreshDTO(null, false);
				refreshDTO.setAppId(header.getAppId());
				refreshDTO.setUserId(NumberUtils.createLong(userId));
				refreshDTO.setType(devType);
				refreshDTO.setToken(header.getToken());
				checkEnum = ResponseUtils.getResponseData(authAPi.checkToken(refreshDTO));
			}
		} catch (Exception e) {
			logger.error("token校验异常",e);
			throw QuanhuException.busiError("token校验异常");
		}
		
		if(checkEnum == TokenCheckEnum.SUCCESS.getStatus()){
		}else{
			if(checkEnum == TokenCheckEnum.INVALID.getStatus()){
				throw QuanhuException.busiError(ExceptionEnum.TOKEN_INVALID);
			}
			if(checkEnum == TokenCheckEnum.EXPIRE.getStatus()){
				throw QuanhuException.busiError(ExceptionEnum.TOKEN_EXPIRE);
			}
			if(checkEnum == TokenCheckEnum.NO_TOKEN.getStatus()){
				throw QuanhuException.busiError(ExceptionEnum.NO_TOKEN);
			}
			logger.info("[checkToken]:header:{},checkEnum:{}",JsonUtils.toFastJson(header),checkEnum);
			if(checkEnum == TokenCheckEnum.ERROR.getStatus()){
				throw QuanhuException.busiError(ExceptionEnum.NEEDTOKEN);
			}
		}
	}
}
