package com.yryz.quanhu.coterie.member.event;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 私圈事件
 *
 * @author chengyunfei
 */
@Service
public class CoterieEventManager {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Reference
    private EventAPI eventAPI;

    @Resource
    private CoterieService coterieService;

    @Resource
    private CoterieMemberService coterieMemberService;

    /**
     * 创建私圈 事件
     *
     * @param
     */
    public void createCoterieEvent(Long coterieId) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            EventInfo event = new EventInfo();
            event.setCoterieId(coterie.getCoterieId().toString());
            event.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            event.setUserId(coterie.getOwnerId());
            event.setEventNum(1);
            event.setEventCode(EventEnum.CREATE_COTERIE.getCode());
            logger.info(GsonUtils.parseJson(event));
            eventAPI.commit(event);
        } catch (Exception e) {
            logger.error("event Exception", e);
        }
    }

    /**
     * 加入私圈 事件
     *
     * @param coterieId
     */
    public void joinCoterieEvent(Long userId,Long coterieId) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            EventInfo event = new EventInfo();
            event.setCoterieId(coterie.getCoterieId().toString());
            event.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            event.setUserId(String.valueOf(userId));
            event.setEventNum(1);
            event.setAmount(Double.valueOf(coterie.getJoinFee()));
            event.setOwnerId(coterie.getOwnerId());
            event.setEventCode(EventEnum.JOIN_COTERIE.getCode());
            eventAPI.commit(event);
        } catch (Exception e) {
            logger.error("event Exception", e);
        }
    }

    /**
     * 资源付费 事件
     *
     * @param userId
     * @param coterieId
     * @param resourceId
     */
    @Deprecated
    public void resourcePayEvent(Long userId, Long coterieId, Long resourceId) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            EventInfo event = new EventInfo();
            event.setCoterieId(coterie.getCoterieId().toString());
            event.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            event.setUserId(userId.toString());
            event.setEventNum(1);
            event.setOwnerId(coterie.getOwnerId());
            event.setResourceId(resourceId.toString());
            event.setEventCode(EventEnum.RESOURCE_PAY.getCode());
            eventAPI.commit(event);
        } catch (Exception e) {
            logger.error("event Exception", e);
        }
    }
}
