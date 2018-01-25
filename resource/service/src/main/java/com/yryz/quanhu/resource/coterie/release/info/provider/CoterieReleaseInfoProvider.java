package com.yryz.quanhu.resource.coterie.release.info.provider;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.resource.coterie.release.info.api.CoterieReleaseInfoApi;
import com.yryz.quanhu.resource.coterie.release.info.vo.CoterieReleaseInfoVo;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.release.config.service.ReleaseConfigService;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author wangheng
 * @Description: 私圈文章 - 特有
 * @date 2018年1月24日 下午1:24:40
 */
@Service(interfaceClass = CoterieReleaseInfoApi.class)
public class CoterieReleaseInfoProvider implements CoterieReleaseInfoApi {

    private Logger logger = LoggerFactory.getLogger(CoterieReleaseInfoProvider.class);

    @Autowired
    private ReleaseConfigService releaseConfigService;

    @Autowired
    private ReleaseInfoService releaseInfoService;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private IdAPI idAPI;

    @Autowired
    private OrderSDK orderSDK;

    @Reference(lazy = true, check = false, timeout = 10000)
    private CountApi countApi;
    
    @Reference(lazy = true, check = false, timeout = 10000)
    private CoterieMemberAPI coterieMemberAPI;

    @Override
    public Response<ReleaseInfo> release(ReleaseInfo record) {
        try {
            Assert.notNull(record.getCoterieId(), "release() CoterieId is null !");
            Assert.isTrue(record.getContentPrice() >= 0L, "release() ContentPrice is not unsigned !");
            // 校验用户是否存在
            ResponseUtils.getResponseData(userApi.getUserSimple(record.getCreateUserId()));

            if (StringUtils.isBlank(record.getModuleEnum())) {
                record.setModuleEnum(ResourceTypeEnum.RELEASE + "-");
            }

            // TODO 校验是否为圈主

            record.setClassifyId(ReleaseConstants.COTERIE_DEFAULT_CLASSIFY_ID);
            record.setDelFlag(CommonConstants.DELETE_NO);
            record.setShelveFlag(CommonConstants.SHELVE_YES);

            ReleaseConfigVo cfgVo = releaseConfigService.getTemplate(ReleaseConstants.COTERIE_DEFAULT_CLASSIFY_ID);
            Assert.notNull(cfgVo, "私圈发布文章，发布模板不存在！classifyId：" + ReleaseConstants.COTERIE_DEFAULT_CLASSIFY_ID);

            // 校验模板
            Assert.isTrue(releaseInfoService.releaseInfoCheck(record, cfgVo), "私圈发布文章，参数校验不通过！");

            // 用户输入时至少有一个（富文本）元素不为空
            if (StringUtils.isEmpty(record.getContent()) && StringUtils.isEmpty(record.getImgUrl())
                    && StringUtils.isEmpty(record.getVideoUrl()) && StringUtils.isEmpty(record.getAudioUrl())) {
                throw QuanhuException.busiError("富文本元素(文字、图片、视频、音频)至少有一个不能为空！");
            }

            // kid 生成
            record.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
            releaseInfoService.insertSelective(record);

            try {
                // TODO 资源聚合

                // 资源计数接入
                countApi.commitCount(BehaviorEnum.Release, record.getKid(), null, 1L);
            } catch (Exception e) {
                logger.error("资源聚合、统计计数 接入异常！", e);
            }

            return ResponseUtils.returnObjectSuccess(record);

        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("私圈发布文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<CoterieReleaseInfoVo> infoByKid(Long kid, Long headerUserId) {
        try {
            ReleaseInfoVo vo = releaseInfoService.selectByKid(kid);
            Assert.notNull(vo, "文章不存在！kid:" + kid);

            CoterieReleaseInfoVo infoVo = new CoterieReleaseInfoVo();
            BeanUtils.copyProperties(infoVo, vo);

            // TODO 创建者用户信息
            UserSimpleVO user = ResponseUtils.getResponseData(userApi.getUserSimple(vo.getCreateUserId()));

            // 若资源已删除、或者 已经下线 直接返回
            if (CommonConstants.DELETE_YES.equals(infoVo.getDelFlag())
                    || CommonConstants.SHELVE_NO.equals(infoVo.getShelveFlag())) {
                releaseInfoService.resourcePropertiesEmpty(infoVo);

                return ResponseUtils.returnObjectSuccess(infoVo);
            }

            Byte canReadFlag = ReleaseConstants.CanReadType.NO;
            // 访问用户角色
            Byte headerUserRole = 0;
            // 付费文章 设置可读标识
            if (null == headerUserId) {
                if (infoVo.getContentPrice() == 0L) {
                    canReadFlag = ReleaseConstants.CanReadType.YES;
                }
            } else {
                // 若需要付费文章,并且是非圈主，查询 购买记录
                if (infoVo.getContentPrice() > 0L && headerUserRole == 0) {
                    // TODO 查询 购买记录
                    canReadFlag = ReleaseConstants.CanReadType.YES;
                } else {
                    canReadFlag = ReleaseConstants.CanReadType.YES;
                }
            }
            infoVo.setCanReadFlag(canReadFlag);

            // 登录用户不可读文章，对资源属性 做特殊处理
            if (ReleaseConstants.CanReadType.NO.equals(canReadFlag)) {
                releaseInfoService.resourcePropertiesEmpty(infoVo);
            }

            try {
                // TODO 资源聚合

                // 资源计数接入
                countApi.commitCount(BehaviorEnum.Read, infoVo.getKid(), null, 1L);
            } catch (Exception e) {
                logger.error("资源聚合、统计计数 接入异常！", e);
            }

            return ResponseUtils.returnObjectSuccess(infoVo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取平台文章详情异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<String, Object>> createOrder(Long kid, Long headerUserId) {
        try {
            Assert.notNull(headerUserId, "headerUserId is null !");

            ReleaseInfoVo info = releaseInfoService.selectByKid(kid);
            Assert.notNull(info, "资源文章不存在！");
            Assert.isTrue(CommonConstants.DELETE_NO.equals(info.getDelFlag())
                    && CommonConstants.SHELVE_YES.equals(info.getShelveFlag()), "资源文章已删除或者已下架！");
            Assert.isTrue(info.getContentPrice() > 0L, "当前资源文章免费，不能创建订单");
            // 创建订单者 不能是作者
            Assert.isTrue(!headerUserId.equals(info.getCreateUserId()), "阅读资源文章，创建订单者 不能是作者");

            InputOrder inputOrder = new InputOrder();
            inputOrder.setBizContent(JsonUtils.toFastJson(info));
            inputOrder.setCost(info.getContentPrice());
            inputOrder.setCoterieId(info.getCoterieId());
            inputOrder.setCreateUserId(headerUserId);
            inputOrder.setFromId(headerUserId);
            inputOrder.setModuleEnum(info.getModuleEnum());
            inputOrder.setOrderEnum(OrderEnum.READ_ORDER);
            inputOrder.setResourceId(info.getKid());
            inputOrder.setToId(info.getCreateUserId());
            Map<String, Object> result = new HashMap<>();
            result.put("orderId", orderSDK.createOrder(inputOrder));

            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("付费阅读文章，创建订单异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
