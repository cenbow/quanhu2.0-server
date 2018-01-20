package com.yryz.quanhu.score.service;


import com.yryz.quanhu.score.vo.EventReportCount;
import com.yryz.quanhu.score.vo.EventReportVo;

/**
 * @author chengYunfei
 * @date 2017/11/20
 * @description 事件计数
 */
public interface EventReportAPI {

	/**
	 * 事件计数
	 * @param eventReportVo
	 * @return
	 */
	public EventReportCount eventReportCount(EventReportVo eventReportVo);

}
