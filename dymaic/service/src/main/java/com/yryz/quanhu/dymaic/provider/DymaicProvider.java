package com.yryz.quanhu.dymaic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.dymaic.dto.QueryDymaicDTO;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.service.DymaicServiceImpl;
import com.yryz.quanhu.dymaic.service.DymaicTopServiceImpl;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.util.*;

/**
 * @desc 动态服务
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 04
 */
@Service
public class DymaicProvider implements DymaicService {

    Logger logger = LoggerFactory.getLogger(DymaicProvider.class);

    @Autowired
    DymaicServiceImpl dymaicService;

    @Autowired
    DymaicTopServiceImpl dymaicTopService;

    @Override
    public Response<Boolean> send(Dymaic dymaic) {

        try {
            Assert.notNull(dymaic, "dymaic can not be null");

            dymaic.setCreateDate(new Date());
            dymaic.setShelveFlag(DymaicServiceImpl.STATUS_ON);
            dymaic.setDelFlag(DymaicServiceImpl.STATUS_ON);
            return ResponseUtils.returnObjectSuccess(dymaicService.send(dymaic));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("send", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> delete(Long userId, Long kid) {
        try {
            Assert.notNull(userId, "userId can not be null");
            Assert.notNull(kid, "kid can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.delete(userId, kid));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("delete", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> shelve(Long userId, Long kid, Boolean shelve) {
        try {
            Assert.notNull(userId, "userId can not be null");
            Assert.notNull(kid, "kid can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.shelve(userId, kid, shelve));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("shelve", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Dymaic> get(Long kid) {
        try {
            Assert.notNull(kid, "kid can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.get(kid));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("get", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<Long, Dymaic>> get(List<Long> kids) {
        try {
            Assert.notNull(kids, "kid can not be null");
            if (kids.isEmpty()) {
                return ResponseUtils.returnObjectSuccess(new HashMap<>());
            }

            return ResponseUtils.returnObjectSuccess(dymaicService.get(kids));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("get", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> addTopDymaic(Long userId, Long dymaicId) {
        try {
            Assert.notNull(userId, "userId can not be null");
            Assert.notNull(dymaicId, "dymaicId can not be null");

            Dymaic dymaic = dymaicService.get(dymaicId);
            if (dymaic == null) {
                return ResponseUtils.returnException(new QuanhuException(ExceptionEnum.RESOURCE_NO_EXIST));
            }
            if (userId == null || !userId.equals(dymaic.getUserId())) {
                return ResponseUtils.returnException(new QuanhuException(ExceptionEnum.RESOURCE_NO_EXIST));
            }

            return ResponseUtils.returnObjectSuccess(dymaicTopService.add(userId, dymaicId));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("addTopDymaic", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<DymaicVo> getTopDymaic(Long userId){
        try {
            Assert.notNull(userId, "userId can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicTopService.get(userId));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("getTopDymaic", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> deleteTopDymaic(Long userId, Long dymaicId){
        try {
            Assert.notNull(userId, "userId can not be null");
            Assert.notNull(dymaicId, "dymaicId can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicTopService.delete(userId, dymaicId));
        } catch (Exception e) {
            logger.error("deleteTopDymaic", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<Long, Dymaic>> getLastSend(Set<Long> userIds) {
        try {
            Assert.notNull(userIds, "userIds can not be null");
            if (userIds.isEmpty()) {
                return ResponseUtils.returnObjectSuccess(new HashMap<>());
            }

            return ResponseUtils.returnObjectSuccess(dymaicService.getLastSend(userIds));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("getLastSend", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<DymaicVo>> getSendList(Long sourceUserId, Long targetUserId, Long kid, Long limit) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.getSendList(sourceUserId, targetUserId, kid, limit));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("getSendList", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> rebuildSendList(Long userId) {
        try {
            Assert.notNull(userId, "userIds can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.rebuildSendList(userId));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("rebuildSendList", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<DymaicVo>> getTimeLine(Long userId, Long kid, Long limit) {
        try {
            Assert.notNull(userId, "userIds can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.getTimeLine(userId, kid, limit));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("getTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> pushTimeLine(Dymaic dymaic) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.pushTimeLine(dymaic));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("pushTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> shuffleTimeLine(Long userId, Long debarUserId) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.shuffleTimeLine(userId, debarUserId));
        } catch (Exception e) {
            logger.error("shuffleTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> rebuildTimeLine(Long userId, Long limit) {
        try {
            Assert.notNull(userId, "userIds can not be null");

            return ResponseUtils.returnObjectSuccess(dymaicService.rebuildTimeLine(userId, limit));
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("rebuildTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<PageList<DymaicVo>> queryAllDymaic(QueryDymaicDTO queryDymaicDTO) {
        try {
            PageList<DymaicVo> pageList = dymaicService.queryAll(queryDymaicDTO);

            return ResponseUtils.returnObjectSuccess(pageList);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("listAllDymaic", e);
            return ResponseUtils.returnException(e);
        }
    }
}
