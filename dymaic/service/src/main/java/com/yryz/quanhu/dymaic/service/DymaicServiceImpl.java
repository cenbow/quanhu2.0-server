package com.yryz.quanhu.dymaic.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.dymaic.dao.DymaicDao;
import com.yryz.quanhu.dymaic.dao.redis.DymaicCache;
import com.yryz.quanhu.dymaic.mq.DymaicSender;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
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
 * TODO
 * 对接动态写入
 * 对接粉丝、用户、统计系统
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
    public Response<Boolean> delete(String userId, Long kid) {
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
                    dymaic = new Dymaic();
                    dymaic.setKid(dymaic.getKid());
                    dymaic.setDelFlag(dymaic.getDelFlag());
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
                } else {
                    Dymaic tmp = new Dymaic();
                    tmp.setKid(dymaic.getKid());
                    tmp.setDelFlag(dymaic.getDelFlag());
                    writeCacheList.add(tmp);
                }
                result.put(dymaic.getKid(), dymaic);
            }

            if (writeCacheList != null) {
                //写入redis
                dymaicCache.addDynamic(writeCacheList);
            }
        }

        return result;
    }

    @Override
    public Response<List<DymaicVo>> getSendList(String userId, Long kid, Long limit) {
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
    public Response<Boolean> rebuildSendList(String userId) {
        List<Long> kids = dymaicDao.getSendListIds(userId);
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.addSendList(userId, new HashSet<>(kids));
        }

        return ResponseUtils.returnObjectSuccess(true);
    }


    @Override
    public Response<List<DymaicVo>> getTimeLine(String userId, Long kid, Long limit) {
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
        Long startTime = System.currentTimeMillis();
        String userId = dynamic.getUserId();
        Long kid = dynamic.getKid();

        //获取粉丝列表
        Set<String> followers = new HashSet<>();
        followers.add("debar");
        followers.add("xiepeng");
        followers.add(userId);

        //push
        for (String followerId : followers) {
            dymaicCache.addTimeLine(followerId, kid);
        }

        logger.info("[dymaic] push dymaicId " + dynamic.getKid() + ", followers " + followers.size() + ", take timeMillis " + (System.currentTimeMillis() - startTime));

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Boolean> shuffleTimeLine(String userId, String debarUserId) {
        //1, 检查custId的timeLine是否存在

        //2，查询debarCust的sendList
        Set<Long> kids = dymaicCache.rangeAllSendList(debarUserId);

        //3，批量删除custId的timeLine中的相关id
        if (kids != null && !kids.isEmpty()) {
            dymaicCache.removeTimeLine(userId, kids);
        }

        return ResponseUtils.returnObjectSuccess(true);
    }

    @Override
    public Response<Boolean> rebuildTimeLine(String userId, Long limit) {
        Long startTime = System.currentTimeMillis();
        //获取粉丝列表
        List<String> followers = new ArrayList<>();
        followers.add("xiepeng");
        followers.add("debar");
        followers.add(userId);

        //2，通过In查询，从mysql中取limit条好友动态
        int idsSize = 0;
        List<Long> kids = dymaicDao.getTimeLineIds(followers, limit);

        //3，批量写入TimeLine
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
        Map<String, Dymaic> custInfos = null;
        if (dymaicMap != null) {
            Set<String> userIds = new HashSet<>();
            for (Dymaic dymaic : dymaicMap.values()) {
                userIds.add(dymaic.getUserId());
            }
            if (!userIds.isEmpty()) {
                //todo调用用户系统
                custInfos = new HashMap<>();
            }
        }

        //3 统计数据
        Map<String, Dymaic> statistics = null;
        if (!kids.isEmpty()) {
            //todo调用统计系统
            statistics = new HashMap<>();
        }

        //4, 聚合信息
        if (dymaicMap != null) {
            for (Long kid : kids) {
                Dymaic dymaic = dymaicMap.get(kid);

                if (dymaic != null) {
                    DymaicVo vo = new DymaicVo();
                    BeanUtils.copyProperties(dymaic, vo);

                    if (custInfos != null && custInfos.containsKey(dymaic.getUserId())) {
                        vo.setUser(custInfos.get(dymaic.getUserId()));
                    } else {
                        vo.setUser(new Dymaic());
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

    public static class TimeLineMonitor {

        private Long startTime;
        private Long endTime;
        private String userId;
        private Integer followers;
        private Integer size;

        public void TimeMonitor() {
        }
    }
}
