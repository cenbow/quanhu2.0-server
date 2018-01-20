package com.yryz.quanhu.score.service;


import com.yryz.quanhu.score.vo.EventRecordVo;

/**
 * @author chengYunfei
 * @date 2017/11/20
 * @description 事件管理API(阅读 评论 点赞 分享 收藏 )
 */
public interface EventRecordAPI {

	/**
	 * 保存事件
	 * @param eventRecordVo
	 * @return
	 */
	public Long addEventRecord(EventRecordVo eventRecordVo);

}
