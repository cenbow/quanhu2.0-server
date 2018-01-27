package com.yryz.quanhu.behavior.collection.dto;

import java.io.Serializable;

/**
 * @ClassName: CollectionInfoDto
 * @Description: CollectionInfoDto
 * @author jiangzhichao
 * @date 2018-01-26 17:57:44
 *
 */
public class CollectionInfoDto implements Serializable {

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 10;
    /** 资源id */
    private Long resourceId;
    /** 资源类型 */
    private String moduleEnum;
    /** 私圈id */
    private Long coterieId;
    /** 创建用户id */
    private Long createUserId;

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

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }
}
