package com.yryz.quanhu.openapi.validation.filter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONObject;
import com.yryz.common.annotation.UserBehaviorArgs;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.openapi.validation.BehaviorArgsBuild;
import com.yryz.quanhu.openapi.validation.BehaviorValidFilterChain;
import com.yryz.quanhu.openapi.validation.IBehaviorValidFilter;
import com.yryz.quanhu.support.illegalWord.api.IllegalWordsApi;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/25 13:40
 * Created by huangxy
 *
 * 用户提交敏感词校验
 */
@Service
public class UserIllegalWordsFilter implements IBehaviorValidFilter {

    private static final Logger logger = LoggerFactory.getLogger(UserIllegalWordsFilter.class);

    @Reference(check = false)
    private IllegalWordsApi illegalWordsApi;

    @Autowired
    private BehaviorArgsBuild behaviorArgsBuild;

    @Override
    public void filter(BehaviorValidFilterChain filterChain) {

        logger.info("验证用户提交内容敏感词={}",filterChain.getContext());

        UserBehaviorArgs args = filterChain.getUserBehaviorArgs();
        JoinPoint joinPoint = filterChain.getJoinPoint();
        
        // 敏感词纯文字过滤,敏感词，不做强校验，异常不抛出，继续执行
        try{
            String [] keys = args.contexts();
            for(String key : keys){
                //获取值
                String value = (String) behaviorArgsBuild.getParameterValue(key,joinPoint.getArgs());

                //调用api进行敏感词过滤
                if(StringUtils.isBlank(value)){
                    continue;
                }
                //替换
                Response<String> rpc = illegalWordsApi.replaceIllegalWords(value,"*");
                if(rpc.success()){
                    //重新赋值
                    behaviorArgsBuild.replaceParameterValue(key,joinPoint.getArgs(),rpc.getData());
                }
            }
        }catch (Exception e){
            logger.warn("敏感词纯文字过滤，校验异常！",e);
        }
        // 敏感词contentSource富文本过滤
        try{
            String [] css = args.contentSources();
            for(String cs : css){
                //获取值
                String value = (String) behaviorArgsBuild.getParameterValue(cs,joinPoint.getArgs());

                //调用api进行敏感词过滤
                if(StringUtils.isBlank(value)){
                    continue;
                }
                //替换
                behaviorArgsBuild.replaceParameterValue(cs,joinPoint.getArgs(),this.replaceContentSourceIllagelWords(value));
            }
        }catch (Exception e){
            logger.warn("敏感词contentSource富文本过滤，校验异常！",e);
        }finally {
            filterChain.execute();
        }
    }
    
    /**  
    * @Description: contentSource富文本，文字部分敏感词过滤
    * @author wangheng
    * @param @param contentSource
    * @param @return
    * @return String
    * @throws  
    */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private String replaceContentSourceIllagelWords(String contentSource) {
        List<? extends HashMap> csJsonArrays = new ArrayList<>();
        try {
            csJsonArrays = JSONObject.parseArray(contentSource, new HashMap<String, Object>().getClass());
            if (!CollectionUtils.isEmpty(csJsonArrays)) {
                for (Map<String, Object> csJson : csJsonArrays) {
                    String text = (csJson.get("text") == null) ? "" : csJson.get("text").toString();
                    if (StringUtils.isBlank(text)) {
                        continue;
                    }
                    Response<String> res = illegalWordsApi.replaceIllegalWords(text, "*");
                    if (res != null && res.success() && ResponseConstant.SUCCESS.getCode().equals(res.getCode())) {
                        csJson.put("text", res.getData());
                    }
                }
            }
        } catch (Exception e) {
            logger.error("过滤'contentSource' 'text' 部分失败!", e);
            return contentSource;
        }
        return JSONObject.toJSONString(csJsonArrays);
    }
}
