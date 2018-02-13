package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.yryz.common.constant.IdConstants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.UserStarContants;
import com.yryz.quanhu.user.dao.UserStarAuthDao;
import com.yryz.quanhu.user.dao.UserStarAuthLogDao;
import com.yryz.quanhu.user.dao.UserStarRedisDao;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.entity.UserBaseInfo;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.entity.UserStarAuthLog;
import com.yryz.quanhu.user.manager.EventManager;
import com.yryz.quanhu.user.manager.MessageManager;
import com.yryz.quanhu.user.provider.UserStarForAdminProvider;
import com.yryz.quanhu.user.service.UserService;
import com.yryz.quanhu.user.service.UserStarForAdminService;
import com.yryz.quanhu.user.service.UserTagService;
import com.yryz.quanhu.user.vo.EventAccountVO;
import com.yryz.quanhu.user.vo.StarAuthInfoVO;
import com.yryz.quanhu.user.vo.UserInfoVO;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.yryz.quanhu.user.contants.UserStarContants.StarAuditStatus.AUDIT_FAIL;
import static com.yryz.quanhu.user.contants.UserStarContants.StarAuditStatus.AUDIT_SUCCESS;
import static com.yryz.quanhu.user.contants.UserStarContants.StarAuditStatus.CANCEL_AUTH;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/2/3 16:46
 * Created by huangxy
 */
@Service
@Transactional
public class UserStarForAdminServiceImpl implements UserStarForAdminService{

    private static final Logger logger = LoggerFactory.getLogger(UserStarForAdminServiceImpl.class);

    @Autowired
    private UserStarAuthDao userStarAuthDao;
    @Autowired
    private UserStarAuthLogDao userStarAuthLogDao;
    @Autowired
    private UserTagService userTagService;
    @Autowired
    private UserService userService;
    @Autowired
    private EventManager eventManager;
    @Autowired
    private MessageManager messageManager;
    @Autowired
    private UserStarRedisDao userStarRedisDao;

    @Reference(check=false)
    private IdAPI idApi;
    @Reference(check = false)
    private ElasticsearchService elasticsearchService;

    @Value("${appId}")
    private String appId;


    @Override
    public Boolean updateAuth(UserStarAuthDto dto) {
        /**
         * 更新字段
         */
        UserStarAuth starAuth = new UserStarAuth();
        starAuth.setAuditStatus(dto.getAuditStatus());
        starAuth.setLastUpdateUserId(dto.getLastUpdateUserId());
        starAuth.setAuditFailReason(dto.getAuditFailReason());
        starAuth.setKid(dto.getKid());

        //更新数据库
        int updateCount = userStarAuthDao.updateAuditStatus(starAuth);

        /**
         * 反查询达人用户信息，进行联动
         */
        starAuth = userStarAuthDao.selectByKid(UserStarAuth.class,dto.getKid());

        /**
         * 同步操作：
         * 1,联动发消息，
         * 2,设置等级,成长值
         * 3,更新用户为达人标识
         * 4,同步达人审核日志记录
         * 5,同步删除达人缓存
         */
        this.syncMessage(starAuth);
        this.syncUserRole(starAuth);
        this.syncUserGrowLevel(starAuth);
        this.syncUserStarAuthLog(starAuth);
        this.syncDeleteRedis(starAuth);

        return updateCount==1?true:false;
    }

    @Override
    public Boolean updateRecmd(UserStarAuthDto dto,boolean isTop) {

        UserStarAuth starAuth = new UserStarAuth();
        starAuth.setKid(dto.getKid());
        starAuth.setRecommendStatus(dto.getRecommendStatus());          //状态
        starAuth.setRecommendHeight(dto.getRecommendHeight());          //权重值
        starAuth.setRecommendOperate(dto.getRecommendOperate());        //推荐人
        starAuth.setRecommendDesc(dto.getRecommendDesc());              //推荐语
        starAuth.setLastUpdateUserId(dto.getLastUpdateUserId());        //操作人
        starAuth.setOperational(dto.getOperational());                  //推荐人名称
        /**
         * 反查询达人用户信息，进行联动
         */
        UserStarAuth orgAuth = userStarAuthDao.selectByKid(UserStarAuth.class,dto.getKid());


        //置顶  计算推荐权重最大值
        if(isTop){
            int maxRecmdNum = userStarAuthDao.getMaxHeight();
            maxRecmdNum++;
            starAuth.setRecommendHeight(maxRecmdNum);
        }else{

            if(11 == dto.getRecommendStatus().intValue()){      //推荐，在当前推荐值上加1
                starAuth.setRecommendHeight(orgAuth.getRecommendHeight()+1);
            }
        }

        //更新达人数据库
        int updateCount = userStarAuthDao.updateRecommendStatus(starAuth);


        /**
         *1,同步删除达人缓存
         */
        this.syncDeleteRedis(orgAuth);

        return updateCount==1?true:false;
    }

