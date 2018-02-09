package com.yryz.quanhu.dymaic.canal.dao;

import static com.yryz.quanhu.dymaic.canal.constants.ESConstants.USER_CREATEDATE;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Set;

import javax.annotation.Resource;

import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.vo.UserInfoVO;
import org.apache.commons.collections.CollectionUtils;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.user.vo.UserInfoVO;
import org.apache.commons.collections.CollectionUtils;
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
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
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

        QueryBuilder queryTagId = QueryBuilders.termQuery(ESConstants.USER_TAG_ID, tagId);
        QueryBuilder queryTagOnLine = QueryBuilders.termQuery(ESConstants.USER_TAG_ONlINE, 10);
        QueryBuilder queryUserRole = QueryBuilders.termQuery(ESConstants.USER_ROLE, 11);
        SearchQuery query = null;

        //分页信息
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize,
                Sort.by(Direction.DESC, ESConstants.STAR_RECOMMEND_HEIGHT, ESConstants.STAR_RECOMMEND_TIME,
                        ESConstants.STAR_AUTHTIME, ESConstants.USER_LASTHEAT));
        if (userId != null) {
            //当前用户
            QueryBuilder queryMyself = QueryBuilders.termQuery(ESConstants.USER_ID_KEY, userId.toString());
            query = new NativeSearchQueryBuilder()
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

//        logger.debug("searchStarUser query: {}", GsonUtils.parseJson(query));
        return elasticsearchTemplate.queryForList(query, UserInfo.class);
    }

    @Override
    public PageList<UserInfoVO> adminSearchUser(AdminUserInfoDTO adminUserDTO) {
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
        String appId = adminUserDTO.getAppId();
        Integer userRole = adminUserDTO.getUserRole();
        Byte recommendStatus = adminUserDTO.getRecommendStatus();
        Set<Long> tagIds = adminUserDTO.getTagIds();

        String contactCall= adminUserDTO.getContactCall();
        String realName = adminUserDTO.getRealName();

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if(userRole!=null){
            /**
             * 判断普通用户，或者达人
             */
            if(userRole.intValue() == 10){
                //普通用户
                boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.USER_ROLE, userRole.intValue()));
            }else if(userRole.intValue() == 11){
                //达人  不要通过userRole查询，因为审核中或者取消审核会为普通用户，所以通过达人申请表所有状态查询
                BoolQueryBuilder starBoolQusery = QueryBuilders.boolQuery();
                starBoolQusery.should(QueryBuilders.matchQuery(ESConstants.STAR_AUDITSTATUS, 10));
                starBoolQusery.should(QueryBuilders.matchQuery(ESConstants.STAR_AUDITSTATUS, 11));
                starBoolQusery.should(QueryBuilders.matchQuery(ESConstants.STAR_AUDITSTATUS, 12));
                starBoolQusery.should(QueryBuilders.matchQuery(ESConstants.STAR_AUDITSTATUS, 13));
                boolQueryBuilder.must(starBoolQusery);
            }
        }
        if(!StringUtils.isBlank(contactCall)){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.STAR_CONTACTCALL, "*" + contactCall + "*"));
        }
        if(!StringUtils.isBlank(realName)){
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.STAR_REALNAME, "*" + realName + "*"));
        }
        if (StringUtils.isNotBlank(nickName)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.USER_NICKNAME, "*" + nickName + "*"));
        }
        if (StringUtils.isNotBlank(phone)) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery(ESConstants.USER_PHONE, "*" + phone + "*"));
        }
        if (StringUtils.isNotBlank(channelCode)) {
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
        if (recommendStatus!=null){
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.STAR_RECOMMENDSTATUS, recommendStatus));
        }
        if (growLevel != null) {
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.EVENT_GROWLEVEL, growLevel));
        }
        if(StringUtils.isNotBlank(appId)){
        	boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.USER_APPID, appId));
        }

        if (CollectionUtils.isNotEmpty(tagIds)) {
            BoolQueryBuilder tagBoolQusery = QueryBuilders.boolQuery();
            for (Long tagId : tagIds) {
                tagBoolQusery.should(QueryBuilders.matchQuery(ESConstants.USER_TAG_ID, tagId));
            }
            boolQueryBuilder.must(QueryBuilders.termQuery(ESConstants.USER_TAG_ONlINE, 10));
            boolQueryBuilder.must(tagBoolQusery);
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
        List<FieldSortBuilder> sortBuilders = getAdminUserSortBuilder(adminUserDTO);
        for (FieldSortBuilder sortBuilder : sortBuilders) {
            queryBuilder.withSort(sortBuilder);
        }

        SearchQuery query = queryBuilder.build();
//        logger.info("adminSearchUser query: {}", GsonUtils.parseJson(query));
        AggregatedPage<UserInfo> page = elasticsearchTemplate.queryForPage(query, UserInfo.class);
        List<UserInfo> userInfoList = page.getContent();
        List<UserInfoVO> userInfoVOS = Lists.newArrayList();
        if (CollectionUtils.isNotEmpty(userInfoList)) {
            userInfoVOS = GsonUtils.parseList(userInfoList, UserInfoVO.class);
        }
        PageList<UserInfoVO> pageList = new PageList<>();
        pageList.setCurrentPage(pageNo);
        pageList.setPageSize(pageSize);
        pageList.setEntities(userInfoVOS);
        pageList.setCount(page.getTotalElements());
        return pageList;
    }

    private List<FieldSortBuilder> getAdminUserSortBuilder(AdminUserInfoDTO adminUserDTO) {
        List<FieldSortBuilder> sortBuilders = new ArrayList<>();
        Integer userRole = adminUserDTO.getUserRole();
        if (userRole != null && userRole.equals(ESConstants.USER_ROLE_STAR)) {
            sortBuilders.add(SortBuilders.fieldSort(ESConstants.STAR_AUTHTIME).order(SortOrder.DESC));
        }
        sortBuilders.add(SortBuilders.fieldSort(ESConstants.USER_CREATEDATE).order(SortOrder.DESC));
        return sortBuilders;
    }


}
