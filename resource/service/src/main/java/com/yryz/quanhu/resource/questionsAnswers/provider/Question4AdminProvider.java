package com.yryz.quanhu.resource.questionsAnswers.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.BeanUtils;
import com.yryz.quanhu.resource.questionsAnswers.api.Question4AdminApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.Question4AdminService;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAdminVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


@Service(interfaceClass = Question4AdminApi.class)
public class Question4AdminProvider implements Question4AdminApi {

    private static final Logger logger = LoggerFactory.getLogger(Question4AdminProvider.class);

    @Autowired
    private Question4AdminService questionService;


    @Override
    public Response<Integer> shalveDown(Long kid) {
        try {
            Integer result = this.questionService.shalveDown(kid);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<QuestionAdminVo>> queryQuestionAnswerVoList(QuestionDto questionDto) {
        try {
            PageList<QuestionAdminVo> result = this.questionService.queryQuestionAnswerList(questionDto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }



    @Override
    public Response<QuestionAnswerVo> queryDetail(Long kid) {
        try {
            QuestionAnswerVo result= this.questionService.queryAvailableQuestionByKid(kid);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}

