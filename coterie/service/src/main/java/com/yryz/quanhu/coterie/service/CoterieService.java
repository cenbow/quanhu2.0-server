package com.yryz.quanhu.coterie.service;

import java.util.List;

import com.yryz.quanhu.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.vo.CoterieBaseInfo;
import com.yryz.quanhu.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.entity.CoterieAuditRecord;

/**
 * 私圈service
 * @author jk
 *
 */
public interface CoterieService {
	/**
	 * 新建私圈
	 * @return 
	 */
	CoterieInfo save(CoterieBaseInfo info);
	
	/**
	 * 编辑私圈
	 */
	void modify(CoterieInfo info);
	
	/**
	 * 更新圈主的达人身份
	 */
	void modifyCoterieExpert(String custId, Byte isExpert);
	
	/**
	 * 删除私圈
	 */
	void remove(String coterieId);
	
	/**
	 * 查询私圈
	 */
	CoterieInfo find(String coterieId);
	
	/**
	 * 按状态查询私圈列表
	 */
	List<CoterieInfo> findList(Integer status);
	
	/**
	 * 查询私圈列表
	 */
	List<CoterieInfo> findList(Coterie coterie);
	
	/**
	 * 查询私圈列表by私圈ID集合
	 */
	List<CoterieInfo> findList(List<String> coterieIdList);
	
	/**
	 * 分页查询私圈列表
	 */
	List<CoterieInfo> findPage(String circleId, Integer pageNum, Integer pageSize, Byte status);
	
	/**
	 * 分页查询私圈列表  上架的私圈
	 */
	List<CoterieInfo> findPage(Integer pageNum, Integer pageSize);
	
	/**
	 * 我创建的私圈列表
	 * @param custId
	 * @return
	 */
	List<CoterieInfo> findMyCreateCoterie(String custId, String circleId);
	
	/**
	 * 我创建的私圈列表
	 * @param custId
	 * @return
	 */
	List<CoterieInfo> findMyCreateCoterie(String custId, Integer pageNum, Integer pageSize, Integer status);
	
	/**
	 * 我创建的私圈   数量
	 * @param custId
	 * @return
	 */
	Integer findMyCreateCoterieCount(String custId, Integer status);
	
	/**
	 * 我参与的私圈列表
	 * @param custId
	 * @return
	 */
	List<CoterieInfo> findMyJoinCoterie(String custId, Integer pageNum, Integer pageSize);
	
	/**
	 * 我参与的私圈  数量
	 * @param custId
	 * @return
	 */
	Integer findMyJoinCoterieCount(String custId);
	
	/**
	 * 我参与的私圈列表
	 * @param custId
	 * @return
	 */
	List<CoterieInfo> findMyJoinCoterie(String custId, String circleId);
	
	/**
	 * 根据名称查私圈
	 * @param name
	 * @return
	 */
	List<CoterieInfo> findByName(String name);
	
	/**
	 * 查询私圈（待审核或上架的私圈）
	 * @param custId 用户ID
	 * @param circleId 圈子ID
	 * @return
	 */
	CoterieInfo find(String custId, String circleId);
	
	/**
	 * 平台后台私圈列表
	 * @param param
	 * @return
	 */
	List<CoterieAdmin> find(CoterieSearchParam param);
	
	/**
	 * 私圈搜索总数
	 * @param param
	 * @return
	 */
	Integer findCountBySearchParam(CoterieSearchParam param);
	
	/**
	 * 保存私圈审核记录
	 */
	void saveAuditRecord(CoterieAuditRecord record);
	
	/**
	 * 查询私圈审核记录
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	List<CoterieAuditRecord> findAuditRecordList(String coterieId, Integer pageNum, Integer pageSize);
	
	/**
	 * 私圈数量
	 * @param circleId 
	 * @param status
	 * @return
	 */
	Integer getCoterieCount(String circleId, Byte status);
	
	/**
	 * 推荐私圈设置
	 * @param coterieIdList
	 */
	public void recommendCoterie(List<String> coterieIdList);
	
	/**
	 * 取消推荐私圈设置
	 * @param coterieIdList
	 */
	public void cancelRecommendCoterie(List<String> coterieIdList);
	
	/**
	 * 获取运营推荐的私圈
	 * @param circleId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<CoterieInfo> getRecommendList(String circleId, Integer start, Integer pageSize);
    
	/**
	 * 按达人和热度查询私圈
	 * @param circleId
	 * @param expert
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<CoterieInfo> getHeatList(String circleId, Byte expert, Integer start, Integer pageSize);
	
	/**
	 * 按圈子和热度查询私圈
	 * @param circleId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<Coterie> getHeatList(String circleId, Integer start, Integer pageSize);
	
	/**
	 * 按圈子和热度查询私圈
	 * @param circleId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<CoterieInfo> queryPageForApp(Integer pageNum, Integer pageSize);
	
	/**
	 * 根据名称模糊所属私圈
	 * @param name
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	public List<CoterieInfo> getCoterieLikeName(String circleId, String name, Integer start, Integer pageSize);
	
	/**
	 * 查询用户已创建私圈的  圈子ID集合
	 * @param ownerId
	 * @return
	 */
	public List<String> getCircleIdListByOwnerId(String ownerId);
}
