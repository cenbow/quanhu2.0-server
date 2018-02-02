package com.yryz.quanhu.order.sdk.constant;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.order.enums.AccountEnum;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.vo.AccountOrder;
import com.yryz.quanhu.order.vo.IntegralOrder;
import com.yryz.quanhu.order.vo.OrderInfo;
import com.yryz.quanhu.order.vo.PreOrderVo;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liupan
 * @version 1.0
 * @date 2017年9月11日 下午3:02:01
 * @Description 订单枚举类
 */
public enum OrderEnum {

    /**
     * 付费提问:"付费{cost}向圈主提问"
     */
    QUESTION_ORDER(OrderType.ORDER_TYPE_MINUS, ProductEnum.COTERIE_ASK_PAY, BranchFeesEnum.QUESTION, "付费提问"),

    /**
     * 圈主回答
     */
    ANSWER_ORDER(OrderType.ORDER_TYPE_PLUS, ProductEnum.COTERIE_ASK_PAY, BranchFeesEnum.ANSWER, "圈主回答问题"),

    /**
     * 未回答退款："圈主未回答问题，退款{cost}"
     */
    NO_ANSWER_ORDER(OrderType.ORDER_TYPE_PLUS, ProductEnum.COTERIE_ASK_RETURN, BranchFeesEnum.NO_ANSWER, "问答退款"),

    /**
     * 付费阅读:"付费阅读文章,金额:{cost}"
     */
    READ_ORDER(OrderType.ORDER_TYPE_MINUS, ProductEnum.RESOURCE_PAY, BranchFeesEnum.READ, "付费阅读"),

    /**
     * 付费加入私圈:"付费加入私圈,金额:{cost}"
     */
    JOIN_COTERIE_ORDER(OrderType.ORDER_TYPE_MINUS, ProductEnum.COTERIE_JOIN, BranchFeesEnum.JOIN_COTERIE, "付费加入私圈"),

    /**
     * 付费活动报名
     */
    ACTIVITY_SIGNUP_ORDER(OrderType.ORDER_TYPE_MINUS, ProductEnum.ACTIVITY_SIGNUP, BranchFeesEnum.ACTIVITY_SIGNUP, "付费报名活动"),

    /**
     * 打赏付费
     */
    REWARD_ORDER(OrderType.ORDER_TYPE_MINUS, ProductEnum.REWARD_TYPE, BranchFeesEnum.REWARD, "打赏");

    /**
     * 支付类型，1，加币；0，减币
     */
    private Integer orderType;

    /**
     * 产品枚举
     */
    private ProductEnum productEnum;

    /**
     * 分费枚举
     */
    private BranchFeesEnum branchFeesEnum;

    /**
     * 备注
     */
    private String remark;


    OrderEnum(Integer orderType, ProductEnum productEnum, BranchFeesEnum branchFeesEnum, String remark) {
        this.orderType = orderType;
        this.productEnum = productEnum;
        this.branchFeesEnum = branchFeesEnum;
        this.remark = remark;
    }

    public PreOrderVo getOrder(Long orderId, Long fromId, Long toId, long cost) {
        OrderInfo orderInfo = getOrderInfo(orderId, fromId, cost);
        List<AccountOrder> accounts = new ArrayList<>();
        List<IntegralOrder> integrals = new ArrayList<>();
        List<FeeDetail> fees = branchFeesEnum.getFee();
        for (FeeDetail feeDetail : fees) {
            // 0消费减费，1消费加费，2积分减费，3积分加费
            switch (feeDetail.getType()) {
                case 0:
                    AccountOrder accountOrderMinus = getAccountOrder(orderId, fromId, toId, cost, feeDetail);
                    accountOrderMinus.setOrderType(OrderType.ORDER_TYPE_MINUS);
                    accounts.add(accountOrderMinus);
                    break;
                case 1:
                    AccountOrder accountOrderPlus = getAccountOrder(orderId, fromId, toId, cost, feeDetail);
                    accountOrderPlus.setOrderType(OrderType.ORDER_TYPE_PLUS);
                    accounts.add(accountOrderPlus);
                    break;
                case 2:
                    IntegralOrder integralOrderMinus = getIntegralOrder(orderId, fromId, toId, cost, feeDetail);
                    integralOrderMinus.setOrderType(OrderType.ORDER_TYPE_MINUS);
                    integrals.add(integralOrderMinus);
                    break;
                case 3:
                    IntegralOrder integralOrderPlus = getIntegralOrder(orderId, fromId, toId, cost, feeDetail);
                    integralOrderPlus.setOrderType(OrderType.ORDER_TYPE_PLUS);
                    integrals.add(integralOrderPlus);
                    break;

            }
        }
        PreOrderVo preOrderVo = new PreOrderVo();
        preOrderVo.setOrderInfo(orderInfo);
        preOrderVo.setAccounts(accounts);
        preOrderVo.setIntegrals(integrals);
        //检查流水记录精度是否存在丢失，若存在采取平台多进少出的原则补齐
        checkOrder(preOrderVo, cost);
        return preOrderVo;
    }

