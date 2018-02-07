package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityCountApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityCountDto;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminActivityCountTest {

    @Reference
    AdminActivityCountApi adminActivityCountApi;
    /**
     * 获取活动统计数量
     * @param   adminActivityCountDto
     * @return
     * */
    @Test
    public void activityCount(){
        AdminActivityCountDto adminActivityCountDto = new AdminActivityCountDto();
        adminActivityCountDto.setActivityInfoId(748727544046698496L);
        adminActivityCountDto.setCurrentPage(1);
        adminActivityCountDto.setPageSize(10);
        adminActivityCountDto.setKid(748727544046698496L);
        adminActivityCountDto.setPage("SIGNUP-ACTIVITY-DETAIL");
        System.out.println(JsonUtils.toFastJson(adminActivityCountApi.activityCount(adminActivityCountDto)));
    }

    /**
     * 获取活动统计数量总和
     * @param   adminActivityCountDto
     * @return
     * */
    @Test
    public void activityTotalCount(){
        AdminActivityCountDto adminActivityCountDto = new AdminActivityCountDto();
        adminActivityCountDto.setActivityInfoId(748727544046698496L);
        adminActivityCountDto.setCurrentPage(1);
        adminActivityCountDto.setPageSize(10);
        adminActivityCountDto.setKid(748727544046698496L);
        adminActivityCountDto.setPage("SIGNUP-ACTIVITY-DETAIL");
        System.out.println(JsonUtils.toFastJson(adminActivityCountApi.activityTotalCount(adminActivityCountDto)));

    }

}
