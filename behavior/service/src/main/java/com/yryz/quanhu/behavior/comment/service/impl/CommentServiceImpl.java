package com.yryz.quanhu.behavior.comment.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.comment.dao.CommentDao;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 18:47 2018/1/23
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    public int accretion(Comment comment) {
        return commentDao.accretion(comment);
    }

    @Override
    public Comment querySingleComment(Comment comment) {
        return commentDao.querySingleComment(comment);
    }

    @Override
    public int delComment(Comment comment) {
        return commentDao.delComment(comment);
    }

    @Override
    public PageList<CommentVO> queryComments(CommentFrontDTO commentFrontDTO) {
        Page<Comment> page=PageHelper.startPage(commentFrontDTO.getCurrentPage().intValue(),commentFrontDTO.getPageSize().intValue());
        List<CommentVO> commentVOS = commentDao.queryComments(commentFrontDTO);
        PageList pageList = new PageList();
        pageList.setCurrentPage(commentFrontDTO.getCurrentPage());
        pageList.setPageSize(commentFrontDTO.getPageSize());
        List<CommentVO> commentVOS_ = new ArrayList<CommentVO>();
        List<Comment> commentsnew = new ArrayList<Comment>();
        for (CommentVO commentVO : commentVOS) {
            CommentFrontDTO commentFrontDTOnew = new CommentFrontDTO();
            commentFrontDTOnew.setTopId(commentVO.getKid());
            commentFrontDTOnew.setResourceId(commentVO.getResourceId());
            commentVOS_ = commentDao.queryComments(commentFrontDTOnew);
            int i = 0;
            for (CommentVO commentVOsnew : commentVOS_) {
                i++;
                Comment comment = new Comment();
                comment.setId(commentVOsnew.getId());
                comment.setKid(commentVOsnew.getKid());
                comment.setTopId(commentVOsnew.getTopId());
                comment.setParentId(commentVOsnew.getParentId());
                comment.setParentUserId(commentVOsnew.getParentUserId());
                comment.setModuleEnum(commentVOsnew.getModuleEnum());
                comment.setTargetUserId(commentVOsnew.getTargetUserId());
                comment.setDelFlag(commentVOsnew.getDelFlag());
                comment.setRecommend(commentVOsnew.getRecommend());
                comment.setCreateUserId(commentVOsnew.getCreateUserId());
                comment.setCreateDate(commentVOsnew.getCreateDate());
                comment.setTenantId(commentVOsnew.getTenantId());
                comment.setRevision(commentVOsnew.getRevision());
                comment.setNickName(commentVOsnew.getNickName());
                comment.setResourceId(commentVOsnew.getResourceId());
                comment.setCoterieId(commentVOsnew.getCoterieId());
                comment.setContentComment(commentVOsnew.getContentComment());
                comment.setUserImg(commentVOsnew.getUserImg());
                comment.setLastUpdateUserId(commentVOsnew.getLastUpdateUserId());
                comment.setLastUpdateDate(commentVOsnew.getLastUpdateDate());
                //需要接统计
                comment.setLikeCount(0);
                comment.setLikeFlag((byte) 0);
                commentsnew.add(comment);
                if (i >= 3) {
                    break;
                }
            }
            //需要接统计
            commentVO.setLikeCount(0);
            commentVO.setLikeFlag((byte) 0);

            commentVO.setCommentCount(commentsnew.size());
            commentVO.setChildrenComments(commentsnew);
        }
        pageList.setCount(Long.valueOf(commentVOS.size()));
        pageList.setEntities(commentVOS);
        return pageList;
    }

    @Override
    public int updownBatch(List<Comment> comments) {
        return commentDao.updownBatch(comments);
    }

    @Override
    public PageList<CommentVOForAdmin> queryCommentForAdmin(CommentDTO commentDTO) {
        Page<CommentVOForAdmin> page=PageHelper.startPage(commentDTO.getCurrentPage().intValue(),commentDTO.getPageSize().intValue());
        PageList pageList = new PageList();
        pageList.setCurrentPage(commentDTO.getCurrentPage());
        pageList.setPageSize(commentDTO.getPageSize());
        List<CommentVOForAdmin> commentVOForAdmins = commentDao.queryCommentForAdmin(commentDTO);
        pageList.setEntities(commentVOForAdmins);
        pageList.setCount(Long.valueOf(commentVOForAdmins.size()));
        return pageList;
    }

    @Override
    public CommentInfoVO querySingleCommentInfo(CommentSubDTO commentSubDTO) {
        return commentDao.querySingleCommentInfo(commentSubDTO);
    }

    @Override
    public PageList<CommentVO> querySubCommentsInfo(CommentSubDTO commentSubDTO) {
        Page<Comment> page=PageHelper.startPage(commentSubDTO.getCurrentPage().intValue(),commentSubDTO.getPageSize().intValue());
        CommentFrontDTO commentFrontDTO = new CommentFrontDTO();
        commentFrontDTO.setCurrentPage(commentSubDTO.getCurrentPage());
        commentFrontDTO.setPageSize(commentSubDTO.getPageSize());
        commentFrontDTO.setTopId(commentSubDTO.getKid());
        PageList pageList=new PageList();
        pageList.setCurrentPage(commentSubDTO.getCurrentPage());
        pageList.setPageSize(commentSubDTO.getPageSize());
        List<CommentVO> commentVOS = commentDao.queryComments(commentFrontDTO);
        pageList.setEntities(commentVOS);
        pageList.setCount(Long.valueOf(commentVOS.size()));
        return pageList;
    }
}
