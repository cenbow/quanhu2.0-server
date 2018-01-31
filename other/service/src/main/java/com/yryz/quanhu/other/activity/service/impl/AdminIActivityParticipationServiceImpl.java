package com.yryz.quanhu.other.activity.service.impl;


import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.yryz.common.constant.ModuleContants;
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
import com.yryz.quanhu.other.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.other.activity.service.AdminIActivityParticipationService;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteRecordVo;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.enums.ResourceTypeEnum;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import com.yryz.quanhu.score.enums.EventEnum;
import com.yryz.quanhu.score.service.ScoreAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.dto.AdminUserInfoDTO;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    /**
     * 增加票数
     */
    @Override
    public Integer addVote(Long id, Integer count) {

        return activityParticipationDao.updateAddVote(id, count);
    }

    @Override
    public Integer updateStatus(Long id, Byte status) {

        return activityParticipationDao.updateStatus(id, status);
    }

    /**
     * 参与活动列表
     */
    @Override
    public List<AdminActivityVoteDetailVo> list(AdminActivityVoteDetailDto adminActivityVoteDetailDto) {
        if (null == adminActivityVoteDetailDto.getActivityInfoId()) {
            logger.debug("请通过投票活动列表进入！！！");
            isNull(adminActivityVoteDetailDto.getActivityInfoId(), "活动ID不能为空，请通过投票活动列表进入！！！");
        }
        List<AdminActivityVoteDetailVo> list = null;
        if (StringUtils.isNotBlank(adminActivityVoteDetailDto.getNickName()) || StringUtils.isNotBlank(adminActivityVoteDetailDto.getPhone())|| adminActivityVoteDetailDto.getBeginCreateDate()!=null|| adminActivityVoteDetailDto.getEndCreateDate()!=null) {
            /*TODO List<String> custIds = custAPI.getAdminList(adminActivityVoteDetailDto.getNickName(), adminActivityVoteDetailDto.getPhone(), null, null);
            if(!CollectionUtils.isEmpty(custIds)){
            	adminActivityVoteDetailDto.setCustIds(custIds);
            	list = activityParticipationDao.select(adminActivityVoteDetailDto);
            }*/
           
        }
        else{
       	 list = activityParticipationDao.select(adminActivityVoteDetailDto);
       }

       if(CollectionUtils.isEmpty(list)){
    	   return list;
       }

        //票数占比：显示对应内容在当前活动中所获票数占总票数的比值，到小数点后4位；
        Integer sum = activityVoteRecordDao.selectCountByActivityInfoId(adminActivityVoteDetailDto.getActivityInfoId(), null);

        for (AdminActivityVoteDetailVo voteDetailVo : list) {
           /* TODO CustInfo custInfo = custAPI.getCustInfo(voteDetailVo.getCreateUserId());
            voteDetailVo.setNickName(custInfo.getCustNname());
            voteDetailVo.setPhone(custInfo.getCustPhone());*/
            voteDetailVo.setUrl(getUrl(voteDetailVo));

            Integer voteCount = activityVoteRecordDao.selectCountByActivityInfoId(adminActivityVoteDetailDto.getActivityInfoId(), voteDetailVo.getVoteNo());
            double data = 0.00;
            if (sum != 0 && voteCount != 0) {
                data = (double) voteCount / (double) sum;
            }
            String voteProportion = new DecimalFormat("0.0000").format(data);
            voteDetailVo.setVoteProportion(voteProportion);
        }

        return list;
    }
  
    /**
     * 参与活动列表分页
     */
    @Override
    public PageList adminlistDetail(AdminActivityVoteDetailDto adminActivityVoteDetailDto) {
    	Integer pageNo = adminActivityVoteDetailDto.getPageNo();
		Integer pageSize = adminActivityVoteDetailDto.getPageSize();
		  List<AdminActivityVoteDetailVo> list =null;
		if(adminActivityVoteDetailDto.getPageNo()==null|| adminActivityVoteDetailDto.getPageNo()<=0){
			adminActivityVoteDetailDto.setPageNo(0);
		}else{
			adminActivityVoteDetailDto.setPageNo((adminActivityVoteDetailDto.getPageNo()-1)* adminActivityVoteDetailDto.getPageSize());
		}
		if(adminActivityVoteDetailDto.getPageSize()==null|| adminActivityVoteDetailDto.getPageSize()<=0){
			adminActivityVoteDetailDto.setPageSize(10);
		}	
        if (null == adminActivityVoteDetailDto.getActivityInfoId()) {
            logger.debug("请通过投票活动列表进入！！！");
            isNull(adminActivityVoteDetailDto.getActivityInfoId(), "活动ID不能为空，请通过投票活动列表进入！！！");
        }
        if (StringUtils.isNotBlank(adminActivityVoteDetailDto.getNickName()) || StringUtils.isNotBlank(adminActivityVoteDetailDto.getPhone())|| adminActivityVoteDetailDto.getBeginCreateDate()!=null|| adminActivityVoteDetailDto.getEndCreateDate()!=null) {
            /*TODO List<String> custIds = custAPI.getAdminList(adminActivityVoteDetailDto.getNickName(), adminActivityVoteDetailDto.getPhone(), null, null);
            if(!CollectionUtils.isEmpty(custIds))
            {
             adminActivityVoteDetailDto.setCustIds(custIds);
             list = activityParticipationDao.selectPage(adminActivityVoteDetailDto);
            }*/
        }
        else{
        	 list = activityParticipationDao.selectPage(adminActivityVoteDetailDto);
        }
        
        if (CollectionUtils.isEmpty(list)) {
			return new PageList(pageNo, pageSize, list, 0L);
		}
       

        //票数占比：显示对应内容在当前活动中所获票数占总票数的比值，到小数点后4位；
        Integer sum = activityVoteRecordDao.selectCountByActivityInfoId(adminActivityVoteDetailDto.getActivityInfoId(), null);

        for (AdminActivityVoteDetailVo voteDetailVo : list) {
           /* TODO CustInfo custInfo = custAPI.getCustInfo(voteDetailVo.getCreateUserId());
            voteDetailVo.setNickName(custInfo.getCustNname());
            voteDetailVo.setPhone(custInfo.getCustPhone());*/
            voteDetailVo.setUrl(getUrl(voteDetailVo));

            Integer voteCount = activityVoteRecordDao.selectCountByActivityInfoId(adminActivityVoteDetailDto.getActivityInfoId(), voteDetailVo.getVoteNo());
            double data = 0.00;
            if (sum != 0 && voteCount != 0) {
                data = (double) voteCount / (double) sum;
            }
            String voteProportion = new DecimalFormat("0.0000").format(data);
            voteDetailVo.setVoteProportion(voteProportion);
        }

      
		return new PageList(pageNo, pageSize, list, (long)activityParticipationDao.select(adminActivityVoteDetailDto).size());
    }

    @Override
    public List<AdminActivityVoteRecordVo> voteList(AdminActivityVoteRecordDto adminActivityVoteRecordDto) {

        if (StringUtils.isNotBlank(adminActivityVoteRecordDto.getNickName())|| adminActivityVoteRecordDto.getBeginCreateDate()!=null|| adminActivityVoteRecordDto.getEndCreateDate()!=null) {
        	String nickName=null;
        	String  beginDate=null;
        	String  endDate=null;
        	SimpleDateFormat sdf =   new SimpleDateFormat( " yyyy-MM-dd HH:mm:ss " ); 
        	if(StringUtils.isNotBlank(adminActivityVoteRecordDto.getNickName())){
        		nickName= adminActivityVoteRecordDto.getNickName();
        	}
        	if(adminActivityVoteRecordDto.getBeginCreateDate()!=null|| adminActivityVoteRecordDto.getEndCreateDate()!=null)
        	{
        		 beginDate=sdf.format(adminActivityVoteRecordDto.getBeginCreateDate());
            	 endDate=sdf.format(adminActivityVoteRecordDto.getEndCreateDate());
        	}
        	
            /*TODO List<String> custIds = custAPI.getAdminList(nickName, null, beginDate, endDate);
            if (!CollectionUtils.isEmpty(custIds)) {
                adminActivityVoteRecordDto.setCustIds(custIds);
            }*/
        }

        List<AdminActivityVoteRecordVo> list = activityVoteRecordDao.select(adminActivityVoteRecordDto);

        for (AdminActivityVoteRecordVo voteRecord : list) {
            /*TODO CustInfo custInfo = custAPI.getCustInfo(voteRecord.getCreateUserId());
            voteRecord.setNickName(custInfo.getCustNname());
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
            try {
				voteRecord.setCreateDate(sdf.parse(custInfo.getCreateDate().substring(0, 18)));
			} catch (ParseException e) {
				 
				e.printStackTrace();
			}
            voteRecord.setPhone(custInfo.getCustPhone());*/
        }

        return list;
    }
    
    @Override
    public  PageList adminlist(AdminActivityVoteRecordDto adminActivityVoteRecordDto) {
        
    	Integer pageNo = adminActivityVoteRecordDto.getPageNo();
		Integer pageSize = adminActivityVoteRecordDto.getPageSize();
		if(adminActivityVoteRecordDto.getPageNo()==null|| adminActivityVoteRecordDto.getPageNo()<=0){
			adminActivityVoteRecordDto.setPageNo(0);
		}else{
			adminActivityVoteRecordDto.setPageNo((adminActivityVoteRecordDto.getPageNo()-1)* adminActivityVoteRecordDto.getPageSize());
		}
		if(adminActivityVoteRecordDto.getPageSize()==null|| adminActivityVoteRecordDto.getPageSize()<=0){
			adminActivityVoteRecordDto.setPageSize(10);
		}	
		String nickName=null;
    	String  beginDate=null;
    	String  endDate=null;
    	List<String> custIds =new ArrayList<String>();;
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
        	
             /*TODO custIds = custAPI.getAdminList(nickName, null, beginDate, endDate);*/
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
            /* TODO CustInfo custInfo = custAPI.getCustInfo(voteRecord.getCreateUserId());
            voteRecord.setNickName(custInfo.getCustNname());
            SimpleDateFormat sdf =   new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" ); 
            try {
				voteRecord.setCreateDate(sdf.parse(custInfo.getCreateDate().substring(0, 18)));
			} catch (ParseException e) {
				 
				e.printStackTrace();
			}
            voteRecord.setPhone(custInfo.getCustPhone());*/
        }
        
		 
		if (CollectionUtils.isEmpty(list)) {
			return new PageList(pageNo, pageSize, list, 0L);
		}
		return new PageList(pageNo, pageSize, list, (long)activityVoteRecordDao.select(adminActivityVoteRecordDto).size());
    }

    public AdminActivityInfoVo1 detail(Long infoId) {
        if (null != infoId) {
            return activityVoteService.getActivityDetail(infoId);
        }
        return null;
    }

    //平台获得前台页面规则
    private String getUrl(AdminActivityVoteDetailVo voteDetailVo) {

//  http://m-dev.quanhu365.com/activity/platform-activity/vote/93/detail/57

        /*TODO String frontURL = Global.getConfig("front.server.url");

        if (null != voteDetailVo.getActivityInfoId()) {
            return frontURL + "activity/platform-activity/vote/" + voteDetailVo.getActivityInfoId() + "/detail/" + voteDetailVo.getId() + "?custId=XXX";
        }*/
        return "";
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
        Response<Page<UserBaseInfoVO>> userInfoPage = userApi.listUserInfo(pageNo,pageSize,custInfoDTO);
        PageList<UserBaseInfoVO> pageList = new PageList<UserBaseInfoVO>();
        if (userInfoPage.success()){
            pageList.setCount(userInfoPage.getData().getTotal());
            pageList.setEntities(userInfoPage.getData().getResult());
            pageList.setCurrentPage(pageNo);
            pageList.setPageSize(pageSize);
        }
        return pageList;
    }

    public String saveVoteDetail(AdminActivityVoteDetailDto voteDetailDto) {

        //在一个活动里一个用户只能参与一次
        List<AdminActivityVoteDetailVo> voteDetailVos = activityParticipationDao.selectByParam(voteDetailDto);

        if ( ! CollectionUtils.isEmpty(voteDetailVos)) {
           return "此用户已参加此次活动，不能重复参加活动" ;
        }

        Integer voteNo = activityParticipationDao.maxId(voteDetailDto.getActivityInfoId());

        if (voteNo == null) {
            voteNo = 0;
        }
        voteDetailDto.setKid(idApi.getSnowflakeId().getData());
        //maxId
        voteDetailDto.setVoteNo(voteNo + 1);
        voteDetailDto.setModuleEnum(ModuleContants.ACTIVITY_WORKS_ENUM);
        AdminActivityInfoVo1 adminActivityInfoVo1 = activityInfoDao.selectByPrimaryKey(voteDetailDto.getActivityInfoId());
        ActivityVoteConfig config = activityVoteConfigDao.selectVoteByActivityInfoId(voteDetailDto.getActivityInfoId());
        voteDetailDto.setObtainIntegral(config.getAmount());
        voteDetailDto.setAddVote(0);
        activityParticipationDao.insertByPrimaryKeySelective(voteDetailDto);
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
    private void commitResource(AdminActivityVoteDetailDto voteDetail,AdminActivityInfoVo1 activityInfo) {
        try {
            ResourceTotal resourceTotal = new ResourceTotal();
            resourceTotal.setContent(voteDetail.getContent());
            resourceTotal.setCreateDate(DateUtils.getString(new Date()));
            resourceTotal.setExtJson(JsonUtils.toFastJson(voteDetail));
            resourceTotal.setModuleEnum(new Integer(voteDetail.getModuleEnum()));
            resourceTotal.setPublicState(ResourceEnum.PUBLIC_STATE_TRUE);
            resourceTotal.setResourceId(voteDetail.getKid());

            Response<UserSimpleVO> userSimple = userApi.getUserSimple(voteDetail.getCreateUserId());
            if(userSimple.success()
                    && userSimple.getData() != null
                    && userSimple.getData().getUserRole() != null) {
                resourceTotal.setTalentType(String.valueOf(userSimple.getData().getUserRole()));
            }

            resourceTotal.setTitle(activityInfo.getTitle());
            resourceTotal.setUserId(voteDetail.getCreateUserId());
            resourceDymaicApi.commitResourceDymaic(resourceTotal);
        } catch (Exception e) {
            logger.error("资源聚合 接入异常！", e);
        }
	}

	/*@Override
    public List<CircleInfo> circleList() {
        return circleAPI.getCircleList(null, null);
    }*/
}
