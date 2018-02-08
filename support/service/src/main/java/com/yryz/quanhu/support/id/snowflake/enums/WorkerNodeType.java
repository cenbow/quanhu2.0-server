package com.yryz.quanhu.support.id.snowflake.enums;

import com.yryz.quanhu.support.id.snowflake.utils.ValuedEnum;

/**
 * WorkerNodeType
 * <li>CONTAINER: Such as Docker
 * <li>ACTUAL: Actual machine
 * @author danshiyu
 *
 */
public enum WorkerNodeType implements ValuedEnum<Integer> {
	/** 容器 */
	CONTAINER(1),
	/** 真实 */
	ACTUAL(2);
	 
   /**
     * Constructor with field of type
     */
    private WorkerNodeType(Integer type) {
        this.type = type;
    }
	 
    /**
     * Lock type
     */
	private final Integer type;

	@Override
	public Integer value() {
		return type;
	}

}
