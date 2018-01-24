package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.dto.*;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

@Api(description = "圈粉提问接口")
@RestController
public class QuestionController {

	@Reference
	private QuestionApi questionApi;


	@ApiOperation("圈粉发布问题")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/coterie/question/add")
	public Response<QuestionVo> saveQuestion(@RequestBody QuestionDto questionDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		questionDto.setCreateUserId(Long.valueOf(header.getUserId()));
		return questionApi.saveQuestion(questionDto);
	}

	@ApiOperation("圈粉删除未回答的问题")
	@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
	@PostMapping(value = "/{version}/coterie/question/delete")
	public Response<Integer> findUser(@RequestBody QuestionDto questionDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);

		return questionApi.deleteQuestion(questionDto.getKid(),Long.valueOf(header.getUserId()));
	}

}
