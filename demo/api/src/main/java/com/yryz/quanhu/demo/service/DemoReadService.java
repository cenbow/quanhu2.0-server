package com.yryz.quanhu.demo.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.quanhu.demo.vo.DemoVo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * API包不应该仅仅包含RPC接口,也应该包含SDK(方便消费端使用的相关类)
 * Demo读服务 尽可能直接访问redis缓存而不调用rpc 减少一次网络传输
 */
@Service
public class DemoReadService {
    protected final Log logger = LogFactory.getLog(DemoReadService.class);

    @Autowired
    private DemoService demoService;

    @Resource
    private RedisTemplate<String, DemoVo> redisTemplate;

    public DemoVo find(Long id) {
        DemoVo cache = redisTemplate.opsForValue().get(DemoService.cacheKey(id));
        if (null != cache) {
            logger.info("find in cache");
            return cache;
        }
        logger.info("find by rpc");
        return demoService.find(id);
    }

    /**
     * 先从缓存批量拿,缓存不存在list条目为null,收集所有null的id,发送rpc请求拿,最后合并
     */
    public List<DemoVo> find(List<Long> ids) {
        // 准备批量查询缓存的key
        List<String> keyList = new ArrayList<>();
        for (Long id : ids) {
            keyList.add(DemoService.cacheKey(id));
        }

        List<DemoVo> demoVoList = redisTemplate.opsForValue().multiGet(keyList);

        // 收集不存在缓存的条目
        List<Integer> nullIndexList = new ArrayList<>();
        List<Long> nullIdList = new ArrayList<>();

        int index = 0;
        for (DemoVo demoVo : demoVoList) {
            if (demoVo == null) {
                nullIndexList.add(index);
                nullIdList.add(ids.get(index));
            }
            index++;
        }

        if (!nullIndexList.isEmpty()) {   // 没有全部命中缓存
            List<DemoVo> demoVoRpcList = demoService.find(nullIdList);
            // 合并进返回list
            int indexRpc = 0;
            for (DemoVo demoVoRpc : demoVoRpcList) {
                demoVoList.set(nullIndexList.get(indexRpc), demoVoRpc);
                indexRpc++;
            }
        }

        return demoVoList;
    }
}
