package com.yryz.quanhu.resource.release.info.provider;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.release.config.service.ReleaseConfigService;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @Description 平台文章
* @author wangheng
* @date 2018年1月23日 上午11:11:44
*/
@Service(interfaceClass = ReleaseInfoApi.class)
public class ReleaseInfoProvider implements ReleaseInfoApi {

    private Logger logger = LoggerFactory.getLogger(ReleaseInfoProvider.class);

    @Autowired
    private ReleaseConfigService releaseConfigService;

    @Autowired
    private ReleaseInfoService releaseInfoService;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private IdAPI idAPI;

    @Reference(lazy = true, check = false, timeout = 10000)
    private CountApi countApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private ResourceDymaicApi resourceDymaicApi;

    @Override
    public Response<ReleaseInfo> release(ReleaseInfo record) {
        try {
            Assert.notNull(record.getContentSource(),"ContentSource is NULL !");
            
            record.setClassifyId(ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);
            record.setDelFlag(CommonConstants.DELETE_NO);
            record.setShelveFlag(CommonConstants.SHELVE_YES);

            Assert.isNull(record.getCoterieId(), "平台文章发布 没有：CoterieId");
            Assert.isTrue(null == record.getContentPrice() || record.getContentPrice() == 0L,
                    "平台文章发布 不能设置付费：ContentPrice");

            if (StringUtils.isBlank(record.getModuleEnum())) {
                record.setModuleEnum(ResourceTypeEnum.RELEASE);
            }

            // 校验用户是否存在
            UserSimpleVO createUser = ResponseUtils.getResponseData(userApi.getUserSimple(record.getCreateUserId()));
            Assert.notNull(createUser, "发布者用户不存在！userId：" + record.getCreateUserId());

            ReleaseConfigVo cfgVo = releaseConfigService.getTemplate(ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);
            Assert.notNull(cfgVo, "平台发布文章，发布模板不存在！classifyId：" + ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);

            // 校验模板
            Assert.isTrue(releaseInfoService.releaseInfoCheck(record, cfgVo), "平台发布文章，参数校验不通过！");

            // 用户输入时至少有一个（富文本）元素不为空
            if (StringUtils.isEmpty(record.getContent()) && StringUtils.isEmpty(record.getImgUrl())
                    && StringUtils.isEmpty(record.getVideoUrl()) && StringUtils.isEmpty(record.getAudioUrl())) {
                throw QuanhuException.busiError("富文本元素(文字、图片、视频、音频)至少有一个不能为空！");
            }

            // kid 生成
            record.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
            releaseInfoService.insertSelective(record);

            // 资源进聚合
            releaseInfoService.commitResource(resourceDymaicApi, record, createUser);

            try {

                // 接入统计计数
                countApi.commitCount(BehaviorEnum.Release, record.getKid(), null, 1L);
            } catch (Exception e) {
                logger.error("资源聚合、统计计数 接入异常！", e);
            }

            return ResponseUtils.returnObjectSuccess(record);

        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("平台发布文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<ReleaseInfoVo> infoByKid(Long kid, Long headerUserId) {
        try {
            ReleaseInfoVo infoVo = releaseInfoService.selectByKid(kid);
            Assert.notNull(infoVo, "文章不存在！kid:" + kid);

            Assert.isTrue(0L == infoVo.getCoterieId(), "非平台文章禁止访问！");

            // 创建者用户信息
            UserSimpleVO createUser = ResponseUtils.getResponseData(userApi.getUserSimple(infoVo.getCreateUserId()));
            Assert.notNull(createUser, "文章创建者用户不存在！userId:" + infoVo.getCreateUserId());
            infoVo.setUser(createUser);

            // 若资源已删除、或者 已经下线 直接返回
            if (CommonConstants.DELETE_YES.equals(infoVo.getDelFlag())
                    || CommonConstants.SHELVE_NO.equals(infoVo.getShelveFlag())) {
                releaseInfoService.resourcePropertiesEmpty(infoVo);

                return ResponseUtils.returnObjectSuccess(infoVo);
            }

            try {
                // 接入统计计数
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
    public Response<PageList<ReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto, Long headerUserId, boolean isCount,
            boolean isGetCreateUser) {
        try {
            // 只查询 平台文章
            dto.setCoterieId(0L);
            PageList<ReleaseInfoVo> pageList = releaseInfoService.pageByCondition(dto, isCount);
            if (!isGetCreateUser || null == pageList || CollectionUtils.isEmpty(pageList.getEntities())) {
                return ResponseUtils.returnObjectSuccess(pageList);
            }

            List<ReleaseInfoVo> voList = pageList.getEntities();

            // 获取所有创建者用户ID
            Set<String> userIds = new HashSet<>();
            for (ReleaseInfoVo info : voList) {
                if (null == info || null == info.getCreateUserId()) {
                    continue;
                }
                userIds.add(String.valueOf(info.getCreateUserId()));
            }

            // 获取用户信息集合
            Map<String, UserSimpleVO> userMap = ResponseUtils.getResponseData(userApi.getUserSimple(userIds));
            if (null != userMap) {
                for (ReleaseInfoVo info : voList) {
                    if (null == info || null == info.getCreateUserId() || null == userMap.get(info.getCreateUserId())) {
                        continue;
                    }
                    UserSimpleVO userVo = userMap.get(info.getCreateUserId());
                    info.setUser(userVo);
                }
            }

            return ResponseUtils.returnObjectSuccess(pageList);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取平台文章列表异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> deleteBykid(ReleaseInfo upInfo) {
        try {
            upInfo.setDelFlag(CommonConstants.DELETE_YES);

            // 校验记录是否存在
            ReleaseInfo info = releaseInfoService.selectByKid(upInfo.getKid());
            Assert.notNull(info, "文章资源不存在，kid：" + upInfo.getKid());

            Assert.isTrue(info.getCreateUserId().equals(upInfo.getLastUpdateUserId()), "操作用户非作者不能删除！");

            int result = releaseInfoService.updateByUkSelective(upInfo);
            Assert.isTrue(result > 0, "作者删除文章失败！");

            try {
                // TODO 动态资源下线

                // 接入统计计数
                countApi.commitCount(BehaviorEnum.Release, upInfo.getKid(), null, -1L);
            } catch (Exception e) {
                logger.error("资源聚合、统计计数 接入异常！", e);
            }

            return ResponseUtils.returnObjectSuccess(result);

        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("作者删除文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> shelvesByCondition(ReleaseInfo record, ReleaseInfoDto dto) {
        return ResponseUtils.returnObjectSuccess(0);
    }

    @Override
    public Response<List<Long>> getKidByCreatedate(String startDate, String endDate) {
        try {
            List<Long> data = this.releaseInfoService.getKidByCreatedate(startDate, endDate);
            return ResponseUtils.returnObjectSuccess(data);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<ReleaseInfoVo>> selectByKids(Set<Long> kids) {
        try {
            ReleaseInfoDto dto = new ReleaseInfoDto();
            dto.setKids((Long[]) kids.toArray());
            List<ReleaseInfoVo> list = this.releaseInfoService.selectByCondition(dto);
            return ResponseUtils.returnObjectSuccess(list);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("未知异常", e);
            return ResponseUtils.returnException(e);
        }
    }
}
