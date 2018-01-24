package com.yryz.quanhu.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.vo.StarInfoVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserStarTest {
	@Reference
	UserStarApi starApi;
	
	//@Test
	public void save(){
		StarAuthInfo info = new StarAuthInfo();
		info.setAuthWay((byte)10);
		info.setAuthType((byte)10);
		info.setAppId("vebff12m1762");
		info.setUserId("724011759597371392");
		info.setRealName("呵呵");
		info.setIdCard("421281198902045756");
		info.setLocation("位置");
		info.setTradeField("it");
		info.setResourceDesc("哒呵呵哒呵呵哒呵呵哒");
		info.setContactCall("15088052677");
		Response<Boolean> response = starApi.save(info);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	@Test
	public void starRecommonedList(){
		StarAuthParamDTO paramDTO = new StarAuthParamDTO();
		paramDTO.setStart(0);
		paramDTO.setLimit(10);
		paramDTO.setUserId(null);
		Response<List<StarInfoVO>> response = starApi.starList(paramDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
