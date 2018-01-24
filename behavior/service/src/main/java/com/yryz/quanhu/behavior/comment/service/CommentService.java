package com.yryz.quanhu.behavior.comment.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;


/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 18:46 2018/1/23
 */
public interface CommentService {

    int accretion(Comment comment);

    Comment querySingleComment(Comment comment);

    int delComment(Comment comment);

    PageList<CommentVO> queryComments(CommentFrontDTO commentFrontDTO);
}
