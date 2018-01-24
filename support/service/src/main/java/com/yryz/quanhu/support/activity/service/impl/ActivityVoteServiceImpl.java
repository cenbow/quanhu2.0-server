package com.yryz.quanhu.support.activity.service.impl;

import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.support.activity.constants.ActivityCandidateConstants;
import com.yryz.quanhu.support.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.support.activity.dao.*;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.support.activity.service.ActivityVoteService;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class ActivityVoteServiceImpl implements ActivityVoteService {

    private Logger logger = LoggerFactory.getLogger(ActivityVoteServiceImpl.class);

    @Autowired
    IdAPI idAPI;

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

    @Autowired
    ActivityVoteDetailDao activityVoteDetailDao;

    @Autowired
    ActivityUserPrizesDao activityUserPrizesDao;

    @Autowired
    ActivityVoteRecordDao activityVoteRecordDao;

    @Autowired
    RedisTemplateBuilder templateBuilder;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    /**
     *  投票活动主信息
     *  @param kid
     *  @param userId
     *  @return
     * */
    public ActivityVoteInfoVo detail(Long kid, Long userId) {
        ActivityVoteInfoVo activityInfoVo = this.getVoteInfo(kid);
        if(activityInfoVo != null) {
            //TODO:用户是否参与此活动
            //TODO:判断用户是否禁言
            //设置已参与人数
            Object joinCount = stringRedisTemplate.opsForHash().get(ActivityVoteConstants.getKeyConfig(activityInfoVo.getKid()), "joinCount");
            activityInfoVo.setJoinCount( joinCount != null ? Integer.valueOf(joinCount.toString()) : 0);
            Date now = new Date();
            activityInfoVo.setCurrentDate(now);
            //活动未开始
            if(now.compareTo(activityInfoVo.getBeginTime()) == -1){
                activityInfoVo.setActivityStatus(ActivityVoteConstants.ACTIVITY_STATUS_NOSTART);
            }
            //活动已结束
            else if(now.compareTo(activityInfoVo.getEndTime()) == 1){
                activityInfoVo.setActivityStatus(ActivityVoteConstants.ACTIVITY_STATUS_OVER);
            }
            //活动进行中
            else if(now.compareTo(activityInfoVo.getBeginTime()) == 1 && now.compareTo(activityInfoVo.getEndTime()) == -1){
                activityInfoVo.setActivityStatus(ActivityVoteConstants.ACTIVITY_STATUS_PROCESSING);
            }
        }

        return activityInfoVo;
    }

    /**
     * 设置投票详细信息
     * @param   joinCount
     * @param   activityVoteConfig
     * */
    public void setVoteConfig(Integer joinCount, ActivityVoteConfig activityVoteConfig) {
        Map<String, String> map = new HashMap<>();
        if(joinCount != null) {
            map.put("joinCount", joinCount.toString());//当前活动已参加的人员数量
        }
        if(activityVoteConfig.getUserNum() != null) {
            map.put("userNum", activityVoteConfig.getUserNum().toString());//参与人数上限
        }
        if(StringUtils.isNotBlank(activityVoteConfig.getNoRewardContent())) {
            map.put("noRewardContent", activityVoteConfig.getNoRewardContent());
        }
        if(StringUtils.isNotBlank(activityVoteConfig.getConfigSources())) {
            map.put("configSources", activityVoteConfig.getConfigSources());
        }
        if(activityVoteConfig.getInAppVoteConfigCount() != null) {
            map.put("inAppVoteConfigCount", activityVoteConfig.getInAppVoteConfigCount().toString());
        }
        if(activityVoteConfig.getInAppVoteType() != null) {
            map.put("inAppVoteType", activityVoteConfig.getInAppVoteType().toString());
        }
        if(activityVoteConfig.getOtherAppVoteConfigCount() != null) {
            map.put("otherAppVoteConfigCount", activityVoteConfig.getOtherAppVoteConfigCount().toString());
        }
        if(activityVoteConfig.getOtherAppVoteType() != null) {
            map.put("otherAppVoteType", activityVoteConfig.getOtherAppVoteType().toString());
        }

        stringRedisTemplate.opsForHash().putAll(ActivityVoteConstants.getKeyConfig(activityVoteConfig.getActivityInfoId()), map);
    }

    /**
     * 获取投票活动详情
     * @param kid
     * @return
     * */
    public ActivityVoteInfoVo getVoteInfo(Long kid) {
        RedisTemplate<String, ActivityVoteInfoVo> template = templateBuilder.buildRedisTemplate(ActivityVoteInfoVo.class);
        ActivityVoteInfoVo activityVoteInfoVo = template.opsForValue().get(ActivityVoteConstants.getKeyInfo(kid));
        if(activityVoteInfoVo == null) {
            //获取活动主信息
            activityVoteInfoVo = activityInfoDao.selectVoteByKid(kid);
            //获取活动配置信息
            ActivityVoteConfig activityVoteConfig = activityVoteConfigDao.selectByActivityInfoId(kid);
            if(activityVoteInfoVo != null && activityVoteConfig != null) {
                activityVoteInfoVo.setActivityJoinBegin(activityVoteConfig.getActivityJoinBegin());
                activityVoteInfoVo.setActivityJoinEnd(activityVoteConfig.getActivityJoinEnd());
                activityVoteInfoVo.setActivityVoteBegin(activityVoteConfig.getActivityVoteBegin());
                activityVoteInfoVo.setActivityVoteEnd(activityVoteConfig.getActivityVoteEnd());
                activityVoteInfoVo.setPrizesFlag(activityVoteConfig.getPrizesFlag() == null ? 10 : activityVoteConfig.getPrizesFlag());//是否有奖品
                activityVoteInfoVo.setAmount(activityVoteConfig.getAmount());
                activityVoteInfoVo.setUserFlag(activityVoteConfig.getUserFlag() == null ? 10 : activityVoteConfig.getUserFlag());//用户是否可参与(收禁言规则影响)
                activityVoteInfoVo.setUserNum(activityVoteConfig.getUserNum() == null ? 0 : activityVoteConfig.getUserNum());//参与人数上限
                template.opsForValue().set(ActivityVoteConstants.getKeyInfo(activityVoteInfoVo.getKid()), activityVoteInfoVo);

                this.setVoteConfig(activityVoteInfoVo.getJoinCount() == null ? 0 : activityVoteInfoVo.getJoinCount(), activityVoteConfig);
            }
        }

        return activityVoteInfoVo;
    }

    /**
     *  确认投票
     *  @param  record
     *  @return
     * */
    @Transactional(propagation = Propagation.REQUIRED)
    public int voteRecord(ActivityVoteRecord record) {
        ActivityVoteInfoVo activityVoteInfoVo = this.getVoteInfo(record.getActivityInfoId());
        //相关验证规则
        this.validateActivity(activityVoteInfoVo);
        //获取活动配置信息
        ActivityVoteConfig activityVoteConfig = activityVoteConfigDao.selectByActivityInfoId(activityVoteInfoVo.getKid());
        if(activityVoteConfig == null) {
            throw QuanhuException.busiError("活动已关闭或不存在");
        }
        Integer voteConfig = null;
        Integer voteType = null;
        if(ActivityVoteConstants.IN_APP.equals(record.getOtherFlag())) {
            voteConfig = activityVoteConfig.getInAppVoteConfigCount();
            voteType = activityVoteConfig.getInAppVoteType();
        } else if(ActivityVoteConstants.OTHER_APP.equals(record.getOtherFlag())) {
            voteConfig = activityVoteConfig.getOtherAppVoteConfigCount();
            voteType = activityVoteConfig.getOtherAppVoteType();
        }
        if(voteType == null || voteConfig == null) {
            logger.error("voteType : " + voteType + " voteConfig : " + voteConfig);
            throw QuanhuException.busiError("活动已关闭或不存在");
        }
        //用户的投票数
        int count = activityVoteRecordDao.voteRecordCount(activityVoteInfoVo.getKid(),
                record.getCreateUserId(),
                record.getOtherFlag(),
                ActivityVoteConstants.FIXED_VOTE_TYPE.equals(voteType) ? "fixed" : "event");
        if(voteConfig <= count) {
            int flag = activityUserPrizesDao.updateUserRoll(record.getCreateUserId());
            if(flag == 0){
                throw QuanhuException.busiError("无可用的投票券");
            }
            record.setFreeVoteFlag(ActivityVoteConstants.NO_FREE_VOTE);
            activityUserPrizesDao.updateStatus(record.getCreateUserId());
        }
        //更新参与者的票数
        int votoDetailCount = activityVoteDetailDao.updateVoteCount(record.getCandidateId(), activityVoteInfoVo.getKid());
        if(votoDetailCount == 0){
            throw QuanhuException.busiError("参与者不存在");
        }

        Response<Long> result = idAPI.getKid("qh_activity_vote_record");
        if(!result.success()){
            throw QuanhuException.busiError("调用发号器失败");
        }
        record.setKid(result.getData());
        //插入投票记录
        activityVoteRecordDao.insertByPrimaryKeySelective(record);

        RedisTemplate<String, Long> template = templateBuilder.buildRedisTemplate(Long.class);
        template.opsForZSet().incrementScore(ActivityCandidateConstants.getKeyRank(record.getActivityInfoId()),
                record.getCandidateId(),
                1d);

        return count;
    }

    /**
     * 查看用户是否有可用的投票卷
     * @param   createUserId
     * @return
     * */
    public int selectUserRoll(Long createUserId) {
        return activityUserPrizesDao.selectUserRoll(createUserId) > 0 ? 1 : 0;
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
        if(now.compareTo(activityVoteInfoVo.getActivityVoteBegin()) == -1 ) {
            throw QuanhuException.busiError("该活动还未进入投票阶段");
        }
        if(now.compareTo(activityVoteInfoVo.getActivityVoteEnd()) == 1 ) {
            throw QuanhuException.busiError("该活动投票阶段已结束");
        }
    }

}
