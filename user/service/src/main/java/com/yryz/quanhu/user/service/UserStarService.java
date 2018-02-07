package com.yryz.quanhu.user.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.pagehelper.Page;
import com.yryz.quanhu.user.dto.StarAuthParamDTO;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.entity.UserStarAuthLog;
import com.yryz.quanhu.user.vo.UserStarSimpleVo;

/**
 * 达人业务
 * @author danshiyu
 *
 */
public interface UserStarService {
	/**
	 * 保存达人认证信息
	 * @param record
	 * @return
	 */
    int save(UserStarAuth record);
    
    /**
     * 查询单条达人认证信息
     * @param userId
     * @param idCard
     * @return
     */
    UserStarAuth get(String userId,String idCard);
    
    /**
     * 批量查询达人认证信息
     * @param userIds
     * @return
     */
    List<UserStarAuth> get(List<String> userIds);
    
    /**
     * 更新达人认证信息
     * @param record
     * @return
     */
    int update(UserStarAuth record);
    
    /**
     * 包含审核取消认证更新
     * @param reAuthModel
     * @return
     */
    int updateAudit(UserStarAuth reAuthModel);
    
    /**
     * 推荐更新
     * @param authModel
     * @return
     */
    int updateRecommend(UserStarAuth authModel);
    /**
     * 推荐权重更新
     * @param userId
     * @param weight
     * @return
     */
    int updateRecommendHeight(Long userId,Integer weight);
    
    /**
     * 后台达人认证信息列表
     * @param paramDTO
     * @return
     */
    Page<UserStarAuth> listByParams(Integer pageNo,Integer pageSize,StarAuthParamDTO paramDTO);
    
    /**
     * app端达人列表
     * @param paramDTO
     * @param start
     *@param limit @return
     */
    Page<UserStarAuth> starList(StarAuthParamDTO paramDTO);

    /**
     * 标签达人列表
     * @param paramDTO
     * @return
     */
    Page<UserStarAuth> labelStarList(StarAuthParamDTO paramDTO);
    
    /**
     * 达人申请以及审核日志查询
     * @param userId
     * @return
     */
    List<UserStarAuthLog> listStarDetail(String userId);
    
    /**
     * 统计达人总数
     * @return
     */
    Integer countStar();
    /**
     * 查询达人简要信息
     * @param userIds
     * @return
     */
    Map<String,UserStarSimpleVo> getStarSimple(Set<String> userIds);
}
