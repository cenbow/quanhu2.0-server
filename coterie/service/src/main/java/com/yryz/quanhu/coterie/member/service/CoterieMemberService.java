package com.yryz.quanhu.coterie.member.service;

import com.yryz.quanhu.coterie.member.dto.CoterieMemberSearchDto;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;

import java.util.List;

/**
 * 私圈成员服务
 * @author jk
 */
public interface CoterieMemberService {
	/**
	 * 申请加入私圈
	 * @param userId 用户ID
	 * @param coterieId 私圈ID
	 * @param reason 申请理由
	 */
	public void join(Long userId, Long coterieId, String reason);

	/**
	 * 申请加入私圈  审批通过
	 * @param coterieId 私圈ID
	 */
	public void agree(Long userId, Long coterieId);

	/**
	 * 申请加入私圈  审批不通过
	 * @param coterieId 私圈ID
	 */
	public void disagree(Long userId, Long coterieId);

	/**
	 * 踢出私圈
	 * @param userId
	 * @param coterieId
	 */
	public void kick(Long userId, Long coterieId, String reason);

	/**
	 * 退出私圈
	 * @param userId
	 * @param coterieId
	 */
	public void quit(Long userId, Long coterieId);

	/**
	 * 禁言
	 * @param userId
	 * @param coterieId
	 */
	public void banSpeak(Long userId, Long coterieId);

	/**
	 * 取消禁言
	 * @param userId
	 * @param coterieId
	 */
	public void unBanSpeak(Long userId, Long coterieId);

	/**
	 * 私圈成员列表
	 * @param coterieId
	 * @return
	 */
	public List<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 * 申请加入私圈记录列表
	 * @param coterieId
	 * @return
	 */
	public List<CoterieMemberApplyVo> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 * 私圈新申请的成员数量
	 * @param coterieId
	 * @return
	 */
	public Integer queryNewMemberNum(Long coterieId);

	/**
	 * 私圈成员数量
	 * @param coterieId
	 * @return
	 */
	public Integer queryMemberNum(Long coterieId);

	/**
	 * 查询单个成员信息
	 * @param userId 成员ID
	 * @param coterieId 私圈ID
	 * @return 成员信息，查不到返回null
	 */
	public CoterieMemberVo queryMemberInfo(Long userId, Long coterieId);

	/**
	 * 搜索 私圈成员
	 * @param param
	 * @return
	 */
	List<CoterieMemberVo> find(CoterieMemberSearchDto param);

	/**
	 * 搜索 私圈成员数量
	 * @param param
	 * @return
	 */
	Integer findCount(CoterieMemberSearchDto param);

	/**
	 * 查询申请信息
	 * @param coterieId 私圈ID
	 * @param userId 申请人ID
	 * @return
	 */
	CoterieMemberApplyVo findWaitingMemberApply(Long coterieId, Long userId);
}
