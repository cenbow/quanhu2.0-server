package com.yryz.quanhu.openapi.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Maps;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.openapi.ApplicationOpenApi;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.user.service.AccountApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
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
    public Response<Map<String, Object>> forceUpgrade(@RequestHeader String appVersion, @RequestParam String os, HttpServletRequest request) {
        if (StringUtils.isEmpty(os)) {
            return ResponseUtils.returnException(QuanhuException.busiError("os参数缺失"));
        }
        if (StringUtils.isEmpty(appVersion)) {
            return ResponseUtils.returnException(QuanhuException.busiError("appVersion参数缺失"));
        }
        Map<String, Object> map = Maps.newHashMap();
        //是否强制升级
        map.put("forceUpgradeFlag", false);
        //当前版本号
        map.put("version", appVersion);
        //当前操作系统
        map.put("os", os);
        //要升级的版本号
        map.put("upgradeVersion", "1.0.0");
        try {
            String configStr = ResponseUtils.getResponseData(basicConfigApi.getValue(os));
            JSONObject config = GsonUtils.json2Obj(configStr, JSONObject.class);
            String upgradeVersion = config.get("upgradeVersion").toString();
            //强制升级的版本比当前版本高
            if (new Integer(upgradeVersion.replace(".", "")) > new Integer(appVersion)) {
                map.put("forceUpgradeFlag", true);
            } else {
                map.put("forceUpgradeFlag", false);
            }
            map.put("upgradeVersion", upgradeVersion);
        } catch (Exception e) {
            logger.error("forceUpgrade error", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }
}
