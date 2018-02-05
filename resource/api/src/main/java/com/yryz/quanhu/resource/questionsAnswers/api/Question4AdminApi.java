package com.yryz.quanhu.resource.questionsAnswers.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAdminVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;

public interface Question4AdminApi {

    Response<Integer> shalveDown(Long kid);

    Response<PageList<QuestionAdminVo>> queryQuestionAnswerVoList(QuestionDto questionDto);

    Response<QuestionAnswerVo> queryDetail(Long kid);
}
