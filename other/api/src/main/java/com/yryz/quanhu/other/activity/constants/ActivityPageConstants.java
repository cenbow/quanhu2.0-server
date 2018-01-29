package com.yryz.quanhu.other.activity.constants;

public class ActivityPageConstants {

    public static long getCurrentPage(Integer currentPage, Integer pageSize) {
        return (currentPage -1) * pageSize;
    }

    public static long getPageSize(Integer currentPage, Integer pageSize) {
        return ((currentPage -1) * pageSize) + pageSize -1;
    }

}
