package com.yryz.quanhu.message.message.constants;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 *
 * @Description:
 * @Date: Created in 2018 2018/1/20 17:58
 * @Author: pn
 */
public class MessageContants {

    /**
     * 通知公告
     */
    public static final int NOTICE_TYPE = 1;

    /**
     * 系统消息
     */
    public static final int SYSTEM_TYPE = 2;

    /**
     * 账户与安全
     */
    public static final int ORDER_TYPE = 3;

    /**
     * 互动消息
     */
    public static final int INTERACTIVE_TYPE = 4;

    /**
     * 推荐与活动
     */
    public static final int RECOMMEND_TYPE = 5;




    /**
     * 推送方式，1：立即推送，
     */
    public static final Integer PUSH_TYPE_START = 1;

    /**
     * 2：定时推送（关联推送时间pushDate）
     */
    public static final Integer PUSH_TYPE_TIMING = 2;




    /**
     * 持久化类型：0：不在消息列表显示
     */
    public static final Integer NOT_PERSISTENT = 0;

    /**
     * 1：代表在APP消息列表显示
     */
    public static final Integer PERSISTENT = 1;





    /**
     * 消息推送状态分为三种：0:未推送，表示还没到推送时间的消息；
     */
    public static final Integer PUSH_STATUS_NOT = 0;

    /**
     * 1:进行中，表示正在进行推送的消息；
     */
    public static final Integer PUSH_STATUS_ING = 1;

    /**
     * 2:已推送，表示已经推送过的消息；
     */
    public static final Integer PUSH_STATUS_END = 2;





    /**
     * 删除标记 0：已删除，
     */
    public static final Integer DEL_FLAG_DELETE = 0;

    /**
     * 1：正常
     */
    public static final Integer DEL_FLAG_NOT_DELETE = 1;




    /**
     * 消息来源，1：手动选择
     */
    public static final Integer MESSAGE_SOURCE_WRITE = 1;

    /**
     * 2：从已有内容中选择
     */
    public static final Integer MESSAGE_SOURCE_READ = 2;

}
