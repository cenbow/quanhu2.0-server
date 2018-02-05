package com.yryz.quanhu.coterie.coterie.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.coterie.coterie.vo.*;

import java.util.List;

/**
 * 私圈服务
 * @author wt
 */
public interface CoterieApi {
	/**
	 * 申请创建私圈
	 * @param info
	 * @return 
	 * @throws ServiceException
	 */
	public Response<CoterieInfo> applyCreate(CoterieBasicInfo info);
	
	/**
	 * 设置私圈信息
	 * @param info  coterieId必填
	 * @throws ServiceException
	 */
	public Response<CoterieInfo> modifyCoterieInfo(CoterieInfo info);
	
	/**
	 * 我创建的私圈 
	 * @param custId
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyCreateCoterie(String custId);
	
	/**
	 * 我加入的私圈 审核通过，上架，未删除的私圈
	 * @param custId
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId);

	/**
	 * 我加入的私圈 审核通过，上架，未删除的私圈
	 */
	public Response<List<CoterieInfo>> getMyJoinCoterie(String custId, Integer pageNum, Integer pageSize);

	/**
	 * 我加入的私圈数量  审核通过，上架，未删除的私圈
	 * @param custId
	 * @return
	 * @throws ServiceException
	 */
	public Response<Integer> getMyJoinCoterieCount(String custId);
	
	/**
	 * 根据kid查询私圈信息 
	 * @param
	 * @return
	 * @throws ServiceException
	 */
	public Response<CoterieInfo> queryCoterieInfo(Long coterieId);
	
	/**
	 * 批量查询私圈信息 按热度排序
	 * @param coterieIdList 私圈ID集合
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryListByCoterieIdList(List<Long> coterieIdList);
	
	/**
	 * 更多私圈 热门私圈
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryHotCoterieList(Integer pageNum, Integer pageSize);
	
	/**
	 * 分页获取私圈列表
	 * @param pageNum 当前页，最小为1
	 * @param pageSize 每页显示多少条
	 * @return
	 * @throws ServiceException
	 */
	public Response<List<CoterieInfo>> queryPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 更新私圈主达人状态
	 * @param custId  圈主用户ID
	 * @param isExpert 是否达人，0否，1是
	 * @throws ServiceException
	 */
	public void modifyCoterieExpert(String custId, Byte isExpert);
	
	/**
	 * 我的圈子数：创建的和参加的私圈数总和
	 * @param custId
	 * @return
	 */
	public Response<Integer> getMyCoterieCount(String custId);
	
	/**
	 * @Override
	 * @Title: regroupQr
	 * @Description: 重新组装二维码
	 * @author
	 * @param @param info
	 * @throws
	 */
	public Response<String> regroupQr(Long coterieId);
	
	/**
	 * 根据创建日期查询所有私圈ID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	Response<List<Long>> getKidByCreateDate(String startDate, String endDate);
    
	/**
	 * 查询私圈
	 * @param kidList
	 * @return
	 */
	Response<List<Coterie>> getByKids(List<Long> kidList);
}
