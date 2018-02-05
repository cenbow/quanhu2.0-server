package com.yryz.quanhu.coterie.coterie.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.vo.CoterieAdmin;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import java.util.List;

/**
 * 私圈service
 * @author chengyunfei
 *
 */
public interface CoterieService {
	/**
	 * 新建私圈
	 * @return
	 */
	CoterieInfo save(CoterieBasicInfo info);
	
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
	void remove(Long coterieId);
	
	/**
	 * 查询私圈
	 */
	CoterieInfo find(Long coterieId);
	
	/**
	 * 查询私圈列表
	 */
	List<CoterieInfo> findList(Coterie coterie);
	
	/**
	 * 查询私圈列表by私圈ID集合
	 */
	List<CoterieInfo> findList(List<Long> coterieIdList);
	
	/**
	 * 分页查询私圈列表  上架的私圈
	 */
	List<CoterieInfo> findPage(Integer pageNum, Integer pageSize);

	/**
	 * 我创建的私圈列表 审核中，已上线，未通过
	 * @param custId
	 * @return
	 */
	List<CoterieInfo> findMyCreateCoterie(String custId);
	
	/**
	 * 我创建的私圈   审核中，已上线的数量
	 * @param custId
	 * @return
	 */
	Integer findMyCreateCoterieCount(String custId);
	
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
	List<CoterieInfo> findMyJoinCoterie(String custId);
	
	/**
	 * 根据名称查私圈
	 * @param name
	 * @return
	 */
	List<CoterieInfo> findByName(String name);
	
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
	
	/**
	 * 获取推荐的私圈列表
	 */
	public List<CoterieInfo> getRecommendCoterieList();
	/**
	 * 成员大于50的取200个
	 * @return
	 */
	public List<CoterieInfo> getOrderByMemberNum();
	
	/**
	 *  修改私圈成员数目
	 * @param coterieId
	 * @return
	 */
	public int updateMemberNum(Long coterieId, Integer newMemberNum, Integer oldMemberNum);
	
	/**
	 * 根据创建日期查询所有私圈ID
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	List<Long> getKidByCreateDate(String startDate, String endDate);
    
	/**
	 * 查询私圈
	 * @param kidList
	 * @return
	 */
    List<Coterie> getByKids(List<Long> kidList);


	PageList<CoterieInfo> queryCoterieByPage(CoterieSearchParam param);
}
