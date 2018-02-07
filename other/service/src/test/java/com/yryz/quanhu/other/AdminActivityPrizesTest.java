/**
 * 
 */
package com.yryz.quanhu.other;


import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityPrizesApi;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author chenshi
 * @date 2017年9月12日
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminActivityPrizesTest {
	@Reference
	AdminActivityPrizesApi adminActivityPrizesApi;
	/**
	 * 奖品列表
	 * @param inActivityPrizes
	 * @return
	 */
	@Test
	public void listPrizes(){
		AdminInActivityUserPrizes inActivityPrizes = new AdminInActivityUserPrizes();
		System.out.println(JsonUtils.toFastJson(adminActivityPrizesApi.listPrizes(inActivityPrizes)));
	}
	
	/**
	 * 批量修改已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Test
	public void updateBatchUsed(){
		Long[] ids=new Long[]{1L};
		System.out.println(JsonUtils.toFastJson(adminActivityPrizesApi.updateBatchUsed(ids)));
	}
	
}
