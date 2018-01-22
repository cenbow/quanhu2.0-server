package com.yryz.quanhu.message.im.service;

import com.yryz.common.exception.BaseException;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.message.common.im.yunxin.YunxinRelation;
import com.yryz.quanhu.message.common.im.yunxin.YunxinTeam;
import com.yryz.quanhu.message.common.im.yunxin.YunxinUser;
import com.yryz.quanhu.message.common.im.yunxin.vo.ImTeamInfoVo;
import com.yryz.quanhu.message.common.im.yunxin.vo.ImTeamSearchResponseVo;
import com.yryz.quanhu.message.im.entity.ImRelation;
import com.yryz.quanhu.message.im.entity.ImUser;
import com.yryz.quanhu.message.im.entity.TeamModel;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/1/20
 * @description
 */
@Service
public class ImService {
    private static final Logger logger = LoggerFactory.getLogger(ImService.class);

    public void addUser(ImUser imUser) {
        try {
            String userId = imUser.getUserId();
            String nick = imUser.getNick();
            String iconUrl = imUser.getIconUrl();
            YunxinUser.getInstance().addUser(userId, nick, iconUrl);
            logger.info("同步用户：" + userId + ", " + nick + ", " + iconUrl);
        } catch (Exception e) {
            logger.error("[Im addUser]", e);
            throw new BaseException("sys error");
        }
    }


    public void addUserList(List<ImUser> list) {
        if (list == null || list.size() < 1) {
            return;
        }

        for (ImUser imUser : list) {
            try {
                addUser(imUser);
            } catch (Exception e) {
                continue;
            }
        }
    }

    public void updateUser(ImUser imUser) {
        try {
            YunxinUser.getInstance().updateUser(imUser.getUserId(), imUser.getNick(), imUser.getIconUrl());
            logger.info("更新用户信息：" + imUser.getUserId() + ", " + imUser.getNick() + ", " + imUser.getIconUrl());
        } catch (Exception e) {
            logger.error("[Im modifyUser]", e);
            throw new BaseException("sys error");
        }
    }

    public void updateUserList(List<ImUser> list) {
        if (list == null || list.size() < 1) {
            return;
        }

        for (ImUser user : list) {
            try {
                updateUser(user);
            } catch (Exception e) {
                logger.error("[Im modifyUser]", e);
                throw new BaseException("sys error");
            }
        }

    }


    public void block(ImUser imUser) {
        try {
            String userId = imUser.getUserId();
            YunxinUser.getInstance().block(userId);
            YunxinUser.getInstance().unblock(userId);
            logger.info("云信封禁用户发出下线消息，然后解封 userId:{}", userId);
        } catch (Exception e) {
            logger.error("[Im user block and unblock]", e);
            throw new BaseException("sys error");
        }
    }

    public void addFriend(ImRelation imRelation) {
        String aCustId = imRelation.getUserId();
        String bCustId = imRelation.getTargetUserId();
        String nameNotes = imRelation.getNameNotes();
        if (StringUtils.isEmpty(aCustId) || StringUtils.isEmpty(bCustId)) {
            return;
        }

        try {
            YunxinRelation.getInstance().addFriend(aCustId, bCustId);
            logger.info("同步好友：" + aCustId + ", " + bCustId);
        } catch (Exception e) {
            logger.error("[Im addUser]", e);
            // throw new BaseException("sys error");
        }

        try {
            if (nameNotes != null) {
                logger.info("同步好友备注：" + aCustId + ", " + bCustId + ", " + nameNotes);
                YunxinRelation.getInstance().updateFriend(aCustId, bCustId, nameNotes);
            }
        } catch (Exception e) {
            logger.error("[Im updateFriend]", e);
            // throw new BaseException("sys error");
        }
    }

    public void addFriendList(List<ImRelation> list) {
        for (ImRelation relation : list) {
            try {
                YunxinRelation.getInstance().addFriend(relation.getUserId(), relation.getTargetUserId());
                logger.info("同步好友：" + relation.getUserId() + ", " + relation.getTargetUserId());
            } catch (Exception e) {
                logger.error("[Im addUser]", e);
                // throw new BaseException("sys error");
            }

            try {
                if (!StringUtils.isBlank(relation.getNameNotes())) {
                    logger.info("同步好友备注：" + relation.getUserId() + ", " + relation.getTargetUserId() + ", "
                            + relation.getNameNotes());
                    YunxinRelation.getInstance().updateFriend(relation.getUserId(), relation.getTargetUserId(),
                            relation.getNameNotes());
                    ;
                }
            } catch (Exception e) {
                logger.error("[Im addFriendList error]", e);
                // throw new BaseException("sys error");
            }
        }
    }

    public void deleteFriend(ImRelation imRelation) {
        String aCustId = imRelation.getUserId();
        String bCustId = imRelation.getTargetUserId();
        if (StringUtils.isEmpty(aCustId) || StringUtils.isEmpty(bCustId)) {
            return;
        }
        try {
            YunxinRelation.getInstance().deleteFriend(aCustId, bCustId);
            logger.info("删除好友：" + aCustId + ", " + bCustId);
        } catch (Exception e) {
            logger.error("[Im deleteFriend error]", e);
            // throw new BaseException("sys error");
        }
    }

    public void setSpecialRelation(ImRelation imRelation) {
        try {
            String userId = imRelation.getUserId();
            String targetUserId = imRelation.getTargetUserId();
            String relationType = imRelation.getRelationType();
            String relationValue = imRelation.getRelationValue();
            YunxinRelation.getInstance().setSpecialRelation(userId, targetUserId, relationType, relationValue);
        } catch (Exception e) {
            logger.error("Im setSpecialRelation error", e);
            // throw new BaseException("sys error");
        }

    }

