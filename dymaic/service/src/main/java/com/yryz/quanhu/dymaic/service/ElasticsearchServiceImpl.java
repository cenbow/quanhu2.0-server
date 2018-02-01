package com.yryz.quanhu.dymaic.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.dto.StarInfoDTO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Lists;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicPostInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;

@Service
public class ElasticsearchServiceImpl implements ElasticsearchService {
	private Logger logger = LoggerFactory.getLogger(ElasticsearchServiceImpl.class);
	@Resource
	private ElasticsearchTemplate elasticsearchTemplate;
	@Resource
	private UserRepository userRepository;
	@Resource
	private ResourceInfoRepository resourceInfoRepository;
	@Resource
	private CoterieInfoRepository coterieInfoRepository;
	@Reference
	private UserApi userApi;
	@Reference
	private CoterieApi coterieApi;
	@Reference
	private ReleaseInfoApi releaseInfoApi;
	@Reference
	private TopicApi topicApi;
	@Reference
	private TopicPostApi topicPostApi;
	@Reference
	private ResourceApi resourceApi;

	@Override
	public Response<PageList<UserSimpleVo>> searchUser(String keyWord, Integer page, Integer size) {
		try {
			List<UserInfo> list = userRepository.search(keyWord, page, size);
			List<UserSimpleVo> rstList = Lists.newArrayList();
			for (int i = 0; i < list.size(); i++) {
				UserInfo info = list.get(i);
				UserSimpleVo vo = new UserSimpleVo();
				if (info.getUserBaseInfo() != null) {
					BeanUtils.copyProperties(info.getUserBaseInfo(), vo);
					rstList.add(vo);
				}
			}
			PageList<UserSimpleVo> pageList = new PageList<UserSimpleVo>();
			pageList.setEntities(rstList);
			pageList.setCount(null);
			pageList.setCurrentPage(page);
			pageList.setPageSize(size);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("searchUser", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<StarInfoVO>> searchStarUser(StarInfoDTO starInfoDTO) {
		logger.info("searchStarUser request, starInfoDTO: {}", GsonUtils.parseJson(starInfoDTO));

		List<UserInfo> list = userRepository.searchStarUser(starInfoDTO);
		// 数据转换UserInfo -> StarInfoVO
		List<StarInfoVO> starInfoVOList = Lists.newArrayList();

		if (CollectionUtils.isNotEmpty(list)) {
			for (UserInfo userInfo : list) {
				StarInfoVO starInfoVO = new StarInfoVO();

				if (userInfo.getUserBaseInfo() != null) {
					// 用户数据
					UserSimpleVO simpleVO = new UserSimpleVO();
					BeanUtils.copyProperties(userInfo.getUserBaseInfo(), simpleVO);
					starInfoVO.setUserInfo(simpleVO);
				}
				if (userInfo.getUserStarInfo() != null) {
					// 达人数据
					UserStarSimpleVo starSimpleVo = new UserStarSimpleVo();
					BeanUtils.copyProperties(userInfo.getUserStarInfo(), starSimpleVo);
					starInfoVO.setStarInfo(starSimpleVo);
				}
				starInfoVOList.add(starInfoVO);

			}
		}

		PageList<StarInfoVO> pageList = new PageModel<StarInfoVO>().getPageList(starInfoVOList);
		logger.info("searchStarUser result, pageList: {}", GsonUtils.parseJson(pageList));
		return ResponseUtils.returnObjectSuccess(pageList);
	}

	@Override
	public Response<PageList<ResourceInfoVo>> searchTopicInfo(String keyWord, Integer page, Integer size) {
		try {
			List<ResourceInfo> list = resourceInfoRepository.searchTopicInfo(keyWord, page, size);
			List<ResourceInfoVo> rstList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				ResourceInfo info = list.get(i);
				ResourceInfoVo vo = GsonUtils.parseObj(info, ResourceInfoVo.class);
				rstList.add(vo);

				if (info.getResourceType() == 1 && info.getTopicInfo() != null
						&& info.getTopicInfo().getCreateUserId() != null) {
					Optional<UserInfo> user = userRepository.findById(info.getTopicInfo().getCreateUserId());
					if (user.isPresent()) {
						UserSimpleVo userVo = new UserSimpleVo();
						BeanUtils.copyProperties(user.get(), userVo);
						vo.setCreateUserInfo(userVo);
					}
				}

				if (info.getResourceType() == 2 && info.getTopicPostInfo() != null
						&& info.getTopicPostInfo().getCreateUserId() != null) {
					Optional<UserInfo> user = userRepository.findById(info.getTopicPostInfo().getCreateUserId());
					if (user.isPresent()) {
						UserSimpleVo userVo = new UserSimpleVo();
						BeanUtils.copyProperties(user.get(), userVo);
						vo.setCreateUserInfo(userVo);
					}
				}
			}

			PageList<ResourceInfoVo> pageList = new PageList<ResourceInfoVo>();
			pageList.setEntities(rstList);
			pageList.setCount(null);
			pageList.setCurrentPage(page);
			pageList.setPageSize(size);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("searchUser", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<ResourceInfoVo>> searchReleaseInfo(String keyWord, Integer page, Integer size) {
		try {
			List<ResourceInfo> list = resourceInfoRepository.searchReleaseInfo(keyWord, page, size);
			List<ResourceInfoVo> rstList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				ResourceInfo info = list.get(i);
				ResourceInfoVo vo = GsonUtils.parseObj(info, ResourceInfoVo.class);
				rstList.add(vo);

				if (info.getReleaseInfo() != null && info.getReleaseInfo().getCreateUserId() != null) {
					Optional<UserInfo> user = userRepository.findById(info.getReleaseInfo().getCreateUserId());
					if (user.isPresent()) {
						UserSimpleVo userVo = new UserSimpleVo();
						BeanUtils.copyProperties(user.get(), userVo);
						vo.setCreateUserInfo(userVo);
					}
				}
			}

			PageList<ResourceInfoVo> pageList = new PageList<ResourceInfoVo>();
			pageList.setEntities(rstList);
			pageList.setCount(null);
			pageList.setCurrentPage(page);
			pageList.setPageSize(size);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("searchUser", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public Response<PageList<CoterieInfoVo>> searchCoterieInfo(String keyWord, Integer page, Integer size) {
		try {
			List<CoterieInfo> list = coterieInfoRepository.search(keyWord, page, size);
			List<CoterieInfoVo> rstList = new ArrayList<>();
			for (int i = 0; i < list.size(); i++) {
				CoterieInfo info = list.get(i);
				CoterieInfoVo vo = new CoterieInfoVo();
				BeanUtils.copyProperties(info, vo);
				rstList.add(vo);
				if (vo.getCreateUserId() == null) {
					continue;
				}

				Optional<UserInfo> user = userRepository.findById(vo.getCreateUserId());
				if (user.isPresent()) {
					UserSimpleVo userVo = new UserSimpleVo();
					BeanUtils.copyProperties(user.get(), userVo);
					vo.setUser(userVo);
				}
			}
			PageList<CoterieInfoVo> pageList = new PageList<CoterieInfoVo>();
			pageList.setEntities(rstList);
			pageList.setCount(null);
			pageList.setCurrentPage(page);
			pageList.setPageSize(size);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("searchUser", e);
			return ResponseUtils.returnException(e);
		}
	}

	@Override
	public void rebuildUserInfo() {
		String currentDate = DateUtils.getDate();
		Response<List<Long>> rst = userApi.getUserIdByCreateDate("2010-01-01 00:00:00", currentDate + " 23:59:59");
		List<Long> list = ResponseUtils.getResponseData(rst);
		if (list == null || list.isEmpty()) {
			return;
		}

		// 删除index
		elasticsearchTemplate.deleteIndex("quanhu-v2-userinfo");
		// 每100条批量导入
		List<Long> ulist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			ulist.add(list.get(i));
			if (ulist.size() >= 100 || (i + 1) == list.size()) {
				List<UserBaseInfoVO> resList = ResponseUtils.getResponseData(userApi.getAllByUserIds(ulist));
				if (resList!=null) {
					saveAllUsers(resList);
				}
				ulist.clear();
			}
		}
		logger.info("userInfo index rebuild completed");
	}

	@Override
	public void rebuildCoterieInfo() {
		String currentDate = DateUtils.getDate();
		Response<List<Long>> rst = coterieApi.getKidByCreateDate("2010-01-01 00:00:00", currentDate + " 23:59:59");
		List<Long> list = ResponseUtils.getResponseData(rst);
		if (list == null || list.isEmpty()) {
			return;
		}

		// 删除index
		elasticsearchTemplate.deleteIndex("quanhu-v2-coterieinfo");
		// 每100条批量导入
		List<Long> kidlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			kidlist.add(list.get(i));
			if (kidlist.size() >= 100 || (i + 1) == list.size()) {
				List<Coterie> resList = ResponseUtils.getResponseData(coterieApi.getByKids(kidlist));
				if (resList != null) {
					saveAllCoterie(resList);
				}
				kidlist.clear();
			}
		}
		logger.info("coterieInfo index rebuild completed");
	}

	@Override
	public void rebuildResourceInfo() {
		// 删除index
		elasticsearchTemplate.deleteIndex("quanhu-v2-resourceinfo");
		rebuildReleaseInfo();
		rebuildTopicInfo();
		rebuildTopicPostInfo();
	}

	private void rebuildReleaseInfo() {
		String currentDate = DateUtils.getDate();
		Response<List<Long>> rst = releaseInfoApi.getKidByCreatedate("2010-01-01 00:00:00", currentDate + " 23:59:59");
		List<Long> list = ResponseUtils.getResponseData(rst);
		if (list == null || list.isEmpty()) {
			return;
		}

		// 每100条批量导入
		Set<Long> kidlist = new HashSet<>();
		for (int i = 0; i < list.size(); i++) {
			kidlist.add(list.get(i));
			if (kidlist.size() >= 100 || (i + 1) == list.size()) {
				List<ReleaseInfoVo> resList = ResponseUtils.getResponseData(releaseInfoApi.selectByKids(kidlist));
				if (resList != null) {
					saveAllReleaseInfo(resList);
				}
				kidlist.clear();
			}
		}
		logger.info("releaseInfo index rebuild completed");
	}

	private void rebuildTopicInfo() {
		String currentDate = DateUtils.getDate();
		Response<List<Long>> rst = topicApi.getKidByCreatedate("2010-01-01 00:00:00", currentDate + " 23:59:59");
		List<Long> list = ResponseUtils.getResponseData(rst);
		if (list == null || list.isEmpty()) {
			return;
		}

		// 每100条批量导入
		List<Long> kidlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			kidlist.add(list.get(i));
			if (kidlist.size() >= 100 || (i + 1) == list.size()) {
				List<Topic> resList = ResponseUtils.getResponseData(topicApi.getByKids(kidlist));
				if (resList != null) {
					saveAllTopicInfo(resList);
				}
				kidlist.clear();
			}
		}
		logger.info("topicInfo index rebuild completed");
	}

	private void rebuildTopicPostInfo() {
		String currentDate = DateUtils.getDate();
		Response<List<Long>> rst = topicPostApi.getKidByCreatedate("2010-01-01 00:00:00", currentDate + " 23:59:59");
		List<Long> list = ResponseUtils.getResponseData(rst);
		if (list == null || list.isEmpty()) {
			return;
		}

		// 每100条批量导入
		List<Long> kidlist = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			kidlist.add(list.get(i));
			if (kidlist.size() >= 100 || (i + 1) == list.size()) {
				List<TopicPostWithBLOBs> resList = ResponseUtils.getResponseData(topicPostApi.getByKids(kidlist));
				if (resList != null) {
					saveAllTopicPostInfo(resList);
				}
				kidlist.clear();
			}
		}
		logger.info("topicPostInfo index rebuild completed");
	}

	private void saveAllReleaseInfo(List<ReleaseInfoVo> volist) {
		// 热度信息获取
		Set<String> resourceIds = new HashSet<>();
		for (int i = 0; i < volist.size(); i++) {
			resourceIds.add(volist.get(i).getKid().toString());
		}
		Map<String, ResourceVo> resMap = ResponseUtils.getResponseData(resourceApi.getResourcesByIds(resourceIds));
		List<ResourceInfo> rlist = new ArrayList<>();
		for (int i = 0; i < volist.size(); i++) {
			ReleaseInfo d = GsonUtils.parseObj(volist.get(i), ReleaseInfo.class);
			ResourceInfo resource = new ResourceInfo();
			resource.setCreateDate(d.getCreateDate());
			resource.setKid(d.getKid());
			resource.setLastHeat(0L);
			if (resMap != null) {
				ResourceVo r = resMap.get(d.getKid());
				if (r != null && r.getHeat() != null) {
					resource.setLastHeat(r.getHeat());
				}
			}
			resource.setReleaseInfo(d);
			resource.setResourceType(3);
			rlist.add(resource);
		}
		resourceInfoRepository.saveAll(rlist);
	}

	private void saveAllTopicInfo(List<Topic> volist) {
		// 热度信息获取
		Set<String> resourceIds = new HashSet<>();
		for (int i = 0; i < volist.size(); i++) {
			resourceIds.add(volist.get(i).getKid().toString());
		}
		Map<String, ResourceVo> resMap = ResponseUtils.getResponseData(resourceApi.getResourcesByIds(resourceIds));

		List<ResourceInfo> rlist = new ArrayList<>();
		for (int i = 0; i < volist.size(); i++) {
			TopicInfo topic = GsonUtils.parseObj(volist.get(i), TopicInfo.class);
			ResourceInfo resource = new ResourceInfo();
			resource.setCreateDate(topic.getCreateDate());
			resource.setKid(topic.getKid());
			resource.setLastHeat(0L);
			if (resMap != null) {
				ResourceVo r = resMap.get(topic.getKid());
				if (r != null && r.getHeat() != null) {
					resource.setLastHeat(r.getHeat());
				}
			}
			resource.setTopicInfo(topic);
			resource.setResourceType(1);
			rlist.add(resource);
		}
		resourceInfoRepository.saveAll(rlist);
	}

	private void saveAllTopicPostInfo(List<TopicPostWithBLOBs> volist) {
		// 热度信息获取
		Set<String> resourceIds = new HashSet<>();
		for (int i = 0; i < volist.size(); i++) {
			resourceIds.add(volist.get(i).getKid().toString());
		}
		Map<String, ResourceVo> resMap = ResponseUtils.getResponseData(resourceApi.getResourcesByIds(resourceIds));
		List<ResourceInfo> rlist = new ArrayList<>();
		for (int i = 0; i < volist.size(); i++) {
			TopicPostInfo d = GsonUtils.parseObj(volist.get(i), TopicPostInfo.class);
			ResourceInfo resource = new ResourceInfo();
			resource.setCreateDate(d.getCreateDate());
			resource.setKid(d.getKid());
			resource.setLastHeat(0L);
			if (resMap != null) {
				ResourceVo r = resMap.get(d.getKid());
				if (r != null && r.getHeat() != null) {
					resource.setLastHeat(r.getHeat());
				}
			}
			resource.setTopicPostInfo(d);
			resource.setResourceType(2);
			rlist.add(resource);
		}
		resourceInfoRepository.saveAll(rlist);
	}

	private void saveAllUsers(List<UserBaseInfoVO> volist) {
		List<UserInfo> userlist = new ArrayList<>();
		for (int j = 0; j < volist.size(); j++) {
			UserBaseInfo baseInfo = GsonUtils.parseObj(volist.get(j), UserBaseInfo.class);
			UserInfo userInfo = new UserInfo();
			userInfo.setUserBaseInfo(baseInfo);
			userInfo.setUserId(baseInfo.getUserId());
			userlist.add(userInfo);
		}
		userRepository.saveAll(userlist);
	}

	private void saveAllCoterie(List<Coterie> volist) {
		List<CoterieInfo> list = new ArrayList<>();
		for (int i = 0; i < volist.size(); i++) {
			Coterie c = volist.get(i);
			CoterieInfo info = GsonUtils.parseObj(c, CoterieInfo.class);
			info.setKid(c.getCoterieId());
			info.setCoterieName(c.getName());
			info.setState(c.getStatus());
			list.add(info);
		}
		coterieInfoRepository.saveAll(list);
	}
}
