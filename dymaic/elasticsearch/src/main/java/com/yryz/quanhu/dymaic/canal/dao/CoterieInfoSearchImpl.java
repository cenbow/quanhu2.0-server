package com.yryz.quanhu.dymaic.canal.dao;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;

@Component
public class CoterieInfoSearchImpl implements CoterieInfoSearch {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public List<CoterieInfo> search(String keyWord, Integer page, Integer size) {
		QueryBuilder query1 = QueryBuilders.matchQuery("coterieName", keyWord);
		QueryBuilder query2 = QueryBuilders.matchQuery("intro", keyWord);
		QueryBuilder query3 = QueryBuilders.termQuery("deleted", 10);
		QueryBuilder query4 = QueryBuilders.termQuery("shelveFlag", 10);
		QueryBuilder query5 = QueryBuilders.termQuery("state", 11);
		
		List<Order> orders=new ArrayList<>();
		orders.add(Order.desc("sort"));
		orders.add(Order.desc("memberNum"));
		orders.add(Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		SearchQuery query = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.boolQuery().must(query3).must(query4).must(query5)
						.must(QueryBuilders.boolQuery().should(query1).should(query2)))
				.withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, CoterieInfo.class);
	}

}
