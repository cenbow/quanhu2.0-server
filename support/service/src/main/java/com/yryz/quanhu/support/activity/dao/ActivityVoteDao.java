package com.yryz.quanhu.support.activity.dao;

import java.util.List;

import com.yryz.quanhu.support.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.support.activity.vo.AdminActivityVoteVo;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface ActivityVoteDao {

	List<AdminActivityVoteVo> adminlist(AdminActivityInfoVoteDto param);

	long adminlistCount(AdminActivityInfoVoteDto param);
	
	int insert(ActivityInfo record);
	
	int update(ActivityInfo record);

	AdminActivityInfoVo1 selectByPrimaryKey(Long id);

	Integer getVoteTotalCount(Long id);

}