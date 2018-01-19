package com.yryz.quanhu.support.sysconfig.provider;

import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.yryz.common.utils.IdGen;
import com.yryz.quanhu.support.sysconfig.entity.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.id.api.ConfigApi;
import com.yryz.quanhu.support.sysconfig.service.ConfigService;

/**
 * 配置信息
 * 
 * @author danshiyu
 *
 */
@Service(interfaceClass=ConfigApi.class)
public class ConfigProvier implements ConfigApi {
	private Logger logger = LoggerFactory.getLogger(ConfigProvier.class);
	@Autowired
	private ConfigService configService;

	@Override
	public <T> Response<Boolean> updateCongfigs(String configType, String operate, T configValue, T configDesc) {
		try {
			if (StringUtils.isBlank(configType)) {
				throw QuanhuException.busiError("configType不能为空");
			}
			if (configValue == null) {
				throw QuanhuException.busiError("configValue不能为空");
			}
			if (configValue instanceof String || configValue instanceof Integer || configValue instanceof Long
					|| configValue instanceof Double || configValue instanceof Boolean || configValue instanceof Byte || configValue instanceof Date) {
				throw QuanhuException.busiError("不支持的类型");
			}
			if (configDesc != null) {
				if (!configValue.getClass().equals(configDesc.getClass())) {
					throw QuanhuException.busiError("configValue和configDesc类型不一致");
				}
			}
			configService.updateCongfigs(configType, operate, configValue, configDesc);
			return ResponseUtils.returnSuccess();
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("updateCongfigs error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public <T> Response<T> getConfig(String configType, T t) {
		try {
			if (StringUtils.isBlank(configType)) {
				throw QuanhuException.busiError("configType不能为空");
			}
			if (t == null) {
				throw QuanhuException.busiError("t不能为空");
			}
			configService.getConfig(configType, t);
			return ResponseUtils.returnObjectSuccess(t);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("updateCongfigs error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public <T> Response<Boolean> addCongfig(String configType, String operate, T configValue, T configDesc) {
		Config config = new Config();
		config.setConfigId(IdGen.uuid());
		config.setConfigType(configType);
		config.setConfigName(configType + " name");
		config.setOperationName(operate);
		config.setConfigValue(JSON.toJSONString(configValue));
		config.setConfigDesc(JSON.toJSONString(configDesc));
		Date now = new Date();
		config.setCreateDate(now);
		config.setUpdateDate(now);
		configService.addConfig(config);
		return ResponseUtils.returnSuccess();
	}

}
