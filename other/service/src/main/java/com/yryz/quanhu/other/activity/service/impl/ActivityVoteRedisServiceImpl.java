package com.yryz.quanhu.other.activity.service.impl;

import com.yryz.common.exception.QuanhuException;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.other.activity.constants.ActivityCandidateConstants;
import com.yryz.quanhu.other.activity.constants.ActivityRedisConstants;
import com.yryz.quanhu.other.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.other.activity.dao.ActivityVoteDetailDao;
import com.yryz.quanhu.other.activity.service.ActivityCandidateService;
import com.yryz.quanhu.other.activity.service.ActivityVoteRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class ActivityVoteRedisServiceImpl implements ActivityVoteRedisService {

    @Autowired
    ActivityVoteDetailDao activityVoteDetailDao;

    @Autowired
    ActivityCandidateService activityCandidateService;

    @Autowired
    RedisTemplateBuilder templateBuilder;

    @Autowired
    StringRedisTemplate stringRedisTemplate;


    /**
     * 增加参与者
     * @param   activityInfoId      活动id
     * @param   detailKid            参与者kid
     * @param   detailId             参与者id
     * @param   totalCount           总票数
     * */
    public void addCandidate(Long activityInfoId, Long detailKid, Long detailId, Long totalCount) {
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        //递增参与人数
        stringRedisTemplate.opsForHash().increment(ActivityVoteConstants.getKeyConfig(activityInfoId),
                "joinCount", 1d);
        this.shelvesCandidate(activityInfoId, detailKid, detailId, totalCount, template);
    }

    /**
     * 上架参与者
     * @param   activityInfoId      活动id
     * @param   detailKid            参与者kid
     * @param   detailId             参与者id
     * @param   totalCount           总票数
     * @param   template
     * */
    public void shelvesCandidate(Long activityInfoId, Long detailKid, Long detailId, Long totalCount, RedisTemplate<String, Long> template) {
        if(template == null) {
            template = templateBuilder.buildRedisTemplate(Long.class);
        }
        //增加首页列表
        String keyId = ActivityCandidateConstants.getKeyId(activityInfoId);
        if(!template.hasKey(keyId)) {
            activityCandidateService.setList(activityInfoId);
        }
        template.opsForZSet().add(keyId,
                detailKid,
                detailId);
        //增加排行榜列表
        String keyRank = ActivityCandidateConstants.getKeyRank(activityInfoId);
        template.opsForZSet().add(keyRank,
                detailKid,
                totalCount);
    }

    /**
     * 删除参与者
     * @param   activityInfoId  活动id
     * @param   detailKid        参与者kid
     * */
    public void remCandidate(Long activityInfoId, Long detailKid) {
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        //增加首页列表
        String keyId = ActivityCandidateConstants.getKeyId(activityInfoId);
        if(!template.hasKey(keyId)) {
            activityCandidateService.setList(activityInfoId);
        }
        template.opsForZSet().remove(keyId, detailKid);
        //增加排行榜列表
        String keyRank = ActivityCandidateConstants.getKeyRank(activityInfoId);
        template.opsForZSet().remove(keyRank, detailKid);
    }

    /**
     * 删除投票活动详情信息
     * @param   activityInfoId
     * */
    public void delVoteInfo(Long activityInfoId) {
        String keyInfo = ActivityVoteConstants.getKeyInfo(activityInfoId);
        String keyConfig = ActivityVoteConstants.getKeyConfig(activityInfoId);
        stringRedisTemplate.delete(keyInfo);
        stringRedisTemplate.delete(keyConfig);
    }

    /**
     * 投票递增票数
     * @param   activityInfoId  活动id
     * @param   detailKid        参与者kid
     * @param   vote              递增票数
     * */
    public void vote(Long activityInfoId, Long detailKid, double vote) {
        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        String keyRank = ActivityCandidateConstants.getKeyRank(activityInfoId);
        if(!template.hasKey(keyRank)) {
            activityCandidateService.setRank(activityInfoId);
        }
        template.opsForZSet().incrementScore(keyRank,
                detailKid,
                vote);
    }

    /**
     * 生成参与者编号
     * @param   activityInfoId
     * @return
     * */
    public Long getMaxVoteNo(Long activityInfoId) {
        if(!stringRedisTemplate.opsForHash().hasKey(ActivityVoteConstants.ACTIVITY_VOTE_NO, activityInfoId.toString())) {
            Integer maxVoteNo = activityVoteDetailDao.selectMaxVoteNo(activityInfoId);
            stringRedisTemplate.opsForHash().putIfAbsent(ActivityVoteConstants.ACTIVITY_VOTE_NO,
                    activityInfoId.toString(),
                    maxVoteNo == null ? "0" : String.valueOf(maxVoteNo));
            stringRedisTemplate.expire(ActivityVoteConstants.ACTIVITY_VOTE_NO,
                    ActivityRedisConstants.TIMEOUT_VERY_LONG, TimeUnit.SECONDS);
        }
        Long voteNo = stringRedisTemplate.opsForHash().increment(ActivityVoteConstants.ACTIVITY_VOTE_NO, activityInfoId.toString(), 1);
        if(voteNo == null) {
            throw QuanhuException.busiError("生成参与者编号失败");
        }

        return voteNo;
    }

}
