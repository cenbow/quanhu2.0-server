package com.yryz.quanhu.other.activity.service;


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
public interface AdminIActivityParticipationService {

	AdminActivityInfoVo1 detail(Long id);

	/**
	 * 参与活动列表
	 */
	PageList<AdminActivityVoteDetailVo> list(AdminActivityVoteDetailDto adminActivityVoteDetailDto);

	/**
	 * 增加票数
	 */
	Integer addVote(Long id, Integer count);

	Integer updateStatus(Long id, Byte status);

	public AdminConfigObjectDto getVoteConfig(Long infoId);

    PageList<UserBaseInfoVO> selectUser(AdminUserInfoDTO custInfoDTO, Integer pageNo, Integer pageSize);

    public String saveVoteDetail(AdminActivityVoteDetailDto voteDetailDto);
	/**
	 * 投票用户数据
	 */
	PageList adminlist(AdminActivityVoteRecordDto adminActivityVoteRecordDto);

}
