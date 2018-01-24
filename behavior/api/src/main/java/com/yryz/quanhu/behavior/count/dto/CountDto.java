package com.yryz.quanhu.behavior.count.dto;

/**
 * @Author: liupan
 * @Path: com.yryz.quanhu.behavior.count.dto
 * @Desc: 计数查询DTO
 * @Date: 2018/1/24.
 */
public class CountDto {

    /**
     * 统计数类型，必传，多条件查询已逗号分隔。评论数10，点赞数11，阅读数12，转发数13，打赏数14，收藏数15，分享数16，举报数17，发布数18，私圈数19，活动数20
     */
    private String countType;

    /**
     * 业务ID，必传
     */
    private Long kid;

    public String getCountType() {
        return countType;
    }

    public void setCountType(String countType) {
        this.countType = countType;
    }

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }
}
