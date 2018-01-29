package com.yryz.quanhu.coterie.member.event;

import com.google.common.collect.Sets;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.*;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.message.message.api.MessageAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 私圈成员消息
 * @author chengyunfei
 *
 */
@Service
public class CoterieMemberMessageManager {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CoterieService coterieService;
	
	@Autowired
	private UserApi userApi;
	
	@Autowired
	private MessageAPI messageAPI;
	
	/**
	 * 加入私圈申请 消息
	 */
	public void joinMessage(Long userId,Long coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}

			Response<UserSimpleVO> response = userApi.getUserSimple(userId);

			UserSimpleVO user = null;
			if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
				user = response.getData();
				if (user == null) {
					throw new QuanhuException(ExceptionEnum.SysException);
				}
			}

//			fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.INTERACTIVE_TYPE);
			message.setLabel(MessageLabel.INTERACTIVE_COTERIE);
			message.setToCust(coterie.getOwnerId());
			message.setViewCode(MessageViewCode.INTERACTIVE_MESSAGE);
			message.setActionCode(MessageActionCode.APPLY_LIST);
			message.setTitle("新成员审核");
			message.setContent(user.getUserNickName()+"申请加入私圈"+(StringUtils.isEmpty(reason)?"":"，加入理由："+reason));
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			message.setCircleRoute(coterie.getCircleRoute());
			message.setCoterieId(coterieId.toString());
			
			InteractiveBody body=new InteractiveBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());
			body.setCircleName(coterie.getCircleName());
			body.setCoterieId(coterie.getCoterieId().toString());
			body.setCoterieName(coterie.getName());
			
			body.setCustId(userId.toString());
			body.setCustImg(user.getUserImg());
			body.setCustName(user.getUserNickName());
			message.setBody(body);
			messageAPI.sendMessage(message, true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
	
	/**
	 * 被踢出私圈 消息
	 */
	public void kickMessage(Long userId,Long coterieId,String reason){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}
UserSimpleVO user = null;
			Response<UserSimpleVO> response =userApi.getUserSimple(userId);
			if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
				user = response.getData();
				if (user == null) {
					throw new QuanhuException(ExceptionEnum.SysException);
				}
			}

//			fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.INTERACTIVE_TYPE);
			message.setLabel(MessageLabel.INTERACTIVE_COTERIE);
			message.setToCust(userId.toString());
			message.setViewCode(MessageViewCode.INTERACTIVE_MESSAGE);
			message.setActionCode(MessageActionCode.NONE);//无跳转
			message.setTitle("踢出私圈通知");
			message.setContent(user.getUserNickName()+"将你踢出了私圈"+(StringUtils.isEmpty(reason)?"":"，踢出理由："+reason));
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			
			InteractiveBody body=new InteractiveBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());
			body.setCircleName(coterie.getCircleName());
			body.setCoterieId(coterie.getCoterieId().toString());
			body.setCoterieName(coterie.getName());
			
			body.setCustId(coterie.getOwnerId());
			body.setCustImg(user.getUserImg());
			body.setCustName(user.getUserNickName());
			message.setBody(body);
			messageAPI.sendMessage(message, true);
		}catch (Exception e) {
			logger.error("notice Exception", e);
		}
	}
	
	/**
	 * 被禁言	 消息
	 */
	public void banSpeakMessage(Long userId,Long coterieId){
		try{
			CoterieInfo coterie=coterieService.find(coterieId);
			if(coterie==null){
				return;
			}

			UserSimpleVO user = null;
			Response<UserSimpleVO> response =userApi.getUserSimple(userId);
			if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
				user = response.getData();
				if (user == null) {
					throw new QuanhuException(ExceptionEnum.SysException);
				}
			}

//			fillCircleInfo(Arrays.asList(coterie));
			MessageVo message=new MessageVo();
			message.setType(MessageType.INTERACTIVE_TYPE);
			message.setLabel(MessageLabel.INTERACTIVE_COTERIE);
			message.setToCust(userId.toString());
			message.setViewCode(MessageViewCode.INTERACTIVE_MESSAGE);
			message.setActionCode(MessageActionCode.COTERIE_HOME);
			message.setTitle("禁言通知");
			message.setContent(user.getUserNickName()+"将你设为了禁言。");
			message.setImg(coterie.getIcon());
			message.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			message.setCircleRoute(coterie.getCircleRoute());
			message.setCoterieId(coterieId.toString());
			
			InteractiveBody body=new InteractiveBody();
			body.setBodyImg(coterie.getIcon());
			body.setBodyTitle(coterie.getName());
			body.setCircleName(coterie.getCircleName());
			body.setCoterieId(coterie.getCoterieId().toString());
			body.setCoterieName(coterie.getName());
			
			body.setCustId(coterie.getOwnerId());
			body.setCustImg(user.getUserImg());
			body.setCustName(user.getUserNickName());
			message.setBody(body);
			messageAPI.sendMessage(message, true);
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
