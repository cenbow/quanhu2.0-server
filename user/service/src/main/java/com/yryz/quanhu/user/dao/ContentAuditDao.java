package com.yryz.quanhu.user.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Mapper;

import com.yryz.quanhu.user.entity.ContentAudit;

@Mapper
public interface ContentAuditDao {
    List<ContentAudit> findByTypeAndInfoId(Map<String, Object> map);

    void deleteById(Integer id);

    void save(ContentAudit contentAudit);
}
