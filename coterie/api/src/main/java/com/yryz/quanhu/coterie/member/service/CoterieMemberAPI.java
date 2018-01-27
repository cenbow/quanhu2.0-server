package com.yryz.quanhu.coterie.member.service;

import com.yryz.common.response.PageList;
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
	public Response<Integer> permission(Long userId, Long coterieId);

	/**
	 * 申请加入私圈审批通过
	 * @param userId
	 * @param coterieId
	 */
	public Response<String> audit(Long userId, Long memberId, Long coterieId, Byte type);

	/**
	 * 私圈新申请的成员数量
	 * @param coterieId
	 * @return
	 */
	public Response<Integer> queryNewMemberNum(Long coterieId);

	/**
	 * 是否被禁言
	 * @param coterieId
	 * @param userId
	 * @return
	 */
	public Response<Boolean> isBanSpeak(Long userId, Long coterieId);

	/**
	 * 私圈成员列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Response<PageList<CoterieMemberVo>> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize);

	/**
	 *  申请加入私圈记录列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public Response<PageList<CoterieMemberApplyVo>> queryMemberApplyList(Long coterieId, Integer pageNum, Integer pageSize);
}
