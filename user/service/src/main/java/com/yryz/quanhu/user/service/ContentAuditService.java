package com.yryz.quanhu.user.service;

import com.github.pagehelper.PageHelper;
import com.yryz.quanhu.user.dao.ContentAuditDao;
import com.yryz.quanhu.user.entity.ContentAudit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class ContentAuditService {
    @Autowired
    private ContentAuditDao contentAuditDao;

    @Transactional
    public void testTransactional() {
        deleteById(405);
        if(2<2){
            throw new RuntimeException("事务回滚");
        }
    }

    public List<ContentAudit> findByTypeAndInfoId(Map<String, Object> map){
        PageHelper.startPage(1, 2);
        return contentAuditDao.findByTypeAndInfoId(map);
    }

    @Transactional
    void deleteById(Integer id){
        contentAuditDao.deleteById(id);
    }

    @Transactional
    void save(ContentAudit contentAudit){
        contentAuditDao.save(contentAudit);
    }
}
