package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.resource.questionsAnswers.entity.Question;

public interface QuestionService {

    /**
     * 发布问题
     */
    public Question saveQuestion(Question question);


    int deleteQuestion(Question question);
}
