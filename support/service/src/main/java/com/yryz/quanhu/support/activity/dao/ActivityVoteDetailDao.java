package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.support.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityVoteDetailVo;
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

    List<ActivityVoteDetailVo> selectVoteList(@Param("activityInfoId") Long activityInfoId, @Param("voteNo") Integer voteNo);

    List<ActivityVoteDetailVo> batchVote(List<Long> set);

    int selectCandidateCount(@Param("activityInfoId") Long activityInfoId, @Param("createUserId") Long createUserId);

    Integer selectMaxVoteNo(@Param("activityInfoId") Long activityInfoId);

    int updateVoteCount(@Param("kid") Long kid, @Param("activityInfoId") Long activityInfoId);




    AdminActivityVoteDetailVo selectByPrimaryKey(Long kid);


    int insert(AdminActivityVoteDetailDto record);

    List<AdminActivityVoteDetailVo> select(@Param("record") AdminActivityVoteDetailDto adminActivityVoteDetailDto);

    List<AdminActivityVoteDetailVo> selectPage(@Param("record") AdminActivityVoteDetailDto adminActivityVoteDetailDto);

    List<AdminActivityVoteDetailVo> selectByParam(@Param("record") AdminActivityVoteDetailDto adminActivityVoteDetailDto);

    Integer updateAddVote(@Param("id") Long id, @Param("addVote") Integer count);

    Integer updateStatus(@Param("id") Long id, @Param("status") Byte status);

    List<AdminActivityVoteDetailVo> selectRankList(@Param("activityInfoId") Long activityInfoId);

    long adminRanklistCount(AdminActivityVoteDetailDto adminActivityVoteDetailDto);

    Integer maxId(@Param("id") Long id);

    Integer selectMaxId();
}