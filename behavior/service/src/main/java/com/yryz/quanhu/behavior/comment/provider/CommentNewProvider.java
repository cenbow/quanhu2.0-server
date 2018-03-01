package com.yryz.quanhu.behavior.comment.provider;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.service.CommentNewService;
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;

@Service(interfaceClass = CommentApi.class)
public class CommentNewProvider implements CommentApi {
	private static final Logger logger = LoggerFactory.getLogger(CommentNewProvider.class);
	
	@Autowired
	private CommentNewService commentService;
	
	@Override
	public Response<Comment> accretion(Comment comment) {
		try {
	
            Comment commentSuccess = commentService.accretion(comment);
            return ResponseUtils.returnObjectSuccess(commentSuccess);
        } catch (Exception e) {
            logger.error("[comment]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<Map<String, Integer>> delComment(Comment comment) {
		try {			
			Map<String, Integer> map = new HashMap<String, Integer>();
            int count = commentService.delComment(comment);
            if (count > 0) {
                map.put("result", 1);
            } else {
                map.put("result", 0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("[comment]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Integer> updownBatch(List<Comment> comments) {
		try {
            int count = commentService.updownBatch(comments);
            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            logger.error("[comment_updownBatch]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<PageList<CommentVOForAdmin>> queryCommentForAdmin(CommentDTO commentDTO) {
		 try {
	            return ResponseUtils.returnObjectSuccess(commentService.queryCommentForAdmin(commentDTO));
	        } catch (Exception e) {
	            logger.error("[comment_queryCommentForAdmin]", e);
	            return ResponseUtils.returnException(e);
	        }
	}

	@Override
	public Response<CommentInfoVO> querySingleCommentInfo(CommentSubDTO commentSubDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response<Integer> updownSingle(Comment comment) {
		try {
            int count = commentService.updownSingle(comment);
            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            logger.error("[comment_updownBatch]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<PageList<CommentListInfoVO>> listComments(CommentFrontDTO commentFrontDTO) {
		try {
            return ResponseUtils.returnObjectSuccess(commentService.listComments(commentFrontDTO));
        } catch (Exception e) {
            logger.error("[comment_list]", e);
            return ResponseUtils.returnException(e);
        }
	}

	@Override
	public Response<CommentDetailVO> queryCommentDetail(CommentSubDTO commentSubDTO) {
		try {
            return ResponseUtils.returnObjectSuccess(commentService.queryCommentDetail(commentSubDTO));
        } catch (Exception e) {
            logger.error("[comment_detail]", e);
            return ResponseUtils.returnException(e);
        }
	}

}
