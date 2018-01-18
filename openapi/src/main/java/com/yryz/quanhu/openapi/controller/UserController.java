package com.yryz.quanhu.openapi.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.AuthTokenVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "用户接口")
@RestController
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Reference
	private AccountApi accountApi;
	@Reference
	private UserApi userApi;
	@Reference
	private AuthApi authApi;
	
	@ApiOperation("用户token刷新")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/user/refreshToken")
    public Response<AuthTokenVO> refreshToken(String userId,HttpServletRequest request) {
		try {
			if(StringUtils.isBlank(userId)){
				throw QuanhuException.busiError("userId不能为空");
			}
			RequestHeader header = WebUtil.getHeader(request);
			AuthRefreshDTO refreshDTO = new AuthRefreshDTO(header.getRefreshToken(), true);
			refreshDTO.setAppId(header.getToken());
			refreshDTO.setToken(header.getToken());
			refreshDTO.setUserId(userId);
			refreshDTO.setType(DevType.getEnumByType(header.getDevType(), header.getUserAgent()));
			return authApi.refreshToken(refreshDTO);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
    }
	
	@ApiOperation("用户信息查询")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/user/find")
    public Response<UserSimpleVO> findUser(String userId) {
		try {
			if(StringUtils.isBlank(userId)){
				throw QuanhuException.busiError("userId不能为空");
			}
			return userApi.getUserSimple(userId);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
    }
}
