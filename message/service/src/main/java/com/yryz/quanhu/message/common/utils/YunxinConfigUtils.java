package com.yryz.quanhu.message.common.utils;

import com.yryz.common.context.SpringContextHelper;
import com.yryz.quanhu.message.common.im.yunxin.YunxinConfig;
import com.yryz.quanhu.message.common.remote.YunXinConfigRemote;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/7
 * @description
 */
public class YunxinConfigUtils {


    public static YunxinConfig getYunxinConfig() {
        YunXinConfigRemote yunXinConfigRemote = SpringContextHelper.getBean(YunXinConfigRemote.class);
        YunxinConfig yunxinConfig = yunXinConfigRemote.getYunxinConfig();
        return yunxinConfig;
    }
}
