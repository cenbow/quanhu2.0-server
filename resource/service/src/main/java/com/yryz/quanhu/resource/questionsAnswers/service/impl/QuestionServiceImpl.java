package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.QuestionDao;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wanght
 * @date 2018-02-1-22
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Reference
    private UserApi userApi;

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
        return this.questionDao.updateByPrimaryKeySelective(question);
    }


    /**
     * 查询问题的详情
     *
     * @param kid
     * @return
     */
    @Override
    public QuestionVo getDetail(Long kid, Long userId) {
        /**
         * 参数校验
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        Question questionBysearch = this.questionDao.selectByPrimaryKey(kid);
        if (null == questionBysearch) {
            throw QuanhuException.busiError("查询的问题不存在");
        }

        /**
         * 如果是私密问题，只有提问人和圈主可见
         */
        Long createUserId = questionBysearch.getCreateUserId();
        if (questionBysearch.getIsOnlyShowMe().compareTo(QuestionAnswerConstants.showType.ONESELF) == 0) {
            if (createUserId.compareTo(userId) != 0) {
                throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TOREAD);
            }
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(questionVo, questionBysearch);
        if (null != createUserId) {
            Response<UserSimpleVO> userSimpleVOResponse = userApi.getUserSimple(String.valueOf(createUserId));
            if (ResponseConstant.SUCCESS.getCode().equals(userSimpleVOResponse.getCode())) {
                UserSimpleVO userSimpleVO = userSimpleVOResponse.getData();
                questionVo.setUser(userSimpleVO);
            }
        }
        return questionVo;
    }


}
