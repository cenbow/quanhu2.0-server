package com.yryz.quanhu.resource.questionsAnswers.service;

import com.yryz.common.message.MessageConstant;
import com.yryz.quanhu.resource.questionsAnswers.vo.MessageBusinessVo;

public interface SendMessageService {
    public Boolean sendNotify4Question(MessageBusinessVo messageBusinessVo, MessageConstant messageConstant);
}
