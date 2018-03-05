package com.yryz.quanhu.behavior.like.vo;




import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 14:52 2018/1/24
 */
public class LikeVO implements Serializable{

    private static final long serialVersionUID = 4043275150693984318L;
    /**
     * 业务ID
     */
    
    private long kid;

    /**
     * 资源ID
     */
    
    private long resourceId;

    /**
     * 点赞时间
     */
    private String createDate;

    /**
     * 用户ID
     */
    
    private long userId;

    /**
     * 用户昵称
     */
    private String userNickName;

    /**
     * 用户头像
     */
    private String userImg;
    /**
     * 点赞状态 10-未点赞 11-已点赞
     */
    private Integer likeFlag;
    public long getKid() {
        return kid;
    }

    public void setKid(long kid) {
        this.kid = kid;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

	public Integer getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(Integer likeFlag) {
		this.likeFlag = likeFlag;
	}
}
