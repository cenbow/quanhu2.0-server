package com.yryz.quanhu.coterie.member.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberApplyVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForJoin;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVo;
import com.yryz.quanhu.coterie.member.vo.CoterieMemberVoForPermission;

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
	 * @param userId
	 * @param coterieId
	 * @param reason
	 */
	public Response<String> kick(Long userId, Long coterieId, String reason);

	/**
	 * 圈粉退出私圈
	 * @param userId
	 * @param coterieId
	 */
	public Response<String> quit(Long userId, Long coterieId);

	/**
	 * 设置禁言/取消禁言
	 * @param userId
	 * @param coterieId
	 */
	public Response<String> banSpeak(Long userId, Long coterieId, Integer type);


	/**
	 * 获取私圈成员权限信息
	 * @param userId
	 * @param coterieId
	 */
	public Response<CoterieMemberVoForPermission> permission(Long userId, Long coterieId);










/******** 0124 ********************************/

	/**
	 * 申请加入私圈  审批通过
	 * @param userId
	 * @param coterieId
	 */
	public void agree(Long userId, Long coterieId);
	/**
	 * 申请加入私圈  审批不通过
	 * @param userId
	 * @param coterieId
	 */
	public void disagree(Long userId, Long coterieId);

	/**
	 * 私圈成员列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<CoterieMemberVo> queryMemberList(Long coterieId, Integer pageNum, Integer pageSize);
	/**
	 *  申请加入私圈记录列表
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
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
	 * 查询私圈里的某个成员
	 * @param userId
	 * @param coterieId
	 * @return
	 */
	public CoterieMemberVo queryCoterieMemberInfo(Long userId, Long coterieId);

	/**
	 * 查询申请记录
	 * @param coterieId 申请的私圈
	 * @param userId 申请人ID
	 * @return
	 */
	public CoterieMemberApplyVo queryWaitingMemberApply(Long coterieId, Long userId);
	
}
