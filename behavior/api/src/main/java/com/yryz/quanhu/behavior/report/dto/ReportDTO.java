package com.yryz.quanhu.behavior.report.dto;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.report.entity.Report;

import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 16:13 2018/1/20
 */
public class ReportDTO extends Report{

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 20;

    private String startDate;
    private String endDate;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
