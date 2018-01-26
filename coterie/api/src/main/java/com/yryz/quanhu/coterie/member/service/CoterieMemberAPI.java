package com.yryz.quanhu.coterie.member.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.member.vo.*;

import java.util.List;

/**
 * 私圈成员服务
 * @author chengyunfei
 *
 */
public interface CoterieMemberAPI {
	/**
	 * 用户申请加入私圈
	 * @param userId 用户ID
	 * @param coterieId 私圈ID
	 * @param reason 申请理由
	 */
	public Response<CoterieMemberVoForJoin> join(Long userId, Long coterieId, String reason);

	/**
	 * 圈主踢出私圈成员
	 * @param memberId
	 * @param coterieId
	 * @param reason
	 */
	public Response<String> kick(Long userId, Long memberId, Long coterieId, String reason);

	/**
	 * 圈粉退出私圈
	 * @param userId
	 * @param coterieId
	 */
	public Response<String> quit(Long userId, Long coterieId);

	/**
	 * 设置禁言/取消禁言
	 * @param memberId
	 * @param coterieId
	 */
	public Response<String> banSpeak(Long userId, Long memberId, Long coterieId, Integer type);


	/**
	 * 获取私圈成员权限信息
	 * @param userId
	 * @param coterieId
	 */
	public Response<CoterieMemberVoForPermission> permission(Long userId, Long coterieId);

	/**
	 * 申请加入私圈审批通过
	 * @param userId
	 * @param coterieId
	 */
	public Response audit(Long userId, Long memberId, Long coterieId, Integer type);

	/**
	 * 私圈新申请的成员数量
	 * @param coterieId
	 * @return
	 */
	public Response<CoterieMemberVoForNewMemberCount> queryNewMemberNum(Long coterieId);

	/**
	 * 私圈成员列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Response<List<CoterieMemberVo>> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 *  申请加入私圈记录列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Response<List<CoterieMemberApplyVo>> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 * 是否被禁言
	 * @param coterieId
	 * @param userId
	 * @return
	 */
	public Response<Boolean> isBanSpeak(Long userId, Long coterieId);

	/**
	 * 私圈成员数量
	 * @param coterieId
	 * @return
	 */
//	public Integer queryMemberNum(Long coterieId);

	/**
	 * 查询私圈里的某个成员
	 * @param userId
	 * @param coterieId
	 * @return
	 */
//	public CoterieMemberVo queryCoterieMemberInfo(Long userId, Long coterieId);

	/**
	 * 查询申请记录
	 * @param coterieId 申请的私圈
	 * @param userId 申请人ID
	 * @return
	 */
//	public CoterieMemberApplyVo queryWaitingMemberApply(Long coterieId, Long userId);
	
}
