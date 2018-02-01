package com.yryz.quanhu.order.grow.dao.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.yryz.quanhu.grow.entity.GrowFlow;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;

/**
 * Created by syc on 2017/8/28.
 */
@Mapper
public interface GrowFlowDao {

    void save(GrowFlow gf);

    int update(GrowFlow gf);

    List<GrowFlow> getAll();
    
    List<GrowFlow> getPage(@Param("gfq") GrowFlowQuery gfq );
    
    long countgetPage(@Param("gfq") GrowFlowQuery gfq);
    
    List<GrowFlow> getAll(@Param("gfq") GrowFlowQuery gfq);

}
