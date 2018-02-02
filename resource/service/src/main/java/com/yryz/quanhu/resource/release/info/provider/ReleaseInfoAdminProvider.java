package com.yryz.quanhu.resource.release.info.provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.CommonConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.message.MessageVo;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.MessageUtils;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoAdminApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @Description: 平台文章 管理后台
* @author wangheng
* @date 2018年2月2日 上午11:34:31
*/
@Service(interfaceClass = ReleaseInfoAdminApi.class)
public class ReleaseInfoAdminProvider implements ReleaseInfoAdminApi {

    private Logger logger = LoggerFactory.getLogger(ReleaseInfoProvider.class);

    @Autowired
    private ReleaseInfoService releaseInfoService;

    @Autowired
    private ReleaseInfoProvider releaseInfoProvider;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private ResourceApi resourceApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private MessageAPI messageAPI;

    @Override
    public Response<ReleaseInfo> release(ReleaseInfo record) {
        try {
            return releaseInfoProvider.release(record);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("管理后台马甲发布文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<ReleaseInfoVo> infoByKid(Long kid) {
        try {
            ReleaseInfoVo infoVo = releaseInfoService.selectByKid(kid);
            Assert.notNull(infoVo, "文章不存在！kid:" + kid);

            Assert.isTrue(0L == infoVo.getCoterieId(), "非平台文章禁止访问！");

            // 创建者用户信息
            UserSimpleVO createUser = ResponseUtils.getResponseData(userApi.getUserSimple(infoVo.getCreateUserId()));
            Assert.notNull(createUser, "文章创建者用户不存在！userId:" + infoVo.getCreateUserId());
            infoVo.setUser(createUser);

            return ResponseUtils.returnObjectSuccess(infoVo);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("管理后台获取平台文章详情异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<ReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto) {
        try {
            return releaseInfoProvider.pageByCondition(dto, null, true, true);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("管理后台获取文章列表异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Integer> batchShelve(Long[] kids, Byte shelveFlag, Long lastUpdateUserId) {
        try {
            Assert.notNull(kids, "kids is null !");
            Assert.notNull(shelveFlag, "shelveFlag is null !");
            Assert.isTrue(CommonConstants.SHELVE_NO.equals(shelveFlag) || CommonConstants.SHELVE_YES.equals(shelveFlag),
                    "shelveFlag not is (10,11) !");
            Assert.notNull(lastUpdateUserId, "lastUpdateUserId is null !");
            logger.debug("平台文章，批量上下架操作。kids：" + kids + ", shelveFlag :" + shelveFlag);

            ReleaseInfoDto dto = new ReleaseInfoDto();
            dto.setKids(kids);

            ReleaseInfo up = new ReleaseInfo();
            up.setShelveFlag(shelveFlag);
            up.setLastUpdateUserId(lastUpdateUserId);
            int upCount = releaseInfoService.updateByCondition(up, dto);
            Response<Integer> result = ResponseUtils.returnObjectSuccess(upCount);

            // 没有未更新的记录
            if (upCount < 1) {
                return result;
            }

            for (Long kid : kids) {
                if (null == kid) {
                    continue;
                }

                ReleaseInfoVo releaseInfoVo = releaseInfoService.selectByKid(kid);
                if (null == releaseInfoVo) {
                    logger.debug("平台文章，批量下架操作资源不存在。kid：" + kid);
                    continue;
                }

                if (CommonConstants.SHELVE_NO.equals(shelveFlag)) {
                    try {
                        // 聚合资源下线
                        resourceApi.deleteResourceById(String.valueOf(releaseInfoVo.getKid()));
                    } catch (Exception e) {
                        logger.error("资源聚合下架接入异常！", e);
                    }

                    // 推送下架消息
                    this.shelveSendMessage(releaseInfoVo);
                } else if (CommonConstants.SHELVE_YES.equals(shelveFlag)) {

                    // 创建者用户
                    UserSimpleVO createUser = ResponseUtils
                            .getResponseData(userApi.getUserSimple(releaseInfoVo.getCreateUserId()));
                    if (null == createUser) {
                        continue;
                    }
                    try {
                        // 聚合资源上线
                        this.releaseInfoProvider.commitResourceAndDynamic(releaseInfoVo, createUser);
                    } catch (Exception e) {
                        logger.error("资源聚合上架接入异常！", e);
                    }
                }
            }

            return result;
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("管理后台批量上下架文章异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

    private void shelveSendMessage(ReleaseInfo info) {
        MessageVo mvo = MessageUtils.buildMessage(MessageConstant.RELEASE_SHELVE,
                String.valueOf(info.getCreateUserId()), info.getTitle(), null);
        logger.info("内容被下线，向作者推送消息 !");
        messageAPI.sendMessage(mvo, false);
    }
}
