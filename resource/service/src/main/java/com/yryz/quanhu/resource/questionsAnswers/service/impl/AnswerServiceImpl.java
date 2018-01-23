package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.resource.questionsAnswers.dao.AnswerDao;
import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Override
    public AnswerVo saveAnswer(AnswerWithBLOBs answerWithBLOBs) {
        /**
         * 校验参数
         */
        Long questionId=answerWithBLOBs.getQuestionId();
        String coteriaId=answerWithBLOBs.getCoterieId();
        if(null ==questionId || StringUtils.isBlank(coteriaId)){
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        /**
         *校验圈主是否禁言，圈粉是否删除
         */
        this.answerDao.insertSelective(answerWithBLOBs);
        AnswerVo answerVo=new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs,answerVo);
        return answerVo;
    }

    @Override
    public Integer deleteAnswer(Answer answer) {
        /**
         * 校验参数
         */
        Long kid=answer.getKid();
        Byte shelveFlag=answer.getShelveFlag();
        if(null==kid ||null ==shelveFlag){
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        return this.answerDao.updateByPrimaryKey(answer);
    }

    @Override
    public AnswerVo getDetail(Long kid) {
        AnswerWithBLOBs answerWithBLOBs= this.answerDao.selectByPrimaryKey(kid);
        AnswerVo answerVo =new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs,answerVo);
        return answerVo;
    }


}
