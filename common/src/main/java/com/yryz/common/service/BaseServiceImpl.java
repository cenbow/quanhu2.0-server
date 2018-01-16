package com.yryz.common.service;

import com.yryz.common.dao.BaseDao;
import com.yryz.common.entity.GenericEntity;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.springframework.util.Assert;

import java.util.List;

public abstract class BaseServiceImpl implements BaseService {

    protected abstract BaseDao getDao();

    /**
     * 通过kid获取实体
     * @param   t   返回实体类型
     * @param   kid
     * @return t
     * */
    public <T> T get(Class<T> t, Long kid){
        return getDao().selectByKid(t, kid);
    }

    /**
     * 获取实体列表
     * @param   t   查询条件
     * @return t
     * */
    public <T> List<T> getList(T t) {
        return getDao().getList(t);
    }

    /**
     * 获取实体
     * @param   t   查询条件
     * @return t
     * */
    public <T> T getEntity(T t) {
        List<T> list = getDao().getList(t);
        if(!CollectionUtils.isEmpty(list)){
            if(list.size() == 1){
                return list.get(0);
            }else if(list.size() > 1){
                throw new TooManyResultsException("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
            }
        } else {
            return null;
        }

        return null;
    }

    /**
     * 新增
     * @param t     参数
     * */
    public <T> int insert(T t){
        return getDao().insert(t);
    }

    /**
     * 跟据参数新增
     * @param t     参数
     * */
    public <T> int insertByPrimaryKeySelective(T t){
        return getDao().insertByPrimaryKeySelective(t);
    };

    /**
     * 更新
     * @param t     参数
     * @return
     * */
    public <T> int update(T t){
        return getDao().update(t);
    }

    /**
     * 新增或更新
     * @param   t     参数
     * @return
     * */
    public <T extends GenericEntity> int saveOrUpdate(T t) {
        GenericEntity entity = (GenericEntity)t;
        if(entity.getKid() != null){
            return update(t);
        }else{
            return insert(t);
        }
    }

    /**
     * 删除
     * @param kid   主键
     * @return
     * */
    public int delete(Long kid) {
        return getDao().delete(kid);
    }

    /**
     * 判断PRC接口返回结果，判断若为null值，则抛出异常
     *
     * @param rpcResponse
     * @param <T>
     * @return
     */
    public <T> T isSuccessNotNull(Response<T> rpcResponse) {
        Assert.notNull(rpcResponse, "调用PRC接口异常！");
        if (rpcResponse.success()) {
            T t = rpcResponse.getData();
            if (t == null) {
                throw QuanhuException.busiError("调用RPC接口，返回结果数据为：null");
            }
            return t;
        }
        throw new QuanhuException(rpcResponse.getCode(), rpcResponse.getMsg(), rpcResponse.getErrorMsg());
    }

    /**
     * 判断PRC接口返回结果不判断null值，根据业务需求自己判断
     *
     * @param rpcResponse
     * @param <T>
     * @return
     */
    public <T> T isSuccess(Response<T> rpcResponse) {
        Assert.notNull(rpcResponse, "调用PRC接口异常！");
        if (rpcResponse.success()) {
            return rpcResponse.getData();
        }
        throw new QuanhuException(rpcResponse.getCode(), rpcResponse.getMsg(), rpcResponse.getErrorMsg());
    }
}
