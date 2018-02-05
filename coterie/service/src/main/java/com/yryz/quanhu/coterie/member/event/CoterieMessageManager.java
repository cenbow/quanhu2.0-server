package com.yryz.quanhu.coterie.member.event;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.common.message.*;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;

/**
 * 私圈消息管理
 * @author jk
 *
 */
@Service
public class CoterieMessageManager {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Reference
	private MessageAPI messageAPI;
	

	@Resource
	private CoterieService coterieService;
	
	/**
	 * 审批通过 消息
	 * @param
	 */
	public void agreeMessage(String coterieId){
		try{
			CoterieInfo coterie=coterieService.find(Long.parseLong(coterieId));
			if(coterie==null){
				return;
			}
			//fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.SYSTEM_TYPE);
			message.setLabel(MessageLabel.SYSTEM_REVIEW);
			message.setToCust(coterie.getOwnerId());
			message.setViewCode(MessageViewCode.SYSTEM_MESSAGE_2);
			message.setActionCode(MessageActionCode.COTERIE_HOME);
			message.setTitle("私圈审核通知");
			message.setContent("您申请创建的私圈已审核通过！");
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			message.setCoterieId(coterieId);
			
			SystemBody body=new SystemBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());

			body.setCoterieId(coterie.getCoterieId()+"");
			body.setCoterieName(coterie.getName());
			message.setBody(body);
			messageAPI.sendMessage(message,true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
	
	/**
	 * 审批未通过消息
	 * @param coterieId
	 */
	public void disagreeMessage(String coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(Long.parseLong(coterieId));
			if(coterie==null){
				return;
			}
			//fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.SYSTEM_TYPE);
			message.setLabel(MessageLabel.SYSTEM_REVIEW);
			message.setToCust(coterie.getOwnerId());
			message.setViewCode(MessageViewCode.SYSTEM_MESSAGE_2);
			message.setActionCode(MessageActionCode.MYCOTERIE);
			message.setTitle("私圈审核通知");
			message.setContent("您申请创建的私圈审核未通过"+(StringUtils.isEmpty(reason)?"":"，原因："+reason));
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			SystemBody body=new SystemBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());

			body.setCoterieId(coterie.getCoterieId()+"");
			body.setCoterieName(coterie.getName());
			message.setBody(body);
			messageAPI.sendMessage(message,true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
	
	/**
	 * 私圈下架 消息
	 */
	public void offlineMessage(String coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(Long.parseLong(coterieId));
			if(coterie==null){
				return;
			}
			//fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.SYSTEM_TYPE);
			message.setLabel(MessageLabel.SYSTEM_REVIEW);
			message.setToCust(coterie.getOwnerId());
			message.setViewCode(MessageViewCode.SYSTEM_MESSAGE_2);
			message.setActionCode(MessageActionCode.MYCOTERIE);
			message.setTitle("私圈审核通知");
			message.setContent("您的私圈被管理员下线"+(StringUtils.isEmpty(reason)?"":"，原因："+reason));
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			SystemBody body=new SystemBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());
			body.setCoterieId(coterie.getCoterieId()+"");
			body.setCoterieName(coterie.getName());
			message.setBody(body);
			messageAPI.sendMessage(message,true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
	
//	private void fillCircleInfo(List<CoterieInfo> infoList){
//		if(infoList==null || infoList.isEmpty()){
//			return;
//		}
//		Set<String> circleIdSet=Sets.newHashSet();
//		for (int i = 0; i < infoList.size(); i++) {
//			String circleId=infoList.get(i).getCircleId();
//			if(StringUtils.isNotEmpty(circleId)){
//				circleIdSet.add(circleId);
//			}
//		}
//		if(circleIdSet.isEmpty()){
//			return;
//		}
//
//		Map<String,CircleInfo > maps=circleAPI.getCircle(circleIdSet, false);
//		for (int i = 0; i < infoList.size(); i++) {
//			CoterieInfo o=infoList.get(i);
//			CircleInfo circle=maps.get(o.getCircleId());
//			if(circle!=null){
//				o.setCircleName(circle.getCircleName());
//				o.setCircleRoute(circle.getCircleRoute());
//			}
//		}
//	}
}
