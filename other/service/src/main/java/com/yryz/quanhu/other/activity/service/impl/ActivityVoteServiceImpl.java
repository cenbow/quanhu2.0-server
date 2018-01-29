package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.other.activity.constants.ActivityCandidateConstants;
import com.yryz.quanhu.other.activity.constants.ActivityVoteConstants;
import com.yryz.quanhu.other.activity.dao.*;
import com.yryz.quanhu.other.activity.dto.ActivityVoteDto;
import com.yryz.quanhu.other.activity.entity.ActivityUserPrizes;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.entity.ActivityVoteRecord;
import com.yryz.quanhu.other.activity.service.ActivityVoteService;

import com.yryz.quanhu.other.activity.vo.ActivityPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityUserPrizesVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.ActivityVoteInfoVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class ActivityVoteServiceImpl implements ActivityVoteService {

    private Logger logger = LoggerFactory.getLogger(ActivityVoteServiceImpl.class);

    @Reference(check = false)
    IdAPI idAPI;

    @Autowired
    ActivityInfoDao activityInfoDao;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

    @Autowired
    ActivityVoteDetailDao activityVoteDetailDao;

    @Autowired
    ActivityPrizesDao activityPrizesDao;

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
        if(activityInfoVo == null) {
            throw QuanhuException.busiError("活动已关闭或不存在");
        }
        if(userId != null) {
            //获取当前用户是否参与此活动
            activityInfoVo.setJoinFlag(activityVoteDetailDao.selectCandidateCount(kid, userId) == 0 ? 10 : 11);
        } else {
            activityInfoVo.setJoinFlag(10);
        }
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
        //TODO:设置浏览数
        activityInfoVo.setAmountOfAccess(0L);

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
                activityVoteInfoVo.setInAppVoteType(activityVoteConfig.getInAppVoteType());
                activityVoteInfoVo.setInAppVoteConfigCount(activityVoteConfig.getInAppVoteConfigCount());
                activityVoteInfoVo.setOtherAppVoteType(activityVoteConfig.getOtherAppVoteType());
                activityVoteInfoVo.setOtherAppVoteConfigCount(activityVoteConfig.getOtherAppVoteConfigCount());
                activityVoteInfoVo.setCommentFlag(activityVoteConfig.getCommentFlag());
                activityVoteInfoVo.setConfigSources(activityVoteConfig.getConfigSources());//配置元数据
                activityVoteInfoVo.setNoRewardContent(activityVoteConfig.getNoRewardContent());//无奖励配置文案

                template.opsForValue().set(ActivityVoteConstants.getKeyInfo(activityVoteInfoVo.getKid()), activityVoteInfoVo);
                this.setVoteConfig(activityVoteInfoVo.getJoinCount() == null ? 0 : activityVoteInfoVo.getJoinCount(), activityVoteConfig);
            }
        }

        return activityVoteInfoVo;
    }

    /**
     *  确认投票
     *  @param  record
     *  @param  activityVoteInfoVo
     *  @return
     * */
    @Transactional(propagation = Propagation.REQUIRED)
    public int voteRecord(ActivityVoteRecord record, ActivityVoteInfoVo activityVoteInfoVo) {
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
            if(flag == 0) {
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
        //生成投票编号
        Response<Long> result = idAPI.getSnowflakeId();
        if(!result.success()){
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        record.setKid(result.getData());
        //插入投票记录
        activityVoteRecordDao.insertByPrimaryKeySelective(record);
        //累计排行榜中的票数
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
        return activityUserPrizesDao.selectUserRoll(createUserId) > 0 ? 11 : 10;
    }

    /**
     *  奖品列表
     *  @param  activityVoteDto
     *  @return
     * */
    public PageList<ActivityPrizesVo> prizesList(ActivityVoteDto activityVoteDto) {
        Page<Object> page = PageHelper.startPage(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
        List<ActivityPrizesVo> list = activityPrizesDao.selectListCondition(activityVoteDto.getActivityInfoId());
        PageList<ActivityPrizesVo> pageList = new PageList<>();
        pageList.setCurrentPage(activityVoteDto.getCurrentPage());
        pageList.setPageSize(activityVoteDto.getPageSize());
        pageList.setEntities(list);
        pageList.setCount(page.getTotal());

        return pageList;
    }

    /**
     * 领取奖品
     * @param   activityInfoId
     * @param   phone
     * @param   userId
     * @return
     * */
    public ActivityUserPrizesVo getPrizes(Long activityInfoId, String phone, Long userId) {
        //用户的投票数
        int count = activityVoteRecordDao.voteRecordCount(activityInfoId, userId, 1, "fixed");
        if(count == 0) {
            throw QuanhuException.busiError("未在当前活动中投票，不能领取奖品");
        }
        ActivityVoteDto activityVoteDto = new ActivityVoteDto();
        activityVoteDto.setActivityInfoId(activityInfoId);
        activityVoteDto.setCreateUserId(userId);
        //获取用户是否领取过该活动
        List<ActivityUserPrizes> activityUserPrizes = activityUserPrizesDao.selectUserPrizesList(activityVoteDto);
        if (!CollectionUtils.isEmpty(activityUserPrizes)) {
            throw QuanhuException.busiError("您已经领取过该奖品");
        }
        ActivityUserPrizesVo result = new ActivityUserPrizesVo();
        List<ActivityUserPrizes> resultList = new ArrayList<>();
        result.setPrizes(resultList);
        result.setUserId(userId);
        //获取可领取的奖品
        List<ActivityPrizesVo> activityPrizes = activityPrizesDao.selectAvailablePrizesVo(activityInfoId);
        if (!CollectionUtils.isEmpty(activityPrizes)) {
            for (ActivityPrizesVo activity : activityPrizes) {
                if (activity != null) {
                    List<ActivityPrizesVo> prizesList = new ArrayList<>();
                    //如果减少库存成功，加入待发放列表
                    if(activityPrizesDao.updateIssueNum(activity.getKid()) > 0) {
                        prizesList.add(activity);
                    }
                    //插入用户奖品数据
                    if(!CollectionUtils.isEmpty(prizesList)) {
                        for(ActivityPrizesVo prizes : prizesList) {
                            ActivityUserPrizes userPrize = new ActivityUserPrizes();
                            userPrize.setCreateUserId(userId);
                            userPrize.setPrizesName(prizes.getPrizesName());
                            userPrize.setPrizesType(prizes.getPrizesType());
                            userPrize.setCanNum(prizes.getCanNum());
                            userPrize.setPhone(phone);
                            UUID id = UUID.randomUUID();
                            String[] idd = id.toString().split("-");
                            userPrize.setOnlyCode(idd[0]);
                            userPrize.setPrizesNum(prizes.getPrizesNum());
                            userPrize.setPrizesUnit(prizes.getPrizesUnit());
                            userPrize.setBeginTime(prizes.getBeginTime());
                            userPrize.setEndTime(prizes.getEndTime());
                            userPrize.setRemark(prizes.getRemark());
                            userPrize.setState(1);
                            userPrize.setActivityInfoId(activityInfoId);
                            //生成投票编号
                            Response<Long> idResult = idAPI.getSnowflakeId();
                            if(!idResult.success()) {
                                throw new QuanhuException(ExceptionEnum.SysException);
                            }
                            userPrize.setKid(idResult.getData());
                            activityUserPrizesDao.insertByPrimaryKeySelective(userPrize);
                            resultList.add(userPrize);
                        }
                        //TODO:发送短信提醒中奖，需要确认是否批量发送
                    }
                }
            }
        }
        if(CollectionUtils.isEmpty(resultList)) {
            throw QuanhuException.busiError("奖品已经领完");
        }

        return result;
    }

    /**
     * 我的卡劵
     * @param   activityVoteDto
     * @return
     * */
    public PageList<ActivityUserPrizes> userPrizesList(ActivityVoteDto activityVoteDto) {
        Page<ActivityVoteDetailVo> page = PageHelper.startPage(activityVoteDto.getCurrentPage(), activityVoteDto.getPageSize());
        PageList<ActivityUserPrizes> pageList = new PageList<>();
        pageList.setCurrentPage(activityVoteDto.getCurrentPage());
        pageList.setPageSize(activityVoteDto.getPageSize());
        pageList.setEntities(activityUserPrizesDao.selectUserPrizesList(activityVoteDto));
        pageList.setCount(page.getTotal());
        return pageList;
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
