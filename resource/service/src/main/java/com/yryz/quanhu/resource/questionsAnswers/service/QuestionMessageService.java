package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.common.message.MessageConstant;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;

public interface QuestionMessageService {
    Boolean sendNotify4Question(Question question, MessageConstant messageConstant);
}
