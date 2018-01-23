package com.yryz.quanhu.support.activity.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.support.activity.constants.ActivityCandidateConstants;
import com.yryz.quanhu.support.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.support.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.support.activity.dao.ActivityVoteConfigDao;
import com.yryz.quanhu.support.activity.dao.ActivityVoteDetailDao;
import com.yryz.quanhu.support.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.service.ActivityCandidateService;
import com.yryz.quanhu.support.activity.service.ActivityVoteService;
import com.yryz.quanhu.support.activity.vo.ActivityVoteConfigVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteDetailVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ActivityCandidateServiceImpl implements ActivityCandidateService {

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    ActivityVoteService activityVoteService;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

    @Autowired
    ActivityVoteDetailDao activityVoteDetailDao;

    @Autowired
    RedisTemplateBuilder templateBuilder;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    public void join(ActivityVoteDto activityVoteDto) {
        //获取投票信息
        ActivityVoteInfoVo activityVoteInfoVo = activityVoteService.getVoteInfo(activityVoteDto.getActivityInfoId());
        //验证活动有效性
        this.validateActivity(activityVoteInfoVo);
        //获取用户是否参与
        int candidateCount = activityVoteDetailDao.selectCandidateCount(activityVoteDto.getActivityInfoId(),
                activityVoteDto.getCandidateId());
        if(candidateCount != 0) {
            throw QuanhuException.busiError("用户已参与");
        }
        //减少参与人数
        int flag = activityInfoDao.updateJoinCount(activityVoteDto.getActivityInfoId(), activityVoteInfoVo.getUserNum());
        if(flag == 0) {
            throw QuanhuException.busiError("参加人数已满");
        }


    }

    /**
     * 活动配置信息
     * @param   activityInfoId
     * @return
     * */
    public ActivityVoteConfigVo config(Long activityInfoId) {
        ActivityVoteConfigVo activityVoteConfigVo = new ActivityVoteConfigVo();
        Object configSources = stringRedisTemplate.opsForHash().get(ActivityVoteConstants.getKeyConfig(activityInfoId), "configSources");
        if(configSources == null) {
            //获取活动配置
            ActivityVoteConfig activityVoteConfig = activityVoteConfigDao.selectByActivityInfoId(activityInfoId);
            if(activityVoteConfig != null) {
                activityVoteService.setVoteConfig(null, activityVoteConfig);
                configSources = activityVoteConfig.getConfigSources();
            }
        }

        activityVoteConfigVo.setActivityInfoId(activityInfoId);
        activityVoteConfigVo.setConfigSources(configSources != null ? configSources.toString() : "");
        return activityVoteConfigVo;
    }

    /**
     * 获取参与者详情
     * @param   activityInfoId
     * @param   candidateId
     * @param   userId
     * @return
     * */
    public ActivityVoteDetailVo detail(Long activityInfoId, Long candidateId, Long userId) {
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        Double score = template.opsForZSet().score(ActivityCandidateConstants.getKeyRank(activityInfoId), candidateId);
        if(score == null) {
            return null;
        }
        Set<ZSetOperations.TypedTuple<Long>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>(candidateId, score));
        List<ActivityVoteDetailVo> list = this.getDetail(activityInfoId, set);
        ActivityVoteDetailVo detail = !CollectionUtils.isEmpty(list) ? list.get(0) : null;
        //TODO:需要补充信息
        return detail;
    }

    /**
     * 获取参与者列表
     * @param   activityVoteDto
     * @return
     * */
    public PageList<ActivityVoteDetailVo> list(ActivityVoteDto activityVoteDto) {
        PageList<ActivityVoteDetailVo> pageList = new PageList<>();
        if(StringUtils.isNotBlank(activityVoteDto.getQueryCondition())) {

        } else {
            String key = ActivityCandidateConstants.getKeyId(activityVoteDto.getActivityInfoId());
            if(!stringRedisTemplate.hasKey(key)) {
                this.setList(activityVoteDto.getActivityInfoId());
            }
            RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
            Set<ZSetOperations.TypedTuple<Long>> list = template.opsForZSet().reverseRangeWithScores(key,
                    (activityVoteDto.getCurrentPage() -1) * activityVoteDto.getPageSize(),
                    activityVoteDto.getPageSize());

            List<ActivityVoteDetailVo> resultList = this.getDetail(activityVoteDto.getActivityInfoId(), list);
            pageList.setCurrentPage(activityVoteDto.getCurrentPage());
            pageList.setPageSize(activityVoteDto.getPageSize());
            pageList.setEntities(resultList);
        }

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
        Set<ZSetOperations.TypedTuple<Long>> list = template.opsForZSet().reverseRangeWithScores(key,
                (activityVoteDto.getCurrentPage() -1) * activityVoteDto.getPageSize(),
                activityVoteDto.getPageSize());

        List<ActivityVoteDetailVo> rankList = this.getDetail(activityVoteDto.getActivityInfoId(), list);
        PageList<ActivityVoteDetailVo> pageList = new PageList<>();
        pageList.setCurrentPage(activityVoteDto.getCurrentPage());
        pageList.setPageSize(activityVoteDto.getPageSize());
        pageList.setEntities(rankList);

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
            List<ActivityVoteDetailVo> list = activityVoteDetailDao.batchVote(ids);
            if(!CollectionUtils.isEmpty(list)) {
                RedisTemplate<String, ActivityVoteDetailVo> template = templateBuilder.buildRedisTemplate(ActivityVoteDetailVo.class);
                Map<String, ActivityVoteDetailVo> map = new HashMap<>();
                list.stream()
                        .forEach(detailVo -> {
                    map.put(ActivityCandidateConstants.getKeyInfo(activityInfoId, detailVo.getKid()), detailVo);
                });
                template.opsForValue().multiSet(map);
            }

            return list;
        }

        return null;
    }

    private void setList(Long activityInfoId) {
        String key = ActivityCandidateConstants.getKeyId(activityInfoId);
        this.setCandidateInfo(key, activityInfoId, "kid");
    }

    private void setRank(Long activityInfoId) {
        String key = ActivityCandidateConstants.getKeyRank(activityInfoId);
        this.setCandidateInfo(key, activityInfoId, "voteCount");
    }

    private void setCandidateInfo(String key, Long activityInfoId, String sort) {
        Page<ActivityVoteDetailVo> page = PageHelper.startPage(ActivityCandidateConstants.CURRENTPAGE, ActivityCandidateConstants.PAGESIZE);
        List<ActivityVoteDetailVo> list = activityVoteDetailDao.selectVoteList(activityInfoId);
        if(!CollectionUtils.isEmpty(list)) {
            BigDecimal total = new BigDecimal(page.getTotal());
            BigDecimal pageSize = new BigDecimal(ActivityCandidateConstants.PAGESIZE);
            if(total.compareTo(pageSize) == 1) {
                int c = total.divide(pageSize).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();
                for(int i = 0; i<c; i++) {
                    PageHelper.startPage(i+2, ActivityCandidateConstants.PAGESIZE);
                    list.addAll(activityVoteDetailDao.selectVoteList(activityInfoId));
                }
            }
            Set<ZSetOperations.TypedTuple<Long>> set = new HashSet<>();
            list.stream()
                    .filter(detail -> detail.getKid() != null)
                    .forEach(detail -> {
                        double score = 0;
                        if("kid".equals(sort)) {
                            score = detail.getKid().doubleValue();
                        } else {
                            score = detail.getVoteCount() == null ? 0 : detail.getVoteCount().doubleValue();
                        }

                        ZSetOperations.TypedTuple tuple = new DefaultTypedTuple<>(detail.getKid(), score);
                        set.add(tuple);
                    });
            RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
            template.opsForZSet().add(key, set);
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
        if(now.compareTo(activityVoteInfoVo.getEndTime()) == -1) {
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

}
