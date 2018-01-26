package com.yryz.quanhu.coterie.coterie.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.exception.ServiceException;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAuditInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;

import java.util.List;

/**
 * 私圈服务
 * @author wt
 *
 */
public interface CoterieApi {
	/**
	 * 查询私圈信息列表
	 * @param coterieIdList 私圈ID集合
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryListByCoterieIdList(List<Long> coterieIdList) throws ServiceException;
	/**
	 * 查询已上架的私圈列表
	 * @param circleId
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryPage(String circleId, Integer pageNum, Integer pageSize) throws ServiceException;
	
	/**
	 * 查询已上架的私圈列表
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryPage(Integer pageNum, Integer pageSize) throws ServiceException;
	
	/**
	 * 查询私圈信息
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<CoterieInfo> queryCoterieInfo(Long coterieId) throws ServiceException;
	
	/**
	 * 设置私圈信息
	 * @param info  coterieId必填
	 * @throws ServiceException
	 */
	public void modifyCoterieInfo(CoterieInfo info) throws ServiceException;
	
	/**
	 * 更新私圈主达人状态
	 * @param custId  圈主用户ID
	 * @param isExpert 是否达人，0否，1是
	 * @throws ServiceException
	 */
	public void modifyCoterieExpert(String custId, Byte isExpert) throws ServiceException;
	
	/**
	 * 申请创建私圈
	 * @param info
	 * @return 
	 * @throws ServiceException
	 */
	public Response<CoterieInfo> applyCreate(CoterieBasicInfo info) throws ServiceException;
	
	/**
	 * 我创建的私圈
	 * @param custId
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyCreateCoterie(String custId) throws ServiceException;

	/**
	 * 我创建的私圈
	 * @param custId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyCreateCoterie(String custId, Integer pageNum, Integer pageSize) throws ServiceException;

	/**
	 * 我创建的私圈  数量
	 * @param custId
	 * @return
	 * @throws ServiceException
	 */
	public Response<Integer> getMyCreateCoterieCount(String custId) throws ServiceException;

	/**
	 * 我创建的私圈
	 * @param custId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyCreateCoterie(String custId, Integer pageNum, Integer pageSize, Integer status) throws ServiceException;

	/**
	 * 我创建的私圈  数量
	 * @param custId
	 * @return
	 * @throws ServiceException
	 */
	public Response<Integer> getMyCreateCoterieCount(String custId, Integer status) throws ServiceException;

	/**
	 * 我加入的私圈
	 * @param custId
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId) throws ServiceException;

	/**
	 * 我加入的私圈
	 * @param custId
	 * @param pageNum
	 * @param pageSize
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId, Integer pageNum, Integer pageSize) throws ServiceException;

	/**
	 * 我加入的私圈 数量
	 * @param custId
	 * @return
	 * @throws ServiceException
	 */
	public Response<Integer> getMyJoinCoterieCount(String custId) throws ServiceException;

	/**
	 * 私圈数量
	 * @param circleId null表示不需要此条件
	 * @param status null表示不需要此条件
	 * @return 私圈数量
	 * @throws ServiceException
	 */
	public Response<Integer> getCoterieCount(String circleId, Byte status) throws ServiceException;

	/**
	 * 平台首页 推荐3个私圈
	 * @param custId
	 * @return
	 */
	public Response<List<CoterieInfo>> getRecommendCoterie(String custId);

	/**
	 * 查询已上架的私圈列表
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryPageForApp(Integer pageNum, Integer pageSize) throws ServiceException;

	/**
	 * 圈子首页 推荐
	 * @param
	 * @return
	 */
	public Response<List<CoterieInfo>> getRecommendCoterieForCircle();
	
	/**
	 * 根据名称模糊所属私圈
	 * @param circleId  可以为null
	 * @param name
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public Response<List<CoterieInfo>> getCoterieLikeName(String circleId, String name, Integer start, Integer pageSize);
	
	/**
	 * 查询我创建了私圈的圈子ID集合
	 * @param ownerId
	 * @return
	 */
	public Response<List<String>> getCircleIdList(String ownerId);
	
	/**
	 * 我的圈子数：创建的和参加的私圈数总和
	 * @param custId
	 * @return
	 */
	public Response<Integer> getMyCoterieCount(String custId);
	
	/**
	 * 保存私圈审核记录
	 * @param info
	 */
	void saveAuditRecord(CoterieAuditInfo info);
	/**
	 * @Override
	 * @Title: regroupQr
	 * @Description: 重新组装二维码
	 * @author
	 * @param @param info
	 * @throws
	 */
	public Response<String> regroupQr(CoterieInfo info);
}
