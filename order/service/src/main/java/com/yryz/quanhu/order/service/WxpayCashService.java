package com.yryz.quanhu.order.service;

import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/26 14:51
 * Created by lifan
 */
public interface WxpayCashService {

    /**
     * 获取微信提现加密RSA公钥
     *
     * @return
     */
    Map<String, String> wxpayCashPublicKey();

    /**
     * 微信提现到银行卡
     *
     * @param orderId
     * @param userName
     * @param bankCardNo
     * @param wxBankId
     * @param amount
     * @return
     */
    boolean wxpayCash(String orderId, String userName, String bankCardNo, String wxBankId, String amount);

    /**
     * 查询微信提现到银行卡的结果
     *
     * @param orderId
     * @return
     */
    Map<String, String> wxpayCashQuery(String orderId);

}
