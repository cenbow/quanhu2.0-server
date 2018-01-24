package com.yryz.quanhu.support.activity.constants;

public class ActivityVoteConstants {

    /** 未开始 */
    public static Integer ACTIVITY_STATUS_NOSTART = 11;
    /** 进行中 */
    public static Integer ACTIVITY_STATUS_PROCESSING = 12;
    /** 已结束 */
    public static Integer ACTIVITY_STATUS_OVER = 13;

    public static String ACTIVITY_VOTE = "ACTIVITY_VOTE_";

    public static String ACTIVITY_CONFIG = "_ACTIVITY_CONFIG";

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
