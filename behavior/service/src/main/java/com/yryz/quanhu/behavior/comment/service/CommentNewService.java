package com.yryz.quanhu.behavior.comment.service;

import java.util.List;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;

public interface CommentNewService {
	Comment accretion(Comment comment);

    Comment querySingleComment(Comment comment);

    int delComment(Comment comment);

	PageList<CommentListInfoVO> listComments(CommentFrontDTO commentFrontDTO);

    int updownBatch(List<Comment> comments);

    PageList<CommentVOForAdmin> queryCommentForAdmin(CommentDTO commentDTO);

    CommentDetailVO  queryCommentDetail(CommentSubDTO commentSubDTO);

    int updownSingle(Comment comment);
}
