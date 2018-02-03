package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAdminVo;

public interface Question4AdminService {


    /**
     * 查询问答列表
     * @param dto
     * @return
     */
    public PageList<QuestionAdminVo> queryQuestionAnswerList(QuestionDto dto);


    /**
     * 查询有用的提问
     * @param kid
     * @return
     */
    public Question queryAvailableQuestionByKid(Long kid);


    public Integer shalveDown(Long kid);
}
