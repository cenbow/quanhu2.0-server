package com.yryz.quanhu.demo.elasticsearch.dao;

import java.util.List;

import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * 特别复杂的查询  可以用ElasticsearchTemplate
 * @author jk
 *
 */
public interface UserDao {
	List<User> find(String realName);
}
