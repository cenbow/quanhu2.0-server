package com.yryz.quanhu.behavior.count.provider;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.collection.api.CollectionInfoApi;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.service.CountService;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.other.activity.api.ActivityInfoApi;


/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.provider
 * @Desc: 计数系统count服务
 * @Date: 2018/1/23.
 */
@Service(interfaceClass = CountApi.class)
public class CountProvider implements CountApi {

    private static final Logger logger = LoggerFactory.getLogger(CountProvider.class);

    @Autowired
    private CountService countService;

    @Reference
    private CollectionInfoApi collectionInfoApi;

    @Reference
    private LikeApi likeApi;

    @Reference
    private CoterieApi coterieApi;

    @Reference
    private ActivityInfoApi activityInfoApi;

    @Override
    public Response<Object> commitCount(BehaviorEnum behaviorEnum, Long kid, String page, Long count) {
        try {
            if (StringUtils.isEmpty(page)) {
                page = "-1";
            }
            if (kid != null) {
                countService.commitCount(behaviorEnum, kid.toString(), page, count);
            }
        } catch (Exception e) {
            logger.error("commitCount kid falid! kid:" + kid, e);
            return ResponseUtils.returnException(e);
        }
        return ResponseUtils.returnSuccess();
    }

    @Override
    public Response<Map<String, Long>> getCount(String countType, Long kid, String page) {
        Map<String, Long> map = Maps.newConcurrentMap();
        try {
            if (StringUtils.isEmpty(page)) {
                page = "-1";
            }
            if (kid == null) {
                throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
            }
            List<BehaviorEnum> list = getBehaviorEnumList(countType);
            for (BehaviorEnum behaviorEnum : list) {
                Long count = 0L;
                if (BehaviorEnum.Coterie.getCode().equals(behaviorEnum.getCode())) {
                    try {
                        count = ResponseUtils.getResponseData(coterieApi.getMyCoterieCount(kid.toString())).longValue();
                    } catch (Exception e) {
                        logger.error("get coterie count error", e);
                    }
                } else if (BehaviorEnum.Activity.getCode().equals(behaviorEnum.getCode())) {
                    try {
                        count = ResponseUtils.getResponseData(activityInfoApi.myListCount(kid)).longValue();
                    } catch (Exception e) {
                        logger.error("get activity count error", e);
                    }
                } else {
                    count = countService.getCount(kid.toString(), behaviorEnum.getCode(), page);
                }
                map.put(behaviorEnum.getKey(), count);
            }
        } catch (Exception e) {
            logger.error("getCount falid! ", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }

    @Override
    public Response<Map<String, Long>> getCountFlag(String countType, Long kid, String page, Long userId) {
        Map<String, Long> map = ResponseUtils.getResponseData(getCount(countType, kid, page));

        try {
            if (MapUtils.isEmpty(map) || userId == null) {
                return ResponseUtils.returnObjectSuccess(map);
            }
            // 如果查点赞数或者收藏数，自动拼装点赞状态和收藏状态
            for (String code : countType.split(",")) {
                switch (code) {
                    case "11":
                        //点赞状态
                        Map<String, Object> likeMap = Maps.newHashMap();
                        likeMap.put("resourceId", kid);
                        likeMap.put("userId", userId);
                        Long likeFlag = ResponseUtils.getResponseData(likeApi.getLikeFlag(likeMap));
                        if (likeFlag != null) {
                            map.put("likeFlag", likeFlag);
                        }
                        break;
                    case "15":
                        //收藏状态
                        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
                        collectionInfoDto.setResourceId(kid);
                        collectionInfoDto.setCreateUserId(userId);
                        Integer collectionFlag = ResponseUtils.getResponseData(collectionInfoApi.collectionStatus(collectionInfoDto));
                        if (collectionFlag != null) {
                            map.put("collectionFlag", collectionFlag.longValue());
                        }
                        break;
                }
            }
        } catch (Exception e) {
            logger.error("getCountFlag error!", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }

    public List<BehaviorEnum> getBehaviorEnumList(String countType) {
        List<BehaviorEnum> list = Lists.newArrayList();
        if (StringUtils.isEmpty(countType)) {
            return list;
        }
        for (String code : countType.split(",")) {
            switch (code) {
                case "10":
                    list.add(BehaviorEnum.Comment);
                    break;
                case "11":
                    list.add(BehaviorEnum.Like);
                    break;
                case "12":
                    list.add(BehaviorEnum.Read);
                    break;
                case "13":
                    list.add(BehaviorEnum.Transmit);
                    break;
                case "14":
                    list.add(BehaviorEnum.Reward);
                    break;
                case "15":
                    list.add(BehaviorEnum.Collection);
                    break;
                case "16":
                    list.add(BehaviorEnum.Share);
                    break;
                case "17":
                    list.add(BehaviorEnum.Report);
                    break;
                case "18":
                    list.add(BehaviorEnum.Release);
                    break;
                case "19":
                    list.add(BehaviorEnum.Coterie);
                    break;
                case "20":
                    list.add(BehaviorEnum.Activity);
                    break;
                case "21":
                    list.add(BehaviorEnum.RealRead);
                    break;
                case "22":
                    list.add(BehaviorEnum.TALK);
                    break;
            }
        }
        return list;
    }

    @Override
    public Response<Map<Long, Map<String, Long>>> getCount(String countType, List<Long> kids, String page) {
        Map<Long, Map<String, Long>> map = Maps.newConcurrentMap();
        try {
            if (StringUtils.isEmpty(page)) {
                page = "-1";
            }
            if (kids == null || kids.size() == 0) {
                throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
            }
            List<BehaviorEnum> list = getBehaviorEnumList(countType);
            //根据kids初始化返回map集合
            for (Long kid : kids) {
                if (kid == null) {
                    //不允许kids存在空值
                    logger.error("kids list has null!");
                    return ResponseUtils.returnObjectSuccess(map);
                }
                map.put(kid, Maps.newHashMap());
            }
            //循环查询不同类型的数据
            for (BehaviorEnum behaviorEnum : list) {
                List<Long> counts = countService.getCount(kids, behaviorEnum.getCode(), page);
                for (int i = 0; i < counts.size(); i++) {
                    Long count = counts.get(i);
                    Long kid = kids.get(i);
                    map.get(kid).put(behaviorEnum.getKey(), count);
                }
            }
        } catch (Exception e) {
            logger.error("getCount falid! ", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }

    @Override
    public Response<Map<Long, Map<String, Long>>> getCountFlag(String countType, List<Long> kids, String page, Long userId) {
        Map<Long, Map<String, Long>> map = ResponseUtils.getResponseData(getCount(countType, kids, page));
        try {
            if (userId != null) {
                // 如果查点赞数或者收藏数，自动拼装点赞状态和收藏状态
                for (String code : countType.split(",")) {
                    switch (code) {
                        case "11":
                            //点赞状态
                            Map<String, Integer> likeMap = ResponseUtils.getResponseData(likeApi.getLikeFlagBatch(kids, userId));
                            for (Long kid : map.keySet()) {
                                map.get(kid).put("likeFlag", likeMap.get(kid.toString()).longValue());
                            }
                            break;
//                    case "15":
//                        //收藏状态
//                        CollectionInfoDto collectionInfoDto = new CollectionInfoDto();
//                        collectionInfoDto.setResourceId(kid);
//                        collectionInfoDto.setCreateUserId(userId);
//                        Integer collectionFlag = ResponseUtils.getResponseData(collectionInfoApi.collectionStatus(collectionInfoDto));
//                        if (collectionFlag != null) {
//                            map.put("collectionFlag", collectionFlag.longValue());
//                        }
//                        break;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getCountFlag error!", e);
        }
        return ResponseUtils.returnObjectSuccess(map);
    }
}
