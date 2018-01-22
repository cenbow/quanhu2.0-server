package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.support.activity.vo.ActivityEnrolConfigVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityEnrolConfigDao
  * @Description: ActivityEnrolConfig数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:21:28
  *
 */
@Mapper
public interface ActivityEnrolConfigDao {

    ActivityEnrolConfigVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityEnrolConfig activityEnrolConfig);

    void insertByPrimaryKeySelective(ActivityEnrolConfig activityEnrolConfig);

    int update(ActivityEnrolConfig activityEnrolConfig);

    ActivityEnrolConfig selectByActivityId(Long activityKid);
}