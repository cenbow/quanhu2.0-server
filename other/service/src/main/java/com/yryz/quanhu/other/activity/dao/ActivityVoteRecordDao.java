package com.yryz.quanhu.other.activity.dao;

import com.yryz.quanhu.other.activity.dto.AdminActivityVoteRecordDto;
import com.yryz.quanhu.other.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.other.activity.vo.ActivityVoteRecordVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteRecordVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    int voteRecordCount(@Param("activityInfoId") Long activityInfoId,
                        @Param("createUserId") Long createUserId,
                        @Param("otherFlag") Integer otherFlag,
                        @Param("voteType") String voteType);

    List<AdminActivityVoteRecordVo> select(@Param("record") AdminActivityVoteRecordDto adminActivityVoteRecordDto);

    List<AdminActivityVoteRecordVo> selectPage(@Param("record") AdminActivityVoteRecordDto adminActivityVoteRecordDto);

    Integer selectCountByActivityInfoId(@Param("id") Long id, @Param("voteNo") Integer voteNo);

    List<AdminActivityCountVo> selectTotalCount(AdminActivityVoteRecordDto adminActivityVoteRecordDto);

    int selectRecordVoteCount(AdminActivityVoteRecordDto adminActivityVoteRecordDto);

}