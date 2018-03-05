package com.yryz.quanhu.behavior.like.entity;



import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:38 2018/1/24
 */
public class Like extends GenericEntity {

    /**
     *资源ID
     */
    
    private long resourceId;

    /**
     *资源类型
     */
    private String moduleEnum;

    /**
     * 点赞人ID
     */
    
    private long userId;

    /**
     * 资源创建人ID
     */
    
    private long resourceUserId;

    /**
     *私圈id
     */
    
    private long coterieId;
    /**
     * 点赞状态10-未点赞 11-已点赞
     */
    private Integer likeFlag;
    public long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(long coterieId) {
        this.coterieId = coterieId;
    }

    public long getResourceUserId() {
        return resourceUserId;
    }

    public void setResourceUserId(long resourceUserId) {
        this.resourceUserId = resourceUserId;
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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

	public Integer getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(Integer likeFlag) {
		this.likeFlag = likeFlag;
	}
}
