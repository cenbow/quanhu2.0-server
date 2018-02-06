package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.service.CommentApi;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 14:54 2018/1/30
 */
@Api(tags = "管理后台测试")
@RestController
public class BackManagerTestController {

    @Reference(check=false)
    private CommentApi commentApi;

    
    @ApiOperation("评论查看")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/comment/backlist")
    public Response<PageList<CommentVOForAdmin>> commentPage(CommentDTO commentDTO) {
        return commentApi.queryCommentForAdmin(commentDTO);
    }
}
