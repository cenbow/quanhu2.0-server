package com.yryz.quanhu.support.activity.constants;

public class ActivityCandidateConstants {

    public static Integer CURRENTPAGE = 1;

    public static Integer PAGESIZE = 1000;

    public static String ACTIVITY_CANDIDATE = "ACTIVITY_CANDIDATE_";

    public static String ACTIVITY_RANK = "_ACTIVITY_RANK";

    public static String ACTIVITY_ID = "_ACTIVITY_ID";

    public static String getKeyInfo(Long activityId, Long candidateId) {
        StringBuffer stringBuffer = new StringBuffer("activity:candidate:");
        stringBuffer.append(activityId);
        stringBuffer.append(":");
        stringBuffer.append(candidateId);
        return stringBuffer.toString();
    }

    public static String getKeyRank(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityCandidateConstants.ACTIVITY_CANDIDATE);
        stringBuffer.append(activityId);
        stringBuffer.append(ActivityCandidateConstants.ACTIVITY_RANK);
        return stringBuffer.toString();
    }

    public static String getKeyId(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityCandidateConstants.ACTIVITY_CANDIDATE);
        stringBuffer.append(activityId);
        stringBuffer.append(ActivityCandidateConstants.ACTIVITY_ID);
        return stringBuffer.toString();
    }

}
