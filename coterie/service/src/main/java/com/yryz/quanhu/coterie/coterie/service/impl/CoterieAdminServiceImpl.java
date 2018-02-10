package com.yryz.quanhu.coterie.coterie.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.constant.ModuleContants;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseConstant;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.GsonUtils;
import com.yryz.quanhu.coterie.coterie.dao.CoterieMapper;
import com.yryz.quanhu.coterie.coterie.dao.CoterieRedis;
import com.yryz.quanhu.coterie.coterie.entity.Coterie;
import com.yryz.quanhu.coterie.coterie.service.CoterieAdminService;
import com.yryz.quanhu.coterie.coterie.vo.*;
import com.yryz.quanhu.coterie.member.event.CoterieEventManager;
import com.yryz.quanhu.coterie.member.event.CoterieMessageManager;
import com.yryz.quanhu.resource.api.ResourceDymaicApi;
import com.yryz.quanhu.resource.vo.ResourceTotal;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserBaseInfoVO;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ScheduledExecutorTask;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 私圈服务实现 admin
 *
 * @author chengyunfei
 */
@Service
public class CoterieAdminServiceImpl implements CoterieAdminService {
    @Resource
    private CoterieMapper coterieMapper;

    @Reference(check = false)
    private IdAPI idapi;
    @Reference(check = false)
    private ResourceDymaicApi resourceDymaicApi;
    @Reference(check = false)
    private EventAPI eventAPI;
    @Reference(check = false)
    private UserApi userApi;

    @Resource
    private CoterieMessageManager coterieMessageManager;

    @Resource
    private CoterieEventManager coterieEventManager;

    @Autowired
    private CoterieRedis coterieRedis;


    @Override
    public PageList<CoterieInfo> queryCoterieByCoterieSearch(CoterieSearchParam param) {
        try {
            List<Coterie> list = Lists.newArrayList();
            Integer currentPage = param.getPageNum();
            int start = (param.getPageNum() - 1) * param.getPageSize();
            param.setPageNum(start);
            Integer count = coterieMapper.selectCountBySearchParam(param);
            list = coterieMapper.selectBySearchParam(param);

            Set<String> ids = new HashSet<>();

            for (Coterie coterie : list) {
                ids.add(coterie.getOwnerId());
            }

            Response<Map<String,UserBaseInfoVO>> response  = userApi.getUser(ids);
            Map<String, UserBaseInfoVO> userMap = ResponseUtils.getResponseNotNull(response);


            List<CoterieInfo> infos = list.stream().map(coterie-> {

                CoterieInfo info = new CoterieInfo();
                BeanUtils.copyProperties(coterie, info);

                info.setOwnerName(userMap.get(coterie.getOwnerId()).getUserNickName());
                info.setPhone(userMap.get(coterie.getOwnerId()).getUserPhone());

                return info;

            }).collect(Collectors.toList());

            PageList<CoterieInfo> pageList = new PageList<>(currentPage, param.getPageSize(), infos, Long.parseLong(String.valueOf(count)));

            return pageList;

        } catch (Exception e) {
            e.printStackTrace();
            throw QuanhuException.busiError("分页查询发生异常");
        }

    }

    @Override
    public Response<String> audit(CoterieUpdateAdmin coterie) {
        try {
            if (null == coterie.getStatus()) {

                throw QuanhuException.busiError("审核时审核状态不能为空");
            }

            Integer result = coterieMapper.updateCoterieAdmin(coterie);
            if (result > 0) {

                Coterie coterieDb = coterieMapper.selectByCoterieId(coterie.getCoterieId());
                coterieRedis.save(coterieDb);

                if (coterie.getStatus() == 11) {

                    //dynamic
                    ResourceTotal resourceTotal = new ResourceTotal();
                    resourceTotal.setCreateDate(DateUtils.getDate());
                    resourceTotal.setExtJson(JSON.toJSONString(coterieDb));
                    resourceTotal.setResourceId(coterie.getCoterieId());
                    resourceTotal.setModuleEnum(Integer.valueOf(ModuleContants.COTERIE));
                    resourceTotal.setUserId(NumberUtils.toLong(coterieDb.getOwnerId()));
                    resourceTotal.setCoterieId(String.valueOf(coterie.getCoterieId()));
                    resourceDymaicApi.commitResourceDymaic(resourceTotal);

                    //event
                    coterieEventManager.createCoterieEvent(coterie.getCoterieId());

                    //msg
                    coterieMessageManager.agreeMessage(coterie.getCoterieId());


                } else {
                    //msg
                    coterieMessageManager.disagreeMessage(coterie.getCoterieId(), coterie.getAuditRemark());
                }

            }
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            throw QuanhuException.busiError("审核私圈发生异常");
        }
    }
}
