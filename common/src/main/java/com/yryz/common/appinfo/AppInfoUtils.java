package com.yryz.common.appinfo;

import com.alibaba.dubbo.rpc.RpcContext;
import com.yryz.common.constant.Constants;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/7
 * @description
 */
public class AppInfoUtils {

    public static AppInfo getAppInfo() {
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId(RpcContext.getContext().getAttachment(Constants.APP_ID));
        return appInfo;
    }

}
