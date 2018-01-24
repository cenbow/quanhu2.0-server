package com.yryz.quanhu.coterie.coterie.service.impl;

import com.google.common.collect.Lists;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.common.CoterieConstant;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.entity.CoterieAuditRecord;
import com.yryz.quanhu.coterie.coterie.entity.CoterieSearch;
import com.yryz.quanhu.coterie.coterie.exception.MysqlOptException;
import com.yryz.quanhu.coterie.coterie.until.IdUtils;
import com.yryz.quanhu.coterie.coterie.until.QrUtils;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

//import com.yryz.service.circle.modules.coterie.dao.persistence.CoterieAuditRecordMapper;

/**
 * 私圈服务实现
 * @author jk
 *
 */
@Service
public class CoterieServiceImpl implements CoterieService {
	@Resource
	private CoterieMapper coterieMapper;


	//private CoterieAuditRecordMapper coterieAuditRecordMapper;

	@Override
	public CoterieInfo save(CoterieBasicInfo info) {
		Coterie coterie=(Coterie) GsonUtils.parseObj(info, Coterie.class);
		//todo
		 coterie.setCoterieId(IdUtils.randomappId());
		String qrUrl= QrUtils.createQr("",coterie.getCoterieId());
		coterie.setQrUrl(qrUrl);
		coterie.setConsultingFee(0);
		coterie.setCreateDate(new Date());
		coterie.setDeleted((byte)0);
		coterie.setJoinCheck(info.getJoinCheck()==null? 1 : info.getJoinCheck() );
		coterie.setJoinFee(info.getJoinFee()==null?0:info.getJoinFee());
		coterie.setLastUpdateDate(new Date());
		coterie.setMemberNum(0);
		coterie.setStatus(CoterieConstant.Status.WAIT.getStatus());
		try{
			coterieMapper.insertSelective(coterie);
			return (CoterieInfo) GsonUtils.parseObj(coterie, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param coterie:"+coterie,e);
		}
	}

	@Override
	public void modify(CoterieInfo info) {
		Coterie coterie=(Coterie) GsonUtils.parseObj(info, Coterie.class);
		try{
			coterieMapper.updateByCoterieIdSelective(coterie);
		}catch (Exception e) {
			throw new MysqlOptException("param coterie:"+coterie,e);
		}
	}

	@Override
	public void remove(String coterieId) {
		try{
			Coterie coterie=new Coterie();
			coterie.setCoterieId(coterieId);
			coterie.setDeleted((byte)1);
			coterieMapper.updateByCoterieIdSelective(coterie);
		}catch (Exception e) {
			throw new MysqlOptException("param coterieId:"+coterieId,e);
		}
	}

	@Override
	public CoterieInfo find(String coterieId) {
		Coterie info=null;
		try{
			info=coterieMapper.selectByCoterieId(coterieId);
		}catch (Exception e) {
			throw new MysqlOptException("param coterieId:"+coterieId,e);
		}
		return (CoterieInfo) GsonUtils.parseObj(info, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(Integer status) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByStatus(status);
		}catch (Exception e) {
			throw new MysqlOptException("param status:"+status,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(Coterie coterie) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByCoterie(coterie);
		}catch (Exception e) {
			throw new MysqlOptException("param coterie:"+coterie,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findPage(Integer pageNum, Integer pageSize, Byte status) {
		List<Coterie> list=Lists.newArrayList();
		try{
			Integer start=(pageNum-1)*pageSize;
			list=coterieMapper.findPageByStatus( start, pageSize, status);
		}catch (Exception e) {
			String msg="param pageNum:"+pageNum+"pageSize:"+pageSize+"status:"+status;
			throw new MysqlOptException(msg,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findPage(Integer pageNum, Integer pageSize) {
		List<Coterie> list=Lists.newArrayList();
		try{
			Integer start=(pageNum-1)*pageSize;
			list=coterieMapper.findPage(start, pageSize);
		}catch (Exception e) {
			throw new MysqlOptException("param pageNum:"+pageNum+"pageSize:"+pageSize,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(List<String> coterieIdList) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByCoterieIdList(coterieIdList);
		}catch (Exception e) {
			throw new MysqlOptException("param coterieIdList:"+coterieIdList,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyCreateCoterie(String custId ) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectMyCreateCoterie(custId );
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId ) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectMyJoinCoterie(custId );
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findByName(String name) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectByName(name);
		}catch (Exception e) {
			throw new MysqlOptException("param name:"+name,e);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public CoterieInfo find(String custId, String circleId) {
		try{
			Coterie info=coterieMapper.selectByCustIdAndCircleId(custId, circleId);
			return (CoterieInfo) GsonUtils.parseObj(info, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("find param custId:"+custId+",circleId:"+circleId,e);
		}
	}

	@Override
	public void saveAuditRecord(CoterieAuditRecord record) {
		try{
			//coterieAuditRecordMapper.insert(record);
		}catch (Exception e) {
			throw new MysqlOptException("param record:"+record,e);
		}
	}

	@Override
	public List<CoterieAuditRecord> findAuditRecordList(String coterieId, Integer pageNum, Integer pageSize) {
		try{
			int start = (pageNum-1)*pageSize;
			return null;//coterieAuditRecordMapper.selectPage(coterieId,start, pageSize);
		}catch (Exception e) {
			throw new MysqlOptException("param pageNum:"+pageNum+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public List<CoterieAdmin> find(CoterieSearchParam param) {
		try{
			CoterieSearch searchParam= GsonUtils.parseObj(param, CoterieSearch.class);
			int start=(param.getPageNum()-1)*param.getPageSize();
			searchParam.setStart(start);
			List<Coterie> list=coterieMapper.selectBySearchParam(searchParam);
			
			List<CoterieAdmin> rstList=Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				Coterie c=list.get(i);
				CoterieAdmin info=new CoterieAdmin();
				info.setConsultingFee(c.getConsultingFee());
				info.setCoterieId(c.getCoterieId());
				info.setCreateDate(c.getCreateDate());
				info.setIntro(c.getIntro());
				info.setJoinFee(c.getJoinFee());
				info.setName(c.getName());
				info.setCustId(c.getOwnerId());
				info.setJoinCheck(c.getJoinCheck());
				info.setStatus(c.getStatus());
				info.setOwnerIntro(c.getOwnerIntro());
				info.setRecommend(c.getRecommend());
				info.setIsExpert(c.getIsExpert());
				rstList.add(info);
			}
			
			return rstList;
		}catch (Exception e) {
			throw new MysqlOptException("param param:"+param,e);
		}
	}

	@Override
	public Integer getCoterieCount(String circleId,Byte status) {
		try{
			return coterieMapper.selectCountByCircleId(circleId, status);
		}catch (Exception e) {
			throw new MysqlOptException("param circleId:"+circleId,e);
		}
	}

	@Override
	public Integer findCountBySearchParam(CoterieSearchParam param) {
		try{
			CoterieSearch searchParam= GsonUtils.parseObj(param, CoterieSearch.class);
			int start=(param.getPageNum()-1)*param.getPageSize();
			searchParam.setStart(start);
			return coterieMapper.selectCountBySearchParam(searchParam);
		}catch (Exception e) {
			throw new MysqlOptException("param param:"+param,e);
		}
	}

	@Override
	public List<CoterieInfo> findMyCreateCoterie(String custId, Integer pageNum, Integer pageSize, Integer status) {
		try{
			int start=(pageNum-1)*pageSize;
			List<Coterie> list=coterieMapper.selectMyCreateCoteriePage(custId, start, pageSize,status);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId+",pageNum:"+pageNum+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public Integer findMyCreateCoterieCount(String custId,Integer status) {
		try{
			return coterieMapper.selectMyCreateCoterieCount(custId,status);
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId,e);
		}
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId, Integer pageNum, Integer pageSize) {
		try{
			int start=(pageNum-1)*pageSize;
			List<Coterie> list=coterieMapper.selectMyJoinCoteriePage(custId, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId+",pageNum:"+pageNum+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public Integer findMyJoinCoterieCount(String custId) {
		try{
			return coterieMapper.selectMyJoinCoterieCount(custId);
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId,e);
		}
	}

	@Override
	public void recommendCoterie(List<String> coterieIdList) {
		try{
			coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.YES.getStatus());
		}catch (Exception e) {
			throw new MysqlOptException("param coterieIdList:"+coterieIdList,e);
		}
	}

	@Override
	public void cancelRecommendCoterie(List<String> coterieIdList) {
		try{
			coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.NO.getStatus());
		}catch (Exception e) {
			throw new MysqlOptException("param coterieIdList:"+coterieIdList,e);
		}
	}

	@Override
	public List<CoterieInfo> getRecommendList(String circleId, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectRecommendList(circleId, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param circleId:"+circleId+",start:"+start+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public List<CoterieInfo> getHeatList(String circleId, Byte expert, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectHeatList(circleId, expert, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param circleId:"+circleId+",expert:"+expert+",start:"+start+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public List<Coterie> getHeatList(String circleId, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectHeatListByCircleId(circleId,start, pageSize);
			return list;
		}catch (Exception e) {
			throw new MysqlOptException("param circleId:"+circleId+",start:"+start+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public List<CoterieInfo> queryPageForApp(Integer pageNum, Integer pageSize) {
		try{
			int start=(pageNum-1)*pageSize;
			List<Coterie> list=coterieMapper.findPage(start, pageSize);
			Collections.sort(list, new Comparator<Coterie>() {
				@Override
	            public int compare(Coterie o1, Coterie o2) {
	            	if(o1.getHeat()==null){
	            		o1.setHeat(0L);
	            	}
	            	if(o2.getHeat()==null){
	            		o2.setHeat(0L);
	            	}
	                return o2.getHeat().compareTo(o1.getHeat());
	            }
	        });
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param start:"+pageNum+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public List<CoterieInfo> getCoterieLikeName(String circleId, String name, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectLikeName(circleId,name, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new MysqlOptException("param name:"+name+",start:"+start+",pageSize:"+pageSize,e);
		}
	}

	@Override
	public void modifyCoterieExpert(String custId, Byte isExpert) {
		try{
			coterieMapper.updateExpert(custId, isExpert);
		}catch (Exception e) {
			throw new MysqlOptException("param custId:"+custId+",isExpert:"+isExpert,e);
		}
	}

	@Override
	public List<String> getCircleIdListByOwnerId(String ownerId) {
		try{
			List<String> list=coterieMapper.selectCircleIdListByOwnerId(ownerId);
			return list;
		}catch (Exception e) {
			throw new MysqlOptException("param ownerId:"+ownerId,e);
		}
	}

}
