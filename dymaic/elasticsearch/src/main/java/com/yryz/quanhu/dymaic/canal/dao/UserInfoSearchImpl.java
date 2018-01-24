package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;
import javax.annotation.Resource;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.dymaic.canal.entity.UserInfo;

@Repository
public class UserInfoSearchImpl implements UserInfoSearch {
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	private String quanhu_app_id = "vebff12m1762";

	@Override
	public List<UserInfo> search(String keyWord, Integer page, Integer size) {
		// WildcardQueryBuilder
		// query1=QueryBuilders.wildcardQuery("userNickName", "*"+keyWord+"*");
		QueryBuilder query1 = QueryBuilders.matchQuery("userDesc", keyWord);
		QueryBuilder query2 = QueryBuilders.matchQuery("userNickName", keyWord);
		QueryBuilder query3 = QueryBuilders.termQuery("delFlag", 10);
		QueryBuilder query4 = QueryBuilders.termQuery("appId", quanhu_app_id);
		Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "lastHeat"));
		SearchQuery query = new NativeSearchQueryBuilder()
				.withFilter(QueryBuilders.boolQuery().must(query3).must(query4)
						.must(QueryBuilders.boolQuery().should(query1).should(query2)))
				.withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, UserInfo.class);
	}

}
