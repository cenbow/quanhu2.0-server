package com.yryz.common.utils;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PageUtils {

    private static final Logger logger = LoggerFactory.getLogger(PageUtils.class);

    private static int maxPageSize = 100;

    /**
     * 开始分页
     *
     * @param currentPage  页码
     * @param pageSize 每页显示数量
     */
    public static Page startPage(int currentPage, int pageSize) {
        return PageUtils.startPage(currentPage, pageSize, true);
    }

    /**
     * 开始分页
     *
     * @param currentPage  页码
     * @param pageSize 每页显示数量
     * @param maxFlag  是否检查最大值
     */
    public static Page startPage(int currentPage, int pageSize, boolean maxFlag) {
        if(maxFlag){
            if(pageSize > maxPageSize){
                pageSize = maxPageSize;
                logger.error("pageSize大于maxPageSize，使用maxPageSize覆盖原pageSize 【"+maxPageSize+"】 ");
            }
        }

        return PageHelper.startPage(currentPage, pageSize);
    }

}
