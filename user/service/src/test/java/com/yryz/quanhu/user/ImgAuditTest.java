package com.yryz.quanhu.user;

import java.util.ArrayList;
import java.util.List;

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
import com.yryz.quanhu.user.dto.UserImgAuditFindDTO;
import com.yryz.quanhu.user.service.UserImgAuditApi;
import com.yryz.quanhu.user.vo.UserImgAuditVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ImgAuditTest {
	@Reference
	UserImgAuditApi auditApi;
	
	@Test
	public void imgAudit(){
		//auditApi.auditImg(auditDTO, ImgAuditStatus.FAIL.getStatus());
		List<UserImgAuditDTO> auditDTOs = new ArrayList<>();
		UserImgAuditDTO auditDTO = new UserImgAuditDTO("737237750614581248", "https://cdn.yryz.com/pic/opus/FF366EFF-13A4-4101-B4F5-28D0CD7771AB_iOS.jpg",(byte)10);
		UserImgAuditDTO auditDTO1 = new UserImgAuditDTO("749375104855851008", "https://cdn-qa.yryz.com/pic/hwq/c5bbb6c5b74b56c59d33592219cfe9c1.jpg",(byte)10);
		auditDTOs.add(auditDTO1);
		auditDTOs.add(auditDTO);
		auditApi.batchAuditImg(auditDTOs, 12);
	}
	
	//@Test
	public void imgQuery(){
		Response<PageList<UserImgAuditVO>> response = auditApi.listByParams(new UserImgAuditFindDTO(1, 10, null, 10));
		System.out.println(JsonUtils.toFastJson(response));
	}
}
