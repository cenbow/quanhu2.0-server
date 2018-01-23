package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

/**
 * 
  * @ClassName: ActivityVoteDetailDao
  * @Description: ActivityVoteDetail数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:24:28
  *
 */
@Mapper
public interface ActivityVoteDetailDao {

    ActivityVoteDetailVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityVoteDetail activityVoteDetail);

    void insertByPrimaryKeySelective(ActivityVoteDetail activityVoteDetail);

    int update(ActivityVoteDetail activityVoteDetail);

    List<ActivityVoteDetailVo> selectVoteList(@Param("activityInfoId") Long activityInfoId);

    List<ActivityVoteDetailVo> batchVote(List<Long> set);

    int selectCandidateCount(@Param("activityInfoId") Long activityInfoId, @Param("createUserId") Long createUserId);

}