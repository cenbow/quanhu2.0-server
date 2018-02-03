package com.yryz.quanhu.behavior.comment.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.comment.dao.CommentDao;
import com.yryz.quanhu.behavior.comment.dto.CommentDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentFrontDTO;
import com.yryz.quanhu.behavior.comment.dto.CommentSubDTO;
import com.yryz.quanhu.behavior.comment.entity.Comment;
import com.yryz.quanhu.behavior.comment.service.CommentService;
import com.yryz.quanhu.behavior.comment.vo.CommentInfoVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVO;
import com.yryz.quanhu.behavior.comment.vo.CommentVOForAdmin;
import com.yryz.quanhu.behavior.count.api.CountFlagApi;
import com.yryz.quanhu.grow.entity.GrowFlowQuery;
import com.yryz.quanhu.message.push.api.PushAPI;
import com.yryz.quanhu.message.push.entity.PushReqVo;
import com.yryz.quanhu.resource.hotspot.api.HotSpotApi;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.service.EventAcountApiService;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.GrowFlowReportVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 18:47 2018/1/23
 */
@Service
public class CommentServiceImpl implements CommentService {

    private static final Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private CommentDao commentDao;

    @Reference(check = false)
    private UserApi userApi;

    @Reference(check = false)
    private CountFlagApi countFlagApi;

    @Reference(check = false)
    private PushAPI pushAPI;

    @Reference(check = false)
    private EventAPI eventAPI;

    @Reference(check = false)
    private HotSpotApi hotSpotApi;

    @Reference(check = false)
    private EventAcountApiService eventAcountApiService;

    @Override
    public int accretion(Comment comment) {
        int count=commentDao.accretion(comment);
        if(count>0){
            UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
            if (null!=userSimpleVO){
                this.sendMessage(comment.getTargetUserId(),"用户"+userSimpleVO.getUserNickName()+"评论了你!");
            }
        }
        try{
            GrowFlowQuery gfq=new GrowFlowQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            gfq.setStartTime(sdf.format(startOfTodDay()));
            gfq.setEndTime(sdf.format(endOfTodDay()));

            List<EventInfo> eventInfos=new ArrayList<EventInfo>();
            //被评论者得成长值
            EventInfo eventInfo=new EventInfo();
            eventInfo.setOwnerId(comment.getCreateUserId().toString());
            eventInfo.setUserId(String.valueOf(comment.getTargetUserId()));
            eventInfo.setEventGrow(String.valueOf(2));
            eventInfo.setCreateTime(String.valueOf(new Date()));
            eventInfo.setResourceId(String.valueOf(comment.getResourceId()));
            eventInfo.setEventCode(String.valueOf(14));
            eventInfo.setEventNum(1);
            eventInfo.setAmount(0.0);
            eventInfo.setCoterieId(String.valueOf(comment.getCoterieId()));
            eventInfo.setCircleId("");
            gfq.setEventCode("14");
            gfq.setUserId(String.valueOf(comment.getTargetUserId()));
            List<GrowFlowReportVo> growFlowQueries=eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount=0;
            for(GrowFlowReportVo growFlowReportVo:growFlowQueries){
                currentCount+=growFlowReportVo.getNewGrow();
            }
            if(currentCount<10) {
                eventInfos.add(eventInfo);
            }
            //评论者
            EventInfo eventInfo_=new EventInfo();
            eventInfo_.setOwnerId(String.valueOf(comment.getTargetUserId()));
            eventInfo_.setUserId(comment.getCreateUserId().toString());
            eventInfo_.setEventGrow(String.valueOf(5));
            eventInfo_.setCreateTime(String.valueOf(new Date()));
            eventInfo_.setResourceId(String.valueOf(comment.getResourceId()));
            eventInfo_.setEventCode(String.valueOf(5));
            eventInfo_.setEventNum(1);
            eventInfo_.setAmount(0.0);
            eventInfo_.setCoterieId(String.valueOf(comment.getCoterieId()));
            eventInfo_.setCircleId("");

            gfq.setEventCode("5");
            gfq.setUserId(String.valueOf(comment.getCreateUserId()));
            List<GrowFlowReportVo> growFlowQueries_=eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount_=0;
            for(GrowFlowReportVo growFlowReportVo:growFlowQueries_){
                currentCount_+=growFlowReportVo.getNewGrow();
            }
            if(currentCount_<10){
                eventInfos.add(eventInfo_);
            }
            try{
                hotSpotApi.saveHeat("1",String.valueOf(comment.getResourceId()));
                hotSpotApi.saveHeat("2",String.valueOf(comment.getCreateUserId()));
            }catch (Exception e){
                logger.info("评论接入热度值出现异常:"+e);
            }
            eventAPI.commit(eventInfos);
        }catch (Exception e){
            logger.info("评论接入积分系统出现异常:"+e);
        }
        return count;
    }

