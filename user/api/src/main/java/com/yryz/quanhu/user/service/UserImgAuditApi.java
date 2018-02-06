package com.yryz.quanhu.user.service;

import java.util.List;

import com.aliyun.oss.ServiceException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.UserImgAuditDTO;
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
	 * @param aduitActionStatus  11:通过 12:拒绝
	 * @return 
	 * @throws ServiceException
	 */
	Response<Boolean> auditImg(UserImgAuditDTO record,Integer aduitActionStatus);

	/**
	 * 批量审核用户头像审核信息
	 * @param record 11:通过 12:拒绝
	 * @param aduitActionStatus
	 * @throws ServiceException
	 */
	Response<Boolean> batchAuditImg(List<UserImgAuditDTO> record,Integer aduitActionStatus);

	/**
	 * 查询头像审核信息
	 * @param pageNo
	 * @param pageSize
	 * @param custId
	 * @param auditStatus 10:待审核 11:通过 12:拒绝
	 * @return
	 * @throws ServiceException
	 */
	Response<PageList<UserImgAuditVO>> listByParams(Integer pageNo,Integer pageSize,Long userId, Integer auditStatus);
}
