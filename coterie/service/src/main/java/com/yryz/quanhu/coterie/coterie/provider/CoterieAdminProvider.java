package com.yryz.quanhu.coterie.coterie.provider;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminAPI;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminService;
import com.yryz.quanhu.coterie.coterie.service.CoterieService;
import com.yryz.quanhu.coterie.coterie.vo.CoterieBasicInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.coterie.coterie.vo.CoterieSearchParam;
import com.yryz.quanhu.coterie.coterie.vo.CoterieUpdateAdmin;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.score.vo.EventReportVo;
import com.yryz.quanhu.user.dto.UserRelationCountDto;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.service.UserRelationApi;
import com.yryz.quanhu.user.vo.UserLoginSimpleVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

@Service(interfaceClass=CoterieAdminAPI.class)
public class CoterieAdminProvider implements CoterieAdminAPI {


    @Resource
    private CoterieAdminService coterieAdminService;

    @Reference
    private UserApi userApi;

    @Autowired
    private CoterieService coterieService;


    @Reference
    private EventAPI eventAPI;

    @Reference
    private UserRelationApi userRelationApi;


    @Override
    public Response<PageList<CoterieInfo>> getCoterieByPage(CoterieSearchParam param) {
        try {
            PageList<CoterieInfo> coterieInfos = coterieAdminService.queryCoterieByCoterieSearch(param);
            return ResponseUtils.returnObjectSuccess(coterieInfos);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        }
    }



    /**
     * 编辑私圈
     */
    public Response<String> audit(CoterieUpdateAdmin info) {

        return coterieAdminService.audit(info);
    }

    @Override
    public Response<CoterieInfo> queryCoterieInfo(Long coterieId) {

        if (coterieId == null) {
            return ResponseUtils.returnCommonException("coterieId is not null");
        }
        try {
            CoterieInfo info = coterieAdminService.getCoterieInfo(coterieId);
            if (info != null) {
                //todo user info
                UserLoginSimpleVO simpleVO = ResponseUtils
                        .getResponseData(userApi.getUserLoginSimpleVO(Long.parseLong(info.getOwnerId())));

                if (null != simpleVO) {
                    info.setPhone(simpleVO.getUserPhone());
                    info.setOwnerName(simpleVO.getUserNickName());
                    info.setOwnerIntro(simpleVO.getUserDesc());
                    info.setStarTradeField(simpleVO.getStarTradeField());
                }
            }
            return ResponseUtils.returnObjectSuccess(info);
        } catch (Exception e) {
            return ResponseUtils.returnException(e);
        }
    }


    /**
     * 设置私圈信息
     * @param info  coterieId必填
     */
    @Override
    public Response<CoterieInfo> modifyCoterieInfo(CoterieInfo info) {

        try {
            if (info == null ||  info.getCoterieId()==null) {
                return ResponseUtils.returnCommonException("私圈ID不存在");
            }
            String name = StringUtils.trim(info.getName());
            if (StringUtils.isNotEmpty(name)) {
                List<CoterieInfo> clist = coterieAdminService.findByName(name);
                if (!clist.isEmpty()&&!clist.get(0).getCoterieId().equals(info.getCoterieId())) {
                    return ResponseUtils.returnCommonException("私圈名称已存在");
                }
            }
            if (info.getJoinFee() != null && (info.getJoinFee() > 50000 || info.getJoinFee() < 0)) {
                return ResponseUtils.returnCommonException("加入私圈金额设置不正确。");
            }
            if (info.getConsultingFee()!=null && (info.getConsultingFee() > 10000 || info.getConsultingFee() < 0)) {
                return ResponseUtils.returnCommonException("私圈咨询费金额设置不正确。");
            }
            coterieAdminService.modify(info);
            return ResponseUtils.returnObjectSuccess(coterieAdminService.find(info.getCoterieId()));
        } catch (Exception e) {
            return ResponseUtils.returnException(e);
        }
    }


    /**
     * 查询有权限创建私圈的用户
     * @return
     */
    @Override
    public Response<Set<Long>> queryAbleCreteCoterieUserIds() {
        try {
        return ResponseUtils.returnObjectSuccess(coterieAdminService.queryAbleCreteCoterieUserIds());
    } catch (Exception e) {
        return ResponseUtils.returnException(e);
    }
    }


    /**
     * 创建私圈
     * @param info
     * @return
     */
    @Override
    public Response<CoterieInfo> applyCreate(CoterieBasicInfo info) {
        try {
            checkApplyCreateParam(info);
            return ResponseUtils.returnObjectSuccess(coterieAdminService.save(info));
        }catch (Exception e) {
            return ResponseUtils.returnException(e);
        }
    }


    public void checkApplyCreateParam(CoterieBasicInfo info) {
        if (info == null) {
            throw new QuanhuException( "2007","对象为空","对象为空",null);
        }
        if (StringUtils.isEmpty(info.getIcon())) {
            throw new QuanhuException( "2007","参数错误","icon不能为空",null);
        }
        if (StringUtils.isEmpty(info.getIntro())) {
            throw new QuanhuException( "2007","参数错误","intro不能为空",null);
        }
        if (StringUtils.isEmpty(info.getName())) {
            throw new QuanhuException( "2007","参数错误","name不能为空",null);
        }
        if (StringUtils.isEmpty(info.getOwnerId())) {
            throw new QuanhuException( "2007","参数错误","ownerId不能为空",null);
        }
        if (info.getJoinFee()!=null && (info.getJoinFee()>50000 || info.getJoinFee()<0)) {//私圈单位为分，0表示免费，小于100的  单位必定错误
            throw new QuanhuException( "2007","参数错误","加入私圈金额设置不正确。",null);
        }
        List<CoterieInfo> coterieList = coterieService.findByName(StringUtils.trim(info.getName()));
        if (!coterieList.isEmpty()) {
            throw new QuanhuException( "2007","私圈名称已存在","私圈名称已存在");
        }
        Integer count=coterieService.findMyCreateCoterieCount(info.getOwnerId());
        if(count>=10)
        {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),"最多只能申请10个私圈","最多只能申请10个私圈");
        }
        UserSimpleVO cust=ResponseUtils.getResponseData(userApi.getUserSimple(Long.valueOf(info.getOwnerId())));
        if(cust==null){
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "用户不存在","用户("+info.getOwnerId()+")不存在");
        }
        EventInfo param=new EventInfo();
        param.setUserId(info.getOwnerId());
        EventReportVo vo= null;
        try {
            vo = ResponseUtils.getResponseData(eventAPI.getScoreFlowList(param));
        } catch (Exception e) {
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(), "未知错误","积分统计查询失败");
        }
        int level=1;
        if(vo==null || vo.getGrowLevel()==null){
            level=1;
        }else{
            level=Integer.valueOf(vo.getGrowLevel());
        }
        if(level<5){
            String msg="等级达到LV5才能创建私圈";
            throw new QuanhuException(ExceptionEnum.BusiException.getCode(),msg,msg);
        }

        UserRelationCountDto countDto=ResponseUtils.getResponseData(userRelationApi.totalBy(info.getOwnerId(), info.getOwnerId()));
        if(countDto!=null){
            if(countDto.getFollowCount()<20){
                String msg="关注人数需达到20人才能创建私圈";
                throw new QuanhuException(ExceptionEnum.BusiException.getCode(),msg,msg);
            }
        }
    }
}
