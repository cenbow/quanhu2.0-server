package com.yryz.quanhu.openapi.utils;

import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/29
 * @description
 */
public class ComponentUtils {

    public static AfsCheckRequest getAfsCheckRequest(SmsVerifyCodeDTO codeDTO) {
        AfsCheckRequest afsCheckReq = null;
        String session = codeDTO.getSession();//阿里验证码所需参数
        String sig = codeDTO.getSig();//阿里验证码所需参数
        String token = codeDTO.getToken();//阿里验证码所需参数
        String scene = codeDTO.getScene();//阿里验证码所需参数
        if (StringUtils.isNotBlank(session) && StringUtils.isNotBlank(sig)
                && StringUtils.isNotBlank(token) && StringUtils.isNotBlank(scene)) {
            afsCheckReq = new AfsCheckRequest();
            afsCheckReq.setScene(scene);
            afsCheckReq.setSession(session);
            afsCheckReq.setSig(sig);
            afsCheckReq.setToken(token);
        }
        return afsCheckReq;
    }
}
