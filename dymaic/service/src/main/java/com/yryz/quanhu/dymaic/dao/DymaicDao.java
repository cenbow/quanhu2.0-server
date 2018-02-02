package com.yryz.quanhu.dymaic.dao;

import com.yryz.quanhu.dymaic.dto.QueryDymaicDTO;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 28
 */
@Mapper
public interface DymaicDao {

    /**
     * 查询动态信息
     * @param kid
     * @return
     */
    Dymaic get(Long kid);

    /**
     * 批量查询动态
     * @param kids
     * @return
     */
    List<Dymaic> getByids(List<Long> kids);

    /**
     * 新增动态
     *
     * @param dymic
     * @return
     */
    int insert(Dymaic dymic);

    /**
     * 删除动态
     *
     * @param dymic
     * @return
     */
    int update(Dymaic dymic);

    /**
     * 删除动态
     *
     * @param kid
     * @return
     */
    int delete(Long kid);


    /**
     * 查询个人动态id列表
     * @param userId
     * @return
     */
    List<Long> getSendListIds(@Param("userId") Long userId);

    /**
     * 查询好友动态id列表
     * @param userIds
     * @param limit
     * @return
     */
    List<Long> getTimeLineIds(@Param("userIds") List<Long> userIds, @Param("limit") Long limit);

    /**
     * 查询kid最大值
     * @return
     */
    Long getMaxKid();


    /**
     * 管理后台条件查询动态列表
     * @param queryDymaicDTO
     * @return
     */
    List<Dymaic> queryAll(QueryDymaicDTO queryDymaicDTO);
}
