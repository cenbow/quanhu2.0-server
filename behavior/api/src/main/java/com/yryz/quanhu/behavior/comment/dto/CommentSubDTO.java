package com.yryz.quanhu.behavior.comment.dto;



import com.yryz.common.response.PageList;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 11:57 2018/1/24
 */
public class CommentSubDTO extends PageList implements Serializable {
    private static final long serialVersionUID = 5278107300199813679L;

    
    private Long kid;
    
    private Long resourceId;
    
    /**
     * 当前用户id
     */
    private String userId;
    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
