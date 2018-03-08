package com.yryz.quanhu.other.activity.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.context.Context;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.JsonUtils;
import com.yryz.quanhu.other.activity.dao.ActivityInfoDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteConfigDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteDetailDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteRecordDao;
import com.yryz.quanhu.other.activity.dto.*;
import com.yryz.quanhu.other.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.other.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.other.activity.service.ActivityVoteRedisService;
import com.yryz.quanhu.other.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.other.activity.service.AdminIActivityParticipationService;
import com.yryz.quanhu.other.activity.vo.*;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.service.AccountApi;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.converters.SqlDateConverter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.awt.SystemColor.info;
import static org.springframework.util.Assert.isNull;
@Service
public class AdminIActivityParticipationServiceImpl implements AdminIActivityParticipationService {

    private Logger logger = LoggerFactory.getLogger(AdminIActivityParticipationServiceImpl.class);

    @Autowired
    ActivityVoteDetailDao activityParticipationDao;

    @Autowired
    ActivityVoteRecordDao activityVoteRecordDao;

    @Autowired
    AdminActivityVoteService activityVoteService;

    @Autowired
    ActivityVoteConfigDao activityVoteConfigDao;

	@Autowired
    ActivityInfoDao activityInfoDao;
    @Reference(check=false)
    UserApi userApi;
    @Reference(check=false)
    private IdAPI idApi;
    @Reference(check=false)
    ScoreAPI scoreAPI;
    @Reference(check=false)
    ResourceDymaicApi resourceDymaicApi;
    @Reference(check = false)
    private AccountApi accountApi;
    @Reference(check=false)
    ResourceApi resourceApi;

    @Autowired
    ActivityVoteRedisService activityVoteRedisService;
    /**
     * 增加票数
     */
    @Override
    public Integer addVote(Long id, Integer count) {
        AdminActivityVoteDetailVo adminActivityVoteDetailVo = activityParticipationDao.selectByPrimaryKey(id);
        Integer act = activityParticipationDao.updateAddVote(id, count);
        if (act == 1){
            activityVoteRedisService.vote(adminActivityVoteDetailVo.getActivityInfoId(),id, count-adminActivityVoteDetailVo.getAddVote());
        }
        return act;
    }

    @Override
    public Integer updateStatus(Long id, Byte status) {
        Integer count = activityParticipationDao.updateStatus(id, status);
        if(count==1){
            AdminActivityVoteDetailVo adminActivityVoteDetailVo = activityParticipationDao.selectByPrimaryKey(id);
            ActivityVoteConfig config = activityVoteConfigDao.selectVoteByActivityInfoId(adminActivityVoteDetailVo.getActivityInfoId());

            if(status==10){
              /*  //增加已参与人数
                int flag = activityInfoDao.updateJoinCount(adminActivityVoteDetailVo.getActivityInfoId(), config.getUserNum());
                if(flag == 0) {
                    throw QuanhuException.busiError("参加人数已满,无法上架");
                }*/
                //资源提交
                AdminActivityInfoVo1 adminActivityInfoVo1 = activityInfoDao.selectByPrimaryKey(adminActivityVoteDetailVo.getActivityInfoId());
                this.commitResource(adminActivityVoteDetailVo,adminActivityInfoVo1);
                activityVoteRedisService.shelvesCandidate(adminActivityVoteDetailVo.getActivityInfoId(),
                        adminActivityVoteDetailVo.getKid(),adminActivityVoteDetailVo.getId(),
                        Long.valueOf(adminActivityVoteDetailVo.getVoteCount()+adminActivityVoteDetailVo.getAddVote()),
                        null);
            }else if (status==11){
               /* //减少已参与人数
                int flag = activityInfoDao.updateJoinCountDiff(adminActivityVoteDetailVo.getActivityInfoId());
                if(flag == 0) {
                    throw QuanhuException.busiError("参加人数减少异常,无法上架");
                }*/
                //资源删除
                try {
                    resourceApi.deleteResourceById(String.valueOf(adminActivityVoteDetailVo.getKid()));
                } catch (Exception e) {
                    logger.error("资源删除失败");
                }
                activityVoteRedisService.remCandidate(adminActivityVoteDetailVo.getActivityInfoId(),adminActivityVoteDetailVo.getKid());
            }
        }
        return count;
    }

