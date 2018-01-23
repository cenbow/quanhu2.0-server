package com.yryz.quanhu.support.activity.service.impl;

import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.support.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.support.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.support.activity.dao.ActivityVoteConfigDao;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.service.ActivityVoteService;
import com.yryz.quanhu.support.activity.vo.ActivityInfoVo;
import com.yryz.quanhu.support.activity.vo.ActivityVoteInfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class ActivityVoteServiceImpl implements ActivityVoteService {

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

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

}
