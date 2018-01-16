package com.yryz.common.web;

import com.github.pagehelper.PageInfo;
import com.yryz.component.rpc.dto.PageList;

import java.io.Serializable;
import java.util.List;

public class PageModel<T extends Serializable> {

    public PageModel() {

    }

    public PageList<T> getPageList(List<T> list) {
        PageInfo<T> page = new PageInfo<>(list);
        PageList<T> pageList = new PageList<>();
        pageList.setCurrentPage(page.getPageNum());
        pageList.setPageSize(page.getPageSize());
        pageList.setCount(page.getTotal());
        pageList.setEntities(page.getList());
        return pageList;
    }

    public <O> PageList<T> getPageList(List<O> originList, List<T> targetList) {
        PageInfo<O> page = new PageInfo<>(originList);
        PageList<T> pageList = new PageList<>();
        pageList.setCurrentPage(page.getPageNum());
        pageList.setPageSize(page.getPageSize());
        pageList.setCount(page.getTotal());
        pageList.setEntities(targetList);
        return pageList;
    }

}
