/**
 * Copyright (c) 2018-2019 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2018年1月30日
 * Id: ResourceCanalDao.java, 2018年1月30日 下午2:43:00 yehao
 */
package com.yryz.quanhu.resource.dao.canal;

import java.util.ArrayList;
import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.entity.CanalChangeInfo;
import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.utils.GsonUtils;

/**
 * @author yehao
 * @version 2.0
 * @date 2018年1月30日 下午2:43:00
 * @Description Canal数据同步到ES
 */
@Service
public class ResourceCanalDao {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	/**
	 * 发送到canal
	 * @param resourceId
	 * @param heat
	 */
	public void sendToCannel(String resourceId , Long heat ){
		//资源热度 消息体  广播方式发送到 exchange(quanhu.canal.fanout)
		//common 工程项目里面有CanalMsgContent和CanalChangeInfo
		CanalMsgContent content=new CanalMsgContent();
		content.setDbName("quanhu");
		content.setEventType("update");
		content.setTableName("mongodb_resource_heat");
		content.setDataBefore(new ArrayList<>());
		List<CanalChangeInfo> dataAfter=new ArrayList<>();
		CanalChangeInfo kidInfo=new CanalChangeInfo();
		kidInfo.setName("kid");
		kidInfo.setUpdate(false);
		kidInfo.setValue(resourceId);//填真实值
		dataAfter.add(kidInfo);
		
		CanalChangeInfo lastHeatInfo=new CanalChangeInfo();
		lastHeatInfo.setName("lastHeat");
		lastHeatInfo.setUpdate(true);
		lastHeatInfo.setValue(heat.toString());//填真实值
		dataAfter.add(lastHeatInfo);
		content.setDataAfter(dataAfter);
		rabbitTemplate.setExchange("quanhu.canal.fanout");
		rabbitTemplate.convertAndSend(GsonUtils.parseJson(content));
	}

}
