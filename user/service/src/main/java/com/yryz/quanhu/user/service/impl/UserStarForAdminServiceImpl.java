package com.yryz.quanhu.user.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.dymaic.service.ElasticsearchService;
import com.yryz.quanhu.user.dao.UserStarAuthDao;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.dto.UserStarAuthDto;
import com.yryz.quanhu.user.dto.UserTagDTO;
import com.yryz.quanhu.user.entity.UserStarAuth;
import com.yryz.quanhu.user.service.UserStarForAdminService;
import com.yryz.quanhu.user.service.UserTagService;
import com.yryz.quanhu.user.vo.EventAccountVO;
import com.yryz.quanhu.user.vo.StarAuthInfoVO;
import com.yryz.quanhu.user.vo.UserInfoVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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

    @Autowired
    private UserStarAuthDao userStarAuthDao;

    @Autowired
    private UserTagService userTagService;

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


        if(starAuth.getRecommendHeight()==null){
            starAuth.setRecommendHeight(0);
        }

        //置顶  计算推荐权重最大值
        if(isTop){
            int maxRecmdNum = userStarAuthDao.getMaxHeight();
            maxRecmdNum++;
            starAuth.setRecommendHeight(maxRecmdNum);
        }

        //更新数据库
        int updateCount = userStarAuthDao.updateRecommendStatus(starAuth);
        return updateCount==1?true:false;
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
        admin.setUserRole(11);                                       //只查询达人
        admin.setAuthType(dto.getAuthType());                        //认证类型
        admin.setAuthWay(dto.getAuthWay());                          //认证方式
        admin.setAuditStatus(dto.getAuditStatus());                  //认证状态
        if(dto.getUserLevel() != null ){                              //全部
            admin.setGrowLevel(String.valueOf(dto.getUserLevel().intValue()));  //用户等级
        }
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
        admin.setNickName("");                                      //昵称
        admin.setRecommendStatus(dto.getRecommendStatus());         //推荐状态

        admin.setAppId(appId);
        admin.setPageNo(dto.getPageNo());
        admin.setPageSize(dto.getPageSize());

        return admin;
    }

    @Override
    public PageList<UserStarAuthDto> listByRecmd(UserStarAuthDto dto) {

        //转换ES查询条件
        AdminUserInfoDTO admin = this.buildRecmdElasticQueryParameter(dto);

        return this.executeElasticQuery(admin);
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
            Response<PageList<UserInfoVO>> rpc = elasticsearchService.adminSearchUser(admin);
            List<UserInfoVO> esArray = rpc.getData().getEntities();

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
                authDto.setCreateDate(infoVo.getUserBaseInfo().getCreateDate());
                if(event!=null){
                    authDto.setUserLevel(Integer.parseInt(event.getGrowLevel()));
                }
                returnArray.add(authDto);
            }
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
