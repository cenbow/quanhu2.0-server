package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.quanhu.behavior.comment.entity.Comment;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 13:49 2018/1/23
 */
public class CommentVO extends Comment {

    /**
     * 评论条数
     */
    private int commentCount;

    /**
     * 评论集合
     */
    private List<Comment> childrenComments;

    public int getCommentCount() {
        return commentCount;
    }

    public CommentVO setCommentCount(int commentCount) {
        this.commentCount = commentCount;
        return this;
    }

    public List<Comment> getChildrenComments() {
        return childrenComments;
    }

    public CommentVO setChildrenComments(List<Comment> childrenComments) {
        this.childrenComments = childrenComments;
        return this;
    }
}
