package com.yryz.quanhu.order.dao.redis;

/**
 * 资金/账户的redis管理-key管理
 *
 * @author Administrator
 */
public class RedisKeyEnum {

    /**
     * 锁的默认过期时间
     */
    public final static int LOCK_EXPIRE_DEFAULT = 60;

    /**
     * 订单模块redis缓存前缀
     */
    private static final String ORDER_PREFIX = "quanhu-order:";

    /**
     * 用户账户信息key ，数据结构：key-value(JSON)
     *
     * @param custId
     * @return
     */
    public static String getRrzOrderUserAccount(String custId) {
        return ORDER_PREFIX + "RrzOrderUserAccount:" + custId;
    }

    /**
     * 用户消费余额key ，数据结构：key-value(long)
     *
     * @param custId
     * @return
     */
    public static String getUserAccountSum(String custId) {
        return ORDER_PREFIX + "UserAccountSum:" + custId;
    }

    /**
     * 用户消费总额key ，数据结构：key-value(long)
     *
     * @param custId
     * @return
     */
    public static String getUserCostSum(String custId) {
        return ORDER_PREFIX + "UserCostSum:" + custId;
    }

    /**
     * 用户积分余额key ，数据结构：key-value(long)
     *
     * @param custId
     * @return
     */
    public static String getUserIntegralSum(String custId) {
        return ORDER_PREFIX + "UserIntegralSum:" + custId;
    }

    /**
     * 用户安全信息配置key ，数据结构：key-value(JSON)
     *
     * @param custId
     * @return
     */
    public static String getRrzOrderUserPhy(String custId) {
        return ORDER_PREFIX + "RrzOrderUserPhy:" + custId;
    }

    /**
     * 密码验证出错状态的key
     *
     * @param custId
     * @return
     */
    public static String getBanCheck(String custId) {
        return ORDER_PREFIX + "BanCheck:" + custId;
    }

    /**
     * 用户积分统计的报表
     *
     * @return
     */
    public static String integralSum() {
        return ORDER_PREFIX + "INTEGRALSUM";
    }

    /**
     * 预处理订单列表
     *
     * @param orderId
     * @return
     */
    public static String getOrderInfo(String orderId) {
        return ORDER_PREFIX + "ORDERINFO:" + orderId;
    }

}
