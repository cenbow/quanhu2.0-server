package com.yryz.quanhu.support.activity.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityPrizes;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.support.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.support.activity.vo.AdminActivityVoteDetailVo;

import java.util.List;


public interface AdminActivityVoteService {
	
	/**
	 * 活动列表
	 */
	PageList<AdminActivityInfoSignUpVo> adminlist(AdminActivityInfoVoteDto param);
	
	Integer activityRelease(ActivityInfo activity, ActivityVoteConfig config, List<ActivityPrizes> prizes);
	
	AdminActivityInfoVo1 getActivityDetail(Long id);

	ActivityVoteConfig getConfigDetailByActivityId(String activityId);

	List<ActivityPrizes> getPrizesListByActivityId(String activityId);
	
	PageList<AdminActivityVoteDetailVo> selectRankList(AdminActivityVoteDetailDto adminActivityVoteDetailDto);

	Integer sendMessageVote(ActivityInfo activityInfo);
	Integer updateSave(ActivityInfo activity);
}
