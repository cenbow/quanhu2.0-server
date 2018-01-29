package com.yryz.quanhu.resource.release.info.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

@Mapper
public interface ReleaseInfoDao {

    // int insert(ReleaseInfo record);

    int insertSelective(ReleaseInfo record);

    List<ReleaseInfoVo> selectByCondition(ReleaseInfoDto dto);

    ReleaseInfoVo selectByKid(Long kid);

    long countByCondition(ReleaseInfoDto dto);

    int updateByUkSelective(@Param("record") ReleaseInfo record);

    int updateByCondition(@Param("record") ReleaseInfo record, @Param("dto") ReleaseInfoDto dto);
    
    List<Long> selectKidByCreatedate(@Param("startDate")String startDate,@Param("endDate")String endDate);
}