package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityRecord;
import com.yryz.quanhu.support.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.support.activity.dto.ActivityUserPrizesDto;
import com.yryz.common.dao.BaseDao;
import com.yryz.quanhu.support.activity.vo.ActivityRecordVo;
import com.yryz.quanhu.support.activity.vo.ActivityUserPrizesVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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

}