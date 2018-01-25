package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.yryz.common.response.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.QuestionDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.entity.QuestionExample;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;

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


    @Reference
    private IdAPI idAPI;

    @Autowired
    private AnswerService answerService;

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
        Long citeriaId = question.getCoterieId();
        String targetId = question.getTargetId();
        String conttent = question.getContent();
        Byte isAnonymity = question.getIsAnonymity();
        Byte isOnlyShowMe = question.getIsOnlyShowMe();
        if (null == citeriaId || StringUtils.isBlank(targetId) || StringUtils.isBlank(conttent)
                || isAnonymity == null || null == isOnlyShowMe) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        question.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
        question.setCreateDate(new Date());
        question.setQuestionType(QuestionAnswerConstants.questionType.ONE_TO_ONE);
        question.setRevision(0);
        question.setOperatorId("");
        question.setValidTime(QuestionAnswerConstants.VALID_HOUR);
        question.setCityCode("");
        question.setGps("");
        question.setOperateShelveDate(new Date(0));
        question.setOrderFlag(Byte.valueOf("10"));
        question.setOrderId("");
        question.setRefundOrderId("");
        question.setAnswerdFlag(QuestionAnswerConstants.AnswerdFlag.NOt_ANSWERED);
        question.setIsValid(QuestionAnswerConstants.validType.YES);
        question.setDelFlag(CommonConstants.DELETE_NO);
        question.setShelveFlag(CommonConstants.SHELVE_YES);

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
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public int deleteQuestion(Long kid, Long userId) {
        /**
         * 检查参数是否缺失
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        /**
         * 检验操作人是否有权限执行删除操作，问题是否已经回答，是否是提问者本人
         */
        Question questionBySearch = this.questionDao.selectByPrimaryKey(kid);
        if (null == questionBySearch) {
            throw QuanhuException.busiError("删除的问题不存在");
        }
        if (userId.compareTo(questionBySearch.getCreateUserId()) != 0) {
            throw QuanhuException.busiError("非本人不能删除问题");
        }
//        if (questionBySearch.getAnswerdFlag().compareTo(QuestionAnswerConstants.AnswerdFlag.ANSWERED) != 0) {
////            throw QuanhuException.busiError("问题已经回答，不能删除");
////        }
        questionBySearch.setDelFlag(CommonConstants.DELETE_YES);
        return this.questionDao.updateByPrimaryKeySelective(questionBySearch);
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
        QuestionExample example=new QuestionExample();
        QuestionExample.Criteria criteria=example.createCriteria();
        criteria.andKidEqualTo(kid);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        List<Question> questions = this.questionDao.selectByExample(example);
        if (null == questions || questions.isEmpty()) {
            //throw QuanhuException.busiError("查询的问题不存在");
            return null;
        }

        /**
         * 如果是私密问题，只有提问人和圈主可见
         */
        Question questionBysearch=questions.get(0);
        Long createUserId = questionBysearch.getCreateUserId();
        if (questionBysearch.getIsOnlyShowMe().compareTo(QuestionAnswerConstants.showType.ONESELF) == 0) {
            if (createUserId.compareTo(userId) != 0) {
                throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TOREAD);
            }
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(questionVo, questionBysearch);
        if (null != createUserId) {
            questionVo.setUser(ResponseUtils.getResponseData(userApi.getUserSimple(createUserId)));
        }
        return questionVo;
    }


    /**
     * 圈主拒接回答问题
     *
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public Integer rejectAnswer(Long kid, Long userId) {
        /**
         * 参数校验
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        Question question = this.questionDao.selectByPrimaryKey(kid);
        if (null == question) {
            throw QuanhuException.busiError("圈主拒接回答的问题不存在");
        }
        question.setAnswerdFlag(QuestionAnswerConstants.AnswerdFlag.REJECT_ANSWERED);

        return this.questionDao.updateByPrimaryKeySelective(question);
    }

    /**
     * 查询问答列表
     *
     * @param dto
     * @return
     */
    @Override
    public PageList<QuestionAnswerVo> queryQuestionAnswerList(QuestionDto dto) {
        PageList<QuestionAnswerVo> questionAnswerVoPageList = new PageList<>();
        Long coteriaId = dto.getCoterieId();
        Long createUserId = dto.getCreateUserId();
        /**
         * 校验参数
         */
        if (null == coteriaId || null == createUserId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        Integer pageNum = dto.getPageNum() == null ? 1 : dto.getPageNum();
        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Integer pageStartIndex = (pageNum - 1) * pageSize;
        QuestionExample example = new QuestionExample();
        example.setPageStartIndex(pageStartIndex);
        example.setPageSize(pageSize);

        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andCoterieIdEqualTo(coteriaId);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        /**
         *检查用户是否是圈主
         */
        // TODO: 2018/1/24 0024
        Boolean isCoteriaOwner = true;
        if (isCoteriaOwner) {
            criteria.andTargetIdEqualTo(String.valueOf(createUserId));
            example.setOrderByClause("answerd_flag asc,create_date desc");
            criteria.andAnswerdFlagNotEqualTo(QuestionAnswerConstants.AnswerdFlag.REJECT_ANSWERED);
            criteria.andIsValidEqualTo(QuestionAnswerConstants.validType.YES);
        } else {
            criteria.andCreateUserIdEqualTo(createUserId);
            example.setOrderByClause("create_date desc");
        }

        List<Question> list = this.questionDao.selectByExampleWithBLOBs(example);
        List<QuestionAnswerVo> questionAnswerVos = new ArrayList<>();
        for (Question question : list) {
            QuestionAnswerVo questionAnswerVo = new QuestionAnswerVo();
            QuestionVo questionVo = new QuestionVo();
            BeanUtils.copyProperties(question, questionVo);
            Long questionCreateUserId = question.getCreateUserId();
            if (null == questionCreateUserId) {
                questionVo.setUser(ResponseUtils.getResponseData(userApi.getUserSimple(createUserId)));
            }

            /**
             * 根据questionId 查询回答
             */
            if (QuestionAnswerConstants.AnswerdFlag.ANSWERED.compareTo(question.getAnswerdFlag()) == 0) {
                AnswerVo answerVo =
                        this.answerService.queryAnswerVoByquestionId(question.getKid());
                questionAnswerVo.setAnswer(answerVo);
            }
            questionAnswerVo.setQuestion(questionVo);
            questionAnswerVos.add(questionAnswerVo);
        }

        questionAnswerVoPageList.setCount(0L);
        questionAnswerVoPageList.setCurrentPage(pageNum);
        questionAnswerVoPageList.setPageSize(pageSize);
        questionAnswerVoPageList.setEntities(questionAnswerVos);
        return questionAnswerVoPageList;
    }
}
