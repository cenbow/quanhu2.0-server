/**
 * 
 */
package com.yryz.quanhu.other.activity.service;


import com.yryz.common.response.PageList;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;


/**
 * @author chenshi
 * @date 2017年9月12日
 */
public interface AdminActivityPrizesService {
	
	/**
	 * 奖品列表
	 * @param inActivityPrizes
	 * @return
	 */
	PageList<AdminOutActivityUsrePrizes> listPrizes(AdminInActivityUserPrizes inActivityPrizes);
	
	/**
	 * 批量修改已使用
	 * @param ids
	 * @return
	 * @throws Exception
	 */
	int updateBatchUsed(Long[] ids)	throws	Exception;
}
