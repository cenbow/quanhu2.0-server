package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.quanhu.behavior.comment.entity.Comment;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 12:01 2018/1/24
 */
public class CommentInfoVO extends Comment{

    private CommentVO commentVO;

    public CommentVO getCommentVO() {
        return commentVO;
    }

    public CommentInfoVO setCommentVO(CommentVO commentVO) {
        this.commentVO = commentVO;
        return this;
    }
}
