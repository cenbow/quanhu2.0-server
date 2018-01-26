/**
 * 
 */
package com.yryz.quanhu.support.activity.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.support.activity.vo.AdminOutActivityUsrePrizes;

import java.util.List;


/**
 * @author chenshi
 * @date 2017年9月12日
 */
public interface AdminActivityPrizesApi {
	
	/**
	 * 奖品列表
	 * @param inActivityPrizes
	 * @return
	 */
	Response<PageList<AdminOutActivityUsrePrizes>> listPrizes(AdminInActivityUserPrizes inActivityPrizes);
	
	/**
	 * 批量修改已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	Response<Integer> updateBatchUsed(Long[] ids)	throws	Exception;
	
	 /**
	   * 读取Excel里面客户的信息
	   * @param wb
	   * @return
	   */
	 //Response<List<String>> readExcelValue(Workbook wb);

}
