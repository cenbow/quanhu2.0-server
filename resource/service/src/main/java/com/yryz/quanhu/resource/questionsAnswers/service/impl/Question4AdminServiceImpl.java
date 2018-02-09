package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.read.api.ReadApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.AnswerDao;
import com.yryz.quanhu.resource.questionsAnswers.dao.QuestionDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerExample;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.entity.QuestionExample;
import com.yryz.quanhu.resource.questionsAnswers.service.*;
import com.yryz.quanhu.resource.questionsAnswers.vo.*;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wanght
 * @date 2018-02-1-22
 */
@Service
public class Question4AdminServiceImpl implements Question4AdminService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private APIservice apIservice;

    @Autowired
    private AnswerService answerService;

    @Reference
    private ReadApi readApi;

    @Autowired
    private OrderSDK orderSDK;

    @Reference
    private CountApi countApi;

    @Reference
    private MessageAPI messageAPI;

    @Reference
    private CoterieMemberAPI coterieMemberAPI;

    @Reference
    private EventAPI eventAPI;

    @Reference
    private ResourceDymaicApi resourceDymaicApi;

    @Autowired
    private SendMessageService questionMessageService;

    /** 查询问答列表
     * @param dto
     * @return
     */
    @Override
    public PageList<QuestionAdminVo> queryQuestionAnswerList(QuestionDto dto) {
        PageList<QuestionAdminVo> questionAdminVoList = new PageList<>();
        Integer pageNum = dto.getCurrentPage() == null ? 1 : dto.getCurrentPage();
        Integer pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();
        Integer pageStartIndex = (pageNum - 1) * pageSize;
        QuestionExample example = new QuestionExample();
        example.setPageStartIndex(pageStartIndex);
        example.setPageSize(pageSize);
        example.setOrderByClause("create_date desc");

        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andOrderFlagEqualTo(QuestionAnswerConstants.OrderType.paid);
        if(StringUtils.isNotBlank(dto.getLastAnswerDateBegin()) && StringUtils.isNotBlank(dto.getLastAnswerDateEnd())){
            AnswerExample answerExample=new AnswerExample();
            AnswerExample.Criteria answerCriteria=answerExample.createCriteria();
            answerCriteria.andCreateDateBetween(DateUtils.parseDate(dto.getLastAnswerDateBegin()),
                    DateUtils.parseDate(dto.getLastAnswerDateEnd()));
            List<Answer> answers=this.answerDao.selectByExample(answerExample);
            if(answers!=null && !answers.isEmpty()){
                List<Long> kids=new ArrayList<>();
                for(Answer answer:answers){
                    kids.add(answer.getQuestionId());
                }
                criteria.andKidIn(kids);
            }
        }

        Long coteriaId = dto.getCoterieId();
        if(coteriaId!=null) {
            criteria.andCoterieIdEqualTo(coteriaId);
        }
        Long createUserId = dto.getCreateUserId();
        if(null!=createUserId) {
            criteria.andCreateUserIdEqualTo(createUserId);
        }

        if(StringUtils.isNotBlank(dto.getContent())){
            criteria.andContentLike("%"+dto.getContent()+"%");
        }

        if(dto.getShelveFlag()!=null){
            criteria.andShelveFlagEqualTo(dto.getShelveFlag());
        }
        if(dto.getIsOnlyShowMe()!=null){
            criteria.andIsOnlyShowMeEqualTo(dto.getIsOnlyShowMe());
        }

        if(dto.getAnswerdFlag()!=null){
            criteria.andAnswerdFlagEqualTo(dto.getAnswerdFlag());
        }
        if(StringUtils.isNotBlank(dto.getBeginDate()) && StringUtils.isNotBlank(dto.getEndDate())){
            criteria.andCreateDateBetween(DateUtils.parseDate(dto.getBeginDate()),DateUtils.parseDate(dto.getEndDate()));
        }
        example.setOrderByClause("create_date desc");
        Long count=this.questionDao.countByExample(example);
        List<Question> list = this.questionDao.selectByExampleWithBLOBs(example);
        List<QuestionAdminVo> questionAdminVos = new ArrayList<>();
        for (Question question : list) {
            QuestionAdminVo questionAdminVo = new QuestionAdminVo();
            BeanUtils.copyProperties(question, questionAdminVo);
            Long questionCreateUserId = question.getCreateUserId();
            if (null != questionCreateUserId) {
                questionAdminVo.setUser(apIservice.getUser(questionCreateUserId));
            }

            /**
             * 根据questionId 查询回答
             */
            if (QuestionAnswerConstants.AnswerdFlag.ANSWERED.compareTo(question.getAnswerdFlag()) == 0) {
                AnswerVo answerVo =
                        this.answerService.queryAnswerVoByquestionId(question.getKid());
                questionAdminVo.setAnswerDate(answerVo.getCreateDate());
            }
            CoterieInfo coterieInfo= apIservice.getCoterieinfo(question.getCoterieId());
            if(null!=coterieInfo){
                questionAdminVo.setCoterieName(coterieInfo.getName());
            }
            questionAdminVos.add(questionAdminVo);
        }

        questionAdminVoList.setCount(count);
        questionAdminVoList.setCurrentPage(pageNum);
        questionAdminVoList.setPageSize(pageSize);
        questionAdminVoList.setEntities(questionAdminVos);
        return questionAdminVoList;
    }

    /**
     * 查询有用的提问
     *
     * @param kid
     * @return
     */
    @Override
    public QuestionAnswerVo queryAvailableQuestionByKid(Long kid) {
        QuestionAnswerVo questionAnswerVo=new QuestionAnswerVo();
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andKidEqualTo(kid);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        List<Question> questions = this.questionDao.selectByExample(example);
        if (null != questions && !questions.isEmpty()) {
            Question question=questions.get(0);
            QuestionVo questionVo=new QuestionVo();
            BeanUtils.copyProperties(question,questionVo);
            if(question.getCreateUserId()!=null){
              UserSimpleVO userSimpleVO= apIservice.getUser(question.getCreateUserId());
              questionVo.setUser(userSimpleVO);
            }
            if(question.getTargetId()!=null){
                UserSimpleVO userSimpleVO= apIservice.getUser(Long.valueOf(question.getTargetId()));
                questionVo.setTargetUser(userSimpleVO);
            }
            questionAnswerVo.setQuestion(questionVo);

            if(QuestionAnswerConstants.AnswerdFlag.ANSWERED.compareTo(question.getAnswerdFlag())==0){
                AnswerVo answerVo=this.answerService.queryAnswerVoByquestionId(question.getKid());
                questionAnswerVo.setAnswer(answerVo);
            }
        }
        return questionAnswerVo;
    }


    /**
     * 问题下架
     * @param kid
     * @return
     */
    @Override
    public Integer shalveDown(Long kid) {
        Question question=new Question();
        question.setKid(kid);
        question.setShelveFlag(CommonConstants.SHELVE_NO);
        //执行下架数据库操作
        int result=this.questionDao.updateByPrimaryKeySelective(question);
        /**
         * 问题下线通知
         */
        if(result>0) {
            Question questionByQuery = this.questionDao.selectByPrimaryKey(kid);
            if (null != questionByQuery) {
                MessageBusinessVo messageBusinessVo = new MessageBusinessVo();
                messageBusinessVo.setCoterieId(String.valueOf(questionByQuery.getCoterieId()));
                messageBusinessVo.setIsAnonymity(null);
                messageBusinessVo.setKid(questionByQuery.getKid());
                messageBusinessVo.setModuleEnum(ModuleContants.QUESTION);
                messageBusinessVo.setFromUserId(questionByQuery.getCreateUserId());
                messageBusinessVo.setTitle(questionByQuery.getContent());
                messageBusinessVo.setTosendUserId(questionByQuery.getCreateUserId());
                messageBusinessVo.setAmount(questionByQuery.getChargeAmount());
                questionMessageService.sendNotify4Question(messageBusinessVo, MessageConstant.QUESTIONANSWER_HAVE_SHALVEDWON, false);

                if (QuestionAnswerConstants.AnswerdFlag.ANSWERED.compareTo(questionByQuery.getAnswerdFlag()) == 0) {
                    messageBusinessVo.setTosendUserId(Long.valueOf(questionByQuery.getTargetId()));
                    questionMessageService.sendNotify4Question(messageBusinessVo, MessageConstant.QUESTIONANSWER_HAVE_SHALVEDWON, false);
                }
            }
        }
        return  result;
    }


    private Boolean checkIdentity(Long userId, Long citeriaId, MemberConstant.Permission permission) {
        //圈主10 成员20 路人未审请30 路人待审核40
        Response<Integer> data = coterieMemberAPI.permission(userId, citeriaId);
        if (ResponseConstant.SUCCESS.getCode().equals(data.getCode())) {
            if (null != data.getData()) {
                if (permission.getStatus().compareTo(data.getData()) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

}
