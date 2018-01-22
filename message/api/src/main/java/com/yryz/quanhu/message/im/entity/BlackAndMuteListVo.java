package com.yryz.quanhu.message.im.entity;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
public class BlackAndMuteListVo implements Serializable {
    /**
     * 状态码
     * 参考: http://dev.netease.im/docs/product/IM%E5%8D%B3%E6%97%B6%E9%80%9A%E8%AE%AF/%E6%9C%8D%E5%8A%A1%E7%AB%AFAPI%E6%96%87%E6%A1%A3/code%E7%8A%B6%E6%80%81%E8%A1%A8
     */
    private String code;

    /**
     * 加黑的帐号列表
     */
    private List<String> blackList = Lists.newArrayList();

    /**
     * 被静音的帐号列表
     */
    private List<String> muteList = Lists.newArrayList();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<String> blackList) {
        this.blackList = blackList;
    }

    public List<String> getMuteList() {
        return muteList;
    }

    public void setMuteList(List<String> muteList) {
        this.muteList = muteList;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
