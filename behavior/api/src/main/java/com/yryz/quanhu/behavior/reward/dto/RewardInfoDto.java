package com.yryz.quanhu.behavior.reward.dto;

import com.yryz.quanhu.behavior.reward.entity.RewardInfo;

/**
* @author wangheng
* @date 2018年1月26日 下午8:38:10
*/
public class RewardInfoDto extends RewardInfo {

    private static final long serialVersionUID = 1L;

    /**页码*/
    private Integer currentPage = 1;
    /**每页大小*/
    private Integer pageSize = 30;

    /**  
    * @Fields beginDate : 开始日期
    */
    private String beginDate;

    /**  
    * @Fields endDate : 结束日期
    */
    private String endDate;

    /**  
    * @Fields : 查询类型：我打赏的-人-列表，我打赏的-资源-列表，打赏我的-人-列表，打赏我的-资源-列表
    */
    private Byte queryType;

    /**  
    * @Fields orderType : 排序类型 (1:时间最新,2:时间最早)
    */
    private Byte orderType;
    
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

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Byte getQueryType() {
        return queryType;
    }

    public void setQueryType(Byte queryType) {
        this.queryType = queryType;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }
}
