package com.yryz.quanhu.resource.release.info.provider;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
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

    @Override
    public Response<ReleaseInfo> release(ReleaseInfo record) {
        try {
            ReleaseConfigVo cfgVo = releaseConfigService.getTemplate(ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);
            Assert.notNull(cfgVo, "平台发布文章，发布模板不存在！classifyId：" + ReleaseConstants.APP_DEFAULT_CLASSIFY_ID);

            // 校验模板
            Assert.isTrue(releaseInfoService.releaseInfoCheck(record, cfgVo), "平台发布文章，参数校验不通过！");

            // 用户输入时至少有一个（富文本）元素不为空
            if (StringUtils.isEmpty(record.getContent()) && StringUtils.isEmpty(record.getImgUrl())
                    && StringUtils.isEmpty(record.getVideoUrl()) && StringUtils.isEmpty(record.getAudioUrl())) {
                throw QuanhuException.busiError("富文本元素(文字、图片、视频、音频)至少有一个不能为空！");
            }

            // 校验用户是否存在
            ResponseUtils.getResponseData(userApi.getUserSimple(String.valueOf(record.getCreateUserId())));
            // kid 生成
            record.setKid(ResponseUtils.getResponseData(idAPI.getSnowflakeId()));
            releaseInfoService.insertSelective(record);

            // TODO 资源进动态

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

            // TODO 创建者用户信息
            UserSimpleVO user = ResponseUtils
                    .getResponseData(userApi.getUserSimple(String.valueOf(infoVo.getCreateUserId())));

            // TODO 资源互动信息

            // TODO 对接资源浏览数
            return ResponseUtils.returnObjectSuccess(infoVo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取平台文章详情异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<ReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto, Long headerUserId) {
        return null;
    }

    @Override
    public Response<Integer> deleteBykid(ReleaseInfo upInfo) {
        try { // 校验记录是否存在
            ReleaseInfo info = releaseInfoService.selectByKid(upInfo.getKid());
            Assert.notNull(info, "文章资源不存在，kid：" + upInfo.getKid());

            Assert.isTrue(info.getCreateUserId().equals(upInfo.getLastUpdateUserId()), "操作用户非作者不能删除！");

            int result = releaseInfoService.updateByUkSelective(upInfo);
            Assert.isTrue(result > 0, "作者删除文章失败！");

            // TODO 动态资源下线

            // TODO 对接计数

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

}