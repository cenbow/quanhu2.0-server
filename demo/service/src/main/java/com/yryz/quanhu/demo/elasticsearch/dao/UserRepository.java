package com.yryz.quanhu.demo.elasticsearch.dao;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * ElasticsearchRepository 基础查询
 * UserDao 特别复杂的查询通过ElasticsearchTemplate方式扩展
 * @author jk
 */
public interface UserRepository extends ElasticsearchRepository<User, Long>,UserDao{

}
