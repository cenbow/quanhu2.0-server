package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;

public interface QuestionService {

    /**
     * 发布问题
     */
    public Question saveQuestion(Question question);

    /**
     * 标记删除
     * @param question
     * @return
     */
    int deleteQuestion(Question question);


    /**
     *
     * @param kid
     * @param userId
     * @return
     */
    QuestionVo getDetail(Long kid, Long userId);
}
