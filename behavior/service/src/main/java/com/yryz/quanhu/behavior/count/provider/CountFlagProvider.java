package com.yryz.quanhu.behavior.count.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.api.CountFlagApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.count.service.CountService;
import com.yryz.quanhu.behavior.like.Service.LikeApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 13:24 2018/1/27
 */
@Service(interfaceClass = CountFlagApi.class)
public class CountFlagProvider implements CountFlagApi {

    private static final Logger logger = LoggerFactory.getLogger(CountFlagProvider.class);

    @Reference(check = false)
    private CountApi countApi;

    @Reference(check = false)
    private LikeApi likeApi;

    @Override
    public Response<Map<String, Long>> getAllCountFlag(String countType, Long kid, String page, Map<String, Object> map) {
        Map<String,Long> maps=new HashMap<String,Long>();
        try{
            maps=countApi.getCount(countType,kid,page).getData();
            if(!map.isEmpty()){
                maps.put("likeFlag",likeApi.getLikeFlag(map).getData());
            }
            return ResponseUtils.returnObjectSuccess(maps);
        }catch (Exception e){
            logger.error("", e);
            return ResponseUtils.returnException(e);
        }
    }
}
