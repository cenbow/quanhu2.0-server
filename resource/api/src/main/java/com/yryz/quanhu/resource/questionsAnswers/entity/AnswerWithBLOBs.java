package com.yryz.quanhu.resource.questionsAnswers.entity;

public class AnswerWithBLOBs extends Answer {
    private String content;

    private String contentSource;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource == null ? null : contentSource.trim();
    }
}