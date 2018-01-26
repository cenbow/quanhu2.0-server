package com.yryz.quanhu.user;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.constant.DevType;
import com.yryz.common.entity.RequestHeader;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.contants.SmsContants;
import com.yryz.quanhu.user.dto.BindPhoneDTO;
import com.yryz.quanhu.user.dto.ForgotPasswordDTO;
import com.yryz.quanhu.user.dto.LoginDTO;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.SmsVerifyCodeDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.dto.UpdateBaseInfoDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.dto.UserTagDTO.UserTagType;
import com.yryz.quanhu.user.mq.UserSender;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserTagApi;
import com.yryz.quanhu.user.vo.LoginMethodVO;
import com.yryz.quanhu.user.vo.RegisterLoginVO;
import com.yryz.quanhu.user.vo.SmsVerifyCodeVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;

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
	@Reference
	private UserApi userApi;
	@Reference
	private UserTagApi tagApi;
	@Autowired
	private UserSender sender;
	
/*	@Test
	public void sendMq(){
		sender.userCreate("dsdsfdfdfdf");
	}*/
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
		Response<List<LoginMethodVO>> response = accountApi.getLoginMethod(724007310011252736l);
		System.out.println(JsonUtils.toFastJson(response));
	}

	//@Test
	public void thirdLogin(){
		RequestHeader header = new RequestHeader();
		header.setAppId("vebff12m1762");
		header.setAppVersion("2.0");
		header.setDevId("24456241457878");
		header.setDevName("HUAWEI");
		header.setDevType("11");
		header.setDitchCode("APP");
		ThirdLoginDTO loginDTO = new ThirdLoginDTO();
		loginDTO.setAccessToken("162C9522156358786DE06E441E9C2A3F");
		loginDTO.setOpenId("F1E167B92BB7D1B1D093B1D44A9147E3");
		loginDTO.setUserRegInviterCode("48565247");
		loginDTO.setType(12);
		UserRegLogDTO logDTO = new UserRegLogDTO(null, header.getDitchCode(), header.getAppVersion(),
				RegType.QQ.getText(), DevType.ANDROID.getLabel(), header.getDevName(), header.getAppId(),
				"127.0.0.1", "", "", null);
		loginDTO.setRegLogDTO(logDTO);
		Response<RegisterLoginVO> response = accountApi.loginThird(loginDTO, header);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void forgotPassword() {
		Response<Boolean> response = accountApi
				.forgotPassword(new ForgotPasswordDTO("16612345679", "vebff12m1762", "6884", "a12345"));
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getLastLoginTime(){
		Response<Map<String,Date>> response = accountApi.getLastLoginTime(Lists.newArrayList("724011759597371392"));
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void updateUser(){
		UpdateBaseInfoDTO infoDTO = new UpdateBaseInfoDTO();
		infoDTO.setUserId(724007310011252736l);
		infoDTO.setUserGenders(10);
		Response<Boolean> response = userApi.updateUserInfo(infoDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void bindPhone(){
		BindPhoneDTO phoneDTO = new BindPhoneDTO(724007310011252736l, "16612345689", "5023", "vebff12m1762");
		Response<Boolean> response = accountApi.bindPhone(phoneDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void loginByVerifyCode(){
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

		Response<RegisterLoginVO> response = accountApi.loginByVerifyCode(registerDTO, header);
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
	
	//@Test
	public void eidtPassowrd(){
		Response<Boolean> response = accountApi.editPassword(724007310011252736l, "a123456", "a123456");
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void sendVerifyCode() {
		SmsVerifyCodeDTO codeDTO = new SmsVerifyCodeDTO();
		codeDTO.setAppId("vebff12m1762");
		codeDTO.setCode(SmsContants.CODE_FIND_PWD);
		codeDTO.setPhone("16612345689");

		Response<SmsVerifyCodeVO> response = accountApi.sendVerifyCode(codeDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void checkUserDistory(){
		Response<Boolean> response = accountApi.checkUserDisTalk(724007310011252736l);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getUserSimple(){
		Response<UserSimpleVO> response = userApi.getUserSimple(0l);
		UserSimpleVO simpleVO = response.getData();
		//Response<Map<String,UserSimpleVO>> response = userApi.getUserSimple(Sets.newHashSet("724011759597371392"));
		System.out.println(JsonUtils.toFastJson(simpleVO));
	}
	
	//@Test
	public void getUserLoginSimpleVO(){
		Response<UserLoginSimpleVO> response = userApi.getUserLoginSimpleVO(724007310011252736l);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void saveBatch(){
		UserTagDTO dto = new UserTagDTO();
		dto.setTagIds("1,2,3");
		dto.setTagType(UserTagType.US_SELECT.getType());
		dto.setUserId(729671306726400000l);
		Response<Boolean> response = tagApi.batchSaveUserTag(dto);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
