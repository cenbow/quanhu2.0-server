package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.openapi.controller
 * @Desc:
 * @Date: 2018/2/5.
 */
@Api(description = "配置管理")
@RestController
@RequestMapping(value = "services/app")
public class ConfigController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private BasicConfigApi basicConfigApi;

    @ApiOperation("获取强制升级配置接口")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/config/forceUpgrade")
    public Response<Map<String, Object>> forceUpgrade(@RequestHeader String appVersion, @RequestHeader String devType, HttpServletRequest request) {
        if (StringUtils.isEmpty(devType)) {
            return ResponseUtils.returnException(QuanhuException.busiError("devType参数缺失"));
        }
        if (StringUtils.isEmpty(appVersion)) {
            return ResponseUtils.returnException(QuanhuException.busiError("appVersion参数缺失"));
        }
        Map<String, Object> map = Maps.newHashMap();
        //是否强制升级
        map.put("forceUpgradeFlag", false);
        //当前版本号
        map.put("version", appVersion);
        //要升级的版本号
        map.put("upgradeVersion", "1.0.0");
        try {
            String devOs = "";
            if (CommonConstants.DEV_TYPE_IOS.equals(devType)) {
                devOs = "IOS";
            }
            if (CommonConstants.DEV_TYPE_ANROID.equals(devType)) {
                devOs = "Android";
            }
            String configStr = ResponseUtils.getResponseData(basicConfigApi.getValue(devOs));
            Map<String, Object> config = GsonUtils.json2Obj(configStr, Map.class);
            String upgradeVersion = config.get("upgradeVersion").toString();
            //强制升级的版本比当前版本高
            if (new Integer(upgradeVersion.replace(".", "")) > new Integer(appVersion.replace(".", ""))) {
                map.put("forceUpgradeFlag", true);
                map.put("releaseNote", config.get("releaseNote").toString());
            } else {
                map.put("forceUpgradeFlag", false);
            }
            map.put("upgradeVersion", upgradeVersion);
        } catch (Exception e) {
            logger.error("forceUpgrade error", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }

    @ApiOperation("获取广告时长配置接口")
    @ApiImplicitParam(name = "version", paramType = "path", allowableValues = ApplicationOpenApi.CURRENT_VERSION, required = true)
    @GetMapping(value = "/{version}/config/adTime")
    public Response<Map<String, String>> forceUpgrade(HttpServletRequest request) {
        String configStr = ResponseUtils.getResponseData(basicConfigApi.getValue("adTime"));
        Map<String, String> map = Maps.newHashMap();
        map.put("adTime", configStr);
        return ResponseUtils.returnObjectSuccess(map);
    }
}