    /**
     * 当天的开始时间
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date=calendar.getTime();
        return date.getTime();
    }
    /**
     * 当天的结束时间
     * @return
     */
    public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date=calendar.getTime();
        return date.getTime();
    }

    @Override
    public Comment querySingleComment(Comment comment) {
        return commentDao.querySingleComment(comment);
    }

    @Override
    public int delComment(Comment comment) {
        return commentDao.delComment(comment);
    }

    @Override
    public PageList<CommentVO> queryComments(CommentFrontDTO commentFrontDTO) {
        Page<Comment> page = PageHelper.startPage(commentFrontDTO.getCurrentPage().intValue(), commentFrontDTO.getPageSize().intValue());
        PageList pageList = new PageList();
        List<CommentVO> commentVOS = commentDao.queryComments(commentFrontDTO);
        pageList.setCurrentPage(commentFrontDTO.getCurrentPage());
        pageList.setPageSize(commentFrontDTO.getPageSize());
        List<CommentVO> commentVOS_ = null;
        List<Comment> commentsnew = null;
        for (CommentVO commentVO : commentVOS) {
            CommentFrontDTO commentFrontDTOnew = new CommentFrontDTO();
            commentFrontDTOnew.setTopId(commentVO.getKid());
            commentFrontDTOnew.setResourceId(commentVO.getResourceId());
            commentVOS_ = commentDao.queryComments(commentFrontDTOnew);
            commentsnew = new ArrayList<Comment>();
            int i = 0;
            Map<String,Object> map=new HashMap<String, Object>();
            map.put("resourceId",commentVO.getResourceId());
            map.put("userId",commentVO.getCreateUserId());
            map.put("moduleEnum",commentVO.getModuleEnum());
            for (CommentVO commentVOsnew : commentVOS_) {
                i++;
                Comment comment = new Comment();
                comment.setId(commentVOsnew.getId());
                comment.setKid(commentVOsnew.getKid());
                comment.setTopId(commentVOsnew.getTopId());
                comment.setParentId(commentVOsnew.getParentId());
                comment.setParentUserId(commentVOsnew.getParentUserId());
                comment.setModuleEnum(commentVOsnew.getModuleEnum());
                comment.setTargetUserId(commentVOsnew.getTargetUserId());
                UserSimpleVO userSimpleVO = this.getUserSimple(commentVOsnew.getTargetUserId());
                if (null != userSimpleVO) {
                    comment.setTargetUserNickName(userSimpleVO.getUserNickName());
                } else {
                    comment.setTargetUserNickName("");
                }
                comment.setDelFlag(commentVOsnew.getDelFlag());
                comment.setRecommend(commentVOsnew.getRecommend());
                comment.setCreateUserId(commentVOsnew.getCreateUserId());
                comment.setCreateDate(commentVOsnew.getCreateDate());
                comment.setTenantId(commentVOsnew.getTenantId());
                comment.setRevision(commentVOsnew.getRevision());
                comment.setNickName(commentVOsnew.getNickName());
                comment.setResourceId(commentVOsnew.getResourceId());
                comment.setCoterieId(commentVOsnew.getCoterieId());
                comment.setContentComment(commentVOsnew.getContentComment());
                comment.setUserImg(commentVOsnew.getUserImg());
                comment.setLastUpdateUserId(commentVOsnew.getLastUpdateUserId());
                comment.setLastUpdateDate(commentVOsnew.getLastUpdateDate());
                commentsnew.add(comment);
                if (i >= 3) {
                    break;
                }
            }

            Map<String,Long> maps=null;
            try{
                maps= countFlagApi.getAllCountFlag("11",commentVO.getResourceId(),"",map).getData();
            }catch (Exception e){
                logger.info("调用统计信息失败:" + e);
            }
            commentVO.setLikeCount(maps.get("likeCount").intValue());
            commentVO.setLikeFlag(maps.get("likeFlag").byteValue());
            commentVO.setCommentCount(commentVOS_.size());
            commentVO.setChildrenComments(commentsnew);
        }
        pageList.setCount(Long.valueOf(commentVOS.size()));
        pageList.setEntities(commentVOS);
        return pageList;
    }

    @Override
    public int updownBatch(List<Comment> comments) {
        int count=commentDao.updownBatch(comments);
        if(count>0){
            if(comments.size()>0){
                for(Comment comment:comments){
                    UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
                    if (null!=userSimpleVO){
                        this.sendMessage(comment.getTargetUserId(),"您的评论有违纪嫌疑,已被管理员下架!");
                    }
                }
            }
        }
        return count;
    }

    @Override
    public PageList<CommentVOForAdmin> queryCommentForAdmin(CommentDTO commentDTO) {
        PageHelper.startPage(commentDTO.getCurrentPage().intValue(), commentDTO.getPageSize().intValue());
        List<CommentVOForAdmin> commentVOForAdmins = commentDao.queryCommentForAdmin(commentDTO);
        PageList pageList = new PageList();
        pageList.setCurrentPage(commentDTO.getCurrentPage());
        pageList.setPageSize(commentDTO.getPageSize());
        pageList.setEntities(commentVOForAdmins);
        pageList.setCount(100L);
        return pageList;
    }

    @Override
    public CommentInfoVO querySingleCommentInfo(CommentSubDTO commentSubDTO) {
        return commentDao.querySingleCommentInfo(commentSubDTO);
    }

    @Override
    public PageList<CommentVO> querySubCommentsInfo(CommentSubDTO commentSubDTO) {
        CommentSubDTO  commentSubDTO_=new CommentSubDTO();
        commentSubDTO_.setKid(commentSubDTO.getKid());
        CommentInfoVO commentInfoVO=commentDao.querySingleCommentInfo(commentSubDTO_);
        Page<Comment> page = PageHelper.startPage(commentSubDTO.getCurrentPage().intValue(), commentSubDTO.getPageSize().intValue());
        CommentFrontDTO commentFrontDTO = new CommentFrontDTO();
        commentFrontDTO.setCurrentPage(commentSubDTO.getCurrentPage());
        commentFrontDTO.setPageSize(commentSubDTO.getPageSize());
        commentFrontDTO.setTopId(commentSubDTO.getKid());
        if(null!=commentInfoVO){
            commentFrontDTO.setResourceId(commentInfoVO.getResourceId());
        }
        PageList pageList = new PageList();
        pageList.setCurrentPage(commentSubDTO.getCurrentPage());
        pageList.setPageSize(commentSubDTO.getPageSize());
        List<CommentVO> commentVOS = commentDao.queryComments(commentFrontDTO);
        for (CommentVO commentVO : commentVOS) {
            UserSimpleVO userSimpleVO = this.getUserSimple(commentVO.getTargetUserId());
            if(null!=userSimpleVO){
                commentVO.setTargetUserNickName(userSimpleVO.getUserNickName());
            }
        }
        pageList.setEntities(commentVOS);
        pageList.setCount(Long.valueOf(commentVOS.size()));
        return pageList;
    }

    @Override
    public int updownSingle(Comment comment) {
        int count = commentDao.updownSingle(comment);
        if(count>0){
            this.sendMessage(comment.getTargetUserId(),"您的评论有违纪嫌疑,已被管理员下架!");
        }
        return count;
    }

    public UserSimpleVO getUserSimple(Long userId) {
        UserSimpleVO userSimpleVO = null;
        try {
            userSimpleVO = userApi.getUserSimple(userId).getData();
        } catch (Exception e) {
            logger.info("调用用户信息失败:" + e);
        }
        return userSimpleVO;
    }


    public void sendMessage(Long targetUserId,String msg){
        PushReqVo pushReqVo=new PushReqVo();
        List<String> strUserId=new ArrayList<String>();
        strUserId.add(String.valueOf(targetUserId));
        pushReqVo.setCustIds(strUserId);
        pushReqVo.setMsg(msg);
        pushReqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
        List<String> registrationIds=new ArrayList<String>();
        registrationIds.add(String.valueOf(PushReqVo.CommonPushType.BY_REGISTRATIONID));
        pushReqVo.setRegistrationIds(registrationIds);
        try{
            pushAPI.commonSendAlias(pushReqVo);
        }catch (Exception e){
            logger.info("调用极光推送失败:" + e);
        }
    }
}
