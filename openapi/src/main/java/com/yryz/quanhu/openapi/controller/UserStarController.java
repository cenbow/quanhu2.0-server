package com.yryz.quanhu.openapi.controller;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Sets;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.user.dto.StarInfoDTO;
import com.yryz.quanhu.user.vo.UserDynamicVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.annotation.UserBehaviorValidation;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.common.utils.WebUtil;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.openapi.utils.CommonUtils;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.vo.StarInfoVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Api(description = "用户达人接口")
@RestController
@RequestMapping(value = "services/app")
public class UserStarController {
    private static final Logger logger = LoggerFactory.getLogger(UserStarController.class);

    @Reference(check = false)
    private UserStarApi starApi;

//    @Reference
//    private DymaicService dymaicService;

    @Reference(check = false)
    private ElasticsearchService elasticsearchService;

    /**
     * 达人申请
     *
     * @param info
     * @return
     */
    @ResponseBody
    @UserBehaviorValidation(login = true)
    @ApiOperation("达人申请")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/{version}/star/starApply")
    public Response<Boolean> save(@RequestBody StarAuthInfo info, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        info.setAuthWay((byte) 10);
        info.setUserId(header.getUserId());
        info.setAppId(header.getAppId());
        Boolean result = ResponseUtils.getResponseData(starApi.save(info));
        return ResponseUtils.returnApiObjectSuccess(result);
    }

    /**
     * 达人信息编辑
     *
     * @param info
     * @return
     */
    @ApiOperation(" 达人信息编辑")
    @UserBehaviorValidation(login = true)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @PostMapping(value = "/{version}/star/editStarAuth")
    public Response<Boolean> update(@RequestBody StarAuthInfo info, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        info.setAuthWay((byte) 10);
        info.setUserId(header.getUserId());
        info.setAppId(header.getAppId());
        Boolean result = ResponseUtils.getResponseData(starApi.update(info));
        return ResponseUtils.returnApiObjectSuccess(result);
    }

    /**
     * 达人信息获取
     *
     * @param custId
     * @return
     */
    @ApiOperation("达人信息获取")
    @UserBehaviorValidation(login = false)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/star/getStarAuth")
    public Response<StarAuthInfo> get(String userId, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        StarAuthInfo authInfo = null;
        if (StringUtils.isNotBlank(userId)) {
            authInfo = ResponseUtils.getResponseData(starApi.get(userId));
            if (authInfo != null) {
                authInfo.setContactCall(CommonUtils.getPhone(authInfo.getContactCall()));
                authInfo.setIdCard(CommonUtils.getIdCardNo(authInfo.getIdCard()));
                authInfo.setRealName(CommonUtils.getRealName(authInfo.getRealName()));
            }
        } else {
            authInfo = ResponseUtils.getResponseData(starApi.get(header.getUserId()));
        }
        return ResponseUtils.returnApiObjectSuccess(authInfo);
    }

    /**
     * 达人推荐列表
     *
     * @param custId
     * @return
     */
    @ApiOperation("达人推荐列表")
    @UserBehaviorValidation(login = false)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/star/starCommend")
    public Response<PageList<StarInfoVO>> recommendList(Integer pageSize, Integer currentPage, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        StarAuthParamDTO paramDTO = new StarAuthParamDTO();
        paramDTO.setUserId(NumberUtils.createLong(header.getUserId()));
        paramDTO.setCurrentPage(currentPage);
        paramDTO.setPageSize(pageSize);
        PageList<StarInfoVO> list = ResponseUtils.getResponseData(starApi.starList(paramDTO));
        return ResponseUtils.returnApiObjectSuccess(list);
    }

    /**
     * @param start
     * @param limit
     * @param request
     * @return
     */
    @ApiOperation("某一标签下的达人列表")
    @UserBehaviorValidation(login = false)
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/star/label/list")
    public Response<PageList<StarInfoVO>> labelStarList(Long categoryId, Integer pageSize, Integer currentPage, HttpServletRequest request) {
        RequestHeader header = WebUtil.getHeader(request);
        /*StarAuthParamDTO paramDTO = new StarAuthParamDTO();
        paramDTO.setUserId(NumberUtils.createLong(header.getUserId()));

        paramDTO.setCategoryId(categoryId);
        paramDTO.setCurrentPage(currentPage);
        paramDTO.setPageSize(pageSize);
        Response<PageList<StarInfoVO>> labelStarList = starApi.labelStarList(paramDTO);

        PageList<StarInfoVO> pageList = labelStarList.getData();
        getStarDynamic(pageList);*/

        StarInfoDTO starInfoDTO = new StarInfoDTO();
        starInfoDTO.setTagId(categoryId);
        starInfoDTO.setCurrentPage(currentPage);
        starInfoDTO.setPageSize(pageSize);
        starInfoDTO.setUserId(NumberUtils.createLong(header.getUserId()));

        PageList<StarInfoVO> pageList = ResponseUtils.getResponseData(elasticsearchService.searchStarUser(starInfoDTO));
//        logger.info("labelStarList result: {}", GsonUtils.parseJson(pageList.));
        return ResponseUtils.returnApiObjectSuccess(pageList);
    }

    /*private void getStarDynamic(PageList<StarInfoVO> pageList) {
        try {
            if (pageList != null) {
                List<StarInfoVO> entities = pageList.getEntities();
                if (CollectionUtils.isNotEmpty(entities)) {
                    Set<Long> userIds = Sets.newHashSet();
                    for (StarInfoVO entity : entities) {
                        userIds.add(entity.getUserInfo().getUserId());
                    }
                    if (CollectionUtils.isNotEmpty(userIds)) {
                        Response<Map<Long, Dymaic>> lastSends;
                        lastSends = dymaicService.getLastSend(Sets.newHashSet(userIds));
                        logger.info("dymaicService.getLastSend result: ", GsonUtils.parseJson(lastSends));
                        Map<Long, Dymaic> dynaicMap = lastSends.getData();
                        if (MapUtils.isNotEmpty(dynaicMap)) {
                            for (StarInfoVO starInfoVO : entities) {
                                try {
                                    Dymaic dymaic = dynaicMap.get(starInfoVO.getUserInfo().getUserId());
                                    if (dymaic != null) {
                                        UserDynamicVO userDynamicVO = new UserDynamicVO();
                                        BeanUtils.copyProperties(userDynamicVO, dymaic);
                                        starInfoVO.setDynamic(userDynamicVO);
                                    }
                                } catch (Exception e) {
                                    logger.error("for setDynamic error", e);
                                }
                            }
                        }
                    }

                }
            }
        } catch (Exception e) {
            logger.error("getStarDynamic error", e);
        }
    }*/

}
