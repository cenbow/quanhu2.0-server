package com.yryz.quanhu.coterie.member.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.coterie.member.dto.CoterieMemberSearchDto;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;

import java.util.List;

/**
 * 私圈成员服务
 * @author chengyunfei
 */
public interface CoterieMemberService {
	/**
	 * 申请加入私圈
	 * @param userId 用户ID
	 * @param coterieId 私圈ID
	 * @param reason 申请理由
	 */
	public CoterieMemberVoForJoin join(Long userId, Long coterieId, String reason);
//	public void joinCoteriePay(Long userId, Long coterieId, String reason);

	/**
	 * 申请加入私圈  审批通过
	 * @param coterieId 私圈ID
	 */
	public void audit(Long userId, Long coterieId, Byte memberStatus, Byte joinType);

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
	 * 设置禁言/取消禁言
	 * @param userId
	 * @param coterieId
	 */
	public void banSpeak(Long userId, Long coterieId, Integer type);


	/**
	 * 私圈成员列表
	 * @param coterieId
	 * @return
	 */
	public PageList<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 * 申请加入私圈记录列表
	 * @param coterieId
	 * @return
	 */
	public PageList<CoterieMemberApplyVo> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 * 私圈新申请的成员数量
	 * @param coterieId
	 * @return
	 */
	public Integer queryNewMemberNum(Long coterieId);

	/**
	 * 私圈成员权限
	 * @param userId
	 * @param coterieId
	 */
	public Integer permission(Long userId, Long coterieId);

	/**
	 * 私圈成员权限
	 * @param userId
	 * @param coterieId
	 */
	public Boolean isBanSpeak(Long userId, Long coterieId);
}
