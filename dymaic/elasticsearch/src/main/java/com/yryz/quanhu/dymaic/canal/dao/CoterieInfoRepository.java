package com.yryz.quanhu.dymaic.canal.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

public interface CoterieInfoRepository extends ElasticsearchRepository<CoterieInfo, Long>,CoterieInfoSearch {

}
