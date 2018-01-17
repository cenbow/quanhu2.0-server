package com.yryz.quanhu.demo.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@Service(interfaceClass = DemoService.class)
public class DemoServiceImpl implements DemoService {
    // RedisTemplate 含有泛型,无法使用 @Autowired by type 注入,只能使用@Resource by name注入
    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @Override
    public String test(String name) {
        return "Hello " + name + " " + System.currentTimeMillis();
    }
}
