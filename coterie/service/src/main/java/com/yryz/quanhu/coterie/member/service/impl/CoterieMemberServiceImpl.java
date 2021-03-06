package com.yryz.quanhu.coterie.member.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.message.MessageConstant;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.utils.GsonUtils;
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
import com.yryz.quanhu.order.sdk.dto.ExecuteOrderResult;
import com.yryz.quanhu.order.sdk.dto.InputOrder;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserRelationConstant;
import com.yryz.quanhu.user.dto.UserRelationDto;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import javax.annotation.Resource;
import java.util.*;

/**
 * 私圈成员服务实现
 *
 * @author chengyunfei
 */
@Service
public class CoterieMemberServiceImpl implements CoterieMemberService {

    private Logger logger = LoggerFactory.getLogger(getClass());

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

        logger.info("join coterie : " + JsonUtils.toFastJson(coterie));
        //私圈人数已满
        if (coterie.getMemberNum().intValue() + 1 >= 2000) {
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

            logger.info("付费加入私圈自动审核ing");

            //重新组装流程， 不能直接调用audit,有事务问题， 成员与申请， 私圈成员数， 缓存可能都要回滚

            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (null == memberApply) {
                //insert member apply
                logger.info("save or update apply");
                saveOrUpdateApply(userId, coterieId, "", MemberConstant.MemberStatus.PASS.getStatus());
            } else {
                throw QuanhuException.busiError("用户已申请加入私圈!");
            }

            logger.info("审核通过时, 自动关注圈主完成后再更新或插入成员数据");
            saveOrUpdateMember(userId, coterieId, reason, MemberConstant.JoinType.NOTFREE.getStatus());
            logger.info("审核通过时, 更新或插入成员数据完成");

//            this.audit(userId, coterieId, MemberConstant.MemberStatus.PASS.getStatus(), MemberConstant.JoinType.NOTFREE.getStatus());
            logger.info("付费加入私圈自动审核end");

            logger.info("创建订单参数： " + coterie.getOwnerId() + ", join fee : " + coterie.getJoinFee().longValue());
            ExecuteOrderResult orderResult = orderSDK.executeOrderWithResult(OrderEnum.JOIN_COTERIE_ORDER, userId, Long.parseLong(coterie.getOwnerId()), coterie.getJoinFee().longValue());

            if ("200".equals(orderResult.getCode())) {
                logger.info("付费加入成功; userId : " + userId + ", coterieId : " + coterieId);

                logger.info("update member number : coterieId" + coterie.getCoterieId() + ", new memberNum : " + (coterie.getMemberNum() + 1) + ", old memberNum : " + coterie.getMemberNum());
                Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), (coterie.getMemberNum() + 1), coterie.getMemberNum());
                logger.info("update member number result : " + updateNumberResult);
                if (updateNumberResult == 0) {
                    logger.info("update member number result exception");
                }

                //permission cache
                logger.info("审核通过时, 更新成员权限缓存ing");
//            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.MEMBER.getStatus());
                coterieMemberRedis.deletePermission(coterieId, userId);
                logger.info("审核通过时, 更新成员权限缓存end");

                logger.info("审核通过时, 发送加入私圈事件ing");
                coterieEventManager.joinCoterieEvent(userId, coterieId);
                logger.info("审核通过时, 发送加入私圈事件end");

                //如果没有拉黑则自动关注圈主
                logger.info("审核通过时, 自动关注圈主");
                Response<UserRelationDto> response = userRelationApi.setRelation(userId.toString(), coterie.getOwnerId(), UserRelationConstant.EVENT.SET_FOLLOW);

                if (response.getCode() != ResponseConstant.SUCCESS.getCode()) {
                    logger.info(response.getErrorMsg());
                }

                /**
                 * 付费加入私圈发送消息
                 */
                logger.info("付费加入私圈的推送信息开始");
                coterieMemberMessageManager.sendMessageToJoinCoteriePayed(coterieMemberNotify, MessageConstant.JOIN_COTERIE_PAYED);
                logger.info("付费加入私圈的推送信息完成");

