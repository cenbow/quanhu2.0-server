package com.yryz.quanhu.dymaic.canal.constant;

/**
 * 
 * @author jk
 */
public class CommonConstant {
    public final static int BATCH_SIZE = 2*1024;
    
    //TODO 值需要根据表情况更改
    public class UserDb{
    	public final static String DB_NAME="db1";
        public final static String TABLE_USER="qh_user_baseinfo";
    }
    
    public class EventType{
    	public final static String OPT_UPDATE="update";
        public final static String OPT_DELETE="delete";
        public final static String OPT_INSERT="insert";
    }
    
}
