package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.comment.entity.Comment;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 12:01 2018/1/24
 */
public class CommentInfoVO extends Comment{

    PageList<CommentVO> commentEnties;

    public PageList<CommentVO> getCommentEnties() {
        return commentEnties;
    }

    public void setCommentEnties(PageList<CommentVO> commentEnties) {
        this.commentEnties = commentEnties;
    }
}
