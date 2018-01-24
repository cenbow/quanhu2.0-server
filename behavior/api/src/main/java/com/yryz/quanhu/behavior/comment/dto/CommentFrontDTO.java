package com.yryz.quanhu.behavior.comment.dto;

import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 18:10 2018/1/23
 */
public class CommentFrontDTO extends PageList implements Serializable {
    private static final long serialVersionUID = -5685947677273294958L;

    private long topId;
    private long resourceId;
    private String moduleEnum;
    private long kid;
    private byte shelveFlag;
    private long createUserId;

    public String getModuleEnum() {
        return moduleEnum;
    }

    public CommentFrontDTO setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }

    public long getKid() {
        return kid;
    }

    public CommentFrontDTO setKid(long kid) {
        this.kid = kid;
        return this;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public CommentFrontDTO setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
        return this;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public CommentFrontDTO setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
        return this;
    }

    public long getTopId() {
        return topId;
    }

    public CommentFrontDTO setTopId(long topId) {
        this.topId = topId;
        return this;
    }

    public long getResourceId() {
        return resourceId;
    }

    public CommentFrontDTO setResourceId(long resourceId) {
        this.resourceId = resourceId;
        return this;
    }
}

