package com.yryz.quanhu.dymaic.service;


import java.util.List;
import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
//import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ReleaseInfoVo;
import com.yryz.quanhu.dymaic.vo.TopicInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	@Resource
	private UserRepository userRepository;
//	@Resource
//	private ResourceInfoRepository resourceInfoRepository;
	@Resource
	private CoterieInfoRepository coterieInfoRepository;
	
	@Override
	public List<UserSimpleVo> searchUser(String keyWord,Integer page,Integer size) {
		List<UserInfo> list=userRepository.search(keyWord,page,size);
		List<UserSimpleVo> rstList=Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			UserInfo info=list.get(i);
			UserSimpleVo vo=new UserSimpleVo();
			BeanUtils.copyProperties(info, vo);
			rstList.add(vo);
		}
		return rstList;
	}

	@Override
	public PageList<ReleaseInfoVo> searchReleaseInfo(String keyWord, Integer page, Integer size) {
//		List<ResourceInfo> list=resourceInfoRepository.searchTopic(keyWord,page,size);
//		System.out.println(list);
//		List<ReleaseInfoVo> rstList=Lists.newArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			ReleaseInfo info=list.get(i);
//			ReleaseInfoVo vo=new ReleaseInfoVo();
//			BeanUtils.copyProperties(info, vo);
//			rstList.add(vo);
//		}
//		//TODO 需要查询用户的信息
//		PageList<ReleaseInfoVo> pageList=new PageList<ReleaseInfoVo>();
//		pageList.setEntities(rstList);
//		pageList.setCount(null);
//		pageList.setCurrentPage(page);
//		pageList.setPageSize(size);
//		return pageList;
		return null;
	}

	@Override
	public PageList<TopicInfoVo> searchTopicInfo(String keyWord, Integer page, Integer size) {
//		List<TopicInfo> list=topicInfoRepository.search(keyWord,page,size);
//		List<TopicInfoVo> rstList=Lists.newArrayList();
//		for (int i = 0; i < list.size(); i++) {
//			TopicInfo info=list.get(i);
//			TopicInfoVo vo=new TopicInfoVo();
//			BeanUtils.copyProperties(info, vo);
//			rstList.add(vo);
//		}
//		//TODO 需要查询用户的信息
//		PageList<TopicInfoVo> pageList=new PageList<TopicInfoVo>();
//		pageList.setEntities(rstList);
//		pageList.setCount(null);
//		pageList.setCurrentPage(page);
//		pageList.setPageSize(size);
//		return pageList;
		return null;
	}

	@Override
	public PageList<CoterieInfoVo> searchCoterieInfo(String keyWord, Integer page, Integer size) {
		List<CoterieInfo> list=coterieInfoRepository.search(keyWord,page,size);
		List<CoterieInfoVo> rstList=Lists.newArrayList();
		for (int i = 0; i < list.size(); i++) {
			CoterieInfo info=list.get(i);
			CoterieInfoVo vo=new CoterieInfoVo();
			BeanUtils.copyProperties(info, vo);
			rstList.add(vo);
		}
		//TODO 需要查询用户的信息
		PageList<CoterieInfoVo> pageList=new PageList<CoterieInfoVo>();
		pageList.setEntities(rstList);
		pageList.setCount(null);
		pageList.setCurrentPage(page);
		pageList.setPageSize(size);
		return pageList;
	}

}
