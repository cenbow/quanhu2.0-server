package com.yryz.quanhu.message.push.api;




import com.yryz.common.response.Response;
import com.yryz.quanhu.message.push.entity.PushReceived;
import com.yryz.quanhu.message.push.entity.PushReqVo;

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
	 * 
	 * 通用jpush推送业务<br/>
	 * 支持custId别名、tag群推、registrationId设备号推送，custId别名设备号混合推送<br/>
	 * 选择设备号推送时，存在设备号就按设备号推送，否则不推送<br/>
	 * 只有选择设备号和别名同时推送时，优先按设备号推送，设备号不存在时，用别名推送
	 * 注意rpc context传入appId
	 * @param reqVo
	 * @throws
	 */
	Response<Boolean> commonSendAlias(PushReqVo reqVo);
	
	
	/**
	 * 根据极光的msgid获取推送报告
	 * @author danshiyu
	 * @date 2017年9月19日
	 * @param msgId
	 * @return
	 * @throws
	 * @Description 注意rpc context传入appId
	 */
	Response<List<PushReceived>> getReceived(List<String> msgId);
}
