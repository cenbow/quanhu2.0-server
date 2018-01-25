package com.yryz.quanhu.coterie.member.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 私圈成员服务实现
 *
 * @author chengyunfei
 */
@Service
public class CoterieMemberServiceImpl implements CoterieMemberService {
    @Resource
    private CoterieMemberDao coterieMemberDao;
    @Resource
    private CoterieApplyDao coterieApplyDao;
    @Resource
    private CoterieMapper coterieMapper;
//	@Resource
//	private CoterieMemberMessageManager coterieMemberMessageManager;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CoterieMemberVoForJoin join(Long userId, Long coterieId, String reason) {

        CoterieMemberVoForJoin result = new CoterieMemberVoForJoin();

//        Coterie coterie = coterieMapper.selectByCoterieId(coterieId.toString());
        Coterie coterie = new Coterie();
        coterie.setMemberNum(2000);


        //私圈人数已满
        if (coterie.getMemberNum().intValue() >= 2000) {
            result.setStatus(40);
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

//            Order order = new Order(custId, coterieInfo.getJoinFee() * 1L, extJson, CommonConstants.JOIN_COTERIE_MODULE_ENUM, coterieId,
//                    CommonConstants.JOIN_COTERIE_RESOURCE_ID, custId, circleId);
//            Long orderId = orderService.createOrder(OrderConstant.JOIN_COTERIE_ORDER, custId, coterieInfo.getOwnerId(), order);
            Long orderId = 1111111111L;

            result.setStatus(10);
            result.setOrderId(orderId);
            return result;
        }

