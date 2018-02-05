package com.yryz.quanhu.dymaic.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.PageModel;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.dymaic.canal.dao.CoterieInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.ResourceInfoRepository;
import com.yryz.quanhu.dymaic.canal.dao.UserRepository;
import com.yryz.quanhu.dymaic.canal.entity.CoterieInfo;
import com.yryz.quanhu.dymaic.canal.entity.EventAccountInfo;
import com.yryz.quanhu.dymaic.canal.entity.ReleaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.ResourceInfo;
import com.yryz.quanhu.dymaic.canal.entity.TagInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicInfo;
import com.yryz.quanhu.dymaic.canal.entity.TopicPostInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserBaseInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserRegLog;
import com.yryz.quanhu.dymaic.canal.entity.UserStarInfo;
import com.yryz.quanhu.dymaic.canal.entity.UserTagInfo;
import com.yryz.quanhu.dymaic.vo.CoterieInfoVo;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.ResourceInfoVo;
import com.yryz.quanhu.dymaic.vo.UserSimpleVo;
import com.yryz.quanhu.order.api.OrderApi;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.resource.topic.entity.Topic;
import com.yryz.quanhu.resource.topic.entity.TopicPostWithBLOBs;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventAcount;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarInfoDTO;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserOperateApi;
import com.yryz.quanhu.user.service.UserStarApi;
import com.yryz.quanhu.user.service.UserTagApi;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserDynamicVO;
import com.yryz.quanhu.user.vo.UserInfoVO;
import com.yryz.quanhu.user.vo.UserRegLogVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;
import com.yryz.quanhu.user.vo.UserTagVO;

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

	@Reference(check = false)
	private UserStarApi userStarApi;

	@Reference(check = false)
	private UserTagApi userTagApi;
	@Reference(check = false)
	private UserOperateApi userOperateApi;
	@Reference
	private OrderApi orderApi;
	@Reference(check = false)
	private EventAcountApiService acountApiService;

	@Reference(check = false)
    private CountApi countApi;
	
	@Autowired
	private DymaicServiceImpl dymaicService;

	private static final Function<Long, String> LONG_TO_STRING_FUNCTION = new Function<Long, String>() {
		@Override
		public String apply(Long input) {
			return input.toString();
		}
	};

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

	// 非推荐用户状态
	private static final Integer NOT_RECOMMEND = 10;

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
			// 关系数据
			Long userId = starInfoDTO.getUserId();
			Set<String> targetIds = Sets.newHashSet();
			for (UserInfo userInfo : list) {
				if (userInfo != null) {
					targetIds.add(userInfo.getUserId().toString());
				}
			}

			Map<String, UserSimpleVO> userSimpleMap = null;
			try {
				Response<Map<String, UserSimpleVO>> userApiUserSimple = userApi.getUserSimple(userId, targetIds);
				userSimpleMap = userApiUserSimple.getData();
			} catch (Exception e) {
				logger.error("searchStarUser error", e);
			}

			// 动态数据
			Set<Long> dynamicUserIds = getNeedDynamicUserIds(list);
			Map<Long, Dymaic> dymaicMap = null;
			if (CollectionUtils.isNotEmpty(dynamicUserIds)) {
				try {
					dymaicMap = dymaicService.getLastSend(dynamicUserIds);
					logger.info("dymaicService.getLastSend result: {}", GsonUtils.parseJson(dymaicMap));
				} catch (Exception e) {
					logger.error("dymaicService getLastSend error", e);
				}
			}

			for (UserInfo userInfo : list) {
				StarInfoVO starInfoVO = new StarInfoVO();
				// 用户数据
				starInfoVO.parseUser(userInfo.getUserId().toString(), userSimpleMap);

				if (userInfo.getUserStarInfo() != null) {
					// 达人数据
					UserStarSimpleVo starSimpleVo = new UserStarSimpleVo();
					BeanUtils.copyProperties(userInfo.getUserStarInfo(), starSimpleVo);
					starInfoVO.setStarInfo(starSimpleVo);
				}
				// 设置动态数据
				setDynamicInfo(userInfo.getUserId(), starInfoVO, dymaicMap);
				starInfoVOList.add(starInfoVO);

			}
		}

		PageList<StarInfoVO> pageList = new PageModel<StarInfoVO>().getPageList(starInfoVOList);
		logger.info("searchStarUser result, pageList: {}", GsonUtils.parseJson(pageList));
		return ResponseUtils.returnObjectSuccess(pageList);
	}

	private void setDynamicInfo(Long userId, StarInfoVO starInfoVO, Map<Long, Dymaic> dymaicMap) {
		if (MapUtils.isNotEmpty(dymaicMap)) {
			Dymaic dymaic = dymaicMap.get(userId);
			if (dymaic != null) {
				UserDynamicVO userDynamicVO = new UserDynamicVO();
				BeanUtils.copyProperties(dymaic, userDynamicVO);
				starInfoVO.setDynamic(userDynamicVO);
			}
		}
	}

	private Set<Long> getNeedDynamicUserIds(List<UserInfo> list) {
		Set<Long> userIds = Sets.newHashSet();
		for (UserInfo userInfo : list) {
			if (userInfo != null && userInfo.getUserStarInfo() != null) {
				UserStarInfo starInfo = userInfo.getUserStarInfo();
				if (starInfo != null && starInfo.getRecommendStatus() != null
						&& starInfo.getRecommendStatus().equals(NOT_RECOMMEND.byteValue())) {
					userIds.add(userInfo.getUserId());
				}
			}
		}
		logger.info("getNeedDynamicUserIds result: {}", GsonUtils.parseJson(userIds));
		return userIds;
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

				// 2帖子
                if (info.getResourceType() == 2) {
                    if (info.getTopicPostInfo() != null && info.getTopicPostInfo().getCreateUserId() != null) {
                        Optional<UserInfo> user = userRepository.findById(info.getTopicPostInfo().getCreateUserId());
                        if (user.isPresent()) {
                            UserSimpleVo userVo = new UserSimpleVo();
                            BeanUtils.copyProperties(user.get(), userVo);
                            vo.setCreateUserInfo(userVo);
                        }
                    }

                    // 帖子浏览数
                    Map<String, Long> statistics = new HashMap<>();
                    try {
                        String countType = BehaviorEnum.Read.getCode();
                        statistics = ResponseUtils.getResponseData(countApi.getCount(countType, info.getKid(), null));
                    } catch (Exception e) {
                        logger.warn("cannot get statics cause: " + e.getMessage());
                    }
                    vo.setStatistics(statistics);
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
				if (resList != null) {
					saveAllUsers(ulist, resList);
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

	@Override
	public Response<PageList<UserInfoVO>> adminSearchUser(AdminUserInfoDTO adminUserDTO) {
		try {
			logger.info("adminSearchUser request, adminUserDTO: {}", GsonUtils.parseJson(adminUserDTO));
			checkAdminParam(adminUserDTO);
			List<UserInfo> userInfoList = userRepository.adminSearchUser(adminUserDTO);
			List<UserInfoVO> userInfoVOS = GsonUtils.parseList(userInfoList, UserInfoVO.class);
			if (BooleanUtils.isTrue(adminUserDTO.isNeedIntegral())) {
				setUserOrderIntegral(userInfoVOS);
			}
			PageList<UserInfoVO> pageList = new PageModel<UserInfoVO>().getPageList(userInfoVOS);
			return ResponseUtils.returnObjectSuccess(pageList);
		} catch (Exception e) {
			logger.error("adminSearchUser error", e);
			return ResponseUtils.returnException(e);
		}

	}

	private void checkAdminParam(AdminUserInfoDTO adminUserDTO) {
		if (adminUserDTO == null) {
			throw QuanhuException.busiError("adminUserDTO null");
		}
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

	private void saveAllUsers(List<Long> ulist, List<UserBaseInfoVO> volist) {
		Set<String> stringIds = Sets.newHashSet(FluentIterable.from(ulist).transform(LONG_TO_STRING_FUNCTION).toSet());
		logger.info("saveAllUsers request ulist: {}", GsonUtils.parseJson(ulist));
		Response<Map<String, StarAuthInfo>> starResponse = userStarApi.get(stringIds);
		Response<Map<Long, List<UserTagVO>>> userTagInfoResponse = userTagApi.getUserTags(ulist);
		Response<Map<Long, EventAcount>> eventAcountResponse = acountApiService
				.getEventAcountBatch(Sets.newHashSet(ulist));
		Response<Map<Long, UserRegLogVO>> regLogResponse = userOperateApi.listByUserId(ulist);
		logger.info(
				"saveAllUsers starResponse: {}, userTagInfoResponse: {}, eventAcountResponse: {}, regLogResponse: {}",
				GsonUtils.parseJson(starResponse), GsonUtils.parseJson(userTagInfoResponse),
				GsonUtils.parseJson(eventAcountResponse), GsonUtils.parseJson(regLogResponse));

		List<UserInfo> userlist = new ArrayList<>();
		for (int j = 0; j < volist.size(); j++) {
			UserBaseInfo baseInfo = GsonUtils.parseObj(volist.get(j), UserBaseInfo.class);
			UserInfo userInfo = new UserInfo();
			// 用户基础数据
			userInfo.setUserBaseInfo(baseInfo);
			userInfo.setUserId(baseInfo.getUserId());

			// 达人数据
			setStartInfo(userInfo, starResponse);
			// 标签数据
			setTagInfo(userInfo, userTagInfoResponse);
			// 积分数据
			setEventInfo(userInfo, eventAcountResponse);
			// 注册记录数据
			setRegLogInfo(userInfo, regLogResponse);

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

	/**
	 * 用户相关数据
	 */

	private void setRegLogInfo(UserInfo userInfo, Response<Map<Long, UserRegLogVO>> regLogResponse) {
		if (regLogResponse.success() && MapUtils.isNotEmpty(regLogResponse.getData())) {
			Map<Long, UserRegLogVO> regLogVOMap = regLogResponse.getData();
			UserRegLogVO regLogVO = regLogVOMap.get(userInfo.getUserId());
			if (regLogVO != null) {
				UserRegLog userRegLog = new UserRegLog();
				com.yryz.common.utils.BeanUtils.copyProperties(userRegLog, regLogVO);
				userInfo.setUserRegLog(userRegLog);

			}
		}
	}

	private void setEventInfo(UserInfo userInfo, Response<Map<Long, EventAcount>> eventAcountResponse) {
		if (eventAcountResponse.success() && MapUtils.isNotEmpty(eventAcountResponse.getData())) {
			Map<Long, EventAcount> eventAcountMap = eventAcountResponse.getData();
			EventAcount eventAcount = eventAcountMap.get(userInfo.getUserId());
			if (eventAcount != null) {
				EventAccountInfo eventAccountInfo = new EventAccountInfo();
				com.yryz.common.utils.BeanUtils.copyProperties(eventAccountInfo, eventAcount);
				userInfo.setEventAccountInfo(eventAccountInfo);
			}
		}
	}

	private void setTagInfo(UserInfo userInfo, Response<Map<Long, List<UserTagVO>>> userTagInfoResponse) {
		if (userTagInfoResponse.success() && MapUtils.isNotEmpty(userTagInfoResponse.getData())) {
			Map<Long, List<UserTagVO>> listMap = userTagInfoResponse.getData();
			List<UserTagVO> userTagVOS = listMap.get(userInfo.getUserId());
			if (CollectionUtils.isNotEmpty(userTagVOS)) {
				UserTagInfo userTagInfo = new UserTagInfo();
				List<TagInfo> tagInfoList = Lists.newArrayList();
				for (UserTagVO userTagVO : userTagVOS) {
					TagInfo tagInfo = new TagInfo();
					com.yryz.common.utils.BeanUtils.copyProperties(tagInfo, userTagVO);
					tagInfoList.add(tagInfo);
				}
				userTagInfo.setUserTagInfoList(tagInfoList);

				userInfo.setUserTagInfo(userTagInfo);
			}
		}
	}

	private void setStartInfo(UserInfo userInfo, Response<Map<String, StarAuthInfo>> starResponse) {
		if (starResponse.success() && MapUtils.isNotEmpty(starResponse.getData())) {
			Map<String, StarAuthInfo> authInfoMap = starResponse.getData();
			StarAuthInfo starAuthInfo = authInfoMap.get(userInfo.getUserId().toString());
			if (starAuthInfo != null) {
				UserStarInfo userStarInfo = new UserStarInfo();
				com.yryz.common.utils.BeanUtils.copyProperties(userStarInfo, starAuthInfo);
				userInfo.setUserStarInfo(userStarInfo);
			}
		}
	}

	/**
	 * 聚合用户收入
	 * @param userInfoVOS
	 */
	private void setUserOrderIntegral(List<UserInfoVO> userInfoVOS) {
		if (CollectionUtils.isEmpty(userInfoVOS)) {
			return;
		}
		int userLength = userInfoVOS.size();
		List<Long> userIds = new ArrayList<>(userLength);
		for (int i = 0; i < userLength; i++) {
			UserInfoVO infoVO = userInfoVOS.get(i);
			if (infoVO != null && infoVO.getUserBaseInfo().getUserId() != null
					&& infoVO.getUserBaseInfo().getUserId() != 0l) {
				userIds.add(infoVO.getUserBaseInfo().getUserId());
			}
		}
		Map<Long, Long> map = ResponseUtils.getResponseData(orderApi.getUserTotalIntegral(userIds));
		if (MapUtils.isEmpty(map)) {
			return;
		}
		for (int i = 0; i < userLength; i++) {
			Long userId = userInfoVOS.get(i).getUserBaseInfo().getUserId();
			Long userIntegral = map.get(userId);
			if(userIntegral == null){
				userInfoVOS.get(i).setUserOrderIntegralTotal("0");
			}else{
				userInfoVOS.get(i).setUserOrderIntegralTotal(StringUtils.getTwoPointDouble(userIntegral));
			}
		}
	}
}
