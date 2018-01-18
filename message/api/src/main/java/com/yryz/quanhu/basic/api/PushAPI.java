package com.yryz.quanhu.basic.api;




import com.yryz.quanhu.basic.entity.PushReceived;
import com.yryz.quanhu.basic.entity.PushReqVo;

import java.util.List;

/**
 * 
 * @author
 * @version 1.0
 * @date 2017年10月18日 上午9:45:36
 * @Description 极光接口
 */
public interface PushAPI {
	/**
	 * jpush消息推送<br/>
	 * 关闭通知
	 * @param userId
	 *            用户Id
	 * @param notification
	 *            通知
	 * @param msg
	 *            内容 (json字符串)
	 * @return
	 * @throws
	 */
	@Deprecated
	public void sendByAlias(String userId, String notification, String msg);

	/**
	 * jpush消息推送<br/>
	 * 关闭通知
	 * @param userIds
	 *            用户Ids
	 * @param notification
	 *            通知
	 * @param msg
	 *            内容(json字符串)
	 * @return
	 * @throws
	 */
	@Deprecated
	public void sendByAlias(List<String> userIds, String notification, String msg);

	/**
	 * jpush消息推送 byTag<br/>
	 * 关闭通知
	 * @param tag
	 * @param notification 通知
	 * @param msg(json字符串)
	 * @return
	 * @throws
	 */
	@Deprecated
	public void sendByTag(String tag, String notification, String msg);
	
	/**
	 * jpush消息推送 byTag<br/>
	 * 关闭通知
	 * @param tags
	 * @param notification 通知
	 * @param msg(json字符串)
	 * @return
	 * @throws
	 */
	@Deprecated
	public void sendByTag(List<String> tags, String notification, String msg);
	
	/**
	 * 
	 * 通用jpush推送业务<br/>
	 * 支持custId别名、tag群推、registrationId设备号推送，custId别名设备号混合推送<br/>
	 * 选择设备号推送时，存在设备号就按设备号推送，否则不推送<br/>
	 * 只有选择设备号和别名同时推送时，优先按设备号推送，设备号不存在时，用别名推送
	 * @param reqVo
	 * @throws
	 */
	public void commonSendAlias(PushReqVo reqVo);
	
	
	/**
	 * 根据极光的msgid获取推送报告
	 * @author danshiyu
	 * @date 2017年9月19日
	 * @param msgId
	 * @return
	 * @throws
	 * @Description 注意rpc context传入appId
	 */
	public List<PushReceived> getReceived(List<String> msgId);
}
