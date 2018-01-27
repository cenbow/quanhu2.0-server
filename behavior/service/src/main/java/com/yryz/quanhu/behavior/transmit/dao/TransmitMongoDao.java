package com.yryz.quanhu.behavior.transmit.dao;

import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.behavior.transmit.entity.TransmitInfo;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
public class TransmitMongoDao extends AbsBaseMongoDAO<TransmitInfo> {

    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

}
