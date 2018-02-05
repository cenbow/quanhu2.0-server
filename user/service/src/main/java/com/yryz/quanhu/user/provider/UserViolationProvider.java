package com.yryz.quanhu.user.provider;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.contants.ViolatType;
import com.yryz.quanhu.user.entity.UserViolation;
import com.yryz.quanhu.user.service.UserViolationService;
import com.yryz.quanhu.user.service.ViolationApi;
import com.yryz.quanhu.user.vo.ViolationInfo;

@Service(interfaceClass = ViolationApi.class)
public class UserViolationProvider implements ViolationApi {
	private static Logger logger = LoggerFactory.getLogger(UserViolationProvider.class);

	@Autowired
	private UserViolationService violationService;

	@Override
	public Response<Boolean> addViolation(ViolationInfo info) {
		try {
			if (info == null || StringUtils.isEmpty(info.getUserId()) || info.getType() == null
					|| info.getType() < ViolatType.WARN.getType() || info.getType() > ViolatType.DISTORY.getType()) {
				throw QuanhuException.busiError("userId、type不合法");
			}
			if (StringUtils.isBlank(info.getAppId())) {
				throw QuanhuException.busiError("appId为空");
			}
			UserViolation violation = (UserViolation) GsonUtils.parseObj(info, UserViolation.class);
			violationService.saveViolation(violation, info.getAppId());
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("addViolation error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> updateViolation(ViolationInfo violationInfo) {
		try {
			if (violationInfo == null || StringUtils.isEmpty(violationInfo.getUserId())
					|| violationInfo.getType() == null || violationInfo.getType() < ViolatType.ALLTAIK.getType()
					|| violationInfo.getType() > ViolatType.NOFREEZE.getType()) {
				throw QuanhuException.busiError("userId、type不合法");
			}
			if (StringUtils.isBlank(violationInfo.getAppId())) {
				throw QuanhuException.busiError("appId为空");
			}
			UserViolation violation = (UserViolation) GsonUtils.parseObj(violationInfo, UserViolation.class);
			violationService.updateViolation(violation, violationInfo.getAppId());
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("updateViolation error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<ViolationInfo>> getViolation(String custId) {
		// TODO Auto-generated method stub
		return null;
	}

}
