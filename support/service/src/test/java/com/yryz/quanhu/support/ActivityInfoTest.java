package com.yryz.quanhu.support;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.support.activity.api.ActivityInfoApi;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhaohaodong
 * @version 2.0
 * @date 2018年1月23日11:37:32
 * @description
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityInfoTest {
    @Reference
    ActivityInfoApi activityInfoApi;

    @Test
    public void myList() {
        Response<PageList<ActivityInfoAppListVo>> myList = activityInfoApi.myList(1,10,1L);
        System.out.println(JsonUtils.toFastJson(myList));
    }

    @Test
    public void myListCount() {
        Response<Integer> myListCount = activityInfoApi.myListCount(1L);
        System.out.println(JsonUtils.toFastJson(myListCount));
    }

    @Test
    public void appList() {
        Response<PageList<ActivityInfoAppListVo>> appList = activityInfoApi.appList(1,10,1);
        System.out.println(JsonUtils.toFastJson(appList));
    }

    @Test
    public void get() {
        Response<ActivityInfoVo> ActivityInfoVo = activityInfoApi.get(2L,3);
        System.out.println(JsonUtils.toFastJson(ActivityInfoVo));
    }
}
