package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderQuestionNotifyServiceImpl implements IOrderNotifyService {

    @Autowired
    private QuestionService questionService;

    @Override
    public String getModuleEnum() {
        return ResourceTypeEnum.QUESTION;
    }

    @Override
    public void notify(OutputOrder outputOrder) {
     if(outputOrder!=null && outputOrder.getBizContent()!=null){
         Question question=JSONObject.parseObject(outputOrder.getBizContent(), Question.class);
         question.setOrderFlag(QuestionAnswerConstants.OrderType.paid);
         questionService.updateByPrimaryKeySelective(question);
     }
    }
}
