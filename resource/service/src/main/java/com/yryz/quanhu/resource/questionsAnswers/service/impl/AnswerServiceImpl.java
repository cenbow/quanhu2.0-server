package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.read.api.ReadApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.AnswerDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerExample;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.APIservice;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.service.SendMessageService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.MessageBusinessVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private static final Integer AUDIOLENGTH_MAX=180*1000;
    private static final Integer AUDIOLENGTH_MIN=1*1000;

    private static final Integer IMGS_MAX=30;
    private static final Integer CONTENT_LENGTH_MAX=10000;

    @Autowired
    private AnswerDao answerDao;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private AnswerService answerService;

    @Autowired
    private APIservice apIservice;

    @Autowired
    OrderSDK orderSDK;

    @Reference
    private ResourceDymaicApi resourceDymaicApi;

    @Reference
    private ReadApi readApi;

    @Reference
    private ResourceApi resourceApi;

    @Autowired
    private SendMessageService questionMessageService;

    @Override
    @Transactional
    public AnswerVo saveAnswer(AnswerDto answerdto) {
        /**
         * 校验参数
         */
        Long questionId = answerdto.getQuestionId();
        Long coterieId = answerdto.getCoterieId();
        String content=answerdto.getContent();
        String answerAudio=answerdto.getAnswerAudio();
        String imgUrl=answerdto.getImgUrl();
        Long audioLength= answerdto.getAudioLength()==null?0L:answerdto.getAudioLength();

        if (null == questionId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        if(StringUtils.isBlank(imgUrl) && StringUtils.isBlank(content) && StringUtils.isBlank(answerAudio)){
            throw  QuanhuException.busiError("","音频回答和图文回答不能都为空","音频回答和图文回答不能都为空");
        }

        if((StringUtils.isNotBlank(imgUrl) || StringUtils.isNotBlank(content)) && StringUtils.isNotBlank(answerAudio)){
            throw  QuanhuException.busiError("","音频回答和图文回答互斥","音频回答和图片、文字回答只能选一方式回答");
        }


        if(StringUtils.isNotBlank(answerAudio) && (audioLength.longValue()<AUDIOLENGTH_MIN || audioLength>AUDIOLENGTH_MAX)){
            throw  QuanhuException.busiError("","有音频回答，音频时长最少1秒最多180秒","有音频回答，音频时长最少1秒最多180秒");
        }

        if(StringUtils.isBlank(answerAudio) && audioLength.longValue()>0){
            throw  QuanhuException.busiError("","无音频回答，应该无音频时长","无音频回答，应该无音频时长");
        }

        if(StringUtils.isNotBlank(content) && content.length()>CONTENT_LENGTH_MAX){
            throw  QuanhuException.busiError("","文字最多10000字","文字最多10000字");
        }


        if(StringUtils.isNotBlank(imgUrl) && imgUrl.split(",").length>IMGS_MAX){
            throw  QuanhuException.busiError("","图片最多上传30张","图片最多上传30张");
        }

        Question questionCheck=this.questionService.queryAvailableQuestionByKid(questionId);
        if(null==questionCheck){
            throw  QuanhuException.busiError("","提问不存在","提问不存在");
        }

        if(null !=questionCheck.getAnswerdFlag() && QuestionAnswerConstants.AnswerdFlag.NOt_ANSWERED.compareTo(questionCheck.getAnswerdFlag())==-1){
            throw  QuanhuException.busiError("","圈主已处理过该问题，不能再回答","圈主已处理过该问题，不能再回答");
        }

        if(questionCheck.getChargeAmount().longValue()>0 && QuestionAnswerConstants.OrderType.paid.compareTo(questionCheck.getOrderFlag())!=0){
            throw  QuanhuException.busiError("","该付费问题订单未完成，无法回答","该付费问题订单未完成，无法回答");
        }
        AnswerWithBLOBs answerWithBLOBs = new AnswerWithBLOBs();
        BeanUtils.copyProperties(answerdto, answerWithBLOBs);
        answerWithBLOBs.setKid(apIservice.getKid());
        Date createDate=new Date();
        answerWithBLOBs.setCreateDate(createDate);
        answerWithBLOBs.setDelFlag(CommonConstants.DELETE_NO);
        answerWithBLOBs.setRevision(0);
        answerWithBLOBs.setCityCode("");
        answerWithBLOBs.setGps("");
        answerWithBLOBs.setOperatorId("");
        answerWithBLOBs.setOrderFlag(QuestionAnswerConstants.OrderType.Not_paid);
        answerWithBLOBs.setOrderId("");
        answerWithBLOBs.setShelveFlag(CommonConstants.SHELVE_YES);
        answerWithBLOBs.setAnswerType(QuestionAnswerConstants.questionType.ONE_TO_ONE);
        /**
         *校验圈主是否禁言，圈粉是否删除
         */
        this.answerDao.insertSelective(answerWithBLOBs);

        //更新问题的回答状态和时间
        Question question=new Question();
        question.setKid(questionId);
        question.setAnswerdFlag(QuestionAnswerConstants.AnswerdFlag.ANSWERED);
        question.setOperateShelveDate(createDate);
        this.questionService.updateByPrimaryKeySelective(question);
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs, answerVo);

        //向圈主支付回答的费用
        if(null!=questionCheck.getChargeAmount()){
            if(questionCheck.getChargeAmount().longValue()>0){
              Long orderId=  orderSDK.executeOrder(OrderEnum.ANSWER_ORDER,answerdto.getCreateUserId(),questionCheck.getChargeAmount());
              if(null!=orderId){
                  answerWithBLOBs.setOrderFlag(QuestionAnswerConstants.OrderType.paid);
                  answerWithBLOBs.setOrderId(String.valueOf(orderId));
                  this.answerDao.updateByPrimaryKeySelective(answerWithBLOBs);
                  /**
                   * 发送获取奖励金消息
                   */
                  MessageBusinessVo messageBusinessVo =new MessageBusinessVo();
                  messageBusinessVo.setCoterieId(String.valueOf(answerWithBLOBs.getCoterieId()));
                  messageBusinessVo.setIsAnonymity(null);
                  messageBusinessVo.setKid(answerWithBLOBs.getKid());
                  messageBusinessVo.setModuleEnum(ModuleContants.ANSWER);
                  messageBusinessVo.setFromUserId(questionCheck.getCreateUserId());
                  messageBusinessVo.setTosendUserId(answerWithBLOBs.getCreateUserId());
                  messageBusinessVo.setTitle(answerWithBLOBs.getContent());
                  messageBusinessVo.setAmount(questionCheck.getChargeAmount());
                  questionMessageService.sendNotify4Question(messageBusinessVo, MessageConstant.ANSWER_PAYED,true);
              }
            }
        }

        /**
         * 向圈粉发送已回答消息
         */
        MessageBusinessVo messageBusinessVo =new MessageBusinessVo();
        messageBusinessVo.setCoterieId(String.valueOf(answerWithBLOBs.getCoterieId()));
        messageBusinessVo.setIsAnonymity(null);
        messageBusinessVo.setKid(questionCheck.getKid());
        messageBusinessVo.setModuleEnum(ModuleContants.ANSWER);
        messageBusinessVo.setFromUserId(answerWithBLOBs.getCreateUserId());
        messageBusinessVo.setTosendUserId(questionCheck.getCreateUserId());
        messageBusinessVo.setTitle(questionCheck.getContent());
        messageBusinessVo.setAmount(questionCheck.getChargeAmount());
        questionMessageService.sendNotify4Question(messageBusinessVo,MessageConstant.QUESTION_HAVE_ANSWERED,true);

        /**
         * 资源聚合
         */
        ResourceTotal resourceTotal=new ResourceTotal();
        resourceTotal.setCreateDate(DateUtils.getDateTime());
        QuestionAnswerVo questionAnswerVo=new QuestionAnswerVo();
        QuestionVo questionVo=this.questionService.getDetail(questionId,questionCheck.getCreateUserId());
        AnswerVo answerVo1=this.answerService.queryAnswerVoByquestionId(questionId);
        questionAnswerVo.setQuestion(questionVo);
        questionAnswerVo.setAnswer(answerVo1);
        if(questionAnswerVo!=null) {
            resourceTotal.setExtJson(JsonUtils.toFastJson(questionAnswerVo));
        }
        if(QuestionAnswerConstants.showType.ONESELF.compareTo(questionVo.getIsOnlyShowMe())==0){
            resourceTotal.setIntimate(ResourceEnum.INTIMATE_TRUE);
        }else{
            resourceTotal.setIntimate(ResourceEnum.INTIMATE_FALSE);
        }
        resourceTotal.setContent(answerVo1.getContent());
        resourceTotal.setTitle(answerVo1.getContent());
        resourceTotal.setPrice(questionVo.getChargeAmount());
        resourceTotal.setPublicState(ResourceEnum.PUBLIC_STATE_FALSE);
        resourceTotal.setResourceId(answerVo1.getKid());
        resourceTotal.setModuleEnum(Integer.valueOf(ModuleContants.ANSWER));
        resourceTotal.setUserId(answerVo1.getCreateUserId());
        resourceTotal.setCoterieId(String.valueOf(coterieId));
        resourceDymaicApi.commitResourceDymaic(resourceTotal);

        /**
         * 私圈信息 聚合
         */
        ResourceTotal resourceTotalCoterie=new ResourceTotal();
        resourceTotalCoterie.setCreateDate(DateUtils.getDateTime());
        resourceTotalCoterie.setCoterieId(String.valueOf(coterieId));
        CoterieInfo coterieInfo=this.apIservice.getCoterieinfo(questionCheck.getCoterieId());
        if(null!=coterieInfo) {
            resourceTotal.setPublicState(ResourceEnum.PUBLIC_STATE_FALSE);
            resourceTotalCoterie.setExtJson(JsonUtils.toFastJson(coterieInfo));
            resourceTotalCoterie.setResourceId(coterieInfo.getCoterieId());
            resourceTotalCoterie.setModuleEnum(Integer.valueOf(ModuleContants.COTERIE));
            resourceTotalCoterie.setUserId(Long.valueOf(coterieInfo.getOwnerId()));
            resourceTotalCoterie.setContent(coterieInfo.getIntro());
            resourceTotalCoterie.setTitle(coterieInfo.getName());
            resourceDymaicApi.commitResourceDymaic(resourceTotalCoterie);
        }
        return answerVo;
    }

    @Override
    @Transactional
    public Integer deleteAnswer(AnswerDto answerdto) {
        /**
         * 校验参数
         */
        Long kid = answerdto.getKid();
        Byte delFlag = answerdto.getDelFlag();
        if (null == kid || null == delFlag) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerdto, answer);
        //删除聚合的资源
        resourceApi.deleteResourceById(String.valueOf(kid));
        return this.answerDao.updateByPrimaryKey(answer);
    }


    /**
     * 查询回答详情
     * @param kid
     * @return
     */
    @Override
    public AnswerVo getDetail(Long kid) {
        /**
         *校验传入的参数
         */
        if (null == kid) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        AnswerExample example=new AnswerExample();
        AnswerExample.Criteria criteria=example.createCriteria();
        criteria.andQuestionIdEqualTo(kid);
        List<AnswerWithBLOBs> answerWithBLOBsList = this.answerDao.selectByExampleWithBLOBs(example);
        if(answerWithBLOBsList==null || answerWithBLOBsList.isEmpty()){
            return null;
        }
        AnswerWithBLOBs answerWithBLOBs=answerWithBLOBsList.get(0);
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs, answerVo);
        Long createUserId = answerWithBLOBs.getCreateUserId();
        if (null != createUserId) {
            answerVo.setUser(apIservice.getUser(createUserId));
        }
        answerVo.setModuleEnum(ModuleContants.ANSWER);

        //虚拟阅读数
        readApi.read(kid,answerWithBLOBs.getCreateUserId());
        return answerVo;
    }

    /**
     * 通过questionId查询answer
     * @param kid
     * @return
     */
    @Override
    public AnswerVo queryAnswerVoByquestionId(Long kid) {
        AnswerExample example = new AnswerExample();
        AnswerExample.Criteria criteria = example.createCriteria();
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andQuestionIdEqualTo(kid);
        List<AnswerWithBLOBs> answerWithBLOBsList = this.answerDao.selectByExampleWithBLOBs(example);
        if (!answerWithBLOBsList.isEmpty()) {
            AnswerWithBLOBs answerWithBLOBs = answerWithBLOBsList.get(0);
            AnswerVo answerVo = new AnswerVo();
            BeanUtils.copyProperties(answerWithBLOBs, answerVo);
            Long createUserId = answerWithBLOBs.getCreateUserId();
            if (null != createUserId) {
                answerVo.setUser(apIservice.getUser(createUserId));
            }
            answerVo.setModuleEnum(ModuleContants.ANSWER);
            return answerVo;
        }
        return null;
    }
}
