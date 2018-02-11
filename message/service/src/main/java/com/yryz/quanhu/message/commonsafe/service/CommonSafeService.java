/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年12月4日
 * Id: CommonSafeService.java, 2017年12月4日 下午4:58:58 Administrator
 */
package com.yryz.quanhu.message.commonsafe.service;

import com.yryz.common.entity.AfsCheckRequest;
import com.yryz.quanhu.message.commonsafe.dto.IpLimitDTO;
import com.yryz.quanhu.message.commonsafe.dto.VerifyCodeDTO;
import com.yryz.quanhu.message.commonsafe.vo.VerifyCodeVO;

/**
 * @author danshiyu
 * @version 1.0
 * @date 2017年12月4日 下午4:58:58
 * @Description 公共安全业务管理
 */
public interface CommonSafeService {
	/**
	 * 根据业务类型获取普通验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 
	 */
	VerifyCodeVO getVerifyCode(VerifyCodeDTO codeDTO);

	/**
	 * 根据业务类型验证普通验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 0:成功 1:校验失败 2:已过期
	 */
	Integer checkVerifyCode(VerifyCodeDTO codeDTO);

	
	/**
	 * 根据业务类型获取图形验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return 
	 */
	String getImgVerifyCode(VerifyCodeDTO codeDTO);
	
	/**
	 * 根据业务类型验证图形验证码
	 * @param codeDTO {@link VerifyCodeDTO}
	 * @return
	 */
	boolean checkImgVerifyCode(VerifyCodeDTO codeDTO);

	/**
	 * 检查 阿里滑动 校验码
	 * @param verifyCodeDTO
	 * @param afsCheckReq
	 * @return
	 */
	boolean checkSmsSlipCode(VerifyCodeDTO verifyCodeDTO, AfsCheckRequest afsCheckReq);
	
	/**
	 * 根据业务类型记录对每个ip计数
	 * @param serviceType 业务类型{@link #CommonServiceType}
	 * @param ip
	 */
	void saveIpCount(IpLimitDTO dto);
	
	/**
	 * 根据业务类型判断每个ip是否超过访问次数
	 * @param serviceType 业务类型{@link #CommonServiceType}
	 * @param ip
	 * @return
	 */
	void checkIpLimit(IpLimitDTO dto);
}
