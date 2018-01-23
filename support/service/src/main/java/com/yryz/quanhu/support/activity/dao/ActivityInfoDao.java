package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoAppListVo;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    ActivityVoteInfoVo selectVoteByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityInfo activityInfo);

    void insertByPrimaryKeySelective(ActivityInfo activityInfo);

    int update(ActivityInfo activityInfo);

    List<ActivityInfoAppListVo> selectMyList(@Param("custId")Long custId);

    Integer selectMylistCount(@Param("custId") Long custId);

    List<ActivityInfoAppListVo> selectAppList(@Param("type") Integer type);

    int updateJoinCount(@Param("kid") Long kid, @Param("userNum") Integer userNum);
}