/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2017年5月16日
 * Id: ColumnDao.java,v 1.0 2017年5月16日 下午3:22:57 yehao
 */
package com.yryz.quanhu.order.sdk.dao;

import com.yryz.quanhu.order.sdk.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author liupan
 * @ClassName IOrderDao
 * @date 2017年9月16日 下午3:22:57
 * @Description
 */
@Mapper
public interface OrderDao {

    Order selectByKid(Long kid);

    List<Order> selectLatestOrder(@Param("moduleEnum") String moduleEnum, @Param("userId") long userId,
                           @Param("resourceId") long resourceId);

    void insertOrder(Order order);

    int updateOrderState(@Param("kid") Long kid, @Param("orderState") Integer orderState);

}
