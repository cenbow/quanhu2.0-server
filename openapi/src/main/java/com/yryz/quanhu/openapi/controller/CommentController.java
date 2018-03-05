package com.yryz.quanhu.openapi.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.vo.CommentDetailVO;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentListInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentSimpleVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.openapi.ApplicationOpenApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
 * @Date:Created in 19:29 2018/1/23
 */
@Api(tags = "评论")
@RestController
public class CommentController {

    @Reference(cluster="failfast",url="dubbo://127.0.0.1:20882")
    private CommentApi commentApi;

    @ApiOperation("用户评论")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/comment/accretion")
    public Response<CommentSimpleVO> accretion(@RequestBody Comment comment, @RequestHeader Long userId, HttpServletRequest request) {
        comment.setCreateUserId(userId);

        /**
         * 根据参数判断评论类型，私圈/平台
         */
        if(comment.getCoterieId()!=0){
            return coterieAccretion(comment,userId,request);
        }else{
            return platformAccretion(comment,userId,request);
        }
    }
    /**
     * 私圈评论
     * @param comment
     * @param userId
     * @param request
     * @return
     */
    @UserBehaviorArgs(sourceUserId="object.Comment.targetUserId",contexts={"object.Comment.contentComment"},coterieId="object.Comment.coterieId")
    @UserBehaviorValidation(login = true, mute = true, blacklist = true, illegalWords = true,coterieMute=true)
    private Response<CommentSimpleVO> coterieAccretion(Comment comment, Long userId, HttpServletRequest request) {
        return ResponseUtils.returnApiObjectSuccess(ResponseUtils.getResponseData(commentApi.saveComment(comment)));
    }

    /**
     * 平台评论
     * @param comment
     * @param userId
     * @param request
     * @return
     */
    @UserBehaviorArgs(sourceUserId="object.Comment.targetUserId",contexts={"object.Comment.contentComment"},coterieId="object.Comment.coterieId")
    @UserBehaviorValidation(login = true, mute = true, blacklist = true, illegalWords = true)
    private Response<CommentSimpleVO> platformAccretion(Comment comment, Long userId, HttpServletRequest request) {
        return ResponseUtils.returnApiObjectSuccess(ResponseUtils.getResponseData(commentApi.saveComment(comment)));
    }

    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    //@PostMapping(value = "/services/app/{version}/comment/accretion")
    public Response<CommentSimpleVO> addComment(@RequestBody Comment comment, @RequestHeader Long userId, HttpServletRequest request) {
        comment.setCreateUserId(userId);
        CommentSimpleVO simpleVO = ResponseUtils.getResponseData(commentApi.saveComment(comment));
        return ResponseUtils.returnApiObjectSuccess(simpleVO);
    }
    
    
    @ApiOperation("用户评论列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    //@GetMapping(value = "/services/app/{version}/comment/list")
    public Response<PageList<CommentVO>> queryComments(CommentFrontDTO commentFrontDTO,HttpServletRequest request){
        com.yryz.common.entity.RequestHeader header = WebUtil.getHeader(request);
        commentFrontDTO.setUserId(header.getUserId());
        return commentApi.queryComments(commentFrontDTO);
    }

    @ApiOperation("用户评论列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/comment/list")
    public Response<PageList<CommentListInfoVO>> listComments(CommentFrontDTO commentFrontDTO,HttpServletRequest request){
        com.yryz.common.entity.RequestHeader header = WebUtil.getHeader(request);
        commentFrontDTO.setUserId(header.getUserId());
       /* try {
        	Response<PageList<CommentListInfoVO>> response = commentApi.listComments(commentFrontDTO);
        	JsonUtils.toFastJson(response);
        } catch (Exception e) {
			e.printStackTrace();
		}*/
        PageList<CommentListInfoVO> pageList = ResponseUtils.getResponseData(commentApi.listComments(commentFrontDTO));
        return ResponseUtils.returnApiObjectSuccess(pageList);
    }
    
    
    @ApiOperation("用户删除评论")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/services/app/{version}/comment/clean")
    public Response<Map<String, Integer>> delComment(@RequestBody Comment comment,@RequestHeader Long userId){
        comment.setCreateUserId(userId);
        return ResponseUtils.returnApiObjectSuccess(ResponseUtils.getResponseData(commentApi.delComment(comment)));
    }

    
    @ApiOperation("用户评论详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    //@GetMapping(value = "/services/app/{version}/comment/singleInfo")
    public Response<CommentInfoVO> querySingleCommentInfo(CommentSubDTO commentSubDTO,HttpServletRequest request){
    	com.yryz.common.entity.RequestHeader header = WebUtil.getHeader(request);
    	commentSubDTO.setUserId(header.getUserId());
        return commentApi.querySingleCommentInfo(commentSubDTO);
    }

    @ApiOperation("用户评论详情")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/comment/singleInfo")
    public Response<CommentDetailVO> getCommentDetail(CommentSubDTO commentSubDTO,HttpServletRequest request){
    	com.yryz.common.entity.RequestHeader header = WebUtil.getHeader(request);
    	commentSubDTO.setUserId(header.getUserId());
    	CommentDetailVO detailVO = ResponseUtils.getResponseData(commentApi.queryCommentDetail(commentSubDTO));
    	return ResponseUtils.returnApiObjectSuccess(detailVO);
    }
}
