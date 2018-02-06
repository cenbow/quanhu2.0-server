/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年2月6日
 * Id: DraftDao.java, 2018年2月6日 下午5:26:16 yehao
 */
package com.yryz.quanhu.resource.draft.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年2月6日 下午5:26:16
 * @Description 草稿箱工具类
 */
@Mapper
public interface DraftDao {
	
    int insertSelective(ReleaseInfo record);

    List<ReleaseInfoVo> selectByCondition(ReleaseInfoDto dto);

    ReleaseInfoVo selectByKid(Long kid);

    long countByCondition(ReleaseInfoDto dto);

    int updateByUkSelective(@Param("record") ReleaseInfo record);

    int updateByCondition(@Param("record") ReleaseInfo record, @Param("dto") ReleaseInfoDto dto);

    List<Long> selectKidByCondition(ReleaseInfoDto dto);

}