                /**
                 * 私圈奖励
                 */
                logger.info("私圈圈主奖励的推送信息开始");
                coterieMemberMessageManager.sendMessageToJoinCoterieReward(coterieMemberNotify, MessageConstant.JOIN_COTERIE_REWARD);
                logger.info("私圈圈主奖励的推送信息完成");

            } else if ("1000".equals(orderResult.getCode())) {
                logger.info("您的余额不足; userId : " + userId + ", coterieId : " + coterieId);
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw QuanhuException.busiError(ExceptionEnum.USER_NOT_SUFFICIENT_FUNDS);

            } else if ("100".equals(orderResult.getCode())) {
                logger.info("支付失败,请重试; userId : " + userId + ", coterieId : " + coterieId);
//                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                throw QuanhuException.busiError("支付失败,请重试");
            }

            result.setStatus((byte) 10);
            return result;
        }

        //免费不审核,直接加入
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 10) {

            //先入审请表
            //用户是否为待审核或审核通过
            CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (null == memberApply) {
                //insert member apply
                logger.info("save or update apply");
                saveOrUpdateApply(userId, coterieId, reason, MemberConstant.MemberStatus.PASS.getStatus());
            } else {
                throw QuanhuException.busiError("用户已申请加入私圈!");
            }

            //再入成员表
            logger.info("save or update member");
            saveOrUpdateMember(userId, coterieId, reason, MemberConstant.JoinType.FREE.getStatus());

            //如果没有拉黑则自动关注圈主
            Response<UserRelationDto> response = userRelationApi.setRelation(userId.toString(), coterie.getOwnerId(), UserRelationConstant.EVENT.SET_FOLLOW);

            if (response.getCode() != ResponseConstant.SUCCESS.getCode()) {
                logger.info(response.getErrorMsg());
            }

            logger.info("update member number : coterieId" + coterie.getCoterieId() + ", new memberNum : " + (coterie.getMemberNum() + 1) + ", old memberNum : " + coterie.getMemberNum());
            Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), (coterie.getMemberNum() + 1), coterie.getMemberNum());
            logger.info("update member number result : " + updateNumberResult);
            if (updateNumberResult == 0) {
                logger.info("update member number result exception");
            }

            //permission cache
            logger.info("update redis");
//            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.MEMBER.getStatus());
            coterieMemberRedis.deletePermission(coterieId, userId);
            logger.info("update redis end ");

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
                logger.info("join message ");
                coterieMemberMessageManager.joinMessage(userId, coterieId, reason);

                //设置红点
                CoterieInfo coterieInfo = new CoterieInfo();
                coterieInfo.setCoterieId(coterieId);
                coterieInfo.setRedDot(11);
                coterieService.modify(coterieInfo);
                logger.info("set red dot ");

            } else {

                throw QuanhuException.busiError("用户已是待审核或审核通过");
            }

            //permission cache
            logger.info("update redis");
//            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus());
            coterieMemberRedis.deletePermission(coterieId, userId);
            logger.info("update redis end");

            result.setStatus((byte) 30);
            return result;
        }

        throw QuanhuException.busiError("传参有误，请检查私圈是否收费是否需要审核");
    }

    @Override
    @Transactional
    public void kick(Long userId, Long coterieId, String reason) {
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

            //permission cache
            logger.info("kick更新权限缓存ing");
//            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_NON_CHECK.getStatus());
            coterieMemberRedis.deletePermission(coterieId, userId);
            logger.info("kick更新权限缓存end");

            //更新私圈成员数
            logger.info("update member num");
            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie != null) {
                coterieService.updateMemberNum(coterie.getCoterieId(), (coterie.getMemberNum() - 1), coterie.getMemberNum());
            }

            logger.info("kick message ");
            coterieMemberMessageManager.kickMessage(userId, coterieId, reason);
        }

    }

    @Override
    @Transactional
    public void quit(Long userId, Long coterieId) {

        logger.info("quit userId : " + userId + ", coterieId : " + coterieId);

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
            //permission cache
            logger.info("更新权限缓存ing");
            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.STRANGER_NON_CHECK.getStatus());
//            coterieMemberRedis.deletePermission(coterieId, userId);
            logger.info("更新权限缓存end");
            //更新私圈成员数

            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie != null) {
                logger.info("quit更新私圈成员数ing coterie info : " + JsonUtils.toFastJson(coterie));
                coterieService.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                logger.info("quit更新私圈成员数end");
            } else {
                logger.info("quit更新私圈成员数 coterie info is null");
            }

        }
    }

    @Override
    public void banSpeak(Long userId, Long coterieId, Integer type) {
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
    }

    @Override
    public Integer permission(Long userId, Long coterieId) {

        logger.info("permission userId : " + userId + ", coterieId : " + coterieId);

        if (userId == null) {
            logger.info("user is null permission 30");
            return MemberConstant.Permission.STRANGER_NON_CHECK.getStatus();
        }

        Integer permission = coterieMemberRedis.getPermission(coterieId, userId);
        if (null == permission) {
            logger.info("permission redis is null");
            permission = getPermissionByDb(coterieId, userId);
            logger.info("permission db is : " + permission);
            coterieMemberRedis.savePermission(coterieId, userId, permission);
        }
        logger.info("permission is : " + permission);
        return permission;
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
        return coterieApplyDao.selectNewMemberNum(coterieId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void audit(Long userId, Long coterieId, Byte memberStatus, Byte joinType) {

        logger.info("私圈审核ing");

        CoterieInfo coterie = coterieService.find(coterieId);

        logger.info("检查私圈人数是否已满");
        if (coterie.getMemberNum().intValue() + 1 >= 2000) {

            logger.info("私圈人数已达到上限");
            String msg = "私圈人数已达到上限";
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), msg, msg);
        }

        CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);

        String reason = "";

        if (null != memberApply) {
            reason = memberApply.getReason();
            logger.info("加入reason:" + reason);
        }

        if (memberStatus == MemberConstant.MemberStatus.PASS.getStatus()) {
            logger.info("审核通过时, 更新或插入申请信息");
            saveOrUpdateApply(userId, coterieId, "", MemberConstant.MemberStatus.PASS.getStatus());

            //如果没有拉黑则自动关注圈主
            logger.info("审核通过时, 自动关注圈主");
            userRelationApi.setRelation(userId.toString(), coterie.getOwnerId(), UserRelationConstant.EVENT.SET_FOLLOW);

            logger.info("审核通过时, 自动关注圈主完成后再更新或插入成员数据");
            saveOrUpdateMember(userId, coterieId, reason, joinType);
            logger.info("审核通过时, 更新或插入成员数据完成");

            logger.info("update member number : coterieId" + coterie.getCoterieId() + ", new memberNum : " + (coterie.getMemberNum() + 1) + ", old memberNum : " + coterie.getMemberNum());
            Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), (coterie.getMemberNum() + 1), coterie.getMemberNum());
            logger.info("update member number result : " + updateNumberResult);
            if (updateNumberResult == 0) {
                logger.info("update member number result exception");
            }

            //permission cache
            logger.info("审核通过时, 更新成员权限缓存ing");
