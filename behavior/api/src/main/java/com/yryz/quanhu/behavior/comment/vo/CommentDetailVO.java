package com.yryz.quanhu.behavior.comment.vo;

import com.yryz.common.response.PageList;
/**
 * 评论详情返回
 * @author danshiyu
 *
 */
@SuppressWarnings("serial")
public class CommentDetailVO extends CommentSimpleVO {
	/**
	 * 点赞状态
	 */
	private int likeFlag = 0;
	/**
	 * 点赞总数
	 */
	private long likeCount = 0l;
	/**
	 * 回复详情
	 */
	private PageList<CommentSimpleVO> replys = new PageList<>();
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
	public PageList<CommentSimpleVO> getReplys() {
		return replys;
	}
	public void setReplys(PageList<CommentSimpleVO> replys) {
		this.replys = replys;
	}
	
	public static CommentDetailVO parseByCommentSimple(CommentSimpleVO simpleVO){
		CommentDetailVO infoVO = new CommentDetailVO();
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
