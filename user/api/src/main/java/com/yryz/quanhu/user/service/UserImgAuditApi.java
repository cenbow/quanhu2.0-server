package com.yryz.quanhu.user.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
import com.yryz.quanhu.user.dto.UserImgAuditFindDTO;
import com.yryz.quanhu.user.vo.UserImgAuditVO;

/**
 * 用户头像审核服务
 * @author danshiyu
 *
 */
public interface UserImgAuditApi {
	/**
	 * 审核用户头像审核信息
	 * @param record
	 * @return 
	 */
	Response<Boolean> auditImg(UserImgAuditDTO record);

	/**
	 * 批量审核用户头像审核信息
	 * @param record 
	 */
	Response<Boolean> batchAuditImg(UserImgAuditDTO record);

	/**
	 * 查询头像审核信息
	 * @param findDTO
	 * @return
	 */
	Response<PageList<UserImgAuditVO>> listByParams(UserImgAuditFindDTO findDTO);
}
