package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.message.MessageConstant;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.SendMessageService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.MessageBusinessVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class OrderQuestionNotifyServiceImpl implements IOrderNotifyService {

    private static final Logger logger= LoggerFactory.getLogger(OrderQuestionNotifyServiceImpl.class);

    @Autowired
    private QuestionService questionService;

    @Autowired
    private SendMessageService questionMessageService;

    @Override
    public String getModuleEnum() {
        return ModuleContants.QUESTION;
    }

    @Override
    @Transactional
    public void notify(OutputOrder outputOrder) {
     if(outputOrder!=null && outputOrder.getBizContent()!=null){
         Question question=JSONObject.parseObject(outputOrder.getBizContent(), Question.class);
         Question questionByQuery=this.questionService.queryAvailableQuestionByKid(question.getKid());
         questionByQuery.setOrderFlag(QuestionAnswerConstants.OrderType.paid);
         questionService.updateByPrimaryKeySelective(questionByQuery);

         /**
          * 发送通知消息
          */
         MessageBusinessVo messageBusinessVo=new MessageBusinessVo();
         messageBusinessVo.setAmount(question.getChargeAmount());
         messageBusinessVo.setCoterieId(String.valueOf(question.getCoterieId()));
         messageBusinessVo.setIsAnonymity(question.getIsAnonymity());
         messageBusinessVo.setKid(question.getKid());
         messageBusinessVo.setModuleEnum(ModuleContants.QUESTION);
         messageBusinessVo.setFromUserId(question.getCreateUserId());
         messageBusinessVo.setTosendUserId(question.getCreateUserId());
         messageBusinessVo.setTitle(question.getContent());
         messageBusinessVo.setImgUrl("");
         Boolean sendMessageResult = questionMessageService.sendNotify4Question(messageBusinessVo, MessageConstant.QUESTION_PAYED,true);
         if (!sendMessageResult) {
             logger.error("问题支付成功发送问题失败,提问的kid{}", question.getKid());
         }
     }
    }
}
