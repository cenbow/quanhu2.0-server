package com.yryz.quanhu.behavior.count.dao;

import com.google.common.collect.Lists;
import com.yryz.common.mongodb.AbsBaseMongoDAO;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.entity.CountModel;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.dao
 * @Desc:
 * @Date: 2018/1/24.
 */
@Service
public class CountMongoDao extends AbsBaseMongoDAO<CountModel> {
    /**
     * mongoTemplate
     *
     * @param mongoTemplate
     * @see com.yryz.common.mongodb.AbsBaseMongoDAO#setMongoTemplate(org.springframework.data.mongodb.core.MongoTemplate)
     */
    @Override
    protected void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    /**
     * 保存对象
     *
     * @param countModel
     * @return
     * @see com.yryz.common.mongodb.AbsBaseMongoDAO#save(java.lang.Object)
     */
    public CountModel save(CountModel countModel) {
        //设置默认值
        if (countModel.getKid() == null) {
            countModel.setKid("");
        }
        if (countModel.getPage() == null) {
            countModel.setPage("");
        }
        if (countModel.getCount() == null) {
            countModel.setCount(0L);
        }
        countModel.setCreateTime(System.currentTimeMillis());
        return super.save(countModel);
    }

    /**
     * 获取最新的一条数据
     *
     * @param type 行为类型,不能空
     * @param kid  业务表的主键，不能空
     * @param page 页面，给活动统计pv专用，可以空
     * @return CountModel
     */
    public CountModel getLastData(String type, String kid, String page) {
        Query query = new Query();
        if (StringUtils.isNotEmpty(kid)) {
            query.addCriteria(Criteria.where("kid").is(kid));
        }
        if (StringUtils.isNotEmpty(type)) {
            query.addCriteria(Criteria.where("type").is(type));
        }
        if (StringUtils.isNotEmpty(page)) {
            query.addCriteria(Criteria.where("page").is(page));
        }
        List<Order> orders = Lists.newArrayList();
        Order order = new Order(Direction.DESC, "createTime");
        Sort sort = new Sort(order);
        query.with(sort);
        query.limit(1);
        return findOne(query);
    }
}
