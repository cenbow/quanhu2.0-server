package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.dto.AuthRefreshDTO;
import com.yryz.quanhu.user.dto.AuthTokenDTO;
import com.yryz.quanhu.user.service.AuthApi;
import com.yryz.quanhu.user.utils.TokenUtils;
import com.yryz.quanhu.user.vo.AuthTokenVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthTest {
	@Reference
	AuthApi authApi;
	
	/**
	 * checked
	 * 
	 * @param custId
	 * @param token
	 */
	public void checkToken() {
		final String name = "4, checkToken";
		try {
			AuthTokenDTO tokenDTO = new AuthTokenDTO(0l, DevType.ANDROID, "vebff12m1762","");
			AuthRefreshDTO refreshDTO = new AuthRefreshDTO("uW77hO3yyWXSKuunF5esIL1nKAc8WbokgS0unW//QLwOMEblYctjEg==", false);
			refreshDTO.setAppId(tokenDTO.getAppId());
			refreshDTO.setUserId(tokenDTO.getUserId());
			refreshDTO.setType(tokenDTO.getType());
			refreshDTO.setToken(tokenDTO.getToken());
			Response<Integer> result = authApi.checkToken(tokenDTO);
			System.out.println(JsonUtils.toFastJson(result));
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void refreshToken(){
		final String name = "2, refreshToken";
		try {
			Long userId = 724007310011252736l;
			String token = "724007310011252736-sMrAD4AS3zGU1517640982202";
			String refreshToken = "724007310011252736-t8zVeGkTRS1a1517640758070";
			AuthTokenDTO tokenDTO = new AuthTokenDTO(userId, DevType.ANDROID, "vebff12m1762",token);
			AuthRefreshDTO refreshDTO = new AuthRefreshDTO(refreshToken, true);
			refreshDTO.setAppId(tokenDTO.getAppId());
			refreshDTO.setUserId(tokenDTO.getUserId());
			refreshDTO.setType(tokenDTO.getType());
			refreshDTO.setToken(tokenDTO.getToken());
			Response<AuthTokenVO> result = authApi.refreshToken(refreshDTO);
			System.out.println(JsonUtils.toFastJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void getToken(){
		final String name = "1, getToken";
		try {
			AuthTokenDTO tokenDTO = new AuthTokenDTO(724007310011252736l, DevType.ANDROID, "vebff12m1762");
			tokenDTO.setRefreshLogin(true);
			AuthRefreshDTO refreshDTO = new AuthRefreshDTO();
			refreshDTO.setAppId(tokenDTO.getAppId());
			refreshDTO.setUserId(tokenDTO.getUserId());
			refreshDTO.setType(tokenDTO.getType());
			refreshDTO.setRefreshLogin(tokenDTO.isRefreshLogin());
			Response<AuthTokenVO> result = authApi.getToken(refreshDTO);
			System.out.println(JsonUtils.toFastJson(result));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
