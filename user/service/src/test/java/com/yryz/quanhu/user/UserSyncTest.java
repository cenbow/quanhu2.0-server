package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.service.UserSyncApi;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserSyncTest {
	@Reference
	private UserSyncApi syncApi;
	
	@Test
	public void sync(){
		Response<Boolean> response = syncApi.syncUser(2);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
