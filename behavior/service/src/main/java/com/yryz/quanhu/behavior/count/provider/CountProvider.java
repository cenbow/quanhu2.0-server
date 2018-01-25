package com.yryz.quanhu.behavior.count.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.service.CountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.provider
 * @Desc: 计数系统count服务
 * @Date: 2018/1/23.
 */
@Service(interfaceClass = CountApi.class, timeout = 1000 * 60)
public class CountProvider implements CountApi {

    private static final Logger logger = LoggerFactory.getLogger(CountProvider.class);

    @Autowired
    private CountService countService;

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
        try {
            if (StringUtils.isEmpty(page)) {
                page = "-1";
            }
            if (kid == null) {
                throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
            }
            List<BehaviorEnum> list = getBehaviorEnumList(countType);
            Map<String, Long> map = Maps.newConcurrentMap();
            for (BehaviorEnum behaviorEnum : list) {
                Long count = countService.getCount(kid.toString(), behaviorEnum.getCode(), page);
                map.put(behaviorEnum.getKey(), count);
            }
            return ResponseUtils.returnObjectSuccess(map);
        } catch (Exception e) {
            logger.error("getCount falid! ", e);
            return ResponseUtils.returnException(e);
        }
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
            }
        }
        return list;
    }
}
