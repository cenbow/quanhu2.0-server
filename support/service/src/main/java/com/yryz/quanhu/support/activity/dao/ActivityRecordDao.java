package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityRecord;
import com.yryz.quanhu.support.activity.vo.ActivityRecordVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityRecordDao
  * @Description: ActivityRecord数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:23:22
  *
 */
@Mapper
public interface ActivityRecordDao {

    ActivityRecordVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityRecord activityRecord);

    void insertByPrimaryKeySelective(ActivityRecord activityRecord);

    int update(ActivityRecord activityRecord);

}