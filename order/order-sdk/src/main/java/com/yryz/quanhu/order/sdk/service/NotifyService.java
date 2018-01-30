package com.yryz.quanhu.order.sdk.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.sdk.IOrderNotifyService;
import com.yryz.quanhu.order.sdk.constant.OrderConstants;
import com.yryz.quanhu.order.sdk.constant.OrderType;
import com.yryz.quanhu.order.sdk.dao.OrderDao;
import com.yryz.quanhu.order.sdk.dto.OutputOrder;
import com.yryz.quanhu.order.sdk.entity.Order;
import com.yryz.quanhu.order.vo.OrderInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/23 18:19
 * Created by lifan
 */
@Service
public class NotifyService {

    private static final Logger logger = LoggerFactory.getLogger(NotifyService.class);

    @Autowired(required = false)
    private List<IOrderNotifyService> notifyServiceList = new ArrayList<>();

    @Reference
    private OrderAsynApi orderAsynApi;

    @Autowired
    private OrderDao orderDao;

    @Transactional
    public void notify(OrderInfo orderInfo) {
        logger.info("notify method , notify parameters : {}", orderInfo.toString());
        logger.info("notify method , check notify is valid .");
        // 验证平台订单状态
        Response<OrderInfo> response = orderAsynApi.getOrderInfo(orderInfo.getOrderId());
        if (!response.success() || !OrderType.ORDER_STATE_SUCCESS.equals(response.getData().getOrderState())) {
            logger.error("notify method , check rpcOrder pay faild ! rpc data = {}",
                    response.getData() == null ? "" : JSON.toJSONString(response.getData()));
            return;
        }
        // 查询本地订单
        Order localOrder = orderDao.selectByKid(Long.valueOf(orderInfo.getOrderId()));
        // 对比本地订单和回调订单信息是否一致
        if(!verify(orderInfo, localOrder)){
            logger.error("verify orderInfo localOrder failure");
            return;
        }
        logger.info("notify method , check callback is repeat .");
        // 校验是否重复回调
        if (OrderConstants.OrderState.SUCCESS.equals(localOrder.getOrderState())) {
            logger.error("notify method , check localOrder already success ! don't repeat notify ! local data = {}",
                    JSON.toJSONString(localOrder));
            return;
        }
        logger.info("notify method , update local order state is success .");
        // 修改订单完成状态
        int updateCount;
        try {
            updateCount = orderDao.updateOrderState(localOrder.getKid(), OrderConstants.OrderState.SUCCESS);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.Exception.getCode(),
                    ExceptionEnum.Exception.getShowMsg(),
                    "notify method , updateOrderState error !", e);
        }
        logger.info("notify method , updateCount :" + updateCount);
        // 本地订单修改完成，提交与订单绑定的业务数据至业务方
        if (updateCount > 0) {
            if (CollectionUtils.isEmpty(notifyServiceList)) {
                return;
            }
            IOrderNotifyService orderNotifyService = null;
            for (IOrderNotifyService notifyService : notifyServiceList) {
                String moduleEnum = notifyService.getModuleEnum();
                if (!StringUtils.isEmpty(moduleEnum) && moduleEnum.equals(localOrder.getModuleEnum())) {
                    orderNotifyService = notifyService;
                    break;
                }
            }
            if (null != orderNotifyService) {
                //回调业务
                OutputOrder outputOrder = new OutputOrder();
                outputOrder.setOrderId(localOrder.getKid());
                outputOrder.setCost(localOrder.getCost());
                outputOrder.setPayType(localOrder.getPayType());
                outputOrder.setModuleEnum(localOrder.getModuleEnum());
                outputOrder.setCoterieId(localOrder.getCoterieId());
                outputOrder.setResourceId(localOrder.getResourceId());
                outputOrder.setBizContent(localOrder.getBizContent());
                outputOrder.setCreateUserId(localOrder.getCreateUserId());
                outputOrder.setCreateDate(localOrder.getCreateDate());
                orderNotifyService.notify(outputOrder);
            }
        }
    }

    /**
     * 校验回调订单与本地订单是否一致
     *
     * @param orderInfo
     * @param order
     */
    private boolean verify(OrderInfo orderInfo, Order order) {
        // 校验createUserId,cost,payType,orderType,productId,productType,callback
        if (orderInfo == null || order == null) {
            logger.error("orderInfo or order is null");
            return false;
        }
        if (!orderInfo.getCustId().equals(String.valueOf(order.getCreateUserId()))) {
            logger.error("createUserId is difference");
            return false;
        }
        if (!orderInfo.getCost().equals(order.getCost())) {
            logger.error("cost is difference");
            return false;
        }
        if (!orderInfo.getOrderType().equals(order.getPayType() - 10)) {
            logger.error("payType is difference");
            return false;
        }
        if (!orderInfo.getType().equals(order.getOrderType() - 10)) {
            logger.error("orderType is difference");
            return false;
        }
        if (!orderInfo.getProductId().equals(order.getProductId())) {
            logger.error("productId is difference");
            return false;
        }
        if (!orderInfo.getProductType().equals(order.getProductType())) {
            logger.error("productType is difference");
            return false;
        }
        if (!orderInfo.getCallback().equals(order.getCallback())) {
            logger.error("callback is difference");
            return false;
        }
        return true;
    }
}
