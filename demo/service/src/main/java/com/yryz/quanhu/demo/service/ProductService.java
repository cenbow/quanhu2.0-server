package com.yryz.quanhu.demo.service;

import com.github.pagehelper.PageHelper;
import com.yryz.quanhu.demo.dao.ProductDao;
import com.yryz.quanhu.demo.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDao;

    public List<Product> selectAll(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return productDao.selectAll();
    }

    public Product selectById(Long id) {
        return productDao.selectById(id);
    }

    @Transactional
    public int insert(Product product) {
        return productDao.insert(product);
    }

    @Transactional
    public int delete(Long id){
        int result = productDao.delete(id);
        if(id%2==0){
            throw new RuntimeException("事务回滚");
        }
        return result;
    }

    @Transactional
    public int update(Product product) {
        return productDao.update(product);
    }
}
