/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018年1月19日
 * Id: OrderSDKAutoConfig.java, 2018年1月19日 下午6:28:49 yehao
 */
package com.yryz.quanhu.order.sdk.autoconfig;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月19日 下午6:28:49
 * @Description 订单模块SDK的自动配置
 */
@Configuration
@ComponentScan(basePackages = "com.yryz.quanhu.order.sdk")
public class OrderSDKAutoConfig {

}
