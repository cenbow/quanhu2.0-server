package com.yryz.quanhu.behavior.report.dao;

import com.yryz.common.dao.BaseDao;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.entity.Report;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 13:37 2018/1/22
 */
@Mapper
public interface ReportDao extends BaseDao{

    List<ReportDTO> selectByResource(ReportDTO dto);
}

