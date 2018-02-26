package com.yryz.quanhu.coterie.member.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.JsonUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.dao.CoterieApplyDao;
import com.yryz.quanhu.coterie.member.dao.CoterieMemberDao;
import com.yryz.quanhu.coterie.member.dao.CoterieMemberRedis;
import com.yryz.quanhu.coterie.member.entity.CoterieMember;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberNotify;
import com.yryz.quanhu.coterie.member.event.CoterieEventManager;
import com.yryz.quanhu.coterie.member.event.CoterieMemberMessageManager;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;
import com.yryz.quanhu.order.sdk.OrderSDK;
import com.yryz.quanhu.order.sdk.constant.OrderEnum;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

/**
 * 私圈成员服务实现
 *
 * @author chengyunfei
 */
@Service
public class CoterieMemberServiceImpl implements CoterieMemberService {

    @Reference
    private IdAPI idApi;

    @Reference
    private UserApi userApi;

    @Reference
    private UserRelationApi userRelationApi;

    @Autowired
    private OrderSDK orderSDK;

    @Resource
    private CoterieMemberDao coterieMemberDao;

    @Resource
    private CoterieApplyDao coterieApplyDao;

    @Resource
    private CoterieService coterieService;

    @Resource
    private CoterieMemberMessageManager coterieMemberMessageManager;

    @Resource
    private CoterieEventManager coterieEventManager;

    @Autowired
    private CoterieMemberRedis coterieMemberRedis;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoterieMemberVoForJoin join(Long userId, Long coterieId, String reason) {

        CoterieMemberVoForJoin result = new CoterieMemberVoForJoin();

        CoterieInfo coterie = coterieService.find(coterieId);

        //私圈人数已满
        if (coterie.getMemberNum().intValue() >= 2000) {
            result.setStatus((byte) 40);
            return result;
        }

        //收费时,直接加入,返回orderId
        if (coterie.getJoinFee() > 0) {
            CoterieMemberNotify coterieMemberNotify = new CoterieMemberNotify();

            coterieMemberNotify.setUserId(userId);
            coterieMemberNotify.setCoterieId(coterieId);
            coterieMemberNotify.setReason(reason);
            coterieMemberNotify.setCoterieName(coterie.getName());
            coterieMemberNotify.setOwner(coterie.getOwnerId());
            coterieMemberNotify.setAmount(coterie.getJoinFee().longValue());
            coterieMemberNotify.setIcon(coterie.getIcon());

            String extJson = JsonUtils.toFastJson(coterieMemberNotify);

            InputOrder inputOrder = new InputOrder();
            inputOrder.setCreateUserId(userId);
            inputOrder.setCost(coterie.getJoinFee().longValue());
            inputOrder.setCoterieId(coterieId);
            inputOrder.setFromId(userId);
            inputOrder.setModuleEnum(ModuleContants.COTERIE);
            inputOrder.setOrderEnum(OrderEnum.JOIN_COTERIE_ORDER);
            inputOrder.setToId(Long.parseLong(coterie.getOwnerId()));
            inputOrder.setResourceId(coterieId);
            inputOrder.setBizContent(extJson);

            Long orderId = orderSDK.createOrder(inputOrder);

            result.setStatus((byte) 10);
            result.setOrderId(orderId);
            return result;
        }

        //免费不审核,直接加入
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 10) {

            //先入审请表
            //用户是否为待审核或审核通过
            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (null == memberApply) {
                //insert member apply
                saveOrUpdateApply(userId, coterieId, reason, MemberConstant.MemberStatus.PASS.getStatus());
            } else {
                throw QuanhuException.busiError("用户已申请加入私圈!");
            }

            //如果没有拉黑则自动关注圈主
            Response<UserRelationDto> response = userRelationApi.setRelation(userId.toString(), coterie.getOwnerId(), UserRelationConstant.EVENT.SET_FOLLOW);
            if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
                //再入成员表
                saveOrUpdateMember(userId, coterieId, reason, MemberConstant.JoinType.FREE.getStatus());
            } else {

                throw QuanhuException.busiError("添加关注关系异常");
            }

