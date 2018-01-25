package com.yryz.quanhu.resource.questionsAnswers.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.resource.questionsAnswers.constants.QuestionAnswerConstants;
import com.yryz.quanhu.resource.questionsAnswers.dao.AnswerDao;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerExample;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import com.yryz.quanhu.resource.questionsAnswers.service.AnswerService;
import com.yryz.quanhu.resource.questionsAnswers.vo.AnswerVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    @Autowired
    private AnswerDao answerDao;

    @Reference
    private IdAPI idAPI;

    @Reference
    private UserApi userApi;

    @Override
    public AnswerVo saveAnswer(AnswerDto answerdto) {
        /**
         * 校验参数
         */
        Long questionId = answerdto.getQuestionId();
        Long coterieId = answerdto.getCoterieId();
        if (null == questionId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }
        AnswerWithBLOBs answerWithBLOBs = new AnswerWithBLOBs();
        BeanUtils.copyProperties(answerdto, answerWithBLOBs);
        answerWithBLOBs.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
        answerWithBLOBs.setCreateDate(new Date());
        answerWithBLOBs.setDelFlag(CommonConstants.DELETE_NO);
        answerWithBLOBs.setShelveFlag(CommonConstants.SHELVE_YES);
        answerWithBLOBs.setAnswerType(QuestionAnswerConstants.questionType.ONE_TO_ONE);
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
        Answer answer = new Answer();
        BeanUtils.copyProperties(answerdto, answer);
        return this.answerDao.updateByPrimaryKey(answer);
    }

    /**
     * 查询查询回答详情
     *
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
        AnswerExample example=new AnswerExample();
        AnswerExample.Criteria criteria=example.createCriteria();
        criteria.andKidEqualTo(kid);
        criteria.andDelFlagEqualTo(CommonConstants.DELETE_NO);
        criteria.andShelveFlagEqualTo(CommonConstants.SHELVE_YES);
        List<AnswerWithBLOBs> answerWithBLOBsList = this.answerDao.selectByExampleWithBLOBs(example);
        if (null==answerWithBLOBsList || answerWithBLOBsList.isEmpty()) {
            //throw QuanhuException.busiError("查询的回答不存在");
            return null;
        }
        AnswerWithBLOBs answerWithBLOBs=answerWithBLOBsList.get(0);
        AnswerVo answerVo = new AnswerVo();
        BeanUtils.copyProperties(answerWithBLOBs, answerVo);
        Long createUserId = answerWithBLOBs.getCreateUserId();
        if (null != createUserId) {
            answerVo.setUser(ResponseUtils.getResponseData(userApi.getUserSimple(createUserId)));
        }
        return answerVo;
    }

    @Override
    public AnswerVo queryAnswerVoByquestionId(Long kid) {
        AnswerExample example = new AnswerExample();
        AnswerExample.Criteria criteria = example.createCriteria();
        criteria.andQuestionIdEqualTo(kid);
        List<AnswerWithBLOBs> answerWithBLOBsList = this.answerDao.selectByExampleWithBLOBs(example);
        if (!answerWithBLOBsList.isEmpty()) {
            AnswerWithBLOBs answerWithBLOBs = answerWithBLOBsList.get(0);
            AnswerVo answerVo = new AnswerVo();
            BeanUtils.copyProperties(answerWithBLOBs, answerVo);
            Long createUserId = answerWithBLOBs.getCreateUserId();
            if (null != createUserId) {
                answerVo.setUser(ResponseUtils.getResponseData(userApi.getUserSimple(createUserId)));
            }
            return answerVo;
        }
        return null;
    }


}
