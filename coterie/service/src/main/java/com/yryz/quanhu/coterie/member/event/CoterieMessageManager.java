package com.yryz.quanhu.coterie.member.event;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.message.*;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.message.message.api.MessageAPI;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
	public void agreeMessage(Long coterieId){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}
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
			message.setCoterieId(coterieId.toString());
			
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
	public void disagreeMessage(Long coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}
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

			body.setCoterieId(coterieId.toString());
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
	public void offlineMessage(Long coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}
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
			body.setCoterieId(coterieId.toString());
			body.setCoterieName(coterie.getName());
			message.setBody(body);
			messageAPI.sendMessage(message,true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
}
