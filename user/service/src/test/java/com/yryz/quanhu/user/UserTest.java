package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.DevType;
import com.yryz.common.entity.RequestHeader;
import com.yryz.quanhu.user.contants.RegType;
import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.UserRegLogDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.vo.UserSimpleVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
//	    @Autowired
//	    private DemoService demoService;
	//
//	    @Autowired
//	    private ContentAuditService contentAuditService;
	@Reference
	private AccountApi accountApi;
	//
//	    @Test
//	    public void exampleTest(){
//	        List<ContentAudit> result = contentAuditService.findByTypeAndInfoId(new HashMap<>());
	//
//	        DemoVo demoVo = demoService.find(1L);
//	        System.out.printf(demoVo.toString());
//	    }
	//}
	@Test
	public void register(){
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
		registerDTO.setUserPhone("150880525677");
		registerDTO.setUserPwd("");
		registerDTO.setVeriCode("1234");
		registerDTO.setUserRegInviterCode("48565247");
		UserRegLogDTO logDTO = new UserRegLogDTO(null, header.getDitchCode(), header.getAppVersion(), RegType.PHONE.getText(), DevType.ANDROID.getLabel(), header.getDevName(), header.getAppId(), "127.0.0.1", registerDTO.getUserLocation(),registerDTO.getActivityChannelCode() , null);
		registerDTO.setRegLogDTO(logDTO);
		accountApi.register(registerDTO, header);
	}
}
