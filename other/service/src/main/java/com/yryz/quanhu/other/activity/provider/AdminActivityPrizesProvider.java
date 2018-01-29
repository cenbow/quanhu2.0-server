/**
 * 
 */
package com.yryz.quanhu.other.activity.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityPrizesApi;
import com.yryz.quanhu.other.activity.service.AdminActivityPrizesService;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @author chenshi
 * @date 2017年9月12日
 */
@Service(interfaceClass = AdminActivityPrizesApi.class)
public class AdminActivityPrizesProvider implements AdminActivityPrizesApi {

	private static final Logger logger = LoggerFactory.getLogger(AdminActivityPrizesProvider.class);

	@Autowired
	AdminActivityPrizesService adminActivityPrizesService;
	/**
	 * 奖品列表
	 * @param inActivityPrizes
	 * @return
	 */
	@Override
	public Response<PageList<AdminOutActivityUsrePrizes>> listPrizes(AdminInActivityUserPrizes inActivityPrizes){
		PageList<AdminOutActivityUsrePrizes> pageList = null;
		try {
			pageList = adminActivityPrizesService.listPrizes(inActivityPrizes);
		} catch (Exception e) {
			logger.error("查询奖品列表失败");
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}
	
	/**
	 * 批量修改已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response<Integer> updateBatchUsed(Long[] ids){
		Integer count = null;
		try {
			count = adminActivityPrizesService.updateBatchUsed(ids);
		} catch (Exception e) {
			logger.error("批量修改失败:"+ids);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}
	
	 /**
	   * 读取Excel里面客户的信息
	   * @param wb
	   * @return
	   */
	 //Response<List<String>> readExcelValue(Workbook wb);

}
