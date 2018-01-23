package com.yryz.quanhu.user.contants;

import org.springframework.security.access.method.P;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/22 15:07
 * Created by huangxy
 */
public final class UserRelationConstant {


    public static final int YES = 11;
    public static final int NO = 10;


    public static enum TYPE {

        PLATFORM(1,"平台"),                               //平台
        COTERIE(2,"私圈"),                                //私圈
        IM(3,"云信IM")                                    //云信IM
        ;

        private int code;
        private String desc;

        TYPE(int code,String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static TYPE get(int code){
            if(1 == code){
                return PLATFORM;
            }
            if(2 == code){
                return COTERIE;
            }
            if(3 == code){
                return IM;
            }
            return null;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }

    /**
     * 关系状态
     */
    public static enum STATUS{
        FANS        (1),                              //粉丝
        FOLLOW      (2),                              //关注
        TO_BLACK    (3),                              //拉黑
        FROM_BLACK  (4),                              //被拉黑
        FRIEND      (5)                               //好友
        ;

        private int code;

        STATUS(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public static STATUS get(int code){
            if(1 == code){
                return FANS;
            }
            if(2 == code){
                return FOLLOW;
            }
            if(3 == code){
                return TO_BLACK;
            }
            if(4 == code){
                return FROM_BLACK;
            }
            if(5 == code){
                return FRIEND;
            }
            return null;
        }
    }
    /**
     * 操作事件
     */
    public static enum EVENT{
        SET_FOLLOW,                             //关注
        CANCEL_FOLLOW,                          //取消关注
        SET_BLACK,                              //拉黑
        CANCEL_BLACK,                           //取消拉黑
    }


}
