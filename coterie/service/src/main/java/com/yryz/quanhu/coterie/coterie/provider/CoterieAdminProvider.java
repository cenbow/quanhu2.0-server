package com.yryz.quanhu.coterie.coterie.provider;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.google.common.collect.Sets;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.common.CoterieConstant;
import com.yryz.quanhu.coterie.coterie.exception.DatasOptException;
import com.yryz.quanhu.coterie.coterie.exception.ServiceException;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAuditRecordInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieMemberInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.MemberSearchParam;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAuditRecord;
//import com.yryz.service.api.user.entity.CustInfo;
//import com.yryz.service.circle.common.event.CoterieEventManager;
//import com.yryz.service.circle.common.event.CoterieMessageManager;

import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.member.event.*;
@Service(interfaceClass=CoterieAdminAPI.class)
public class CoterieAdminProvider implements CoterieAdminAPI {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CoterieService coterieService;
	
	@Resource
	private CoterieMemberService coterieMemberService;
	

	@Reference
	private UserApi userApi;
	@Reference
	private AccountApi accountApi;
	@Reference
	private CoterieApi coterieApi;
	
 	@Resource
 	private CoterieEventManager coterieEventManager;

	@Resource
	private CoterieMessageManager coterieMessageManager;
	/**
	 * 私圈申请 审批通过（上线）
	 * @param coterieId 私圈ID
	 * @param custId
	 * @param reason
	 */
	@Override
	public void agree(String coterieId,String custId, String reason) {
		logger.info("CoterieAdminAPI.agree params: coterieId:"+coterieId+",reason:"+reason);
		if(StringUtils.isEmpty(coterieId) || StringUtils.isEmpty(custId)){
			throw ServiceException.paramsError();
		}
		audit(coterieId,custId,reason,CoterieConstant.Status.PUTON.getStatus());

		try{
			coterieEventManager.createCoterieEvent(Long.parseLong(coterieId));
			coterieMessageManager.agreeMessage(coterieId);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 私圈申请 审批不通过
	 * @param coterieId
	 * @param custId
	 * @param reason
	 */
	@Override
	public void disagree(String coterieId,String custId, String reason) {
		logger.info("CoterieAdminAPI.disagree params: coterieId:"+coterieId+",reason:"+reason);
		if(StringUtils.isEmpty(coterieId) || StringUtils.isEmpty(custId)){
			throw ServiceException.paramsError();
		}
		audit(coterieId,custId,reason,CoterieConstant.Status.NOTPASS.getStatus());

		try{
			coterieMessageManager.disagreeMessage(coterieId,reason);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 *  私圈 下线
	 * @param coterieId
	 * @param custId
	 * @param reason
	 */
	@Override
	public void offline(String coterieId,String custId, String reason) {
		logger.info("CoterieAdminAPI.offline params: coterieId:"+coterieId+",reason:"+reason);
		if(StringUtils.isEmpty(coterieId) || StringUtils.isEmpty(custId)){
			throw ServiceException.paramsError();
		}
		audit(coterieId,custId,reason,CoterieConstant.Status.PULLOFF.getStatus());

		try{
			coterieMessageManager.offlineMessage(coterieId,reason);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	private void audit(String coterieId,String custId, String reason,Byte status){
		try{
			CoterieInfo info=new CoterieInfo();
			info.setCoterieId(Long.parseLong(coterieId));
			info.setStatus(status);
			coterieService.modify(info);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 私圈分页列表
	 * @param param
	 * @return
	 */
	@Override
	public Response<List<CoterieAdmin>> getCoterieList(CoterieSearchParam param) {
		logger.info("CoterieAdminAPI.getCoterieList params: param:"+param);
		if(param.getPageNum()==null){
			param.setPageNum(1);
		}
		if(param.getPageSize()==null){
			param.setPageSize(10);
		}
		if(param.getSortValue()==null){
			param.setSortValue("desc");
		}
		try{
			List<CoterieAdmin> list=coterieService.find(param);
			Set<String> custIdSet=Sets.newHashSet();
			for (int i = 0; i < list.size(); i++) {
				custIdSet.add(list.get(i).getCustId());
			}
			 Map<String,UserBaseInfoVO>  custMaps=userApi.getUser(custIdSet).getData();
			for (int i = 0; i < list.size(); i++) {
				CoterieAdmin o=list.get(i);
				UserBaseInfoVO cust=custMaps.get(o.getCustId());
				if(cust!=null){
					o.setNickName(cust.getUserNickName());
					o.setPhone(cust.getUserPhone());
					o.setCustName(cust.getUserNickName());
				}
				List<CoterieAuditRecord> auditRecordList=coterieService.findAuditRecordList(o.getCoterieId().toString() , 1, 1);
				if(CollectionUtils.isNotEmpty(auditRecordList)){
					o.setOperDate(auditRecordList.get(0).getCreateDate());
					o.setOperName(auditRecordList.get(0).getCustName());
					o.setReason(auditRecordList.get(0).getRemark());
				}
			}
			return  ResponseUtils.returnListSuccess(list);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 私圈审批记录
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public Response<List<CoterieAuditRecordInfo>> getAuditRecordList(String coterieId,Integer pageNum, Integer pageSize) {
		logger.info("CoterieAdminAPI.disagree params: coterieId:"+coterieId+",pageNum:"+pageNum+",pageSize:"+pageSize);
		if(StringUtils.isEmpty(coterieId) || pageNum==null || pageNum<1 || pageSize==null || pageSize<1){
			throw ServiceException.paramsError();
		}
		try{
			List<CoterieAuditRecord> list=coterieService.findAuditRecordList(coterieId,pageNum, pageSize);
			return    ResponseUtils.returnListSuccess((List<CoterieAuditRecordInfo>)GsonUtils.parseList(list, CoterieAuditRecordInfo.class));
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 成员搜索
	 * @param param
	 * @return
	 */
	@Override
	public Response<List<CoterieMemberInfo>> getMemberList(MemberSearchParam param) {
		logger.info("CoterieAdminAPI.getMemberList params: param:"+param);
		if(param.getPageNum()==null){
			param.setPageNum(1);
		}
		if(param.getPageSize()==null){
			param.setPageSize(10);
		}
		try{
			List<CoterieMemberInfo> list=coterieMemberService.find(param);
			Set<String> custIdSet=Sets.newHashSet();
			for (int i = 0; i < list.size(); i++) {
				custIdSet.add(list.get(i).getCustId());
			}
			Map<String,UserBaseInfoVO> custMaps=userApi.getUser(custIdSet).getData();
			for (int i = 0; i < list.size(); i++) {
				CoterieMemberInfo o=list.get(i);
				UserBaseInfoVO cust=custMaps.get(o.getCustId());
				if(cust!=null){
					o.setCustName(cust.getUserNickName());
					o.setCustIcon(cust.getUserImg());
					o.setPhone(cust.getUserPhone());
					o.setRegisterDate(cust.getCreateDate().toString());
				}
			}
			return ResponseUtils.returnListSuccess(list);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 成员搜索   总数
	 * @param param
	 * @return
	 */
	@Override
	public Response<Integer> getMemberCount(MemberSearchParam param) {
		logger.info("CoterieAdminAPI.getMemberCount params: param:"+param);
		if(param.getPageNum()==null){
			param.setPageNum(1);
		}
		if(param.getPageSize()==null){
			param.setPageSize(10);
		}
		try{
			return ResponseUtils.returnObjectSuccess(coterieMemberService.findCount(param));
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 私圈搜索 数量
	 * @param param
	 * @return
	 */
	@Override
	public Response<Integer> getCoterieCount(CoterieSearchParam param) {
		logger.info("CoterieAdminAPI.getCoterieCount params: param:"+param);
		if(param.getPageNum()==null){
			param.setPageNum(1);
		}
		if(param.getPageSize()==null){
			param.setPageSize(10);
		}
		try{
			return ResponseUtils.returnObjectSuccess(coterieService.findCountBySearchParam(param));
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 推荐私圈设置
	 * @param coterieIdList
	 */
	@Override
	public void recommendCoterie(List<Long> coterieIdList) {
		logger.info("CoterieAdminAPI.recommendCoterie params: coterieIdList:"+coterieIdList);
		if(coterieIdList==null || coterieIdList.isEmpty()){
			return;
		}

		try{
			coterieService.recommendCoterie(coterieIdList);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 取消推荐私圈设置
	 * @param coterieIdList
	 */
	@Override
	public void cancelRecommendCoterie(List<Long> coterieIdList) {
		logger.info("CoterieAdminAPI.cancelRecommendCoterie params: coterieIdList:"+coterieIdList);
		if(coterieIdList==null || coterieIdList.isEmpty()){
			return;
		}

		try{
			coterieService.cancelRecommendCoterie(coterieIdList);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(),e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	
}
