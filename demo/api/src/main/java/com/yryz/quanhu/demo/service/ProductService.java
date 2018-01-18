package com.yryz.quanhu.demo.service;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.demo.entity.Product;

public interface ProductService {
    Response<PageList<Product>> selectAll(Integer pageNum, Integer pageSize, Long total);

    Response<Product> selectById(Long id);

    Response<Integer> insert(Product product);

    Response<Integer> delete(Long id);

    Response<Integer> update(Product product);
}
