package com.yryz.quanhu.dymaic.canal.constant;

/**
 * @author jk
 */
public class CommonConstant {
    public final static int BATCH_SIZE = 5 * 1024;

    public class QuanHuDb {
        public final static String DB_NAME = "quanhu";
        public final static String TABLE_USER = "qh_user_baseinfo";
        public final static String TABLE_TOPIC = "qh_topic";
        public final static String TABLE_TOPIC_POST = "qh_topic_post";
        public final static String TABLE_RELEASE_INFO = "qh_release_info";
        public final static String TABLE_COTERIE = "qh_coterie";
        //用户标签
        public final static String TABLE_USER_TAG = "qh_user_tag";
        //达人信息
        public final static String TABLE_USER_STAR_AUTH = "qh_user_star_auth";

        //注册记录日志表
        public final static String TABLE_USER_REG_LOG = "qh_user_reg_log";




        /**
         * MongoDB 热度数据变更直接发送的mq消息
         */
        public final static String TABLE_MONGODB_RESOURCE_HEAT = "mongodb_resource_heat";
    }

    /**
     * 用户积分数据
     */
    public static final String QUANHU_ACCOUNT = "quanhu_account";
    /**
     * 用户事件账户表
     */
    public static final String EVENT_ACOUNT = "event_acount";


    public class EventType {
        public final static String OPT_UPDATE = "update";
        public final static String OPT_DELETE = "delete";
        public final static String OPT_INSERT = "insert";
    }

}
