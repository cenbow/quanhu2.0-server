package com.yryz.quanhu.resource.questionsAnswers.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;

public interface AnswerApi {

    public Response<AnswerVo> saveAnswer(AnswerDto answerDto);

    public Response<Integer> deletetAnswer(AnswerDto answerDto);

    public Response<AnswerVo> queryAnswer(Long kid,Long userId);
}