            //permission cache
            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.MEMBER.getStatus());

            result.setStatus((byte) 20);
            return result;
        }

        //免费要审核
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 11) {
            //用户是否为待审核或审核通过
            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (null == memberApply) {
                //insert member apply
                saveOrUpdateApply(userId, coterieId, reason, MemberConstant.MemberStatus.WAIT.getStatus());

                //msg
                coterieMemberMessageManager.joinMessage(userId, coterieId, reason);

                //设置红点
                CoterieInfo coterieInfo = new CoterieInfo();
                coterieInfo.setCoterieId(coterieId);
                coterieInfo.setRedDot(11);
                coterieService.modify(coterieInfo);

            } else {

                throw QuanhuException.busiError("用户已是待审核或审核通过");
            }

            //permission cache
            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus());

            result.setStatus((byte) 30);
            return result;
        }

        throw QuanhuException.busiError("传参有误，请检查私圈是否收费是否需要审核");
    }

    @Override
    @Transactional
    public void kick(Long userId, Long coterieId, String reason) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie != null) {
                //update member kickStatus
                CoterieMember coterieMember = new CoterieMember();
                coterieMember.setUserId(userId);
                coterieMember.setCoterieId(coterieId);
                coterieMember.setReason(reason);
                coterieMember.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                coterieMember.setKickStatus(MemberConstant.KickStatus.KICKED.getStatus());
                int resultMember = coterieMemberDao.updateByCoterieMember(coterieMember);

                //update apply delFlag
                CoterieMemberApply apply = new CoterieMemberApply();
                apply.setUserId(userId);
                apply.setCoterieId(coterieId);
                apply.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                int resultApply = coterieApplyDao.updateByCoterieApply(apply);

                if (resultMember > 0 && resultApply > 0) {
                    //更新私圈成员数
                    coterieService.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                }
            }
            //todo msg
            coterieMemberMessageManager.kickMessage(userId, coterieId, reason);

            //permission cache
            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_NON_CHECK.getStatus());
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    @Transactional
    public void quit(Long userId, Long coterieId) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie != null) {
                //update delFlag
                CoterieMember coterieMember = new CoterieMember();
                coterieMember.setUserId(userId);
                coterieMember.setCoterieId(coterieId);
                coterieMember.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                int resultMember = coterieMemberDao.updateByCoterieMember(coterieMember);

                //update delFlag
                CoterieMemberApply apply = new CoterieMemberApply();
                apply.setUserId(userId);
                apply.setCoterieId(coterieId);
                apply.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                int resultApply = coterieApplyDao.updateByCoterieApply(apply);
                if (resultMember > 0 && resultApply > 0) {
                    //更新私圈成员数
                    coterieService.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                }

                //permission cache
                coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_NON_CHECK.getStatus());
            }
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public void banSpeak(Long userId, Long coterieId, Integer type) {
        try {
            CoterieMember record = new CoterieMember();
            record.setUserId(userId);
            record.setCoterieId(coterieId);
            record.setLastUpdateDate(new Date());
            if (type == 10) {
                record.setBanSpeak(MemberConstant.BanSpeak.BANSPEAK.getStatus());
            } else {
                record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            }

            coterieMemberDao.updateByCoterieMember(record);

            if (type == 10) {
                coterieMemberMessageManager.banSpeakMessage(userId, coterieId);
            }
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Integer permission(Long userId, Long coterieId) {

        try {

            if (userId == null) {
                return MemberConstant.Permission.STRANGER_NON_CHECK.getStatus();
            }

            Integer permission = coterieMemberRedis.getPermission(coterieId, userId);
            if (null == permission) {
                permission = getPermissionByDb(coterieId, userId);
                coterieMemberRedis.savePermission(coterieId, userId, permission);
            }
            return permission;

        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Boolean isBanSpeak(Long userId, Long coterieId) {

        if (permission(userId, coterieId) == MemberConstant.Permission.OWNER.getStatus()) {
            return false;
        }

        CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);

        if (null == member) {
            return true;
        }

        if (member.getDelFlag().equals(MemberConstant.DelFlag.NORMAL.getStatus()) && member.getBanSpeak() == MemberConstant.BanSpeak.BANSPEAK.getStatus()) {
            return true;
        }
        return false;
    }

    @Override
    public Integer queryNewMemberNum(Long coterieId) {
        try {
            return coterieApplyDao.selectNewMemberNum(coterieId);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long userId, Long coterieId, Byte memberStatus, Byte joinType) {

        try {

            CoterieInfo coterie = coterieService.find(coterieId);

//            coterieService

            //私圈人数已满
            if (coterie.getMemberNum().intValue() + 1 >= 2000) {
                throw QuanhuException.busiError("私圈人数已达到上限");
            }

            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);

            String reason = "";

            if (null != memberApply) {
                reason = memberApply.getReason();
            }

            if (memberStatus == MemberConstant.MemberStatus.PASS.getStatus()) {
                saveOrUpdateApply(userId, coterieId, "", MemberConstant.MemberStatus.PASS.getStatus());

                //如果没有拉黑则自动关注圈主
                //todo
                Response<UserRelationDto> response = userRelationApi.setRelation(userId.toString(), coterie.getOwnerId(), UserRelationConstant.EVENT.SET_FOLLOW);
                if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
                    saveOrUpdateMember(userId, coterieId, reason, joinType);
                }

                //todo event
                coterieEventManager.joinCoterieEvent(coterieId);

                //permission cache
                coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.MEMBER.getStatus());
            } else {
                saveOrUpdateApply(userId, coterieId, "", memberStatus);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public PageList<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNo, Integer pageSize) {
        List<CoterieMember> list = Lists.newArrayList();

        try {
            int start = (pageNo.intValue() - 1) * pageSize.intValue();
            list = coterieMemberDao.selectPageByCoterieId(coterieId, start, pageSize);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }


        List<CoterieMemberVo> memberVos = Lists.newArrayList();

        for (CoterieMember member : list) {

            CoterieMemberVo vo = new CoterieMemberVo();
            vo.setAmount(member.getAmount());
            vo.setBanSpeak(member.getBanSpeak());
            vo.setCoterieId(member.getCoterieId());
            vo.setCreateDate(member.getCreateDate());
            vo.setJoinType(member.getJoinType());
            vo.setLastUpdateDate(member.getLastUpdateDate());

            Response<UserSimpleVO> response = userApi.getUserSimple(member.getUserId());
            if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
                UserSimpleVO user = response.getData();
                if (user == null) {
                    throw new QuanhuException(ExceptionEnum.SysException);
                }
                vo.setUser(user);
                memberVos.add(vo);
            }
        }

        if (pageNo == 1) {
            CoterieMemberVo qz = new CoterieMemberVo();
            qz.setCoterieId(coterieId);

            CoterieInfo coterie = coterieService.find(coterieId);

            Response<UserSimpleVO> response = userApi.getUserSimple(Long.parseLong(coterie.getOwnerId()));
            if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
                UserSimpleVO user = response.getData();
                if (user == null) {
                    throw new QuanhuException(ExceptionEnum.SysException);
                }
                qz.setUser(user);
            }

            memberVos.add(0, qz);//添加圈主
        }


        PageList<CoterieMemberVo> pageList = new PageList<>(pageNo, pageSize, memberVos);

        return pageList;

    }

    @Override
    public PageList<CoterieMemberApplyVo> queryMemberApplyList(Long coterieId, Integer pageNo, Integer pageSize) {
        List<CoterieMemberApply> list = Lists.newArrayList();
        try {
            int start = (pageNo - 1) * pageSize;
            list = coterieApplyDao.selectPageByCoterieId(coterieId, start, pageSize);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QuanhuException(ExceptionEnum.SysException);
        }

        List<CoterieMemberApplyVo> applyVos = Lists.newArrayList();

        Set<String> ids = new HashSet<>();

        for (CoterieMemberApply apply : list) {
            ids.add(apply.getUserId().toString());
        }

        Map<String, UserSimpleVO> users = new HashMap<>();
        Response<Map<String, UserSimpleVO>> response = userApi.getUserSimple(ids);
        if (response.getCode().equals(ResponseConstant.SUCCESS.getCode())) {
            users = response.getData();
            if (users != null && users.values().isEmpty()) {
                throw new QuanhuException(ExceptionEnum.SysException);
            }
        }

        for (CoterieMemberApply apply : list) {

            CoterieMemberApplyVo vo = new CoterieMemberApplyVo();
            vo.setCoterieId(apply.getCoterieId());
            vo.setCreateDate(apply.getCreateDate());
            vo.setReason(apply.getReason());
            vo.setMemberStatus(apply.getMemberStatus());

            UserSimpleVO user = users.get(apply.getUserId().toString());
            vo.setUser(user);
            applyVos.add(vo);

        }

        //取消红点
        CoterieInfo coterie = new CoterieInfo();
        coterie.setCoterieId(coterieId);
        coterie.setRedDot(10);
        coterieService.modify(coterie);

        PageList<CoterieMemberApplyVo> pageList = new PageList<>(pageNo, pageSize, applyVos);

        return pageList;
    }

    /******************************************/

    private Integer saveOrUpdateApply(Long userId, Long coterieId, String reason, Byte status) {

        try {

            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);

            if (null == memberApply) {

                Long kid = idApi.getSnowflakeId().getData();

                memberApply = new CoterieMemberApply();

                memberApply.setKid(kid);
                memberApply.setCoterieId(coterieId);
                memberApply.setCreateDate(new Date());
                memberApply.setUserId(userId);
                memberApply.setLastUpdateDate(new Date());
                if (StringUtils.isBlank(reason)) {
                    memberApply.setReason("");
                } else {
                    memberApply.setReason(reason);
                }
                memberApply.setCreateUserId(userId);
                memberApply.setMemberStatus(status);

                return coterieApplyDao.insert(memberApply);
            } else {
                memberApply.setMemberStatus(status);
                memberApply.setProcessTime(new Date());
                return coterieApplyDao.updateByCoterieApply(memberApply);
            }

        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }


    private Boolean saveOrUpdateMember(Long userId, Long coterieId, String reason, Byte joinType) {
        try {

            Integer result = null;

            CoterieInfo coterie = coterieService.find(coterieId);

            Long amount = 0L;
            if (joinType.equals(MemberConstant.JoinType.NOTFREE.getStatus())) {
                amount = coterie.getJoinFee().longValue();
            }

            CoterieMember coterieMember = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (coterieMember == null) {

                coterieMember = new CoterieMember();

                coterieMember.setKid(idApi.getSnowflakeId().getData());
                coterieMember.setUserId(userId);
                coterieMember.setCoterieId(coterieId);
                if (StringUtils.isBlank(reason)) {
                    coterieMember.setReason("");
                } else {
                    coterieMember.setReason(reason);
                }
                coterieMember.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
                coterieMember.setCreateDate(new Date());
                coterieMember.setLastUpdateDate(new Date());
                coterieMember.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
                coterieMember.setProcessTime(new Date());
                coterieMember.setJoinType(joinType);
                coterieMember.setKickStatus(MemberConstant.KickStatus.NORMAL.getStatus());
                coterieMember.setDelFlag(MemberConstant.DelFlag.NORMAL.getStatus());
                coterieMember.setAmount(Long.valueOf(amount));
                coterieMember.setCreateUserId(userId);

                result = coterieMemberDao.insert(coterieMember);
            } else {
                coterieMember.setReason(reason);
                coterieMember.setCreateDate(new Date());
                coterieMember.setLastUpdateDate(new Date());
                coterieMember.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
                coterieMember.setProcessTime(new Date());
                coterieMember.setJoinType(joinType);
                coterieMember.setKickStatus(MemberConstant.KickStatus.NORMAL.getStatus());
                coterieMember.setDelFlag(MemberConstant.DelFlag.NORMAL.getStatus());
                coterieMember.setAmount(Long.valueOf(amount));
                coterieMember.setCreateUserId(userId);

                result = coterieMemberDao.updateByCoterieMember(coterieMember);
            }

            Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() + 1, coterie.getMemberNum());
            if (result == 0 || updateNumberResult == 0) {
                throw QuanhuException.busiError("更新成员人数失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw QuanhuException.busiError("保存或更新私圈成员异常");
        }
        return true;
    }

    private Integer getPermissionByDb(Long coterieId, Long userId) {

        CoterieInfo coterie = coterieService.find(coterieId);
        Long ownerId = Long.parseLong(coterie.getOwnerId());

        //是否为圈主
        if (userId.longValue() == ownerId.longValue()) {
            return MemberConstant.Permission.OWNER.getStatus();
        }

        //是否为成员
        CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
        if (null != member && member.getDelFlag().equals(MemberConstant.DelFlag.NORMAL.getStatus())) {
            return MemberConstant.Permission.MEMBER.getStatus();
        }

        //路人
        CoterieMemberApply apply = coterieApplyDao.selectWaitingByCoterieIdAndUserId(coterieId, userId);

        if (null != apply) {
            return MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus();
        } else {
            return MemberConstant.Permission.STRANGER_NON_CHECK.getStatus();
        }
    }

    /**
     * 更新redis缓存
     */
    private void updateMemberModelCache(Long coterieId, Long userId) {
        CoterieMember model = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
        if (model != null) {
            coterieMemberRedis.saveCoterieMember(model);
        }
    }
}
