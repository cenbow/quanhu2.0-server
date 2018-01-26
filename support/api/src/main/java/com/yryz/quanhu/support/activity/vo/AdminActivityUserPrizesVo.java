package com.yryz.quanhu.support.activity.vo;


import java.io.Serializable;
import java.util.List;

import com.yryz.quanhu.support.activity.entity.ActivityPrizes;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author zhangkun
 * @version 1.0
 * @date 2017/9/6
 * @description
 */
public class AdminActivityUserPrizesVo implements Serializable {
    private static final long serialVersionUID = 8911650572892270101L;

    private String msg;

    private String userId;
    private String nickName;
    private List<ActivityPrizes> prizes;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<ActivityPrizes> getPrizes() {
        return prizes;
    }

    public void setPrizes(List<ActivityPrizes> prizes) {
        this.prizes = prizes;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
