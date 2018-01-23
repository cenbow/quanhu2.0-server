package com.yryz.quanhu.support.category.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CategoryDiscoverVo implements Serializable {

    private Long categoryId;
    private String categoryName;
    private List<CategoryVo> subordinate = new ArrayList<>();

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<CategoryVo> getSubordinate() {
        return subordinate;
    }

    public void setSubordinate(List<CategoryVo> subordinate) {
        this.subordinate = subordinate;
    }
}
