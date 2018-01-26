package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
@Api(description = "圈粉提问接口")
@RestController
public class QuestionController {

	@Reference
	private QuestionApi questionApi;

	@ApiOperation("圈粉发布问题")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
			@ApiImplicitParam(name = "userId", paramType = "header", required = true)
	})
	@PostMapping(value = "/services/app/{version}/coterie/question/add")
	public Response<QuestionVo> saveQuestion(@RequestBody QuestionDto questionDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		questionDto.setCreateUserId(Long.valueOf(userId));
		return questionApi.saveQuestion(questionDto);
	}

	@ApiOperation("圈粉删除未回答的问题")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
					@ApiImplicitParam(name = "userId", paramType = "header", required = true)
			})
	@PostMapping(value = "/services/app/{version}/coterie/question/delete")
	public Response<Integer> deleteQueston(@RequestBody QuestionDto questionDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		return questionApi.deleteQuestion(questionDto.getKid(),Long.valueOf(userId));
	}

	@ApiOperation("圈主拒接回答问题")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
					@ApiImplicitParam(name = "userId", paramType = "header", required = true)
			})
	@PostMapping(value = "/services/app/{version}/coterie/question/reject")
	public Response<Integer> rejectQuestion(@RequestBody QuestionDto questionDto, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		return questionApi.rejectAnswerQuestion(questionDto.getKid(),Long.valueOf(userId));
	}


	@ApiOperation("查询问答的详情")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
					@ApiImplicitParam(name = "userId", paramType = "header", required = true)
			})
	@GetMapping(value = "/services/app/{version}/coterie/questionAnswer/single")
	public Response<QuestionAnswerVo> queryQuestionAnswer(Long kid, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		return questionApi.queryQuestionAnswerDetail(kid,Long.valueOf(userId));
	}


	@ApiOperation("查询问答列表")
	@ApiImplicitParams(
			{@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
					@ApiImplicitParam(name = "userId", paramType = "header", required = true)
			})
	@GetMapping(value = "/services/app/{version}/coterie/question/list")
	public Response<PageList<QuestionAnswerVo>> queryQuestionAnswerList(Long coterieId, Integer pageNum, Integer pageSize, HttpServletRequest request) {
		RequestHeader header = WebUtil.getHeader(request);
		String userId=header.getUserId();
		if(StringUtils.isBlank(userId)){
			return ResponseUtils.returnException(QuanhuException.busiError(ExceptionEnum.PARAM_MISSING.getCode(),"缺失用户编号"));
		}
		QuestionDto dto=new QuestionDto();
		dto.setCreateUserId(Long.valueOf(userId));
		dto.setCoterieId(coterieId);
		dto.setPageNum(pageNum);
		dto.setPageSize(pageSize);
		return questionApi.queryQuestionAnswerVoList(dto);
	}


}
