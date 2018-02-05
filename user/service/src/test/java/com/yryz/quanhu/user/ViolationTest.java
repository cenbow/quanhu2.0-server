package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.ViolatType;
import com.yryz.quanhu.user.service.ViolationApi;
import com.yryz.quanhu.user.vo.ViolationInfo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ViolationTest {
	@Reference
	ViolationApi api;
	
	//@Test
	public void save(){
		ViolationInfo info = new ViolationInfo();
		info.setAppId("vebff12m1762");
		info.setUserId("747511020329910272");
		info.setViolationType((byte)ViolatType.WARN.getType());
		Response<Boolean> response = api.addViolation(info);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	@Test
	public void update(){
		ViolationInfo info = new ViolationInfo();
		info.setAppId("vebff12m1762");
		info.setUserId("747511020329910272");
		info.setViolationType((byte)ViolatType.ALLTAIK.getType());
		Response<Boolean> response = api.updateViolation(info);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
