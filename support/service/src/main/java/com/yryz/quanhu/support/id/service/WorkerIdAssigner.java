package com.yryz.quanhu.support.id.service;

/**
 * Represents a worker id assigner 
 * @author danshiyu
 *
 */
public interface WorkerIdAssigner {
	/**
     * Assign worker id 
     * 
     * @return assigned worker id
     */
    long assignWorkerId();
}
