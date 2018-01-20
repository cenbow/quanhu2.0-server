package com.yryz.quanhu.message.im.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.message.im.entity.ImBlackListRequest;
import com.yryz.quanhu.message.im.entity.ImRequest;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
public interface ImAPI {

    /**
     * 同步好友信息
     * @param imRequest
     * @return
     */
    Response<Void> syncUserInfo(ImRequest imRequest);


    Response<Boolean> putToBlackList(ImBlackListRequest imBlackListRequest);
}
