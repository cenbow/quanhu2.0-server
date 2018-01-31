package com.yryz.quanhu.behavior.comment.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.behavior.comment.contants.CommentConstatns;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.catalina.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;

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

    @Reference(check = false)
    private UserApi userApi;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;

    @Override
    public Response<Map<String, Integer>> accretion(Comment comment) {
        RedisTemplate<String, Object> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Object.class);
        try {
            comment.setKid(idAPI.getSnowflakeId().getData());
            Map<String, Integer> map = new HashMap<String, Integer>();
            UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
            if (null != userSimpleVO) {
                comment.setNickName(userSimpleVO.getUserNickName());
                comment.setUserImg(userSimpleVO.getUserImg());
            }
            int count = commentService.accretion(comment);
            if (count > 0) {
                map.put("result", 1);
                try {
                    redisTemplate.opsForValue().set("COMMENT:" + comment.getModuleEnum() + ":" + comment.getKid() + "_" + comment.getTopId() + "_" + comment.getResourceId(), comment);
                } catch (Exception e) {
                    logger.info("同步评论数据到redis中失败" + e);
                }
            } else {
                map.put("result", 0);
            }

            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<String, Integer>> delComment(Comment comment) {
        RedisTemplate<String, Object> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Object.class);
        try {
            Map<String, Integer> map = new HashMap<String, Integer>();
            int count = commentService.delComment(comment);
            if (count > 0) {
                map.put("result", 1);
                Comment comments = new Comment();
                comments.setKid(comment.getKid());
                Comment commentStr = commentService.querySingleComment(comments);
                try{
                    redisTemplate.delete("COMMENT:" + commentStr.getModuleEnum() + ":" + commentStr.getKid() + "_" + commentStr.getTopId() + "_" + commentStr.getResourceId());
                }catch (Exception e){
                    logger.info("从redis中移除评论数据失败" + e);
                }

            } else {
                map.put("result", 0);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.queryComments(commentFrontDTO));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownBatch(List<Comment> comments) {
        try {
            int count = commentService.updownBatch(comments);

            //待定 审核成功后同步到Redis



            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<CommentVOForAdmin>> queryCommentForAdmin(CommentDTO commentDTO) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.queryCommentForAdmin(commentDTO));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<CommentInfoVO> querySingleCommentInfo(CommentSubDTO commentSubDTO) {
        try {
            CommentInfoVO commentInfoVO = commentService.querySingleCommentInfo(commentSubDTO);
            commentInfoVO.setCommentEnties(commentService.querySubCommentsInfo(commentSubDTO));
            return ResponseUtils.returnObjectSuccess(commentInfoVO);
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updownSingle(Comment comment) {
        try {
            return ResponseUtils.returnObjectSuccess(commentService.updownSingle(comment));
        } catch (Exception e) {
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }

}