//            coterieMemberRedis.savePermission(coterieId, userId, MemberConstant.Permission.MEMBER.getStatus());
            coterieMemberRedis.deletePermission(coterieId, userId);
            logger.info("审核通过时, 更新成员权限缓存end");

            logger.info("审核通过时, 发送加入私圈事件ing");
            coterieEventManager.joinCoterieEvent(userId, coterieId);
            logger.info("审核通过时, 发送加入私圈事件end");
        } else {
            logger.info("审核不通过时, 更新申请加入数据(预留)ing");
            saveOrUpdateApply(userId, coterieId, "", memberStatus);
            logger.info("审核不通过时, 更新申请加入数据(预留)end");
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
    }


    private Boolean saveOrUpdateMember(Long userId, Long coterieId, String reason, Byte joinType) {

        logger.info("save or update member");
        Integer result = null;

        CoterieInfo coterie = coterieService.find(coterieId);

        Long amount = 0L;
        if (joinType.equals(MemberConstant.JoinType.NOTFREE.getStatus())) {
            amount = coterie.getJoinFee().longValue();
        }

        logger.info("get member : coterieId : " + coterieId + ", userId : " + userId);
        CoterieMember coterieMember = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);

        if (coterieMember == null) {

            logger.info("coterie member is null ");
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

            logger.info("coterie member insert : " + GsonUtils.parseJson(coterieMember));
            result = coterieMemberDao.insert(coterieMember);
        } else {
            coterieMember.setReason(reason);
            coterieMember.setCreateDate(new Date());
            coterieMember.setLastUpdateDate(new Date());
            coterieMember.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
            coterieMember.setProcessTime(new Date());
            coterieMember.setJoinType(joinType);
            coterieMember.setKickStatus(MemberConstant.KickStatus.NORMAL.getStatus());
            coterieMember.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            coterieMember.setDelFlag(MemberConstant.DelFlag.NORMAL.getStatus());
            coterieMember.setAmount(Long.valueOf(amount));
            coterieMember.setCreateUserId(userId);

            logger.info("coterie member update : " + GsonUtils.parseJson(coterieMember));
            result = coterieMemberDao.updateByCoterieMember(coterieMember);
            logger.info("result : " + result);
        }

//        logger.info("update member number : coterieId" + coterie.getCoterieId() + ", new memberNum : " + (coterie.getMemberNum() + 1) + ", old memberNum : " + coterie.getMemberNum());
//        Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), (coterie.getMemberNum() + 1), coterie.getMemberNum());
//        logger.info("update member number result : " + updateNumberResult);
//        if (result == 0 || updateNumberResult == 0) {
//            logger.info("update member number result exception");
//        }
        logger.info("save or update member end ");
        return true;
    }

    private Integer getPermissionByDb(Long coterieId, Long userId) {

        CoterieInfo coterie = coterieService.find(coterieId);
        Long ownerId = Long.parseLong(coterie.getOwnerId());

        //是否为圈主
        if (userId.longValue() == ownerId.longValue()) {
            logger.info("圈主");
            return MemberConstant.Permission.OWNER.getStatus();
        }

        //是否为成员
        CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
        if (null != member && member.getDelFlag().equals(MemberConstant.DelFlag.NORMAL.getStatus())) {
            logger.info("成员");
            return MemberConstant.Permission.MEMBER.getStatus();
        }

        //路人
        CoterieMemberApply apply = coterieApplyDao.selectWaitingByCoterieIdAndUserId(coterieId, userId);

        if (null != apply) {
            logger.info("路人已申请");
            return MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus();
        } else {
            logger.info("路人未申请");
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
