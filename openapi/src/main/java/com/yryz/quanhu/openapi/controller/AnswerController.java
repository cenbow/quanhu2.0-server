package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.yryz.common.annotation.UserBehaviorValidation;
import  com.yryz.common.annotation.UserBehaviorArgs;

import javax.servlet.http.HttpServletRequest;

@Api(description = "圈主回答接口")
@RestController
public class AnswerController {

    @Reference
    private AnswerApi answerApi;

    @ApiOperation("圈主发布回答")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
                    @ApiImplicitParam(name = "userId", paramType = "header", required = true)
            })
    @UserBehaviorValidation(event = "圈主发布回答",illegalWords = true,login = false)
    @UserBehaviorArgs(sourceContexts={"object.QuestionDto.content","object.QuestionDto.contentSource"})
    @PostMapping(value = "/services/app/{version}/coterie/answer/add")
    public Response<AnswerVo> saveAnswer(@RequestBody AnswerDto answerDto, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        answerDto.setCreateUserId(Long.valueOf(header.getUserId()));
        return answerApi.saveAnswer(answerDto);
    }


    @ApiOperation("查询回答详情")
    @ApiImplicitParams(
            {@ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
                    @ApiImplicitParam(name = "userId", paramType = "header", required = true)
            })
    @GetMapping(value = "/services/app/{version}/coterie/answer/single")
    public Response<AnswerVo> saveAnswer(Long kid, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        return answerApi.getDetail(kid);
    }

}
