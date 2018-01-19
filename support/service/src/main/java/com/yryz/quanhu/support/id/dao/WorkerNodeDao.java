/*
 * WorkerNodeMapper.java
 * Copyright (c) 2012,融众网络技术有限公司(www.11186.com)
 * All rights reserved.
 * ---------------------------------------------------------------------
 * 2017-08-23 Created
 */
package com.yryz.quanhu.support.id.dao;

import com.yryz.quanhu.support.id.entity.WorkerNode;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface WorkerNodeDao {
	  /**
     * Get {@link WorkerNodeEntity} by node host
     * 
     * @param host
     * @param port
     * @return
     */
    WorkerNode getWorkerNodeByHostPort(@Param("host") String host, @Param("port") String port);

    /**
     * Add {@link WorkerNodeEntity}
     * 
     * @param workerNodeEntity
     */
    void addWorkerNode(WorkerNode workerNodeEntity);
}