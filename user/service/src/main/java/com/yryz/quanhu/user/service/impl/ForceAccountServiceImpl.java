package com.yryz.quanhu.user.service.impl;


import org.springframework.stereotype.Service;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.vo.ThirdUser;

/**
 * 强制绑定账号模式
 * 
 * @author danshiyu
 * @version 1.0
 * @data 2017/11/9 0009 46
 */
@Service
public class ForceAccountServiceImpl extends AbstractAccountService {

	/**
	 * 强绑模式
	 */
	@Override
	public Long loginThird(ThirdLoginDTO loginDTO, ThirdUser thirdUser, Long userId) {
		if (userId != null) {
			throw new QuanhuException(ExceptionEnum.NEED_PHONE);
		}
		// 更新设备号
		if (StringUtils.isNotBlank(loginDTO.getDeviceId())) {
			userService.updateUserInfo(new UserBaseInfo(userId, loginDTO.getDeviceId()));
		}
		return userId;
	}

}
