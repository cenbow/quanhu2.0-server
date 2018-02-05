package com.yryz.quanhu.behavior.transmit.dao;

import com.mongodb.WriteResult;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmitMongoDao extends AbsBaseMongoDAO<TransmitInfo> {

    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<TransmitInfo> findPage(Integer currentPage, Integer pageSize, Query query) {
        query.skip((currentPage - 1) * pageSize).limit(pageSize);
        return mongoTemplate.find(query, TransmitInfo.class);
    }

    public Integer remove(Query query) {
        WriteResult result = mongoTemplate.remove(query, TransmitInfo.class);
        if(result != null) {
            return result.getN();
        }

        return 0;
    }

}
