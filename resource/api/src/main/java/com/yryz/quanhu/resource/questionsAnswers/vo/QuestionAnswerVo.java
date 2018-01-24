package com.yryz.quanhu.resource.questionsAnswers.vo;

import java.io.Serializable;

public class QuestionAnswerVo implements Serializable {
    private  QuestionVo question;

    private  AnswerVo answer;

    public QuestionVo getQuestion() {
        return question;
    }

    public void setQuestion(QuestionVo question) {
        this.question = question;
    }

    public AnswerVo getAnswer() {
        return answer;
    }

    public void setAnswer(AnswerVo answer) {
        this.answer = answer;
    }
}