    /**
     * 参与活动列表
     */
    @Override
    public PageList<AdminActivityVoteDetailVo> list(AdminActivityVoteDetailDto adminActivityVoteDetailDto) {
        Page page = new Page();
        if(StringUtils.isEmpty(adminActivityVoteDetailDto.getType())||!adminActivityVoteDetailDto.getType().equals("1")){
            page=PageHelper.startPage(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize());
        }
        if (null == adminActivityVoteDetailDto.getActivityInfoId()) {
            logger.debug("请通过投票活动列表进入！！！");
            isNull(adminActivityVoteDetailDto.getActivityInfoId(), "活动ID不能为空，请通过投票活动列表进入！！！");
        }
        List<AdminActivityVoteDetailVo> list = null;
        if (StringUtils.isNotBlank(adminActivityVoteDetailDto.getNickName()) || StringUtils.isNotBlank(adminActivityVoteDetailDto.getPhone())) {
            List<String> custIds =new ArrayList<String>();
            Response<PageList<UserBaseInfoVO>> userInfoPage = null;
            try {
                SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
                AdminUserInfoDTO custInfoDTO = new AdminUserInfoDTO();
                custInfoDTO.setNickName(adminActivityVoteDetailDto.getNickName());
                custInfoDTO.setPhone(adminActivityVoteDetailDto.getPhone());
                custInfoDTO.setAppId(Context.getProperty("appId"));
                userInfoPage = userApi.listUserInfo(1,20,custInfoDTO);
            } catch (Exception e) {
                logger.error("查询用户列表异常",e);
            }
            if(userInfoPage!=null&&userInfoPage.success()&&!CollectionUtils.isEmpty(userInfoPage.getData().getEntities())){
                for (UserBaseInfoVO uv:userInfoPage.getData().getEntities()){
                    custIds.add(uv.getUserId().toString());
                }
            }
            if (!CollectionUtils.isEmpty(custIds)) {
                adminActivityVoteDetailDto.setCustIds(custIds);
                list = activityParticipationDao.select(adminActivityVoteDetailDto);
            }
           
        }
        else{
       	 list = activityParticipationDao.select(adminActivityVoteDetailDto);
       }

       if(CollectionUtils.isEmpty(list)){
    	   return new PageList(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize(), list, 0L);
       }

        //票数占比：显示对应内容在当前活动中所获票数占总票数的比值，到小数点后4位；
        Integer sum = activityVoteRecordDao.selectCountByActivityInfoId(adminActivityVoteDetailDto.getActivityInfoId(), null);

        for (AdminActivityVoteDetailVo voteDetailVo : list) {
            Set<String> userIds = new HashSet<String>();
            userIds.add(voteDetailVo.getCreateUserId().toString());
            Response<Map<String,UserBaseInfoVO>> users = null;
            try {
                users = userApi.getUser(userIds);
            } catch (Exception e) {
                logger.error("查询用户信息异常",e);
            }
            if(null!=users&&
                    users.success()&&
                    users.getData().get(voteDetailVo.getCreateUserId().toString())!=null){
                voteDetailVo.setNickName(users.getData().get(voteDetailVo.getCreateUserId().toString()).getUserNickName());
                voteDetailVo.setPhone(users.getData().get(voteDetailVo.getCreateUserId().toString()).getUserPhone());
            }

            Integer voteCount = voteDetailVo.getVoteCount();
            double data = 0.00;
            if (sum != 0 && voteCount != 0) {
                data = (double) voteCount / (double) sum;
            }
            String voteProportion = new DecimalFormat("0.0000").format(data);
            voteDetailVo.setVoteProportion(voteProportion);
        }

        return new PageList(adminActivityVoteDetailDto.getPageNo(), adminActivityVoteDetailDto.getPageSize(), list, page.getTotal());
    }
    /**
     * 投票用户数据
     */
    @Override
    public  PageList adminlist(AdminActivityVoteRecordDto adminActivityVoteRecordDto) {
        Integer pageNo = 1;
        Integer pageSize = 10;
        Page<AdminActivityVoteRecordVo> page = new Page<AdminActivityVoteRecordVo>();
        if(adminActivityVoteRecordDto.getType()==null||!adminActivityVoteRecordDto.getType().equals(1)){
            pageNo = adminActivityVoteRecordDto.getPageNo();
            pageSize = adminActivityVoteRecordDto.getPageSize();
            page = PageHelper.startPage(pageNo,pageSize);
        }
		String nickName=null;
    	String  beginDate=null;
    	String  endDate=null;
    	List<String> custIds =new ArrayList<String>();
    	List<AdminActivityVoteRecordVo> list = new ArrayList<AdminActivityVoteRecordVo>();
        if (StringUtils.isNotBlank(adminActivityVoteRecordDto.getNickName())|| adminActivityVoteRecordDto.getBeginCreateDate()!=null|| adminActivityVoteRecordDto.getEndCreateDate()!=null) {
        	
        	SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
        	if(StringUtils.isNotBlank(adminActivityVoteRecordDto.getNickName())){
        		nickName= adminActivityVoteRecordDto.getNickName();
        	}
        	if(adminActivityVoteRecordDto.getBeginCreateDate()!=null|| adminActivityVoteRecordDto.getEndCreateDate()!=null)
        	{
        		 beginDate=sdf.format(adminActivityVoteRecordDto.getBeginCreateDate());
            	 endDate=sdf.format(adminActivityVoteRecordDto.getEndCreateDate());
        	}

            Response<PageList<UserBaseInfoVO>> userInfoPage = null;
            try {
                AdminUserInfoDTO custInfoDTO = new AdminUserInfoDTO();
                custInfoDTO.setNickName(nickName);
                custInfoDTO.setStartDate(beginDate);
                custInfoDTO.setEndDate(endDate);
                custInfoDTO.setAppId(Context.getProperty("appId"));
                userInfoPage = userApi.listUserInfo(pageNo,pageSize,custInfoDTO);
            } catch (Exception e) {
                logger.error("查询用户列表异常",e);
            }
            if(userInfoPage!=null&&userInfoPage.success()&&!CollectionUtils.isEmpty(userInfoPage.getData().getEntities())){
                for (UserBaseInfoVO uv:userInfoPage.getData().getEntities()){
                    custIds.add(uv.getUserId().toString());
                }
            }
        	if (!CollectionUtils.isEmpty(custIds)) {
                adminActivityVoteRecordDto.setCustIds(custIds);
            }
            if(custIds.size()>0){
            	 list = activityVoteRecordDao.selectPage(adminActivityVoteRecordDto);
            }
        }
        else{
		 list = activityVoteRecordDao.selectPage(adminActivityVoteRecordDto);
        }
        for (AdminActivityVoteRecordVo voteRecord : list) {
            Set<String> userIds = new HashSet<String>();
            userIds.add(voteRecord.getCreateUserId().toString());
            Response<Map<String,UserBaseInfoVO>> users = null;
            try {
                users = userApi.getUser(userIds);
            } catch (Exception e) {
                logger.error("查询用户信息异常",e);
            }
            if(null!=users && users.success()&&users.getData().get(voteRecord.getCreateUserId().toString())!=null){
                voteRecord.setNickName(users.getData().get(voteRecord.getCreateUserId().toString()).getUserNickName());
                voteRecord.setCreateDate(users.getData().get(voteRecord.getCreateUserId().toString()).getCreateDate());
                voteRecord.setPhone(users.getData().get(voteRecord.getCreateUserId().toString()).getUserPhone());
            }
            //查询用户是否参加
            AdminActivityVoteDetailDto voteDetailDto = new AdminActivityVoteDetailDto();
            voteDetailDto.setActivityInfoId(adminActivityVoteRecordDto.getActivityInfoId());
            voteDetailDto.setCreateUserId(voteRecord.getCreateUserId());
            List<AdminActivityVoteDetailVo> voteDetailVos = activityParticipationDao.selectByParam(voteDetailDto);
            if ( ! CollectionUtils.isEmpty(voteDetailVos)) {
                voteRecord.setOtherFlag(1);
            }else{
                voteRecord.setOtherFlag(0);
            }
        }
		if (CollectionUtils.isEmpty(list)) {
			return new PageList(pageNo, pageSize, list, 0L);
		}
		return new PageList(pageNo, pageSize, list, page.getTotal());
    }

