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
    private long checkType;
    /**
     * 当前用户id
     */
    private String userId;
    public long getCheckType() {
        return checkType;
    }

    public void setCheckType(long checkType) {
        this.checkType = checkType;
    }

    public long getTopId() {
        return topId;
    }

    public void setTopId(long topId) {
        this.topId = topId;
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

    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}

