package com.yryz.quanhu.dymaic.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;

import java.util.List;
import java.util.Map;

/**
 * 动态服务
 *
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 39
 */
public interface DymaicService {

    /**
     * 发布动态
     *
     * @param dymaic
     * @return
     */
    Response<Boolean> send(Dymaic dymaic);

    /**
     * 删除动态
     *
     * @param kid
     * @return
     */
    Response<Boolean> delete(String userId, Long kid);

    /**
     * 查询动态
     *
     * @param kid
     * @return
     */
    Response<Dymaic> get(Long kid);

    /**
     * 批量查询动态
     *
     * @param ids
     * @return
     */
    Response<Map<Long, Dymaic>> get(List<Long> ids);

    /**
     * 查询个人动态
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    Response<List<DymaicVo>> getSendList(String userId, Long kid, Long limit);

    /**
     * 从数据库重建SendList到cache
     *
     * @param userId
     * @return
     */
    Response<Boolean> rebuildSendList(String userId);

    /**
     * 查询好友动态
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    Response<List<DymaicVo>> getTimeLine(String userId, Long kid, Long limit);

    /**
     * push动态到好友的TimeLine
     *
     * @param dynamic
     * @return
     */
    Response<Boolean> pushTimeLine(Dymaic dynamic);

    /**
     * 移除TimeLine中指定用户的动态
     *
     * @param userId
     * @param debarUserId
     */
    Response<Boolean> shuffleTimeLine(String userId, String debarUserId);

    /**
     * 从数据库重建timeLine到cache
     *
     * @param userId
     * @param limit
     * @return
     */
    Response<Boolean> rebuildTimeLine(String userId, Long limit);
}