        //免费不审核,直接加入
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 10) {

            //先入审请表
            CoterieMemberApply apply = makeApplyInfo(userId, coterieId, reason, MemberConstant.Status.PASS.getStatus());
            coterieApplyDao.insert(apply);

            //再入成员表
            CoterieMember record = new CoterieMember();
            record.setUserId(userId);
            record.setCoterieId(coterieId);
            record.setReason(reason);
            record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            record.setCreateDate(new Date());
            record.setLastUpdateDate(new Date());
            record.setMemberStatus(MemberConstant.Status.PASS.getStatus());
            record.setProcessTime(new Date());
            record.setJoinType(MemberConstant.JoinType.NOTFREE.getStatus());
            record.setAmount(Long.valueOf(coterie.getJoinFee()));
            saveMember(record);

            result.setStatus(20);
            return result;
        }

        //免费要审核
        if (coterie.getJoinFee() == 0 && coterie.getJoinCheck() == 11) {
            try {
                //不能有通过或是不通过的审请
                List<CoterieMemberApply> list = coterieApplyDao.selectByCoterieIdAndCustId(coterieId, userId);
                if (list.isEmpty()) {
                    CoterieMemberApply apply = makeApplyInfo(userId, coterieId, reason, null);
                    coterieApplyDao.insert(apply);
                    //coterieMemberMessageManager.joinMessage(custId, coterieId, reason);
                }
            } catch (Exception e) {
                throw new QuanhuException(ExceptionEnum.SysException);
            }
        }

        result.setStatus(30);
        return result;
    }

    @Override
    @Transactional
    public void kick(Long userId, Long coterieId, String reason) {
        try {
//            Coterie coterie = coterieMapper.selectByCoterieId(coterieId.toString());
//            if (coterie != null) {
                //update kickStatus
                CoterieMember coterieMember = new CoterieMember();
                coterieMember.setUserId(userId);
                coterieMember.setCoterieId(coterieId);
                coterieMember.setReason(reason);
                int resultMember = coterieMemberDao.updateByCoterieMember(coterieMember);

                //update delFlag
                CoterieMemberApply apply = new CoterieMemberApply();
                apply.setUserId(userId);
                apply.setCoterieId(coterieId);
                apply.setDelFlag(MemberConstant.DelFlag.DELETED.getStatus());
                int resultApply = coterieApplyDao.updateByCoterieApply(apply);

                if (resultMember > 0 && resultApply > 0) {
                    //更新私圈成员数
//                    coterieMapper.updateMemberNum(coterie.getCoterieId().toString(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                }
//            }
//            coterieMemberMessageManager.kickMessage(custId, coterieId, reason);
        } catch (Exception e) {
            e.printStackTrace();
            throw new MysqlOptException("param custId:" + userId + "coterieId:" + coterieId, e);
        }
    }

    @Override
    @Transactional
    public void quit(Long userId, Long coterieId) {
        try {
//            Coterie coterie = coterieMapper.selectByCoterieId(coterieId.toString());
//            if (coterie != null) {
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
//                    coterieMapper.updateMemberNum(coterie.getCoterieId().toString(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                }
//            }
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
         Integer result = coterieMemberDao.updateByCoterieMember(record);

         if (result > 0) {

         } else {
             System.out.println("#######################################"+ result);
         }
//            coterieMemberMessageManager.banSpeakMessage(custId, coterieId);
        } catch (Exception e) {
            e.printStackTrace();
//            throw new QuanhuException(ExceptionEnum.SysException);
        }
    }


    /************** 0124 ***********************************************************************/


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void agree(Long userId, Long coterieId) {
        CoterieMemberApply apply = new CoterieMemberApply();
        apply.setCoterieId(coterieId);
        apply.setUserId(userId);
        apply.setLastUpdateDate(new Date());
        apply.setProcessTime(new Date());
        apply.setMemberStatus(MemberConstant.Status.PASS.getStatus());
        try {
            coterieApplyDao.updateByCoterieApply(apply);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieApply:" + apply, e);
        }

        CoterieMember member = coterieMemberDao.selectByCoterieIdAndCustId(coterieId, userId);
        if (member == null) {
//            Coterie info = coterieMapper.selectByCoterieId(coterieId);
//            if (info == null) {
//                throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
//            }
            CoterieMember record = new CoterieMember();
            record.setUserId(userId);
            record.setCoterieId(coterieId);
            record.setLastUpdateDate(new Date());
            record.setMemberStatus(MemberConstant.Status.PASS.getStatus());
            record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            record.setCreateDate(new Date());
            record.setProcessTime(new Date());
            //需要审批的一定是免费的
            record.setJoinType(MemberConstant.JoinType.FREE.getStatus());
            record.setAmount(0L);
            saveMember(record);
        }
    }

    @Override
    public void disagree(Long userId, Long coterieId) {
        CoterieMemberApply apply = new CoterieMemberApply();
        apply.setCoterieId(coterieId);
        apply.setUserId(userId);
        apply.setLastUpdateDate(new Date());
        apply.setProcessTime(new Date());
        apply.setMemberStatus(MemberConstant.Status.NOTPASS.getStatus());
        try {
            coterieApplyDao.updateByCoterieApply(apply);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieApply:" + apply, e);
        }
    }

    @Override
    public List<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize) {
        List<CoterieMember> list = Lists.newArrayList();
        try {
            int start = (pageNum.intValue() - 1) * pageSize.intValue();
            list = coterieMemberDao.selectPageByCoterieId(coterieId, start, pageSize);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId, e);
        }
        return (List<CoterieMemberVo>) GsonUtils.parseList(list, CoterieMemberVo.class);

    }

    @Override
    public List<CoterieMemberApplyVo> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize) {
        List<CoterieMemberApply> list = Lists.newArrayList();
        try {
            int start = (pageNum - 1) * pageSize;
            list = coterieApplyDao.selectPageByCoterieId(coterieId, start, pageSize);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId, e);
        }
        return (List<CoterieMemberApplyVo>) GsonUtils.parseList(list, CoterieMemberApplyVo.class);
    }

    @Override
    public Integer queryNewMemberNum(Long coterieId) {
        try {
            return coterieApplyDao.selectNewMemberNum(coterieId);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId, e);
        }
    }

    @Override
    public Integer queryMemberNum(Long coterieId) {
        try {
            return coterieMemberDao.selectCountByCoterieId(coterieId);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId, e);
        }
    }

    @Override
    public CoterieMemberVo queryMemberInfo(Long userId, Long coterieId) {

        try {
            CoterieMember member = coterieMemberDao.selectByCoterieIdAndCustId(coterieId, userId);
            return (CoterieMemberVo) GsonUtils.parseObj(member, CoterieMemberVo.class);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId + ",custId:" + userId, e);
        }
    }

    @Override
    public List<CoterieMemberVo> find(CoterieMemberSearchDto param) {
        try {
            CoterieMemberSearchDto searchParam = GsonUtils.parseObj(param, CoterieMemberSearchDto.class);
            int start = (param.getPageNum() - 1) * param.getPageSize();
//            searchParam.setStart(start);
            List<CoterieMember> list = coterieMemberDao.selectBySearchParam(searchParam);

            List<Long> coterieIdList = Lists.newArrayList();
            List<CoterieMemberVo> rstList = Lists.newArrayList();
            for (int i = 0; i < list.size(); i++) {
                CoterieMember c = list.get(i);
                CoterieMemberVo info = new CoterieMemberVo();
                info.setAmount(c.getAmount().intValue());
                info.setBanSpeak(c.getBanSpeak());
                info.setCoterieId(c.getCoterieId());
                info.setCreateDate(c.getCreateDate());
                info.setJoinType(c.getJoinType());
                info.setUserId(c.getUserId());
                rstList.add(info);

                coterieIdList.add(c.getCoterieId());
            }

            if (!coterieIdList.isEmpty()) {
                Map<String, Coterie> maps = Maps.newHashMap();
//                List<Coterie> coterieList = coterieMapper.selectListByCoterieIdList(coterieIdList);
                List<Coterie> coterieList = new ArrayList<>();
                for (int i = 0; i < coterieList.size(); i++) {
                    Coterie c = coterieList.get(i);
                    maps.put(c.getCoterieId() + "", c);
                }
                for (int j = 0; j < rstList.size(); j++) {
                    CoterieMemberVo info = rstList.get(j);
                    Coterie c = maps.get(info.getCoterieId());
                    if (c != null) {
//                        info.setCoterieName(c.getName());
                    }
                }
            }

            return rstList;
        } catch (Exception e) {
            throw new MysqlOptException("param param:" + param, e);
        }
    }

    @Override
    public Integer findCount(CoterieMemberSearchDto param) {
        try {
            CoterieMemberSearchDto searchParam = GsonUtils.parseObj(param, CoterieMemberSearchDto.class);
            int start = (param.getPageNum() - 1) * param.getPageSize();
//            searchParam.setStart(start);
            return coterieMemberDao.selectCountBySearchParam(searchParam);
        } catch (Exception e) {
            throw new MysqlOptException("param param:" + param, e);
        }
    }

    @Override
    public CoterieMemberApplyVo findWaitingMemberApply(Long coterieId, Long userId) {
        try {
            CoterieMemberApply apply = coterieApplyDao.selectWaitingByCoterieIdAndCustId(coterieId, userId);
            return GsonUtils.parseObj(apply, CoterieMemberApplyVo.class);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieId:" + coterieId + ",custId" + userId, e);
        }
    }

    private CoterieMemberApply makeApplyInfo(Long userId, Long coterieId, String reason, Integer status){
        CoterieMemberApply apply = new CoterieMemberApply();
        apply.setCoterieId(coterieId);
        apply.setCreateDate(new Date());
        apply.setUserId(userId);
        apply.setLastUpdateDate(new Date());
        apply.setReason(reason);
        if (null != status) {
            apply.setMemberStatus(status);
        } else {
            apply.setMemberStatus(MemberConstant.Status.WAIT.getStatus());
        }

        return apply;
    }

    private void saveMember(CoterieMember record) {
        try {
            CoterieMember member = coterieMemberDao.selectByCoterieIdAndCustId(record.getCoterieId(), record.getUserId());
            if (member == null) {
                Coterie coterie = coterieMapper.selectByCoterieId(record.getCoterieId().toString());
                if (coterie != null) {
                    int count = coterieMapper.updateMemberNum(coterie.getCoterieId().toString(), coterie.getMemberNum() + 1, coterie.getMemberNum());
                    if (count > 0) {
                        coterieMemberDao.insert(record);
                    } else {
                        throw new QuanhuException(ExceptionEnum.SysException);//"更新成员人数失败"
                    }
                }
            }
        } catch (Exception e) {
            throw new MysqlOptException("param coterieMember:" + record, e);
        }
    }


}
