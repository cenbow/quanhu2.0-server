package com.yryz.quanhu.demo.dao;

import com.yryz.quanhu.demo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductDao {
    List<Product> selectAll();

    Product selectById(Long id);

    int insert(Product product);

    int delete(Long id);

    int update(Product product);
}
