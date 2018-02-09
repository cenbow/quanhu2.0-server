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
    * 查询类型：我打赏的-人-列表，我打赏的-资源-列表，打赏我的-人-列表，打赏我的-资源-列表，某资源被打赏-用户-列表
    * @author wangheng
    * @date 2018年1月27日 下午4:00:17  
    *    
    */
    public static interface QueryType {
        public static final Byte my_reward_user_list = 11;

        public static final Byte my_reward_resource_list = 12;

        public static final Byte reward_my_user_list = 13;

        public static final Byte reward_my_resource_list = 14;

        public static final Byte reward_resource_user_list = 15;
    }

    /**  
    * @Fields : 打赏统计目标类型（10：用户）
    */
    public static final Byte target_type_user = 10;
    /**  
    * @Fields : 打赏统计目标类型（11：资源）
    */
    public static final Byte target_type_resource = 11;
    

    /**  
    * @Description: 列表排序类型
    * @author wangheng
    * @date 2018年1月22日 下午5:09:03  
    *    
    */
    public static final class OrderType {
        /**  
        * @Fields newest : 时间最新
        */
        public static final byte time_new = 1;

        /**  
        * @Fields first : 时间最早
        */
        public static final byte time_old = 2;
    }
}
