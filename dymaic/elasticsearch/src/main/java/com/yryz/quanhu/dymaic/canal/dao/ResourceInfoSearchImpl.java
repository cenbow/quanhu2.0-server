package com.yryz.quanhu.dymaic.canal.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;

@Component
public class ResourceInfoSearchImpl implements ResourceInfoSearch {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public List<ResourceInfo> searchTopicInfo(String keyWord, Integer page, Integer size) {
		QueryBuilder query1 = QueryBuilders.matchQuery("topicInfo.content", keyWord);
		QueryBuilder query2 = QueryBuilders.matchQuery("topicInfo.title", keyWord);
		QueryBuilder query3 = QueryBuilders.termQuery("topicInfo.delFlag", 10);
		QueryBuilder query4 = QueryBuilders.termQuery("topicInfo.shelveFlag", 10);
		QueryBuilder query5 = QueryBuilders.termQuery("topicInfo.coterieId", 0);
		
		QueryBuilder R1 = QueryBuilders.boolQuery().must(query3).must(query4).must(query5)
				.must(QueryBuilders.boolQuery().should(query1).should(query2));
		
		QueryBuilder q1 = QueryBuilders.matchQuery("topicPostInfo.content", keyWord);
		QueryBuilder q2 = QueryBuilders.termQuery("topicPostInfo.delFlag", 10);
		QueryBuilder q3 = QueryBuilders.termQuery("topicPostInfo.shelveFlag", 10);
		QueryBuilder q4 = QueryBuilders.termQuery("topicPostInfo.coterieId", 0);
		
		QueryBuilder R2=QueryBuilders.boolQuery().must(q2).must(q3).must(q4).must(q1);
		
		List<Order> orders=new ArrayList<>();
		orders.add(Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		SearchQuery query = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.boolQuery().should(R1).should(R2))
				.withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, ResourceInfo.class);
	}

	@Override
	public List<ResourceInfo> searchReleaseInfo(String keyWord, Integer page, Integer size) {
		QueryBuilder query1 = QueryBuilders.termQuery("releaseInfo.delFlag", 10);
		QueryBuilder query2 = QueryBuilders.termQuery("releaseInfo.shelveFlag", 10);
		QueryBuilder query3 = QueryBuilders.termQuery("releaseInfo.coterieId", 0);
		QueryBuilder query4 = QueryBuilders.matchQuery("releaseInfo.content", keyWord);
		QueryBuilder query5 = QueryBuilders.matchQuery("releaseInfo.title", keyWord);
		QueryBuilder R1 = QueryBuilders.boolQuery().must(query1).must(query2).must(query3)
				.must(QueryBuilders.boolQuery().should(query4).should(query5));
		
		List<Order> orders=new ArrayList<>();
		orders.add(Order.desc("lastHeat"));
		orders.add(Order.desc("createDate"));
		Pageable pageable = PageRequest.of(page, size, Sort.by(orders));
		SearchQuery query = new NativeSearchQueryBuilder()
				.withFilter(R1)
				.withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, ResourceInfo.class);
	}
	

}
