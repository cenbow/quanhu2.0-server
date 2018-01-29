package com.yryz.quanhu.other.activity.constants;

public class ActivityVoteConstants {

    /** app内 */
    public static Integer IN_APP = 0;
    /** app外 */
    public static Integer OTHER_APP = 1;

    /** 固定投票数 */
    public static Integer FIXED_VOTE_TYPE = 11;
    /** 每天投票数 */
    public static Integer EVENT_VOTE_TYPE = 12;

    /** 未开始 */
    public static Integer ACTIVITY_STATUS_NOSTART = 11;
    /** 进行中 */
    public static Integer ACTIVITY_STATUS_PROCESSING = 12;
    /** 已结束 */
    public static Integer ACTIVITY_STATUS_OVER = 13;

    /** 免费投票 */
    public static Integer FREE_VOTE = 0;
    /** 投票卷投票 */
    public static Integer NO_FREE_VOTE = 1;


    public static String ACTIVITY_VOTE = "ACTIVITY_VOTE_";

    public static String ACTIVITY_CONFIG = "_ACTIVITY_CONFIG";

    public static String ACTIVITY_RECORD = "ACTIVITY_RECORD";

    public static String ACTIVITY_VOTE_NO = "ACTIVITY_VOTE_NO";

    public static String getKeyInfo(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer("activity:vote:info:");
        stringBuffer.append(activityId);
        return stringBuffer.toString();
    }

    public static String getKeyConfig(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityVoteConstants.ACTIVITY_VOTE);
        stringBuffer.append(activityId);
        stringBuffer.append(ActivityVoteConstants.ACTIVITY_CONFIG);
        return stringBuffer.toString();
    }

}
