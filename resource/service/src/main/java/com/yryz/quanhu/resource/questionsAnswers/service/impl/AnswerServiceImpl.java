package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.resource.questionsAnswers.dao.AnswerDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import net.minidev.json.writer.BeansMapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Reference
    private UserApi userApi;

    @Override
    public AnswerVo saveAnswer(AnswerDto answerdto) {
        /**
         * 校验参数
         */
        Long questionId = answerdto.getQuestionId();
        String coteriaId = answerdto.getCoterieId();
        if (null == questionId || StringUtils.isBlank(coteriaId)) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        AnswerWithBLOBs answerWithBLOBs=new AnswerWithBLOBs();
        BeanUtils.copyProperties(answerdto,answerWithBLOBs);
        answerWithBLOBs.setCreateDate(new Date());
        /**
         *校验圈主是否禁言，圈粉是否删除
         */
        this.answerDao.insertSelective(answerWithBLOBs);
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs, answerVo);
        return answerVo;
    }

    @Override
    public Integer deleteAnswer(AnswerDto answerdto) {
        /**
         * 校验参数
         */
        Long kid = answerdto.getKid();
        Byte shelveFlag = answerdto.getShelveFlag();
        if (null == kid || null == shelveFlag) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        Answer answer=new Answer();
        BeanUtils.copyProperties(answerdto,answer);
        return this.answerDao.updateByPrimaryKey(answer);
    }

    /**
     * 查询查询回答详情
     * @param kid
     * @return
     */
    @Override
    public AnswerVo getDetail(Long kid, Long userId) {
        /**
         *校验传入的参数
         */
        if (null == kid || null == userId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        AnswerWithBLOBs answerWithBLOBs = this.answerDao.selectByPrimaryKey(kid);
        if (null == answerWithBLOBs) {
            throw QuanhuException.busiError("查询的回答不存在");
        }
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs, answerVo);
        Long createUserId = answerWithBLOBs.getCreateUserId();
        if (null != createUserId) {
            Response<UserSimpleVO> userSimpleVOResponse = userApi.getUserSimple(String.valueOf(createUserId));
            if (ResponseConstant.SUCCESS.getCode().equals(userSimpleVOResponse.getCode())) {
                UserSimpleVO userSimpleVO = userSimpleVOResponse.getData();
                answerVo.setUser(userSimpleVO);
            }
        }
        return answerVo;
    }


}
