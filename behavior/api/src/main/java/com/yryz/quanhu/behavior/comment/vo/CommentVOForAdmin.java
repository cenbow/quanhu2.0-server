package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 20:48 2018/1/24
 */
public class CommentVOForAdmin extends GenericEntity {

    /**
     * 评论内容
     */
    private String contentComment;

    /**
     * 上下加
     */
    private byte shelveFlag;

    public String getContentComment() {
        return contentComment;
    }

    public CommentVOForAdmin setContentComment(String contentComment) {
        this.contentComment = contentComment;
        return this;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public CommentVOForAdmin setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
        return this;
    }
}