    @Override
    public Boolean updateRecmdsort(List<UserStarAuthDto> dtos) {

        List<UserStarAuth> list = new ArrayList<>();
        int updateCount = 0;
        for(int i = 0 ; i < dtos.size() ; i++){
            UserStarAuthDto dto = dtos.get(i);

            UserStarAuth starAuth = new UserStarAuth();
            starAuth.setKid(dto.getKid());
            starAuth.setRecommendStatus((byte) 11);                         //推荐状态
            starAuth.setRecommendHeight(dto.getRecommendHeight());          //权重值
            starAuth.setRecommendOperate(dto.getRecommendOperate());        //推荐人
            starAuth.setRecommendDesc(null);                                //推荐语(不修改)
            starAuth.setLastUpdateUserId(dto.getLastUpdateUserId());        //操作人
            starAuth.setOperational(dto.getOperational());                  //推荐人名称

            updateCount += userStarAuthDao.updateRecommendStatus(starAuth);
        }

        return updateCount == dtos.size()?true:false;
    }


    /**
     * 同步发生消息
     * @param starAuth
     */
    private void syncMessage(UserStarAuth starAuth){

        String userId = String.valueOf(starAuth.getUserId());
        byte auditStatus = starAuth.getAuditStatus();

        logger.info("syncMessage:{}={} start",userId,auditStatus);

        /**
         *判断状态，发送不同消息
         */
        if(AUDIT_SUCCESS.getStatus() == auditStatus){           //审核通过

            logger.info("messageManager.starSuccess");
            messageManager.starSuccess(userId);
        }else if(AUDIT_FAIL.getStatus() == auditStatus){        //审核失败

            logger.info("messageManager.starFail");
            messageManager.starFail(userId,starAuth.getAuditFailReason());
        }else if(CANCEL_AUTH.getStatus() == auditStatus){       //取消认证

            logger.info("messageManager.starCancel");
            messageManager.starCancel(userId);
        }

        logger.info("syncMessage:{}={} finish",userId,auditStatus);
    }

    /**
     * 同步设置用户等级，成长值
     * @param starAuth
     */
    private void syncUserGrowLevel(UserStarAuth starAuth){

        String userId = String.valueOf(starAuth.getUserId());
        byte auditStatus = starAuth.getAuditStatus();



        if(AUDIT_SUCCESS.getStatus() == auditStatus){           //审核通过

            logger.info("syncUserGrowLevel:{}={} start",userId,auditStatus);
            eventManager.starAuth(userId);
            logger.info("syncUserGrowLevel:{}={} finish",userId,auditStatus);

        }else{
            return;
        }

    }



    /**
     * 同步更新用户等级
     * @param starAuth
     */
    private void syncUserRole(UserStarAuth starAuth){

        Long userId = starAuth.getUserId();
        byte auditStatus = starAuth.getAuditStatus();

        UserBaseInfo info = new UserBaseInfo();
        info.setUserId(userId);
        info.setAuthStatus(auditStatus);


        if(AUDIT_SUCCESS.getStatus() == auditStatus){           //审核通过      设置达人
            info.setUserRole(UserBaseInfo.UserRole.STAR.getRole());
        }else if(AUDIT_FAIL.getStatus() == auditStatus){        //审核失败      设置普通用户
            info.setUserRole(UserBaseInfo.UserRole.NORMAL.getRole());
        }else if(CANCEL_AUTH.getStatus() == auditStatus){       //取消认证      设置普通用户
            info.setUserRole(UserBaseInfo.UserRole.NORMAL.getRole());
        }else{
            return;
        }

        logger.info("syncUserRole:{} start",JSON.toJSON(info));
        /**
         * 更新达人状态位
         */
        userService.updateUserInfo(info);

        logger.info("syncUserRole:{} finish",JSON.toJSON(info));
    }

