package com.yryz.quanhu.other.activity.dao;

import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.vo.ActivityVoteConfigVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityVoteConfigDao
  * @Description: ActivityVoteConfig数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:24:05
  *
 */
@Mapper
public interface ActivityVoteConfigDao {

    ActivityVoteConfigVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityVoteConfig activityVoteConfig);

    void insertByPrimaryKeySelective(ActivityVoteConfig activityVoteConfig);

    int update(ActivityVoteConfig activityVoteConfig);

    ActivityVoteConfig selectByActivityInfoId(Long activityInfoId);



    ActivityVoteConfig selectByPrimaryKey(Long kid);

    ActivityVoteConfig selectByInfoId(Long kid);

    ActivityVoteConfig selectVoteByActivityInfoId(Long activityInfoId);

}