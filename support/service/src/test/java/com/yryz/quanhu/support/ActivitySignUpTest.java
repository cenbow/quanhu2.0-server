package com.yryz.quanhu.support;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.support.activity.api.ActivitySignUpApi;
import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.support.activity.entity.ActivityRecord;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivitySignUpHomeAppVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * @author zhaohaodong
 * @version 2.0
 * @date 2018年1月23日11:37:32
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitySignUpTest {
    @Reference
    ActivitySignUpApi activitySignUpApi;

    @Test
    public void getActivitySignUpHome() {
        Response<ActivitySignUpHomeAppVo> ActivitySignUpHomeAppVo = activitySignUpApi.getActivitySignUpHome(1L,"1");
        System.out.println(JsonUtils.toFastJson(ActivitySignUpHomeAppVo));
    }
    @Test
    public void getActivitySignUpFrom() {
        Response<ActivityEnrolConfig>  ActivityEnrolConfig = activitySignUpApi.getActivitySignUpFrom(2L,"122");
        System.out.println(JsonUtils.toFastJson(ActivityEnrolConfig));
    }

    @Test
    public void getActivitySignUpStatus() {
        Response<Map<String,Integer>> map = activitySignUpApi.getActivitySignUpStatus(2L,"1");
        System.out.println(JsonUtils.toFastJson(map));
    }
    @Test
    public void activitySignUpSubmit() {
        ActivityRecord activityRecord = new ActivityRecord();
        activityRecord.setActivityInfoId(2L);
        activityRecord.setEnrolSources("测试测试");
        activityRecord.setPhone("13135630495");
        Response<ActivityRecord> aar = activitySignUpApi.activitySignUpSubmit(activityRecord,"222");
        System.out.println(JsonUtils.toFastJson(aar));
    }
}
