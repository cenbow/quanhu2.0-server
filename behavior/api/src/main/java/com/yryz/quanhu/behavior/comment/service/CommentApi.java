package com.yryz.quanhu.behavior.comment.service;

import com.yryz.common.context.Context;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;

import java.util.List;
import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 13:45 2018/1/23
 */
public interface CommentApi {
	/**
	 * 评论信息key
	 * @param commentId
	 * @return
	 */
	static String getCommentKey(Long commentId){
		return String.format("%s:%s", Context.getProperty("comment.info"),commentId.toString());
	}
	
	/**
	 * 一级评论列表
	 * @param moduleEnum
	 * @param resourceId
	 * @return
	 */
	static String getCommentListKey(Long resourceId){
		return String.format("%s:%s", Context.getProperty("comment.list"),resourceId.toString());
	}
	
	/**
	 * 评论回复列表
	 * @param topCommentId
	 * @return
	 */
	static String getCommentReplyListKey(Long topCommentId){
		return String.format("%s:%s:reply", Context.getProperty("comment.list"),topCommentId.toString());
	}
	
    /**
     * 添加评论
     * @param comment
     * @return
     */
    Response<Comment> accretion(Comment comment);

    /**
     * 删除评论
     * @param comment
     * @return
     */
    Response<Map<String,Integer>> delComment(Comment comment);

    /**
     * 评论列表
     * @param commentFrontDTO
     * @return
     */
    Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO);
    
    /**
     * 评论列表
     * @param commentFrontDTO
     * @return
     */
    Response<PageList<CommentListInfoVO>> listComments(CommentFrontDTO commentFrontDTO);
    
    /**
     * 批量上下架
     * @param comments
     * @return
     */
    Response<Integer> updownBatch(List<Comment> comments);

    /**
     * 评论列表{管理后台}
     * @param commentDTO
     * @return
     */
    Response<PageList<CommentVOForAdmin>> queryCommentForAdmin(CommentDTO commentDTO);

    /**
     * 评论详情
     * @param commentSubDTO
     * @return
     */
    Response<CommentInfoVO> querySingleCommentInfo(CommentSubDTO commentSubDTO);
    
    /**
     * 新评论详情
     * @param commentSubDTO
     * @return
     */
    Response<CommentDetailVO>  queryCommentDetail(CommentSubDTO commentSubDTO);
    
    /**
     * 单个下架
     * @param:comment
     * @return:Integer
     **/
    Response<Integer> updownSingle(Comment comment);

}
