package com.yryz.quanhu.behavior.comment.dto;

import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 13:46 2018/1/23
 */
public class CommentDTO extends PageList implements Serializable {
    private static final long serialVersionUID = 2488137967287515580L;

    /**
     * 关键字
     */
    private String keyWords;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;

    /**
     * 上下架
     */
    private byte shelveFlag;

    public String getKeyWords() {
        return keyWords;
    }

    public CommentDTO setKeyWords(String keyWords) {
        this.keyWords = keyWords;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public CommentDTO setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public CommentDTO setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public CommentDTO setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
        return this;
    }
}
