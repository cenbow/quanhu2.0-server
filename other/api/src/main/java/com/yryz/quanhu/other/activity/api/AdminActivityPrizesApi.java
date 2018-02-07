/**
 * 
 */
package com.yryz.quanhu.other.activity.api;


import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;


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
	Response<Integer> updateBatchUsed(Long[] ids);
	
}
