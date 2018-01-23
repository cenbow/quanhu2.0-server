package com.yryz.quanhu.message.notice.vo;

import com.yryz.quanhu.message.notice.entity.Notice;

import java.io.Serializable;
import java.util.List;

/**
 * @author pengnian
 * @ClassName: NoticeVo
 * @Description: NoticeVo
 * @date 2018-01-20 16:54:53
 */
public class NoticeVo implements Serializable {
    /**
     * 已下线公告id集合
     */
    private List<Long> offlineNoticeIdList;

    /**
     * 上线公告集合
     */
    private List<Notice> onlineNoticeList;

    public NoticeVo() {
    }

    public NoticeVo(List<Notice> onlineNoticeList, List<Long> offlineNoticeIdList) {
        this.offlineNoticeIdList = offlineNoticeIdList;
        this.onlineNoticeList = onlineNoticeList;
    }

    public List<Long> getOfflineNoticeIdList() {
        return offlineNoticeIdList;
    }

    public void setOfflineNoticeIdList(List<Long> offlineNoticeIdList) {
        this.offlineNoticeIdList = offlineNoticeIdList;
    }

    public List<Notice> getOnlineNoticeList() {
        return onlineNoticeList;
    }

    public void setOnlineNoticeList(List<Notice> onlineNoticeList) {
        this.onlineNoticeList = onlineNoticeList;
    }
}
