package com.yryz.quanhu.coterie.coterie.service.impl;

import com.google.common.collect.Lists;
import com.yryz.common.exception.QuanhuException;
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
		 coterie.setCoterieId(Long.parseLong(IdUtils.randomappId()));
		String qrUrl= QrUtils.createQr("",coterie.getCoterieId()+"");
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

			throw new QuanhuException( "2007","参数错误","param coterie",null);

		}
	}

	@Override
	public void modify(CoterieInfo info) {
		Coterie coterie=(Coterie) GsonUtils.parseObj(info, Coterie.class);
		try{
			coterieMapper.updateByCoterieIdSelective(coterie);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param coterie",null);
		}
	}

	@Override
	public void remove(Long coterieId) {
		try{
			Coterie coterie=new Coterie();
			coterie.setCoterieId( coterieId );
			coterie.setDeleted((byte)1);
			coterieMapper.updateByCoterieIdSelective(coterie);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param coterie",null);
		}
	}

	@Override
	public CoterieInfo find(Long coterieId) {
		Coterie info=null;
		try{
			info=coterieMapper.selectByCoterieId(coterieId);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param coterie",null);
		}
		return (CoterieInfo) GsonUtils.parseObj(info, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(Integer status) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByStatus(status);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param coterie",null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(Coterie coterie) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByCoterie(coterie);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param coterie",null);
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
			throw new QuanhuException( "2007","参数错误","msg",null);
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
			throw new QuanhuException( "2007","参数错误","param coterie",null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(List<Long> coterieIdList) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectListByCoterieIdList(coterieIdList);
		}catch (Exception e) {

			throw new QuanhuException( "2007","参数错误","param coterieIdList",null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyCreateCoterie(String custId ) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectMyCreateCoterie(custId );
		}catch (Exception e) {

			throw new QuanhuException( "2007","参数错误","param custId",null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId ) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectMyJoinCoterie(custId );
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param custId",null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findByName(String name) {
		List<Coterie> list=Lists.newArrayList();
		try{
			list=coterieMapper.selectByName(name);
		}catch (Exception e) {

			throw new QuanhuException( "2007","参数错误","param name:"+name,null);
		}
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public CoterieInfo find(String custId, String circleId) {
		try{
			Coterie info=coterieMapper.selectByCustIdAndCircleId(custId, circleId);
			return (CoterieInfo) GsonUtils.parseObj(info, CoterieInfo.class);
		}catch (Exception e) {

			throw new QuanhuException( "2007","参数错误","find param custId:"+custId+",circleId:",null);
		}
	}

	@Override
	public void saveAuditRecord(CoterieAuditRecord record) {
		try{
			//coterieAuditRecordMapper.insert(record);
		}catch (Exception e) {

			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieAuditRecord> findAuditRecordList(String coterieId, Integer pageNum, Integer pageSize) {
		try{
			int start = (pageNum-1)*pageSize;
			return null;//coterieAuditRecordMapper.selectPage(coterieId,start, pageSize);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);

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
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public Integer getCoterieCount(String circleId,Byte status) {
		try{
			return coterieMapper.selectCountByCircleId(circleId, status);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
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
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieInfo> findMyCreateCoterie(String custId, Integer pageNum, Integer pageSize, Integer status) {
		try{
			int start=(pageNum-1)*pageSize;
			List<Coterie> list=coterieMapper.selectMyCreateCoteriePage(custId, start, pageSize,status);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public Integer findMyCreateCoterieCount(String custId,Integer status) {
		try{
			return coterieMapper.selectMyCreateCoterieCount(custId,status);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId, Integer pageNum, Integer pageSize) {
		try{
			int start=(pageNum-1)*pageSize;
			List<Coterie> list=coterieMapper.selectMyJoinCoteriePage(custId, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public Integer findMyJoinCoterieCount(String custId) {
		try{
			return coterieMapper.selectMyJoinCoterieCount(custId);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public void recommendCoterie(List<Long> coterieIdList) {
		try{
			coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.YES.getStatus());
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public void cancelRecommendCoterie(List<Long> coterieIdList) {
		try{
			coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.NO.getStatus());
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieInfo> getRecommendList(String circleId, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectRecommendList(circleId, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieInfo> getHeatList(String circleId, Byte expert, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectHeatList(circleId, expert, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<Coterie> getHeatList(String circleId, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectHeatListByCircleId(circleId,start, pageSize);
			return list;
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
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
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<CoterieInfo> getCoterieLikeName(String circleId, String name, Integer start, Integer pageSize) {
		try{
			List<Coterie> list=coterieMapper.selectLikeName(circleId,name, start, pageSize);
			return GsonUtils.parseList(list, CoterieInfo.class);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public void modifyCoterieExpert(String custId, Byte isExpert) {
		try{
			coterieMapper.updateExpert(custId, isExpert);
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<String> getCircleIdListByOwnerId(String ownerId) {
		try{
			List<String> list=coterieMapper.selectCircleIdListByOwnerId(ownerId);
			return list;
		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}
	@Override
	public int updateMemberNum( Long coterieId,  Integer newMemberNum, Integer oldMemberNum)
	{
		try{
			return  coterieMapper.updateMemberNum( coterieId,    newMemberNum,  oldMemberNum);

		}catch (Exception e) {
			throw new QuanhuException( "2007","参数错误","param record:"+"record,circleId:",null);
		}
	}

	@Override
	public List<Long> getKidByCreateDate(String startDate, String endDate) {
		try{
			return  coterieMapper.selectKidByCreateDate(startDate, endDate);
		}catch (Exception e) {
			throw new MysqlOptException("getKidByCreateDate startDate:"+startDate+",endDate:"+endDate,e);
		}
	}

	@Override
	public List<Coterie> getByKids(List<Long> kidList) {
		try{
			return  coterieMapper.selectByKids(kidList);
		}catch (Exception e) {
			throw new MysqlOptException("getByKids kidList:"+kidList,e);
		}
	}
}
