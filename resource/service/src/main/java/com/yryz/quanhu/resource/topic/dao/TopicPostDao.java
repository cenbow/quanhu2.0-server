package com.yryz.quanhu.resource.topic.dao;

import com.yryz.quanhu.resource.topic.entity.TopicPost;
import com.yryz.quanhu.resource.topic.entity.TopicPostExample;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface TopicPostDao {
    long countByExample(TopicPostExample example);

    int deleteByExample(TopicPostExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TopicPostWithBLOBs record);

    int insertSelective(TopicPostWithBLOBs record);

    List<TopicPostWithBLOBs> selectByExampleWithBLOBs(TopicPostExample example);

    List<TopicPost> selectByExample(TopicPostExample example);

    TopicPostWithBLOBs selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TopicPostWithBLOBs record);

    int updateByPrimaryKeyWithBLOBs(TopicPostWithBLOBs record);

    int updateByPrimaryKey(TopicPost record);
}