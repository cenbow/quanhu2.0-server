package com.yryz.quanhu.resource.questionsAnswers.api;


import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;

public interface QuestionApi {

    public Response<QuestionVo> saveQuestion(QuestionDto questionDto);

    Response<Integer> deleteQuestion(QuestionDto questionDto);
}
