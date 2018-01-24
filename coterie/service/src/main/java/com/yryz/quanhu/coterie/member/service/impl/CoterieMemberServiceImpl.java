package com.yryz.quanhu.coterie.member.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.member.constants.MemberConstant;
import com.yryz.quanhu.coterie.member.dao.CoterieApplyDao;
import com.yryz.quanhu.coterie.member.dao.CoterieMemberDao;
import com.yryz.quanhu.coterie.member.dto.CoterieMemberSearchDto;
import com.yryz.quanhu.coterie.member.entity.CoterieMember;
import com.yryz.quanhu.coterie.member.entity.CoterieMemberApply;
import com.yryz.quanhu.coterie.member.service.CoterieMemberService;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
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
    public void join(Long userId, Long coterieId, String reason) {
//        Coterie info = coterieMapper.selectByCoterieId(coterieId);
//        if (info == null) {
//            throw new QuanhuException(ExceptionEnum.COTERIE_NON_EXISTENT);
//        }

//        if (info.getJoinCheck().intValue() == 10 || info.getJoinFee().intValue() > 0) {//不审核 或付费支付的直接加入
            CoterieMember record = new CoterieMember();
            record.setUserId(userId);
            record.setCoterieId(coterieId);
            record.setReason(reason);
            record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
            record.setCreateDate(new Date());
            record.setLastUpdateDate(new Date());
            record.setMemberStatus(MemberConstant.Status.PASS.getStatus());
            record.setProcessTime(new Date());
//            record.setJoinType(info.getJoinFee().intValue() > 0 ? MemberConstant.JoinType.NOTFREE.getStatus() : MemberConstant.JoinType.FREE.getStatus());
//            record.setAmount(Long.valueOf(info.getJoinFee()));
            saveMember(record);
//        } else {
            CoterieMemberApply apply = new CoterieMemberApply();
            apply.setCoterieId(coterieId);
            apply.setCreateDate(new Date());
            apply.setUserId(userId);
            apply.setLastUpdateDate(new Date());
            apply.setReason(reason);
            apply.setMemberStatus(MemberConstant.Status.WAIT.getStatus());
            try {
                List<CoterieMemberApply> list = coterieApplyDao.selectByCoterieIdAndCustId(coterieId, userId);
                if (list.isEmpty()) {
                    coterieApplyDao.insertSelective(apply);
//                    coterieMemberMessageManager.joinMessage(custId, coterieId, reason);
                }
            } catch (Exception e) {
                throw new MysqlOptException("param coterieApply:" + apply, e);
            }
//        }
    }

    private void saveMember(CoterieMember record) {
        try {
            CoterieMember member = coterieMemberDao.selectByCoterieIdAndCustId(record.getCoterieId(), record.getUserId());
            if (member == null) {
//                Coterie coterie = coterieMapper.selectByCoterieId(record.getCoterieId());
//                if (coterie != null) {
//                    int count = coterieMapper.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() + 1, coterie.getMemberNum());
//                    if (count > 0) {
                        coterieMemberDao.insert(record);
//                    } else {
//                        throw new QuanhuException(ExceptionEnum.SysException);//"更新成员人数失败"
//                    }
//                }
            }
        } catch (Exception e) {
            throw new MysqlOptException("param coterieMember:" + record, e);
        }
    }

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
            coterieApplyDao.updateByCoterieIdAndCustIdSelective(apply);
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
            coterieApplyDao.updateByCoterieIdAndCustIdSelective(apply);
        } catch (Exception e) {
            throw new MysqlOptException("param coterieApply:" + apply, e);
        }
    }

    @Override
    @Transactional
    public void kick(Long userId, Long coterieId, String reason) {
        try {
//            Coterie coterie = coterieMapper.selectByCoterieId(coterieId);
//            if (coterie != null) {
                int count = coterieMemberDao.deleteByCustIdAndCoterieId(userId, coterieId);
                if (count > 0) {
//                    coterieMapper.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() - 1, coterie.getMemberNum());
                }
//            }
//            coterieMemberMessageManager.kickMessage(custId, coterieId, reason);
        } catch (Exception e) {
            throw new MysqlOptException("param custId:" + userId + "coterieId:" + coterieId, e);
        }
    }

    @Override
    @Transactional
    public void quit(Long userId, Long coterieId) {
        try {
//            Coterie coterie = coterieMapper.selectByCoterieId(coterieId);
//            if (coterie != null) {
//                int count = coterieMemberDao.deleteByCustIdAndCoterieId(custId, coterieId);
//                if (count > 0) {
//                    coterieMapper.updateMemberNum(coterie.getCoterieId(), coterie.getMemberNum() - 1, coterie.getMemberNum());
//                }
//            }
        } catch (Exception e) {
//            throw new MysqlOptException("param custId:" + custId + "coterieId:" + coterieId, e);
        }
    }

    @Override
    public void banSpeak(Long userId, Long coterieId) {
        CoterieMember record = new CoterieMember();
        record.setUserId(userId);
        record.setCoterieId(coterieId);
        record.setLastUpdateDate(new Date());
        record.setBanSpeak(MemberConstant.BanSpeak.BANSPEAK.getStatus());
        try {
            coterieMemberDao.updateByCustIdAndCoterieId(record);
//            coterieMemberMessageManager.banSpeakMessage(custId, coterieId);
        } catch (Exception e) {
            throw new MysqlOptException("param coterie:" + record, e);
        }
    }

    @Override
    public void unBanSpeak(Long userId, Long coterieId) {
        CoterieMember record = new CoterieMember();
        record.setUserId(userId);
        record.setCoterieId(coterieId);
        record.setLastUpdateDate(new Date());
        record.setBanSpeak(MemberConstant.BanSpeak.NORMAL.getStatus());
        try {
            coterieMemberDao.updateByCustIdAndCoterieId(record);
        } catch (Exception e) {
            throw new MysqlOptException("param coterie:" + record, e);
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
                    maps.put(c.getCoterieId(), c);
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

}
