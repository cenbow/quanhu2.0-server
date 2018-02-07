package com.yryz.quanhu.other;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.ActivityCandidateApi;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityCandidateTest {
    @Reference
    ActivityCandidateApi activityCandidateApi;
    /**
     * 增加参与者
     * @param activityVoteDto
     * @return
     * */
    @Test
    public void join(){
        String string = "{\"activityInfoId\":1,\"content\":\"啥啥啥啥啥啥所所所所所所所所所所所所所所所所所所所所所所所所\",\"content1\":\"啥啥啥啥啥啥所所所所所所所\",\"content2\":\"刷刷刷啥啥啥啥啥啥所所所所所所所所所所所所\",\"coverPlan\":\"https://cdn-qa.yryz.com/pic/hwq/4963878ef73216419c6b50c8db9a4b59.jpg\",\"createUserId\":730940641361125376,\"imgUrl\":\"https://cdn-qa.yryz.com/pic/hwq/c2f508bba2aa53d910e0a82194fa48e1.png;https://cdn-qa.yryz.com/pic/hwq/e80e276505e7502c4478b9afaad4aabf.png;https://cdn-qa.yryz.com/pic/hwq/e80e276505e7502c4478b9afaad4aabf.png\",\"pageNo\":1,\"pageSize\":10}";
        ActivityVoteDto activityVoteDto= JSON.parseObject(string, new TypeReference<ActivityVoteDto>() {});
        System.out.println(JsonUtils.toFastJson(activityCandidateApi.join(activityVoteDto)));
    }

    /**
     * 参与投票活动
     * @param   activityInfoId
     * @return
     * */
    @Test
    public void config(){
        System.out.println(JsonUtils.toFastJson(activityCandidateApi.config(1L)));
    }
    /**
     * 获取参与者详情
     * @param   activityVoteDto
     * @return
     * */
    @Test
    public void detail(){
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setActivityInfoId(738845872269795323L);
        activityVoteDto.setCandidateId(738948384549339136L);
        activityVoteDto.setOtherFlag(0);
        activityVoteDto.setCreateUserId(730940641361125376L);
        System.out.println(JsonUtils.toFastJson(activityCandidateApi.detail(activityVoteDto)));
    }

    /**
     * 参与者列表
     * @param   activityVoteDto
     * @return
     * */
    @Test
    public void list(){
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setActivityInfoId(738845872269795323L);
        activityVoteDto.setOtherFlag(0);
        activityVoteDto.setCreateUserId(730940641361125376L);
        System.out.println(JsonUtils.toFastJson(activityCandidateApi.list(activityVoteDto)));

    }
/**
     * 排行榜
     * @param   activityVoteDto
     * @return
     * */
    @Test
    public void rank(){
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setActivityInfoId(738845872269795323L);
        activityVoteDto.setOtherFlag(0);
        activityVoteDto.setCreateUserId(730940641361125376L);
        System.out.println(JsonUtils.toFastJson(activityCandidateApi.rank(activityVoteDto)));

    }
}
