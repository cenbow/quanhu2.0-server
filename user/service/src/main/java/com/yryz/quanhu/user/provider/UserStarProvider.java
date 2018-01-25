package com.yryz.quanhu.user.provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.dto.StarAuthQueryDTO;
import com.yryz.quanhu.user.dto.StarRecommendQueryDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.entity.UserStarAuth.StarAuditStatus;
import com.yryz.quanhu.user.entity.UserStarAuth.StarAuthType;
import com.yryz.quanhu.user.entity.UserStarAuth.StarAuthWay;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.service.UserStarService;
import com.yryz.quanhu.user.utils.PhoneUtils;
import com.yryz.quanhu.user.vo.StarAuthAuditVo;
import com.yryz.quanhu.user.vo.StarAuthLogVO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;

@Service(interfaceClass = UserStarApi.class)
public class UserStarProvider implements UserStarApi {
	private static final Logger logger = LoggerFactory.getLogger(UserStarProvider.class);

	@Autowired
	private UserStarService userStarService;

	@Autowired
	private UserService userService;

	@Override
	public Response<Boolean> save(StarAuthInfo info) {
		if (info != null && info.getAuthWay() == null) {
			info.setAuthWay(StarAuthWay.ADMIN_SET.getWay());
		}
		
		try {
			if(StringUtils.isBlank(info.getUserId())){
				throw QuanhuException.busiError("用户id不能为空");
			}
			UserStarAuth model = (UserStarAuth) GsonUtils.parseObj(info, UserStarAuth.class);
			UserStarAuth authModel = userStarService.get(info.getUserId(), null);
			if (authModel != null) {
				if (authModel.getAuditStatus() == StarAuditStatus.WAIT_AUDIT.getStatus()) {
					throw QuanhuException.busiError("待审核中，不允许重复申请");
				}
				if (authModel.getAuditStatus() == StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
					// 编辑资料不更新真实姓名和身份证号
					authModel.setRealName(null);
					authModel.setIdCard(null);
				} else {
					if (info.getAuthType() == StarAuthType.USER_APLLY.getType()
							&& StringUtils.isNotBlank(info.getIdCard())) {
						UserStarAuth authModel2 = userStarService.get(null, info.getIdCard());
						if (authModel2 != null && !info.getUserId().equals(authModel2.getUserId())
								&& authModel2.getAuditStatus() < StarAuditStatus.AUDIT_FAIL.getStatus()) {
							throw QuanhuException.busiError("身份证信息已存在");
						}
					}
				}
				userStarService.update(model);
			} else {
				checkParam(info);
				if (info.getAuthType().intValue() == StarAuthType.USER_APLLY.getType()
						&& StringUtils.isNotBlank(info.getIdCard())) {
					UserStarAuth authModel2 = userStarService.get(null, info.getIdCard());
					if (authModel2 != null && authModel2.getAuditStatus() < StarAuditStatus.AUDIT_FAIL.getStatus()) {
						throw QuanhuException.busiError("身份证信息已存在");
					}
				}
				userStarService.save(model);
			}
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.save]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<StarAuthInfo> get(String userId) {
		try {
			if (StringUtils.isBlank(userId)) {
				throw QuanhuException.busiError("userId不能为空");
			}
			UserStarAuth model = userStarService.get(userId, null);
			if(model == null){
				model = new UserStarAuth();
				model.setUserId(NumberUtils.createLong(userId));
				model.setAuditStatus((byte)14);
			}
			StarAuthInfo authInfo = (StarAuthInfo) GsonUtils.parseObj(model, StarAuthInfo.class);
			return ResponseUtils.returnObjectSuccess(authInfo);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.get]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, StarAuthInfo>> get(Set<String> userIds) {
		if (CollectionUtils.isEmpty(userIds)) {
			return null;
		}
		try {
			Map<String, StarAuthInfo> map = new HashMap<>();
			List<UserStarAuth> authModels = userStarService.get(new ArrayList<>(userIds));
			for (UserStarAuth authModel : authModels) {
				StarAuthInfo info = (StarAuthInfo) GsonUtils.parseObj(authModel, StarAuthInfo.class);
				if (info != null) {
					map.put(info.getUserId(), info);
				}
			}
			return ResponseUtils.returnObjectSuccess(map);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.get]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> update(StarAuthInfo info) {
		try {
			if (info == null) {
				throw QuanhuException.busiError("达人认证信息不能为空");
			}
			if(StringUtils.isBlank(info.getUserId())){
				throw QuanhuException.busiError("用户id不能为空");
			}
			if (info.getAuthWay() == null) {
				info.setAuthWay(StarAuthWay.ADMIN_SET.getWay());
			}
			UserStarAuth authModel = userStarService.get(info.getUserId(), null);
			if (authModel == null) {
				throw QuanhuException.busiError("认证信息不存在");
			}
			if (authModel.getAuditStatus() == StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
				// 编辑资料不更新真实姓名和身份证号
				authModel.setRealName(null);
				authModel.setIdCard(null);
			} else {
				if (info.getAuthType() == StarAuthType.USER_APLLY.getType()
						&& StringUtils.isNotBlank(info.getIdCard())) {
					UserStarAuth authModel2 = userStarService.get(null, info.getIdCard());
					if (authModel2 != null && !info.getUserId().equals(authModel2.getUserId())
							&& authModel2.getAuditStatus() < StarAuditStatus.AUDIT_FAIL.getStatus()) {
						throw QuanhuException.busiError("身份证信息已存在");
					}
				}
			}
			if (authModel.getAuditStatus() == StarAuditStatus.WAIT_AUDIT.getStatus()) {
				throw QuanhuException.busiError("待审核中，不允许编辑");
			}
			UserStarAuth model = (UserStarAuth) GsonUtils.parseObj(info, UserStarAuth.class);

			userStarService.update(model);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.get]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> updateAudit(StarAuthAuditVo auditVo) {

		try {
			if (auditVo == null || auditVo.getAuditStatus() == null
					|| auditVo.getAuditStatus() < StarAuditStatus.WAIT_AUDIT.getStatus()
					|| auditVo.getAuditStatus() > StarAuditStatus.CANCEL_AUTH.getStatus()
					|| auditVo.getUserId() == null) {
				throw QuanhuException.busiError("auditVo、auditStatus、userId为空");
			}
			UserStarAuth authModel = userStarService.get(auditVo.getUserId().toString(), null);
			if (authModel == null) {
				throw QuanhuException.busiError("认证信息不存在");
			}
			// 审核通过
			if (auditVo.getAuditStatus() == StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
				if (authModel.getAuditStatus() != StarAuditStatus.WAIT_AUDIT.getStatus()) {
					throw QuanhuException.busiError("操作不合法");
				}
			}
			// 审核失败
			if (auditVo.getAuditStatus() == StarAuditStatus.AUDIT_FAIL.getStatus()) {
				if (authModel.getAuditStatus() != StarAuditStatus.WAIT_AUDIT.getStatus()) {
					throw QuanhuException.busiError("操作不合法");
				}
			}
			// 取消认证
			if (auditVo.getAuditStatus() == StarAuditStatus.CANCEL_AUTH.getStatus()) {
				if (authModel.getAuditStatus() != StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
					throw QuanhuException.busiError("操作不合法");
				}
			}

			UserStarAuth reAuthModel = new UserStarAuth();
			reAuthModel.setUserId(auditVo.getUserId());
			reAuthModel.setAuditStatus(auditVo.getAuditStatus());
			reAuthModel.setAuditFailReason(auditVo.getReason());
			reAuthModel.setOperational(auditVo.getOperational());
			userStarService.updateAudit(reAuthModel);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.updateAudit]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> updateRecommend(StarAuthInfo authInfo) {

		try {
			if (authInfo == null || StringUtils.isEmpty(authInfo.getUserId())
					|| authInfo.getRecommendStatus() == null) {
				throw QuanhuException.busiError("authModel、recommendStatus、userId为空");
			}
			if (StringUtils.isBlank(authInfo.getRecommendDesc()) || authInfo.getRecommendDesc().length() > 200) {
				throw QuanhuException.busiError("推荐语为空或者超长");
			}
			UserStarAuth authModel = userStarService.get(authInfo.getUserId(), null);
			if (authModel == null) {
				throw QuanhuException.busiError("认证信息不存在");
			}
			if (authModel.getAuditStatus() != StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
				throw QuanhuException.busiError("操作不合法");
			}
			if (authModel.getRecommendStatus().intValue() == authInfo.getRecommendStatus().intValue()) {
				throw QuanhuException.busiError("操作不合法");
			}

			UserStarAuth model = (UserStarAuth) GsonUtils.parseObj(authInfo, UserStarAuth.class);
			userStarService.updateRecommend(model);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.updateRecommend]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> updateStarWeight(String userId, Integer weight) {

		try {
			if (StringUtils.isEmpty(userId) || weight == null) {
				throw QuanhuException.busiError("userId、weight为空");
			}
			UserStarAuth model = userStarService.get(userId, null);
			if (model == null || model.getAuditStatus() != StarAuditStatus.AUDIT_SUCCESS.getStatus()) {
				throw QuanhuException.busiError("非达人不能设置排序权重");
			}
			UserBaseInfo info = new UserBaseInfo();
			info.setUserId(NumberUtils.createLong(userId));
			info.setLastHeat(weight);
			userService.updateUserInfo(info);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.updateStarWeight]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<StarAuthInfo>> listByAuth(Integer pageNo, Integer pageSize, StarAuthQueryDTO paramDTO) {

		try {
			if (pageNo == null || pageSize == null || pageSize < 0 || pageSize > 100) {
				throw QuanhuException.busiError("pageNo、pageSize不合法");
			}
			StarAuthParamDTO authParamDTO = (StarAuthParamDTO) GsonUtils.parseObj(paramDTO, StarAuthParamDTO.class);
			authParamDTO.setStarAuthList(true);
			if (StringUtils.isNotBlank(authParamDTO.getStartTime())) {
				authParamDTO.setStartTime(String.format("%s 00:00:00", authParamDTO.getStartTime()));
			}
			if (StringUtils.isNotBlank(authParamDTO.getEndTime())) {
				authParamDTO.setEndTime(String.format("%s 23:59:59", authParamDTO.getEndTime()));
			}
			Page<UserStarAuth> page = userStarService.listByParams(pageNo, pageSize, authParamDTO);
			List<StarAuthInfo> infos = (List<StarAuthInfo>) GsonUtils.parseList(page.getResult(), StarAuthInfo.class);
			PageModel<StarAuthInfo> model = new PageModel<>();
			PageList<StarAuthInfo> listPage = model.getPagelist(pageNo, pageSize, infos, page.getTotal());

			return ResponseUtils.returnObjectSuccess(listPage);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.listByAuth]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<StarAuthInfo>> listByRecommend(Integer pageNo, Integer pageSize,
			StarRecommendQueryDTO paramDTO) {

		try {
			if (pageNo == null || pageSize == null || pageSize < 0 || pageSize > 100) {
				throw QuanhuException.busiError("pageNo、pageSize不合法");
			}
			StarAuthParamDTO authParamDTO = (StarAuthParamDTO) GsonUtils.parseObj(paramDTO, StarAuthParamDTO.class);
			authParamDTO.setAuditStatus(StarAuditStatus.WAIT_AUDIT.getStatus());
			authParamDTO.setStarRecommend(true);
			Page<UserStarAuth> page = userStarService.listByParams(pageNo, pageSize, authParamDTO);
			List<StarAuthInfo> infos = (List<StarAuthInfo>) GsonUtils.parseList(page.getResult(), StarAuthInfo.class);
			PageModel<StarAuthInfo> model = new PageModel<>();
			PageList<StarAuthInfo> listPage = model.getPagelist(pageNo, pageSize, infos, page.getTotal());
			return ResponseUtils.returnObjectSuccess(listPage);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.listByRecommend]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<StarAuthLogVO>> listAuthLog(String userId) {

		try {
			if (StringUtils.isBlank(userId)) {
				throw QuanhuException.busiError("userId不能为空");
			}
			return ResponseUtils.returnObjectSuccess((List<StarAuthLogVO>) GsonUtils
					.parseList(userStarService.listStarDetail(userId), StarAuthLogVO.class));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.listAuthLog]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<StarInfoVO>> starList(StarAuthParamDTO authParamDTO) {
		try {
			if(authParamDTO.getStart() == null ||authParamDTO.getStart() < 0){
				authParamDTO.setStart(0);
			}
			authParamDTO.setStart(0);
			if(authParamDTO.getLimit() == null ||authParamDTO.getLimit() > 100 || authParamDTO.getLimit() < 0 ){
				authParamDTO.setLimit(10);
			}
			List<UserStarAuth> list = userStarService.starList(authParamDTO);
			List<StarInfoVO> authInfos = getStarInfoList(authParamDTO.getUserId(), list);	
			return ResponseUtils.returnObjectSuccess(authInfos);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.starList]", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<StarInfoVO>> labelStarList(StarAuthParamDTO paramDTO) {
		try {
			logger.info("labelStarList request: {}", GsonUtils.parseJson(paramDTO));
			List<UserStarAuth> list = userStarService.labelStarList(paramDTO);
			logger.info("userStarService.labelStarList result: {}", GsonUtils.parseJson(list));

			List<StarInfoVO> starInfoVOList = getStarInfoList(paramDTO.getUserId(), list);
			logger.info("labelStarList result: {}", GsonUtils.parseJson(starInfoVOList));
			return ResponseUtils.returnObjectSuccess(starInfoVOList);
		} catch (Exception e) {
			logger.error("labelStarList error", e);
			return ResponseUtils.returnException(e);
		}
	}



	/**
	 * 解析达人信息
	 * @param userId
	 * @param authInfos
	 * @return
	 */
	private List<StarInfoVO> getStarInfoList(Long userId,List<UserStarAuth> authInfos){
		List<StarInfoVO> list = null;
		
		Map<String, UserSimpleVO> userVos = null;
		Set<String> userIds = null;
		//Map<String,String> levelMap = null;
		int length = authInfos == null ? 0 : authInfos.size();
		list = new ArrayList<>(length);
		userIds = new HashSet<>(length);
		
		if(length == 0){
			return list;
		}
		
		for (int i = 0; i < length; i++) {
			userIds.add(authInfos.get(i).getUserId().toString());
		}
		if (CollectionUtils.isNotEmpty(userIds)){
			userVos = userService.getUserSimple(userId, userIds);
			//levelMap = getUserLevels(userIds);
		}
		
		for (int i = 0; i < length; i++) {
			UserStarAuth authInfo = authInfos.get(i);
			StarInfoVO infoDTO = new StarInfoVO();
			UserStarSimpleVo simpleVo = (UserStarSimpleVo) GsonUtils.parseObj(authInfo, UserStarSimpleVo.class);
			simpleVo.setAuthType(null);
			simpleVo.setAuthWay(null);
			simpleVo.setUserId(null);
			infoDTO.parseUser(authInfo.getUserId().toString(), userVos);
			//infoDTO.getCustInfo().setCustLevel(levelMap.get(authInfo.getUserId()));
			infoDTO.setStarInfo(simpleVo);
			list.add(infoDTO);
		}
		return list;
	}
	@Override
	public Response<Integer> countStar() {
		try {
			Integer num = userStarService.countStar();
			return ResponseUtils.returnObjectSuccess(num == null ? 0 : num);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("[auth.countStar]", e);
			return ResponseUtils.returnException(e);
		}
	}

	/**
	 * 校验参数
	 * 
	 * @param authInfo
	 */
	private void checkParam(StarAuthInfo authInfo) {
		if (authInfo == null) {
			throw QuanhuException.busiError("达人认证信息不能为空");
		}
		if (StringUtils.isEmpty(authInfo.getAppId())) {
			throw QuanhuException.busiError("应用id不能为空");
		}
		if (StringUtils.isEmpty(authInfo.getUserId())) {
			throw QuanhuException.busiError("用户id不能为空");
		}
		if (authInfo.getAuthType() == null || authInfo.getAuthType().intValue() < StarAuthType.USER_APLLY.getType()
				|| authInfo.getAuthType() > StarAuthType.ADMIN_SET.getType()) {
			throw QuanhuException.busiError("认证类型不合法");
		}
		if (StringUtils.isBlank(authInfo.getTradeField()) || authInfo.getTradeField().length() < 1
				|| authInfo.getTradeField().length() > 10) {
			throw QuanhuException.busiError("请输入行业及领域");
		}
		// 后台设置无需以下校验
		if (authInfo.getAuthWay() == StarAuthWay.ADMIN_SET.getWay()) {
			return;
		}
		if (StringUtils.isEmpty(authInfo.getLocation())) {
			throw QuanhuException.busiError("请选择所在地区");
		}
		if (StringUtils.isBlank(authInfo.getResourceDesc()) || authInfo.getResourceDesc().length() < 10
				|| authInfo.getResourceDesc().length() > 100) {
			throw QuanhuException.busiError("描述不得少于10个字");
		}
		if (StringUtils.isBlank(authInfo.getContactCall())) {
			throw QuanhuException.busiError("联系方式为空");
		}
		if (authInfo.getAuthType() == StarAuthType.USER_APLLY.getType()
				&& !PhoneUtils.checkPhone(authInfo.getContactCall())) {
			throw QuanhuException.busiError("请填写正确的手机号");
		}
		if (authInfo.getAuthType() == StarAuthType.USER_APLLY.getType() && StringUtils.isBlank(authInfo.getIdCard())) {
			throw QuanhuException.busiError("个人申请需填写身份证号");
		}
		if (StringUtils.isEmpty(authInfo.getRealName()) || authInfo.getRealName().length() < 2
				|| authInfo.getRealName().length() > 10) {
			throw QuanhuException.busiError("姓名为2~10个以内的汉字");
		}
		if (authInfo.getAuthType() == StarAuthType.ADMIN_SET.getType()
				&& (StringUtils.isBlank(authInfo.getOrganizationName()) || authInfo.getOrganizationName().length() < 2
						|| authInfo.getOrganizationName().length() > 10)) {
			throw QuanhuException.busiError("机构名称为2~10个以内的汉字");
		}
		if (authInfo.getAuthType() == StarAuthType.ADMIN_SET.getType()
				&& StringUtils.isBlank(authInfo.getOrganizationPaper())) {
			throw QuanhuException.busiError("企业/机构申请需上传证件");
		}
	}

}
