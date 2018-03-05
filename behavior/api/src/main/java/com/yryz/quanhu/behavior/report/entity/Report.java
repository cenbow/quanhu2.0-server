package com.yryz.quanhu.behavior.report.entity;



import com.yryz.common.entity.GenericEntity;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 11:29 2018/1/22
 */
public class Report extends GenericEntity {

    /**
     * 举报资源类型
     */
    private String moduleEnum;
    /**
     * 举报资源名称
     */
    private String moduleEnumName;
    /**
     * 举报资源ID
     */
    private String resourceId;
    /**
     * 资源作者
     */
    private long resourceUserId;
    /**
     * 举报类型
     */
    private String reportType;
    /**
     * 举报类型名称
     */
    private String reportDesc;
    /**
     * 举报内容
     */
    private String reportContext;
    /**
     * 举报状态
     */
    private Integer reportStatus;
    /**
     * 解决方案
     */
    private String solutionType;
    /**
     * 解决方案说明
     */
    private String solutionDesc;

    private Integer delFlag;

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getModuleEnumName() {
        return moduleEnumName;
    }

    public void setModuleEnumName(String moduleEnumName) {
        this.moduleEnumName = moduleEnumName;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public long getResourceUserId() {
        return resourceUserId;
    }

    public void setResourceUserId(long resourceUserId) {
        this.resourceUserId = resourceUserId;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public String getReportContext() {
        return reportContext;
    }

    public void setReportContext(String reportContext) {
        this.reportContext = reportContext;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }

    public String getSolutionType() {
        return solutionType;
    }

    public void setSolutionType(String solutionType) {
        this.solutionType = solutionType;
    }

    public String getSolutionDesc() {
        return solutionDesc;
    }

    public void setSolutionDesc(String solutionDesc) {
        this.solutionDesc = solutionDesc;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}
