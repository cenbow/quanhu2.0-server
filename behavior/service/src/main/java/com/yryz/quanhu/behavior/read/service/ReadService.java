package com.yryz.quanhu.behavior.read.service;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.read.service
 * @Desc:
 * @Date: 2018/1/30.
 */
public interface ReadService {

    public void read(Long kid);

    public void excuteViewCountJob();
}
