package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
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

    private String nickName;

    private long resourceId;

    private String moduleEnum;

    private String createDateStr;

    private String updateDateStr;

    private Long targetUserId;

    public Long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(Long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }
}
