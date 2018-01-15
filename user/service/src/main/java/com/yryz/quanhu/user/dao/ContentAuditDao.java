package com.yryz.quanhu.user.dao;

import com.yryz.quanhu.user.entity.ContentAudit;
//import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//@Mapper
public interface ContentAuditDao {
    List<ContentAudit> findByTypeAndInfoId(Map<String, Object> map);

    void deleteById(Long id);

    void save(ContentAudit contentAudit);
}
