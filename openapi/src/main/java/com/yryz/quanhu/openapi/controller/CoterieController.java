package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.service.UserApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.List;

/**
 * @author wangtao
 * @version 2.0
 * @date 2018年1月20日 上午11:04:02
 * @Description 私圈接口
 */
@Api(description = "私圈接口")
@RestController
@RequestMapping(value = "services/app")
public class CoterieController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Reference
    private AccountApi accountApi;
    @Reference
    private UserApi userApi;
    @Reference
    private AuthApi authApi;
    @Reference
    private CoterieApi coterieApi;

    @Reference
    private CoterieMemberAPI coterieMemberAPI;

    @ApiOperation("我创建的和加入的私圈数量和")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @GetMapping(value = "/{version}/coterieInfo/count")
    public Response<Integer> getMyCoterieCount(@RequestHeader Long userId, HttpServletRequest request) {
    	if(userId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
        return coterieApi.getMyCoterieCount(String.valueOf(userId));
    }
    
    @ApiOperation("发布私圈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "info", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/{version}/coterieInfo/create")
    public Response<CoterieInfo> publish(@RequestHeader Long userId, @RequestBody CoterieBasicInfo info, HttpServletRequest request) {
        info.setOwnerId(userId.toString());
        if(info.getJoinFee()!=null && info.getJoinFee()!=0){//收费一定不审核
        	info.setJoinCheck(10);
        }
        return coterieApi.applyCreate(info);
    }

    @ApiOperation("设置私圈， 更新圈子的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "config", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/{version}/coterieInfo/config")
    public Response<CoterieInfo> config(@RequestHeader Long userId, @RequestBody CoterieBasicInfo config, HttpServletRequest request) {
        Integer permission = ResponseUtils.getResponseData(coterieMemberAPI.permission(userId, config.getCoterieId()));

        if (permission == null) {
            throw QuanhuException.busiError("获取权限异常 ：permission is null");
        } else if (permission != MemberConstant.Permission.OWNER.getStatus()) {
            throw QuanhuException.busiError("权限异常 ：您不是圈主无法更新私圈相关信息");
        }
        if(config.getJoinFee()!=null && config.getJoinFee()!=0){//收费一定不审核
        	config.setJoinCheck(10);
        }
        CoterieInfo info=GsonUtils.parseObj(config, CoterieInfo.class);
        Response<CoterieInfo> result = coterieApi.modifyCoterieInfo(info);
        return result;
    }

    /**
     * 获取私圈详情
     *
     * @param
     * @param
     * @return
     */
    @ApiOperation("获取私圈详情")
    @ApiImplicitParams({
		    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
		    @ApiImplicitParam(name = "userId", paramType = "header", required = true),
		    @ApiImplicitParam(name = "coterieId", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/coterieInfo/single")
    public Response<CoterieInfo> details(@RequestHeader Long userId,Long coterieId, HttpServletRequest request) {
        if(coterieId==null){
        	return ResponseUtils.returnCommonException("参数不能为空");
        }
        
        Response<CoterieInfo> coterieInfo = coterieApi.queryCoterieInfo(coterieId);
        if(coterieInfo.success() && userId!=null){
        	//如果是圈主第一次访问则记录时间
        	CoterieInfo info = coterieInfo.getData();
            if (info!=null && userId.toString().equals(info.getOwnerId())) {
            	CoterieInfo param=new CoterieInfo();
            	param.setMasterLastViewTime(new Date());
            	param.setCoterieId(coterieId);
            	coterieApi.modifyCoterieInfo(param);
            }
        }
        return coterieInfo;
    }

    /**
     * 获取我创建的私圈列表
     * @return
     */
    @ApiOperation("获取我创建的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true)})
    @GetMapping(value = "/{version}/coterieInfo/creator")
    public Response<List<CoterieInfo>> getMyCreateCoterie(@RequestHeader Long userId,Integer currentPage, Integer pageSize) {
    	if(userId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
    	if(currentPage==null || currentPage<1){
    		currentPage=1;
    	}
    	if(pageSize==null || pageSize<1){
    		pageSize=100;
    	}
        return coterieApi.getMyCreateCoterie(userId.toString(),currentPage,pageSize);
    }
    
    /**
     * 个人主页圈主创建的私圈列表
     * @return
     */
    @ApiOperation("个人主页圈主创建的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "query", required = true)})
    @GetMapping(value = "/{version}/coterieInfo/createlist")
    public Response<List<CoterieInfo>> getCreateCoterie(Long userId,Integer currentPage, Integer pageSize) {
    	if(userId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
    	if(currentPage==null || currentPage<1){
    		currentPage=1;
    	}
    	if(pageSize==null || pageSize<1){
    		pageSize=100;
    	}
        return coterieApi.getCreateCoterie(userId.toString(),currentPage,pageSize);
    }

    /**
     * 获取我加入的私圈列表
     * @return
     */
    @ApiOperation("我加入的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @GetMapping(value = "/{version}/coterieInfo/list/join")
    public Response<List<CoterieInfo>> getMyJoinCoterie(@RequestHeader Long userId,Integer pageNum,Integer pageSize) {
    	if(userId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
    	if(pageNum==null || pageNum<1){
    		pageNum=1;
    	}
    	if(pageSize==null || pageSize<1){
    		pageSize=10;
    	}
        return coterieApi.getMyJoinCoterie(userId.toString(),pageNum,pageSize);
    }
    
    /**
     * 获取我加入的私圈详情
     *
     * @return
     */
    @ApiOperation("个人主页加入的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "query", required = true) })
    @GetMapping(value = "/{version}/coterieInfo/user/join")
    public Response<List<CoterieInfo>> getJoinCoterie(Long userId,Integer pageNum,Integer pageSize) {
    	if(userId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
    	if(pageNum==null || pageNum<1){
    		pageNum=1;
    	}
    	if(pageSize==null || pageSize<1){
    		pageSize=10;
    	}
        return coterieApi.getMyJoinCoterie(userId.toString(),pageNum,pageSize);
    }

    /**
     * 分页获取私圈列表
     *
     * @return
     */
    @ApiOperation("分页获取私圈列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/coterieInfo/list")
    public Response<PageList<CoterieInfo>> queryPage(Integer currentPage, Integer pageSize, HttpServletRequest request) {
    	if(currentPage==null || currentPage<1){
    		currentPage=1;
    	}
    	if(pageSize==null || pageSize<1){
    		pageSize=10;
    	}
        PageList<CoterieInfo> page = new PageList<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        List<CoterieInfo> list = coterieApi.queryPage(currentPage, pageSize).getData();
        page.setCount((long) list.size());
        page.setEntities(list);
        return ResponseUtils.returnObjectSuccess(page);

    }

    /**
     * 首页 热门 私圈
     *
     * @param
     * @return
     */
    @ApiOperation("获取热门私圈 ")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/coterieInfo/list/recommend")
    public Response<PageList<CoterieInfo>> getRecommendCoterie(Integer currentPage, Integer pageSize, HttpServletRequest request) {
        PageList<CoterieInfo> page = new PageList<>();
        page.setCurrentPage(currentPage);
        page.setPageSize(pageSize);
        
        //1、优先展示后台人工推荐的私圈X个，再从剩下的私圈中选取加入人数超过50人的私圈Y，X+Y<=200；
        //2、人工推荐的私圈按照推荐排序从小到大顺序，加入人数超过50人的私圈按照加入人数从多到少排；
        List<CoterieInfo> list = ResponseUtils.getResponseData(coterieApi.queryHotCoterieList(currentPage, pageSize));
        page.setCount(null);
        page.setEntities(list);
        return ResponseUtils.returnObjectSuccess(page);
    }
    
    /**
     * 私圈 二维码
     *
     * @param
     * @return
     */
    @ApiOperation("获取私圈二维码base64 ")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/coterieInfo/qr")
    public Response<String> getQrurl(Long coterieId, HttpServletRequest request) {
    	if(coterieId==null){
    		return ResponseUtils.returnCommonException("参数错误");
    	}
        return coterieApi.regroupQr(coterieId);
    }
}
