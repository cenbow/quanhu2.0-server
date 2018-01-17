package com.yryz.quanhu.demo.elasticsearch.dao;

import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.demo.elasticsearch.entity.User;

/**
 * 特别复杂的查询  可以用ElasticsearchTemplate
 * @author jk
 *
 */
@Repository
public class UserDaoImpl implements UserDao{
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public List<User> find(String realName) {
		SearchQuery query=new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.matchQuery("realName",realName)).build();
		return elasticsearchTemplate.queryForList(query, User.class);
	}

}