    @Override
    public ActivityVoteDetailVo candDetail(Long kid) {
        return activityParticipationDao.selectByKid(kid);
    }

    public AdminActivityInfoVo1 detail(Long infoId) {
        if (null != infoId) {
            return activityVoteService.getActivityDetail(infoId);
        }
        return null;
    }

    public AdminConfigObjectDto getVoteConfig(Long infoId) {

        ActivityVoteConfig config = activityVoteConfigDao.selectByInfoId(infoId);
        if (config == null) {
            return null;
        }

        AdminConfigObjectDto adminConfigObjectDto = null;

        if (StringUtils.isNotBlank(config.getConfigSources())) {

            adminConfigObjectDto = new AdminConfigObjectDto();

            AdminConfigDto adminConfigDto = JSON.parseObject(config.getConfigSources(),AdminConfigDto.class);

            if (adminConfigDto == null) {
                return null;
            }

            if (StringUtils.isNotBlank(adminConfigDto.getCoverPlan())) {
                AdminConfigContentDto coverPlan = JSON.parseObject(adminConfigDto.getCoverPlan(),AdminConfigContentDto.class);
                adminConfigObjectDto.setCoverPlan(coverPlan);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getContent())) {
                AdminConfigContentDto content = JSON.parseObject(adminConfigDto.getContent(), AdminConfigContentDto.class);
                adminConfigObjectDto.setContent(content);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getContent1())) {
                AdminConfigContentDto content1 = JSON.parseObject(adminConfigDto.getContent1(), AdminConfigContentDto.class);
                adminConfigObjectDto.setContent1(content1);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getContent2())) {
                AdminConfigContentDto content2 = JSON.parseObject(adminConfigDto.getContent2(), AdminConfigContentDto.class);
                adminConfigObjectDto.setContent2(content2);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getImgUrl())) {
                AdminConfigContentDto imgUrl = JSON.parseObject(adminConfigDto.getImgUrl(),AdminConfigContentDto.class);
                adminConfigObjectDto.setImgUrl(imgUrl);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getVideoUrl())) {
                AdminConfigContentDto videoUrl = JSON.parseObject(adminConfigDto.getVideoUrl(),AdminConfigContentDto.class);
                adminConfigObjectDto.setVideoUrl(videoUrl);
            }
        }
        return adminConfigObjectDto;
    }

    /**
     * 弹框选择马甲
     */
    @Override
    public  PageList<UserBaseInfoVO> selectUser(AdminUserInfoDTO custInfoDTO, Integer pageNo, Integer pageSize) {
        custInfoDTO.setAppId(Context.getProperty("appId"));
        Response<PageList<UserBaseInfoVO>> userInfoPage = userApi.listUserInfo(pageNo,pageSize,custInfoDTO);
        PageList<UserBaseInfoVO> pageList = new PageList<UserBaseInfoVO>();
        if (userInfoPage.success()){
            pageList.setCount(userInfoPage.getData().getCount());
            pageList.setEntities(userInfoPage.getData().getEntities());
            pageList.setCurrentPage(pageNo);
            pageList.setPageSize(pageSize);
        }
        return pageList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public String saveVoteDetail(AdminActivityVoteDetailDto voteDetailDto) {
        Response<Boolean> rpc = null;
        try {
            rpc = accountApi.checkUserDisTalk(voteDetailDto.getCreateUserId());
        } catch (Exception e) {
            logger.error("查询禁言异常",e);
            throw new QuanhuException("","","查询禁言异常");
        }
        if(!rpc.success()||rpc.getData()){
            throw new QuanhuException("","","该用户已被平台管理员禁言，不允许操作");
        }
        AdminActivityInfoVo1 adminActivityInfoVo1 = activityInfoDao.selectByPrimaryKey(voteDetailDto.getActivityInfoId());
        ActivityVoteConfig config = activityVoteConfigDao.selectVoteByActivityInfoId(voteDetailDto.getActivityInfoId());
        //增加已参与人数
        int flag = activityInfoDao.adminUpdateJoinCount(voteDetailDto.getActivityInfoId());
        if(flag == 0) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),"活动不存在","活动不存在");
        }
        //在一个活动里一个用户只能参与一次
        List<AdminActivityVoteDetailVo> voteDetailVos = activityParticipationDao.selectByParam(voteDetailDto);
        if ( ! CollectionUtils.isEmpty(voteDetailVos)) {
           return "此用户已参加此次活动，不能重复参加活动" ;
        }
        /*Integer voteNo = activityParticipationDao.maxId(voteDetailDto.getActivityInfoId());
        if (voteNo == null) {
            voteNo = 0;
        }*/
        voteDetailDto.setKid(idApi.getSnowflakeId().getData());
        //maxId
        voteDetailDto.setVoteNo(activityVoteRedisService.getMaxVoteNo(voteDetailDto.getActivityInfoId()).intValue());
        voteDetailDto.setModuleEnum(ModuleContants.ACTIVITY_WORKS_ENUM);
        voteDetailDto.setObtainIntegral(config.getAmount());
        voteDetailDto.setAddVote(0);
        activityParticipationDao.insertByPrimaryKeySelective(voteDetailDto);
        activityVoteRedisService.addCandidate(voteDetailDto.getActivityInfoId(),voteDetailDto.getKid(),voteDetailDto.getId(),0L);
        if(config.getAmount()!=0){
            try {
                scoreAPI.addScore(voteDetailDto.getCreateUserId().toString(), config.getAmount().intValue(), EventEnum.ADD_SCORE.getCode());
            } catch (Exception e) {
                logger.error("加积分异常",e);
            }
        }
        commitResource(voteDetailDto, adminActivityInfoVo1);
		return null;
    }

    private void commitResource(ActivityVoteDetail activityVoteDetail,AdminActivityInfoVo1 activityInfo) {
        try {
            ActivityVoteDetailResourceVo activityVoteDetailResourceVo = new ActivityVoteDetailResourceVo();
            BeanUtils.copyProperties(activityVoteDetailResourceVo, activityVoteDetail);
            activityVoteDetailResourceVo.setTitle(activityInfo.getTitle());
            ResourceTotal resourceTotal = new ResourceTotal();
            resourceTotal.setContent(activityVoteDetailResourceVo.getContent());
            resourceTotal.setCreateDate(DateUtils.getString(new Date()));
            resourceTotal.setExtJson(JsonUtils.toFastJson(activityVoteDetailResourceVo));
            resourceTotal.setModuleEnum(new Integer(activityVoteDetailResourceVo.getModuleEnum()));
            resourceTotal.setPublicState(ResourceEnum.PUBLIC_STATE_TRUE);
            resourceTotal.setResourceId(activityVoteDetailResourceVo.getKid());
            Response<UserSimpleVO> userSimple = userApi.getUserSimple(activityVoteDetailResourceVo.getCreateUserId());
            if(userSimple.success()
                    && userSimple.getData() != null
                    && userSimple.getData().getUserRole() != null) {
                resourceTotal.setTalentType(String.valueOf(userSimple.getData().getUserRole()));
            }

            resourceTotal.setTitle(activityInfo.getTitle());
            resourceTotal.setUserId(activityVoteDetailResourceVo.getCreateUserId());
            resourceDymaicApi.commitResourceDymaic(resourceTotal);
        } catch (Exception e) {
            logger.error("资源聚合 接入异常！", e);
        }
	}
}