    /**
     * 同步达人审核记录日志
     * @param starAuth
     */
    private void syncUserStarAuthLog(UserStarAuth starAuth){

        UserStarAuthLog logModel = new UserStarAuthLog();
        BeanUtils.copyProperties(starAuth,logModel);

        //生成ID
        logModel.setId(null);
        logModel.setKid(idApi.getKid(IdConstants.QUANHU_USER_STAR_AUTH_LOG).getData());
        logModel.setCreateDate(new Date());

        logger.info("syncUserStarAuthLog:{} start", JSON.toJSON(logModel));
        //新增数据
        userStarAuthLogDao.insert(logModel);

        logger.info("syncUserStarAuthLog:{} finish", JSON.toJSON(logModel));
    }

    /**
     * 同步删除缓存
     * @param starAuth
     */
    private void syncDeleteRedis(UserStarAuth starAuth){
        logger.info("syncDeleteRedis:{} start",starAuth.getUserId());
        userStarRedisDao.delete(String.valueOf(starAuth.getUserId()));
        logger.info("syncDeleteRedis:{} finish",starAuth.getUserId());
    }

    @Override
    public List<UserTagDTO> getTags(UserStarAuthDto dto) {
        List<UserTagDTO> outArray = new ArrayList<>();
        /**
         * 查询用户信息
         */
        UserStarAuth starAuth = userStarAuthDao.selectByKid(UserStarAuth.class,dto.getKid());
        if(starAuth==null){
            throw new RuntimeException("达人查询记录不存在");
        }
        /**
         * 查询标签信息
         */
        return userTagService.getUserTags(starAuth.getUserId());
    }

    @Override
    public Boolean updateTags(UserStarAuthDto dto) {

        //查询UserId
        UserStarAuth entity = userStarAuthDao.selectByKid(UserStarAuth.class,dto.getKid());
        if(entity==null){
            throw new RuntimeException("达人查询记录不存在");
        }
        Set<Long> tags = dto.getTagIds();
        Iterator<Long> iterator = tags.iterator();
        StringBuffer tagIdsBuffer = new StringBuffer();
        while (iterator.hasNext()){
            tagIdsBuffer.append(",").append(iterator.next());
        }
        if(tags.size()>0){
            tagIdsBuffer = new StringBuffer(tagIdsBuffer.substring(1,tagIdsBuffer.length()));
        }

        UserTagDTO userTagDTO = new UserTagDTO();
        userTagDTO.setUserId(entity.getUserId());
        userTagDTO.setTagIds(tagIdsBuffer.toString());
        userTagDTO.setUpdateUserId(dto.getLastUpdateUserId());
        userTagDTO.setTagType((byte) 11);       //统一为运营设置标签

        userTagService.batch(userTagDTO);
        return true;
    }

    @Override
    public UserStarAuthDto getAuth(UserStarAuthDto dto) {
        //通过Kid查询信息
        UserStarAuth entity = userStarAuthDao.selectByKid(UserStarAuth.class,dto.getKid());
        if(null != entity){
            BeanUtils.copyProperties(entity,dto);
            return dto;
        }else {
            return null;
        }
    }

    private AdminUserInfoDTO buildAuthElasticQueryParameter(UserStarAuthDto dto){

        AdminUserInfoDTO admin = new AdminUserInfoDTO();
        admin.setPhone(dto.getContactCall());                        //手机号
        admin.setRealName(dto.getRealName());                        //真实姓名
        admin.setContactCall(dto.getContactCall());                  //联系方式
        admin.setAuthType(dto.getAuthType());                        //认证类型
        admin.setAuthWay(dto.getAuthWay());                          //认证方式
        admin.setAuditStatus(dto.getAuditStatus());                  //认证状态  通过认证状态es查询是否为达人，如果是达人肯定存在审核信息

        if(dto.getUserLevel() != null ){                              //全部
            admin.setGrowLevel(String.valueOf(dto.getUserLevel().intValue()));  //用户等级
        }

        //日期区间查询，暂没实现
        admin.setApplyAuthBeginDate(dto.getBeginDate());             //申请认证时间
        admin.setApplyAuthEndDate(dto.getEndDate());                 //申请认证时间
        admin.setAppId(appId);

        admin.setPageNo(dto.getPageNo());
        admin.setPageSize(dto.getPageSize());
        return admin;
    }


