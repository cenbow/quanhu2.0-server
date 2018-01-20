package com.yryz.quanhu.user;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.contants.SmsContants;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.RegisterLoginVO;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
	// @Autowired
	// private DemoService demoService;
	//
	// @Autowired
	// private ContentAuditService contentAuditService;
	@Reference
	private AccountApi accountApi;

	//
	// @Test
	// public void exampleTest(){
	// List<ContentAudit> result = contentAuditService.findByTypeAndInfoId(new
	// HashMap<>());
	//
	// DemoVo demoVo = demoService.find(1L);
	// System.out.printf(demoVo.toString());
	// }
	// }
	// @Test
	public void register() {
		RegisterDTO registerDTO = new RegisterDTO();
		RequestHeader header = new RequestHeader();
		header.setAppId("vebff12m1762");
		header.setAppVersion("2.0");
		header.setDevId("24456241457878");
		header.setDevName("HUAWEI");
		header.setDevType("1");
		header.setDitchCode("APP");
		registerDTO.setActivityChannelCode("quanhu-new");
		registerDTO.setCityCode("431000");
		registerDTO.setDeviceId("24456241457878");
		registerDTO.setUserLocation("湖北 武汉");
		registerDTO.setUserPhone("16612345679");
		registerDTO.setUserPwd("");
		registerDTO.setVeriCode("6700");
		registerDTO.setUserRegInviterCode("48565247");
		UserRegLogDTO logDTO = new UserRegLogDTO(null, header.getDitchCode(), header.getAppVersion(),
				RegType.PHONE.getText(), DevType.ANDROID.getLabel(), header.getDevName(), header.getAppId(),
				"127.0.0.1", registerDTO.getUserLocation(), registerDTO.getActivityChannelCode(), null);
		registerDTO.setRegLogDTO(logDTO);
		Response<RegisterLoginVO> loginVO = accountApi.register(registerDTO, header);

		System.out.println(JsonUtils.toFastJson(loginVO));
	}

	// @Test
	public void getLoginMethod() {
		Response<List<LoginMethodVO>> response = accountApi.getLoginMethod("724007310011252736");
		System.out.println(JsonUtils.toFastJson(response));
	}

	//@Test
	public void forgotPassword() {
		Response<Boolean> response = accountApi
				.forgotPassword(new ForgotPasswordDTO("16612345679", "vebff12m1762", "6884", "a12345"));
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void loginByVerifyCode(){
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setPhone("16612345679");
		//loginDTO.setPassword("a12345");
		loginDTO.setVerifyCode("");
		RequestHeader header = new RequestHeader();
		header.setAppId("vebff12m1762");
		header.setAppVersion("2.0");
		header.setDevId("24456241457878");
		header.setDevName("HUAWEI");
		header.setDevType("1");
		header.setDitchCode("APP");
		Response<RegisterLoginVO> response = accountApi.loginByVerifyCode(loginDTO, header);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void login() {
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setPhone("16612345679");
		loginDTO.setPassword("a12345");
		RequestHeader header = new RequestHeader();
		header.setAppId("vebff12m1762");
		header.setAppVersion("2.0");
		header.setDevId("24456241457878");
		header.setDevName("HUAWEI");
		header.setDevType("1");
		header.setDitchCode("APP");
		Response<RegisterLoginVO> response = accountApi.login(loginDTO, header);
		System.out.println(JsonUtils.toFastJson(response));
	}

	@Test
	public void sendVerifyCode() {
		SmsVerifyCodeDTO codeDTO = new SmsVerifyCodeDTO();
		codeDTO.setAppId("vebff12m1762");
		codeDTO.setCode(SmsContants.CODE_LOGIN);
		codeDTO.setPhone("16612345679");

		SmsVerifyCodeVO codeVO = accountApi.sendVerifyCode(codeDTO).getData();
		System.out.println(JsonUtils.toFastJson(codeVO));
	}
}
