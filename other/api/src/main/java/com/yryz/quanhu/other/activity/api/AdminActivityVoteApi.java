package com.yryz.quanhu.other.activity.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.entity.ActivityPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoSignUpVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;

import java.util.List;


public interface AdminActivityVoteApi {
	
	/**
	 * 活动列表
	 */
	Response<PageList<AdminActivityInfoSignUpVo>> adminlist(AdminActivityInfoVoteDto param);

	Response<Integer> activityRelease(ActivityInfo activity, ActivityVoteConfig config, List<ActivityPrizes> prizes);

	Response<AdminActivityInfoVo1> getActivityDetail(Long id);

	Response<ActivityVoteConfig> getConfigDetailByActivityId(String activityId);

	Response<List<ActivityPrizes>> getPrizesListByActivityId(String activityId);

	Response<PageList<AdminActivityVoteDetailVo>> selectRankList(AdminActivityVoteDetailDto adminActivityVoteDetailDto);

	Response<Integer> sendMessageVote(ActivityInfo activityInfo);
	Response<Integer> updateSave(ActivityInfo activity);
}
