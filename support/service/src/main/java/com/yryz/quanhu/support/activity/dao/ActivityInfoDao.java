package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityInfoDao
  * @Description: ActivityInfo数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:22:12
  *
 */
@Mapper
public interface ActivityInfoDao {

    ActivityInfoVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityInfo activityInfo);

    void insertByPrimaryKeySelective(ActivityInfo activityInfo);

    int update(ActivityInfo activityInfo);

}