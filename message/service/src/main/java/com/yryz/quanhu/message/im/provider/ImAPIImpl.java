package com.yryz.quanhu.message.im.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.appinfo.AppInfo;
import com.yryz.common.appinfo.AppInfoUtils;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.im.api.ImAPI;
import com.yryz.quanhu.message.im.entity.BlackAndMuteListVo;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.message.im.entity.ImUser;
import com.yryz.quanhu.message.im.entity.TeamModel;
import com.yryz.quanhu.message.im.service.ImService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
@Service
public class ImAPIImpl implements ImAPI {

    private static final Logger logger = LoggerFactory.getLogger(ImAPIImpl.class);

    @Autowired
    private ImService imService;

    @Override
    public Response<Boolean> addUser(ImUser imUser) {
        try {
            logger.info("addUser request: {}", JSON.toJSONString(imUser));
            checkAppInfo();
            imService.addUser(imUser);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("add addUser error", e);
            return ResponseUtils.returnException(e);
        }
    }

    private void checkAppInfo() {
        AppInfo appInfo = AppInfoUtils.getAppInfo();
        if (StringUtils.isBlank(appInfo.getAppId())) {
            throw QuanhuException.busiError("请传入appId");
        }
    }

    @Override
    public Response<Boolean> addUserList(List<ImUser> list) {
        try {
            logger.info("addUserList request: {}", JSON.toJSONString(list));
            checkAppInfo();
            imService.addUserList(list);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("addUserList error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> updateUser(ImUser imUser) {
        try {
            logger.info("updateUser request: {}", JSON.toJSONString(imUser));
            checkAppInfo();
            imService.updateUser(imUser);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("updateUser error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> updateUserList(List<ImUser> list) {
        try {
            logger.info("updateUserList request: {}", JSON.toJSONString(list));
            checkAppInfo();
            imService.updateUserList(list);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("updateUserList error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> block(ImUser imUser) {
        try {
            logger.info("block request: {}", JSON.toJSONString(imUser));
            checkAppInfo();
            imService.block(imUser);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("block error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> addFriend(ImRelation imRelation) {
        try {
            logger.info("addFriend request: {}", JSON.toJSONString(imRelation));
            checkAppInfo();
            imService.addFriend(imRelation);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("addFriend error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> addFriendList(List<ImRelation> list) {
        try {
            logger.info("addFriendList request: {}", JSON.toJSONString(list));
            checkAppInfo();
            imService.addFriendList(list);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("addFriendList error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> deleteFriend(ImRelation imRelation) {
        try {
            logger.info("imRelation request: {}", JSON.toJSONString(imRelation));
            checkAppInfo();
            imService.deleteFriend(imRelation);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("deleteFriend error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> setSpecialRelation(ImRelation imRelation) {
        try {
            logger.info("setSpecialRelation request: {}", JSON.toJSONString(imRelation));
            checkAppInfo();
            imService.setSpecialRelation(imRelation);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("setSpecialRelation error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<BlackAndMuteListVo> listBlackAndMuteList(ImRelation imRelation) {
        try {
            logger.info("listBlackAndMuteList request: {}", JSON.toJSONString(imRelation));
            checkAppInfo();
            BlackAndMuteListVo blackAndMuteListVo = imService.listBlackAndMuteList(imRelation);
            logger.info("listBlackAndMuteList result: {}", JSON.toJSONString(blackAndMuteListVo));
            return ResponseUtils.returnObjectSuccess(blackAndMuteListVo);
        } catch (Exception e) {
            logger.error("setSpecialRelation error", e);
            return ResponseUtils.returnException(e);
        }
    }

    @Override
    public Response<String> addTeam(TeamModel model) {
        try {
            logger.info("addTeam request: {}", JSON.toJSONString(model));
            checkAppInfo();
            return imService.addTeam(model);
        } catch (Exception e) {
            logger.error("addTeam error", e);
            return ResponseUtils.returnException(e);
        }

    }

    @Override
    public Response<Boolean> updateTeam(TeamModel model) {
        try {
            logger.info("updateTeam request: {}", JSON.toJSONString(model));
            checkAppInfo();
            imService.updateTeam(model);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("updateTeam error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> deleteTeam(TeamModel model) {
        try {
            logger.info("deleteTeam request: {}", JSON.toJSONString(model));
            checkAppInfo();
            imService.deleteTeam(model);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("deleteTeam error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> changeOwner(String tid, String owner, String newOwner) {
        try {
            logger.info("deleteTeam request, tid:{}, owner:{}, newOwner:{}", tid, owner, newOwner);
            checkAppInfo();
            imService.changeOwner(tid, owner, newOwner);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("changeOwner error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<List<TeamModel>> getTeamList(List<String> tids) {
        try {
            logger.info("getTeamList request: {}", JSON.toJSONString(tids));
            checkAppInfo();
            List<TeamModel> teamList = imService.getTeamList(tids);
            return ResponseUtils.returnListSuccess(teamList);
        } catch (Exception e) {
            logger.error("getTeamList error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> joinTeam(String tid, String owner, String msg, Integer magree, List<String> members) {
        try {
            logger.info("getTeamList request, tid:{}, owner:{}, msg:{}, magree:{}, members:{}",
                    tid, owner, msg, magree, members);
            checkAppInfo();
            imService.joinTeam(tid, owner, msg, magree, members);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("joinTeam error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> updateManager(String tid, String owner, List<String> members, Boolean isManager) {
        try {
            logger.info("updateManager request, tid:{}, owner:{}, members:{}, isManager:{}",
                    tid, owner, members, isManager);
            checkAppInfo();
            imService.updateManager(tid, owner, members, isManager);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("updateManager error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> muteTlist(String tid, String owner, String userId, Integer mute) {
        try {
            logger.info("muteTlist request, tid:{}, owner:{}, userId:{}, mute:{}",
                    tid, owner, userId, mute);
            checkAppInfo();
            imService.muteTlist(tid, owner, userId, mute);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("muteTlist error", e);
            return ResponseUtils.returnException(e);
        }


    }

    @Override
    public Response<Boolean> muteTlistAll(String tid, String owner, Boolean mute) {
        try {
            logger.info("muteTlistAll request, tid:{}, owner:{}, mute:{}",
                    tid, owner, mute);
            checkAppInfo();
            imService.muteTlistAll(tid, owner, mute);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("muteTlistAll error", e);
            return ResponseUtils.returnException(e);
        }


    }


}
