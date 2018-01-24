package com.yryz.quanhu.behavior.comment.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.comment.contants.CommentConstatns;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 19:13 2018/1/23
 */
@Service(interfaceClass = CommentApi.class)
public class CommentProvider implements CommentApi {

    private static final Logger logger = LoggerFactory.getLogger(CommentProvider.class);

    @Autowired
    private CommentService commentService;

    @Reference(check = false)
    private IdAPI idAPI;

    @Override
    public Response<Map<String, Integer>> accretion(Comment comment) {
        try {
            comment.setKid(idAPI.getKid(CommentConstatns.QH_COMMENT_INFO).getData());
            Map<String,Integer> map=new HashMap<String, Integer>();
            //根据调用用户拿到用户的对象comment.getCreateUserId();
            comment.setNickName("");
            comment.setUserImg("");
            int count=commentService.accretion(comment);
            if(count>0){
                map.put("result",1);
            }else{
                map.put("result",0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<String, Integer>> delComment(Comment comment) {
        try{
            Map<String,Integer> map=new HashMap<String, Integer>();
            int count=commentService.delComment(comment);
            if(count>0){
                map.put("result",1);
            }else{
                map.put("result",0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO) {
        try{
            return ResponseUtils.returnObjectSuccess(commentService.queryComments(commentFrontDTO));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownBatch(List<Comment> comments) {
        try{
            int count=commentService.updownBatch(comments);
            return ResponseUtils.returnObjectSuccess(count);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVOForAdmin>> queryCommentForAdmin(CommentDTO commentDTO) {
        try{
            return ResponseUtils.returnObjectSuccess(commentService.queryCommentForAdmin(commentDTO));
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }
}
