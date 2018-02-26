package com.yryz.quanhu.user;

import java.util.List;

import com.yryz.common.utils.SplitterUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.dto.StarRecommendQueryDTO;
import com.yryz.quanhu.user.service.UserOperateApi;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.vo.MyInviterVO;
import com.yryz.quanhu.user.vo.StarInfoVO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserStarTest {
	@Reference
	UserStarApi starApi;
	@Autowired
	UserOperateApi operateApi;
	@Test
	public void save(){
		String userId = "357091546488832";
		List<String> splitToList = SplitterUtils.ID_SPLITTER.splitToList(userId);
		for (String id : splitToList) {
			StarAuthInfo info = new StarAuthInfo();
			info.setAuthWay((byte)11);
			info.setAuthType((byte)10);
			info.setAppId("vebff12m1762");
			info.setUserId(id);
			info.setRealName("呵呵");
			info.setIdCard("421281198902045156");
			info.setLocation("位置");
			info.setTradeField("haha");
			info.setResourceDesc("哒呵呵哒呵呵哒呵呵哒");
			info.setContactCall("15088052677");
			Response<Boolean> response = starApi.save(info);
			System.out.println(JsonUtils.toFastJson(response));
		}
	}
	
	//@Test
	public void starRecommonedList(){
		StarAuthParamDTO paramDTO = new StarAuthParamDTO();
		paramDTO.setCurrentPage(1);
		paramDTO.setPageSize(10);
		paramDTO.setUserId(null);
		Response<PageList<StarInfoVO>> response = starApi.starList(paramDTO);
		System.out.println(JsonUtils.toFastJson(response));
	}

	//@Test
	public void labelStarListTest(){
		StarAuthParamDTO paramDTO = new StarAuthParamDTO();
		paramDTO.setCategoryId(13L);
		paramDTO.setCurrentPage(1);
		paramDTO.setPageSize(10);
		paramDTO.setUserId(727061873573347328L);
		Response<PageList<StarInfoVO>> response = starApi.labelStarList(paramDTO);
		System.out.println("labelStarListTest: " + JsonUtils.toFastJson(response));
	}
	
	public void listByParams(){
		
		//starApi.listByRecommend(1, 10, new StarRecommendQueryDTO());
	}
	
	//@Test
	public void getInviter(){
		Response<MyInviterVO> response = operateApi.getMyInviter(738943677265461248L, 10, null);
		System.out.println(JsonUtils.toFastJson(response));
	}
}
