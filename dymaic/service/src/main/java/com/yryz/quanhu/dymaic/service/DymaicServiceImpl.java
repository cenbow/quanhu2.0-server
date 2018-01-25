package com.yryz.quanhu.dymaic.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.Response;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.dymaic.dao.DymaicDao;
import com.yryz.quanhu.dymaic.dao.redis.DymaicCache;
import com.yryz.quanhu.dymaic.mq.DymaicSender;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 04
 * <p>
 * <p>
 * TODO
 * 对接动态写入、对接统计查询
 */
@Service
public class DymaicServiceImpl {

    Logger logger = LoggerFactory.getLogger(DymaicServiceImpl.class);

    @Autowired
    DymaicDao dymaicDao;

    @Autowired
    DymaicCache dymaicCache;

    @Autowired
    DymaicSender dymaicSender;

    @Reference
    UserApi userApi;

    @Reference
    UserRelationApi userRelationApi;

    @Reference
    IdAPI idAPI;

    /**
     * 发布动态
     *
     * @param dymaic
     * @return
     */
    public Boolean send(Dymaic dymaic) {
        Response<Long> idRsp = idAPI.getKid("dymaic");
        dymaic.setKid(idRsp.getData());

        //write db
        dymaicDao.insert(dymaic);

        //write cache
        dymaicCache.addDynamic(dymaic);

        //write sendList
        dymaicCache.addSendList(dymaic.getUserId(), dymaic.getKid());

        //mq
        dymaicSender.directSend(dymaic);

        return true;
    }

    /**
     * 删除动态
     *
     * @param userId
     * @param kid
     * @return
     */
    public Boolean delete(Long userId, Long kid) {
        //write db
        Dymaic dymaic = new Dymaic();
        dymaic.setKid(kid);
        dymaic.setDelFlag(11);
        dymaicDao.update(dymaic);

        //update cache
        dymaicCache.addDynamic(dymaic);

        //update sendList
        dymaicCache.removeSendList(userId, kid);

        return true;
    }

    /**
     * 查询动态
     *
     * @param kid
     * @return
     */
    public Dymaic get(Long kid) {
        Dymaic dymaic = dymaicCache.getDynamic(kid);

        if (dymaic == null) {
            dymaic = dymaicDao.get(kid);

            if (dymaic != null) {
                if (dymaic.getDelFlag() != 10) {
                    Dymaic tmp = new Dymaic();
                    tmp.setKid(dymaic.getKid());
                    tmp.setDelFlag(dymaic.getDelFlag());
                    dymaic = tmp;
                }
                dymaicCache.addDynamic(dymaic);
            } else {
                dymaic = new Dymaic();
            }
        }

        return dymaic;
    }

    /**
     * 批量查询动态信息
     *
     * @param kids
     * @return
     */
    public Map<Long, Dymaic> get(List<Long> kids) {
        if (kids == null || kids.isEmpty()) {
            return null;
        }

        Map<Long, Dymaic> result = new HashMap<>();

        //查询缓存
        List<Dymaic> cacheList = dymaicCache.getDynamic(kids);
        List<Long> nullIdList = new ArrayList<>();
        int index = 0;
        for (Dymaic dymaic : cacheList) {
            if (dymaic == null) {
                nullIdList.add(kids.get(index));
            } else {
                result.put(dymaic.getKid(), dymaic);
            }
            index++;
        }

        //从数据库查询
        if (!nullIdList.isEmpty()) {
            List<Dymaic> dbList = dymaicDao.getByids(nullIdList);
            List<Dymaic> writeCacheList = new ArrayList<>();
            for (Dymaic dymaic : dbList) {
                if (dymaic.getDelFlag() == 10) {
                    writeCacheList.add(dymaic);
                    result.put(dymaic.getKid(), dymaic);
                } else {
                    Dymaic tmp = new Dymaic();
                    tmp.setKid(dymaic.getKid());
                    tmp.setDelFlag(dymaic.getDelFlag());
                    writeCacheList.add(tmp);
                    result.put(dymaic.getKid(), tmp);
                }
            }

            if (writeCacheList != null) {
                //写入redis
                dymaicCache.addDynamic(writeCacheList);
            }
        }

        return result;
    }

    /**
     * 查询个人动态列表
     *
     * @param sourceUserId
     * @param targetUserId
     * @param kid
     * @param limit
     * @return
     */
    public List<DymaicVo> getSendList(Long sourceUserId, Long targetUserId, Long kid, Long limit) {
        Set<Long> kids = dymaicCache.rangeSendList(targetUserId, kid, limit);

        List<DymaicVo> result;
        if (kids == null || kids.isEmpty()) {
            result = new ArrayList<>();
        } else {
            result = this.mergeDymaicVo(sourceUserId, kids);
        }

        return result;
    }

    /**
     * 重构个人动态列表Cache
     *
     * @param userId
     * @return
     */
    public Boolean rebuildSendList(Long userId) {
        List<Long> kids = dymaicDao.getSendListIds(userId);
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.addSendList(userId, new HashSet<>(kids));
        }

