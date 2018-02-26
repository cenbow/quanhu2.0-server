package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;

public interface AnswerService {

    public AnswerVo saveAnswer(AnswerDto answerdto);

    public Integer deleteAnswer(AnswerDto answerdto);

    public AnswerVo queryAnswerVoByquestionId(Long kid);

    AnswerVo getDetail(Long kid);

    AnswerWithBLOBs queryAnswerBykid(Long kid);
}
