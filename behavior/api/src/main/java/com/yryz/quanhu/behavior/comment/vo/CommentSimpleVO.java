package com.yryz.quanhu.behavior.comment.vo;

import java.io.Serializable;
import com.yryz.quanhu.user.vo.UserSimpleNoneOtherVO;
/**
 * 评论返回vo
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class CommentSimpleVO implements Serializable {
	private Long kid;
	/**
	 * 评论/回复总数
	 */
	private int commentCount;
	/**
     * 根评论ID
     */
    private long topId;

    /**
     * 父级评论ID
     */
    private long parentId;

    /**
     * 父级评论用户ID
     */
    private long parentUserId;

    /**
     * 目标源功能ID
     */
    private String moduleEnum;

    /**
     * 私圈ID
     */
    private long coterieId;

    /**
     * 目标源/资源Id
     */
    private long resourceId;

    /**
     * 目标源用户ID
     */
    private long targetUserId;

    /**
     * 评论内容(文字)
     */
    private String contentComment;
    
    /**
     * 评论时间
     */
    private long createTime;
    
    /**
     * 被评论人用户信息
     */
    private UserSimpleNoneOtherVO parentUser = new UserSimpleNoneOtherVO();
    
    /**
     * 评论人用户信息
     */
    private UserSimpleNoneOtherVO user = new UserSimpleNoneOtherVO();
    
    
	public long getTopId() {
		return topId;
	}
	public void setTopId(long topId) {
		this.topId = topId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public long getParentUserId() {
		return parentUserId;
	}
	public void setParentUserId(long parentUserId) {
		this.parentUserId = parentUserId;
	}
	public String getModuleEnum() {
		return moduleEnum;
	}
	public void setModuleEnum(String moduleEnum) {
		this.moduleEnum = moduleEnum;
	}
	public long getCoterieId() {
		return coterieId;
	}
	public void setCoterieId(long coterieId) {
		this.coterieId = coterieId;
	}
	public long getResourceId() {
		return resourceId;
	}
	public void setResourceId(long resourceId) {
		this.resourceId = resourceId;
	}
	public long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getContentComment() {
		return contentComment;
	}
	public void setContentComment(String contentComment) {
		this.contentComment = contentComment;
	}
	public UserSimpleNoneOtherVO getUser() {
		return user;
	}
	public void setUser(UserSimpleNoneOtherVO user) {
		this.user = user;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public Long getKid() {
		return kid;
	}
	public void setKid(Long kid) {
		this.kid = kid;
	}
	public UserSimpleNoneOtherVO getParentUser() {
		return parentUser;
	}
	public void setParentUser(UserSimpleNoneOtherVO parentUser) {
		this.parentUser = parentUser;
	}
	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
}
