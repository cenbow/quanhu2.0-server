package com.yryz.quanhu.demo.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * user Elasticsearch仓库
 *
 * @author jk
 */
public interface UserRepository extends ElasticsearchRepository<User, Long>{

}
