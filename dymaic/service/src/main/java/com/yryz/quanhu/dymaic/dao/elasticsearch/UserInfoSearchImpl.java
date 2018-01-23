package com.yryz.quanhu.dymaic.dao.elasticsearch;

import java.util.List;
import javax.annotation.Resource;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.WildcardQueryBuilder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Repository;

import com.yryz.quanhu.dymaic.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVO;

@Repository
public class UserInfoSearchImpl implements UserInfoSearch{
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	
	@Override
	public List<UserInfo> search(String keyWord,Integer page,Integer size) {
//		WildcardQueryBuilder query1=QueryBuilders.wildcardQuery("userNickName", "*"+keyWord+"*");
		QueryBuilder query1=QueryBuilders.matchQuery("userDesc", keyWord);
		QueryBuilder query2=QueryBuilders.matchQuery("userNickName", keyWord);
		Pageable pageable=PageRequest.of(page, size,Sort.by(Direction.DESC, "lastHeat"));
		SearchQuery query=new NativeSearchQueryBuilder().withFilter(QueryBuilders.boolQuery().should(query1)
				.should(query2)).withPageable(pageable).build();
		return elasticsearchTemplate.queryForList(query, UserInfo.class);
	}

}
