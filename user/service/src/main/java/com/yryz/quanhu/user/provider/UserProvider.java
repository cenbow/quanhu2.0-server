package com.yryz.quanhu.user.provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yryz.quanhu.user.entity.UserRegInfo;
import com.yryz.quanhu.user.vo.UserRegInfoVO;
import org.apache.commons.collections.CollectionUtils;
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
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UpdateBaseInfoDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
/**
 * 用户基础信息服务
 * @author danshiyu
 *
 */
@Service(interfaceClass=UserApi.class)
public class UserProvider implements UserApi{
	private static final Logger logger = LoggerFactory.getLogger(UserProvider.class);

	@Autowired
	private UserService userService;

	@Override
	public Response<UserSimpleVO> getUserSimple(Long userId) {
		try {
			if(userId == null){
				throw QuanhuException.busiError("userId不能为空");
			}
			return ResponseUtils.returnObjectSuccess(userService.getUserSimple(userId));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<UserSimpleVO> getUserSimple(Long userId, Long friendId) {
		try {
			if(friendId == null){
				throw QuanhuException.busiError("friendId不能为空");
			}
			UserSimpleVO simpleVO = userService.getUserSimple(userId,friendId);

			return ResponseUtils.returnObjectSuccess(simpleVO);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, UserSimpleVO>> getUserSimple(Set<String> userIds) {
		try {
			if(CollectionUtils.isEmpty(userIds)){
				throw QuanhuException.busiError("userIds不能为空");
			}
			Map<String, UserSimpleVO> map = userService.getUserSimple(userIds);

			return ResponseUtils.returnObjectSuccess(map);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, UserSimpleVO>> getUserSimple(Long userId, Set<String> firendIds) {
		try {
			if(CollectionUtils.isEmpty(firendIds)){
				throw QuanhuException.busiError("userIds不能为空");
			}
			Map<String, UserSimpleVO> map = userService.getUserSimple(userId,firendIds);
			return ResponseUtils.returnObjectSuccess(map);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<UserLoginSimpleVO> getUserLoginSimpleVO(Long userId) {
		try {
			if(userId == null){
				throw QuanhuException.busiError("userId不能为空");
			}
			UserLoginSimpleVO loginSimpleVO = userService.getUserLoginSimpleVO(userId);
			return ResponseUtils.returnObjectSuccess(loginSimpleVO);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<UserLoginSimpleVO> getUserLoginSimpleVO(Long userId, Long friendId) {
		try {
			if(friendId == null){
				throw QuanhuException.busiError("friendId不能为空");
			}
			UserLoginSimpleVO loginSimpleVO = userService.getUserLoginSimpleVO(userId,friendId);
			return ResponseUtils.returnObjectSuccess(loginSimpleVO);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<String> getUserIdByPhone(String phone, String appId) {
		try {
			if(StringUtils.isEmpty(phone)){
				throw QuanhuException.busiError("phone不能为空");
			}
			if(StringUtils.isEmpty(appId)){
				throw QuanhuException.busiError("appId不能为空");
			}
			return ResponseUtils.returnObjectSuccess(userService.getUserByPhone(phone, appId));
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, String>> getUserIdByPhone(Set<String> phones, String appId) {
		try {
			if(CollectionUtils.isEmpty(phones)){
				throw QuanhuException.busiError("phones不能为空");
			}
			if(StringUtils.isEmpty(appId)){
				throw QuanhuException.busiError("appId不能为空");
			}
			Map<String, String> map = userService.getUserByPhone(phones, appId);
			return ResponseUtils.returnObjectSuccess(map);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Map<String, UserBaseInfoVO>> getUser(Set<String> userIds) {
		try {
			if(CollectionUtils.isEmpty(userIds)){
				throw QuanhuException.busiError("userIds不能为空");
			}
			Map<String, UserBaseInfoVO> map = userService.getUser(userIds);
			return ResponseUtils.returnObjectSuccess(map);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<String>> getUserIdByParams(AdminUserInfoDTO custInfoDTO) {
		try {
			if(custInfoDTO == null || StringUtils.isBlank(custInfoDTO.getAppId())){
				throw QuanhuException.busiError("appId不能为空");
			}
			logger.info("getUserIdByParams custInfoDTO: {}", GsonUtils.parseJson(custInfoDTO));
			List<String> list = userService.getUserIdByParams(custInfoDTO);
			return ResponseUtils.returnObjectSuccess(list);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<String> getDeviceIdByUserId(Long userId) {
		try {
			if(userId == null || userId == 0L){
				throw QuanhuException.busiError("userId不能为空");
			}
			String devId = userService.getDeviceIdByUserId(userId);
			return ResponseUtils.returnObjectSuccess(devId);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<String>> getDeviceIdByUserId(List<String> userIds) {
		try {
			if(CollectionUtils.isEmpty(userIds)){
				throw QuanhuException.busiError("userIds不能为空");
			}
			List<String> devIds = userService.getDeviceIdByUserId(userIds);
			return ResponseUtils.returnObjectSuccess(devIds);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<UserBaseInfoVO>> listUserInfo(int pageNo, int pageSize, AdminUserInfoDTO custInfoDTO) {
		try {
			if(custInfoDTO == null || StringUtils.isBlank(custInfoDTO.getAppId())){
				throw QuanhuException.busiError("appId不能为空");
			}
			Page<UserBaseInfo> list = userService.listUserInfo(pageNo, pageSize, custInfoDTO);
			List<UserBaseInfoVO> baseInfos = GsonUtils.parseList(list, UserBaseInfoVO.class);
			PageList<UserBaseInfoVO> pageList = new PageList<>(pageNo, pageSize,
					baseInfos,list.getTotal());
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("listUserInfo error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<Boolean> updateUserInfo(UpdateBaseInfoDTO infoDTO) {
		try {
			logger.info("updateUserInfo infoDTO: {}", GsonUtils.parseJson(infoDTO));
			if(infoDTO == null || infoDTO.getUserId() == null){
				throw QuanhuException.busiError("用户id不能为空");
			}
			UserSimpleVO info = userService.getUserSimple(infoDTO.getUserId());
			if(info == null){
				throw QuanhuException.busiError("用户不存在");
			}
			UserBaseInfo baseInfo = GsonUtils.parseObj(infoDTO, UserBaseInfo.class);
			userService.updateUserInfo(baseInfo);
			return ResponseUtils.returnObjectSuccess(true);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("updateUserInfo error", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<Long>> getUserIdByCreateDate(String startDate, String endDate) {
		try {
			logger.info("getUserIdByCreateDate startDate: {},endDate: {}", startDate,endDate);
			List<Long> list = userService.getUserIdByCreateDate(startDate, endDate);
			return ResponseUtils.returnListSuccess(list);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户ID查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<List<UserBaseInfoVO>> getUserListByCreateDate(String startDate, String endDate) {
		try {
			List<UserBaseInfo> userBaseInfoList = userService.getUserListByCreateDate(startDate, endDate);
			List<UserBaseInfoVO> list = GsonUtils.parseList(userBaseInfoList, UserBaseInfoVO.class);
			return ResponseUtils.returnListSuccess(list);
		} catch (Exception e) {
			logger.error("getUserListByCreateDate error", e);
			return ResponseUtils.returnException(e);
		}

	}

	@Override
	public Response<List<UserBaseInfoVO>> getAllByUserIds(List<Long> userIds) {
		try {
			logger.info("getAllByUserIds userIds: {}", userIds);
			List<UserBaseInfo> list = userService.getAllByUserIds(userIds);
			List<UserBaseInfoVO> blist = GsonUtils.parseList(list, UserBaseInfoVO.class);
			return ResponseUtils.returnListSuccess(blist);
		} catch (QuanhuException e) {
			return ResponseUtils.returnException(e);
		} catch (Exception e) {
			logger.error("用户信息查询异常", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<UserRegInfoVO>> listMsgUserInfo(int pageNo, int pageSize, AdminUserInfoDTO custInfoDTO) {
		try {
			if(custInfoDTO == null || StringUtils.isBlank(custInfoDTO.getAppId())){
				throw QuanhuException.busiError("appId不能为空");
			}
			Page<UserRegInfo> list = userService.listMsgUserInfo(pageNo, pageSize, custInfoDTO);
			List<UserRegInfoVO> baseInfos = GsonUtils.parseList(list, UserRegInfoVO.class);
			PageList<UserRegInfoVO> pageList = new PageList<>(pageNo, pageSize, baseInfos, list.getTotal());
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("listUserInfo error", e);
			return ResponseUtils.returnException(e);
		}
	}
}
