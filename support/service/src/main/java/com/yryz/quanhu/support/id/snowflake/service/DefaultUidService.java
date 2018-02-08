package com.yryz.quanhu.support.id.snowflake.service;

import com.yryz.quanhu.support.id.snowflake.exception.UidGenerateException;

/**
 * Represents a unique id generator.
 * @author danshiyu
 *
 */
public interface DefaultUidService {
	 /**
     * Get a unique ID
     *
     * @return UID
     * @throws UidGenerateException
     */
    long getUID() throws UidGenerateException;

    /**
     * Parse the UID into elements which are used to generate the UID. <br>
     * Such as timestamp & workerId & sequence...
     *
     * @param uid
     * @return Parsed info
     */
    String parseUID(long uid);
}
