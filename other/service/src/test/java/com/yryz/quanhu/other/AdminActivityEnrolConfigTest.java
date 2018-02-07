package com.yryz.quanhu.other;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityEnrolConfigApi;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminActivityEnrolConfigTest {

	@Reference
	AdminActivityEnrolConfigApi adminActivityEnrolConfigAPI;
	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	@Test
	public void getActivityEnrolConfigByActId(){
		System.out.println(JsonUtils.toFastJson(adminActivityEnrolConfigAPI.getActivityEnrolConfigByActId(2L)));
	}
}
