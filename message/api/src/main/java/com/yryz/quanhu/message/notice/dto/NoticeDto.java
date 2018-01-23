package com.yryz.quanhu.message.notice.dto;

import com.yryz.quanhu.message.notice.entity.Notice;

import java.io.Serializable;

/**
 * @author pengnian
 * @ClassName: NoticeDto
 * @Description: NoticeDto
 * @date 2018-01-20 16:54:53
 */
public class NoticeDto implements Serializable {

    /**
     * 查询时间点
     */
    private String searchDate;

    /**
     * 公告id
     */
    private Long kid;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }
}
