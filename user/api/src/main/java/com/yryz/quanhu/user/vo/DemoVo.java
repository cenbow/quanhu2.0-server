package com.yryz.quanhu.user.vo;

import java.io.Serializable;
import java.util.Date;

public class DemoVo implements Serializable {
    private Long id;
    private String name;
    private Integer age;
    private Date createDate;

    public DemoVo() {
    }

    public DemoVo(Long id, String name, Integer age, Date createDate) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "DemoVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", createDate=" + createDate +
                '}';
    }
}