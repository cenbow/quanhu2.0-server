package com.yryz.quanhu.behavior.count.service.impl;

import com.yryz.quanhu.behavior.count.dao.CountMongoDao;
import com.yryz.quanhu.behavior.count.dto.CountStatisticsDto;
import com.yryz.quanhu.behavior.count.entity.CountModel;
import com.yryz.quanhu.behavior.count.service.CountStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountStatisticsServiceImpl implements CountStatisticsService {

    @Autowired
    private CountMongoDao countMongoDao;

    /**
     * 获取活动每天的浏览数统计
     * @param   countStatisticsDto
     * @return
     * */
    public Map<String, Long> countModelMap(CountStatisticsDto countStatisticsDto) {
        Map<String, Long> resultMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String startDate = sdf.format(countStatisticsDto.getStartDate());
        String endDate = sdf.format(countStatisticsDto.getEndDate());
        Query query = new Query();
        query.addCriteria(Criteria.where("kid").is(countStatisticsDto.getKid().toString()));
        query.addCriteria(Criteria.where("page").is(countStatisticsDto.getPage()));
        if(!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate) ) {
            query.addCriteria(new Criteria().andOperator(Criteria.where("date").gte(Integer.valueOf(startDate)),
                    Criteria.where("date").lte(Integer.valueOf(endDate))));
        }
        long count = countMongoDao.count(query);
        if(count > 0) {
            query.with(new Sort(Sort.Direction.DESC, "date"));
            query.with(new Sort(Sort.Direction.DESC, "time"));
            List<CountModel> list = countMongoDao.find(query);
            if(!CollectionUtils.isEmpty(list)) {
                for(int i=0; i<list.size(); i++) {
                    CountModel countModel = list.get(i);
                    String d = countModel.getDate().toString();
                    Long c = countModel.getCount();
                    if(!resultMap.containsKey(d)) {
                        resultMap.put(d, c);
                    }
                }
                resultMap.put("count", list.get(0).getCount());
            }
        }

        return resultMap;
    }

}
