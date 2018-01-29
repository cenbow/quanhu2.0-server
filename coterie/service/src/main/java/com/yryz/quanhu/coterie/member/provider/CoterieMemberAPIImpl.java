package com.yryz.quanhu.coterie.member.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.service.CoterieMemberAPI;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.Objects;


@Service(interfaceClass = CoterieMemberAPI.class)
public class CoterieMemberAPIImpl implements CoterieMemberAPI {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private CoterieMemberService coterieMemberService;

    @Resource
    private CoterieService coterieService;

    @Reference
    private UserApi userApi;

    @Override
    public Response<CoterieMemberVoForJoin> join(Long userId, Long coterieId, String reason) {
        logger.debug("join params: userId(" + userId + "),coterieId(" + coterieId + ")reason(" + reason + ")");
        if (null == userId || null == coterieId) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        CoterieInfo info = coterieService.find(coterieId);
        if (info == null) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        try {
            CoterieMemberVoForJoin result = coterieMemberService.join(userId, coterieId, reason);
            return ResponseUtils.returnObjectSuccess(result);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }

    }

    @Override
    public Response<String> kick(Long userId, Long memberId, Long coterieId, String reason) {

        logger.debug("kick params: memberId(" + memberId + "),coterieId(" + coterieId + "),reason(" + reason + ")");

        //是否为圈主
        if (!isBoss(userId, coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NOT_HAVE_COTERIE);
        }

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        if (null == memberId || null == coterieId) {
            throw QuanhuException.busiError("");
        }

        try {
            coterieMemberService.kick(memberId, coterieId, reason);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        return ResponseUtils.returnSuccess();
    }

    @Override
    public Response<String> quit(Long userId, Long coterieId) {

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        //用户是否存在
        if (!isExistUser(userId)) {
            throw new QuanhuException(ExceptionEnum.USER_MISSING);
        }

        try {
            coterieMemberService.quit(userId, coterieId);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<String> banSpeak(Long userId, Long memberId, Long coterieId, Integer type) {

        //是否为圈主
        if (!isBoss(userId, coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NOT_HAVE_COTERIE);
        }

        //用户是否存在
        if (!isExistUser(userId)) {
            throw new QuanhuException(ExceptionEnum.USER_MISSING);
        }

        try {
            coterieMemberService.banSpeak(memberId, coterieId, type);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<Integer> permission(Long userId, Long coterieId) {

        //用户是否存在
        if (!isExistUser(userId)) {
            throw new QuanhuException(ExceptionEnum.USER_MISSING);
        }

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }
        try {
            Integer permission = coterieMemberService.permission(userId, coterieId);
            return ResponseUtils.returnObjectSuccess(permission);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }


    @Override
    public Response<Boolean> isBanSpeak(Long userId, Long coterieId) {

        //用户是否存在
        if (!isExistUser(userId)) {
            throw new QuanhuException(ExceptionEnum.USER_MISSING);
        }

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }
        try {
            Boolean flag = coterieMemberService.isBanSpeak(userId, coterieId);
            return ResponseUtils.returnObjectSuccess(flag);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }

    }

    @Override
    public Response<String> audit(Long userId, Long memberId, Long coterieId, Byte memberStatus) {

        logger.debug("audit params: memberId(" + memberId + "),coterieId(" + coterieId + "),type(" + memberStatus);

        //是否为圈主
        if (!isBoss(userId, coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NOT_HAVE_COTERIE);
        }

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        //用户是否存在
        if (!isExistUser(memberId)) {
            throw new QuanhuException(ExceptionEnum.USER_MISSING);
        }

        try {
            coterieMemberService.audit(memberId, coterieId, memberStatus, MemberConstant.JoinType.FREE.getStatus());

            return ResponseUtils.returnSuccess();

        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<Integer> queryNewMemberNum(Long coterieId) {

        logger.debug("queryNewMemberNum params: coterieId(" + coterieId + ")");

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        try {
            Integer count = coterieMemberService.queryNewMemberNum(coterieId);
            return ResponseUtils.returnObjectSuccess(count);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<PageList<CoterieMemberApplyVo>> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize) {

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        try {
            PageList<CoterieMemberApplyVo> applyList = coterieMemberService.queryMemberApplyList(coterieId, pageNum, pageSize);
            return ResponseUtils.returnObjectSuccess(applyList);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Response<PageList<CoterieMemberVo>> queryMemberList(Long coterieId, Integer pageNo, Integer pageSize) {

        logger.debug("queryMemberList params: coterieId(" + coterieId + ")pageNo(" + pageNo + ")pageSize(" + pageSize + ")");

        //圈子是否存在
        if (!isExistCoterie(coterieId)) {
            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
        }

        if (Objects.isNull(pageNo) || Objects.isNull(pageSize) || pageNo <= 0 || pageNo <= 0) {
            throw new QuanhuException(ExceptionEnum.PAGE_PARAM_ERROR);
        }

        try {
            PageList<CoterieMemberVo> memberList = coterieMemberService.queryMemberList(coterieId, pageNo, pageSize);
            return ResponseUtils.returnObjectSuccess(memberList);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    /**
     * 用户是否为圈主
     * @param userId
     * @param coterieId
     * @return
     */
    private Boolean isBoss(Long userId, Long coterieId) {
        Integer permission = coterieMemberService.permission(userId, coterieId);
        if (permission.intValue() == MemberConstant.Permission.OWNER.getStatus()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户是否存在
     * @param userId
     * @return
     */
    private Boolean isExistUser(Long userId) {
        Response<UserSimpleVO> response = userApi.getUserSimple(userId);
        if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
            if (response.getData() != null) {
                return true;
            } else {
                return false;
            }
        } else {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    /**
     * 私圈是否存在
     * @param coterieId
     * @return
     */
    private Boolean isExistCoterie(Long coterieId) {
        CoterieInfo coterie = coterieService.find(coterieId);
        if (coterie != null) {
            return true;
        } else {
            return false;
        }
    }
}
