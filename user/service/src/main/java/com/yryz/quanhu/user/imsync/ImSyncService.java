package com.yryz.quanhu.user.imsync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.quanhu.user.dao.UserBaseInfoDao;
import com.yryz.quanhu.user.dao.UserRelationDao;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.imsync.yunxin.ImRemote;
import com.yryz.quanhu.user.utils.ThreadPoolUtil;

@Service
public class ImSyncService {
	private static final Logger logger = LoggerFactory.getLogger(ImSyncService.class);

	@Autowired
	private UserBaseInfoDao userDao;

	@Autowired
	private UserRelationDao relationDao;

	/**
	 * 全量同步用户信息到IM
	 */
	public void syncImUser() {
		logger.info("[sync mysql to im] start sync IM...");
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				syncImUserInfo();
			}
		});
	}
	
	/**
	 * 全量同步好友信息到IM
	 */
	public void syncImFriend() {
		logger.info("[sync mysql to im] start sync IM...");
		ThreadPoolUtil.execue(new Runnable() {
			@Override
			public void run() {
				syncImFriendInfo();
			}
		});
	}
	
	/**
	 * 
	 * @author danshiyu
	 * @date 2017年10月12日
	 * @Description 同步im用户
	 */
	@SuppressWarnings("resource")
	private void syncImUserInfo() {
		long start = System.currentTimeMillis();
		logger.info("[sync mysql to im] start sync im user...");
		int total = 0;
		try {
			// 从mysql查询
			Page<UserBaseInfo> page = getAllUserInfo(1);
			total = (int) page.getTotal();
			ThreadPoolUtil.execue(new SyncImUser(page.getResult()));
			for (int i = 2; i < page.getPages() + 1; i++) {
				page = getAllUserInfo(i);
				ThreadPoolUtil.execue(new SyncImUser(page.getResult()));
			}
		} catch (Exception e) {
			logger.error("[sync mysql to im] syncImUser error", e);
		} finally {
			long takeTime = System.currentTimeMillis() - start;
			logger.info("[sync mysql to im] complete sync imUser... total " + total + ", take time" + takeTime);
		}
	}

	/**
	 * 
	 * @author danshiyu
	 * @date 2017年10月12日
	 * @Description 同步im好友
	 */
	@SuppressWarnings("resource")
	private void syncImFriendInfo() {
		long start = System.currentTimeMillis();
		logger.info("[sync mysql to im] start sync im friend...");
		int total = 0;
		try {
			// 从mysql查询
			Page<UserRelationDto> page = getAllRelation(1);
			total = (int) page.getTotal();
			ThreadPoolUtil.execue(new SyncImFriend(page.getResult()));
			for (int i = 2; i < page.getPages() + 1; i++) {
				page = getAllRelation(i);
				ThreadPoolUtil.execue(new SyncImFriend(page.getResult()));
			}
		} catch (Exception e) {
			logger.error("[sync mysql to im] syncImFriendInfo error", e);
		} finally {
			long takeTime = System.currentTimeMillis() - start;
			logger.info(
					"[sync mysql to im] complete sync syncImFriendInfo... total " + total + ", take time" + takeTime);
		}
	}

	/**
	 * 分页获取用户信息
	 * 
	 * @date 2017年9月26日
	 * @param pageNum
	 * @return
	 */
	private Page<UserBaseInfo> getAllUserInfo(int pageNum) {
		Page<UserBaseInfo> page = PageHelper.startPage(pageNum, 5000);
		try {
			Map<String, Object> map = new HashMap<>();
			map.put("appId", "vebff12m1762");
			userDao.getUserByParams(map);
		} catch (Exception e) {
			logger.error("[userDao.getUserByParams]", e);
		}
		return page;
	}

	/**
	 * 分页获取好友信息
	 * 
	 * @date 2017年9月26日
	 * @param pageNum
	 * @return
	 */
	private Page<UserRelationDto> getAllRelation(int pageNum) {
		Page<UserRelationDto> page = PageHelper.startPage(pageNum, 5000);
		try {
			@SuppressWarnings("unused")
			List<UserRelationDto> relationDtos = relationDao.selectAllFriend(5);
		} catch (Exception e) {
			logger.error("[relationDao.getAllRelation]", e);
		}
		return page;
	}

	/**
	 * 好友同步线程
	 *
	 */
	private class SyncImFriend implements Runnable {
		List<UserRelationDto> perList = null;

		public SyncImFriend(List<UserRelationDto> perLists) {
			perList = new ArrayList<UserRelationDto>(perLists);
		}

		@Override
		public void run() {
			if (CollectionUtils.isEmpty(perList)) {
				return;
			}
			for (int i = 0; i < perList.size(); i++) {
				UserRelationDto relationDto = perList.get(i);
				ImRemote.addFirend(relationDto.getSourceUserId(), relationDto.getTargetUserId(),
						relationDto.getUserRemarkName());
			}
		}
	}

	/**
	 * 用户信息同步线程
	 *
	 */
	private class SyncImUser implements Runnable {
		List<UserBaseInfo> perList = null;

		public SyncImUser(List<UserBaseInfo> perLists) {
			perList = new ArrayList<UserBaseInfo>(perLists);
		}

		@Override
		public void run() {
			if (CollectionUtils.isEmpty(perList)) {
				return;
			}
			for (int i = 0; i < perList.size(); i++) {
				UserBaseInfo baseInfo = perList.get(i);
				ImRemote.add(baseInfo.getUserId().toString(), baseInfo.getUserNickName(), baseInfo.getUserImg());
			}
		}

	}
}
