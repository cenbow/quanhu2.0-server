package com.yryz.quanhu.coterie.member.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Sets;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.*;
import com.yryz.quanhu.user.service.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Service(interfaceClass = CoterieMemberAPI.class)
public class CoterieMemberAPIImpl implements CoterieMemberAPI {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CoterieMemberService coterieMemberService;

    @Resource
    private CoterieService coterieService;

	@Reference
	private UserApi userApi;

//	@Resource
//	private CoterieEventManager coterieEventManager;

    @Override
    public Response<CoterieMemberVoForJoin> join(Long userId, Long coterieId, String reason) {

        logger.info("join params: userId(" + userId + "),coterieId(" + coterieId + ")reason(" + reason + ")");
        if (null == userId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        CoterieInfo info = coterieService.find(coterieId);
        if (info == null) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        CoterieMemberVoForJoin join = null;

        try {
            join = coterieMemberService.join(userId, coterieId, reason);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        return new Response<>(join);
    }

    @Override
    public Response<String> kick(Long userId, Long memberId, Long coterieId, String reason) {

        //是否为圈主

        //todo



        logger.info("kick params: memberId(" + memberId + "),coterieId(" + coterieId + "),reason(" + reason + ")");
        if (null == memberId || null == coterieId) {
            throw QuanhuException.busiError("");
        }
        try {
            coterieMemberService.kick(memberId, coterieId, reason);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        return new Response<>();
    }

    @Override
    public Response<String> quit(Long custId, Long coterieId) {
        logger.info("quit params: custId(" + custId + ") coterieId(" + coterieId + ")");
        if (custId == null  || coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            coterieMemberService.quit(custId, coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        return new Response<>();
    }

    @Override
    public Response<String> banSpeak(Long userId, Long memberId, Long coterieId, Integer type) {
        logger.info("banSpeak params: memberId(" + memberId + "),coterieId(" + coterieId + ")");
        if (null == memberId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            coterieMemberService.banSpeak(memberId, coterieId, type);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        return new Response<>();
    }

    @Override
    public Response<Integer> permission(Long userId, Long coterieId) {
        Integer permission = coterieMemberService.permission(userId, coterieId);

        return new Response<Integer>(permission);
    }


    @Override
    public Response<Boolean> isBanSpeak(Long userId, Long coterieId) {
        Boolean flag = coterieMemberService.isBanSpeak(userId, coterieId);

        return new Response<>(flag);
    }

    @Override
    public Response<String> audit(Long userId, Long memberId, Long coterieId, Byte type) {

        //是否为圈主
        //todo

        logger.debug("audit params: memberId(" + memberId + "),coterieId(" + coterieId + "),type(" + type);

        if (null == userId || null == coterieId) {
            throw QuanhuException.busiError("");
        }
        try {
            coterieMemberService.audit(memberId, coterieId, type);
//            coterieEventManager.joinCoterieEvent(coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        return new Response();
    }

    @Override
    public Response<Integer> queryNewMemberNum(Long coterieId) {
        logger.info("queryNewMemberNum params: coterieId(" + coterieId + ")");
        if (coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            Integer count = coterieMemberService.queryNewMemberNum(coterieId);
            return new Response<>(count);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
//            throw new QuanhuException(ExceptionEnum.SysException);
        }
        return new Response<>();
    }

    @Override
    public Response<PageList<CoterieMemberApplyVo>> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize) {
        logger.info("queryMemberApplyList params: coterieId(" + coterieId + ")");
        if (coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            PageList<CoterieMemberApplyVo> applyList = coterieMemberService.queryMemberApplyList(coterieId, pageNum, pageSize);
            return new Response<>(applyList);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<PageList<CoterieMemberVo>> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize) {
        logger.info("queryMemberList params: coterieId(" + coterieId + ")pageNum(" + pageNum + ")pageSize(" + pageSize + ")");
        if (coterieId == null || pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie == null) {
                throw new QuanhuException(ExceptionEnum.SysException);
            }
            PageList<CoterieMemberVo> memberList = coterieMemberService.queryMemberList(coterieId, pageNum, pageSize);

            return new Response<>(memberList);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }
}
