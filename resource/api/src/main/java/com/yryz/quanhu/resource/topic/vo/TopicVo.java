package com.yryz.quanhu.resource.topic.vo;

import com.yryz.quanhu.user.vo.UserSimpleVO;

public class TopicVo {

    private Long kid;

    private String title;

    private String imgUrl;

    private String content;

    private Byte recommend;

    private Byte shelveFlag;

    private Byte delFlag;

    private Integer sort;

    private Integer replyCount;

    private Integer coterieId;

    private String contentSource;

    private UserSimpleVO user;


    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Byte getRecommend() {
        return recommend;
    }

    public void setRecommend(Byte recommend) {
        this.recommend = recommend;
    }

    public Byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getReplyCount() {
        return replyCount;
    }

    public void setReplyCount(Integer replyCount) {
        this.replyCount = replyCount;
    }

    public Integer getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Integer coterieId) {
        this.coterieId = coterieId;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }
}
