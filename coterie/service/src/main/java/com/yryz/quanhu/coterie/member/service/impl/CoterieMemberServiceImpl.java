package com.yryz.quanhu.coterie.member.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.dao.CoterieApplyDao;
import com.yryz.quanhu.coterie.member.dao.CoterieMemberDao;
import com.yryz.quanhu.coterie.member.dto.CoterieMemberSearchDto;
import com.yryz.quanhu.coterie.member.entity.CoterieMember;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberNotify;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

import static java.lang.Long.getLong;

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

    @Resource
    private CoterieMemberDao coterieMemberDao;

    @Resource
    private CoterieApplyDao coterieApplyDao;

    @Resource
    private CoterieService coterieService;

//	@Resource
//	private CoterieMemberMessageManager coterieMemberMessageManager;

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
        //todo  付费加入
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

            //todo order
//            Order order = new Order(custId, coterieInfo.getJoinFee() * 1L, extJson, CommonConstants.JOIN_COTERIE_MODULE_ENUM, coterieId,
//                    CommonConstants.JOIN_COTERIE_RESOURCE_ID, custId, circleId);
//            Long orderId = orderService.createOrder(OrderConstant.JOIN_COTERIE_ORDER, custId, coterieInfo.getOwnerId(), order);
            Long orderId = 1111111111L;

            result.setStatus((byte) 10);
            result.setOrderId(orderId);
            return result;
        }

        //免费不审核,直接加入
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 10) {

            //先入审请表
            CoterieMemberApply apply = makeApplyInfo(userId, coterieId, reason, MemberConstant.MemberStatus.PASS.getStatus());
            coterieApplyDao.insert(apply);

            //再入成员表
            CoterieMember record = new CoterieMember();
            record.setKid(idApi.getSnowflakeId().getData());
            record.setUserId(userId);
            record.setCoterieId(coterieId);
            record.setReason(reason);
            record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            record.setCreateDate(new Date());
            record.setLastUpdateDate(new Date());
            record.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
            record.setProcessTime(new Date());
            record.setJoinType(MemberConstant.JoinType.NOTFREE.getStatus());
            record.setKickStatus(MemberConstant.KickStatus.NORMAL.getStatus());
            record.setDelFlag(MemberConstant.DelFlag.NORMAL.getStatus());
            record.setAmount(Long.valueOf(coterie.getJoinFee()));
            record.setCreateUserId(userId);
            saveMember(record);

            result.setStatus((byte) 20);
            return result;
        }

        //免费要审核
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 11) {
            try {
                //不能有通过或是不通过的审请
                CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
                if (null == memberApply) {
                    CoterieMemberApply apply = makeApplyInfo(userId, coterieId, reason, null);
                    coterieApplyDao.insert(apply);
                    //todo msg
                    //coterieMemberMessageManager.joinMessage(custId, coterieId, reason);
                }
            } catch (Exception e) {
                throw new QuanhuException(ExceptionEnum.SysException);
            }
            result.setStatus((byte) 30);
        }

        return result;
    }

    @Override
    @Transactional
    public void kick(Long userId, Long coterieId, String reason) {
        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            if (coterie != null) {
                //update kickStatus
                CoterieMember coterieMember = new CoterieMember();
                coterieMember.setUserId(userId);
                coterieMember.setCoterieId(coterieId);
                coterieMember.setReason(reason);
                coterieMember.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                coterieMember.setDelFlag(MemberConstant.KickStatus.KICKED.getStatus());
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
            }
            //todo msg
            //coterieMemberMessageManager.kickMessage(custId, coterieId, reason);
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
            }
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public void banSpeak(Long userId, Long coterieId, Integer type) {
        CoterieMember record = new CoterieMember();
        record.setUserId(userId);
        record.setCoterieId(coterieId);
        record.setLastUpdateDate(new Date());
        if (type == 1) {
            record.setBanSpeak(MemberConstant.BanSpeak.BANSPEAK.getStatus());
        } else {
            record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
        }

        try {
            coterieMemberDao.updateByCoterieMember(record);
            //todo msg
            //coterieMemberMessageManager.banSpeakMessage(custId, coterieId);
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Integer permission(Long userId, Long coterieId) {

        try {
            CoterieInfo coterie = coterieService.find(coterieId);
            Long ownerId = Long.parseLong(coterie.getOwnerId());

            //是否为圈主
            if (userId.longValue() == ownerId.longValue()) {
                return MemberConstant.Permission.OWNER.getStatus();
            }

            //是否为成员
            CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
            if (null != member) {
                return MemberConstant.Permission.MEMBER.getStatus();
            }

            //路人
            CoterieMemberApply apply = coterieApplyDao.selectWaitingByCoterieIdAndUserId(coterieId, userId);

            if (null != apply) {
                return MemberConstant.Permission.STRANGER_WAITING_CHECK.getStatus();
            } else {
                return MemberConstant.Permission.STRANGER_NON_CHECK.getStatus();
            }
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }

    @Override
    public Boolean isBanSpeak(Long userId, Long coterieId) {

        CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);

        if (member.getBanSpeak() == MemberConstant.BanSpeak.BANSPEAK.getStatus()) {
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
        Integer result = null;
        CoterieMemberApply apply = null;
        CoterieMember record = new CoterieMember();

        try {
            if (joinType.equals(MemberConstant.JoinType.FREE.getStatus())) {
                //免费审请，先update再insert member
                apply = new CoterieMemberApply();
                apply.setCoterieId(coterieId);
                apply.setUserId(userId);
                apply.setLastUpdateDate(new Date());
                apply.setProcessTime(new Date());
                if (null == memberStatus) {
                    apply.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
                } else {
                    apply.setMemberStatus(memberStatus);
                }
                result = coterieApplyDao.updateByCoterieApply(apply);

                //免费审核
                record.setJoinType(MemberConstant.JoinType.FREE.getStatus());
                record.setAmount(0L);

                CoterieMemberApply memberApply = coterieApplyDao.selectByCoterieIdAndUserId(coterieId, userId);
                record.setReason(memberApply.getReason());

            } else {
                //收费，先insert member apply再 insert member
                apply = makeApplyInfo(userId, coterieId, "", MemberConstant.MemberStatus.PASS.getStatus());
                result = coterieApplyDao.insert(apply);

                //收费不用审核，直接加入
                CoterieInfo coterie = coterieService.find(coterieId);
                record.setJoinType(MemberConstant.JoinType.NOTFREE.getStatus());
                record.setAmount(coterie.getJoinFee().longValue());
                record.setReason("");
            }

            if (result > 0 && apply.getMemberStatus() == MemberConstant.MemberStatus.PASS.getStatus()) {
                record.setKid(idApi.getSnowflakeId().getData());
                record.setUserId(userId);
                record.setCoterieId(coterieId);
                record.setLastUpdateDate(new Date());
                record.setMemberStatus(MemberConstant.MemberStatus.PASS.getStatus());
                record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
                record.setDelFlag(MemberConstant.DelFlag.NORMAL.getStatus());
                record.setKickStatus(MemberConstant.KickStatus.NORMAL.getStatus());
                record.setProcessTime(apply.getProcessTime());
                record.setCreateUserId(userId);

                CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(coterieId, userId);
                if (member == null) {
                    record.setCreateDate(new Date());
                    saveMember(record);
                } else {
                    coterieMemberDao.updateByCoterieMember(record);
                }
            }
        } catch (Exception e) {
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

        PageList<CoterieMemberApplyVo> pageList = new PageList<>(pageNo, pageSize, applyVos);

        return pageList;
    }

    private CoterieMemberApply makeApplyInfo(Long userId, Long coterieId, String reason, Byte status) {
        CoterieMemberApply apply = new CoterieMemberApply();
        apply.setCoterieId(coterieId);
        apply.setCreateDate(new Date());
        apply.setUserId(userId);
        apply.setLastUpdateDate(new Date());
        apply.setReason(reason);
        apply.setCreateUserId(userId);
        if (null != status) {
            apply.setMemberStatus(status);
        } else {
            apply.setMemberStatus(MemberConstant.MemberStatus.WAIT.getStatus());
        }

        Long kid = idApi.getSnowflakeId().getData();
        apply.setKid(kid);

        return apply;
    }

    private void saveMember(CoterieMember record) {
        try {
            CoterieMember member = coterieMemberDao.selectByCoterieIdAndUserId(record.getCoterieId(), record.getUserId());
            Integer result = null;
            if (member == null) {
                result = coterieMemberDao.insert(record);
            } else {
                result = coterieMemberDao.updateByCoterieMember(record);
            }

            CoterieInfo coterie = coterieService.find(record.getCoterieId());
            Integer updateNumberResult = coterieService.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() + 1, coterie.getMemberNum());
            if (updateNumberResult == 0) {
                throw new QuanhuException(ExceptionEnum.SysException);//"更新成员人数失败"
            }
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }
}
