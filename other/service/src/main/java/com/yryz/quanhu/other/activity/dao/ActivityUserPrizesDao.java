package com.yryz.quanhu.other.activity.dao;

import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.other.activity.vo.AdminInActivityUserPrizes;
import com.yryz.quanhu.other.activity.vo.AdminOutActivityUsrePrizes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
  * @ClassName: ActivityUserPrizesDao
  * @Description: ActivityUserPrizes数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:23:45
  *
 */
@Mapper
public interface ActivityUserPrizesDao {

    ActivityUserPrizesVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityUserPrizes activityUserPrizes);

    void insertByPrimaryKeySelective(ActivityUserPrizes activityUserPrizes);

    int update(ActivityUserPrizes activityUserPrizes);

    int updateUserRoll(@Param("createUserId") Long createUserId);

    int selectUserRoll(@Param("createUserId") Long createUserId);

    int updateStatus(@Param("createUserId") Long createUserId);

    List<ActivityUserPrizes> selectUserPrizesList(ActivityVoteDto activityVoteDto);

    ActivityUserPrizes selectByPrimaryKey(Long kid);

    List<AdminOutActivityUsrePrizes> listPrizesByConditionAndPage(@Param("dto") AdminInActivityUserPrizes dto);

    Integer	listPrizesByConditionAndPageCount(@Param("dto") AdminInActivityUserPrizes dto);

    int updateBatchUsed(@Param("ids") Long[] ids, @Param("status") Byte status)	throws	Exception;

    List<ActivityUserPrizes> selectUserPrizesList();

}