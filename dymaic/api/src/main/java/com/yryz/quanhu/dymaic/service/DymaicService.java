package com.yryz.quanhu.dymaic.service;

import com.yryz.common.response.Response;
import com.yryz.quanhu.dymaic.vo.Dymaic;
import com.yryz.quanhu.dymaic.vo.DymaicVo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 动态服务
 *
 * @author xiepeng
 * @version 1.0
 * @data 2018/1/19 0019 39
 */
public interface DymaicService {

    /**
     * 发布动态信息
     *
     * @param dymaic
     * @return
     */
    Response<Boolean> send(Dymaic dymaic);

    /**
     * 删除动态信息
     *
     * @param kid
     * @return
     */
    Response<Boolean> delete(Long userId, Long kid);

    /**
     * 上下架动态信息
     *
     * @param userId
     * @param kid
     * @param shelve
     * @return
     */
    Response<Boolean> shelve(Long userId, Long kid, Boolean shelve);

    /**
     * 查询动态信息
     *
     * @param kid
     * @return
     */
    Response<Dymaic> get(Long kid);

    /**
     * 批量查询动态信息
     *
     * @param kids
     * @return
     */
    Response<Map<Long, Dymaic>> get(List<Long> kids);

    /**
     * 添加置顶动态
     *
     * @param userId
     * @param dymaicId
     */
    Response<Boolean> addTopDymaic(Long userId, Long dymaicId);

    /**
     * 查询置顶动态
     *
     * @param userId
     * @return
     */
    Response<DymaicVo> getTopDymaic(Long userId);

    /**
     * 删除置顶动态
     *
     * @param userId
     * @param dymaicId
     * @return
     */
    Response<Boolean> deleteTopDymaic(Long userId, Long dymaicId);

    /**
     * 批量查询用户最后更新动态
     *
     * @param userIds
     * @return
     */
    Response<Map<Long, Dymaic>> getLastSend(Set<Long> userIds);

    /**
     * 查询个人动态列表
     *
     * @param sourceUserId
     * @param targetUserId
     * @param kid
     * @param limit
     * @return
     */
    Response<List<DymaicVo>> getSendList(Long sourceUserId, Long targetUserId, Long kid, Long limit);

    /**
     * 重建个人动态列表Cache
     *
     * @param userId
     * @return
     */
    Response<Boolean> rebuildSendList(Long userId);

    /**
     * 查询好友动态列表
     *
     * @param userId
     * @param kid
     * @param limit
     * @return
     */
    Response<List<DymaicVo>> getTimeLine(Long userId, Long kid, Long limit);

    /**
     * push动态到好友的TimeLine
     *
     * @param dymaic
     * @return
     */
    Response<Boolean> pushTimeLine(Dymaic dymaic);

    /**
     * 移除TimeLine中指定用户的动态
     *
     * @param userId
     * @param debarUserId
     */
    Response<Boolean> shuffleTimeLine(Long userId, Long debarUserId);

    /**
     * 从数据库重建timeLine到cache
     *
     * @param userId
     * @param limit
     * @return
     */
    Response<Boolean> rebuildTimeLine(Long userId, Long limit);
}
