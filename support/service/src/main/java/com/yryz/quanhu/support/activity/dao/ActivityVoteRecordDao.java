package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.vo.ActivityVoteRecordVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityVoteRecordDao
  * @Description: ActivityVoteRecord数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:24:45
  *
 */
@Mapper
public interface ActivityVoteRecordDao  {

    ActivityVoteRecordVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityVoteRecord activityVoteRecord);

    void insertByPrimaryKeySelective(ActivityVoteRecord activityVoteRecord);

    int update(ActivityVoteRecord activityVoteRecord);

}