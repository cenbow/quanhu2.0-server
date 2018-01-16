package com.yryz.common.service;

import com.yryz.common.entity.GenericEntity;
import com.yryz.component.rpc.RpcResponse;

import java.util.List;

public interface BaseService {

    /**
     * 通过kid获取实体
     * @param   t   返回实体类型
     * @param   kid
     * @return t
     * */
    <T> T get(Class<T> t, Long kid);

    /**
     * 获取实体列表
     * @param   t   查询条件
     * @return t
     * */
    <T> List<T> getList(T t);

    /**
     * 获取实体
     * @param   t   查询条件
     * @return t
     * */
    <T> T getEntity(T t);

    /**
     * 新增
     * @param t     参数
     * */
    <T> int insert(T t);

    /**
     * 跟据参数新增
     * @param t     参数
     * */
    <T> int insertByPrimaryKeySelective(T t);

    /**
     * 更新
     * @param t     参数
     * @return
     * */
    <T> int update(T t);

    /**
     * 新增或更新
     * @param   t     参数
     * @return
     * */
    <T extends GenericEntity> int saveOrUpdate(T t);

    /**
     * 删除
     * @param kid   主键
     * @return
     * */
    int delete(Long kid);

    /**
     * 判断PRC接口返回结果，判断若为null值，则抛出异常
     *
     * @param rpcResponse
     * @param <T>
     * @return
     */
    public <T> T isSuccessNotNull(RpcResponse<T> rpcResponse);

    /**
     * 判断PRC接口返回结果不判断null值，根据业务需求自己判断
     *
     * @param rpcResponse
     * @param <T>
     * @return
     */
    public <T> T isSuccess(RpcResponse<T> rpcResponse);

}
