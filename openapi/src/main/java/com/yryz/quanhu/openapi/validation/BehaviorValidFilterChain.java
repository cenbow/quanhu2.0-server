package com.yryz.quanhu.openapi.validation;

import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.annotation.UserBehaviorValidation;
import org.aspectj.lang.JoinPoint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>  基于权限校验的Filter链，可自定义添加
 * Created on 2018/1/25 10:40
 * Created by huangxy
 */
public class BehaviorValidFilterChain implements IBehaviorValidFilter {

    private List<IBehaviorValidFilter> filters = new ArrayList<>();

    private Map<String,Object> context = new HashMap<>();

    private int idx = 0;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {
        if(filters==null||filters.size()==idx){
            return;
        }
        IBehaviorValidFilter filter = filters.get(idx);
        idx++;
        filter.filter(filterChain);
    }

    private UserBehaviorArgs userBehaviorArgs;
    private UserBehaviorValidation userBehaviorValidation;
    private JoinPoint joinPoint;


    public UserBehaviorArgs getUserBehaviorArgs() {
        return userBehaviorArgs;
    }

    public void setUserBehaviorArgs(UserBehaviorArgs userBehaviorArgs) {
        this.userBehaviorArgs = userBehaviorArgs;
    }

    public UserBehaviorValidation getUserBehaviorValidation() {
        return userBehaviorValidation;
    }

    public void setUserBehaviorValidation(UserBehaviorValidation userBehaviorValidation) {
        this.userBehaviorValidation = userBehaviorValidation;
    }

    public JoinPoint getJoinPoint() {
        return joinPoint;
    }

    public void setJoinPoint(JoinPoint joinPoint) {
        this.joinPoint = joinPoint;
    }

    public void execute(){
        filter(this);
    }

    public void addFilter(IBehaviorValidFilter filter){
        filters.add(filter);
    }

    public Map<String, Object> getContext() {
        return context;
    }

    public void setContextValue(String key, Object value){
        context.put(key,value);
    }


    public Long getLoginUserId(){
        return Long.valueOf(String.valueOf(context.get("loginUserId")));
    }

    public String getLoginToken(){
        return (String) context.get("loginToken");
    }
}
