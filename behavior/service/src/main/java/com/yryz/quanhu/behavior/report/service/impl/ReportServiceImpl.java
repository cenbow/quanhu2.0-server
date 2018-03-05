package com.yryz.quanhu.behavior.report.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.PageModel;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.behavior.report.contants.ReportConstatns;
import com.yryz.quanhu.behavior.report.dao.ReportDao;
import com.yryz.quanhu.behavior.report.dto.ReportDTO;
import com.yryz.quanhu.behavior.report.service.ReportService;
import com.yryz.quanhu.behavior.report.vo.ReportVo;
import com.yryz.quanhu.resource.enums.ResourceModuleEnum;
import com.yryz.quanhu.resource.questionsAnswers.api.AnswerApi;
import com.yryz.quanhu.resource.questionsAnswers.api.QuestionApi;
import com.yryz.quanhu.resource.questionsAnswers.dto.AnswerDto;
import com.yryz.quanhu.resource.release.info.api.ReleaseInfoApi;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.topic.api.TopicApi;
import com.yryz.quanhu.resource.topic.api.TopicPostApi;
import com.yryz.quanhu.support.config.api.BasicConfigApi;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.contants.ViolatType;
import com.yryz.quanhu.user.service.ViolationApi;
import com.yryz.quanhu.user.vo.ViolationInfo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 14:44 2018/1/22
 */
@Service
@Transactional
public class ReportServiceImpl implements ReportService {


