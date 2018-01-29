package com.yryz.quanhu.behavior.reward.constants;

/**
* @Description: 打赏
* @author wangheng
* @date 2018年1月27日 上午11:57:46
*/
public final class RewardConstants {

    /**  
    * @Fields : 打赏状态：未支付
    */
    public static final Byte reward_status_pay_not = 10;

    /**  
    * @Fields : 打赏状态：支付成功
    */
    public static final Byte reward_status_pay_success = 11;

    /**  
    * @Description: 打赏查询类型
    * 查询类型：我打赏的-人-列表，我打赏的-资源-列表，打赏我的-人-列表，打赏我的-资源-列表
    * @author wangheng
    * @date 2018年1月27日 下午4:00:17  
    *    
    */
    public static interface QueryType {
        public static final Byte my_reward_user_list = 11;

        public static final Byte my_reward_resource_list = 12;

        public static final Byte reward_my_user_list = 13;

        public static final Byte reward_my_resource_list = 14;
    }

}
