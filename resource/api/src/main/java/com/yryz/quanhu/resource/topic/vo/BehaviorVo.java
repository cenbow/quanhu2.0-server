package com.yryz.quanhu.resource.topic.vo;

import java.io.Serializable;

public class BehaviorVo implements Serializable {
    private  Long likeCount;

    private  Long commentCount;

    public Long getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Long likeCount) {
        this.likeCount = likeCount;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }
}
