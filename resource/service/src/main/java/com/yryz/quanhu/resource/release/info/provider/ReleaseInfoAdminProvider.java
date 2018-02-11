package com.yryz.quanhu.resource.release.info.provider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
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
import com.yryz.quanhu.resource.coterie.release.info.provider.CoterieReleaseInfoProvider;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoAdminApi;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
 * @author wangheng
 * @Description: 文章 管理后台
 * @date 2018年2月2日 上午11:34:31
 */
@Service(interfaceClass = ReleaseInfoAdminApi.class)
public class ReleaseInfoAdminProvider implements ReleaseInfoAdminApi {

    private Logger logger = LoggerFactory.getLogger(ReleaseInfoProvider.class);

    @Autowired
    private ReleaseInfoService releaseInfoService;

    @Autowired
    private ReleaseInfoProvider releaseInfoProvider;

    @Autowired
    private CoterieReleaseInfoProvider coterieReleaseInfoProvider;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private ResourceApi resourceApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private MessageAPI messageAPI;

    @Reference(lazy = true, check = false, timeout = 10000)
    private CountApi countApi;

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
            Assert.notNull(kid, "kid is null !");
            ReleaseInfoVo infoVo = releaseInfoService.selectByKid(kid);
            Assert.notNull(infoVo, "文章不存在！kid:" + kid);

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
            Assert.isTrue(kids.length > 0, "kids.length is 0 !");
            Assert.notNull(shelveFlag, "shelveFlag is null !");
            Assert.isTrue(CommonConstants.SHELVE_NO.equals(shelveFlag) || CommonConstants.SHELVE_YES.equals(shelveFlag),
                    "shelveFlag not is (10,11) !");
            Assert.notNull(lastUpdateUserId, "lastUpdateUserId is null !");
            logger.debug("文章，批量上下架操作。源kids：" + kids + ", shelveFlag :" + shelveFlag);

            // 查询 kids 中已经是shelveFlag 状态的记录 从kids 集合中剔除
            ReleaseInfoDto dto = new ReleaseInfoDto();
            dto.setKids(kids);
            dto.setShelveFlag(shelveFlag);
            List<Long> sheKids = releaseInfoService.selectKidByCondition(dto);

            // 查询kids 中已经是【删除】状态的 记录 从kids 集合中剔除
            ReleaseInfoDto delDto = new ReleaseInfoDto();
            delDto.setKids(kids);
            delDto.setDelFlag(CommonConstants.DELETE_YES);
            List<Long> delKids = releaseInfoService.selectKidByCondition(delDto);

            List<Long> kidList = new ArrayList<>(Arrays.asList(kids));
            kidList.removeAll(sheKids);
            kidList.removeAll(delKids);

            Long[] targetKids = kidList.toArray(new Long[]{});
            logger.debug("平文章，批量上下架操作。处理后kids：" + targetKids + ", shelveFlag :" + shelveFlag);

            if (targetKids.length < 1) {
                return ResponseUtils.returnObjectSuccess(0);
            }

            ReleaseInfoDto rd = new ReleaseInfoDto();
            rd.setKids(targetKids);

            ReleaseInfo up = new ReleaseInfo();
            up.setShelveFlag(shelveFlag);
            up.setLastUpdateUserId(lastUpdateUserId);
            int upCount = releaseInfoService.updateByCondition(up, rd);
            Response<Integer> result = ResponseUtils.returnObjectSuccess(upCount);

            // 没有未更新的记录
            if (upCount < 1) {
                return result;
            }

            for (Long kid : targetKids) {
                if (null == kid) {
                    continue;
                }

                ReleaseInfoVo releaseInfoVo = releaseInfoService.selectByKid(kid);
                if (null == releaseInfoVo) {
                    logger.debug("文章，批量下架操作资源不存在。kid：" + kid);
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

                    try {
                        // 发布数减一
                        countApi.commitCount(BehaviorEnum.Release, releaseInfoVo.getCreateUserId(), null, -1L);
                    } catch (Exception e) {
                        logger.error("我的发布数更新异常！", e);
                    }
                } else if (CommonConstants.SHELVE_YES.equals(shelveFlag)) {

                    // 创建者用户
                    UserSimpleVO createUser = ResponseUtils
                            .getResponseData(userApi.getUserSimple(releaseInfoVo.getCreateUserId()));
                    if (null == createUser) {
                        continue;
                    }
                    try {
                        // 私圈文章 聚合资源、好友动态 上线
                        if (null != releaseInfoVo.getCoterieId() && 0L != releaseInfoVo.getCoterieId()) {
                            this.coterieReleaseInfoProvider.commitResourceAndDynamic(releaseInfoVo, createUser);
                        } else {
                            // 平台文章 聚合资源上线
                            this.releaseInfoProvider.commitResourceAndDynamic(releaseInfoVo, createUser);
                        }
                    } catch (Exception e) {
                        logger.error("资源聚合上架接入异常！", e);
                    }
                    try {
                        // 发布数加一
                        countApi.commitCount(BehaviorEnum.Release, releaseInfoVo.getCreateUserId(), null, 1L);
                    } catch (Exception e) {
                        logger.error("我的发布数更新异常！", e);
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