        return true;
    }

    /**
     * 查询好友动态列表
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    public List<DymaicVo> getTimeLine(Long userId, Long kid, Long limit) {
        Set<Long> kids = dymaicCache.rangeTimeLine(userId, kid, limit);
        logger.info("dymaicIds size " + kids.size());

        List<DymaicVo> result;
        if (kids == null || kids.isEmpty()) {
            result = new ArrayList<>();
        } else {
            result = this.mergeDymaicVo(userId, kids);
        }

        return result;
    }

    /**
     * 好友动态Push计算
     *
     * @param dymaic
     * @return
     */
    public Boolean pushTimeLine(Dymaic dymaic) {
        if (dymaic == null || StringUtils.isEmpty(dymaic.getUserId()) || dymaic.getKid() == null) {
            return true;
        }

        Long startTime = System.currentTimeMillis();
        Long userId = dymaic.getUserId();
        Long kid = dymaic.getKid();

        //获取粉丝列表
        List<Long> followers = getFans(userId);

        //push
        for (Long followerId : followers) {
            dymaicCache.addTimeLine(followerId, kid);
        }

        logger.info("[dymaic] push dymaicId " + dymaic.getKid() + ", followers " + followers.size() + ", take timeMillis " + (System.currentTimeMillis() - startTime));

        return true;
    }

    /**
     * 删除好友动态中指定用户的动态
     *
     * @param userId
     * @param debarUserId
     * @return
     */
    public Boolean shuffleTimeLine(Long userId, Long debarUserId) {
        Set<Long> kids = dymaicCache.rangeAllSendList(debarUserId);

        if (kids != null && !kids.isEmpty()) {
            dymaicCache.removeTimeLine(userId, kids);
        }

        return true;
    }

    /**
     * 重构好友动态列表Cache
     *
     * @param userId
     * @param limit
     * @return
     */
    public Boolean rebuildTimeLine(Long userId, Long limit) {
        Long startTime = System.currentTimeMillis();
        //获取粉丝列表
        List<Long> followers = getFans(userId);

        //从mysql中取limit条好友动态
        int idsSize = 0;
        List<Long> kids = dymaicDao.getTimeLineIds(followers, limit);

        //批量写入TimeLine
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.addTimeLine(userId, new HashSet<>(kids));
            idsSize = kids.size();
        }

        logger.info("[dymaic] rebuildTimeLine userId " + userId + ", followers " + followers.size() + ", find dymaic " + idsSize + ", take timeMillis " + (System.currentTimeMillis() - startTime));

        return true;
    }

    /**
     * 根据动态id，聚合相应的摘要、用户、统计数据
     *
     * @param kids
     * @return
     */
    private List<DymaicVo> mergeDymaicVo(Long userId, Set<Long> kids) {
        List<DymaicVo> result = new ArrayList<>();

        //1 查询动态摘要
        List<Long> kidList = new ArrayList<>(kids);
        Map<Long, Dymaic> dymaicMap = this.get(kidList);

        //2 查询用户信息
        Map<String, UserSimpleVO> users = null;
        if (dymaicMap != null) {
            Set<String> userIds = new HashSet<>();
            for (Dymaic dymaic : dymaicMap.values()) {
                if (dymaic.getUserId() != null) {
                    userIds.add(dymaic.getUserId().toString());
                }
            }
            if (!userIds.isEmpty()) {
                Response<Map<String, UserSimpleVO>> rsp = userApi.getUserSimple(userId, userIds);
                users = rsp.getData();
            }
        }

        //3 查询统计数据
        Map<String, Dymaic> statistics = null;
        if (!kids.isEmpty()) {
            try {
                //todo rpcInvoke
            } catch (Exception e) {
                // ignore
            }
            statistics = new HashMap<>();
        }

        if (logger.isDebugEnabled()) {
            logger.debug("debug findusers hit " + (users == null ? 0 : users.size()));
            logger.debug("debug findstatics hit " + (statistics == null ? 0 : statistics.size()));
        }

        //4, 聚合动态、用户、统计信息
        if (dymaicMap != null) {
            for (Long kid : kids) {
                Dymaic dymaic = dymaicMap.get(kid);

                if (dymaic != null) {
                    DymaicVo vo = new DymaicVo();
                    BeanUtils.copyProperties(dymaic, vo);

                    if (users != null && users.containsKey(dymaic.getUserId().toString())) {
                        vo.setUser(users.get(dymaic.getUserId().toString()));
                    } else {
                        vo.setUser(new UserSimpleVO());
                    }

                    if (statistics != null && statistics.containsKey(kid)) {
                        vo.setStatistics(statistics.get(kid));
                    } else {
                        vo.setStatistics(new Dymaic());
                    }
                    result.add(vo);
                }
            }
        }

        return result;
    }

    /**
     * 查询粉丝ID列表
     *
     * @param userId
     * @return
     */
    private List<Long> getFans(Long userId) {
        List<Long> result = new ArrayList<>();

        Response<Set<String>> fansRsp = userRelationApi.selectBy(userId.toString(), UserRelationConstant.STATUS.FANS);

        if (fansRsp.getData() != null) {
            for (String fansId : fansRsp.getData()) {
                result.add(Long.parseLong(fansId));
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("debug findFans " + userId + ", hit " + result.size());
        }

        return result;
    }

    public static class TimeLineMonitor {

        private Long startTime;
        private Long endTime;
        private Long userId;
        private Integer followers;
        private Integer size;

        public void TimeMonitor() {
        }
    }
}
