package com.yryz.quanhu.behavior.comment.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.response.PageList;
import com.yryz.framework.core.cache.RedisTemplateBuilder;
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
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author:sun
 * @version:2.0
 * @Description:评论
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

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplateBuilder redisTemplateBuilder;


    @Override
    public int accretion(Comment comment) {
        RedisTemplate<String, Object> redisTemplate = redisTemplateBuilder.buildRedisTemplate(Object.class);
        comment.setModuleEnum(ModuleContants.COMMENT);
        int count = commentDao.accretion(comment);
        if (count > 0) {
            Comment commentRedis = commentDao.querySingleCommentById(comment.getId());
            if (null != commentRedis) {
                try {
                    redisTemplate.opsForValue().set("COMMENT:" + ModuleContants.COMMENT+ ":" + commentRedis.getKid() + "_" + commentRedis.getTopId() + "_" + commentRedis.getResourceId(), commentRedis);
                } catch (Exception e) {
                    logger.info("同步评论数据到redis中失败" + e);
                }
            }
            UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
            if (null != userSimpleVO) {
                this.sendMessage(comment.getTargetUserId(), "用户" + userSimpleVO.getUserNickName() + "评论了你!");
            }
        }
        try {
            GrowFlowQuery gfq = new GrowFlowQuery();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            gfq.setStartTime(sdf.format(startOfTodDay()));
            gfq.setEndTime(sdf.format(endOfTodDay()));

            List<EventInfo> eventInfos = new ArrayList<EventInfo>();
            //被评论者得成长值
            EventInfo eventInfo = new EventInfo();
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
            List<GrowFlowReportVo> growFlowQueries = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries) {
                currentCount += growFlowReportVo.getNewGrow();
            }
            if (currentCount < 10) {
                eventInfos.add(eventInfo);
            }
            //评论者
            EventInfo eventInfo_ = new EventInfo();
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
            List<GrowFlowReportVo> growFlowQueries_ = eventAcountApiService.getGrowFlowAll(gfq).getData();
            int currentCount_ = 0;
            for (GrowFlowReportVo growFlowReportVo : growFlowQueries_) {
                currentCount_ += growFlowReportVo.getNewGrow();
            }
            if (currentCount_ < 10) {
                eventInfos.add(eventInfo_);
            }
            try {
                hotSpotApi.saveHeat("1", String.valueOf(comment.getResourceId()));
                hotSpotApi.saveHeat("2", String.valueOf(comment.getCreateUserId()));
            } catch (Exception e) {
                logger.info("评论接入热度值出现异常:" + e);
            }
            eventAPI.commit(eventInfos);
        } catch (Exception e) {
            logger.info("评论接入积分系统出现异常:" + e);
        }
        return count;
    }

    /**
     * 当天的开始时间
     *
     * @return
     */
    public static long startOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date date = calendar.getTime();
        return date.getTime();
    }

    /**
     * 当天的结束时间
     *
     * @return
     */
    public static long endOfTodDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        Date date = calendar.getTime();
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
        PageHelper.startPage(commentFrontDTO.getCurrentPage().intValue(), commentFrontDTO.getPageSize().intValue());
        PageList pageList = new PageList();
        Set<String> setKeys = null;
        if (null != stringRedisTemplate) {
            setKeys = stringRedisTemplate.keys("COMMENT:" + ModuleContants.COMMENT + ":*_0_" + commentFrontDTO.getResourceId());
            logger.info("先走redis查值1");
        }
        List<CommentVO> commentVOS = null;
        if (setKeys.size() <= 0) {
            commentVOS = commentDao.queryComments(commentFrontDTO);
            logger.info("redis查不到值，走数据库2");
        } else {
            logger.info("redis查到了,走redis3");
            commentVOS = new ArrayList<CommentVO>();
            for (String strKey : setKeys) {
                String stringRedis = stringRedisTemplate.opsForValue().get(strKey);
                CommentVO commentVO = new CommentVO();
                JSONObject obj = new JSONObject().fromObject(stringRedis);
                Comment comment = (Comment) JSONObject.toBean(obj, Comment.class);
                commentVO.setContentComment(comment.getContentComment());
                commentVO.setParentId(comment.getParentId());
                commentVO.setTopId(comment.getTopId());
                commentVO.setModuleEnum(comment.getModuleEnum());
                commentVO.setCreateUserId(comment.getCreateUserId());
                commentVO.setKid(comment.getKid());
                commentVO.setCoterieId(comment.getCoterieId());
                commentVO.setDelFlag(comment.getDelFlag());
                commentVO.setKids(comment.getKids());
                commentVO.setLikeCount(comment.getLikeCount());
                commentVO.setLikeFlag(comment.getLikeFlag());
                commentVO.setNickName(comment.getNickName());
                commentVO.setParentUserId(comment.getParentUserId());
                commentVO.setRecommend(comment.getRecommend());
                commentVO.setResourceId(comment.getResourceId());
                commentVO.setRevision(comment.getRevision());
                commentVO.setShelveFlag(comment.getShelveFlag());
                commentVO.setTargetUserId(comment.getTargetUserId());
                commentVO.setTargetUserNickName(comment.getTargetUserNickName());
                commentVO.setTenantId(comment.getTenantId());
                commentVO.setUserImg(comment.getUserImg());
                commentVO.setCreateDate(comment.getCreateDate());
                commentVO.setId(comment.getId());
                commentVO.setLastUpdateDate(comment.getLastUpdateDate());
                commentVO.setLastUpdateUserId(comment.getLastUpdateUserId());
                commentVOS.add(commentVO);
            }
        }
        List<CommentVO> commentVOS_ = new ArrayList<CommentVO>();//null
        List<Comment> commentsnew = null;
        for (CommentVO commentVO : commentVOS) {
            CommentFrontDTO commentFrontDTOnew = new CommentFrontDTO();
            commentFrontDTOnew.setTopId(commentVO.getKid());
            commentFrontDTOnew.setResourceId(commentVO.getResourceId());
            Set<String> setSubKey = null;
            if (null != stringRedisTemplate) {
                setSubKey = stringRedisTemplate.keys("COMMENT:*:*_" + commentVO.getKid() + "_" + commentVO.getResourceId());
                logger.info("先走redis查4");
            }
            if (setSubKey.size() <= 0) {
                commentVOS_ = commentDao.queryComments(commentFrontDTOnew);
                logger.info("redis查不到走数据库5"+"size:"+setSubKey.size() );
            } else {
                logger.info("redis查到了走redis6");
                for (String strSub : setSubKey) {
                    String onekeyStr = stringRedisTemplate.opsForValue().get(strSub);
                    if (null != onekeyStr && !onekeyStr.equals("")) {
                        CommentVO commentVO_ = new CommentVO();
                        JSONObject obj = new JSONObject().fromObject(onekeyStr);
                        Comment comment = (Comment) JSONObject.toBean(obj, Comment.class);
                        commentVO_.setContentComment(comment.getContentComment());
                        commentVO_.setParentId(comment.getParentId());
                        commentVO_.setTopId(comment.getTopId());
                        commentVO_.setModuleEnum(comment.getModuleEnum());
                        commentVO_.setCreateUserId(comment.getCreateUserId());
                        commentVO_.setKid(comment.getKid());
                        commentVO_.setCoterieId(comment.getCoterieId());
                        commentVO_.setDelFlag(comment.getDelFlag());
                        commentVO_.setKids(comment.getKids());
                        commentVO_.setLikeCount(comment.getLikeCount());
                        commentVO_.setLikeFlag(comment.getLikeFlag());
                        commentVO_.setNickName(comment.getNickName());
                        commentVO_.setParentUserId(comment.getParentUserId());
                        commentVO_.setRecommend(comment.getRecommend());
                        commentVO_.setResourceId(comment.getResourceId());
                        commentVO_.setRevision(comment.getRevision());
                        commentVO_.setShelveFlag(comment.getShelveFlag());
                        commentVO_.setTargetUserId(comment.getTargetUserId());
                        commentVO_.setTargetUserNickName(comment.getTargetUserNickName());
                        commentVO_.setTenantId(comment.getTenantId());
                        commentVO_.setUserImg(comment.getUserImg());
                        commentVO_.setCreateDate(comment.getCreateDate());
                        commentVO_.setId(comment.getId());
                        commentVO_.setLastUpdateDate(comment.getLastUpdateDate());
                        commentVO_.setLastUpdateUserId(comment.getLastUpdateUserId());
                        if (null != commentVO_) {
                            commentVOS_.add(commentVO_);
                        }

                    }
                }
            }
            commentsnew = new ArrayList<Comment>();
            int i = 0;
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("resourceId", commentVO.getResourceId());
            map.put("userId", commentVO.getCreateUserId());
            map.put("moduleEnum", commentVO.getModuleEnum());
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

            Map<String, Long> maps = null;
            try {
                maps = countFlagApi.getAllCountFlag("11", commentVO.getResourceId(), "", map).getData();
            } catch (Exception e) {
                logger.info("调用统计信息失败:" + e);
            }
            commentVO.setLikeCount(maps.get("likeCount").intValue());
            commentVO.setLikeFlag(maps.get("likeFlag").byteValue());
            commentVO.setCommentCount(commentVOS_.size());
            commentVO.setChildrenComments(commentsnew);
        }
        pageList.setCurrentPage(commentFrontDTO.getCurrentPage());
        pageList.setPageSize(commentFrontDTO.getPageSize());
        pageList.setCount(Long.valueOf(commentVOS.size()));
        /*用于redis分页*/
        if (setKeys.size() > 0) {
            int page = commentFrontDTO.getCurrentPage();//相当于pageNo
            int count = commentFrontDTO.getPageSize();//相当于pageSize
            int size = commentVOS.size();
            int pageCount = size / count;
            int fromIndex = count * (page - 1);
            int toIndex = fromIndex + count;
            if (toIndex >= size) {
                toIndex = size;
            }
            if (page > pageCount + 1) {
                fromIndex = 0;
                toIndex = 0;
            }
            logger.info("走redis分页");
            pageList.setEntities(commentVOS.subList(fromIndex, toIndex));
        } else {
            logger.info("走原始分页");
            pageList.setEntities(commentVOS);
        }
        return pageList;
    }

    @Override
    public int updownBatch(List<Comment> comments) {
        int count = commentDao.updownBatch(comments);
        if (count > 0) {
            if (comments.size() > 0) {
                for (Comment comment : comments) {
                    UserSimpleVO userSimpleVO = userApi.getUserSimple(comment.getCreateUserId()).getData();
                    if (null != userSimpleVO) {
                        this.sendMessage(comment.getTargetUserId(), "您的评论有违纪嫌疑,已被管理员下架!");
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
        pageList.setCount(commentDao.queryCommentForAdminCount(commentDTO));//需要做个统计计数查询
        return pageList;
    }

    @Override
    public CommentInfoVO querySingleCommentInfo(CommentSubDTO commentSubDTO) {
        CommentInfoVO commentInfoVO = new CommentInfoVO();
        Set<String> strKey = stringRedisTemplate.keys("COMMENT:"+ModuleContants.COMMENT+":" + commentSubDTO.getKid() + "_0_"+commentSubDTO.getResourceId());
        if (strKey.size() > 0) {
            logger.info("评论详情走redis1");
            for (String str : strKey) {
                String commentRedisSingleStr = stringRedisTemplate.opsForValue().get(str);
                JSONObject obj = new JSONObject().fromObject(commentRedisSingleStr);
                Comment comment = (Comment) JSONObject.toBean(obj, Comment.class);
                commentInfoVO.setContentComment(comment.getContentComment());
                commentInfoVO.setParentId(comment.getParentId());
                commentInfoVO.setTopId(comment.getTopId());
                commentInfoVO.setModuleEnum(comment.getModuleEnum());
                commentInfoVO.setCreateUserId(comment.getCreateUserId());
                commentInfoVO.setKid(comment.getKid());
                commentInfoVO.setCoterieId(comment.getCoterieId());
                commentInfoVO.setDelFlag(comment.getDelFlag());
                commentInfoVO.setKids(comment.getKids());
                commentInfoVO.setLikeCount(comment.getLikeCount());
                commentInfoVO.setLikeFlag(comment.getLikeFlag());
                commentInfoVO.setNickName(comment.getNickName());
                commentInfoVO.setParentUserId(comment.getParentUserId());
                commentInfoVO.setRecommend(comment.getRecommend());
                commentInfoVO.setResourceId(comment.getResourceId());
                commentInfoVO.setRevision(comment.getRevision());
                commentInfoVO.setShelveFlag(comment.getShelveFlag());
                commentInfoVO.setTargetUserId(comment.getTargetUserId());
                commentInfoVO.setTargetUserNickName(comment.getTargetUserNickName());
                commentInfoVO.setTenantId(comment.getTenantId());
                commentInfoVO.setUserImg(comment.getUserImg());
                commentInfoVO.setCreateDate(comment.getCreateDate());
                commentInfoVO.setId(comment.getId());
                commentInfoVO.setLastUpdateDate(comment.getLastUpdateDate());
                commentInfoVO.setLastUpdateUserId(comment.getLastUpdateUserId());
                commentInfoVO.setCommentEnties(null);
            }
        } else {
            logger.info("评论详情走数据库2");
            commentInfoVO = commentDao.querySingleCommentInfo(commentSubDTO);
        }
        return commentInfoVO;
    }

    @Override
    public PageList<CommentVO> querySubCommentsInfo(CommentSubDTO commentSubDTO) {
        CommentSubDTO commentSubDTO_ = new CommentSubDTO();
        commentSubDTO_.setKid(commentSubDTO.getKid());
        CommentInfoVO commentInfoVO = new CommentInfoVO();
        Set<String> strKey = stringRedisTemplate.keys("COMMENT:"+ModuleContants.COMMENT+":" + commentSubDTO.getKid() + "_0_"+commentSubDTO.getResourceId());
        if (strKey.size() > 0) {
            logger.info("评论详情走redis1");
            for (String str : strKey) {
                String commentRedisSingleStr = stringRedisTemplate.opsForValue().get(str);
                JSONObject obj = new JSONObject().fromObject(commentRedisSingleStr);
                Comment comment = (Comment) JSONObject.toBean(obj, Comment.class);
                commentInfoVO.setContentComment(comment.getContentComment());
                commentInfoVO.setParentId(comment.getParentId());
                commentInfoVO.setTopId(comment.getTopId());
                commentInfoVO.setModuleEnum(comment.getModuleEnum());
                commentInfoVO.setCreateUserId(comment.getCreateUserId());
                commentInfoVO.setKid(comment.getKid());
                commentInfoVO.setCoterieId(comment.getCoterieId());
                commentInfoVO.setDelFlag(comment.getDelFlag());
                commentInfoVO.setKids(comment.getKids());
                commentInfoVO.setLikeCount(comment.getLikeCount());
                commentInfoVO.setLikeFlag(comment.getLikeFlag());
                commentInfoVO.setNickName(comment.getNickName());
                commentInfoVO.setParentUserId(comment.getParentUserId());
                commentInfoVO.setRecommend(comment.getRecommend());
                commentInfoVO.setResourceId(comment.getResourceId());
                commentInfoVO.setRevision(comment.getRevision());
                commentInfoVO.setShelveFlag(comment.getShelveFlag());
                commentInfoVO.setTargetUserId(comment.getTargetUserId());
                commentInfoVO.setTargetUserNickName(comment.getTargetUserNickName());
                commentInfoVO.setTenantId(comment.getTenantId());
                commentInfoVO.setUserImg(comment.getUserImg());
                commentInfoVO.setCreateDate(comment.getCreateDate());
                commentInfoVO.setId(comment.getId());
                commentInfoVO.setLastUpdateDate(comment.getLastUpdateDate());
                commentInfoVO.setLastUpdateUserId(comment.getLastUpdateUserId());
                commentInfoVO.setCommentEnties(null);
            }
        } else {
            logger.info("评论详情走数据库2");
            commentInfoVO = commentDao.querySingleCommentInfo(commentSubDTO_);
        }
        PageHelper.startPage(commentSubDTO.getCurrentPage().intValue(), commentSubDTO.getPageSize().intValue());
        CommentFrontDTO commentFrontDTO = new CommentFrontDTO();
        commentFrontDTO.setCurrentPage(commentSubDTO.getCurrentPage());
        commentFrontDTO.setPageSize(commentSubDTO.getPageSize());
        commentFrontDTO.setTopId(commentSubDTO.getKid());
        if (null != commentInfoVO) {
            commentFrontDTO.setResourceId(commentInfoVO.getResourceId());
        }
        PageList pageList = new PageList();
        pageList.setCurrentPage(commentSubDTO.getCurrentPage());
        pageList.setPageSize(commentSubDTO.getPageSize());
        List<CommentVO> commentVOS = new ArrayList<CommentVO>();
        Set<String> setSubKey = stringRedisTemplate.keys("COMMENT:"+ModuleContants.COMMENT+":*_" + commentFrontDTO.getTopId() + "_"+commentSubDTO.getResourceId());
        if (setSubKey.size() > 0) {
            logger.info("评论详情走redis3");
            for (String strkey : setSubKey) {
                CommentVO commentVO = new CommentVO();
                String commentSubSingle = stringRedisTemplate.opsForValue().get(strkey);
                JSONObject obj = new JSONObject().fromObject(commentSubSingle);
                Comment comment = (Comment) JSONObject.toBean(obj, Comment.class);
                commentVO.setContentComment(comment.getContentComment());
                commentVO.setParentId(comment.getParentId());
                commentVO.setTopId(comment.getTopId());
                commentVO.setModuleEnum(comment.getModuleEnum());
                commentVO.setCreateUserId(comment.getCreateUserId());
                commentVO.setKid(comment.getKid());
                commentVO.setCoterieId(comment.getCoterieId());
                commentVO.setDelFlag(comment.getDelFlag());
                commentVO.setKids(comment.getKids());
                commentVO.setLikeCount(comment.getLikeCount());
                commentVO.setLikeFlag(comment.getLikeFlag());
                commentVO.setNickName(comment.getNickName());
                commentVO.setParentUserId(comment.getParentUserId());
                commentVO.setRecommend(comment.getRecommend());
                commentVO.setResourceId(comment.getResourceId());
                commentVO.setRevision(comment.getRevision());
                commentVO.setShelveFlag(comment.getShelveFlag());
                commentVO.setTargetUserId(comment.getTargetUserId());
                commentVO.setTargetUserNickName(comment.getTargetUserNickName());
                commentVO.setTenantId(comment.getTenantId());
                commentVO.setUserImg(comment.getUserImg());
                commentVO.setCreateDate(comment.getCreateDate());
                commentVO.setId(comment.getId());
                commentVO.setLastUpdateDate(comment.getLastUpdateDate());
                commentVO.setLastUpdateUserId(comment.getLastUpdateUserId());
                commentVOS.add(commentVO);
            }
        } else {
            logger.info("评论详情走数据库4");
            commentVOS = commentDao.queryComments(commentFrontDTO);
        }
        for (CommentVO commentVO : commentVOS) {
            UserSimpleVO userSimpleVO = this.getUserSimple(commentVO.getTargetUserId());
            if (null != userSimpleVO) {
                commentVO.setTargetUserNickName(userSimpleVO.getUserNickName());
            }
        }
         /*用于redis分页*/
        if (setSubKey.size() > 0) {
            int page = commentSubDTO.getCurrentPage();//相当于pageNo
            int count = commentSubDTO.getPageSize();//相当于pageSize
            int size = commentVOS.size();
            int pageCount = size / count;
            int fromIndex = count * (page - 1);
            int toIndex = fromIndex + count;
            if (toIndex >= size) {
                toIndex = size;
            }
            if (page > pageCount + 1) {
                fromIndex = 0;
                toIndex = 0;
            }
            logger.info("评论详情走redis分页5");
            pageList.setEntities(commentVOS.subList(fromIndex, toIndex));
        } else {
            logger.info("评论详情走原始分页6");
            pageList.setEntities(commentVOS);
        }
        pageList.setCount(Long.valueOf(commentVOS.size()));
        return pageList;
    }

    @Override
    public int updownSingle(Comment comment) {
        int count = commentDao.updownSingle(comment);
        if (count > 0) {
            this.sendMessage(comment.getTargetUserId(), "您的评论有违纪嫌疑,已被管理员下架!");
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


    public void sendMessage(Long targetUserId, String msg) {
        PushReqVo pushReqVo = new PushReqVo();
        List<String> strUserId = new ArrayList<String>();
        strUserId.add(String.valueOf(targetUserId));
        pushReqVo.setCustIds(strUserId);
        pushReqVo.setMsg(msg);
        pushReqVo.setPushType(PushReqVo.CommonPushType.BY_ALIAS);
        List<String> registrationIds = new ArrayList<String>();
        registrationIds.add(String.valueOf(PushReqVo.CommonPushType.BY_REGISTRATIONID));
        pushReqVo.setRegistrationIds(registrationIds);
        try {
            pushAPI.commonSendAlias(pushReqVo);
        } catch (Exception e) {
            logger.info("调用极光推送失败:" + e);
        }
    }
}
