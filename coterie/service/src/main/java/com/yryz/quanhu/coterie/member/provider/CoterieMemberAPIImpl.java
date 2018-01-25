package com.yryz.quanhu.coterie.member.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.google.common.collect.Sets;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForPermission;
import com.yryz.quanhu.user.service.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
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

//        CoterieInfo info = coterieService.find(coterieId.toString());
//        if (info == null) {
////            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
//            throw new QuanhuException(ExceptionEnum.SysException);
//        }
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
    public Response<String> kick(Long userId, Long coterieId, String reason) {
        logger.info("kick params: userId(" + userId + "),coterieId(" + coterieId + "),reason(" + reason + ")");
        if (null == userId || null == coterieId) {
            throw QuanhuException.busiError("");
        }
        try {
            coterieMemberService.kick(userId, coterieId, reason);
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
    public Response<String> banSpeak(Long userId, Long coterieId, Integer type) {
        logger.info("banSpeak params: userId(" + userId + "),coterieId(" + coterieId + ")");
        if (null == userId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            coterieMemberService.banSpeak(userId, coterieId, type);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
//            throw new QuanhuException(ExceptionEnum.SysException);
        }
        return new Response<>();
    }

    @Override
    public Response<CoterieMemberVoForPermission> permission(Long userId, Long coterieId) {
        return null;
    }


    /************* 0124 *******************************************/











    @Override
    public void agree(Long userId, Long coterieId) {
        logger.info("agree params: userId(" + userId + "),coterieId(" + coterieId + ")");
        if (null == userId || null == coterieId) {
            throw QuanhuException.busiError("");
        }
        try {
            coterieMemberService.agree(userId, coterieId);
//            coterieEventManager.joinCoterieEvent(coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public void disagree(Long userId, Long coterieId) {
        logger.info("disagree params: userId(" + userId + "),coterieId(" + coterieId + ")");
            if (null == userId || null == coterieId) {
            throw QuanhuException.busiError("");
        }
        try {
            coterieMemberService.disagree(userId, coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public List<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize) {
        logger.info("queryMemberList params: coterieId(" + coterieId + ")pageNum(" + pageNum + ")pageSize(" + pageSize + ")");
        if (coterieId == null || pageNum == null || pageSize == null || pageNum <= 0 || pageSize <= 0) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
//            CoterieInfo coterie = coterieService.find(coterieId);
//            if (coterie == null) {
//                throw new ServiceException(ServiceException.CODE_SYS_ERROR, "私圈（" + coterieId + "）不存在");
//                throw new QuanhuException(ExceptionEnum.SysException);
//            }
            List<CoterieMemberVo> memberList = coterieMemberService.queryMemberList(coterieId, pageNum, pageSize);

            Set<Long> set = Sets.newHashSet();
//            set.add(coterie.getOwnerId());
            for (int i = 0; i < memberList.size(); i++) {
                set.add(memberList.get(i).getUserId());
            }

//            Map<String, Long> custMap = userAPI.getCustInfo(set);
            for (int i = 0; i < memberList.size(); i++) {
                CoterieMemberVo info = memberList.get(i);
//                CustInfo cust = custMap.get(info.getCustId());
//                if (cust != null) {
//                    info.setCustIcon(cust.getCustImg());
//                    info.setCustName(cust.getCustNname());
//                }
            }
            if (pageNum == 1) {
//                CustInfo cust = custMap.get(coterie.getOwnerId());
//                if (cust == null) {
//                    throw new ServiceException(ServiceException.CODE_SYS_ERROR, "未获取到圈主信息");
//                }
                CoterieMemberVo qz = new CoterieMemberVo();
                qz.setCoterieId(coterieId);
//                qz.setCustIcon(cust.getCustImg());
//                qz.setCustId(cust.getCustId());
//                qz.setCustName(cust.getCustNname());
                memberList.add(0, qz);//添加圈主
            }
            return memberList;
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public List<CoterieMemberApplyVo> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize) {
        logger.info("queryMemberApplyList params: coterieId(" + coterieId + ")");
            if (coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            List<CoterieMemberApplyVo> applyList = coterieMemberService.queryMemberApplyList(coterieId, pageNum, pageSize);

            Set<String> set = Sets.newHashSet();
            for (int i = 0; i < applyList.size(); i++) {
//                set.add(applyList.get(i).getUsertId());
            }

//            Map<String, CustInfo> custMap = custAPI.getCustInfo(set);
//            for (int i = 0; i < applyList.size(); i++) {
//                MemberApplyInfo info = applyList.get(i);
//                CustInfo cust = custMap.get(info.getCustId());
//                if (cust != null) {
//                    info.setCustIcon(cust.getCustImg());
//                    info.setCustName(cust.getCustNname());
//                }
//            }
            return applyList;
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Integer queryNewMemberNum(Long coterieId) {
        logger.info("queryNewMemberNum params: coterieId(" + coterieId + ")");
            if (coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            return coterieMemberService.queryNewMemberNum(coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Integer queryMemberNum(Long coterieId) {
        logger.info("queryMemberNum params: coterieId(" + coterieId + ")");
        if (coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            return coterieMemberService.queryMemberNum(coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public CoterieMemberVo queryCoterieMemberInfo(Long custId, Long coterieId) {
        logger.info("quit params: custId(" + custId + ") coterieId(" + coterieId + ")");
        if (custId == null  || coterieId == null) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            return coterieMemberService.queryMemberInfo(custId, coterieId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public CoterieMemberApplyVo queryWaitingMemberApply(Long coterieId, Long userId) {
        logger.info("quit params: userId(" + userId + ") coterieId(" + coterieId + ")");
        if (null == userId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        try {
            return coterieMemberService.findWaitingMemberApply(coterieId, userId);
        } catch (Exception e) {
            logger.error("unKown Exception", e);
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

}
