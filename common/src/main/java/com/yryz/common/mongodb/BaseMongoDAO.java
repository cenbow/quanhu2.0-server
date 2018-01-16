package com.yryz.common.mongodb;

import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

/**
 * @author zhongying
 * @version 1.0
 * @param <T>
 * @date 2017年4月7日 下午3:04:22
 * @Description MongoDB标准查询接口
 */
public interface BaseMongoDAO<T> {
	
    /** 
     * 通过条件查询实体(集合) 
     *  
     * @param query 
     */  
    public List<T> find(Query query) ;  
  
    /** 
     * 通过一定的条件查询一个实体 
     *  
     * @param query 
     * @return 
     */  
    public T findOne(Query query) ;  
  
    /** 
     * 通过条件查询更新数据 
     *  
     * @param query 
     * @param update 
     * @return 
     */  
    public T update(Query query, Update update) ;  
  
    /** 
     * 保存一个对象到mongodb 
     *  
     * @param entity 
     * @return 
     */  
    public T save(T entity) ;  
  
    /** 
     * 通过ID获取记录 
     *  
     * @param id 
     * @return 
     */  
    public T findById(String id) ;  
  
    /** 
     * 通过ID获取记录,并且指定了集合名(表的意思) 
     *  
     * @param id 
     * @param collectionName 
     *            集合名 
     * @return 
     */  
    public T findById(String id, String collectionName) ;  
      
    /** 
     * 分页查询 
     * @param page 
     * @param query 
     * @return 
     */  
    public Page<T> findPage(Page <T> page, Query query);
      
    /** 
     * 求数据总和 
     * @param query 
     * @return 
     */  
    public long count(Query query);  

}

