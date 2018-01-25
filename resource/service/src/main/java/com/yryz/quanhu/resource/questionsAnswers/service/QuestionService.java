package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;

public interface QuestionService {

    /**
     * 发布问题
     */
    public Question saveQuestion(Question question);

    /**
     * 标记删除
     * @param kid
     * @param userId
     * @return
     */
    int deleteQuestion(Long kid, Long userId);


    /**
     *查询问题的详情
     * @param kid
     * @param userId
     * @return
     */
    QuestionAnswerVo getDetail(Long kid, Long userId);

    /**
     * 圈主拒绝回答问题
     * @param kid
     * @param userId
     * @return
     */
    Integer rejectAnswer(Long kid, Long userId);


    /**
     * 查询问答列表
     * @param dto
     * @return
     */
    public PageList<QuestionAnswerVo> queryQuestionAnswerList(QuestionDto dto);
}