    public Response<String> addTeam(TeamModel model) {
        if (model == null || StringUtils.isEmpty(model.getMsg()) || StringUtils.isEmpty(model.getOwner())
                || StringUtils.isEmpty(model.getTname())) {
            return ResponseUtils.returnCommonException("msg owner tname can not be null");
        }
        if (model.getJoinmode() < 0 || model.getJoinmode() > 2 || model.getMagree() < 0 || model.getMagree() > 1) {
            return null;
        }
        try {
            String addTeam = YunxinTeam.getInstance().addTeam(model.getTname(), model.getOwner(), model.getAnnouncement(),
                    model.getIntro(), model.getMsg(), model.getMagree(), model.getJoinmode(), model.getIcon(), model.getMembers());
            return ResponseUtils.returnObjectSuccess(addTeam);
        } catch (Exception e) {
            logger.error("[Im addTeam]", e);
            return ResponseUtils.returnException(e);
        }
    }

    public void updateTeam(TeamModel model) {
        if (model == null || StringUtils.isEmpty(model.getTid()) || StringUtils.isEmpty(model.getOwner())) {
            return;
        }

        try {
            YunxinTeam.getInstance().updateTeam(model.getTid(), model.getTname(), model.getOwner(),
                    model.getAnnouncement(), model.getIntro(), model.getIcon());
        } catch (Exception e) {
            logger.error("[Im updateTeam]", e);
            throw QuanhuException.busiError(e.getMessage());
        }
    }

    public void deleteTeam(TeamModel model) {
        String tid = model.getTid();
        String owner = model.getOwner();
        if (StringUtils.isEmpty(tid) || StringUtils.isEmpty(owner)) {
            return;
        }

        try {
            YunxinTeam.getInstance().deleteTeam(tid, owner);
        } catch (Exception e) {
            logger.error("[Im deleteTeam]", e);
            throw QuanhuException.busiError(e.getMessage());
        }
    }

    public void changeOwner(String tid, String owner, String newOwner) {
        if (StringUtils.isEmpty(tid) || StringUtils.isEmpty(owner) || StringUtils.isEmpty(newOwner)) {
            return;
        }

        try {
            YunxinTeam.getInstance().changeOwner(tid, owner, newOwner);
        } catch (Exception e) {
            logger.error("[Im changeOwner]", e);
        }
    }

    public List<TeamModel> getTeamList(List<String> tids) {
        if (tids == null || tids.isEmpty()) {
            return null;
        }
        List<TeamModel> list = new ArrayList<>();
        try {
            ImTeamSearchResponseVo responseVo = YunxinTeam.getInstance().getTeamInfo(tids);
            if (responseVo != null) {
                List<ImTeamInfoVo> infoVos = responseVo.getTinfos();
                if (infoVos != null && !infoVos.isEmpty()) {
                    for (int i = 0; i < infoVos.size(); i++) {
                        ImTeamInfoVo infoVo = infoVos.get(i);
                        TeamModel model = new TeamModel();
                        model.setAnnouncement(infoVo.getAnnuncement());
                        model.setIcon(infoVo.getIcon());
                        model.setIntro(infoVo.getIntro());
                        model.setJoinmode(infoVo.getJoinmode());
                        model.setOwner(infoVo.getOwner());
                        model.setTid(infoVo.getTid().toString());
                        model.setTname(infoVo.getTname());
                        list.add(model);
                    }
                }
            }
            return list;
        } catch (Exception e) {
            logger.error("[Im getTeam]", e);
            return null;
        }
    }

    public void joinTeam(String tid, String owner, String msg, Integer magree, List<String> members) {
        if(StringUtils.isBlank(tid) || StringUtils.isBlank(owner) || StringUtils.isBlank(msg) || CollectionUtils.isEmpty(members)){
            return ;
        }
        try {
            YunxinTeam.getInstance().joinTeam(tid, owner, msg, magree, new JSONArray(members).toString());
        } catch (Exception e) {
            logger.error("[Im joinTeam]", e);
            throw QuanhuException.busiError("Im joinTeam fail", e.getMessage());
        }
    }

    public void updateManager(String tid, String owner, List<String> members, Boolean isManager) {
        if(StringUtils.isBlank(tid) || StringUtils.isBlank(owner) || CollectionUtils.isEmpty(members)){
            return;
        }
        try {
            if(isManager){
                YunxinTeam.getInstance().addManager(tid, owner, new JSONArray(members).toString());
            }else{
                YunxinTeam.getInstance().removeManager(tid, owner, new JSONArray(members).toString());
            }
        } catch (Exception e) {
            logger.error("[Im eidtManager]", e);
            throw QuanhuException.busiError("Im eidtManager fail", e.getMessage());
        }
    }

    public void muteTlist(String tid, String owner, String custId, Integer mute) {
        if(StringUtils.isBlank(tid) || StringUtils.isBlank(owner) || StringUtils.isBlank(custId)){
            return;
        }
        try {
            YunxinTeam.getInstance().muteTlist(tid, owner, custId, mute);
        } catch (Exception e) {
            logger.error("[Im muteTlist]", e);
            throw QuanhuException.busiError("Im muteTlist fail",e.getMessage());
        }
    }

    public void muteTlistAll(String tid, String owner, Boolean mute) {
        if(StringUtils.isBlank(tid) || StringUtils.isBlank(owner) ){
            return;
        }
        try {
            YunxinTeam.getInstance().muteTlistAll(tid, owner, mute);
        } catch (Exception e) {
            logger.error("[Im muteTlistAll]", e);
            throw QuanhuException.busiError("Im muteTlistAll fail",e.getMessage());

        }
    }
}
