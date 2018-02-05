package com.yryz.quanhu.resource.release.buyrecord.provider;

import java.util.ArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.BeanUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.release.buyrecord.api.ReleaseBuyRecordAdminApi;
import com.yryz.quanhu.resource.release.buyrecord.dto.ReleaseBuyRecordDto;
import com.yryz.quanhu.resource.release.buyrecord.entity.ReleaseBuyRecord;
import com.yryz.quanhu.resource.release.buyrecord.service.ReleaseBuyRecordService;
import com.yryz.quanhu.resource.release.buyrecord.vo.CoterieBuyRecordVo;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @author wangheng
* @date 2018年2月5日 上午11:39:15
*/
@Service(interfaceClass = ReleaseBuyRecordAdminApi.class)
public class ReleaseBuyRecordAdminProvider implements ReleaseBuyRecordAdminApi {

    Logger logger = LoggerFactory.getLogger(ReleaseBuyRecordAdminProvider.class);

    @Autowired
    private ReleaseBuyRecordService releaseBuyRecordService;

    @Reference
    private ResourceApi resourceApi;

    @Reference
    private CoterieApi coterieApi;
    

    @Reference
    private UserApi userApi;

    @Override
    public Response<PageList<CoterieBuyRecordVo>> pageByCondition(ReleaseBuyRecordDto releaseBuyRecordDto) {
        try {
            PageList<ReleaseBuyRecord> pageList = releaseBuyRecordService.pageByCondition(releaseBuyRecordDto, true);

            PageList<CoterieBuyRecordVo> result = new PageList<>(pageList.getCurrentPage(), pageList.getPageSize(),
                    new ArrayList<>(), pageList.getCount());
            if (CollectionUtils.isEmpty(pageList.getEntities())) {
                return ResponseUtils.returnObjectSuccess(result);
            }

            // 设置 聚合资源、私圈信息
            for (ReleaseBuyRecord record : pageList.getEntities()) {
                if (null == record) {
                    continue;
                }
                CoterieBuyRecordVo vo = new CoterieBuyRecordVo();
                BeanUtils.copyProperties(vo, record);
                // 聚合资源
                ResourceVo resourceVo = ResponseUtils
                        .getResponseData(resourceApi.getResourcesById(String.valueOf(record.getResourceId())));
                if (null == resourceVo) {
                    continue;
                }

                vo.setResourceVo(resourceVo);
                try {
                    vo.setExtJsonObj(JSON.parse(resourceVo.getExtJson()));
                } catch (Exception e) {
                    logger.error("ExtJson is not json Obj !", e);
                }
                // 私圈信息
                CoterieInfo coterieInfo = ResponseUtils
                        .getResponseData(coterieApi.queryCoterieInfo(record.getCoterieId()));
                if (null != coterieInfo) {
                    vo.setCoterieName(coterieInfo.getName());
                }
                
                // 购买人信息
                UserSimpleVO user = ResponseUtils.getResponseData(userApi.getUserSimple(record.getCreateUserId()));
                if (null != user) {
                    vo.setUser(user);
                }
                result.getEntities().add(vo);
            }

            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取私圈购买列表异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
