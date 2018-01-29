package com.yryz.quanhu.other.activity.dao;

import com.yryz.quanhu.other.activity.dto.AdminActivityInfoVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityInfo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface ActivityVoteDao {

	List<AdminActivityVoteVo> adminlist(AdminActivityInfoVoteDto param);

	long adminlistCount(AdminActivityInfoVoteDto param);
	
	int insert(ActivityInfo record);
	
	int update(ActivityInfo record);

	AdminActivityInfoVo1 selectByPrimaryKey(Long id);

	Integer getVoteTotalCount(Long id);

}