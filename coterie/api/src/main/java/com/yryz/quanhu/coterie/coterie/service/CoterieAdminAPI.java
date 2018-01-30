package com.yryz.quanhu.coterie.coterie.service;

import java.util.List;

import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAuditRecordInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieMemberInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.MemberSearchParam;

/**
 * 私圈管理后台 接口
 * @author wt
 *
 */
public interface CoterieAdminAPI {
	/**
	 * 私圈申请 审批通过（上线）
	 * @param coterieId 私圈ID
	 * @param custId
	 * @param reason
	 */
	public void agree(String coterieId, String custId, String reason);
	
	/**
	 * 私圈申请 审批不通过
	 * @param coterieId
	 * @param custId
	 * @param reason
	 */
	public void disagree(String coterieId, String custId, String reason);
	
	/**
	 *  私圈 下线
	 * @param coterieId
	 * @param custId
	 * @param reason
	 */
	public void offline(String coterieId, String custId, String reason);
	
	/**
	 * 私圈分页列表
	 * @param param
	 * @return
	 */
	public List<CoterieAdmin> getCoterieList(CoterieSearchParam param);
	
	/**
	 * 私圈搜索 数量
	 * @param param
	 * @return
	 */
	public Integer getCoterieCount(CoterieSearchParam param);
	
	/**
	 * 
	 * @param pageNum
	 * @param pageSize
	 * @return 操作记录
	 */
	/**
	 * 私圈审批记录
	 * @param coterieId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<CoterieAuditRecordInfo> getAuditRecordList(String coterieId, Integer pageNum, Integer pageSize);
	
	/**
	 * 成员搜索
	 * @param param
	 * @return
	 */
	public List<CoterieMemberInfo> getMemberList(MemberSearchParam param);
	
	/**
	 * 成员搜索   总数
	 * @param param
	 * @return
	 */
	public Integer getMemberCount(MemberSearchParam param);
	
	/**
	 * 推荐私圈设置
	 * @param coterieIdList
	 */
	public void recommendCoterie(List<Long> coterieIdList);
	
	/**
	 * 取消推荐私圈设置
	 * @param coterieIdList
	 */
	public void cancelRecommendCoterie(List<Long> coterieIdList);
}
