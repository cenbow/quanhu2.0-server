package com.yryz.quanhu.behavior.collection.dao;

import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.entity.CollectionInfo;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
  * @ClassName: CollectionInfoDao
  * @Description: CollectionInfo数据访问接口
  * @author jiangzhichao
  * @date 2018-01-26 17:57:44
  *
 */
@Mapper
public interface CollectionInfoDao {

    CollectionInfoVo selectByKid(Long kid);

    int delete(Long kid);

    int deleteByResourceId(@Param("resourceId") Long resourceId, @Param("moduleEnum") String moduleEnum, @Param("createUserId") Long createUserId);

    void insert(CollectionInfo collectionInfo);

    int insertByPrimaryKeySelective(CollectionInfo collectionInfo);

    int update(CollectionInfo collectionInfo);

    List<CollectionInfoVo> selectList(CollectionInfoDto collectionInfoDto);

    int selectCount(@Param("resourceId") Long resourceId, @Param("moduleEnum") String moduleEnum, @Param("createUserId") Long createUserId);

}