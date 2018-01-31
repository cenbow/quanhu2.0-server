package com.yryz.quanhu.other.activity.provider;


import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.other.activity.api.AdminIActivityParticipationApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteRecordDto;
import com.yryz.quanhu.other.activity.dto.AdminConfigObjectDto;
import com.yryz.quanhu.other.activity.service.AdminIActivityParticipationService;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteRecordVo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


/**
 * 投票类活动参与内容管理
 */
@Service(interfaceClass = AdminIActivityParticipationApi.class)
public class AdminIActivityParticipationProvider implements AdminIActivityParticipationApi {

	private static final Logger logger = LoggerFactory.getLogger(AdminIActivityParticipationProvider.class);

	@Autowired
	AdminIActivityParticipationService adminIActivityParticipationService;

	@Override
	public Response<AdminActivityInfoVo1> detail(Long id){
		AdminActivityInfoVo1 adminActivityInfoVo1 = null;
		try {
			adminActivityInfoVo1 = adminIActivityParticipationService.detail(id);
		} catch (Exception e) {
			logger.error("后台获取活动失败:"+ id);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(adminActivityInfoVo1);
	}

	/**
	 * 参与活动列表
	 */
	@Override
	public Response<List<AdminActivityVoteDetailVo>> list(AdminActivityVoteDetailDto adminActivityVoteDetailDto){
		List<AdminActivityVoteDetailVo> list = null;
		try {
			list = adminIActivityParticipationService.list(adminActivityVoteDetailDto);
		} catch (Exception e) {
			logger.error("参与活动列表异常:"+ adminActivityVoteDetailDto);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}

	/**
	 * 投票用户数据
	 */
	@Override
	public Response<List<AdminActivityVoteRecordVo>> voteList(AdminActivityVoteRecordDto adminActivityVoteRecordDto){
		List<AdminActivityVoteRecordVo> list = null;
		try {
			list = adminIActivityParticipationService.voteList(adminActivityVoteRecordDto);
		} catch (Exception e) {
			logger.error("投票用户数据异常:"+ adminActivityVoteRecordDto);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}


	/**
	 * 增加票数
	 */
	@Override
	public Response<Integer> addVote(Long id, Integer count){
		Integer count1 = null;
		try {
			count1 = adminIActivityParticipationService.addVote(id,count);
		} catch (Exception e) {
			logger.error("后台增加票数失败");
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count1);
	}

	@Override
	public Response<Integer> updateStatus(Long id, Byte status){
		Integer count = null;
		try {
			count = adminIActivityParticipationService.updateStatus(id,status);
		} catch (Exception e) {
			logger.error("后台更改状态失败");
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}

	//public ListPage<CustInfo> selectUser(String type, String nickName, String circle, Integer pageNo, Integer pageSize);

	@Override
	public Response<AdminConfigObjectDto> getVoteConfig(Long infoId){
		AdminConfigObjectDto adminConfigObjectDto = null;
		try {
			adminConfigObjectDto = adminIActivityParticipationService.getVoteConfig(infoId);
		} catch (Exception e) {
			logger.error("后台获取活动配置失败:"+ infoId);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(adminConfigObjectDto);
	}

	@Override
	public Response<String> saveVoteDetail(AdminActivityVoteDetailDto voteDetailDto){
		String str = null;
		try {
			str = adminIActivityParticipationService.saveVoteDetail(voteDetailDto);
		} catch (Exception e) {
			logger.error("后台发布作品失败:"+ voteDetailDto);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(str);
	}

	/*List<CircleInfo> circleList();*/

	@Override
	public Response<PageList> adminlist(AdminActivityVoteRecordDto adminActivityVoteRecordDto){
		PageList<AdminActivityVoteRecordVo> list = null;
		try {
			list = adminIActivityParticipationService.adminlist(adminActivityVoteRecordDto);
		} catch (Exception e) {
			logger.error("投票用户数据异常:"+ adminActivityVoteRecordDto);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}

	@Override
	public Response<PageList> adminlistDetail(AdminActivityVoteDetailDto adminActivityVoteDetailDto){
		PageList<AdminActivityVoteRecordVo> list = null;
		try {
			list = adminIActivityParticipationService.adminlistDetail(adminActivityVoteDetailDto);
		} catch (Exception e) {
			logger.error("投票用户数据异常:"+ adminActivityVoteDetailDto);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}

	@Override
	public Response<PageList<UserBaseInfoVO>> selectUser(AdminUserInfoDTO custInfoDTO, Integer pageNo, Integer pageSize){
		PageList<UserBaseInfoVO>  list = null;
		try {
			list = adminIActivityParticipationService.selectUser(custInfoDTO, pageNo, pageSize);
		} catch (Exception e) {
			logger.error("用户数据异常:"+ custInfoDTO);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}
}
