package com.yryz.quanhu.coterie.coterie.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.coterie.common.CoterieConstant;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.dao.CoterieRedis;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.entity.CoterieSearch;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.*;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 私圈服务实现
 * 
 * @author chengyunfei
 *
 */
@Service
public class CoterieServiceImpl implements CoterieService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Resource
	private CoterieMapper coterieMapper;
	@Reference(check = false)
	private IdAPI idapi;
	@Reference(check = false)
	private ResourceDymaicApi resourceDymaicApi;
	@Reference(check = false)
	private EventAPI eventAPI;
	@Reference(check = false)
	private UserApi userApi;
	@Autowired
	private CoterieRedis coterieRedis;

	@Override
	public CoterieInfo save(CoterieBasicInfo info) {
		Coterie coterie = (Coterie) GsonUtils.parseObj(info, Coterie.class);
		coterie.setCoterieId(idapi.getSnowflakeId().getData());
		coterie.setConsultingFee(0);
		coterie.setCreateDate(new Date());
		coterie.setDeleted((byte) 10);
		coterie.setHeat(0L);
		coterie.setJoinCheck(info.getJoinCheck() == null ? 11 : info.getJoinCheck());
		coterie.setJoinFee(info.getJoinFee() == null ? 0 : info.getJoinFee());
		coterie.setLastUpdateDate(new Date());
		coterie.setMemberNum(0);
		coterie.setShelveFlag(11);
		coterie.setRevision(1);
		coterie.setStatus(CoterieConstant.Status.WAIT.getStatus());
		coterie.setRecommend((byte) 10);
		UserSimpleVO user = ResponseUtils.getResponseData(userApi.getUserSimple(Long.parseLong(info.getOwnerId())));
		coterie.setIsExpert(user.getUserRole());
		coterie.setRedDot(10);
		coterieMapper.insertSelective(coterie);
		updateCache(coterie.getCoterieId());
		return (CoterieInfo) GsonUtils.parseObj(coterie, CoterieInfo.class);
	}

	@Override
	public void modify(CoterieInfo info) {
		Coterie coterie = (Coterie) GsonUtils.parseObj(info, Coterie.class);
		coterieMapper.updateByCoterieIdSelective(coterie);
		updateCache(coterie.getCoterieId());
	}

	@Override
	public void remove(Long coterieId) {
		Coterie coterie = new Coterie();
		coterie.setCoterieId(coterieId);
		coterie.setDeleted((byte) 1);
		coterieMapper.updateByCoterieIdSelective(coterie);
		updateCache(coterie.getCoterieId());
	}

	@Override
	public CoterieInfo find(Long coterieId) {

		logger.info("coterieId : " + coterieId);

		Coterie info = coterieRedis.get(coterieId);

		if(info==null){
			info = coterieMapper.selectByCoterieId(coterieId);
		}

		logger.info("coterie : " + JsonUtils.toFastJson(info));

		return (CoterieInfo) GsonUtils.parseObj(info, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(Coterie coterie) {
		List<Coterie> list = Lists.newArrayList();
		list = coterieMapper.selectListByCoterie(coterie);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findPage(Integer pageNum, Integer pageSize) {
		List<Coterie> list = Lists.newArrayList();
		Integer start = (pageNum - 1) * pageSize;
		list = coterieMapper.findPage(start, pageSize);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findList(List<Long> coterieIdList) {
		List<Coterie> list = Lists.newArrayList();
		list = coterieMapper.selectListByCoterieIdList(coterieIdList);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyCreateCoterie(String custId) {
		List<Coterie> list = Lists.newArrayList();
		list = coterieMapper.selectMyCreateCoterie(custId);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId) {
		List<Coterie> list = Lists.newArrayList();
		list = coterieMapper.selectMyJoinCoterie(custId);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findByName(String name) {
		List<Coterie> list = Lists.newArrayList();
		list = coterieMapper.selectByName(name);
		return (List<CoterieInfo>) GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public Integer findMyCreateCoterieCount(String custId) {
		return coterieMapper.selectMyCreateCoterieCount(custId);
	}

	@Override
	public List<CoterieInfo> findMyJoinCoterie(String custId, Integer pageNum, Integer pageSize) {
		int start = (pageNum - 1) * pageSize;
		List<Coterie> list = coterieMapper.selectMyJoinCoteriePage(custId, start, pageSize);
		return GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public Integer findMyJoinCoterieCount(String custId) {
		return coterieMapper.selectMyJoinCoterieCount(custId);
	}

	@Override
	public void recommendCoterie(List<Long> coterieIdList) {
		coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.YES.getStatus());
		for (int i = 0; i < coterieIdList.size(); i++) {
			updateCache(coterieIdList.get(i));
		}
	}

	@Override
	public void cancelRecommendCoterie(List<Long> coterieIdList) {
		coterieMapper.updateRecommend(coterieIdList, CoterieConstant.Recommend.NO.getStatus());
		for (int i = 0; i < coterieIdList.size(); i++) {
			updateCache(coterieIdList.get(i));
		}
	}

	@Override
	public void modifyCoterieExpert(String custId, Byte isExpert) {
		coterieMapper.updateExpert(custId, isExpert);
	}

	@Override
	public int updateMemberNum(Long coterieId, Integer newMemberNum, Integer oldMemberNum) {
		int c=coterieMapper.updateMemberNum(coterieId, newMemberNum, oldMemberNum);
		updateCache(coterieId);
		return c;
	}

	@Override
	public List<Long> getKidByCreateDate(String startDate, String endDate) {
		return coterieMapper.selectKidByCreateDate(startDate, endDate);
	}

	@Override
	public List<Coterie> getByKids(List<Long> kidList) {
		Map<Long,Coterie> coteries = coterieRedis.multiGet(kidList);
		List<Coterie> result=Lists.newArrayList();
		result.addAll(coteries.values());
        if(coteries.size()==kidList.size()){
        	return result;
        }
        
        List<Long> nullIdList = new ArrayList<>();
        for (int i = 0; i < kidList.size(); i++) {
        	if(coteries.get(kidList.get(i))==null){
        		nullIdList.add(kidList.get(i));
        	}
		}

        if (!nullIdList.isEmpty()) {
            List<Coterie> cList = coterieMapper.selectByKids(nullIdList);
            // 合并进返回list
            for (int i=0;i<cList.size();i++) {
            	Coterie model=cList.get(i);
            	result.add(model);
                coterieRedis.save(model);
            }
        }

        return result;
	}

	@Override
	public PageList<CoterieInfo> queryCoterieByPage(CoterieSearchParam param) {
		List<Coterie> list = Lists.newArrayList();
		int start = (param.getPageNum() - 1) * param.getPageSize();
		list = coterieMapper.findPageByStatus(start, param.getPageSize(), param.getStatus());
		PageList<CoterieInfo> pageList = new PageList<>(param.getPageNum(), param.getPageSize(),
				GsonUtils.parseList(list, CoterieInfo.class));
		return pageList;
	}

	@Override
	public List<CoterieInfo> getRecommendCoterieList() {
		List<Coterie> list = coterieMapper.selectRecommendList();
		return GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> getOrderByMemberNum() {
		List<Coterie> list = coterieMapper.selectOrderByMemberNum();
		return GsonUtils.parseList(list, CoterieInfo.class);
	}

	@Override
	public List<CoterieInfo> findCreateCoterie(String custId, Integer pageNum, Integer pageSize) {
		int start=(pageNum-1)*pageSize;
		List<Coterie> list = coterieMapper.selectCreateCoterie(custId, start, pageSize);
		return GsonUtils.parseList(list, CoterieInfo.class);
	}
	
	/**
	 * 更新redis缓存
	 */
	private void updateCache(Long coterieId){
		Coterie model = coterieMapper.selectByCoterieId(coterieId);
		if(model != null){
			coterieRedis.save(model);
		}
	}
}
