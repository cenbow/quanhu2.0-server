package com.yryz.quanhu.other.activity.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.api.CountStatisticsApi;
import com.yryz.quanhu.behavior.count.dto.CountStatisticsDto;
import com.yryz.quanhu.other.activity.dao.ActivityVoteDetailDao;
import com.yryz.quanhu.other.activity.dao.ActivityVoteRecordDao;
import com.yryz.quanhu.other.activity.dto.AdminActivityCountDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteDetailDto;
import com.yryz.quanhu.other.activity.dto.AdminActivityVoteRecordDto;
import com.yryz.quanhu.other.activity.entity.ActivityVoteDetail;
import com.yryz.quanhu.other.activity.service.AdminActivityCountService;
import com.yryz.quanhu.other.activity.service.AdminActivityVoteService;
import com.yryz.quanhu.other.activity.vo.AdminActivityCountVo;
import com.yryz.quanhu.other.activity.vo.AdminActivityInfoVo1;
import com.yryz.quanhu.other.activity.vo.AdminActivityVoteDetailVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AdminActivityCountServiceImpl implements AdminActivityCountService {

    @Reference(check = false, timeout = 30000)
    CountStatisticsApi countStatisticsApi;

    @Autowired
    AdminActivityVoteService adminActivityVoteService;

    @Autowired
    ActivityVoteRecordDao activityVoteRecordDao;

    @Autowired
    ActivityVoteDetailDao activityParticipationDao;

    private static final Logger logger = LoggerFactory.getLogger(AdminActivityCountServiceImpl.class);

    /**
     * 获取活动统计数量
     * @param   adminActivityCountDto
     * @return
     * */
    public PageList<AdminActivityCountVo> activityCount(AdminActivityCountDto adminActivityCountDto) {
        AdminActivityInfoVo1 activityDetail = adminActivityVoteService.getActivityDetail(adminActivityCountDto.getActivityInfoId());
        if(adminActivityCountDto.getStartDate() == null) {
            if(activityDetail == null) {
                adminActivityCountDto.setStartDate(new Date());
            } else {
                adminActivityCountDto.setStartDate(activityDetail.getOnlineTime());
            }
        }
        if(adminActivityCountDto.getEndDate() == null) {
            adminActivityCountDto.setEndDate(new Date());
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            adminActivityCountDto.setStartDate(sdf.parse(sdf.format(adminActivityCountDto.getStartDate())));
            adminActivityCountDto.setEndDate(sdf.parse(sdf.format(adminActivityCountDto.getEndDate())));
        } catch (ParseException e) {
            logger.error("时间转换异常",e);
        }
        Map<String, Long> recordMap = new HashMap<>();
        //获取活动投票数
        AdminActivityVoteRecordDto adminActivityVoteRecordDto = new AdminActivityVoteRecordDto();
        adminActivityVoteRecordDto.setActivityInfoId(adminActivityCountDto.getActivityInfoId());
        adminActivityVoteRecordDto.setBeginCreateDate(adminActivityCountDto.getStartDate());
        adminActivityVoteRecordDto.setEndCreateDate(adminActivityCountDto.getEndDate());
        List<AdminActivityCountVo> activityCountVos = activityVoteRecordDao.selectTotalCount(adminActivityVoteRecordDto);
        if(!CollectionUtils.isEmpty(activityCountVos)) {
            activityCountVos.stream().forEach(vo -> recordMap.put(vo.getDate(), vo.getTotalNo().longValue()));
        }
        Map<String, Long> mapPage = null;
        Map<String, Long> mapPageCandidate = null;
        //获取活动浏览数
        CountStatisticsDto countStatisticsDto = new CountStatisticsDto();
        countStatisticsDto.setKid(adminActivityCountDto.getKid());
        countStatisticsDto.setPage(adminActivityCountDto.getPage());
        countStatisticsDto.setStartDate(adminActivityCountDto.getStartDate());
        countStatisticsDto.setEndDate(adminActivityCountDto.getEndDate());
        if(!StringUtils.isEmpty(adminActivityCountDto.getPage())) {
            Response<Map<String, Long>> mapResponse = countStatisticsApi.countModelMap(countStatisticsDto);
            if(mapResponse.success()) {
                mapPage = mapResponse.getData();
            }
        }
        AdminActivityVoteDetailDto adminActivityVoteDetailDto = new AdminActivityVoteDetailDto();
        adminActivityVoteDetailDto.setActivityInfoId(countStatisticsDto.getKid());
        List<AdminActivityCountVo> list = this.getDate(countStatisticsDto.getStartDate(), countStatisticsDto.getEndDate());
        if(!CollectionUtils.isEmpty(list)) {
            Integer preCount = 0;
            Integer preCandidateCount = 0;
            for(int i=list.size()-1; i>-1; i--) {
                AdminActivityCountVo adminActivityCountVo = list.get(i);
                adminActivityCountVo.setTotalNo(recordMap.get(adminActivityCountVo.getDate()) == null
                        ? 0 : recordMap.get(adminActivityCountVo.getDate()).intValue());
                if(mapPage != null) {
                    Integer count = mapPage.get(adminActivityCountVo.getDate()) == null
                            ? 0 : mapPage.get(adminActivityCountVo.getDate()).intValue();
                    if(count != 0) {
                        adminActivityCountVo.setDetailCount(count - preCount > 0 ? count - preCount : 0);
                        preCount = count;
                    } else {
                        adminActivityCountVo.setDetailCount(count);
                    }
                }
                List<AdminActivityVoteDetailVo> activityVoteDetailList =activityParticipationDao.select(adminActivityVoteDetailDto);
                Integer candidateCount = 0;
                if (!CollectionUtils.isEmpty(activityVoteDetailList)){
                    for (AdminActivityVoteDetailVo adminActivityVoteDetailVo :activityVoteDetailList){
                        if(!StringUtils.isEmpty(adminActivityCountDto.getPageCandidate())) {
                            countStatisticsDto.setKid(adminActivityVoteDetailVo.getKid());
                            countStatisticsDto.setPage(adminActivityCountDto.getPageCandidate());
                            Response<Map<String, Long>> mapResponse = countStatisticsApi.countModelMap(countStatisticsDto);
                            if(mapResponse.success()) {
                                mapPageCandidate = mapResponse.getData();
                            }
                            if(mapPageCandidate != null) {
                                Integer count = mapPageCandidate.get(adminActivityCountVo.getDate()) == null
                                        ? 0 : mapPageCandidate.get(adminActivityCountVo.getDate()).intValue();
                                candidateCount += count;
                            }
                        }
                        countStatisticsDto.setKid(adminActivityVoteDetailVo.getActivityInfoId());
                    }
                }
                if(candidateCount != 0) {
                    adminActivityCountVo.setCandidateDetailCount(candidateCount - preCandidateCount > 0 ? candidateCount - preCandidateCount : 0);
                    preCandidateCount = candidateCount;
                } else {
                    adminActivityCountVo.setCandidateDetailCount(candidateCount);
                }
              /*  StringBuffer sb = new StringBuffer();
                String s = sb.append(adminActivityCountVo.getDate()).insert(4,"-").toString();
                sb = new StringBuffer();
                s = sb.append(s).insert(7,"-").toString();
                adminActivityCountVo.setDate(s);*/
            }
        }

        if(activityDetail.getActivityType()==11){
            Collections.sort(list, new Comparator<AdminActivityCountVo>() {
                @Override
                public int compare(AdminActivityCountVo o1, AdminActivityCountVo o2) {
                    return (o1.getDetailCount()==o2.getDetailCount())?0:(o2.getDetailCount()-o1.getDetailCount());
                }
            });
        }
        PageList<AdminActivityCountVo> pageList = new PageList<>();
        pageList.setCurrentPage(adminActivityCountDto.getCurrentPage());
        pageList.setPageSize(adminActivityCountDto.getPageSize());
        pageList.setCount((long) list.size());
        int currentPage = this.getCurrentPage(adminActivityCountDto.getCurrentPage(), adminActivityCountDto.getPageSize(), list.size());
        int pageSize = this.getPageSize(adminActivityCountDto.getCurrentPage(), adminActivityCountDto.getPageSize(), list.size());
        if(list.size() > 0) {
            list = list.subList(currentPage, pageSize);
        }
        pageList.setEntities(list);

        return pageList;
    }

    /**
     * 获取活动统计数量总和
     * @param   adminActivityCountDto
     * @return
     * */
    public AdminActivityCountVo activityTotalCount(AdminActivityCountDto adminActivityCountDto) {
        if(adminActivityCountDto.getStartDate() == null) {
            AdminActivityInfoVo1 activityDetail = adminActivityVoteService.getActivityDetail(adminActivityCountDto.getActivityInfoId());
            if(activityDetail == null) {
                adminActivityCountDto.setStartDate(new Date());
            }
            adminActivityCountDto.setStartDate(activityDetail.getBeginTime());
        }
        if(adminActivityCountDto.getEndDate() == null) {
            adminActivityCountDto.setEndDate(new Date());
        }
        AdminActivityCountVo adminActivityCountVo = new AdminActivityCountVo();
        //获取活动投票数
        AdminActivityVoteRecordDto adminActivityVoteRecordDto = new AdminActivityVoteRecordDto();
        adminActivityVoteRecordDto.setActivityInfoId(adminActivityCountDto.getActivityInfoId());
        adminActivityVoteRecordDto.setBeginCreateDate(adminActivityCountDto.getStartDate());
        adminActivityVoteRecordDto.setEndCreateDate(adminActivityCountDto.getEndDate());
        int count = activityVoteRecordDao.selectRecordVoteCount(adminActivityVoteRecordDto);
        adminActivityCountVo.setTotalNo(count);

        Map<String, Long> mapPage = null;
        Map<String, Long> mapPageCandidate = null;
        //获取活动浏览数
        CountStatisticsDto countStatisticsDto = new CountStatisticsDto();
        countStatisticsDto.setKid(adminActivityCountDto.getKid());
        countStatisticsDto.setPage(adminActivityCountDto.getPage());
        countStatisticsDto.setStartDate(adminActivityCountDto.getStartDate());
        countStatisticsDto.setEndDate(adminActivityCountDto.getEndDate());
        if(!StringUtils.isEmpty(adminActivityCountDto.getPage())) {
            Response<Map<String, Long>> mapResponse = countStatisticsApi.countModelMap(countStatisticsDto);
            if(mapResponse.success()) {
                mapPage = mapResponse.getData();
            }
        }
        if(mapPage != null) {
            Long pageCount = 0L;
            if(mapPage.get("count") != null) {
                pageCount = mapPage.get("count");
            }
            adminActivityCountVo.setDetailCount(pageCount.intValue());
        }
        AdminActivityVoteDetailDto adminActivityVoteDetailDto = new AdminActivityVoteDetailDto();
        adminActivityVoteDetailDto.setActivityInfoId(countStatisticsDto.getKid());
        List<AdminActivityVoteDetailVo> activityVoteDetailList =activityParticipationDao.select(adminActivityVoteDetailDto);
        Integer candidateCount = 0;
        if (!CollectionUtils.isEmpty(activityVoteDetailList)){
            for (AdminActivityVoteDetailVo adminActivityVoteDetailVo :activityVoteDetailList){
                if(!StringUtils.isEmpty(adminActivityCountDto.getPageCandidate())) {
                    countStatisticsDto.setKid(adminActivityVoteDetailVo.getKid());
                    countStatisticsDto.setPage(adminActivityCountDto.getPageCandidate());
                    Response<Map<String, Long>> mapResponse = countStatisticsApi.countModelMap(countStatisticsDto);
                    if(mapResponse.success()) {
                        mapPageCandidate = mapResponse.getData();
                    }
                    if(mapPageCandidate != null) {
                        Integer c = mapPageCandidate.get("count") == null
                                ? 0 : mapPageCandidate.get("count").intValue();
                        candidateCount += c;
                    }
                }
                countStatisticsDto.setKid(adminActivityVoteDetailVo.getActivityInfoId());
            }
        }
        adminActivityCountVo.setCandidateDetailCount(candidateCount);

        return adminActivityCountVo;
    }

    /**
     * 获取时间差
     * @param   startDate
     * @param   endDate
     * @return
     * */
    private List<AdminActivityCountVo> getDate(Date startDate, Date endDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c = Calendar.getInstance();
        c.setTime(endDate);
        List<AdminActivityCountVo> list = new ArrayList<>();
        long day = ((endDate.getTime() - startDate.getTime()) / (1000 * 3600 * 24))+1;
        for(int i=0; i<day; i++) {
            String date = sdf.format(c.getTime());
            AdminActivityCountVo adminActivityCountVo = new AdminActivityCountVo();
            adminActivityCountVo.setDate(date);
            list.add(adminActivityCountVo);
            c.add(Calendar.DATE, -1);
        }

        return list;
    }

    public int getCurrentPage(Integer currentPage, Integer pageSize, Integer total) {
        Integer pageNo = (currentPage -1) * pageSize;
        return pageNo > total ? total : pageNo;
    }

    public int getPageSize(Integer currentPage, Integer pageSize, Integer total) {
        Integer size = ((currentPage -1) * pageSize) + pageSize;
        return size > total ? total : size;
    }

    public static void main(String[] args) {
        String a = "20180701";
        StringBuffer sb = new StringBuffer();
        a = sb.append(a).insert(4,"-").toString();
        sb = new StringBuffer();
        sb.append(a).insert(7,"-");
        System.out.println(sb.toString());
    }


}
