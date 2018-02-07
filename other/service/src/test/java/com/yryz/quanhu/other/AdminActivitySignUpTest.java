package com.yryz.quanhu.other;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivitySignUpApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoSignUpDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityInfoAndEnrolConfig;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityRecordVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminActivitySignUpTest {
	@Reference
	AdminActivitySignUpApi adminActivitySignUpApi;
	/**
	 * 后台发布报名类活动
	 * @param activityInfo
	 * @param activityEnrolConfig
	 * @param request
	 * @return
	 */
	@Test
	public void activityRelease(){
		ActivityInfoAndEnrolConfig activityInfoAndEnrolConfig = new ActivityInfoAndEnrolConfig();
		activityInfoAndEnrolConfig.setActivityType(11);
		activityInfoAndEnrolConfig.setBeginTime(new Date());
		activityInfoAndEnrolConfig.setConfigSources("[{\"required\":\"1\",\"type\":\"1\",\"title\":\"阿萨德\",\"inputPrompt\":\"阿萨德\",\"errorPrompt\":\"啊啊是\",\"id\":\"1\"}]");
		activityInfoAndEnrolConfig.setContent(" 是刷刷刷啥啥啥啥啥啥所所所所所所所所所所所所");
		activityInfoAndEnrolConfig.setContentSources("[{\"text\":\"是刷刷刷啥啥啥啥啥啥所所所所所所所所所所所所\"}]");
		activityInfoAndEnrolConfig.setCoverPlan("https://cdn-qa.yryz.com/pic/hwq/df4b5d9064c922c9f0094cfb9a75e426.png");
		activityInfoAndEnrolConfig.setCreateUserId(1L);
		activityInfoAndEnrolConfig.setEndTime(new Date());
		activityInfoAndEnrolConfig.setEnrolUpper(22);
		activityInfoAndEnrolConfig.setLastUpdateUserId(1L);
		activityInfoAndEnrolConfig.setOnlineTime(new Date());
		activityInfoAndEnrolConfig.setPhoneRequired(11);
		activityInfoAndEnrolConfig.setRemark("12121212");
		activityInfoAndEnrolConfig.setSignUpType(13);
		activityInfoAndEnrolConfig.setTitle("asdfasdasdas");
		System.out.println(adminActivitySignUpApi.activityRelease(activityInfoAndEnrolConfig));
	}
	/**
	 * (后台)报名活动列表
	 * @param pageNo
	 * @param pageSize
	 * @param adminActivityInfoSignUpDto
	 * @return
	 */
	@Test
	public void adminlist(){
		System.out.println(adminActivitySignUpApi.adminlist(1,10,null));
	}
	/**
	 * （后台）获取报名类活动详情
	 * @param id
	 * @return
	 */
	@Test
	public void getActivitySignUpDetail(){
		System.out.println(JsonUtils.toFastJson(adminActivitySignUpApi.getActivitySignUpDetail("2")));
	}
	/**
	 * 根据活动ID获取活动主表详情
	 * @param id
	 * @return
	 */
	@Test
	public void getActivityDetail(){
		System.out.println(JsonUtils.toFastJson(adminActivitySignUpApi.getActivityDetail("2")));
	}
	/**
	 * (后台)报名人员列表
	 * */
	@Test
	public void attendlist(){
		AdminActivityRecordVo adminActivityRecordVo = new AdminActivityRecordVo();
		adminActivityRecordVo.setActivityInfoId(2L);
		System.out.println(JsonUtils.toFastJson(adminActivitySignUpApi.attendlist(1,10,adminActivityRecordVo)));
	}
	/**
	 * (后台)所有上线的活动列表
	 * */
	@Test
	public void adminAllSharelist(){
		AdminActivityInfoDto adminActivityInfoDto = new AdminActivityInfoDto();
		System.out.println(JsonUtils.toFastJson(adminActivitySignUpApi.adminAllSharelist(1,10,adminActivityInfoDto)));
	}
	/**
	 *  后台编辑活动
	 * @param activityInfoAndEnrolConfig
	 * @param request
	 * @return
	 */
	@Test
	public void activityInfoEdit(){
		ActivityInfo activityInfo = new ActivityInfo();
		activityInfo.setRemark("ewewewe");
		activityInfo.setKid(2L);
		System.out.println(adminActivitySignUpApi.activityInfoEdit(activityInfo));
	}
	
	/**
	 * (后台)所有上线的活动列表+不分页
	 * @return
	 */
	@Test
	public void adminAllSharelistNoPage(){
		System.out.println(JsonUtils.toFastJson(adminActivitySignUpApi.adminAllSharelistNoPage()));
	}
	
	

}
