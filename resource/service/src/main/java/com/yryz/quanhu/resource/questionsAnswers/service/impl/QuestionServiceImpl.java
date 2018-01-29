package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.QuestionDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.entity.QuestionExample;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionMessageService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.resource.topic.vo.BehaviorVo;
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
public class QuestionServiceImpl implements QuestionService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionService.class);

    @Autowired
    private QuestionDao questionDao;

    @Autowired
    private APIservice apIservice;

    @Autowired
    private AnswerService answerService;


    @Autowired
    private OrderSDK orderSDK;

    @Reference
    private CountApi countApi;

    @Reference
    private MessageAPI messageAPI;

    @Reference
    private CoterieMemberAPI coterieMemberAPI;


    @Autowired
    private QuestionMessageService questionMessageService;

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
        Long createUserId = question.getCreateUserId();
        Byte isAnonymity = question.getIsAnonymity();
        Byte isOnlyShowMe = question.getIsOnlyShowMe();
        if (null == citeriaId || StringUtils.isBlank(targetId) || StringUtils.isBlank(conttent)
                || isAnonymity == null || null == isOnlyShowMe || null == createUserId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        /**
         *校验私圈是否合法
         */
        if(targetId.equals(String.valueOf(createUserId))){
            throw QuanhuException.busiError("不能向自己提问。");
        }
        CoterieInfo coterieInfo = apIservice.getCoterieinfo(citeriaId);
        if (null == coterieInfo) {
            throw QuanhuException.busiError("提问的私圈不存在。");
        }
        Integer consultingFee = coterieInfo.getConsultingFee() == null ? 0 : coterieInfo.getConsultingFee();
        //保存提问费用
        question.setChargeAmount(Long.valueOf(consultingFee));

        //圈主10 成员20 路人未审请30 路人待审核40
        if (!checkIdentity(createUserId, Long.valueOf(citeriaId), MemberConstant.Permission.MEMBER)) {
            throw QuanhuException.busiError("非圈粉不能提问");
        }

        if (!checkIdentity(Long.valueOf(targetId), Long.valueOf(citeriaId), MemberConstant.Permission.OWNER)) {
            throw QuanhuException.busiError("不能向非圈主用户提问.");
        }

        question.setKid(apIservice.getKid());
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

        questionDao.insertSelective(question);
        if (question.getChargeAmount().longValue() > 0) {
            InputOrder inputOrder = new InputOrder();
            inputOrder.setBizContent(JSON.toJSONString(question));
            inputOrder.setCost(question.getChargeAmount());
            inputOrder.setCoterieId(question.getCoterieId());
            inputOrder.setCreateUserId(question.getCreateUserId());
            inputOrder.setFromId(question.getCreateUserId());
            inputOrder.setToId(Long.valueOf(AccountEnum.SYSID));
            inputOrder.setModuleEnum(ResourceTypeEnum.QUESTION);
            inputOrder.setOrderEnum(OrderEnum.QUESTION_ORDER);
            inputOrder.setResourceId(question.getKid());
            Long orderId = orderSDK.createOrder(inputOrder);
            question.setOrderId(String.valueOf(orderId));
            question.setOrderFlag(QuestionAnswerConstants.OrderType.Not_paid);
            this.questionDao.updateByPrimaryKeySelective(question);
        }
        Boolean  sendResult=questionMessageService.sendNotify4Question(question,MessageConstant.QUESTION_TO_BE_ANSWERED);
        if(!sendResult){
            logger.info("提问被删除，发送消息失败");
        }
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
        /**
         * 圈粉删除问题，如果是付费问题，则进行退款，并通知圈粉
         */
        if(questionBySearch.getChargeAmount()>0){
            Long orderId=orderSDK.executeOrder(OrderEnum.NO_ANSWER_ORDER,questionBySearch.getCreateUserId(),questionBySearch.getChargeAmount());
            if(null!=orderId){
                questionBySearch.setOrderFlag(QuestionAnswerConstants.OrderType.Have_refund);
                questionBySearch.setRefundOrderId(String.valueOf(orderId));
                //发送通知消息
              Boolean  sendResult=questionMessageService.sendNotify4Question(questionBySearch,MessageConstant.QUESTION_DELETE);
              if(!sendResult){
                  logger.info("提问被删除，发送消息失败");
              }
            }else{
                questionBySearch.setOrderFlag(QuestionAnswerConstants.OrderType.For_refund);
            }
        }
        return this.questionDao.updateByPrimaryKeySelective(questionBySearch);
    }


    /**
     * 查询问题的详情
     *
     * @param kid
     * @return
     */
    @Override
    public QuestionAnswerVo getDetail(Long kid, Long userId) {
        QuestionAnswerVo questionAnswerVo = new QuestionAnswerVo();
        /**
         * 参数校验
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria criteria = example.createCriteria();
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
        Question questionBysearch = questions.get(0);
        Long createUserId = questionBysearch.getCreateUserId();
        Long targetId=Long.valueOf(questionBysearch.getTargetId());
        if (questionBysearch.getIsOnlyShowMe().compareTo(QuestionAnswerConstants.showType.ONESELF) == 0) {
            if (createUserId.compareTo(userId) != 0) {
                throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TOREAD);
            }
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(questionBysearch, questionVo);
        if (null != createUserId) {
            questionVo.setUser(apIservice.getUser(createUserId));
        }
        if(null !=targetId){
            questionVo.setTargetUser(apIservice.getUser(targetId));
        }
        questionVo.setModuleEnum(ResourceTypeEnum.QUESTION);
        questionAnswerVo.setQuestion(questionVo);
        questionAnswerVo.setAnswer(this.answerService.queryAnswerVoByquestionId(questionVo.getKid()));
        return questionAnswerVo;
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
        String targetId = question.getTargetId();
        if (!String.valueOf(userId).equals(targetId)) {
            throw new QuanhuException(ExceptionEnum.USER_NO_RIGHT_TOREJECT);
        }
        question.setAnswerdFlag(QuestionAnswerConstants.AnswerdFlag.REJECT_ANSWERED);

        /**
         * 圈粉删除问题，如果是付费问题，则进行退款，并通知圈粉
         */
        if(question.getChargeAmount()>0){
           Long orderId= orderSDK.executeOrder(OrderEnum.NO_ANSWER_ORDER,question.getCreateUserId(),question.getChargeAmount());
           if(null!=orderId){
               question.setRefundOrderId(String.valueOf(orderId));
               question.setOrderFlag(QuestionAnswerConstants.OrderType.Have_refund);
           }else{
               question.setOrderFlag(QuestionAnswerConstants.OrderType.For_refund);
           }
        }
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
        Boolean isCoteriaOwner = checkIdentity(createUserId, coteriaId, MemberConstant.Permission.OWNER);
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
            if (null != questionCreateUserId) {
                questionVo.setUser(apIservice.getUser(questionCreateUserId));
            }

            if(null !=question.getTargetId()){
                questionVo.setTargetUser(apIservice.getUser(Long.valueOf(question.getTargetId())));
            }
            questionVo.setModuleEnum(ResourceTypeEnum.QUESTION);
            Response<Map<String, Long>> countData = countApi.getCount("10,11", questionVo.getKid(), null);
            if (ResponseConstant.SUCCESS.getCode().equals(countData.getCode())) {
                Map<String, Long> count = countData.getData();
                if (count != null) {
                    BehaviorVo behaviorVo = new BehaviorVo();
                    if (count.containsKey("likeCount")) {
                        behaviorVo.setLikeCount(count.get("likeCount"));
                    }
                    if (count.containsKey("commentCount")) {
                        behaviorVo.setCommentCount(count.get("commentCount"));
                    }
                    questionVo.setBehaviorVo(behaviorVo);
                }
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

    /**
     * 查询有用的提问
     *
     * @param kid
     * @return
     */
    @Override
    public Question queryAvailableQuestionByKid(Long kid) {
        QuestionExample example = new QuestionExample();
        QuestionExample.Criteria criteria = example.createCriteria();
        criteria.andKidEqualTo(kid);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andIsValidEqualTo(QuestionAnswerConstants.validType.YES);
        List<Question> questions = this.questionDao.selectByExample(example);
        if (null != questions && !questions.isEmpty()) {
            return questions.get(0);
        }
        return null;
    }

    @Override
    public Integer updateByPrimaryKeySelective(Question question) {
        return this.questionDao.updateByPrimaryKeySelective(question);
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


    /**
     * job 失效问题退款
     * @param
     */
    @Override
    public void updateInValidQuestionRefund() {
        /**
         * 查询失效的问题
         */
        QuestionExample example=new QuestionExample();
        QuestionExample.Criteria criteria=example.createCriteria();
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        criteria.andIsValidEqualTo(QuestionAnswerConstants.validType.YES);
        criteria.andChargeAmountGreaterThan(0L);
        criteria.andOrderFlagEqualTo(QuestionAnswerConstants.OrderType.paid);
        Calendar invaidDate=Calendar.getInstance();
        invaidDate.add(Calendar.HOUR_OF_DAY,-48);
        criteria.andCreateDateLessThan(invaidDate.getTime());
        criteria.andAnswerdFlagEqualTo(QuestionAnswerConstants.AnswerdFlag.NOt_ANSWERED);
        List<Question> questions=this.questionDao.selectByExample(example);
        for(Question question:questions){
            logger.info("==退款的提问编号==:{}",question.getKid());
            question.setIsValid(QuestionAnswerConstants.validType.NO);
            /**
             * 失效进行全额退款
             */
            Long orderId=orderSDK.executeOrder(OrderEnum.NO_ANSWER_ORDER,question.getCreateUserId(),question.getChargeAmount());
            if(null!=orderId){
                question.setOrderFlag(QuestionAnswerConstants.OrderType.Have_refund);
                question.setRefundOrderId(String.valueOf(orderId));
            }else{
                question.setOrderFlag(QuestionAnswerConstants.OrderType.For_refund);
            }
            this.questionDao.updateByPrimaryKeySelective(question);
        }
   }
}
