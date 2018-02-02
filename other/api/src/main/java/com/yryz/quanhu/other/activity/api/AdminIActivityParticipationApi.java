package com.yryz.quanhu.other.activity.api;


import com.github.pagehelper.Page;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteRecordDto;
import com.yryz.quanhu.other.activity.dto.AdminConfigObjectDto;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteRecordVo;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

import java.util.List;


/**
 * 投票类活动参与内容管理
 */
public interface AdminIActivityParticipationApi {

	Response<AdminActivityInfoVo1> detail(Long id);

	/**
	 * 参与活动列表
	 */
	Response<PageList<AdminActivityVoteDetailVo>> list(AdminActivityVoteDetailDto adminActivityVoteDetailDto);

	/**
	 * 增加票数
	 */
	Response<Integer> addVote(Long id, Integer count);

	Response<Integer> updateStatus(Long id, Byte status);

	Response<AdminConfigObjectDto> getVoteConfig(Long infoId);

	Response<String> saveVoteDetail(AdminActivityVoteDetailDto voteDetailDto);
	/**
	 * 投票用户数据
	 */
	Response<PageList> adminlist(AdminActivityVoteRecordDto adminActivityVoteRecordDto);

    Response<PageList<UserBaseInfoVO>> selectUser(AdminUserInfoDTO custInfoDTO, Integer pageNo, Integer pageSize);
}