    /**
     * 检查流水记录精度是否存在丢失，若存在采取平台多进少出的原则补齐
     * 四类总额统计，数据应该是跟cost相匹配或者略少，取决于精度。0消费减费，1消费加费，2积分减费，3积分加费
     * 数据完整性校验，总额比cost大，异常;
     * 1.执行精度校验，取出精度差额。i=cost-sum
     * 2.加费优先取系统账户(优先账户)如果没有优先账户，默认取第一个(随机)，i数据累计增加到该账户流水
     * 3.减费优先取非系统用户(随机),如果只有优先账户，取第一个。
     *
     * @param orderVO
     * @param cost
     * @return
     */
    private void checkOrder(PreOrderVo orderVO, long cost) {
        //收益账户流水
        List<AccountOrder> accounts = orderVO.getAccounts();
        //积分账户流水
        List<IntegralOrder> integrals = orderVO.getIntegrals();
        if (CollectionUtils.isEmpty(accounts) && CollectionUtils.isEmpty(integrals)) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(), "账务流水不存在");
        }
        //入账总额
        long inCost = 0;
        //出账总额
        long outCost = 0;
        //平台账户入账
        Object platformInOrder = null;
        //非平台账户入账
        Object otherInOrder = null;
        //平台账户出账
        //ServiceReturn platformOutOrder = null;
        //非平台账户出账
        Object otherOutOrder = null;
        //资金收益账户流水
        if (!CollectionUtils.isEmpty(accounts)) {

            for (AccountOrder accountOrder : accounts) {
                if (OrderType.ORDER_TYPE_PLUS.equals(accountOrder.getOrderType())) {
                    inCost += accountOrder.getCost();
                    //平台入账对象
                    if (null == platformInOrder && AccountEnum.SYSID.equals(accountOrder.getCustId())) {
                        platformInOrder = accountOrder;
                    }
                    //非平台入账对象
                    if (null == otherInOrder && !AccountEnum.SYSID.equals(accountOrder.getCustId())) {
                        otherInOrder = accountOrder;
                    }
                } else if (OrderType.ORDER_TYPE_MINUS.equals(accountOrder.getOrderType())) {
                    outCost += accountOrder.getCost();
                    //平台出账对象
                    //if (null == platformOutOrder && AccountEnum.SYSID.equals(accountOrder.getCustId())) {
                    //   platformOutOrder = accountOrder;
                    //}
                    //非平台出账对象
                    if (null == otherOutOrder && !AccountEnum.SYSID.equals(accountOrder.getCustId())) {
                        otherOutOrder = accountOrder;
                    }
                }
            }
        }
        //积分账户流水
        if (!CollectionUtils.isEmpty(integrals)) {
            for (IntegralOrder integralOrder : integrals) {
                if (OrderType.ORDER_TYPE_PLUS.equals(integralOrder.getOrderType())) {
                    inCost += integralOrder.getCost();
                    //平台入账对象
                    if (null == platformInOrder && AccountEnum.SYSID.equals(integralOrder.getCustId())) {
                        platformInOrder = integralOrder;
                    }
                    //非平台入账对象
                    if (null == otherInOrder && !AccountEnum.SYSID.equals(integralOrder.getCustId())) {
                        otherInOrder = integralOrder;
                    }
                } else if (OrderType.ORDER_TYPE_MINUS.equals(integralOrder.getOrderType())) {
                    outCost += integralOrder.getCost();
                    //平台出账对象
                    //if (null == platformOutOrder && AccountEnum.SYSID.equals(integralOrder.getCustId())) {
                    //    platformOutOrder = integralOrder;
                    //}
                    //非平台出账对象
                    if (null == otherOutOrder && !AccountEnum.SYSID.equals(integralOrder.getCustId())) {
                        otherOutOrder = integralOrder;
                    }
                }
            }
        }
        //入账丢失精度，多的钱优先给平台，如果没有平台分成，给随机其他账户
        if (inCost > cost) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(), "账务流水错误");
        } else if (inCost < cost) {
            //平台入账对象存在,多的钱加给平台
            if (null != platformInOrder) {
                if (platformInOrder instanceof AccountOrder) {
                    AccountOrder accountOrder = (AccountOrder) platformInOrder;
                    accountOrder.setCost(accountOrder.getCost() + (cost - inCost));
                } else if (platformInOrder instanceof IntegralOrder) {
                    IntegralOrder integralOrder = (IntegralOrder) platformInOrder;
                    integralOrder.setCost(integralOrder.getCost() + (cost - inCost));
                } else {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(), "账户详情对象类型不匹配");
                }
            }
            //随机加给其他账户
            else {
                if (null == otherInOrder) {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(), "账务流水错误");
                }

                if (otherInOrder instanceof AccountOrder) {
                    AccountOrder accountOrder = (AccountOrder) otherInOrder;
                    accountOrder.setCost(accountOrder.getCost() + (cost - inCost));
                } else if (otherInOrder instanceof IntegralOrder) {
                    IntegralOrder integralOrder = (IntegralOrder) otherInOrder;
                    integralOrder.setCost(integralOrder.getCost() + (cost - inCost));
                } else {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(), "账户详情对象类型不匹配");
                }
            }
        }
        //出账丢失精度，多的钱优先由随机用户出，如果用户不是出钱方，则抛出异常
        if (outCost > cost) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                    ExceptionEnum.BusiException.getShowMsg(), "账务流水错误");
        } else if (outCost < cost) {
            //多出的钱随机用户承担
            if (null != otherOutOrder) {
                if (otherOutOrder instanceof AccountOrder) {
                    AccountOrder accountOrder = (AccountOrder) otherOutOrder;
                    accountOrder.setCost(accountOrder.getCost() + (cost - inCost));
                } else if (otherOutOrder instanceof IntegralOrder) {
                    IntegralOrder integralOrder = (IntegralOrder) otherOutOrder;
                    integralOrder.setCost(integralOrder.getCost() + (cost - inCost));
                } else {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                            ExceptionEnum.BusiException.getShowMsg(), "账户详情对象类型不匹配");
                }
            }
            //用户不是出钱方，抛异常
            else {
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(),
                        ExceptionEnum.BusiException.getShowMsg(), "账务流水错误");
            }
        }
    }

    /**
     * 封装orderinfo订单基础信息
     *
     * @param orderId
     * @param fromId
     * @param cost
     * @return
     */
    private OrderInfo getOrderInfo(Long orderId, Long fromId, Long cost) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCustId(String.valueOf(fromId));
        orderInfo.setCost(cost);
        orderInfo.setOrderDesc(productEnum.getDesc());
        orderInfo.setOrderId(String.valueOf(orderId));
        orderInfo.setOrderType(orderType);
        orderInfo.setProductDesc(productEnum.getDesc());
        orderInfo.setProductId(String.valueOf(productEnum.getType()));
        orderInfo.setProductType(productEnum.getType());
        orderInfo.setOrderState(OrderType.ORDER_STATE_CREATE);
        orderInfo.setRemark(remark);
        orderInfo.setType(OrderType.TYPE_THREE);
        return orderInfo;
    }

    /**
     * 封装AccountOrder，加减费相关信息。账户余额，不可提现
     *
     * @param orderId
     * @param orderId
     * @param cost
     * @return
     */
    private AccountOrder getAccountOrder(Long orderId, Long fromId, Long toId, Long cost, FeeDetail feeDetail) {
        AccountOrder accountOrder = new AccountOrder();
        accountOrder.setCost(cost * feeDetail.getFee() / 100L);
        if (feeDetail.getCustId().equals("fromId")) {
            accountOrder.setCustId(String.valueOf(fromId));
        } else if (feeDetail.getCustId().equals("toId")) {
            accountOrder.setCustId(String.valueOf(toId));
        } else {
            accountOrder.setCustId(feeDetail.getCustId());
        }
        accountOrder.setOrderDesc(feeDetail.getFeeDesc());
        accountOrder.setOrderId(String.valueOf(orderId));
        accountOrder.setProductDesc(productEnum.getDesc());
        accountOrder.setProductId(String.valueOf(productEnum.getType()));
        accountOrder.setProductType(productEnum.getType());
        accountOrder.setRemarks(remark);
        return accountOrder;
    }

    /**
     * 封装IntegralOrder，加减费相关信息。账户余额，可提现.
     *
     * @param orderId
     * @param orderId
     * @param cost
     * @return
     */
    private IntegralOrder getIntegralOrder(Long orderId, Long fromId, Long toId, Long cost, FeeDetail feeDetail) {
        IntegralOrder integralOrder = new IntegralOrder();
        integralOrder.setCost(cost * feeDetail.getFee() / 100L);
        if (feeDetail.getCustId().equals("fromId")) {
            integralOrder.setCustId(String.valueOf(fromId));
        } else if (feeDetail.getCustId().equals("toId")) {
            integralOrder.setCustId(String.valueOf(toId));
        } else {
            integralOrder.setCustId(feeDetail.getCustId());
        }
        integralOrder.setOrderDesc(feeDetail.getFeeDesc());
        integralOrder.setOrderId(String.valueOf(orderId));
        integralOrder.setProductDesc(productEnum.getDesc());
        integralOrder.setProductId(String.valueOf(productEnum.getType()));
        integralOrder.setProductType(productEnum.getType());
        integralOrder.setRemarks(remark);
        return integralOrder;
    }

}
