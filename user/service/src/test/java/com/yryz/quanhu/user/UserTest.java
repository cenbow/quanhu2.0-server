package com.yryz.quanhu.user;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.SplitterUtils;
import com.yryz.quanhu.user.vo.*;
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
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.contants.SmsContants;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
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
import com.yryz.quanhu.user.service.UserOperateApi;
import com.yryz.quanhu.user.service.UserTagApi;

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
	@Reference
	private UserOperateApi opApi;

	//@Test
	public void getUserTagsTest() {
		List<Long> ids = Lists.newArrayList();
		ids.add(738943471107031040L);
		ids.add(738931462378192897L);

		Response<Map<Long, List<UserTagVO>>> userTags = tagApi.getUserTags(ids);
		System.out.println("getUserTagsTest: " + GsonUtils.parseJson(userTags));
	}
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

	//@Test
	public void getLoginMethod() {
		Response<List<LoginMethodVO>> response = accountApi.getLoginMethod(724007310011252736L);
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
		loginDTO.setAccessToken("077C5D51858725575DDC14FA85ED0EC4");
		loginDTO.setOpenId("EF6CCDB1A0B1E87C9259139A1E5756B9");
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
		infoDTO.setUserId(731519998090690560L);
		infoDTO.setUserGenders(10);
		Response<Boolean> response = userApi.updateUserInfo(infoDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}

	@Test
	public void getUserListByCreateDateTest(){
		String start = "2018-01-01";
		String end = "2018-03-01";
		Response<List<UserBaseInfoVO>> listResponse = userApi.getUserListByCreateDate(start, end);
		System.out.println("listResponse result: "+ JsonUtils.toFastJson(listResponse));
	}


	
	//@Test
	public void bindPhone(){
		BindPhoneDTO phoneDTO = new BindPhoneDTO(724007310011252736L, "16612345689", "5023", "vebff12m1762");
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
		loginDTO.setPhone("18500000010");
		loginDTO.setPassword("71b596cb42ee254f7416043d184fc970");
		RequestHeader header = new RequestHeader();
		header.setAppId("vebff12m1762");
		header.setAppVersion("2.0");
		header.setDevId("24456241457878");
		header.setDevName("HUAWEI");
		header.setDevType("11");
		header.setDitchCode("APP");
		Response<RegisterLoginVO> response = accountApi.login(loginDTO, header);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void eidtPassowrd(){
		Response<Boolean> response = accountApi.editPassword(724007310011252736L, "a123456", "a123456");
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void sendVerifyCode() {
		SmsVerifyCodeDTO codeDTO = new SmsVerifyCodeDTO();
		codeDTO.setAppId("vebff12m1762");
		codeDTO.setCode(SmsContants.CODE_FIND_PWD);
		codeDTO.setUserId(738943677265461248L);
		//codeDTO.setPhone("16612345689");

		Response<SmsVerifyCodeVO> response = accountApi.sendVerifyCode(codeDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void checkUserDistory(){
		Response<Boolean> response = accountApi.checkUserDisTalk(724007310011252736L);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getUserSimple(){
		Response<UserSimpleVO> response = userApi.getUserSimple(726907134491074560L);
		//UserSimpleVO simpleVO = response.getData();
		//Response<Map<String,UserSimpleVO>> response = userApi.getUserSimple(Sets.newHashSet("724011759597371392"));
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getUserLoginSimpleVO(){
		Response<UserLoginSimpleVO> response = userApi.getUserLoginSimpleVO(724007310011252736L);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void saveBatch() throws InterruptedException {
		String userIds = "724011759597371392,726904523150958592,726907134491074560,727061873573347328,727447149320273920,727909974996672512,728921199276711936,729669966696603648,729671306726400000,729748409979297792,729840906395049984,729841971546939392,729867054155956224,729869321898680320,729872671973171200,729894078090174464,729895040162848768,729905210645413888,729909574332186624,729914419055296512,730180329070518272,730921949663453184,730922963275735040,730938167459962880,730939490309890048,730940641361125376,730941139577331712,730943665018101760,730957219935453184,730974039027384320,730978024757035008,730988916794097664,731021214948163584,731154152775909376,731176469425979392,731177139440877568,731181932624379904,731189680745381888,731202445388185600,731214728994652160,731215347469942784,731228816487383040,731257644307873792,731271319483744256,731323477566939136,731333441891065856,731331225687990272,731339248686497792,731348989672726528,731519998090690560,732736435908280320,732957317487091712,732959344711655424,732985870429003776,736957598487642112,736963800420417536,737091498388062208,737162777665495040,737232613833695232,737237750614581248,737239863738499072,737249037788635136,737249982681440256,737251236811898880,737409858544099328,737469249351843840,738323707325939712,738422165156233216,738604752805920768,738706217113600000,738710065404297216,738715270904381440,738725166509309952,738729736354512896,738732278974873600,738738927584526336,738744700020572160,738768597218607104,738839567258206208,738902995334955008,738931462378192896,738931462378192897,738931462378192898,738931479558062080,738931479558062081,738931479558062082,738931479558062083,738931479558062084,738931479558062085,738931479558062086,738931479558062087,738931479558062088,738931479558062089,738931479558062090,738931479558062091,738931479558062092,738931479558062093,738931479558062094,738931496737931264,738931496737931265,738931496737931266,738931496737931267,738931496737931268,738931496737931269,738931496737931270,738931496737931271,738931513917800448,738931513917800449,738931513917800450,738931513917800451,738939296398819328,738939966413717504,738940292831232000,738940928486391808,738941100285083648,738941237724037120,738941340803252224,738941443882467328,738941529781813248,738941581321420800,738941701580505088,738941753120112640,738941787479851008,738941839019458560,738941890559066112,738941942098673664,738941993638281216,738942062357757952,738942234156449792,738943213408993280";
		List<String> stringList = SplitterUtils.ID_SPLITTER.splitToList(userIds);
		for (String id : stringList) {
			UserTagDTO dto = new UserTagDTO();
			dto.setTagIds("11,12");
			dto.setTagType(UserTagType.US_SELECT.getType());
			dto.setUserId(Long.valueOf(id));
			Response<Boolean> response = tagApi.batchSaveUserTag(dto);
			Thread.sleep(5);
		}

//		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getInviter(){
		Response<UserRegInviterLinkVO> response = opApi.getInviterLinkByUserId(731519998090690560L);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getMyInviter(){
		Response<MyInviterVO> response = opApi.getMyInviter(731519998090690560L, 10, null);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void getUserByPhone(){
		//Response<String> response = userApi.getUserIdByPhone("13429878385", "vebff12m1762");
		Response<Map<String,String>> response2 = userApi.getUserIdByPhone(Sets.newHashSet("13429878385"), "vebff12m1762");
		System.out.println(JsonUtils.toFastJson(response2));
	}
	
	//@Test
	public void getAdmin(){
		AdminUserInfoDTO infoDTO = new AdminUserInfoDTO(null, null, null, null, null, "vebff12m1762");
		infoDTO.setUserStatus(1);
		
		Response<PageList<UserBaseInfoVO>> response = userApi.listUserInfo(1, 20, infoDTO);
		//Response<List<String>> response2 = userApi.getUserIdByParams(new AdminUserInfoDTO(null, "ss", null, null, null, "vebff12m1762"));
		System.out.println(JsonUtils.toFastJson(response));
	}

	@Autowired
	private UserOperateApi userOperateApi;
	//@Test
	public void setAccountApiTest() {
		List<Long> ids = Lists.newArrayList(738942062357757952L,
				740181057343160320L);
		Response<Map<Long, UserRegLogVO>> mapResponse = userOperateApi.listByUserId(ids);
		System.out.println("mapResponse " + GsonUtils.parseJson(mapResponse));
	}
}
