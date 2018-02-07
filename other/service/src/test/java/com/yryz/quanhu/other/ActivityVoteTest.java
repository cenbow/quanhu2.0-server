package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.ActivityInfoApi;
import com.yryz.quanhu.other.activity.api.ActivityVoteApi;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.other.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteInfoVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivityVoteTest {

    @Reference
    ActivityVoteApi activityVoteApi;
    /**
     * 获取投票活动详情
     * @param   kid
     * @param   userId
     * @return
     * */
    @Test
    public void detail(){
        System.out.println(JsonUtils.toFastJson(activityVoteApi.detail(1L,730940641361125376L)));
    }

  /**
     * 确认投票
     * @param   record
     * @return
     * */
    @Test
    public void single(){
        ActivityVoteRecord record = new ActivityVoteRecord();
        record.setActivityInfoId(750503890980716544L);
        record.setCandidateId(750509921114800128L);
        record.setOtherFlag(0);
        record.setVoteNo(2);
        record.setCreateUserId(750509199560294400L);
        System.out.println(JsonUtils.toFastJson(activityVoteApi.single(record)));
    }

    /**
     * 奖品列表
     * @param   activityVoteDto
     * @return
     * */
    @Test
    public void prizeslist(){
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setActivityInfoId(750503890980716544L);
        System.out.println(JsonUtils.toFastJson(activityVoteApi.prizeslist(activityVoteDto)));
    }
    /**
     * 领取奖品
     * @param   activityInfoId
     * @param   phone
     * @param   userId
     * @return
     * */
    @Test
    public void getPrize(){
        Long activityInfoId = 750503890980716544L;
        String phone = "13136530495";
        Long userId = 750509199560294400L;
        System.out.println(JsonUtils.toFastJson(activityVoteApi.getPrize(activityInfoId,phone,userId)));
    }

    /**
     * 无奖品文案
     * @param   activityInfoId
     * @return
     * */
    @Test
    public void noPrize(){
        Long activityInfoId = 750503890980716544L;
        System.out.println(JsonUtils.toFastJson(activityVoteApi.noPrize(activityInfoId)));
    }

    /**
     * 我的卡劵
     * @param   activityVoteDto
     * @return
     * */
    @Test
    public void userPrizesList(){
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setIsOverdue(10);
        activityVoteDto.setType(11);
        activityVoteDto.setCreateUserId(750509199560294400L);
        System.out.println(JsonUtils.toFastJson(activityVoteApi.userPrizesList(activityVoteDto)));
    }
}
