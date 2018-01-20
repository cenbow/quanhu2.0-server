package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityPrizes;
import com.yryz.quanhu.support.activity.vo.ActivityPrizesVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityPrizesDao
  * @Description: ActivityPrizes数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:23:02
  *
 */
@Mapper
public interface ActivityPrizesDao {

    ActivityPrizesVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityPrizes activityPrizes);

    void insertByPrimaryKeySelective(ActivityPrizes activityPrizes);

    int update(ActivityPrizes activityPrizes);

}