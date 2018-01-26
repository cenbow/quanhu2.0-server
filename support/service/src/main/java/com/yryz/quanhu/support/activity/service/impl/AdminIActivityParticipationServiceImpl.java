package com.yryz.quanhu.support.activity.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.yryz.common.response.PageList;
import com.yryz.quanhu.support.activity.dao.*;
import com.yryz.quanhu.support.activity.dto.*;
import com.yryz.quanhu.support.activity.entity.ActivityVoteConfig;
import com.yryz.quanhu.support.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.support.activity.service.AdminIActivityParticipationService;
import com.yryz.quanhu.support.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.support.activity.vo.AdminActivityVoteDetailVo;
import com.yryz.quanhu.support.activity.vo.AdminActivityVoteRecordVo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    
    /*@Autowired
    CustAPI custAPI;

    @Autowired
    CircleAPI circleAPI;
    
	@Autowired
	ScoreAPI scoreAPI;

	@Autowired
	CircleResourceAPI circleResourceReference;
	
	@Autowired
	UidApi uidReference;*/
	
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

            AdminConfigDto adminConfigDto = JSON.parseObject(config.getConfigSources(), new TypeReference<AdminConfigDto>() {
            });

            if (adminConfigDto == null) {
                return null;
            }

            if (StringUtils.isNotBlank(adminConfigDto.getCoverPlan())) {
                AdminConfigContentDto coverPlan = JSON.parseObject(adminConfigDto.getCoverPlan(), new TypeReference<AdminConfigContentDto>() {
                });
                adminConfigObjectDto.setCoverPlan(coverPlan);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getText())) {
                AdminConfigContentDto text = JSON.parseObject(adminConfigDto.getText(), new TypeReference<AdminConfigContentDto>() {
                });
                adminConfigObjectDto.setText(text);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getImgUrl())) {
                AdminConfigContentDto imgUrl = JSON.parseObject(adminConfigDto.getImgUrl(), new TypeReference<AdminConfigContentDto>() {
                });
                adminConfigObjectDto.setImgUrl(imgUrl);
            }
            if (StringUtils.isNotBlank(adminConfigDto.getVideoUrl())) {
                AdminConfigContentDto videoUrl = JSON.parseObject(adminConfigDto.getVideoUrl(), new TypeReference<AdminConfigContentDto>() {
                });
                adminConfigObjectDto.setVideoUrl(videoUrl);
            }
        }
        return adminConfigObjectDto;
    }

    /**
     * 弹框选择马甲
     */
    /*TODO@Override
    public ListPage<CustInfo> selectUser(String type, String nickName, String circleId, Integer pageNo, Integer pageSize) {
        ListPage<CustInfo> custInfoPage = null;
        if ("0".equals(type)) {
            List<String> custIdsByNickname = null;
            if (StringUtils.isNotBlank(nickName)) {
                custIdsByNickname = custAPI.getAdminList(nickName, null, null, null);
            }

            ListPage<CircleMember> circleVestPage = circleAPI.getAdminCircleVestList(circleId, custIdsByNickname, pageNo, pageSize);

            custInfoPage = new ListPage<>();

            custInfoPage.setTotalCount(circleVestPage.getTotalCount());
            custInfoPage.setPageNo(circleVestPage.getPageNo());
            custInfoPage.setPageSize(circleVestPage.getPageSize());

            Set<String> custIds = new HashSet<>();

            if (!circleVestPage.getList().isEmpty()) {
                for (CircleMember member : circleVestPage.getList()) {
                    custIds.add(member.getCustId());
                }
            }
            List<CustInfo> custInfos = new ArrayList<>();

            Map<String, CustSimpleDTO> custSimpleDTOMap = custAPI.getCustSimple(custIds);
            if (custSimpleDTOMap != null) {
                for (CustSimpleDTO custSimpleDTO : custSimpleDTOMap.values()) {
                    CustInfo custInfo = new CustInfo();
                    custInfo.setCustId(custSimpleDTO.getCustId());
                    custInfo.setCustNname(custSimpleDTO.getCustNname());
                    custInfo.setCustImg(custSimpleDTO.getCustImg());
                    custInfos.add(custInfo);
                }
            }

            custInfoPage.setList(custInfos);
        } else {
            custInfoPage = custAPI.getAdminCustInfoList(circleId, nickName, null, null, null, pageNo, pageSize);
        }
        return custInfoPage;
    }*/

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
        //maxId
        voteDetailDto.setVoteNo(voteNo + 1);
