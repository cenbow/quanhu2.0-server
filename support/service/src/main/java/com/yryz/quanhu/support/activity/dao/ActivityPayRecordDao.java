package com.yryz.quanhu.support.activity.dao;

import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityPayRecord;
import com.yryz.quanhu.support.activity.vo.ActivityPayRecordVo;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
  * @ClassName: ActivityPayRecordDao
  * @Description: ActivityPayRecord数据访问接口
  * @author jiangzhichao
  * @date 2018-01-20 14:22:36
  *
 */
@Mapper
public interface ActivityPayRecordDao {

    ActivityPayRecordVo selectByKid(Long kid);

    int delete(Long kid);

    void insert(ActivityPayRecord activityPayRecord);

    void insertByPrimaryKeySelective(ActivityPayRecord activityPayRecord);

    int update(ActivityPayRecord activityPayRecord);

}