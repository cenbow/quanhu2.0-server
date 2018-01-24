package com.yryz.quanhu.user.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.MysqlOptException;
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
		List<String> tags = mysqlDao.selectTagByUserId(tagDTO.getUserId(), tagDTO.getTagType().intValue());
		String[] tagIdArray = StringUtils.split(tagDTO.getTagIds(), ",");
		List<UserTag> userTags = new ArrayList<>(); 
		Date nowTime = new Date();
		//遍历用户选择的tag,筛选重复的tag,把库里面不存在的tag作为新的tag插入数据库,没有选择tag直接插入
		for(int i = 0 ; i < tagIdArray.length;i++){
			Long tagId = NumberUtils.toLong(tagIdArray[i]);
			UserTag tag = new UserTag();
			tag.setCreateDate(nowTime);
			tag.setCreateUserId(tagDTO.getUpdateUserId() == null ? 0l : tagDTO.getUpdateUserId());
			tag.setTagId(tagId);
			tag.setUserId(tagDTO.getUserId());
			tag.setTagType(tagDTO.getTagType());
			tag.setKid(idApi.getKid(IdConstants.QUANUH_USER_TAG).getData());
			
			if(CollectionUtils.isNotEmpty(tags)){
				boolean tagFlag = false;
				Long oldTagId = null;
				for(int j = 0 ; j < tags.size() ; j++){
					oldTagId = NumberUtils.toLong(tags.get(i));
					if(tagId == oldTagId){
						tagFlag = true;
					}
				}
				if(!tagFlag){
					userTags.add(tag);
				}
			}else{
				userTags.add(tag);
			}
		}
	}

}
