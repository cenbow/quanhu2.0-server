package com.yryz.quanhu.other.category.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryTreeAdminVo implements Serializable {

    private Long kid;
    private Long parentKid;
    private Integer recommend;
    private String categoryName;
    private Integer categoryStatus;
    private Integer categoryType;
    private Integer categorySort;
    private List<CategoryAdminVo> subordinate = new ArrayList<>();

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getParentKid() {
        return parentKid;
    }

    public void setParentKid(Long parentKid) {
        this.parentKid = parentKid;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Integer getCategoryStatus() {
        return categoryStatus;
    }

    public void setCategoryStatus(Integer categoryStatus) {
        this.categoryStatus = categoryStatus;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getCategorySort() {
        return categorySort;
    }

    public void setCategorySort(Integer categorySort) {
        this.categorySort = categorySort;
    }

    public List<CategoryAdminVo> getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(List<CategoryAdminVo> subordinate) {
        this.subordinate = subordinate;
    }
}
