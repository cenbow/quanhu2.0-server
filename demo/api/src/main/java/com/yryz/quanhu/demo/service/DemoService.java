package com.yryz.quanhu.demo.service;

import java.util.List;

import com.yryz.quanhu.demo.vo.DemoVo;

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
