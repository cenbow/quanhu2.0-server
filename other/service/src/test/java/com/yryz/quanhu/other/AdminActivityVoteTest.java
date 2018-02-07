package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityVoteApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import org.json.JSONArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminActivityVoteTest {
	@Reference
	AdminActivityVoteApi adminActivityVoteApi;
	/**
	 * 活动列表
	 */
	@Test
	public void adminlist(){
		AdminActivityInfoVoteDto param = new AdminActivityInfoVoteDto();
		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.adminlist(param)));
	}
	@Test
	public void activityRelease(){
		String str ="{\"createUserId\":1,\"remark\":\"水电费\",\"title\":\"算得上是地方\",\"content\":\" 是大大大大的多多多多多多多多多多多多多多多多\",\"contentSources\":\"[{\\\"text\\\":\\\"是大大大大的多多多多多多多多多多多多多多多多\\\"}]\",\"onlineTime\":1517982907000,\"beginTime\":1517982907000,\"lastUpdateUserId\":1,\"introduceSources\":\"[{\\\"text\\\":\\\"d大大大大大的多多多多多多多多多多多多多多多多\\\"}]\",\"endTime\":1519278900000,\"activityType\":12,\"coverPlan\":\"https://cdn-qa.yryz.com/pic/hwq/c2f508bba2aa53d910e0a82194fa48e1.png\"}";
		String str1 ="{\"userFlag\":11,\"userNum\":100,\"amount\":0,\"createUserId\":1,\"inAppVoteConfigCount\":3,\"lastUpdateUserId\":1,\"noRewardContent\":\"水电费\",\"otherAppVoteType\":1,\"configSources\":\"{\\\"imgUrl\\\":{\\\"errorPrompt\\\":\\\"水电费 \\\",\\\"enable\\\":\\\"1\\\",\\\"upperLimit\\\":\\\"20\\\",\\\"lowerLimit\\\":\\\"1\\\",\\\"required\\\":\\\"1\\\"},\\\"content2\\\":{\\\"errorPrompt\\\":\\\"收到\\\",\\\"enable\\\":\\\"1\\\",\\\"inputPrompt\\\":\\\"收到发\\\",\\\"upperLimit\\\":\\\"500\\\",\\\"lowerLimit\\\":\\\"1\\\",\\\"required\\\":\\\"1\\\"},\\\"content1\\\":{\\\"errorPrompt\\\":\\\"收到\\\",\\\"enable\\\":\\\"1\\\",\\\"inputPrompt\\\":\\\"水电费\\\",\\\"upperLimit\\\":\\\"500\\\",\\\"lowerLimit\\\":\\\"1\\\",\\\"required\\\":\\\"1\\\"},\\\"coverPlan\\\":{\\\"enable\\\":\\\"1\\\",\\\"required\\\":\\\"1\\\"},\\\"content\\\":{\\\"errorPrompt\\\":\\\"收到\\\",\\\"enable\\\":\\\"1\\\",\\\"inputPrompt\\\":\\\"水电费\\\",\\\"upperLimit\\\":\\\"500\\\",\\\"lowerLimit\\\":\\\"1\\\",\\\"required\\\":\\\"1\\\"}}\",\"activityVoteBegin\":1518760556000,\"commentFlag\":11,\"activityJoinBegin\":1518069350000,\"activityJoinEnd\":1518155750000,\"activityVoteEnd\":1519106160000,\"otherAppVoteConfigCount\":3,\"prizesFlag\":11,\"inAppVoteType\":1}";
		String str2 = "[{\"issueNumConfig\":0,\"prizesUnit\":\"\",\"createUserId\":1,\"lastUpdateDate\":null,\"lastUpdateUserId\":1,\"kid\":0,\"issueNum\":100,\"remark\":\"可在投票活动中给喜欢的内容投票。\",\"sort\":0,\"prizesName\":\"投票券\",\"activityInfoId\":0,\"canNum\":5,\"beginTime\":{\"date\":7,\"hours\":0,\"seconds\":0,\"month\":1,\"timezoneOffset\":-480,\"year\":118,\"minutes\":0,\"time\":1517932800000,\"day\":3},\"endTime\":{\"date\":21,\"hours\":0,\"seconds\":0,\"month\":1,\"timezoneOffset\":-480,\"year\":118,\"minutes\":0,\"time\":1519142400000,\"day\":3},\"id\":0,\"prizesNum\":0,\"createDate\":null,\"prizesType\":11},{\"issueNumConfig\":0,\"prizesUnit\":\"傻\",\"createUserId\":1,\"lastUpdateDate\":null,\"lastUpdateUserId\":1,\"kid\":0,\"issueNum\":100,\"remark\":\"斯蒂芬斯蒂芬斯蒂芬\",\"sort\":0,\"prizesName\":\"傻吊\",\"activityInfoId\":0,\"canNum\":0,\"beginTime\":{\"date\":14,\"hours\":0,\"seconds\":0,\"month\":1,\"timezoneOffset\":-480,\"year\":118,\"minutes\":0,\"time\":1518537600000,\"day\":3},\"endTime\":{\"date\":28,\"hours\":0,\"seconds\":0,\"month\":1,\"timezoneOffset\":-480,\"year\":118,\"minutes\":0,\"time\":1519747200000,\"day\":3},\"id\":0,\"prizesNum\":100,\"createDate\":null,\"prizesType\":12}]";
		ActivityInfo activity = JSON.parseObject(str, new TypeReference<ActivityInfo>() {});
		ActivityVoteConfig config = JSON.parseObject(str1, new TypeReference<ActivityVoteConfig>() {});
		List<ActivityPrizes> prizes = null;

		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.activityRelease(activity,config,prizes)));
	}

	@Test
	public void getActivityDetail(){
		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.getActivityDetail(1L)));

	}

	@Test
	public void getConfigDetailByActivityId(){
		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.getConfigDetailByActivityId("738434019265847296")));
	}

	@Test
	public void getPrizesListByActivityId(){
		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.getPrizesListByActivityId("738434019265847296")));
	}

	@Test
	public void selectRankList(){
		AdminActivityVoteDetailDto adminActivityVoteDetailDto = new AdminActivityVoteDetailDto();
		adminActivityVoteDetailDto.setActivityInfoId(738434019265847296L);
		System.out.println(JsonUtils.toFastJson(adminActivityVoteApi.selectRankList(adminActivityVoteDetailDto)));
	}
}
