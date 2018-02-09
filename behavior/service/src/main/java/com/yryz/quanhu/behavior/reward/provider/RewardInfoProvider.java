package com.yryz.quanhu.behavior.reward.provider;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.gift.entity.GiftInfo;
import com.yryz.quanhu.behavior.gift.service.GiftInfoService;
import com.yryz.quanhu.behavior.reward.api.RewardInfoApi;
import com.yryz.quanhu.behavior.reward.constants.RewardConstants;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.BranchFeesEnum;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author wangheng
 * @date 2018年1月27日 下午12:03:30
 */
@Service(interfaceClass = RewardInfoApi.class)
public class RewardInfoProvider implements RewardInfoApi {

    Logger logger = LoggerFactory.getLogger(RewardInfoProvider.class);

    @Autowired
    private RewardInfoService rewardInfoService;

    @Autowired
    private GiftInfoService giftInfoService;

    @Autowired
    private OrderSDK orderSDK;

    @Reference(check = false, lazy = true, timeout = 1000)
    private IdAPI idAPI;

    @Reference(check = false, lazy = true, timeout = 1000)
    private UserApi userApi;

    @Reference(check = false, lazy = true, timeout = 1000)
    private ResourceApi resourceApi;

    @Override
    public Response<Map<String, Object>> reward(RewardInfo record) {
        try {
            Assert.notNull(record.getResourceId(), "打赏资源Id，为NULL！ ");
            Assert.isTrue(null != record.getGiftNum() && record.getGiftNum() > 0, "打赏礼物数量，为NULL！ ");
            Assert.notNull(record.getModuleEnum(), "打赏资源ModuleEnum，为NULL！ ");
            Assert.notNull(record.getToUserId(), "打赏资源ToUserId，为NULL！ ");
            // 打赏礼物 校验
            GiftInfo giftInfo = giftInfoService.selectByKid(record.getGiftId());
            Assert.notNull(giftInfo, "打赏礼物不存在，giftId：" + record.getGiftId());
            Assert.isTrue(giftInfo.getGiftPrice().equals(record.getGiftPrice()), "打赏礼物价值与系统初始化礼物价值 不同！");
            if(record.getCreateUserId().equals(record.getToUserId())){
                throw new QuanhuException(ExceptionEnum.NOT_TO_PLAY_REWARD);
            }
            
            record.setRewardStatus(RewardConstants.reward_status_pay_not);
            record.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
            
            // 创建订单
            InputOrder inputOrder = new InputOrder();
            inputOrder.setBizContent(JsonUtils.toFastJson(record, "yyyy-MM-dd HH:mm:ss"));
            inputOrder.setCost(record.getGiftNum() * giftInfo.getGiftPrice());
            if (null != record.getCoterieId() && 0L != record.getCoterieId()) {
                inputOrder.setCoterieId(record.getCoterieId());
            }
            inputOrder.setCreateUserId(record.getCreateUserId());
            inputOrder.setFromId(record.getCreateUserId());
            // 调用订单 业务枚举
            inputOrder.setModuleEnum(BranchFeesEnum.REWARD.toString());
            inputOrder.setOrderEnum(OrderEnum.REWARD_ORDER);
            inputOrder.setResourceId(record.getResourceId());
            inputOrder.setToId(record.getToUserId());
            Long orderId = orderSDK.createOrder(inputOrder);

            record.setOrderId(orderId);

            rewardInfoService.insertSelective(record);

            Map<String, Object> result = new HashMap<>();
            result.put("orderId", orderId);

            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("发起资源打赏异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<RewardInfoVo>> pageByCondition(RewardInfoDto dto, boolean isCount) {
        try {
            Assert.notNull(dto.getQueryType(), "QueryType is NULL ！");
            if (RewardConstants.QueryType.reward_resource_user_list.equals(dto.getQueryType())) {
                Assert.notNull(dto.getResourceId(), "ResourceId is NULL ！QueryType：" + dto.getQueryType());
            } else if (RewardConstants.QueryType.my_reward_user_list.equals(dto.getQueryType())
                    || RewardConstants.QueryType.my_reward_resource_list.equals(dto.getQueryType())) {
                Assert.notNull(dto.getCreateUserId(), "CreateUserId is NULL ！QueryType：" + dto.getQueryType());
            } else if (RewardConstants.QueryType.reward_my_user_list.equals(dto.getQueryType())
                    || RewardConstants.QueryType.reward_my_resource_list.equals(dto.getQueryType())) {
                Assert.notNull(dto.getToUserId(), "ToUserId is NULL ！QueryType：" + dto.getQueryType());
            }

            // 只返回 打赏成功的记录
            dto.setRewardStatus(RewardConstants.reward_status_pay_success);
            PageList<RewardInfoVo> pageList = rewardInfoService.pageByCondition(dto, isCount);
            if (null == pageList || CollectionUtils.isEmpty(pageList.getEntities()) || null == dto.getQueryType()) {
                return ResponseUtils.returnObjectSuccess(pageList);
            }

            List<RewardInfoVo> entities = pageList.getEntities();
            Set<String> userIds = new HashSet<>();
            Set<String> resourceIds = new HashSet<>();

            // 获取 用户ID 或者 资源ID
            for (RewardInfoVo info : entities) {
                if (null == info) {
                    continue;
                }
                if (RewardConstants.QueryType.my_reward_resource_list.equals(dto.getQueryType())) {
                    resourceIds.add(String.valueOf(info.getResourceId()));
                } else if (RewardConstants.QueryType.my_reward_user_list.equals(dto.getQueryType())) {
                    userIds.add(String.valueOf(info.getToUserId()));
                } else if (RewardConstants.QueryType.reward_my_resource_list.equals(dto.getQueryType())) {
                    userIds.add(String.valueOf(info.getCreateUserId()));
                    resourceIds.add(String.valueOf(info.getResourceId()));
                } else if (RewardConstants.QueryType.reward_my_user_list.equals(dto.getQueryType())
                        || RewardConstants.QueryType.reward_resource_user_list.equals(dto.getQueryType())) {
                    userIds.add(String.valueOf(info.getCreateUserId()));
                }
            }

            // 礼物数据
            for (RewardInfoVo info : entities) {
                if (null == info) {
                    continue;
                }
                // 礼物信息
                GiftInfo giftInfo = giftInfoService.selectByKid(info.getGiftId());
                if (null != giftInfo) {
                    info.setGiftImage(giftInfo.getGiftImage());
                    info.setGiftName(giftInfo.getGiftName());
                }
            }

            // 用户信息
            if (CollectionUtils.isNotEmpty(userIds)) {
                // 获取用户信息集合
                Map<String, UserSimpleVO> userMap = ResponseUtils.getResponseData(userApi.getUserSimple(userIds));

                for (RewardInfoVo info : entities) {
                    if (null == info) {
                        continue;
                    }

                    if (null != userMap) {
                        UserSimpleVO userVo = null;
                        if (RewardConstants.QueryType.my_reward_user_list.equals(dto.getQueryType())) {
                            userVo = userMap.get(String.valueOf(info.getToUserId()));
                        } else {
                            userVo = userMap.get(String.valueOf(info.getCreateUserId()));
                        }
                        info.setUser(userVo);
                    }
                }
            }
            // 资源信息
            else if (CollectionUtils.isNotEmpty(resourceIds)) {
                Map<String, ResourceVo> resourceMap = ResponseUtils
                        .getResponseData(resourceApi.getResourcesByIds(resourceIds));
                if (null != resourceMap) {
                    for (RewardInfoVo info : entities) {
                        if (null == info) {
                            continue;
                        }

                        ResourceVo resourceVo = resourceMap.get(String.valueOf(info.getResourceId()));
                        info.setResourceVo(resourceVo);
                    }
                }
            }

            return ResponseUtils.returnObjectSuccess(pageList);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取资源打赏记录异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> updateByKid(RewardInfo record) {
        try {
            Integer result = rewardInfoService.updateByKid(record);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("更新打赏记录异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<RewardFlowVo>> selectRewardFlow(Long userId, Integer currentPage, Integer pageSize) {
        try {
            PageList<RewardFlowVo> result = rewardInfoService.selectRewardFlow(userId, currentPage, pageSize);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("查询打赏明细流水异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
