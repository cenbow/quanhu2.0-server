package com.yryz.quanhu.order.dao.redis;

/**
 * 资金/账户的redis管理-key管理
 * 
 * @author Administrator
 *
 */
public class RedisKeyEnum {
	
	/**
	 * 锁的默认过期时间
	 */
	public final static int LOCK_EXPIRE_DEFAULT = 60;
	
	/**
	 * 用户账户信息key ，数据结构：key-value(JSON)
	 * 
	 * @param custId
	 * @return
	 */
	public static String getRrzOrderUserAccount(String custId){
		return "RrzOrderUserAccount_" + custId;
	}
	
	/**
	 * 用户消费余额key ，数据结构：key-value(long)
	 * 
	 * @param custId
	 * @return
	 */
	public static String getUserAccountSum(String custId){
		return "UserAccountSum_" + custId;
	}
	
	/**
	 * 用户消费总额key ，数据结构：key-value(long)
	 * 
	 * @param custId
	 * @return
	 */
	public static String getUserCostSum(String custId){
		return "UserCostSum_" + custId;
	}
	
	/**
	 * 用户积分余额key ，数据结构：key-value(long)
	 * 
	 * @param custId
	 * @return
	 */
	public static String getUserIntegralSum(String custId){
		return "UserIntegralSum_" + custId;
	}
	
	/**
	 * 用户安全信息配置key ，数据结构：key-value(JSON)
	 * 
	 * @param custId
	 * @return
	 */
	public static String getRrzOrderUserPhy(String custId){
		return "RrzOrderUserPhy_" + custId;
	}
	
	/**
	 * 密码验证出错状态的key
	 * 
	 * @param custId
	 * @return
	 */
	public static String getBanCheck(String custId){
		return "BanCheck_" + custId;
	}
	
	/**
	 * 用户积分统计的报表
	 * 
	 * @return
	 */
	public static String integralSum(){
		return "INTEGRALSUM";
	}
	
	/**
	 * 预处理订单列表
	 * @param orderId
	 * @return
	 */
	public static String getOrderInfo(String orderId){
		return "ORDERINFO." + orderId;
	}

}
