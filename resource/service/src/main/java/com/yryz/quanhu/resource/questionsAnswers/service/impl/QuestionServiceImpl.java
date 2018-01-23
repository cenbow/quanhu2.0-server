package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.QuestionDao;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 * @date
 */
@Service
public class QuestionServiceImpl implements QuestionService {
    /**
     * 发布问题
     *
     * @param question
     * @return
     */

    @Autowired
    private QuestionDao questionDao;

    /**
     * 发布提问
     *
     * @param question
     * @return
     */
    @Override
    public Question saveQuestion(Question question) {
        /**
         *检验传递的参数是否缺失
         */
        String citeriaId = question.getCoterieId();
        String targetId = question.getTargetId();
        String conttent = question.getContent();
        Byte isAnonymity = question.getIsAnonymity();
        Byte isOnlyShowMe = question.getIsOnlyShowMe();
        if (StringUtils.isBlank(citeriaId) || StringUtils.isBlank(targetId) || StringUtils.isBlank(conttent)
                || isAnonymity == null || null == isOnlyShowMe) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        /**
         *校验圈主是否合法
         */
        // TODO: 2018/1/22 0022  


        /**
         * 校验是是否是圈粉，是否禁言
         */
        /// TODO: 2018/1/22 0022  

        questionDao.insertSelective(question);
        return question;
    }


    /**
     * 删除提问，使用标记删除
     *
     * @param question
     * @return
     */
    @Override
    public int deleteQuestion(Question question) {
        /**
         * 检查参数是否缺失
         */
        Long kid = question.getKid();
        Byte shelverFlag = question.getShelveFlag();
        if (null == kid || null == shelverFlag) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        /**
         * 检验操作人是否有权限执行删除操作，问题是否已经回答，是否是提问者本人
         */
        Question questionBySearch = this.questionDao.selectByPrimaryKey(kid);
        if (question.getCreateUserId().compareTo(questionBySearch.getCreateUserId()) != 0) {
            throw QuanhuException.busiError("非本人不能删除问题");
        }
        if (questionBySearch.getAnswerdFlag().compareTo(QuestionAnswerConstants.AnswerdFlag.ANSWERED) != 0) {
            throw QuanhuException.busiError("问题已经回答，不能删除");
        }
        // TODO: 2018/1/22 0022
        return this.questionDao.updateByPrimaryKeySelective(question);
    }


}
