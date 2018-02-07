package com.yryz.quanhu.other;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminIActivityParticipationApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteRecordDto;
import com.yryz.quanhu.other.activity.dto.AdminConfigObjectDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * 投票类活动参与内容管理
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminIActivityParticipationTest {

	@Reference
	AdminIActivityParticipationApi adminIActivityParticipationApi;

	@Test
	public void detail(){
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.detail(738434019265847296L)));
	}
    /**
	 * 参与活动列表
	 */
	@Test
	public void list(){
		AdminActivityVoteDetailDto adminActivityVoteDetailDto = new AdminActivityVoteDetailDto();
		adminActivityVoteDetailDto.setActivityInfoId(738434019265847296L);
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.list(adminActivityVoteDetailDto)));
	}
	/**
	 * 增加票数
	 */
	@Test
	public void addVote(){
		Long id=731426470882746368L;
		Integer count=100;
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.addVote(id,count)));
	}

	@Test
	public void updateStatus(){
		Long id=731426470882746368L;
		Byte status=10;
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.updateStatus(id,status)));
	}

	@Test
	public void getVoteConfig(){
		Long infoId = 738434019265847296L;
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.getVoteConfig(infoId)));
	}

	@Test
	public void saveVoteDetail(){
		String string = "{\"activityInfoId\":1,\"content\":\"啥啥啥啥啥啥所所所所所所所所所所所所所所所所所所所所所所所所\",\"content1\":\"啥啥啥啥啥啥所所所所所所所\",\"content2\":\"刷刷刷啥啥啥啥啥啥所所所所所所所所所所所所\",\"coverPlan\":\"https://cdn-qa.yryz.com/pic/hwq/4963878ef73216419c6b50c8db9a4b59.jpg\",\"createUserId\":730940641361125376,\"imgUrl\":\"https://cdn-qa.yryz.com/pic/hwq/c2f508bba2aa53d910e0a82194fa48e1.png;https://cdn-qa.yryz.com/pic/hwq/e80e276505e7502c4478b9afaad4aabf.png;https://cdn-qa.yryz.com/pic/hwq/e80e276505e7502c4478b9afaad4aabf.png\",\"pageNo\":1,\"pageSize\":10}";
		AdminActivityVoteDetailDto voteDetailDto = JSON.parseObject(string, new TypeReference<AdminActivityVoteDetailDto>() {});
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.saveVoteDetail(voteDetailDto)));
	}
	/**
	 * 投票用户数据
	 */
	@Test
	public void adminlist(){
		AdminActivityVoteRecordDto adminActivityVoteRecordDto =new AdminActivityVoteRecordDto();
		adminActivityVoteRecordDto.setActivityInfoId(731426470882746368L);
		System.out.println(JsonUtils.toFastJson(adminIActivityParticipationApi.adminlist(adminActivityVoteRecordDto)));


	}
}