    private static final Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);

    @Autowired
    private ReportDao reportDao;

    @Reference(check = false)
    private IdAPI idAPI;

    @Reference(check = false)
    private CountApi countApi;

    @Reference(check = false)
    private BasicConfigApi basicConfigApi;



    @Override
    public int submit(ReportDTO dto) {
        int updateCount = 0;
        try{
            String moduleEnumCode = dto.getModuleEnum();
            ResourceModuleEnum moduleEnum = ResourceModuleEnum.get(moduleEnumCode);
            if(moduleEnum == null){
                throw new RuntimeException("参数非法："+moduleEnumCode);
            }
            String reportDesc = getReportTypeName(dto.getReportType());
            if(StringUtils.isBlank(reportDesc)){
                throw new RuntimeException("参数非法："+dto.getReportType());
            }


            if(ResourceModuleEnum.USER == moduleEnum){
                //判断举报为用户时,资源用户ID为被举报人用户ID
                dto.setResourceUserId(dto.getCreateUserId());
            }else{
                //判断资源类型时，反查询资源作者ID？
                dto.setResourceUserId(0);
            }

            //每次接口生成新的举报记录
            dto.setKid(idAPI.getKid("qh_report").getData());
            dto.setLastUpdateUserId(dto.getCreateUserId());
            dto.setModuleEnumName(moduleEnum.getName());
            dto.setReportDesc(reportDesc);
            dto.setSolutionType(ReportConstatns.SOLUTION.WAIT.getType());   //默认待处理
            dto.setSolutionDesc("");
            dto.setReportStatus(10);                                        //带处理
            dto.setDelFlag(10);
            updateCount = reportDao.insert(dto);

            //资源举报计数{捕获异常}
            try{
                logger.info("countApi.commitCount.start={}",dto.getResourceId());
                countApi.commitCount(BehaviorEnum.Report,Long.parseLong(dto.getResourceId()),"",1L);
                logger.info("countApi.commitCount.finish={}",dto.getResourceId());
            }catch (Exception e){
                logger.error("资源举报计数异常",e);
            }

        }catch (Exception e){
            throw e;
        }
        return updateCount;
    }

    @Override
    public Boolean solution(ReportDTO dto) {
        int updateCount = 0;
        try{
            /**
             * 查询举报信息
             */
            ReportDTO dbReport = reportDao.selectByKid(ReportDTO.class,dto.getKid());
            dbReport.setLastUpdateUserId(dto.getLastUpdateUserId());

            /**
             * 查询改举报的资源，是否已经被其他处理
             */
            dbReport.setReportStatus(11);       //已处理的
            List<ReportDTO> existResources = reportDao.selectByResource(dbReport);
            if(!CollectionUtils.isEmpty(existResources)){
                throw new RuntimeException("该资源已经处理被处理,处理结果:["+existResources.get(0).getSolutionDesc()+"]");
            }

            //判断解决方案
            ReportSolutionImpl solutionImpl = new ReportSolutionImpl();
            boolean solutionResult = false;

            //判断方案
            ReportConstatns.SOLUTION solution = ReportConstatns.SOLUTION.valueOf(dto.getSolutionType());
            switch (solution){
                case SHELVE:                //下架
                    solutionResult = solutionImpl.shelveResource(dbReport);
                    break;
                case MUTE:                  //禁言
                    solutionResult = solutionImpl.violationUser(dbReport,ViolatType.NOTALK);
                    break;
                case WARN:                  //警告
                    solutionResult = solutionImpl.violationUser(dbReport,ViolatType.WARN);
                    break;
                case FREEZE:                //冻结
                    solutionResult = solutionImpl.violationUser(dbReport,ViolatType.FREEZE);
                    break;
                case LOCK:                  //锁定
                    solutionResult = solutionImpl.violationUser(dbReport,ViolatType.DISTORY);
                    break;
                case KEEP:                  //维持现状
                    solutionResult = true;
                    break;
                default:
                    throw new RuntimeException("参数非法："+dto.getSolutionType());
            }

            if(!solutionResult){
                throw new RuntimeException("服务回调异常："+solutionResult);
            }
            /**
             * 更新举报信息
             */
            dbReport.setReportStatus(11);   //已解决
            dbReport.setSolutionType(solution.getType());
            dbReport.setSolutionDesc(solution.getDesc());

            updateCount = reportDao.update(dbReport);

        }catch (Exception e){
            throw e;
        }
        return updateCount==1?true:false;
    }

    @Reference
    private ReleaseInfoApi releaseInfoApi;
    @Reference
    private TopicApi topicApi;
    @Reference
    private TopicPostApi topicPostApi;
    @Reference
    private QuestionApi questionApi;
    @Reference
    private AnswerApi answerApi;
    @Reference
    private ViolationApi violationApi;

    /**
     * 举报解决实现
     */
    private class ReportSolutionImpl{

        /**
         * 资源，调用各资源的下架接口
         * @param dto
         * @return
         */
        public boolean shelveResource(ReportDTO dto){

            //待处理资源类型
            String moduleEnumCode = dto.getModuleEnum();

            //调用不同实现处理
            ResourceModuleEnum moduleEnum = ResourceModuleEnum.get(moduleEnumCode);
            Response<Integer> syncRpc = null;
            switch (moduleEnum){
                case COTERIE:                   //私圈
                    throw new RuntimeException("暂不支持私圈下架（未实现）");
                case USER:                      //用户
                    throw new RuntimeException("暂不支持用户下架（未实现）");
                case TRANSMIT:                  //转发
                    throw new RuntimeException("暂不支持动态转发下架（未实现）");
                case RELEASE:                   //文字

                    ReleaseInfo info = new ReleaseInfo();
                    info.setKid(Long.parseLong(dto.getResourceId()));
                    info.setLastUpdateUserId(dto.getLastUpdateUserId());

                    syncRpc = releaseInfoApi.deleteBykid(info);
                    break;
                case TOPIC:                     //话题
                    syncRpc = topicApi.deleteTopic(Long.parseLong(dto.getResourceId()),dto.getLastUpdateUserId());
                    break;
                case POSTS:                     //帖子
                    syncRpc = topicPostApi.deleteTopicPost(Long.parseLong(dto.getResourceId()),dto.getLastUpdateUserId());
                    break;
                case QUESTION:                  //问题
                    syncRpc = questionApi.deleteQuestion(Long.parseLong(dto.getResourceId()),dto.getLastUpdateUserId());
                    break;
                case ANSWER:                    //答案

                    AnswerDto answerDto = new AnswerDto();
                    answerDto.setKid(Long.parseLong(dto.getResourceId()));
                    answerDto.setLastUpdateUserId(dto.getLastUpdateUserId());
                    syncRpc = answerApi.deletetAnswer(answerDto);

                    break;
                default:
                    throw new RuntimeException("参数非法,moduleEnum："+moduleEnum);
            }

            if(syncRpc.success()&&syncRpc.getData()==1){
                return true;
            }else{
                throw new RuntimeException("同步下架资源异常："+syncRpc.getErrorMsg());
            }
        }

        /**
         * 用户，
         * @param dto
         * @return
         */
        public boolean violationUser(ReportDTO dto, ViolatType type){
            ViolationInfo info = new ViolationInfo();
            info.setUserId(dto.getResourceId());
            info.setViolationType((byte) type.getType());;
            return violationApi.addViolation(info).getData();
        }
    }


    public String getReportTypeName(String typeKey){
        List<ReportVo> out = getReportType(null);
        for (ReportVo vo : out) {
            if(vo.getType().equalsIgnoreCase(typeKey)){
                return vo.getDesc();
            }
        }
        return null;
    }
    /**
     * 通过配置中心获取
     * @param dto
     * @return
     */
    @Override
    public List<ReportVo> getReportType(ReportDTO dto) {
        List<ReportVo> out = new ArrayList<>();

        Response<String> rpc = basicConfigApi.getValue("reportDesc");

        if(rpc.success()){
            /**
             *配置中心json数据转换为对象
             */
            JSONArray array = JSONObject.parseArray(rpc.getData());
            for (int i = 0; i < array.size(); i++) {
                JSONObject objVo = array.getJSONObject(i);
                ReportVo vo = new ReportVo();
                vo.setType(objVo.getString("type"));
                vo.setDesc(objVo.getString("desc"));
                out.add(vo);
            }
        }
        return out;
    }

    @Override
    public PageList<ReportDTO> selectBy(ReportDTO dto) {

        PageHelper.startPage(dto.getCurrentPage(),dto.getPageSize());
        List<ReportDTO> list = reportDao.getList(dto);
        return new PageModel<ReportDTO>().getPageList(list);
    }

    @Override
    public ReportDTO getReport(ReportDTO dto) {
        return reportDao.selectByKid(ReportDTO.class,dto.getKid());
    }

}
