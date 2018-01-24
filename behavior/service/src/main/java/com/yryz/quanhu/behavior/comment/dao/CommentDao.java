package com.yryz.quanhu.behavior.comment.dao;

import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 18:13 2018/1/23
 */
@Mapper
public interface CommentDao {

    int accretion(Comment comment);

    Comment querySingleComment(Comment comment);

    int delComment(Comment comment);

    List<CommentVO> queryComments(CommentFrontDTO commentFrontDTO);

}