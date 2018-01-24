package com.yryz.quanhu.behavior.comment.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;

import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 13:45 2018/1/23
 */
public interface CommentApi {

    /**
     * 添加评论
     * @param comment
     * @return
     */
    Response<Map<String,Integer>> accretion(Comment comment);

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

}
