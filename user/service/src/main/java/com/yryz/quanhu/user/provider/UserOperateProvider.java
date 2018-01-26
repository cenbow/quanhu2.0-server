/**
 * 
 */
package com.yryz.quanhu.user.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.user.service.UserOperateApi;
import com.yryz.quanhu.user.service.UserOperateService;
import com.yryz.quanhu.user.vo.MyInviterVO;
import com.yryz.quanhu.user.vo.UserRegInviterLinkVO;

@Service(interfaceClass=UserOperateApi.class)
public class UserOperateProvider implements UserOperateApi {
	private static final Logger logger = LoggerFactory.getLogger(UserOperateProvider.class);
	
	@Autowired
	private UserOperateService operateService;
	
	@Override
	public Response<UserRegInviterLinkVO> getInviterLinkByUserId(Long userId) {
		try {
			if(userId == null){
				throw QuanhuException.busiError("用户id不能为空");
			}
			String inviterCode = operateService.selectInviterByUserId(userId);
			String inviterUrl = Context.getProperty("reg_inviter_url");
			return ResponseUtils.returnObjectSuccess(new UserRegInviterLinkVO(inviterCode, String.format("%s?inviter=%s", inviterUrl, inviterCode)));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取邀请链接未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<MyInviterVO> getMyInviter(Long userId, Integer limit, Long inviterId) {
		try {
			if(limit == null || limit > 100){
				limit = 10;
			}
			return ResponseUtils.returnObjectSuccess(operateService.getMyInviter(userId, limit, inviterId));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("获取我的邀请详情未知异常", e);
			return ResponseUtils.returnException(e);
		}
	}

}
