package com.yryz.quanhu.user.service;

import com.yryz.quanhu.user.vo.DemoVo;

import java.util.List;

public interface DemoService {
    static String cacheKey(Long id) {
        return "quanhu:demo:" + id;
    }

    DemoVo persist(DemoVo demoVo);

    DemoVo remove(Long id);

    DemoVo merge(DemoVo demoVo);

    DemoVo find(Long id);

    List<DemoVo> find(List<Long> ids);

    List<DemoVo> find(DemoVo demoVo);

    List<DemoVo> find(Integer start, Integer limit);

    void test();
}
