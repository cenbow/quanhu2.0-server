package com.yryz.quanhu.resource.release.config.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.resource.release.config.entity.ReleaseConfig;

@Mapper
public interface ReleaseConfigDao {

    List<ReleaseConfig> selectByClassifyId(Long classifyId);

    int updateByUkSelective(ReleaseConfig record);
}