package com.yryz.quanhu.dymaic.canal.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.dymaic.canal.entity.UserInfo;


/**
 * user Elasticsearch仓库
 *
 * @author jk
 */
public interface UserEsRepository extends ElasticsearchRepository<UserInfo, Long>{

}
