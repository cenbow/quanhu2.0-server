package com.yryz.quanhu.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.aliyun.oss.ServiceException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.user.dto.StarAuthInfo;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.dto.StarAuthQueryDTO;
import com.yryz.quanhu.user.dto.StarRecommendQueryDTO;
import com.yryz.quanhu.user.vo.StarAuthAuditVo;
import com.yryz.quanhu.user.vo.StarAuthLogVO;
import com.yryz.quanhu.user.vo.StarInfoVO;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;


public interface UserStarApi {
	/**
	 * 达人认证提交
	 * @param info
	 * @throws ServiceException
	 */
	public Response<Boolean> save(StarAuthInfo info);
	/**
	 * 查询单条达人认证信息
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	public Response<StarAuthInfo> get(String userId);
    
	/**
	 * 批量查询达人认证信息
	 * @param userIds
	 * @return
	 * @throws ServiceException
	 */
	public Response<Map<String,StarAuthInfo>> get(Set<String> userIds);
	
	/**
	 * 更新达人认证信息
	 * @param record
	 * @return
	 * @throws ServiceException
	 */
    public Response<Boolean> update(StarAuthInfo record);
    /**
     * 包含审核取消认证更新
     * @param auditVo
     * @return
     * @throws ServiceException
     */
    public Response<Boolean> updateAudit(StarAuthAuditVo auditVo);
    /**
     * 推荐更新
     * @param authModel
     * @return
     * @throws ServiceException
     */
    public Response<Boolean> updateRecommend(StarAuthInfo authModel);
    /**
     * 更新达人推荐排序权重值
     * @param userId
     * @param weight
     * @throws ServiceException
     */
    public Response<Boolean> updateStarWeight(String userId,Integer weight);
    /**
     * 后台达人认证信息列表
     * @param pageNo
     * @param pageSize
     * @param paramDTO
     * @return
     * @throws ServiceException
     */
    public Response<PageList<StarAuthInfo>> listByAuth(Integer pageNo,Integer pageSize,StarAuthQueryDTO paramDTO);
    /**
     * 后台达人推荐信息列表
     * @param pageNo
     * @param pageSize
     * @param paramDTO
     * @return
     * @throws ServiceException
     */
    public Response<PageList<StarAuthInfo>> listByRecommend(Integer pageNo,Integer pageSize,StarRecommendQueryDTO paramDTO);
     
    /**
     * 查询达人申请以及审核日志(达人审核详情)
     * @param userId
     * @return
     */
    public Response<List<StarAuthLogVO>> listAuthLog(String userId);
    
    /**
     * app端达人运营推荐列表
     * @param authParamDTO
     * @return
     * @throws ServiceException
     */
    public Response<PageList<StarInfoVO>> starList(StarAuthParamDTO authParamDTO);
    
    /**
     * 统计达人总数
     * @return
     * @throws ServiceException
     */
    Response<Integer> countStar();

	/**
	 * 标签达人列表
	 * @param paramDTO
	 * @return
	 */
    Response<PageList<StarInfoVO>> labelStarList(StarAuthParamDTO paramDTO);
}
