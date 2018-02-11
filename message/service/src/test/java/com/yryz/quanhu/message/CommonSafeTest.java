package com.yryz.quanhu.message;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yryz.common.config.SmsConfigVO;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.message.common.remote.MessageCommonConfigRemote;
import com.yryz.quanhu.message.commonsafe.api.CommonSafeApi;
import com.yryz.quanhu.message.commonsafe.constants.CommonServiceType;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonSafeTest {
	 @Autowired
	 CommonSafeApi safeApi;
	 @Autowired
	 MessageCommonConfigRemote commonConfigRemote;
	 
	 @Test
	 public void sendVerifyCode(){
		 VerifyCodeDTO codeDTO = new VerifyCodeDTO(1, CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), "16789571544", "vebff12m1762");
		 codeDTO.setIp("127.0.0.1");
		 VerifyCodeVO codeVO = safeApi.getVerifyCode(codeDTO).getData();
		 System.out.println(JsonUtils.toFastJson(codeVO));
	 }
	 
	 //@Test
/*	 public void checkVerifyCode(){
		 VerifyCodeDTO codeDTO = new VerifyCodeDTO(1, CommonServiceType.PHONE_VERIFYCODE_SEND.getName(), "15088052677", "vebff12m1762","9831");
		 
		 int result = safeApi.checkVerifyCode(codeDTO).getData();
		
		 
		 System.out.println("reslut:"+result);
	 }*/
	 
}
