package com.yryz.quanhu.other.activity.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityEnrolConfigApi;
import com.yryz.quanhu.other.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.other.activity.service.AdminActivityEnrolConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = AdminActivityEnrolConfigApi.class)
public class AdminActivityEnrolConfigProvider implements AdminActivityEnrolConfigApi {

	private static final Logger logger = LoggerFactory.getLogger(AdminActivityEnrolConfigProvider.class);

	@Autowired
	AdminActivityEnrolConfigService adminActivityEnrolConfigService;
	/**
	 * 根据活动id获取配置信息
	 * @param id
	 * @return
	 */
	@Override
	public Response<ActivityEnrolConfig> getActivityEnrolConfigByActId(Long activityId) {
		ActivityEnrolConfig activityEnrolConfig = null;
		try {
			activityEnrolConfig = adminActivityEnrolConfigService.getActivityEnrolConfigByActId(activityId);
		} catch (Exception e) {
			logger.error("查询活动配置信息失败",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(activityEnrolConfig);
	}

}
