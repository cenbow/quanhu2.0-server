package com.yryz.quanhu.other.category.vo;

import java.io.Serializable;

public class CategoryCheckedVo implements Serializable {

    private Long categoryId;
    private String categoryName;
    private Integer checked;

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

    public Integer getChecked() {
        return checked;
    }

    public void setChecked(Integer checked) {
        this.checked = checked;
    }
}
