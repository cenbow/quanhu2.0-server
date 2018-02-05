package com.yryz.quanhu.other.activity.service;

import org.springframework.data.redis.core.RedisTemplate;

public interface ActivityVoteRedisService {

    /**
     * 增加参与者
     * @param   activityInfoId      活动id
     * @param   detailKid            参与者kid
     * @param   detailId             参与者id
     * @param   totalCount           总票数
     * */
    void addCandidate(Long activityInfoId, Long detailKid, Long detailId, Long totalCount);

    /**
     * 上架参与者
     * @param   activityInfoId      活动id
     * @param   detailKid            参与者kid
     * @param   detailId             参与者id
     * @param   totalCount           总票数
     * @param   template
     * */
    void shelvesCandidate(Long activityInfoId, Long detailKid, Long detailId, Long totalCount, RedisTemplate<String, Long> template);

    /**
     * 删除参与者
     * @param   activityInfoId  活动id
     * @param   detailKid        参与者kid
     * */
    void remCandidate(Long activityInfoId, Long detailKid);

    /**
     * 删除投票活动详情信息
     * @param   activityInfoId
     * */
    void delVoteInfo(Long activityInfoId);

    /**
     * 投票递增票数
     * @param   activityInfoId  活动id
     * @param   detailKid        参与者kid
     * @param   vote              递增票数
     * */
    void vote(Long activityInfoId, Long detailKid, double vote);

    /**
     * 生成参与者编号
     * @param   activityInfoId
     * @return
     * */
    Long getMaxVoteNo(Long activityInfoId);

}
