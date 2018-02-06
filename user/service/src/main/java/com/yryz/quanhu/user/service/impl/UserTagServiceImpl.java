package com.yryz.quanhu.user.service.impl;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.yryz.common.utils.BeanUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.user.vo.UserTagVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dao.UserTagDao;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.entity.UserTag;
import com.yryz.quanhu.user.service.UserTagService;

@Service
public class UserTagServiceImpl implements UserTagService {
	private static final Logger logger = LoggerFactory.getLogger(UserTagServiceImpl.class);
	
	@Autowired
	private UserTagDao mysqlDao;
	@Reference
	private IdAPI idApi;
	@Override
	public int delete(Long userId,Integer tagType) {
		try {
			return mysqlDao.delete(userId,tagType);
		} catch (Exception e) {
			logger.error("[userTag.delete]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public int insert(UserTag record) {
		try {
			record.setKid(idApi.getKid(IdConstants.QUANUH_USER_TAG).getData());
			record.setCreateDate(new Date());
			return mysqlDao.insert(record);
		} catch (Exception e) {
			logger.error("[userTag.delete]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public void batch(UserTagDTO tagDTO) {
		//List<String> tags = mysqlDao.selectTagByUserId(tagDTO.getUserId(), tagDTO.getTagType().intValue());
		String[] tagIdArray = StringUtils.split(tagDTO.getTagIds(), ",");
		List<UserTag> userTags = new ArrayList<>(); 
		Date nowTime = new Date();
		//删除旧的标签
		delete(tagDTO.getUserId(), tagDTO.getTagType().intValue());
		
		for(int i = 0 ; i < tagIdArray.length;i++){
			Long tagId = NumberUtils.toLong(tagIdArray[i]);
			UserTag tag = new UserTag();
			tag.setCreateDate(nowTime);
			tag.setLastUpdateUserId(tagDTO.getUpdateUserId() == null ? 0L : tagDTO.getUpdateUserId());
			tag.setTagId(tagId);
			tag.setUserId(tagDTO.getUserId());
			tag.setTagType(tagDTO.getTagType());
			tag.setKid(ResponseUtils.getResponseData(idApi.getKid(IdConstants.QUANUH_USER_TAG)));
			userTags.add(tag);
			/*if(CollectionUtils.isNotEmpty(tags)){
				boolean tagFlag = false;
				Long oldTagId = null;
				for(int j = 0 ; j < tags.size() ; j++){
					oldTagId = NumberUtils.toLong(tags.get(j));
					if(tagId == oldTagId){
						tagFlag = true;
					}
				}
				if(!tagFlag){
					userTags.add(tag);
				}
			}else{
				userTags.add(tag);
			}*/
		}
		if(CollectionUtils.isNotEmpty(userTags)){
			try {
				mysqlDao.batchSave(userTags);
			} catch (Exception e) {
				logger.error("[userTag.batch]",e);
				throw new MysqlOptException(e);
			}
		}
	}

	@Override
	public List<String> getTagByUserId(Long userId, Integer tagType) {
		try {
			return mysqlDao.selectTagByUserId(userId, tagType);
		} catch (Exception e) {
			logger.error("[userTag.batch]",e);
			throw new MysqlOptException(e);
		}
	}

	@Override
	public Map<Long, List<UserTagVO>> getUserTags(@Param("userIds") List<Long> userIds) {
		Map<Long, List<UserTagVO>> result = Maps.newHashMap();
		Multimap<Long, UserTagVO> userTagMultiMap = ArrayListMultimap.create();
		List<UserTag> userTags = mysqlDao.getUserTags(userIds);
		if (CollectionUtils.isNotEmpty(userTags)) {
			for (UserTag userTag : userTags) {
				UserTagVO userTagVO = new UserTagVO();
				BeanUtils.copyProperties(userTagVO, userTag);
				userTagMultiMap.put(userTag.getUserId(), userTagVO);
			}
		}
		if (MapUtils.isNotEmpty(userTagMultiMap.asMap())) {
			for (Map.Entry<Long, Collection<UserTagVO>> multiEntry : userTagMultiMap.asMap().entrySet()) {
				result.put(multiEntry.getKey(), Lists.newArrayList(multiEntry.getValue()));
			}
		}
		return result;
	}

	@Override
	public List<UserTagDTO> getUserTags(Long userId) {
		return mysqlDao.getUserUnionTags(userId);
	}

}
