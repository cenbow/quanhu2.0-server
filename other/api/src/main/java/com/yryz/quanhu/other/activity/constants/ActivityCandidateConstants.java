package com.yryz.quanhu.other.activity.constants;

public class ActivityCandidateConstants {

    public static Integer CURRENTPAGE = 1;

    public static Integer PAGESIZE = 1000;

    public static String ACTIVITY_CANDIDATE = "activity:candidate:";

    public static String ACTIVITY_RANK = "rank:";

    public static String ACTIVITY_ID = "id:";

    public static String getKeyInfo(Long activityId, Long candidateId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityCandidateConstants.ACTIVITY_CANDIDATE);
        stringBuffer.append(activityId);
        stringBuffer.append(":");
        stringBuffer.append(candidateId);
        return stringBuffer.toString();
    }

    public static String getKeyRank(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityCandidateConstants.ACTIVITY_CANDIDATE);
        stringBuffer.append(ActivityCandidateConstants.ACTIVITY_RANK);
        stringBuffer.append(activityId);
        return stringBuffer.toString();
    }

    public static String getKeyId(Long activityId) {
        StringBuffer stringBuffer = new StringBuffer(ActivityCandidateConstants.ACTIVITY_CANDIDATE);
        stringBuffer.append(ActivityCandidateConstants.ACTIVITY_ID);
        stringBuffer.append(activityId);
        return stringBuffer.toString();
    }

}
