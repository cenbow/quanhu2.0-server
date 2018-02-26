package com.yryz.quanhu.behavior.read.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.read.contants.ReadContants;
import com.yryz.quanhu.behavior.read.entity.DayRule;
import com.yryz.quanhu.behavior.read.entity.HotTime;
import com.yryz.quanhu.behavior.read.entity.ReadCountRule;
import com.yryz.quanhu.behavior.read.service.ReadService;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.read.service.impl
 * @Desc: 浏览数内部逻辑
 * @Date: 2018/1/30.
 */
@Service
public class ReadServiceImpl implements ReadService {

    private static Logger logger = LoggerFactory.getLogger(ReadServiceImpl.class);

    @Reference
    private CountApi countApi;

    @Reference
    private ReleaseInfoApi releaseInfoApi;

    @Reference
    private BasicConfigApi basicConfigApi;

    @Override
    public void read(Long kid) {
        countApi.commitCount(BehaviorEnum.Read, kid, "", getRandomViewCount());
        countApi.commitCount(BehaviorEnum.RealRead, kid, "", 1L);
    }

    /**
     * 随机取增长阅读数
     *
     * @return
     */
    private Long getRandomViewCount() {
        Long readCountMin = new Long(ResponseUtils.getResponseData(basicConfigApi.getValue(ReadContants.READ_COUNT_MIN, "1")));
        Long readCountMax = new Long(ResponseUtils.getResponseData(basicConfigApi.getValue(ReadContants.READ_COUNT_MAX, "20")));
        Long randomReadCount = RandomUtils.nextLong(readCountMin, readCountMax);
        return randomReadCount;
    }

    @Override
    public void excuteViewCountJob() {
        ReadCountRule vcr = initReadCountRule();
        List<DayRule> dayRuleList = vcr.getDayRuleList();
        Collections.sort(dayRuleList, new Comparator<DayRule>() {
            @Override
            public int compare(DayRule o1, DayRule o2) {
                // 返回值为int类型，大于0表示正序，小于0表示逆序
                return o1.getHours() - o2.getHours();
            }
        });
        //是否高峰时段
        boolean hotFlag = getHotTimeFlag(vcr.getHotTimeList());
        //获取dayRange范围内的非公开和非付费的文章

        ReleaseInfoDto releaseInfoDto = new ReleaseInfoDto();
        releaseInfoDto.setBeginDate(DateUtils.formatDate(vcr.getBeginRangeDay(), "yyyy-MM-dd HH:mm:ss"));
        releaseInfoDto.setCoterieId(0L);
        releaseInfoDto.setShelveFlag(CommonConstants.SHELVE_YES);
        releaseInfoDto.setDelFlag(CommonConstants.DELETE_NO);
        releaseInfoDto.setOrderType(null);
        List<ReleaseInfoVo> list = ResponseUtils.getResponseData(releaseInfoApi.selectByCondition(releaseInfoDto));
        logger.info("excuteViewCountJob select realease list size:" + list.size());
        //循环文章list
        for (ReleaseInfoVo res : list) {
            //计算给文章增加多少阅读数
            long readCount = getAutoViewByRule(res.getCreateDate(), dayRuleList, hotFlag);
            //增加阅读数
            try {
                countApi.commitCount(BehaviorEnum.Read, res.getKid(), "", readCount);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("resourceId:" + res.getKid() + " 的文章增加阅读数失败!" + e.getMessage(), e.getCause());
            }
            if(logger.isDebugEnabled()){
                logger.debug("excuteViewCountJob realease kid :" + res.getKid()+".count:"+readCount);
            }
        }
    }

    /**
     * 初始化阅读数配置
     *
     * @return
     */
    private ReadCountRule initReadCountRule() {
        try {
            //获取阅读数相关配置
            ReadCountRule vcr = new ReadCountRule();
            String dayRange = ResponseUtils.getResponseData(basicConfigApi.getValue(ReadContants.READ_DAY_RANGE, "720"));
            String dayRule = ResponseUtils.getResponseData(basicConfigApi.getValue(ReadContants.READ_DAY_RULE));
            String hotTime = ResponseUtils.getResponseData(basicConfigApi.getValue(ReadContants.READ_HOT_TIME));
            vcr.setDayRange(new Integer(dayRange));
            vcr.setDayRuleList(JSONArray.parseArray(dayRule, DayRule.class));
            vcr.setHotTimeList(JSONArray.parseArray(hotTime, HotTime.class));
            return vcr;
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException);
        }
    }

    /**
     * 判断是否属于高峰时段
     *
     * @param hotTimeList
     * @return
     */
    private boolean getHotTimeFlag(List<HotTime> hotTimeList) {
        if (hotTimeList == null || hotTimeList.size() == 0) {
            return false;
        }
        long minutes = DateUtils.getCurMinutes();
        for (HotTime hotTime : hotTimeList) {
            if (minutes >= hotTime.getBeginTime() && minutes <= hotTime.getEndTime()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 按照规则计算文章应该增加的阅读数
     *
     * @param createDate
     * @param dayRuleList
     * @return
     */
    private int getAutoViewByRule(Date createDate, List<DayRule> dayRuleList, boolean hotFlag) {
        Date nowDate = new Date();
        long disHours = DateUtils.getDistanceHours(nowDate, createDate);
        Random random = new Random();
        for (DayRule dr : dayRuleList) {
            if (disHours < dr.getHours()) {//在N小时内取其规则
                if (hotFlag) {//高峰时段
                    return random.nextInt(dr.getHotEndRange() - dr.getHotStartRange() + 1) + dr.getHotStartRange();
                } else {//非高峰时段
                    return random.nextInt(dr.getNormalEndRange() - dr.getNormalStartRange() + 1)
                            + dr.getNormalStartRange();
                }
            }
        }
        return 0;
    }
}
