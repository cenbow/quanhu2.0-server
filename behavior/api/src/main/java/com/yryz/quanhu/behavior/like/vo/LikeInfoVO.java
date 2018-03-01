package com.yryz.quanhu.behavior.like.vo;

import java.io.Serializable;

import com.yryz.quanhu.user.vo.UserSimpleNoneOtherVO;

/**
 * 点赞返回VO
 * 
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class LikeInfoVO implements Serializable {
	/**
	 * 资源ID
	 */

	private long resourceId;

	/**
	 * 点赞时间
	 */
	private long createTime;

	/**
	 * 点赞人用户信息
	 */
	private UserSimpleNoneOtherVO user;
	
	public long getResourceId(){
		return resourceId;
	}

	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}


	public long getCreateTime() {
		return createTime;
	}

	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public UserSimpleNoneOtherVO getUser() {
		return user;
	}

	public void setUser(UserSimpleNoneOtherVO user) {
		this.user = user;
	}
}
