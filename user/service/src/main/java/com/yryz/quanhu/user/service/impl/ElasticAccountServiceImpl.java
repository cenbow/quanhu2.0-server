package com.yryz.quanhu.user.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.quanhu.user.dto.RegisterDTO;
import com.yryz.quanhu.user.dto.ThirdLoginDTO;
import com.yryz.quanhu.user.entity.UserAccount;
import com.yryz.quanhu.user.vo.ThirdUser;

/**
 * @author danshiyu
 * @version 1.0
 * @data 2017/11/9 0009 46
 * @Description 非强制绑定手机号模式
 */
@Service
public class ElasticAccountServiceImpl extends AbstractAccountService {

	/**
	 * 非强绑模式
	 */
	@Override
	@Transactional(rollbackFor = RuntimeException.class)
	public Long loginThird(ThirdLoginDTO loginDTO, ThirdUser thirdUser, Long custId) {
		if (custId != null) {
			RegisterDTO registerDTO = new RegisterDTO();
			registerDTO.setCityCode(loginDTO.getCityCode());
			registerDTO.setUserChannel(loginDTO.getUserChannel());
			registerDTO.setUserNickName(thirdUser.getNickName());
			registerDTO.setUserLocation(thirdUser.getLocation());
			registerDTO.setUserRegInviterCode(loginDTO.getUserRegInviterCode());
			registerDTO.setDeviceId(loginDTO.getDeviceId());
			custId = createUser(registerDTO);
			// 创建第三方账户
			//thirdLoginService.insert(new UserThirdLogin(custId, thirdUser.getThirdId(), loginDTO.getType().byteValue(),
			//		thirdUser.getNickName()));
		}

		// 更新设备号
		if (StringUtils.isNotBlank(loginDTO.getDeviceId())) {
			//userService.updateCustInfo(new CustBaseInfo(custId, loginDTO.getDeviceId()));
		}

		return custId;
	}
}
