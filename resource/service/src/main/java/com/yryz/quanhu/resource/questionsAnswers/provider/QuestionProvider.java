package com.yryz.quanhu.resource.questionsAnswers.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.BeanUtils;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dto.QuestionDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Question;
import com.yryz.quanhu.resource.questionsAnswers.service.QuestionService;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionAnswerVo;
import com.yryz.quanhu.resource.questionsAnswers.vo.QuestionVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;


@Service(interfaceClass = QuestionApi.class)
public class QuestionProvider implements QuestionApi {

    private static final Logger logger = LoggerFactory.getLogger(QuestionProvider.class);

    @Autowired
    private QuestionService questionService;

    /**
     * 圈粉发布问题
     * @param questionDto
     * @return
     */
    @Override
    public Response<QuestionVo> saveQuestion(QuestionDto questionDto) {
        Question question = new Question();
        BeanUtils.copyProperties(question,questionDto);
        try {
            Question data=questionService.saveQuestion(question);
            QuestionVo vo =new QuestionVo();
            BeanUtils.copyProperties(vo,data);
            return ResponseUtils.returnObjectSuccess(vo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 标记删除提问
     * @param kid
     * @param userId
     * @return
     */
    @Override
    public Response<Integer> deleteQuestion(Long kid, Long userId) {
        try {
            int result = this.questionService.deleteQuestion(kid,userId);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }


    @Override
    public Response<Integer> rejectAnswerQuestion(Long kid, Long userId) {
        try {
            int result = this.questionService.rejectAnswer(kid,userId);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<QuestionAnswerVo>> queryQuestionAnswerVoList(QuestionDto questionDto) {
        try {
            PageList<QuestionAnswerVo> result = this.questionService.queryQuestionAnswerList(questionDto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<QuestionVo> queryQuestionDetail(Long kid, Long userId) {
        try {
            QuestionVo result = this.questionService.getDetail(kid,userId);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}

