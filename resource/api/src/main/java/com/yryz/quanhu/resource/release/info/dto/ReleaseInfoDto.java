package com.yryz.quanhu.resource.release.info.dto;

import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;

/**
* @author wangheng
* @date 2018年1月22日 下午4:47:05
*/
public class ReleaseInfoDto extends ReleaseInfo {

    private static final long serialVersionUID = 1L;

    /**  
    * @Fields beginDate : 开始日期
    */
    private String beginDate;

    /**  
    * @Fields endDate : 结束日期
    */
    private String endDate;

    /**  
    * @Fields searchKey : 检索关键字
    */
    private String searchKey;

    /**  
    * @Fields orderType : 排序类型 
    */
    private Byte orderType = ReleaseConstants.OrderType.time_new;

    /**  
    * @Fields payFlag : 付费标识（0：免费，1：付费）
    */
    private Byte payFlag;

    private int currentPage = 1;

    private int pageSize = 30;

    private Long[] kids;
    
    /**  
    * @Fields : 所有私圈：true,false
    */
    private Boolean allCoterie;
    
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

    public String getSearchKey() {
        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Byte getOrderType() {
        return orderType;
    }

    public void setOrderType(Byte orderType) {
        this.orderType = orderType;
    }

    public Byte getPayFlag() {
        return payFlag;
    }

    public void setPayFlag(Byte payFlag) {
        this.payFlag = payFlag;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long[] getKids() {
        return kids;
    }

    public void setKids(Long[] kids) {
        this.kids = kids;
    }

    public Boolean getAllCoterie() {
        return allCoterie;
    }

    public void setAllCoterie(Boolean allCoterie) {
        this.allCoterie = allCoterie;
    }
}
