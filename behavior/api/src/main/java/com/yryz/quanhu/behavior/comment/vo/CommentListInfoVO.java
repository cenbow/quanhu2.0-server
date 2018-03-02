package com.yryz.quanhu.behavior.comment.vo;

import java.util.List;

/**
 * 评论列表返回VO
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class CommentListInfoVO extends CommentSimpleVO {
	/**
	 * 回复列表
	 */
	private List<CommentSimpleVO> replyVos;
	/**
	 * 点赞状态
	 */
	private int likeFlag = 0;
	/**
	 * 点赞总数
	 */
	private long likeCount = 0l;
	public List<CommentSimpleVO> getReplyVos() {
		return replyVos;
	}

	public void setReplyVos(List<CommentSimpleVO> replyVos) {
		this.replyVos = replyVos;
	}

	public int getLikeFlag() {
		return likeFlag;
	}

	public void setLikeFlag(int likeFlag) {
		this.likeFlag = likeFlag;
	}

	public long getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(long likeCount) {
		this.likeCount = likeCount;
	}
	
	public static CommentListInfoVO parseByCommentSimple(CommentSimpleVO simpleVO){
		CommentListInfoVO infoVO = new CommentListInfoVO();
		if(simpleVO == null){
			return infoVO;
		}
		infoVO.setCommentCount(simpleVO.getCommentCount());
		infoVO.setContentComment(simpleVO.getContentComment());
		infoVO.setCreateTime(simpleVO.getCreateTime());
		infoVO.setCoterieId(simpleVO.getCoterieId());
		infoVO.setKid(simpleVO.getKid());
		infoVO.setParentId(simpleVO.getParentId());
		infoVO.setParentUser(simpleVO.getParentUser());
		infoVO.setParentUserId(simpleVO.getParentUserId());
		infoVO.setModuleEnum(simpleVO.getModuleEnum());
		infoVO.setResourceId(simpleVO.getResourceId());
		infoVO.setTargetUserId(simpleVO.getTargetUserId());
		infoVO.setTopId(simpleVO.getTopId());
		infoVO.setUser(simpleVO.getUser());
		return infoVO;
	}
}
