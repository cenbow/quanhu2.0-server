package com.yryz.quanhu.resource.questionsAnswers.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = AnswerApi.class)
public class AnswerProvider implements AnswerApi {
    private static final Logger logger = LoggerFactory.getLogger(AnswerApi.class);

    @Autowired
    private AnswerService answerService;

    @Override
    public Response<AnswerVo> saveAnswer(AnswerDto answerDto) {
        try {
            AnswerVo vo = answerService.saveAnswer(answerDto);
            return ResponseUtils.returnObjectSuccess(vo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> deletetAnswer(AnswerDto answerDto) {
        try {
            Integer result = this.answerService.deleteAnswer(answerDto);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 回答详情
     * @param kid
     * @return
     */
    @Override
    public Response<AnswerVo> getDetail(Long kid) {
        try {
            AnswerVo result = this.answerService.getDetail(kid);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("注册未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }


}