    @Override
    public PageList<UserStarAuthDto> listByAuth(UserStarAuthDto dto) {

        //转换ES查询条件
        AdminUserInfoDTO admin = this.buildAuthElasticQueryParameter(dto);
        /**
         * es搜索查询（默认达人）
         * 名称，手机号，审核状态，认证类型，认证方式，用户等级，申请认证时间区间
         */
        return this.executeElasticQuery(admin);
    }


    private AdminUserInfoDTO buildRecmdElasticQueryParameter(UserStarAuthDto dto){
        AdminUserInfoDTO admin = new AdminUserInfoDTO();

        admin.setUserRole(11);                                      //达人
        admin.setRealName(dto.getRealName());                       //真实姓名
        admin.setContactCall(dto.getContactCall());                 //联系方式
        admin.setRecommendStatus(dto.getRecommendStatus());         //推荐状态
        admin.setAuditStatus((byte) 11);                            //审核通过
        admin.setTagIds(dto.getTagIds());                           //标签查询
        admin.setAppId(appId);
        admin.setPageNo(dto.getPageNo());
        admin.setPageSize(dto.getPageSize());

        return admin;
    }

    @Override
    public PageList<UserStarAuthDto> listByRecmd(UserStarAuthDto dto) {

        //转换ES查询条件
        AdminUserInfoDTO admin = this.buildRecmdElasticQueryParameter(dto);

        //es查询
        PageList<UserStarAuthDto> pageList =  this.executeElasticQuery(admin);

        //查询标签信息
        Set<Long> userIds = new HashSet<>();
        for(UserStarAuthDto esDto : pageList.getEntities()){
            userIds.add(esDto.getUserId());
        }
        List<UserTagDTO> tagsArray = userTagService.getUserGroupConcatTags(userIds);

        if(!CollectionUtils.isEmpty(tagsArray)){
            //重赋值标签信息
            for(UserStarAuthDto esDto : pageList.getEntities()){
                this.mergeTagNames(tagsArray,esDto);
            }
        }
        return pageList;
    }

    private void mergeTagNames(List<UserTagDTO> tagDtos,UserStarAuthDto dto){
        for(int i = 0 ; i < tagDtos.size() ; i++){
            UserTagDTO tag = tagDtos.get(i);
            if(dto.getUserId().longValue() == tag.getUserId().longValue()){
                dto.setTagNames(tag.getTagNames());
                break;
            }
        }
    }

    /**
     * 执行ES查询
     * @param admin
     * @return
     */
    private PageList<UserStarAuthDto> executeElasticQuery(AdminUserInfoDTO admin){

        PageList<UserStarAuthDto> pageList = new PageList<>();

        List<UserStarAuthDto> returnArray = new ArrayList<>();
        try{
            Response<PageList<UserInfoVO>> rpc = elasticsearchService.searchStarUserForAdmin(admin);
            List<UserInfoVO> esArray = rpc.getData().getEntities();

            logger.info("elasticsearchService result entities:{} , pageCount:{}",esArray.size(),rpc.getData().getCount());

            for(int i = 0 ; i < esArray.size() ; i++){
                UserInfoVO infoVo = esArray.get(i);
                StarAuthInfoVO auth = infoVo.getUserStarInfo();
                EventAccountVO event = infoVo.getEventAccountInfo();
                if(auth==null){
                    continue;
                }

                UserStarAuthDto authDto = new UserStarAuthDto();
                //转换对象
                BeanUtils.copyProperties(auth,authDto);

                authDto.setKid(auth.getKid());
                authDto.setUserId(Long.parseLong(auth.getUserId()));
                authDto.setAuthTime(auth.getAuthTime());
                authDto.setCreateDate(infoVo.getUserStarInfo().getCreateDate());
                authDto.setRegisterDate(infoVo.getUserBaseInfo().getCreateDate());
                if(event!=null&&event.getGrowLevel()!=null){
                    authDto.setUserLevel(Integer.parseInt(event.getGrowLevel()));
                }
                returnArray.add(authDto);
            }

            logger.info("format result entities:{} ",returnArray.size());

            pageList.setEntities(returnArray);
            pageList.setPageSize(rpc.getData().getPageSize());
            pageList.setCurrentPage(rpc.getData().getCurrentPage());
            pageList.setCount(rpc.getData().getCount());
        }catch (Exception e){
            throw new QuanhuException("","","ES查询失败",e);
        }
        return pageList;
    }


    @Override
    public PageList<UserStarAuthDto> listByAuthLog(UserStarAuthDto dto) {
        return null;
    }

}
