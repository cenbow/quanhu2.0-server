package com.yryz.quanhu.coterie.provider;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import com.alibaba.dubbo.config.annotation.Service;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.common.CoterieConstant;
import com.yryz.quanhu.coterie.exception.DatasOptException;
import com.yryz.quanhu.coterie.exception.ServiceException;

import com.yryz.quanhu.coterie.service.CoterieAPI;
import com.yryz.quanhu.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.vo.FavoriteCircleInfo;
import com.yryz.quanhu.coterie.vo.CoterieAuditInfo;
import com.yryz.quanhu.coterie.vo.CoterieBaseInfo;
import com.yryz.quanhu.coterie.vo.CoterieInfo;

import com.yryz.quanhu.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.entity.CoterieAuditRecord;

import com.yryz.quanhu.coterie.service.CoterieService;


/**
 * 私圈服务实现
 * @author wt
 *
 */
@Service(interfaceClass=CoterieAPI.class)
public class CoterieAPIImpl implements CoterieAPI {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CoterieService coterieService;


	/**
	 * 查询私圈信息列表
	 * @param coterieIdList 私圈ID集合
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<CoterieInfo> queryListByCoterieIdList(List<String> coterieIdList) {
		logger.info("queryListByCoterieIdList params:" + coterieIdList);
		if (CollectionUtils.isEmpty(coterieIdList)) {
			return Lists.newArrayList();
		}
		try {
			List<CoterieInfo> list=coterieService.findList(coterieIdList);
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 查询已上架的私圈列表
	 * @param circleId
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public List<CoterieInfo> queryPage(String circleId, Integer pageNum, Integer pageSize) {
		logger.info("CoterieAPI.queryPage params: " + pageNum + "," + pageSize);
		if (StringUtils.isEmpty(circleId) || pageNum == null || pageNum < 1 || pageSize == null || pageSize < 1) {
			return Lists.newArrayList();
		}
		try {
			List<CoterieInfo> list=coterieService.findPage(circleId, pageNum, pageSize, CoterieConstant.Status.PUTON.getStatus());
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	/**
	 * 查询已上架的私圈列表

	 * @return
	 * @throws ServiceException
	 */
	@Override
	public CoterieInfo queryCoterieInfo(String coterieId) {

			return null;

	}
	/**
	 * 查询私圈信息

	 * @return
	 * @throws ServiceException
	 */
	@Override
	public void modifyCoterieInfo(CoterieInfo info) {
		logger.info("CoterieAPI.modifyCoterieInfo params:" + info);
		if (info == null || StringUtils.isEmpty(info.getCoterieId())) {
			throw ServiceException.paramsError();
		}
		String name = StringUtils.trim(info.getName());
		if (StringUtils.isNotEmpty(name)) {
			List<CoterieInfo> clist = coterieService.findByName(name);
			if (!clist.isEmpty()) {
				throw new ServiceException(ServiceException.CODE_SYS_ERROR, "私圈名称已存在");
			}
		}
		//费用单位错误防范
		if (info.getJoinFee()!=null && info.getJoinFee()<100 && info.getJoinFee()>0) {
			throw new ServiceException(ServiceException.CODE_SYS_ERROR, "加入私圈金额设置不正确。");
		}
		if (info.getConsultingFee()!=null && info.getConsultingFee()<100 && info.getConsultingFee()>0) {
			throw new ServiceException(ServiceException.CODE_SYS_ERROR, "私圈咨询费金额设置不正确。");
		}
		try {
			coterieService.modify(info);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public CoterieInfo applyCreate(CoterieBaseInfo info) {
		logger.info("CoterieAPI.applyCreate params:" + info);
		checkApplyCreateParam(info);
		try {
			return coterieService.save(info);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	private void checkApplyCreateParam(CoterieBaseInfo info) {

	}

	@Override
	public List<CoterieInfo> getMyCreateCoterie(String custId, String circleId) throws ServiceException {
		logger.info("CoterieAPI.getMyCreateCoterie params: " + custId + ",circleId:" + circleId);
		if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(circleId)) {
			throw ServiceException.paramsError();
		}
		try {
			List<CoterieInfo> list = coterieService.findMyCreateCoterie(custId, circleId);
			fillCustInfo(list);
			fillCircleInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getMyJoinCoterie(String custId, String circleId) throws ServiceException {
		logger.info("CoterieAPI.getMyJoinCoterie params: " + custId + ",circleId:" + circleId);
		if (StringUtils.isEmpty(custId) || StringUtils.isEmpty(circleId)) {
			throw ServiceException.paramsError();
		}
		try {
			List<CoterieInfo> list = coterieService.findMyJoinCoterie(custId, circleId);
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public Integer getCoterieCount(String circleId,Byte status) throws ServiceException {
		logger.info("CoterieAPI.getCoterieCount circleId: " + circleId);
		try {
			return  coterieService.getCoterieCount(circleId, status);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> queryPage(Integer pageNum, Integer pageSize) throws ServiceException {
		logger.info("CoterieAPI.queryPage pageNum: " + pageNum + ",pageSize:" + pageSize);
		try {
			List<CoterieInfo> list=coterieService.findPage(pageNum, pageSize);
			fillCustInfo(list);
			fillCircleInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getMyCreateCoterie(String custId, Integer pageNum, Integer pageSize,Integer status)
			throws ServiceException {
		logger.info("CoterieAPI.getMyCreateCoterie pageNum: " + pageNum + ",pageSize:" + pageSize+",custId:"+custId);
		try {
			List<CoterieInfo> list=coterieService.findMyCreateCoterie(custId, pageNum, pageSize,status);
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public Integer getMyCreateCoterieCount(String custId,Integer status) throws ServiceException {
		logger.info("CoterieAPI.getMyCreateCoterieCount custId: " + custId);
		try {
			return  coterieService.findMyCreateCoterieCount(custId,status);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getMyJoinCoterie(String custId, Integer pageNum, Integer pageSize)
			throws ServiceException {
		logger.info("CoterieAPI.getMyJoinCoterie pageNum: " + pageNum + ",pageSize:" + pageSize+",custId:"+custId);
		try {
			List<CoterieInfo> list=coterieService.findMyJoinCoterie(custId, pageNum, pageSize);
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public Integer getMyJoinCoterieCount(String custId) throws ServiceException {
		logger.info("CoterieAPI.getMyJoinCoterieCount custId: " + custId);
		try {
			return  coterieService.findMyJoinCoterieCount(custId);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getRecommendCoterie(String custId) {

		List<CoterieInfo> resultList=Lists.newArrayList();

		return resultList;
	}
	
	@Override
	public List<CoterieInfo> getRecommendCoterieForCircle(String circleId) {
		logger.info("CoterieAPI.getRecommendCoterieForCircle circleId: " + circleId);
		//获取运营推荐的私圈
		List<CoterieInfo> recommendList=coterieService.getRecommendList(circleId, 0, 1);
		//获取达人的私圈
		List<CoterieInfo> expertList=coterieService.getHeatList(circleId, CoterieConstant.Expert.YES.getStatus(), 0, 20);
		//获取非达人的私圈
		List<CoterieInfo> list=coterieService.getHeatList(circleId, CoterieConstant.Expert.NO.getStatus(), 0, 20);
		
		List<CoterieInfo> resultList=Lists.newArrayList();
		resultList.addAll(recommendList);
		
		for (int i = 0; i < expertList.size(); i++) {
			CoterieInfo info=expertList.get(i);
			if(resultList.size()<5 && !resultList.contains(info)){
				resultList.add(info);
			}
		}
		
		for (int i = 0; i < list.size(); i++) {
			CoterieInfo info=list.get(i);
			if(resultList.size()<10 && !resultList.contains(info)){
				resultList.add(info);
			}
		}
		
		//如果不够10个则取上线的任意圈子补
		List<CoterieInfo> allList=coterieService.findPage(circleId, 1, 20, CoterieConstant.Status.PUTON.getStatus());
		for (int i = 0; i < allList.size(); i++) {
			CoterieInfo info=allList.get(i);
			if(resultList.size()<10 && !resultList.contains(info)){
				resultList.add(info);
			}
		}
		
		fillCustInfo(resultList);
		fillCircleInfo(resultList);
		return resultList;
	}

	@Override
	public List<CoterieInfo> queryPageForApp(Integer pageNum, Integer pageSize) throws ServiceException {
		logger.info("CoterieAPI.queryPageForApp params: " + pageNum + "," + pageSize);
		if (pageNum == null) {
			pageNum=1;
		}
		if(pageSize == null){
			pageSize=10;
		}
		
		try {
			List<CoterieInfo> list=coterieService.queryPageForApp(pageNum, pageSize);
			fillCircleInfo(list);
			fillCustInfo(list);
			return list;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getCoterieLikeName(String circleId,String name, Integer start, Integer pageSize) {
		logger.info("CoterieAPI.getCoterieLikeName circleId:" + circleId + ",name: " + name + ",start:" + start
				+ ",pageSize" + pageSize);
		if (name == null) {
			ServiceException.paramsError("name");
		}
		if(start==null){
			start=0;
		}
		if(pageSize==null){
			pageSize=10;
		}
		
		try {
			List<CoterieInfo> infoList = coterieService.getCoterieLikeName(circleId,name, start, pageSize);
			fillCircleInfo(infoList);
			fillCustInfo(infoList);
			return infoList;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public void modifyCoterieExpert(String custId, Byte isExpert) throws ServiceException {
		logger.info("CoterieAPI.modifyCoterieExpert custId: " + custId + ",isExpert" + isExpert);
		if(StringUtils.isEmpty(custId) || isExpert==null){
			throw ServiceException.paramsError();
		}
		try {
			coterieService.modifyCoterieExpert(custId, isExpert);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	
	@Override
	public List<String> getCircleIdList(String ownerId) {
		logger.info("CoterieAPI.getCircleIdList ownerId:" + ownerId);
		if (StringUtils.isEmpty(ownerId)) {
			ServiceException.paramsError("ownerId");
		}
		
		try {
			List<String> circleIdList = coterieService.getCircleIdListByOwnerId(ownerId);
			return circleIdList;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}
	
	private void fillCircleInfo(List<CoterieInfo> infoList){



	}
	
	private void fillCustInfo(List<CoterieInfo> infoList){

	}

	@Override
	public Integer getMyCoterieCount(String custId) {
		logger.info("CoterieAPI.getMyCreateCoterieCount custId: " + custId);
		try {
			Integer count=coterieService.findMyJoinCoterieCount(custId);
			count+=coterieService.findMyCreateCoterieCount(custId,null);
			return  count;
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public void saveAuditRecord(CoterieAuditInfo info) {
		logger.info("CoterieAPI.saveAuditRecord params:" + info);
		if (info == null || StringUtils.isEmpty(info.getCoterieId()) || StringUtils.isEmpty(info.getCustId())
				|| StringUtils.isEmpty(info.getCustName()) || info.getStatus() == null) {
			throw ServiceException.paramsError();
		}
		
		try {
			CoterieAuditRecord record=GsonUtils.parseObj(info, CoterieAuditRecord.class);
			record.setCreateDate(new Date());
			record.setLastUpdateTime(new Date());
			coterieService.saveAuditRecord(record);
		} catch (DatasOptException e) {
			logger.error(e.getMessage(), e);
			throw ServiceException.sysError();
		} catch (ServiceException e) {
			throw ServiceException.parse(e);
		} catch (Exception e) {
			logger.error("unKown Exception", e);
			throw ServiceException.sysError();
		}
	}

	@Override
	public List<CoterieInfo> getMyCreateCoterie(String custId, Integer pageNum, Integer pageSize)
			throws ServiceException {
		return getMyCreateCoterie(custId,pageNum,pageSize,null);
	}

	@Override
	public Integer getMyCreateCoterieCount(String custId) throws ServiceException {
		return getMyCreateCoterieCount(custId,null);
	}

}
