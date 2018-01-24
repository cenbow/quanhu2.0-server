package com.yryz.quanhu.dymaic.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.dymaic.dao.DymaicDao;
import com.yryz.quanhu.dymaic.dao.redis.DymaicCache;
import com.yryz.quanhu.dymaic.mq.DymaicSender;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 04
 * <p>
 *
 * TODO
 * 关于转发类型的判断...
 *
 * 对接粉丝、用户
 * 对接动态写入、统计系统
 */
@Service
public class DymaicServiceImpl implements DymaicService {

    Logger logger = LoggerFactory.getLogger(DymaicServiceImpl.class);

    @Autowired
    DymaicDao dymaicDao;

    @Autowired
    DymaicCache dymaicCache;

    @Autowired
    DymaicSender dymaicSender;

    @Reference
    UserApi userApi;

    @Override
    public Response<Boolean> send(Dymaic dymaic) {
        //write db
        dymaicDao.insert(dymaic);

        //write cache
        dymaicCache.addDynamic(dymaic);

        //write sendList
        dymaicCache.addSendList(dymaic.getUserId(), dymaic.getKid());

        //mq
        dymaicSender.directSend(dymaic);

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Boolean> delete(Long userId, Long kid) {
        //write db
        Dymaic dymaic = new Dymaic();
        dymaic.setKid(kid);
        dymaic.setDelFlag(11);
        dymaicDao.update(dymaic);

        //update cache
        dymaicCache.addDynamic(dymaic);

        //update sendList
        dymaicCache.removeSendList(userId, kid);

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Dymaic> get(Long kid) {
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

        return ResponseUtils.returnObjectSuccess(dymaic);
    }

    @Override
    public Response<Map<Long, Dymaic>> get(List<Long> kids) {
        Map<Long, Dymaic> result = this.getBatch(kids);
        return ResponseUtils.returnObjectSuccess(result);
    }

    /**
     * 批量查询动态信息
     *
     * @param kids
     * @return
     */
    private Map<Long, Dymaic> getBatch(List<Long> kids) {
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

    @Override
    public Response<List<DymaicVo>> getSendList(Long userId, Long kid, Long limit) {
        Set<Long> kids = dymaicCache.rangeSendList(userId, kid, limit);

        List<DymaicVo> result;
        if (kids == null || kids.isEmpty()) {
            result = new ArrayList<>();
        } else {
            result = this.mergeDymaicVo(kids);
        }

        return ResponseUtils.returnListSuccess(result);
    }

    @Override
    public Response<Boolean> rebuildSendList(Long userId) {
        List<Long> kids = dymaicDao.getSendListIds(userId);
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.addSendList(userId, new HashSet<>(kids));
        }

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<List<DymaicVo>> getTimeLine(Long userId, Long kid, Long limit) {
        Set<Long> kids = dymaicCache.rangeTimeLine(userId, kid, limit);
        logger.info("dymaicIds size " + kids.size());

        List<DymaicVo> result;
        if (kids == null || kids.isEmpty()) {
            result = new ArrayList<>();
        } else {
            result = this.mergeDymaicVo(kids);
        }

        return ResponseUtils.returnListSuccess(result);
    }

    @Override
    public Response<Boolean> pushTimeLine(Dymaic dynamic) {
        if (dynamic == null) {
            return ResponseUtils.returnObjectSuccess(true);
        }

        Long startTime = System.currentTimeMillis();
        Long userId = dynamic.getUserId();
        Long kid = dynamic.getKid();

        //获取粉丝列表
        List<Long> followers = getFollowers(userId);

        //push
        for (Long followerId : followers) {
            dymaicCache.addTimeLine(followerId, kid);
        }

        logger.info("[dymaic] push dymaicId " + dynamic.getKid() + ", followers " + followers.size() + ", take timeMillis " + (System.currentTimeMillis() - startTime));

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Boolean> shuffleTimeLine(Long userId, Long debarUserId) {
        Set<Long> kids = dymaicCache.rangeAllSendList(debarUserId);

        if (kids != null && !kids.isEmpty()) {
            dymaicCache.removeTimeLine(userId, kids);
        }

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Boolean> rebuildTimeLine(Long userId, Long limit) {
        Long startTime = System.currentTimeMillis();
        //获取粉丝列表
        List<Long> followers = getFollowers(userId);

        //从mysql中取limit条好友动态
        int idsSize = 0;
        List<Long> kids = dymaicDao.getTimeLineIds(followers, limit);

        //批量写入TimeLine
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.addTimeLine(userId, new HashSet<>(kids));
            idsSize = kids.size();
        }

        logger.info("[dymaic] rebuildTimeLine userId " + userId + ", followers " + followers.size() + ", find dymaic " + idsSize + ", take timeMillis " + (System.currentTimeMillis() - startTime));

        return ResponseUtils.returnObjectSuccess(true);
    }

    /**
     * 根据动态id，聚合相应的摘要、用户、统计数据
     *
     * @param kids
     * @return
     */
    private List<DymaicVo> mergeDymaicVo(Set<Long> kids) {
        List<DymaicVo> result = new ArrayList<>();

        //1 动态摘要
        List<Long> kidList = new ArrayList<>(kids);
        Map<Long, Dymaic> dymaicMap = this.getBatch(kidList);

        //2 用户信息
        Map<String, UserSimpleVO> users = null;
        if (dymaicMap != null) {
            Set<Long> userIds = new HashSet<>();
            for (Dymaic dymaic : dymaicMap.values()) {
                userIds.add(dymaic.getUserId());
            }
            if (!userIds.isEmpty()) {
                //todo rpcInvoke
                users = new HashMap<>();
            }
        }

        //3 统计数据
        Map<String, Dymaic> statistics = null;
        if (!kids.isEmpty()) {
            //todo rpcInvoke
            statistics = new HashMap<>();
        }

        //4, 聚合信息
        if (dymaicMap != null) {
            for (Long kid : kids) {
                Dymaic dymaic = dymaicMap.get(kid);

                if (dymaic != null) {
                    DymaicVo vo = new DymaicVo();
                    BeanUtils.copyProperties(dymaic, vo);

                    if (users != null && users.containsKey(dymaic.getUserId())) {
                        vo.setUser(users.get(dymaic.getUserId()));
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
    private List<Long> getFollowers(Long userId) {
        //todo rpcInvoke
        return Arrays.asList(new Long[]{100L, 200L, userId});
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
