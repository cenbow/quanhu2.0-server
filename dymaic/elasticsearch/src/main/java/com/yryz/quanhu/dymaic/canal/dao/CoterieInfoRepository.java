package com.yryz.quanhu.dymaic.canal.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

@Repository
public interface CoterieInfoRepository extends ElasticsearchRepository<CoterieInfo, Long>,CoterieInfoSearch {

}
