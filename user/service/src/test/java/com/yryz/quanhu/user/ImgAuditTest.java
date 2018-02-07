package com.yryz.quanhu.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
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
		
		UserImgAuditDTO auditDTO = new UserImgAuditDTO();
		auditDTO.setKid(173493l);
		auditDTO.setAuditStatus((byte)ImgAuditStatus.FAIL.getStatus());
		auditDTO.setKids(Lists.newArrayList(173493l,173494l));
		auditDTO.setUserIds(Lists.newArrayList(749375104855851008l,737237750614581248l));
		//auditApi.batchAuditImg(auditDTO);
		Response<Boolean> response = auditApi.auditImg(auditDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}
	
	//@Test
	public void imgQuery(){
		Response<PageList<UserImgAuditVO>> response = auditApi.listByParams(new UserImgAuditFindDTO(1, 10, null, 10));
		System.out.println(JsonUtils.toFastJson(response));
	}
}
