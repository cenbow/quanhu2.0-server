package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.other.activity.constants.ActivityCandidateConstants;
import com.yryz.quanhu.other.activity.constants.ActivityPageConstants;
import com.yryz.quanhu.other.activity.constants.ActivityRedisConstants;
import com.yryz.quanhu.other.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.other.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteConfigDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteDetailDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteRecordDao;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.other.activity.service.ActivityCandidateService;
import com.yryz.quanhu.other.activity.service.ActivityVoteRedisService;
import com.yryz.quanhu.other.activity.service.ActivityVoteService;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteInfoVo;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ActivityCandidateServiceImpl implements ActivityCandidateService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityCandidateServiceImpl.class);

    @Reference(check = false, timeout = 30000)
    IdAPI idAPI;

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    ActivityVoteDetailDao activityVoteDetailDao;

    @Autowired
    ActivityVoteRecordDao activityVoteRecordDao;

    @Autowired
    ActivityVoteService activityVoteService;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

    @Reference(check = false, timeout = 30000)
    UserApi userApi;

    @Autowired
    ActivityVoteRedisService activityVoteRedisService;

    @Autowired
    RedisTemplateBuilder templateBuilder;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Reference(check = false, timeout = 30000)
    ScoreAPI scoreAPI;

    @Reference(check = false, timeout = 30000)
    ResourceDymaicApi resourceDymaicApi;

    /**
     * 增加参与者
     * @param activityVoteDto
     * */
    @Transactional(propagation = Propagation.REQUIRED)
    public void join(ActivityVoteDto activityVoteDto) {
        //获取投票信息
        ActivityVoteInfoVo activityVoteInfoVo = activityVoteService.getVoteInfo(activityVoteDto.getActivityInfoId());
        //验证活动有效性
        this.validateActivity(activityVoteInfoVo);
        //获取用户是否参与
        int candidateCount = activityVoteDetailDao.selectCandidateCount(activityVoteDto.getActivityInfoId(),
                activityVoteDto.getCreateUserId());
        if(candidateCount != 0) {
            throw QuanhuException.busiError("用户已参与");
        }
        //增加已参与人数
        int flag = activityInfoDao.updateJoinCount(activityVoteDto.getActivityInfoId(), activityVoteInfoVo.getUserNum());
        if(flag == 0) {
            throw QuanhuException.busiError("参加人数已满");
        }
        ActivityVoteDetail voteDetail = new ActivityVoteDetail();
        Response<Long> result = idAPI.getSnowflakeId();
        if(!result.success()){
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        voteDetail.setKid(result.getData());
        voteDetail.setActivityInfoId(activityVoteDto.getActivityInfoId());
        voteDetail.setVoteNo(activityVoteRedisService.getMaxVoteNo(activityVoteDto.getActivityInfoId()).intValue());
        voteDetail.setObtainIntegral(activityVoteInfoVo.getAmount());
        voteDetail.setContent(activityVoteDto.getContent());
        voteDetail.setContent1(activityVoteDto.getContent1());
        voteDetail.setContent2(activityVoteDto.getContent2());
        voteDetail.setCoverPlan(activityVoteDto.getCoverPlan());
        voteDetail.setImgUrl(activityVoteDto.getImgUrl());
        voteDetail.setVideoUrl(activityVoteDto.getVideoUrl());
        voteDetail.setVideoThumbnailUrl(activityVoteDto.getVideoThumbnailUrl());
        voteDetail.setVoteCount(0);
        voteDetail.setAddVote(0);
        voteDetail.setCreateUserId(activityVoteDto.getCreateUserId());
        voteDetail.setModuleEnum(ModuleContants.ACTIVITY_WORKS_ENUM);
        voteDetail.setShelveFlag(10);
        //保存参与信息
        activityVoteDetailDao.insertByPrimaryKeySelective(voteDetail);
        activityVoteDto.setKid(voteDetail.getKid());
        //进入资源库
        this.setResource(voteDetail, activityVoteInfoVo);
        //增加参与者
        activityVoteRedisService.addCandidate(activityVoteDto.getActivityInfoId(),
                voteDetail.getKid(), voteDetail.getId(), voteDetail.getVoteCount().longValue());
        //设置平台积分
        if(activityVoteInfoVo.getAmount() != null && activityVoteInfoVo.getAmount() != 0) {
            try {
                scoreAPI.addScore(activityVoteDto.getCreateUserId().toString(), activityVoteInfoVo.getAmount(), "37");
            } catch (Exception e) {
                logger.error("增加活动积分 失败", e);
            }
        }
    }

    /**
     * 获取参与者详情
     * @param   activityVoteDto
     * @return
     * */
    public ActivityVoteDetailVo detail(ActivityVoteDto activityVoteDto) {
        Set<ZSetOperations.TypedTuple<Long>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>(activityVoteDto.getCandidateId(), 0d));
        List<ActivityVoteDetailVo> list = this.getDetail(activityVoteDto.getActivityInfoId(), set);
        ActivityVoteDetailVo detail = !CollectionUtils.isEmpty(list) ? list.get(0) : null;
        if(detail != null) {
            RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
            if(!template.hasKey(ActivityCandidateConstants.getKeyRank(activityVoteDto.getActivityInfoId()))) {
                this.setRank(activityVoteDto.getActivityInfoId());
            }
            //设置票数
            detail.setVoteCount(this.getScore(activityVoteDto.getActivityInfoId(),
                    activityVoteDto.getCandidateId(), template));
            //设置前后排名
            this.setVoteDiffer(activityVoteDto.getActivityInfoId(),
                    activityVoteDto.getCandidateId(), detail, template);
            //用户是否有可用投票卷
            detail.setUserRollFlag(activityVoteDto.getCreateUserId() != null ?
                    activityVoteService.selectUserRoll(activityVoteDto.getCreateUserId()) : 10);
            //活动主信息相关
            ActivityVoteInfoVo activityVoteInfoVo = activityVoteService.getVoteInfo(activityVoteDto.getActivityInfoId());
            if(activityVoteInfoVo != null){
                detail.setInAppVoteType(activityVoteInfoVo.getInAppVoteType());
                detail.setInAppVoteConfigCount(activityVoteInfoVo.getInAppVoteConfigCount());
                detail.setOtherAppVoteType(activityVoteInfoVo.getOtherAppVoteType());
                detail.setOtherAppVoteConfigCount(activityVoteInfoVo.getOtherAppVoteConfigCount());
                detail.setCommentFlag(activityVoteInfoVo.getCommentFlag());
                detail.setActivityTitle(activityVoteInfoVo.getTitle());
                //获取投票类型
                Integer voteType = ActivityVoteConstants.IN_APP.equals(activityVoteDto.getOtherFlag())
                        ? detail.getInAppVoteType() : detail.getOtherAppVoteType();
                if(voteType != null) {
                    if(activityVoteDto.getCreateUserId() != null) {
                        //用户的投票数
                        int count = activityVoteRecordDao.voteRecordCount(activityVoteDto.getActivityInfoId(),
                                activityVoteDto.getCreateUserId(),
                                activityVoteDto.getOtherFlag(),
                                ActivityVoteConstants.FIXED_VOTE_TYPE.equals(voteType) ? "fixed" : "event");
                        if(ActivityVoteConstants.IN_APP.equals(activityVoteDto.getOtherFlag())) {
                            detail.setInAppVoteCount(count);
                        } else {
                            detail.setOtherAppVoteCount(count);
                        }
                    }
                }
            }
            //设置用户信息
            this.setUserInfo(list);
        }

        return detail;
    }

    /**
     * 获取参与者列表
     * @param   activityVoteDto
     * @return
     * */
    public PageList<ActivityVoteDetailVo> list(ActivityVoteDto activityVoteDto) {
        List<ActivityVoteDetailVo> resultList = null;
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        if(activityVoteDto.getQueryCondition() != null) {
            List<ActivityVoteDetailVo> list = activityVoteDetailDao.selectVoteList(activityVoteDto.getActivityInfoId(),
                    activityVoteDto.getQueryCondition());
            if(!CollectionUtils.isEmpty(list)) {
                Set<ZSetOperations.TypedTuple<Long>> set = new HashSet<>();
                set.add(new DefaultTypedTuple<>(list.get(0).getKid(), new Double(0)));
                resultList = this.getDetail(activityVoteDto.getActivityInfoId(), set);
            }
        } else {
            String key = ActivityCandidateConstants.getKeyId(activityVoteDto.getActivityInfoId());
            if(!stringRedisTemplate.hasKey(key)) {
                this.setList(activityVoteDto.getActivityInfoId());
                this.setRank(activityVoteDto.getActivityInfoId());
            }
            long pageNo = ActivityPageConstants.getCurrentPage(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
            long pageSize = ActivityPageConstants.getPageSize(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
            Set<ZSetOperations.TypedTuple<Long>> list = template.opsForZSet().reverseRangeWithScores(key, pageNo, pageSize);
            resultList = this.getDetail(activityVoteDto.getActivityInfoId(), list);
        }
        //封装数据
        this.sealInfo(resultList, activityVoteDto, template);
        PageList<ActivityVoteDetailVo> pageList = new PageList<>();
        pageList.setCurrentPage(activityVoteDto.getCurrentPage());
        pageList.setPageSize(activityVoteDto.getPageSize());
        pageList.setEntities(resultList);
        pageList.setCount(0L);

        return pageList;
    }

    /**
     * 排行榜
     * @param   activityVoteDto
     * @return
     * */
    public PageList<ActivityVoteDetailVo> rank(ActivityVoteDto activityVoteDto) {
        String key = ActivityCandidateConstants.getKeyRank(activityVoteDto.getActivityInfoId());
        if(!stringRedisTemplate.hasKey(key)) {
            this.setRank(activityVoteDto.getActivityInfoId());
        }
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        long pageNo = ActivityPageConstants.getCurrentPage(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
        long pageSize = ActivityPageConstants.getPageSize(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
        Set<ZSetOperations.TypedTuple<Long>> list = template.opsForZSet().reverseRangeWithScores(key, pageNo, pageSize);
        List<ActivityVoteDetailVo> rankList = this.getDetail(activityVoteDto.getActivityInfoId(), list);
        //封装数据
        this.sealInfo(rankList, activityVoteDto, template);
        PageList<ActivityVoteDetailVo> pageList = new PageList<>();
        pageList.setCurrentPage(activityVoteDto.getCurrentPage());
        pageList.setPageSize(activityVoteDto.getPageSize());
        pageList.setEntities(rankList);
        pageList.setCount(0L);

        return pageList;
    }

    private List<ActivityVoteDetailVo> getDetail(Long activityInfoId, Set<ZSetOperations.TypedTuple<Long>> ids) {
        if(activityInfoId != null && !ids.isEmpty()) {
            RedisTemplate<String, ActivityVoteDetailVo> template = templateBuilder.buildRedisTemplate(ActivityVoteDetailVo.class);
            Set<String> set = new HashSet<>();
            ids.stream().forEach(id -> set.add(ActivityCandidateConstants.getKeyInfo(activityInfoId, id.getValue())));

            List<ActivityVoteDetailVo> list = template.opsForValue().multiGet(set);
            if(!CollectionUtils.isEmpty(list)) {
                List<Long> candidateIds = new ArrayList<>();
                candidateIds.addAll(ids.stream().map(id -> id.getValue()).collect(Collectors.toList()));
                list.stream()
                        .filter(detailVo -> detailVo != null)
                        .filter(detailVo -> candidateIds.contains(detailVo.getKid()))
                        .forEach(detailVo -> candidateIds.remove(detailVo.getKid()));
                if(!candidateIds.isEmpty()) {
                    list.addAll(this.setDetail(activityInfoId, candidateIds));
                }
                Map<Long, ActivityVoteDetailVo> map = new HashMap<>();
                list.stream()
                        .filter(detailVo -> detailVo != null)
                        .forEach(detailVo -> {
                            map.put(detailVo.getKid(), detailVo);
                });
                list = ids.stream()
                        .filter(id -> map.get(id.getValue()) != null)
                        .map(id -> {
                            ActivityVoteDetailVo detailVo = map.get(id.getValue());
                            detailVo.setVoteCount(id.getScore() == null ? 0 : id.getScore().intValue());
                            return detailVo;
                        })
                        .collect(Collectors.toList());
            }

            return list;
        }

        return null;
    }

    private List<ActivityVoteDetailVo> setDetail(Long activityInfoId, List<Long> ids) {
        if(!ids.isEmpty()) {
            List<ActivityVoteDetailVo> list = activityVoteDetailDao.batchVote(activityInfoId, ids);
            if(!CollectionUtils.isEmpty(list)) {
                RedisTemplate<String, ActivityVoteDetailVo> template = templateBuilder.buildRedisTemplate(ActivityVoteDetailVo.class);
//                Map<String, ActivityVoteDetailVo> map = new HashMap<>();
                list.stream()
                        .forEach(detailVo -> {
//                    map.put(ActivityCandidateConstants.getKeyInfo(activityInfoId, detailVo.getKid()), detailVo);
                    template.opsForValue().set(ActivityCandidateConstants.getKeyInfo(activityInfoId, detailVo.getKid()),
                            detailVo, ActivityRedisConstants.TIMEOUT_VERY_LONG, TimeUnit.SECONDS);
                });
//                template.opsForValue().multiSet(map);
            }

            return list;
        }

        return null;
    }

    /**
     * 设置参与者列表
     * @param   activityInfoId
     * */
    public void setList(Long activityInfoId) {
        List<ActivityVoteDetailVo> activityList = this.getCandidateInfo(activityInfoId);
        this.setCandidateInfo(ActivityCandidateConstants.getKeyId(activityInfoId), "kid", activityList);
        this.setCandidateInfo(ActivityCandidateConstants.getKeyRank(activityInfoId),  "voteCount", activityList);
    }

    /**
     * 设置参与者排名
     * @param   activityInfoId
     * */
    public void setRank(Long activityInfoId) {
        List<ActivityVoteDetailVo> activityList = this.getCandidateInfo(activityInfoId);
        this.setCandidateInfo(ActivityCandidateConstants.getKeyRank(activityInfoId),  "voteCount", activityList);
    }

    private List<ActivityVoteDetailVo> getCandidateInfo(Long activityInfoId) {
        Page<ActivityVoteDetailVo> page = PageHelper.startPage(ActivityCandidateConstants.CURRENTPAGE, ActivityCandidateConstants.PAGESIZE);
        List<ActivityVoteDetailVo> list = activityVoteDetailDao.selectVoteList(activityInfoId, null);
        if(!CollectionUtils.isEmpty(list)) {
            BigDecimal total = new BigDecimal(page.getTotal());
            BigDecimal pageSize = new BigDecimal(ActivityCandidateConstants.PAGESIZE);
            if (total.compareTo(pageSize) == 1) {
                int c = total.divide(pageSize).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                for (int i = 0; i < c; i++) {
                    PageHelper.startPage(i + 2, ActivityCandidateConstants.PAGESIZE);
                    list.addAll(activityVoteDetailDao.selectVoteList(activityInfoId, null));
                }
            }
        }

        return list;
    }

    private void setCandidateInfo(String key, String sort, List<ActivityVoteDetailVo> list) {
        if(!CollectionUtils.isEmpty(list)) {
            Set<ZSetOperations.TypedTuple<Long>> set = new HashSet<>();
            list.stream()
                    .filter(detail -> detail.getKid() != null)
                    .forEach(detail -> {
                        long value = 0;
                        double score = 0;
                        if("kid".equals(sort)) {
                            value = detail.getKid();
                            score = detail.getId().doubleValue();
                        } else {
                            value = detail.getKid();
                            score = detail.getVoteCount() == null ? 0 : detail.getVoteCount().doubleValue();
                        }

                        ZSetOperations.TypedTuple tuple = new DefaultTypedTuple<>(value, score);
                        set.add(tuple);
                    });
            RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
            template.opsForZSet().add(key, set);
            template.expire(key, ActivityRedisConstants.TIMEOUT_VERY_LONG, TimeUnit.SECONDS);
        }
    }

    private void setUserInfo(List<ActivityVoteDetailVo> list) {
        Set<String> set = new HashSet<>();
        list.stream()
                .filter(detailVo -> detailVo.getCreateUserId() != null)
                .forEach(detailVo -> set.add(detailVo.getCreateUserId().toString()));
        try {
            Response<Map<String, UserSimpleVO>> result = userApi.getUserSimple(set);
            if(result.success()) {
                Map<String, UserSimpleVO> simple = result.getData();
                list.stream()
                        .filter(detailVo -> detailVo.getCreateUserId() != null)
                        .forEach(detailVo -> detailVo.setUser(simple.get(detailVo.getCreateUserId().toString())));
            }
        } catch (Exception e) {
            logger.error("获取用户信息 失败", e);
        }
    }

    private void validateActivity(ActivityVoteInfoVo activityVoteInfoVo) {
        if(activityVoteInfoVo == null) {
            throw QuanhuException.busiError("活动已关闭或不存在");
        }
        Date now = new Date();
        if(now.compareTo(activityVoteInfoVo.getBeginTime()) == -1) {
            throw QuanhuException.busiError("活动未开始");
        }
        if(now.compareTo(activityVoteInfoVo.getEndTime()) == 1) {
            throw QuanhuException.busiError("活动已结束");
        }
        if(!Integer.valueOf(10).equals(activityVoteInfoVo.getShelveFlag()) ) {
            throw QuanhuException.busiError("活动已下线");
        }
        if(now.compareTo(activityVoteInfoVo.getActivityJoinBegin()) == -1 ) {
            throw QuanhuException.busiError("该活动还未进入参与阶段");
        }
        if(now.compareTo(activityVoteInfoVo.getActivityJoinEnd()) == 1 ) {
            throw QuanhuException.busiError("该活动参与阶段已结束");
        }
    }

    private void setVoteDiffer(Long activityInfoId, Long candidateId ,ActivityVoteDetailVo detail, RedisTemplate<String, Long> template) {
        //获取前后排名
        Long rank = template.opsForZSet().reverseRank(ActivityCandidateConstants.getKeyRank(activityInfoId), candidateId);
        if(rank != null) {
            Set<ZSetOperations.TypedTuple<Long>> rankList = template.opsForZSet().reverseRangeWithScores(ActivityCandidateConstants.getKeyRank(activityInfoId),
                    rank == 0 ? 0 : rank -1,
                    rank +1);
            if(!CollectionUtils.isEmpty(rankList)) {
                //前一名
                Integer frontVoteDiffer = 0;
                //后一名
                Integer afterVoteDiffer = 0;
                List<ZSetOperations.TypedTuple<Long>> differ = new ArrayList<>();
                differ.addAll(rankList);
                if(differ.size() == 2) {
                    int zero = differ.get(0).getScore() == null ? 0 : differ.get(0).getScore().intValue();
                    int one = differ.get(1).getScore() == null ? 0 : differ.get(1).getScore().intValue();
                    if(candidateId.equals(differ.get(0).getValue())) {
                        afterVoteDiffer = (zero - one) + 1;
                    } else {
                        frontVoteDiffer = (one - zero) + 1;
                    }
                } else if(differ.size() == 3) {
                    int zero = differ.get(0).getScore() == null ? 0 : differ.get(0).getScore().intValue();
                    int one = differ.get(1).getScore() == null ? 0 : differ.get(1).getScore().intValue();
                    int two = differ.get(2).getScore() == null ? 0 : differ.get(2).getScore().intValue();
                    frontVoteDiffer = (zero - one) + 1;
                    afterVoteDiffer = (one - two) + 1;
                }
                detail.setFrontVoteDiffer(frontVoteDiffer > 0 ? frontVoteDiffer : 0);
                detail.setAfterVoteDiffer(afterVoteDiffer > 0 ? afterVoteDiffer : 0);
            }
        }
    }

    private int getScore(Long activityInfoId, Long candidateId, RedisTemplate<String, Long> template) {
        Double score = template.opsForZSet().score(ActivityCandidateConstants.getKeyRank(activityInfoId), candidateId);
        return score == null ? 0 : score.intValue();
    }

    private void sealInfo(List<ActivityVoteDetailVo> resultList, ActivityVoteDto activityVoteDto, RedisTemplate<String, Long> template) {
        if(!CollectionUtils.isEmpty(resultList)) {
            //用户是否有可用投票卷
            int userRoll = activityVoteDto.getCreateUserId() != null ?
                    activityVoteService.selectUserRoll(activityVoteDto.getCreateUserId()) : 10;
            //活动主信息相关
            ActivityVoteInfoVo activityVoteInfoVo = activityVoteService.getVoteInfo(activityVoteDto.getActivityInfoId());

            Integer inAppVoteType = activityVoteInfoVo != null ? activityVoteInfoVo.getInAppVoteType() : new Integer(0);
            Integer inAppVoteConfigCount = activityVoteInfoVo != null ? activityVoteInfoVo.getInAppVoteConfigCount() : new Integer(0);
            Integer otherAppVoteType = activityVoteInfoVo != null ? activityVoteInfoVo.getOtherAppVoteType() : new Integer(0);
            Integer otherAppVoteConfigCount = activityVoteInfoVo != null ? activityVoteInfoVo.getOtherAppVoteConfigCount() : new Integer(0);
            Integer inAppVoteCount = null;
            Integer otherAppVoteCount = null;
            if(activityVoteInfoVo != null && activityVoteDto.getCreateUserId() != null){
                //获取投票类型
                Integer voteType = ActivityVoteConstants.IN_APP.equals(activityVoteDto.getOtherFlag())
                        ? activityVoteInfoVo.getInAppVoteType() : activityVoteInfoVo.getOtherAppVoteType();
                if(voteType != null) {
                    //用户的投票数
                    int count = activityVoteRecordDao.voteRecordCount(activityVoteDto.getActivityInfoId(),
                            activityVoteDto.getCreateUserId(),
                            activityVoteDto.getOtherFlag(),
                            ActivityVoteConstants.FIXED_VOTE_TYPE.equals(voteType) ? "fixed" : "event");
                    if(ActivityVoteConstants.IN_APP.equals(activityVoteDto.getOtherFlag())) {
                        inAppVoteCount = count;
                    } else {
                        otherAppVoteCount = count;
                    }
                }
            }
            //组装参数
            for (ActivityVoteDetailVo detailVo : resultList) {
                Double score = template.opsForZSet().score(ActivityCandidateConstants.getKeyRank(detailVo.getActivityInfoId()),
                        detailVo.getKid());
                detailVo.setVoteCount(score == null ? 0 : score.intValue());
                detailVo.setUserRollFlag(userRoll);
                detailVo.setInAppVoteType(inAppVoteType);
                detailVo.setInAppVoteConfigCount(inAppVoteConfigCount);
                detailVo.setOtherAppVoteType(otherAppVoteType);
                detailVo.setOtherAppVoteConfigCount(otherAppVoteConfigCount);
                detailVo.setInAppVoteCount(inAppVoteCount);
                detailVo.setOtherAppVoteCount(otherAppVoteCount);
            }
            this.setUserInfo(resultList);
        }
    }

    private void setResource(ActivityVoteDetail voteDetail, ActivityVoteInfoVo activityVoteInfoVo) {
        try {
            ResourceTotal resourceTotal = new ResourceTotal();
            resourceTotal.setContent(voteDetail.getContent());
            resourceTotal.setCreateDate(DateUtils.getString(new Date()));
            resourceTotal.setExtJson(JsonUtils.toFastJson(voteDetail));
            resourceTotal.setModuleEnum(new Integer(voteDetail.getModuleEnum()));
            resourceTotal.setPublicState(ResourceEnum.PUBLIC_STATE_TRUE);
            resourceTotal.setResourceId(voteDetail.getKid());

            Response<UserSimpleVO> userSimple = userApi.getUserSimple(voteDetail.getCreateUserId());
            if(userSimple.success()
                    && userSimple.getData() != null
                    && userSimple.getData().getUserRole() != null) {
                resourceTotal.setTalentType(String.valueOf(userSimple.getData().getUserRole()));
            }

            resourceTotal.setTitle(activityVoteInfoVo.getTitle());
            resourceTotal.setUserId(voteDetail.getCreateUserId());
            resourceDymaicApi.commitResourceDymaic(resourceTotal);
        } catch (Exception e) {
            logger.error("资源聚合 接入异常！", e);
        }
    }

}
