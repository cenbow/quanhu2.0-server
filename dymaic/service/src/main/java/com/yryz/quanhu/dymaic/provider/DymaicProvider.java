package com.yryz.quanhu.dymaic.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.quanhu.dymaic.service.DymaicService;
import com.yryz.quanhu.dymaic.service.DymaicServiceImpl;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 04
 */
@Service
public class DymaicProvider implements DymaicService {

    Logger logger = LoggerFactory.getLogger(DymaicProvider.class);

    @Autowired
    DymaicServiceImpl dymaicService;

    @Override
    public Response<Boolean> send(Dymaic dymaic) {
        if (dymaic == null) {
            throw new QuanhuException(ExceptionEnum.PARAM_MISSING);
        }

        try {
            dymaic.setCreateDate(DateUtils.getDateTime());
            dymaic.setShelveFlag(DymaicServiceImpl.STATUS_ON);
            dymaic.setDelFlag(DymaicServiceImpl.STATUS_ON);
            return ResponseUtils.returnObjectSuccess(dymaicService.send(dymaic));
        } catch (Exception e) {
            logger.error("send", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> delete(Long userId, Long kid) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.delete(userId, kid));
        } catch (Exception e) {
            logger.error("delete", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> shelve(Long userId, Long kid, Boolean shelve) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.shelve(userId, kid, shelve));
        } catch (Exception e) {
            logger.error("shelve", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Dymaic> get(Long kid) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.get(kid));
        } catch (Exception e) {
            logger.error("get", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<Long, Dymaic>> get(List<Long> kids) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.get(kids));
        } catch (Exception e) {
            logger.error("get", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Map<Long, Dymaic>> getLastSend(Set<Long> userIds) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.getLastSend(userIds));
        } catch (Exception e) {
            logger.error("get", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<DymaicVo>> getSendList(Long sourceUserId, Long targetUserId, Long kid, Long limit) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.getSendList(sourceUserId, targetUserId, kid, limit));
        } catch (Exception e) {
            logger.error("getSendList", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> rebuildSendList(Long userId) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.rebuildSendList(userId));
        } catch (Exception e) {
            logger.error("rebuildSendList", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<List<DymaicVo>> getTimeLine(Long userId, Long kid, Long limit) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.getTimeLine(userId, kid, limit));
        } catch (Exception e) {
            logger.error("getTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<Boolean> pushTimeLine(Dymaic dymaic) {
        try {
            return ResponseUtils.returnObjectSuccess(dymaicService.pushTimeLine(dymaic));
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
            return ResponseUtils.returnObjectSuccess(dymaicService.rebuildTimeLine(userId, limit));
        } catch (Exception e) {
            logger.error("rebuildTimeLine", e);
            return ResponseUtils.returnException(e);
        }
    }
}
