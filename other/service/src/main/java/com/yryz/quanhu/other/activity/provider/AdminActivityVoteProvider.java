package com.yryz.quanhu.other.activity.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.api.AdminActivityVoteApi;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service(interfaceClass = AdminActivityVoteApi.class)
public class AdminActivityVoteProvider implements AdminActivityVoteApi {

	private static final Logger logger = LoggerFactory.getLogger(AdminActivityVoteProvider.class);

	@Autowired
	AdminActivityVoteService adminActivityVoteService;

	/**
	 * 活动列表
	 */
	@Override
	public Response<PageList<AdminActivityVoteVo>> adminlist(AdminActivityInfoVoteDto param){
		PageList<AdminActivityVoteVo> pageList = null;
		try {
			pageList = adminActivityVoteService.adminlist(param);
		} catch (Exception e) {
			logger.error("查询失败:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}

	@Override
	public Response<Integer> activityRelease(ActivityInfo activity, ActivityVoteConfig config, List<ActivityPrizes> prizes){
		Integer count = null;
		try {
			count = adminActivityVoteService.activityRelease(activity,config,prizes);
		} catch (Exception e) {
			logger.error("后台发布活动失败:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}

	@Override
	public Response<AdminActivityInfoVo1> getActivityDetail(Long id){
		AdminActivityInfoVo1 activityInfo = null;
		try {
			activityInfo = adminActivityVoteService.getActivityDetail(id);
		} catch (Exception e) {
			logger.error("后台获取活动失败:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(activityInfo);
	}

	@Override
	public Response<ActivityVoteConfig> getConfigDetailByActivityId(String activityId){
		ActivityVoteConfig activityVoteConfig = null;
		try {
			activityVoteConfig = adminActivityVoteService.getConfigDetailByActivityId(activityId);
		} catch (Exception e) {
			logger.error("后台获取活动失败:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(activityVoteConfig);
	}

	@Override
	public Response<List<ActivityPrizes>> getPrizesListByActivityId(String activityId){
		List<ActivityPrizes> list = null;
		try {
			list = adminActivityVoteService.getPrizesListByActivityId(activityId);
		} catch (Exception e) {
			logger.error("活动getPrizesListByActivityId异常:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(list);
	}

	@Override
	public Response<PageList<AdminActivityVoteDetailVo>> selectRankList(AdminActivityVoteDetailDto adminActivityVoteDetailDto){
		PageList<AdminActivityVoteDetailVo> pageList = null;
		try {
			pageList = adminActivityVoteService.selectRankList(adminActivityVoteDetailDto);
		} catch (Exception e) {
			logger.error("查询selectRankList失败:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(pageList);
	}

	@Override
	public Response<Integer> sendMessageVote(ActivityInfo activityInfo){
		Integer count = null;
		try {
			count = adminActivityVoteService.sendMessageVote(activityInfo);
		} catch (Exception e) {
			logger.error("活动sendMessageVote异常:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}
	@Override
	public Response<Integer> updateSave(ActivityInfo activity){
		Integer count = null;
		try {
			count = adminActivityVoteService.updateSave(activity);
		} catch (Exception e) {
			logger.error("活动updateSave异常:",e);
			return ResponseUtils.returnException(e);
		}
		return ResponseUtils.returnObjectSuccess(count);
	}
}