//    	voteDetailDto.setVoteNo(activityVoteDetailDao.selectMaxNoByActivityId(voteDetailDto.getActivityInfoId())+1);
  /*      voteDetailDto.setFreeVoteFlag((byte) 1);
        voteDetailDto.setVoteCount(0);*/
        voteDetailDto.setModuleEnum("1007");
      /*  TODO voteDetailDto.setResourceId(uidReference.getUID() + "");*/
        AdminActivityInfoVo1 adminActivityInfoVo1 = activityInfoDao.selectByPrimaryKey(voteDetailDto.getActivityInfoId());
        /*voteDetailDto.setObtainIntegral(adminActivityInfoVo1.getAmount());*/
        voteDetailDto.setAddVote(0);
        activityParticipationDao.insert(voteDetailDto);
        /*if(adminActivityInfoVo1.getAmount()!=0){
			*//*TODO scoreAPI.addScore(voteDetailDto.getCreateUserId(), adminActivityInfoVo1.getAmount().intValue(),"-1");*//*
		}*/
		if(null==voteDetailDto.getId() || voteDetailDto.getId().intValue()==0){
			logger.info("insert activity return null");
			Integer id = activityParticipationDao.selectMaxId();
			logger.info("insert activity getMaxId:"+id);
			commitResource(Long.valueOf(id), adminActivityInfoVo1);
		}else{
			commitResource(voteDetailDto.getId(), adminActivityInfoVo1);
		}
		return null;
    }
    private void commitResource(Long id,AdminActivityInfoVo1 activityInfo) {
		/*TODO AdminActivityVoteDetailVo activityVoteDetailVo = activityParticipationDao.selectByPrimaryKey(id);
		List<Resource> list =  new ArrayList<Resource>();
		Resource res = new Resource();
		res.setResourceId(activityVoteDetailVo.getResourceId());
		res.setCustId(activityVoteDetailVo.getCreateUserId());
		res.setResourceType(ResourceTypeEnum.WORK);
		res.setResourceTag("活动作品");
		res.setTitle(activityInfo.getTitle());
		res.setModuleEnum(activityVoteDetailVo.getModuleEnum());
		res.setPics(activityVoteDetailVo.getImgUrl());
		res.setVideo(activityVoteDetailVo.getVideoUrl());
		res.setVideoPic(activityVoteDetailVo.getVideoThumbnailUrl());
		res.setThumbnail(activityVoteDetailVo.getCoverPlan());
		res.setCreateTime(activityVoteDetailVo.getCreateDate() == null ? new Date().getTime() :activityVoteDetailVo.getCreateDate().getTime());//发布时间
		res.setUpdateTime(activityVoteDetailVo.getCreateDate() == null ? new Date().getTime() :activityVoteDetailVo.getCreateDate().getTime());//修改时间
		res.setExtjson(JsonUtils.toFastJson(activityVoteDetailVo));//对象转json
		res.setPublicState(0);
		res.setCircleRoute("activity");
		res.setTalentType("0");
		res.setSummary(activityVoteDetailVo.getText());
		res.setContent(activityVoteDetailVo.getText());
		res.setHeat(0L);
		res.setReadNum(0L);
		res.setPartNum(0L);
		res.setOrderby(0L);
		list.add(res);
		circleResourceReference.commitResource(list);*/
	}

	/*@Override
    public List<CircleInfo> circleList() {
        return circleAPI.getCircleList(null, null);
    }*/
}
