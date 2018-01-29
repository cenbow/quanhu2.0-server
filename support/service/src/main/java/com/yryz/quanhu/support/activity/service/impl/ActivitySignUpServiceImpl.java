package com.yryz.quanhu.support.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.order.api.OrderAsynApi;
import com.yryz.quanhu.order.enums.ProductEnum;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.support.activity.constant.ActivityConstant;
import com.yryz.quanhu.support.activity.dao.ActivityEnrolConfigDao;
import com.yryz.quanhu.support.activity.dao.ActivityRecordDao;
import com.yryz.quanhu.support.activity.entity.ActivityEnrolConfig;
import com.yryz.quanhu.support.activity.entity.ActivityInfo;
import com.yryz.quanhu.support.activity.entity.ActivityRecord;
import com.yryz.quanhu.support.activity.service.ActivityInfoService;
import com.yryz.quanhu.support.activity.service.ActivitySignUpService;
import com.yryz.quanhu.support.activity.util.DateUtils;
import com.yryz.quanhu.support.activity.vo.ActivitySignUpHomeAppVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.NumberUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ActivitySignUpServiceImpl implements ActivitySignUpService {

    private Logger logger = LoggerFactory.getLogger(ActivitySignUpServiceImpl.class);

    @Autowired
    ActivityInfoService activityInfoService;
    @Autowired
    ActivityEnrolConfigDao activityEnrolConfigDao;
    @Autowired
    ActivityRecordDao activityRecordDao;
    @Reference(check=false)
    private IdAPI idApi;
    @Reference(check=false)
    OrderSDK orderSDK;

    @Override
    public ActivitySignUpHomeAppVo getActivitySignUpHome(Long activityInfoId, String custId) {

        Assert.notNull(activityInfoId, "活动id不能为空");
        ActivityInfo activityInfo = activityInfoService.getActivityInfoVo(activityInfoId, null);
        Assert.notNull(activityInfo, "获取活动id:" + activityInfoId + "信息为空");

        ActivitySignUpHomeAppVo activitySignUpHomeAppVo = new ActivitySignUpHomeAppVo();
        BeanUtilsBean.getInstance().getConvertUtils().register(new SqlDateConverter(null), Date.class);
        BeanUtils.copyProperties(activitySignUpHomeAppVo, activityInfo);
        Date date = new Date();

        if (activityInfo.getShelveFlag() == 11) {
            //return ReturnModel.returnException("该活动已下架");
            return activitySignUpHomeAppVo;
        }
        if (DateUtils.getDistanceOfTwoDate(date, activitySignUpHomeAppVo.getBeginTime()) > 0) {
            // 未开始
            activitySignUpHomeAppVo.setActivityStatus(11);
            // 进行中
        } else if (DateUtils.getDistanceOfTwoDate(activitySignUpHomeAppVo.getBeginTime(), date) >= 0
                && DateUtils.getDistanceOfTwoDate(date, activitySignUpHomeAppVo.getEndTime()) >= 0) {
            activitySignUpHomeAppVo.setActivityStatus(12);
        } else if (DateUtils.getDistanceOfTwoDate(date, activitySignUpHomeAppVo.getEndTime()) < 0) {
            activitySignUpHomeAppVo.setActivityStatus(13);
        }
        //设置当前活动报名人数上限
        ActivityEnrolConfig activityEnrolConfig = this.getActivityEnrolConfigByActId(activitySignUpHomeAppVo.getKid());
        if (activityEnrolConfig == null) {
            activitySignUpHomeAppVo.setEnrolUpper(0);
        } else {
            activitySignUpHomeAppVo.setEnrolUpper(activityEnrolConfig.getEnrolUpper());
        }
        //设置当前用户报名状态
        if (StringUtils.isEmpty(custId)) {
            activitySignUpHomeAppVo.setEnrolStatus(ActivityConstant.ACTIVITY_ENROL_STATUS_TAKE_JOIN);
        } else {
            activitySignUpHomeAppVo.setEnrolStatus(this.getEnrolStatusByCustId(custId, activitySignUpHomeAppVo.getKid(), activityEnrolConfig.getSignUpType()));
        }
        activitySignUpHomeAppVo.setCurrentDate(new Date());
        return activitySignUpHomeAppVo;
    }

    private ActivityEnrolConfig getActivityEnrolConfigByActId(Long activityKid) {
        return activityEnrolConfigDao.selectByActivityId(activityKid);
    }

    /**
     * 查询当前用户参与状态
     *
     * @param custId
     * @param activityKid
     * @param SignUpType
     * @return
     */
    @Override
    public Integer getEnrolStatusByCustId(String custId, Long activityKid, Integer SignUpType) {
        Integer status = null;
        if (SignUpType == null) {
            ActivityEnrolConfig activityEnrolConfig = activityEnrolConfigDao.selectByActivityId(activityKid);
            SignUpType = activityEnrolConfig.getSignUpType();
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("custId", custId);
        map.put("activityId", activityKid);
        List<ActivityRecord> activityRecordList = activityRecordDao.getEnrolStatusByCustId(map);
        if (CollectionUtils.isEmpty(activityRecordList)) {
            status = ActivityConstant.ACTIVITY_ENROL_STATUS_TAKE_JOIN;
        } else {
            status = ActivityConstant.ACTIVITY_ENROL_STATUS_ALREADY_JOIN;
        }
        if (status == ActivityConstant.ACTIVITY_ENROL_STATUS_ALREADY_JOIN || SignUpType != ActivityConstant.ACTIVITY_ENROL_TYPE_MONEY) {
            return status;
        }
        //TODO 查询订单状态
        boolean success = orderSDK.isBuyOrderSuccess("", NumberUtils.parseNumber(custId,Long.TYPE),activityRecordList.get(0).getKid());
       if(success){
           status = ActivityConstant.ACTIVITY_ENROL_STATUS_EXCE_JOIN;
       }
        return status;
    }

    @Override
    public ActivityEnrolConfig getActivitySignUpFrom(Long activityInfoId, String custId) {
        Assert.notNull(activityInfoId, "活动id不能为空");
        Assert.notNull(custId, "用户id不能为空");
        ActivityInfo activityInfo = activityInfoService.getActivityInfoVo(activityInfoId, null);
        Assert.notNull(activityInfo, "获取活动id:" + activityInfoId + "信息为空");
        ActivityEnrolConfig activityEnrolConfig = new ActivityEnrolConfig();
        if (activityInfo.getShelveFlag() == 11) {
            activityEnrolConfig.setShelveFlag(11);
            return activityEnrolConfig;
        }
        Date date = new Date();
        if (DateUtils.getDistanceOfTwoDate(date, activityInfo.getBeginTime()) > 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "活动未开始", "活动未开始");
        } else if (DateUtils.getDistanceOfTwoDate(date, activityInfo.getEndTime()) < 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "活动已结束", "活动已结束");
        }
        activityEnrolConfig = activityEnrolConfigDao.selectByActivityId(activityInfoId);
        Assert.notNull(activityEnrolConfig, "获取活动id:" + activityInfoId + "配置信息为空");
        Integer EnrolStatus = this.getEnrolStatusByCustId(custId, activityInfoId, activityEnrolConfig.getSignUpType());
        if (EnrolStatus == ActivityConstant.ACTIVITY_ENROL_STATUS_ALREADY_JOIN) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "您已经参加过该活动，不能再次参加！", "您已经参加过该活动，不能再次参加！");
        }
        if (EnrolStatus == ActivityConstant.ACTIVITY_ENROL_STATUS_EXCE_JOIN) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "已支付完成，请勿重复报名!", "已支付完成，请勿重复报名!");
        }

        if (activityInfo.getJoinCount() >= activityEnrolConfig.getEnrolUpper()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "该活动报名人数已满，不能参加！", "该活动报名人数已满，不能参加！");
        }
        return activityEnrolConfig;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public ActivityRecord activitySignUpSubmit(ActivityRecord activityRecord, String custId) {
        Assert.notNull(custId, "custId不能为空");
        Assert.notNull(activityRecord, "activityRecord不能为空");
        Assert.notNull(activityRecord.getActivityInfoId(), "活动id不能为空");
        Assert.notNull(activityRecord.getEnrolSources(), "报名数据不能为空");

        ActivityInfo activityInfo = activityInfoService.getActivityInfoVo(activityRecord.getActivityInfoId(), null);
        Assert.notNull(activityInfo, "获取活动id:" + activityRecord.getActivityInfoId() + "信息为空");

        if (activityInfo.getShelveFlag() == 11) {
            //return ReturnModel.returnException("该活动已下架");
            activityRecord.setShelveFlag(11);
            return activityRecord;
        }
        Date date = new Date();
        if (DateUtils.getDistanceOfTwoDate(date, activityInfo.getBeginTime()) > 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "活动未开始", "活动未开始");
        } else if (DateUtils.getDistanceOfTwoDate(date, activityInfo.getEndTime()) < 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "活动已结束", "活动已结束");
        }
        ActivityEnrolConfig activityEnrolConfig = activityEnrolConfigDao.selectByActivityId(activityRecord.getActivityInfoId());
        Assert.notNull(activityEnrolConfig, "获取活动id:" + activityRecord.getActivityInfoId() + "配置信息为空");
        Integer EnrolStatus = this.getEnrolStatusByCustId(custId, activityRecord.getActivityInfoId(), activityEnrolConfig.getSignUpType());
        if (EnrolStatus == ActivityConstant.ACTIVITY_ENROL_STATUS_ALREADY_JOIN) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "您已经参加过该活动，不能再次参加！", "您已经参加过该活动，不能再次参加！");
        }

        if (activityInfo.getJoinCount() >= activityEnrolConfig.getEnrolUpper()) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "该活动报名人数已满，不能参加！", "该活动报名人数已满，不能参加！");
        }

        // 构建报名信息
        activityRecord.setAmount(activityEnrolConfig.getAmount());
        activityRecord.setSignUpType(activityEnrolConfig.getSignUpType());
        activityRecord.setCreateUserId(Long.valueOf(custId));
        activityRecord.setLastUpdateUserId(Long.valueOf(custId));
        activityRecord.setEnrolSources(this.replaceIllagelWordsEnrolSources(activityRecord.getEnrolSources()));
        activityRecord.setKid(idApi.getSnowflakeId().getData());
        // 报名类型（1报名需支付货币 2报名需支付积分 3免费报名）
        switch (activityEnrolConfig.getSignUpType()) {
            case 11:
                // 校验报名类活动，提交的参与活动信息；要严格校验，若校验不严格 将导致用户扣钱，参加报名活动失败
                Integer stats = this.getEnrolStatusByCustId(custId, activityRecord.getActivityInfoId(), activityEnrolConfig.getSignUpType());
                if (stats != ActivityConstant.ACTIVITY_ENROL_STATUS_TAKE_JOIN) {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "已支付完成，请勿重复报名!", "已支付完成，请勿重复报名!");
                }
                //TODO 数据提交至订单模块
                // 创建订单
                String orderNo = null;
                try {
                    orderNo = this.generateOrder(activityRecord, custId);
                } catch (Exception e1) {
                    throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "余额不足，请充值!", "余额不足，请充值!");
                }
                Assert.hasText(orderNo, "货币支付，创建订单失败！");
                activityRecord.setOrderNo(orderNo);
                return activityRecord;
            case 12:
                int flag = 0;
                // TODO 支付积分
               /* try {
                    Assert.notNull(activityEnrolConfig.getAmount(), "积分支付失败！");
                    flag = scoreAPI.consumeScore(custId, activityEnrolConfig.getAmount().intValue(), "-2");
                } catch (Exception e) {
                    throw new CommonException("支付积分失败：" + e.getMessage());
                }
                if (flag == 0) {
                    throw new CommonException("积分不足!");
                }*/
                break;
            case 13:
                break;
            default:
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "SignUpType:" + activityEnrolConfig.getSignUpType(), "SignUpType:" + activityEnrolConfig.getSignUpType());
        }

        activityRecordDao.insertByPrimaryKeySelective(activityRecord);

        // 更新主表的当前报名人人数信息
        activityInfo.setJoinCount(activityInfo.getJoinCount() + 1);
        activityInfoService.updateJoinCount(activityInfo.getKid(),activityEnrolConfig.getEnrolUpper());
        return activityRecord;
    }

    private String generateOrder(ActivityRecord activityRecord, String custId) {
        InputOrder inputOrder = new InputOrder();
        inputOrder.setOrderEnum(OrderEnum.ACTIVITY_SIGNUP_ORDER);
        inputOrder.setFromId(activityRecord.getCreateUserId());
        //TODO 字段封装
       /* inputOrder.setToId();
        inputOrder.setModuleEnum();*/
        inputOrder.setBizContent(JSON.toJSONString(activityRecord));
        inputOrder.setResourceId(activityRecord.getKid());
        inputOrder.setCost(Long.valueOf(activityRecord.getAmount()));
        inputOrder.setCreateUserId(activityRecord.getCreateUserId());
        Long oderId = orderSDK.createOrder(inputOrder);
        return oderId.toString();
    }

    public String replaceIllagelWordsEnrolSources(String text) {
        /*@SuppressWarnings("unchecked")
        List<Map<String,String>> voMap = JsonUtils.fromJson(text, List.class);
        try {
            for(Map<String,String> map :voMap){
                map.put("value", illegalWordsService.replaceIllagelWords(map.get("value")));
            }
        } catch (Exception e) {
            logger.error("调用RPC服务出错");
        }
        return JSON.toJSONString(voMap);*/
        //TODO 敏感词替换
        return text;
    }
}
