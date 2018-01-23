package com.yryz.quanhu.dymaic.dao.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.dymaic.entity.UserInfo;

/**
 * user Elasticsearch仓库
 *
 * @author jk
 */
public interface UserRepository extends ElasticsearchRepository<UserInfo, Long>,UserInfoSearch{

}
