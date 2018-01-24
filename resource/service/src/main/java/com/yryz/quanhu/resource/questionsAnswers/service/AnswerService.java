package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;

public interface AnswerService {

    public AnswerVo saveAnswer(AnswerDto answerdto);

    public Integer deleteAnswer(AnswerDto answerdto);

    public  AnswerVo getDetail(Long kid,Long userId);

    public AnswerVo queryAnswerVoByquestionId(Long kid);
}
