package com.yryz.quanhu.openapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.NotLogin;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.vo.StarInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Api(description = "用户达人接口")
@RestController
@RequestMapping(value = "services/app")
public class UserStarController {
	private static final Logger logger = LoggerFactory.getLogger(UserStarController.class);

	@Reference
	private UserStarApi starApi;

	/**
	 * 达人申请
	 * 
	 * @param info
	 * @return
	 */
	@ResponseBody
	@ApiOperation("达人申请")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/star/starApply")
	public Response<Boolean> save(@RequestBody StarAuthInfo info, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		info.setAuthWay((byte) 10);
		info.setUserId(header.getUserId());
		info.setAppId(header.getAppId());
		return starApi.save(info);
	}

	/**
	 * 达人信息编辑
	 * 
	 * @param info
	 * @return
	 */
	@ApiOperation(" 达人信息编辑")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/star/editStarAuth")
	public Response<Boolean> update(@RequestBody StarAuthInfo info, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		info.setAuthWay((byte) 10);
		info.setUserId(header.getUserId());
		info.setAppId(header.getAppId());
		return starApi.update(info);
	}

	/**
	 * 达人信息获取
	 * 
	 * @param custId
	 * @return
	 */
	@ApiOperation("达人信息获取")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/star/getStarAuth")
	public Response<StarAuthInfo> get(String userId, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		if (StringUtils.isNotBlank(userId)) {
			return starApi.get(userId);
		} else {
			return starApi.get(header.getUserId());
		}

	}

	/**
	 * 达人推荐列表
	 * 
	 * @param custId
	 * @return
	 */
	@ApiOperation("达人推荐列表")
	@NotLogin
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@GetMapping(value = "/{version}/star/starCommend")
	public Response<List<StarInfoVO>> recommendList(Integer start,Integer limit,HttpServletRequest request)  {
		RequestHeader header = WebUtil.getHeader(request);
		StarAuthParamDTO paramDTO = new StarAuthParamDTO();
		paramDTO.setUserId(NumberUtils.createLong(header.getUserId()));
		paramDTO.setStart(start);
		paramDTO.setLimit(limit);
		return starApi.starList(paramDTO);
	}

}
