package com.yryz.quanhu.user.dao;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/25
 * @description
 */

@Repository
public class UserStarMongoDAO extends AbsBaseMongoDAO<StarInfoVO> {
    private static final Logger logger = LoggerFactory.getLogger(UserStarMongoDAO.class);

    private static final String COLLECTION_NAME = "user-info";


    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<StarInfoVO> getLabelStarList(StarAuthParamDTO param) {
        try {
            Query query = new Query();

            if (param.getCategoryId() != null && param.getCategoryId() >= 0) {
                query.addCriteria(Criteria.where("userInfo.tagId").is(param.getCategoryId()));
            }

//            if (param.getAuditStatus() != null && param.getAuditStatus() >= 0) {
                query.addCriteria(Criteria.where("starInfo.auditStatus").is(11));
//            }

            if (param.getUserId() != null && param.getUserId() > 0) {
                query.addCriteria(Criteria.where("userId").is(param.getUserId()));
            }

            query.with(new Sort(Sort.Direction.DESC, "starInfo.recommendHeight", "starInfo.recommendTime",
                    "starInfo.authTime", "starInfo.lastHeat")).skip(10).limit(5);

            return mongoTemplate.find(query, StarInfoVO.class, COLLECTION_NAME);
        } catch (Exception e) {
            logger.error("【MessageMongo】 查询列表失败！", e);
            throw new QuanhuException(ExceptionEnum.MONGO_EXCEPTION);
        }
    }
}
