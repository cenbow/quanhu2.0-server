package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;

public interface AnswerService {

    public AnswerVo saveAnswer(AnswerWithBLOBs answerWithBLOBs);

    public Integer deleteAnswer(Answer answer);

    public  AnswerVo getDetail(Long kid);
}
