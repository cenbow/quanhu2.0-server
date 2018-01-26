/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017年5月16日
 * Id: ColumnServiceImpl.java,v 1.0 2017年5月16日 下午3:57:08 yehao
 */
package com.yryz.quanhu.order.sdk.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderConstants;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dao.OrderDao;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.order.sdk.entity.Order;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.PreOrderVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author liupan
 * @date 2017年9月16日 下午3:57:08
 */
@Service
public class OrderService implements OrderSDK {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    @Value("${order.notify.queue}")
    private String notifyQueue;

    @Autowired
    private OrderDao orderDao;

    @Reference
    private IdAPI idAPI;

    @Reference
    private OrderAsynApi orderAsynApi;

    @Reference
    private OrderApi orderApi;

    /**
     * 用户创建待支付订单接口(用于客户端创建订单)
     *
     * @param inputOrder
     * @return 订单ID
     */
    @Transactional
    @Override
    public Long createOrder(InputOrder inputOrder) {
        //检查入参是否合法
        check(inputOrder);
        // 调用idAPI生成订单号
        Response<String> orderIdResponse = idAPI.getOrderId();
        if (null == orderIdResponse || !orderIdResponse.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(),
                    orderIdResponse == null ? "获取订单ID失败" : orderIdResponse.getErrorMsg());
        }
        Long orderId = Long.valueOf(orderIdResponse.getData());
        // 封装预订单PreOrderVo
        OrderEnum orderEnum = inputOrder.getOrderEnum();
        PreOrderVo orderVo = orderEnum.getOrder(orderId, inputOrder.getFromId(), inputOrder.getToId(), inputOrder.getCost());
//        orderVo.getOrderInfo().setBizContent(inputOrder.getBizContent());
        orderVo.getOrderInfo().setCallback(notifyQueue);
        logger.info("createOrder getOrderVo orderVo[orderinfo]:" + orderVo.getOrderInfo().toString());
        logger.info("createOrder getOrderVo orderVo[accounts]:" + orderVo.getAccounts().toString());
        logger.info("createOrder getOrderVo orderVo[integrals]:" + orderVo.getIntegrals().toString());
        // 提交OrderVo对象
        Response<PreOrderVo> response = orderAsynApi.createOrder(orderVo);
        if (null == response || !response.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(),
                    response == null ? "创建订单失败" : response.getErrorMsg());
        }
        PreOrderVo returnOrder = response.getData();
        logger.info("createOrder createOrder returnOrder[orderinfo]:" + returnOrder.getOrderInfo().toString());
        logger.info("createOrder createOrder returnOrder[accounts]:" + returnOrder.getAccounts().toString());
        logger.info("createOrder createOrder returnOrder[integrals]:" + returnOrder.getIntegrals().toString());
        // 保存order对象
        try {
            OrderInfo orderInfo = returnOrder.getOrderInfo();
            Order order = new Order();
            order.setKid(orderId);
            order.setCost(inputOrder.getCost());
            //新标准枚举字段统一从10开始
            //新版Order的payType对应OrderInfo的orderType
            order.setPayType(orderInfo.getOrderType() + 10);
            //新标准枚举字段统一从10开始
            //新版Order的orderType对应OrderInfo的type
            order.setOrderType(orderInfo.getType() + 10);
            order.setProductId(orderInfo.getProductId());
            order.setProductDesc(orderInfo.getProductDesc());
            order.setProductType(orderInfo.getProductType());
            order.setOrderDesc(orderInfo.getOrderDesc());
            order.setRemark(orderVo.getOrderInfo().getRemark());
            //新标准枚举字段统一从10开始
            order.setOrderState(orderInfo.getOrderState() + 10);
            order.setCallback(orderInfo.getCallback());
            order.setBizContent(inputOrder.getBizContent());
            order.setModuleEnum(inputOrder.getModuleEnum());
            order.setCoterieId(inputOrder.getCoterieId() == null ? 0 : inputOrder.getCoterieId());
            order.setResourceId(inputOrder.getResourceId());
            order.setCreateUserId(inputOrder.getCreateUserId());
            order.setLastUpdateUserId(inputOrder.getCreateUserId());
            logger.info("insert order:" + order.toString());
            orderDao.insertOrder(order);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(), "save order to db error!", e);
        }
        return orderId;
    }

    /**
     * 系统触发执行订单接口(用于业务端调用，如圈主回答问题后需要将钱支付给圈主，到期未回答退款给用户)
     *
     * @param orderEnum 订单枚举
     * @param toId      收款人ID
     * @param cost      金额
     * @return 成功返回订单ID，否则抛出QuanhuException
     */
    @Override
    public Long executeOrder(OrderEnum orderEnum, Long toId, Long cost) {
        if (null == orderEnum)
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "orderEnum is null");
        if (null == toId)
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "toId is null");
        if (null == cost || cost < 0)
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "cost is null or less 0");
        // 调用idAPI生成订单号
        Response<String> orderIdResponse = idAPI.getOrderId();
        if (null == orderIdResponse || !orderIdResponse.success()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(),
                    orderIdResponse == null ? "获取订单ID失败" : orderIdResponse.getErrorMsg());
        }
        Long orderId = Long.valueOf(orderIdResponse.getData());
        PreOrderVo orderVo = orderEnum.getOrder(orderId, Long.valueOf(AccountEnum.SYSID), toId, cost);
        Response<?> response = orderApi.executeOrder(orderVo.getOrderInfo(), orderVo.getAccounts(), orderVo.getIntegrals(), null, null, null);
        if (null != response && response.success()) {
            return orderId;
        }
        throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                ExceptionEnum.BusiException.getShowMsg(), response == null ? "execute order error" : response.getErrorMsg());
    }

    private void check(InputOrder inputOrder) {
        if (inputOrder == null)
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "inputOrder is null");
        if (StringUtils.isBlank(inputOrder.getModuleEnum()))
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "moduleEnum is null");
        if (null == inputOrder.getCoterieId())
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "coterieId is null");
        if (null == inputOrder.getResourceId())
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "resourceId is null");
        if (null == inputOrder.getCost() || inputOrder.getCost() < 0)
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "cost is null or less 0");
        if (null == inputOrder.getCreateUserId())
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "createUserId is null");
    }

    /**
     * 检测是否已经购买成功
     *
     * @param moduleEnum
     * @param userId
     * @param resourceId
     * @return
     */
    @Override
    public boolean isBuyOrderSuccess(String moduleEnum, long userId, long resourceId) {
        List<Order> orderList = orderDao.selectLatestOrder(moduleEnum, userId, resourceId);
        if (CollectionUtils.isEmpty(orderList)) {
            return false;
        }
        boolean success = false;
        for (Order order : orderList) {
            if (OrderConstants.OrderState.SUCCESS.equals(order.getOrderState())) {
                success = true;
                break;
            } else {
                Response<OrderInfo> orderInfoResponse = orderAsynApi.getOrderInfo(String.valueOf(order.getKid()));
                if (orderInfoResponse.success() && null != orderInfoResponse.getData()) {
                    if (OrderConstants.RpcOrderState.SUCCESS.equals(orderInfoResponse.getData().getOrderState())) {
                        success = true;
                        break;
                    }
                }
            }
        }
        return success;
    }

}
