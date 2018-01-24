package com.yryz.quanhu.score.service;

import com.yryz.quanhu.score.vo.EventInfo;

/**
 * 签到
 * @author lsn
 *
 */
public interface EventSignApiService {
	/**
	 * 签到
	 * @param ei
	 */
	void commitSignEvent(EventInfo ei);
}
