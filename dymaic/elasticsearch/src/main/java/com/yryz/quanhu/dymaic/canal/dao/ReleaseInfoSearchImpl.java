package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Lists;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;

@Repository
public class ReleaseInfoSearchImpl implements ReleaseInfoSearch {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public List<ReleaseInfo> search(String keyWord, Integer page, Integer size) {
		QueryBuilder query1 = QueryBuilders.matchQuery("content", keyWord);
		QueryBuilder query2 = QueryBuilders.termQuery("delFlag", 10);
		QueryBuilder query3 = QueryBuilders.termQuery("shelveFlag", 10);
		QueryBuilder query4 = QueryBuilders.termQuery("coterieId", 0);
		
		List<Order> orders=Lists.newArrayList();
		orders.add(Order.desc("lastHeat"));
		orders.add(Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		SearchQuery query = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.boolQuery().must(query2).must(query3).must(query4).must(query1))
				.withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, ReleaseInfo.class);
	}

}
