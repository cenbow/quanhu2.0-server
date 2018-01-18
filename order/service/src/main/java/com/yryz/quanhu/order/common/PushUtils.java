//package com.yryz.quanhu.order.common;
//
//import java.util.Date;
//
//import com.rongzhong.component.common.utils.DateUtils;
//import com.rongzhong.component.common.utils.IdGen;
//import com.rongzhong.component.common.utils.StringUtils;
//
///**
// * @author yehao
// * @version 1.0
// * @date 2017年10月16日 下午3:20:00
// * @Description 推送工具类
// */
//public class PushUtils {
//	public static PushBody getPushbody(String from,String custId ,int messageType,String title,int colType)	
//	{
//		PushBody bodyVo = new PushBody();
//		if(StringUtils.isEmpty(from)){
//			bodyVo.setFrom(PushConstant.SYSINFO);
//		} else {
//			bodyVo.setFrom(from);
//		}
//		bodyVo.setMsgId(IdGen.uuid());
//		bodyVo.setMsgType(messageType);
//		bodyVo.setSendTime(DateUtils.formatDateTime(new Date()));
//		bodyVo.setTitle(title);
//		bodyVo.setTo(custId);
//		bodyVo.setColType(colType);
//		return bodyVo;
//	}
//}
