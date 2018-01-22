package com.yryz.quanhu.message.im.api;

import com.yryz.common.response.Response;
import com.yryz.quanhu.message.im.entity.BlackAndMuteListVo;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.message.im.entity.ImUser;
import com.yryz.quanhu.message.im.entity.TeamModel;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
public interface ImAPI {

    /**
     * IM用户相关操作
     */

    /**
     * 添加用户
     *
     * @param imUser
     * @return
     */
    Response<Boolean> addUser(ImUser imUser);

    /**
     * 批量添加用户
     *
     * @param list
     * @return
     */
    Response<Boolean> addUserList(List<ImUser> list);

    /**
     * 更新用户信息
     *
     * @param imUser
     * @return
     */
    Response<Boolean> updateUser(ImUser imUser);

    /**
     * 批量更新用户信息
     *
     * @param
     * @return
     */

    Response<Boolean> updateUserList(List<ImUser> imUser);


    /**
     * 云信封禁用户发出下线消息，然后解封
     *
     * @param imUser
     * @return
     */
    Response<Boolean> block(ImUser imUser);


    /**
     * IM 好友相关操作
     */

    /**
     * 添加好友
     *
     * @param imRelation
     * @return
     */
    Response<Boolean> addFriend(ImRelation imRelation);


    /**
     * 批量添加好友
     *
     * @param list
     * @return
     */
    Response<Boolean> addFriendList(List<ImRelation> list);

    /**
     * 删除好友
     */
    Response<Boolean> deleteFriend(ImRelation imRelation);


    /**
     * 设置/取消黑名单
     *
     * @param imRelation
     *参数	        类型	        必须	    说明
     *userId	    String	    是	    用户帐号，最大长度32字符，必须保证一个APP内唯一
     *targetUserId	String	    是	    被加黑或加静音的帐号
     *relationType	String	    是	    本次操作的关系类型,1:黑名单操作，2:静音列表操作
     *relationValue	String	    是	    操作值，0:取消黑名单或静音，1:加入黑名单或静音
     * @return
     */
    Response<Boolean> setSpecialRelation(ImRelation imRelation);

    /**
     * 查看用户的黑名单和静音列表
     * @param imRelation
     * @return
     */
    Response<BlackAndMuteListVo> listBlackAndMuteList(ImRelation imRelation);


    /**
     * IM 群相关操作
     */
    /**
     * 创建群
     *
     * @param model
     * @return
     */
    Response<String> addTeam(TeamModel model);

    /**
     * 更新群
     *
     * @param model
     * @return
     */
    Response<Boolean> updateTeam(TeamModel model);


    /**
     * 删除群
     *
     * @param model
     * @return
     */
    Response<Boolean> deleteTeam(TeamModel model);


    /**
     * changeOwner
     *
     * @param model
     * @return
     */
    Response<Boolean> changeOwner(String tid, String owner, String newOwner);

    /**
     * 查询群信息
     */
    Response<List<TeamModel>> getTeamList(List<String> tids);

    /**
     * 拉人入群
     *
     * @param tid
     * @param owner
     * @param msg     邀请发送的文字，最大长度150字符
     * @param magree  管理后台建群时，0不需要被邀请人同意加入群，1需要被邀请人同意才可以加入群
     * @param members
     * @Description
     */
    Response<Boolean> joinTeam(String tid, String owner, String msg, Integer magree, List<String> members);


    /**
     * 更新群管理员
     *
     * @param tid
     * @param owner
     * @param members
     * @param isManager true-任命管理员 false-移除管理员
     * @Description
     */
    Response<Boolean> updateManager(String tid, String owner, List<String> members, Boolean isManager);

    /**
     * 禁言群成员
     *
     * @param tid
     * @param owner
     * @param custId
     * @param mute   1-禁言 0-解禁
     * @Description
     */
    Response<Boolean> muteTlist(String tid, String owner, String custId, Integer mute);


    /**
     * 禁言群成员
     *
     * @param tid
     * @param owner
     * @param custId
     * @param mute   true-禁言 false-解禁
     * @Description
     */
    Response<Boolean> muteTlistAll(String tid, String owner, Boolean mute);


}
