package com.yryz.quanhu.other.activity.service;


import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.entity.ActivityRecord;
import com.yryz.quanhu.other.activity.vo.ActivitySignUpHomeAppVo;

public interface ActivitySignUpService {

    ActivitySignUpHomeAppVo getActivitySignUpHome(Long activityInfoId, String custId);
    
    Integer getEnrolStatusByCustId(String custId, Long activityKid, Integer SignUpType);

    ActivityEnrolConfig getActivitySignUpFrom(Long activityInfoId, String custId);

    ActivityRecord activitySignUpSubmit(ActivityRecord activityRecord, String custId);
}
