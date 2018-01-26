package com.yryz.quanhu.dymaic.canal.constant;

/**
 * 
 * @author jk
 */
public class CommonConstant {
    public final static int BATCH_SIZE = 2*1024;
    
    public class QuanHuDb{
    	public final static String DB_NAME="db1";
        public final static String TABLE_USER="qh_user_baseinfo";
        public final static String TABLE_TOPIC="qh_topic";
        public final static String TABLE_TOPIC_POST="qh_topic_post";
        public final static String TABLE_RELEASE_INFO="qh_release_info";
        public final static String TABLE_COTERIE="qh_coterie";
        /**
         * MongoDB 热度数据变更直接发送的mq消息
         */
        public final static String TABLE_MONGODB_RESOURCE_HEAT="mongodb_resource_heat";
    }
    
    
    public class EventType{
    	public final static String OPT_UPDATE="update";
        public final static String OPT_DELETE="delete";
        public final static String OPT_INSERT="insert";
    }
    
}
