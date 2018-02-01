package com.yryz.quanhu.dymaic.canal.dao;

import java.util.List;
import javax.annotation.Resource;

import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.dto.StarInfoDTO;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(UserInfoSearchImpl.class);

    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;
    private String quanhu_app_id = "vebff12m1762";

    @Override
    public List<UserInfo> search(String keyWord, Integer page, Integer size) {
        // WildcardQueryBuilder
        // query1=QueryBuilders.wildcardQuery("userNickName", "*"+keyWord+"*");
        QueryBuilder query1 = QueryBuilders.matchQuery("userBaseInfo.userDesc", keyWord);
        QueryBuilder query2 = QueryBuilders.matchQuery("userBaseInfo.userNickName", keyWord);
        QueryBuilder query3 = QueryBuilders.termQuery("userBaseInfo.delFlag", 10);
        QueryBuilder query4 = QueryBuilders.termQuery("userBaseInfo.appId", quanhu_app_id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Direction.DESC, "userBaseInfo.lastHeat"));
        SearchQuery query = new NativeSearchQueryBuilder()
                .withFilter(QueryBuilders.boolQuery().must(query3).must(query4)
                        .must(QueryBuilders.boolQuery().should(query1).should(query2)))
                .withPageable(pageable).build();
        return elasticsearchTemplate.queryForList(query, UserInfo.class);
    }

    @Override
    public List<UserInfo> searchStarUser(StarInfoDTO starInfoDTO) {
        Long tagId = starInfoDTO.getTagId();
        Integer pageNo = starInfoDTO.getCurrentPage();
        Integer pageSize = starInfoDTO.getPageSize();
        Long userId = starInfoDTO.getUserId();

        QueryBuilder queryTagId = QueryBuilders.termQuery("userTagInfo.userTagInfoList.tagId", tagId);
        QueryBuilder queryUserRole = QueryBuilders.termQuery("userBaseInfo.userRole", 11);
        SearchQuery query = null;

        //分页信息
        Pageable pageable = PageRequest.of(pageNo, pageSize,
                Sort.by(Direction.DESC,
                        "userStarInfo.recommendHeight", "userStarInfo.recommendTime", "userBaseInfo.lastHeat"));
        if (userId != null) {
            //当前用户
            QueryBuilder queryMyself = QueryBuilders.termQuery("userId", userId.toString());
            query = new NativeSearchQueryBuilder()
                    .withFilter(QueryBuilders.boolQuery()
                            .must(queryTagId)
                            .must(queryUserRole)
                            .mustNot(queryMyself))
                    .withPageable(pageable)
                    .build();
        } else {
            query = new NativeSearchQueryBuilder()
                    .withFilter(QueryBuilders.boolQuery()
                            .must(queryTagId)
                            .must(queryUserRole))
                    .withPageable(pageable)
                    .build();
        }

        logger.debug("searchStarUser query: {}", GsonUtils.parseJson(query));
        return elasticsearchTemplate.queryForList(query, UserInfo.class);
    }


}
