package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.contants.Constants.ImgAuditStatus;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
import com.yryz.quanhu.user.service.UserImgAuditApi;
import com.yryz.quanhu.user.vo.UserImgAuditVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImgAuditTest {
	@Reference
	UserImgAuditApi auditApi;
	
	//@Test
	public void imgAudit(){
		UserImgAuditDTO auditDTO = new UserImgAuditDTO("", "");
		auditApi.auditImg(auditDTO, ImgAuditStatus.NO_AUDIT.getStatus());
	}
	
	@Test
	public void imgQuery(){
		Response<PageList<UserImgAuditVO>> response = auditApi.listByParams(1, 10, null, 10);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
