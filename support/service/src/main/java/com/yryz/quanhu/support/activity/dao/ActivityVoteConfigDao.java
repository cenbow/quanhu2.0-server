package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.vo.ActivityVoteConfigVo;
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

}