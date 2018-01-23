package com.yryz.quanhu.resource.questionsAnswers.dao;

import com.yryz.quanhu.resource.questionsAnswers.entity.Answer;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerExample;
import com.yryz.quanhu.resource.questionsAnswers.entity.AnswerWithBLOBs;
import java.util.List;

public interface AnswerDao {
    long countByExample(AnswerExample example);

    int deleteByExample(AnswerExample example);

    int deleteByPrimaryKey(Long id);

    int insert(AnswerWithBLOBs record);

    int insertSelective(AnswerWithBLOBs record);

    List<AnswerWithBLOBs> selectByExampleWithBLOBs(AnswerExample example);

    List<Answer> selectByExample(AnswerExample example);

    AnswerWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AnswerWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(AnswerWithBLOBs record);

    int updateByPrimaryKey(Answer record);
}