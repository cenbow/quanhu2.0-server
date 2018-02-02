package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
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
    private static final int CHAR_51 = 3;
    private static final String CHAR_3F = "%3F";
    private static final String CHAR_63 = "?";
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


    @ApiOperation("发布私圈")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "info", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/{version}/coterieInfo/create")
    public Response<CoterieInfo> publish(@RequestHeader Long userId, @RequestBody CoterieBasicInfo info, HttpServletRequest request) {
        info.setOwnerId(userId.toString());
        if (info.getJoinFee().equals(0)) {
            //免费加入方式，成员必须审核
            info.setJoinCheck(11);
        } else {
            //付费加入方式，成员必须不审核
            info.setJoinCheck(10);
        }
        //info.setOwnerId(useId);
        return coterieApi.applyCreate(info);
    }

    @ApiOperation("设置私圈， 更新圈子的数据")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "config", paramType = "body", required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @PostMapping(value = "/{version}/coterieInfo/config")
    public Response<CoterieInfo> config(@RequestHeader Long userId, @RequestBody CoterieInfo config, HttpServletRequest request) {

        Response<Integer> response = coterieMemberAPI.permission(userId, config.getCoterieId());

        if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
            Integer permission = response.getData();
            if (permission == null) {
                throw QuanhuException.busiError("获取权限异常 ：permission is null");
            } else {
                if (permission != MemberConstant.Permission.OWNER.getStatus()) {
                    throw QuanhuException.busiError("权限异常 ：您不是圈主无法更新私圈相关信息");
                }
            }
        } else {

            throw QuanhuException.busiError("获取权限接口异常: CoterieMemberAPI.permission");
        }


        Response<CoterieInfo> rpcRecord = coterieApi.queryCoterieInfo(config.getCoterieId());
        CoterieInfo record = rpcRecord.getData();

        String tempStr;
        if (StringUtils.isNotBlank(tempStr = config.getIcon())) {
            record.setIcon(tempStr);
        }
        //todo  最多7个字 不能重复
        if (StringUtils.isNotBlank(tempStr = config.getName())) {
            record.setName(tempStr);
        }
        if (StringUtils.isNotBlank(tempStr = config.getQrUrl())) {
            record.setQrUrl(tempStr);
        }
        if (StringUtils.isNotBlank(tempStr = config.getIntro())) {
            record.setIntro(tempStr);
        }
        if (StringUtils.isNotBlank(tempStr = config.getOwnerIntro())) {
            record.setOwnerIntro(tempStr);
        }

        Integer tempNum;
        //todo  不能超过2000
        if ((tempNum = config.getMemberNum()) != null) {
            //设置最大可加入人数，暂不支持
            //record.setMemberNum(tempNum);
            //circleDBRecord.setMemberNum(tempNum);
        }
        if ((tempNum = config.getJoinFee()) != null) {
            //Assert.isTrue(config.getJoinFee() <= 100,"加入费用不能超过100！" );
            record.setJoinFee(tempNum);
        }
        if ((tempNum = config.getConsultingFee()) != null) {
            //Assert.isTrue(config.getConsultingFee() <= 100 , "咨询费用不能超过100悠然币！" );
            record.setConsultingFee(tempNum);
        }
        if (config.getJoinCheck() != null) {
            record.setJoinCheck(config.getJoinCheck());
        }


        //todo 加入方式与是否审核互斥

        if (null != config.getJoinFee() && config.getJoinFee() != 0) {
            record.setJoinCheck(10);
        }

        Response<CoterieInfo> result = coterieApi.modifyCoterieInfo(record);
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
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/coterieInfo/single")
    public Response<CoterieInfo> details(Long coterieId, HttpServletRequest request) {
        //Assert.notNull(coterieId, "私圈id不能为null！");
        Response<CoterieInfo> coterieInfo = coterieApi.queryCoterieInfo(coterieId);
        CoterieInfo rpcCoterieInfo = (CoterieInfo) coterieInfo.getData();
        try {
            //todo
            // if (rpcCoterieInfo != null) {
            //String regroupQrUrl = coterieApi.regroupQr(rpcCoterieInfo).getData();
            //coterieInfo.getData().setQrUrl(regroupQrUrl);
            //}
        } catch (Exception e) {

            logger.error(String.format("query Coterie details error, coterieId=[%s]", coterieId), e);
        }
        return coterieInfo;
    }

    /**
     * 获取我创建的私圈详情
     *
     * @return
     */
    @ApiOperation("获取我创建的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true)})
    @GetMapping(value = "/{version}/coterieInfo/creator")
    public Response<List<CoterieInfo>> getMyCreateCoterie(@RequestHeader Long userId) {

        return coterieApi.getMyCreateCoterie(userId.toString());
    }

    /**
     * 获取我加入的私圈详情
     *
     * @return
     */
    @ApiOperation("获取我加入的私圈列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true),
            @ApiImplicitParam(name = "userId", paramType = "header", required = true) })
    @GetMapping(value = "/{version}/coterieInfo/list/join")
    public Response<List<CoterieInfo>> getMyJoinCoterie(@RequestHeader Long userId) {

        return coterieApi.getMyJoinCoterie(userId.toString());
    }

    /**
     * 分页获取私圈列表
     *
     * @return
     */
    @ApiOperation("分页获取私圈列表")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/coterieInfo/list/")
    public Response<PageList<CoterieInfo>> queryPage(Integer currentPage, Integer pageSize, HttpServletRequest request) {
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


        //todo 1、优先展示后台人工推荐的私圈X个，再从剩下的私圈中选取加入人数超过50人的私圈Y，X+Y<=200；
        //todo 2、人工推荐的私圈按照推荐排序从小到大顺序，加入人数超过50人的私圈按照加入人数从多到少排；
        List<CoterieInfo> list = coterieApi.queryPageForApp(currentPage, pageSize).getData();
        page.setCount((long) list.size());
        page.setEntities(list);
        return ResponseUtils.returnObjectSuccess(page);
    }
}
