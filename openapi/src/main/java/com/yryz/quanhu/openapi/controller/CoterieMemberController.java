package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.dto.CoterieMemberDto;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.coterie.member.vo.*;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author chengyunfei
 * @date 2018/1/25.
 */
@Api(description = "私圈成员相关接口")
@RestController
public class CoterieMemberController {
    @Reference(check = false, timeout = 30000)
    private CoterieMemberAPI coterieMemberAPI;

    @ApiOperation("用户申请加入私圈")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "memberDto", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/services/app/{version}/coterie/member/join")
    public Response<CoterieMemberVoForJoin> join(@RequestHeader("userId") Long userId, @RequestBody CoterieMemberDto memberDto) {
        return coterieMemberAPI.join(userId, memberDto.getCoterieId(), memberDto.getReason());
    }

    @ApiOperation("圈主踢出私圈成员")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "memberDto", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/services/app/{version}/coterie/member/kick")
    public Response<String> kick(@RequestHeader("userId") Long userId,@RequestBody CoterieMemberDto memberDto) {

        return coterieMemberAPI.kick(userId, memberDto.getMemberId(), memberDto.getCoterieId(), memberDto.getReason());
    }

    @ApiOperation("圈粉退出私圈")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "memberDto", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/services/app/{version}/coterie/member/quit")
    public Response<String> quit(@RequestHeader("userId") Long userId,@RequestBody CoterieMemberDto memberDto) {
        return coterieMemberAPI.quit(userId, memberDto.getCoterieId());
    }

    @ApiOperation("设置禁言/取消禁言")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "memberDto", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/services/app/{version}/coterie/member/banSpeak")
    public Response banSpeak(@RequestHeader("userId") Long userId,@RequestBody CoterieMemberDto memberDto) {

        return coterieMemberAPI.banSpeak(userId, memberDto.getMemberId(), memberDto.getCoterieId(), memberDto.getType());
    }

    @ApiOperation("获取用户与私圈的权限关系")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/coterie/member/permission")
    public Response permission(Long coterieId, HttpServletRequest request) {

        CoterieMemberVoForPermission permissionResult = new CoterieMemberVoForPermission();

        String userId = CommonUtils.getHeaderValue(request, "userId");
        if (StringUtils.isNotBlank(userId)) {
            permissionResult.setPermission(ResponseUtils.getResponseData(coterieMemberAPI.permission(Long.parseLong(userId), coterieId)));
        } else {
            permissionResult.setPermission(MemberConstant.Permission.STRANGER_NON_CHECK.getStatus());
        }

        return new Response<>(permissionResult);

    }

    @ApiOperation("获取用户禁言或私圈内禁言的权限")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/coterie/member/coterieBanSpeak")
    public Response coterieBanSpeak(@RequestHeader("userId") Long userId, Long coterieId) {

        CoterieMemberVoForPermission permissionResult = new CoterieMemberVoForPermission();
        permissionResult.setPermission(ResponseUtils.getResponseData(coterieMemberAPI.banSpeakStatus(userId, coterieId)));
        return new Response<>(permissionResult);

    }

    @ApiOperation("成员申请加入私圈的审批")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParams({
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
    @ApiImplicitParam(name = "memberDto", paramType = "body", required = true),
    @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/services/app/{version}/coterie/member/audit")
    public Response audit(@RequestHeader("userId") Long userId, @RequestBody CoterieMemberDto memberDto) {

        coterieMemberAPI.audit(userId, memberDto.getMemberId(), memberDto.getCoterieId(), MemberConstant.MemberStatus.PASS.getStatus());

        return new Response();
    }

    @ApiOperation("私圈新审请的成员数量")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/coterie/member/newMemberNum")
    public Response<CoterieMemberVoForNewMemberCount> newMemberNum(Long coterieId) {

        CoterieMemberVoForNewMemberCount newMemberCount = new CoterieMemberVoForNewMemberCount();
        newMemberCount.setCount(ResponseUtils.getResponseData(coterieMemberAPI.queryNewMemberNum(coterieId)));

        return new Response<>(newMemberCount);
    }

    @ApiOperation("申请加入私圈列表")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/coterie/member/applyList")
    public Response applyList(Long coterieId, Integer currentPage, Integer pageSize) {

        Response<PageList<CoterieMemberApplyVo>> result = coterieMemberAPI.queryMemberApplyList(coterieId, currentPage, pageSize);
        return result;
    }

    @ApiOperation("私圈成员列表")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/services/app/{version}/coterie/member/list")
    public Response list(Long coterieId, Integer currentPage, Integer pageSize) {

        return coterieMemberAPI.queryMemberList(coterieId, currentPage, pageSize);
    }
}
