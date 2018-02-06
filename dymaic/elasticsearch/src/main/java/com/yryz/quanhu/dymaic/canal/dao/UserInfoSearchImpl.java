package com.yryz.quanhu.dymaic.canal.dao;

import static com.yryz.quanhu.dymaic.canal.constants.ESConstants.USER_CREATEDATE;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
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

import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.dymaic.canal.constants.ESConstants;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.user.contants.AdminQueryUserStatus;
import com.yryz.quanhu.user.contants.UserAccountStatus;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.StarInfoDTO;

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
        QueryBuilder queryTagOnLine = QueryBuilders.termQuery("userTagInfo.userTagInfoList.delFlag", 10);
        QueryBuilder queryUserRole = QueryBuilders.termQuery("userBaseInfo.userRole", 11);
        SearchQuery query = null;

        //分页信息
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
                Sort.by(Direction.DESC, "userStarInfo.recommendHeight", "userStarInfo.recommendTime",
                        "userStarInfo.authTime", "userBaseInfo.lastHeat"));
        if (userId != null) {
            //当前用户
            QueryBuilder queryMyself = QueryBuilders.termQuery("userId", userId.toString());
            query = new NativeSearchQueryBuilder()
                    //.withSort(SortBuilders.fieldSort("userStarInfo.recommendTime").unmappedType("integer").order(SortOrder.DESC))
                    .withFilter(QueryBuilders.boolQuery()
                            .must(queryTagId)
                            .must(queryTagOnLine)
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

    @Override
    public List<UserInfo> adminSearchUser(AdminUserInfoDTO adminUserDTO) {
        Integer pageNo = adminUserDTO.getPageNo();
        Integer pageSize = adminUserDTO.getPageSize();
        String startDateStr = adminUserDTO.getStartDate();
        String endDateStr = adminUserDTO.getEndDate();

        //用户
        String nickName = adminUserDTO.getNickName();
        String phone = adminUserDTO.getPhone();

        //注册渠道
        String channelCode = adminUserDTO.getActivityChannelCode();
        //达人认证信息
        Byte auditStatus = adminUserDTO.getAuditStatus();
        Byte authType = adminUserDTO.getAuthType();
        Byte authWay = adminUserDTO.getAuthWay();
        String growLevel = adminUserDTO.getGrowLevel();
        Integer userStatus = adminUserDTO.getUserStatus();
        
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (StringUtils.isNoneBlank(nickName)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.USER_NICKNAME, "*" + nickName + "*"));
        }
        if (StringUtils.isNoneBlank(phone)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.USER_PHONE, "*" + phone + "*"));
        }
        if (StringUtils.isNoneBlank(channelCode)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.USER_ACTIVITYCHANNELCODE, "*" + channelCode + "*"));
        }
        if (auditStatus != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.STAR_AUDITSTATUS, auditStatus));
        }
        if (authType != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.STAR_AUTHTYPE, authType));
        }
        if (authWay != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.STAR_AUTHWAY, authWay));
        }
        if (growLevel != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.EVENT_GROWLEVEL, growLevel));
        }
        if(userStatus != null){
        	if(userStatus == AdminQueryUserStatus.NORMAL.getStatus()){
        		boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.USER_STATUS, UserAccountStatus.NORMAL.getStatus()));
        		boolQueryBuilder.must(QueryBuilders.rangeQuery(ESConstants.BAN_POST_TIME).lte(System.currentTimeMillis()));
        	}
        }
        if (StringUtils.isNoneBlank(startDateStr) && StringUtils.isNoneBlank(endDateStr)) {
            long startDate = DateUtils.parseDate(startDateStr).getTime();
            long endDate = DateUtils.parseDate(endDateStr).getTime();
            boolQueryBuilder.must(QueryBuilders.rangeQuery(USER_CREATEDATE).gte(startDate).lte(endDate));
        }

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);

        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        queryBuilder.withFilter(boolQueryBuilder)
                .withPageable(pageable);

        List<FieldSortBuilder> sortBuilders = getAdminUserSortBuilder();
        for (FieldSortBuilder sortBuilder : sortBuilders) {
            queryBuilder.withSort(sortBuilder);
        }

        SearchQuery query = queryBuilder.build();
        logger.info("adminSearchUser query: {}", GsonUtils.parseJson(query));
        return elasticsearchTemplate.queryForList(query, UserInfo.class);
    }

    private List<FieldSortBuilder> getAdminUserSortBuilder() {
        List<FieldSortBuilder> sortBuilders = new ArrayList<>();
        FieldSortBuilder sort = SortBuilders.fieldSort(USER_CREATEDATE).order(SortOrder.DESC);
        sortBuilders.add(sort);
        return sortBuilders;
    }


}
