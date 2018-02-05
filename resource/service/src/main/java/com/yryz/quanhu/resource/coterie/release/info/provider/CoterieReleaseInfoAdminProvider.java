package com.yryz.quanhu.resource.coterie.release.info.provider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.common.utils.BeanUtils;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.CoterieInfo;
import com.yryz.quanhu.resource.coterie.release.info.api.CoterieReleaseInfoAdminApi;
import com.yryz.quanhu.resource.coterie.release.info.vo.CoterieReleaseInfoVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @author wangheng
* @date 2018年2月3日 下午1:41:17
*/
@Service(interfaceClass = CoterieReleaseInfoAdminApi.class)
public class CoterieReleaseInfoAdminProvider implements CoterieReleaseInfoAdminApi {

    private Logger logger = LoggerFactory.getLogger(CoterieReleaseInfoAdminProvider.class);

    @Autowired
    private ReleaseInfoService releaseInfoService;

    @Reference(lazy = true, check = false, timeout = 10000)
    private UserApi userApi;

    @Reference(lazy = true, check = false, timeout = 10000)
    private CoterieApi coterieApi;
    
    @Override
    public Response<PageList<CoterieReleaseInfoVo>> pageByCondition(ReleaseInfoDto dto) {
        try {
            PageList<CoterieReleaseInfoVo> result = new PageList<>(dto.getCurrentPage(), dto.getPageSize(),
                    new ArrayList<>(), 0L);

            if (null == dto.getOrderType()) {
                dto.setOrderType(ReleaseConstants.OrderType.time_new);
            }
            PageList<ReleaseInfoVo> pageList = releaseInfoService.pageByCondition(dto, true);
            if (null == pageList || CollectionUtils.isEmpty(pageList.getEntities())) {
                return ResponseUtils.returnObjectSuccess(result);
            }
            result.setCount(pageList.getCount());

            final List<ReleaseInfoVo> voList = pageList.getEntities();
            List<CoterieReleaseInfoVo> resultList = result.getEntities();
            // 获取所有 用户ID，私圈ID
            Set<String> userIds = new HashSet<>();
            Set<Long> coterieIds = new HashSet<>();
            for (final ReleaseInfoVo info : voList) {
                if (null == info) {
                    continue;
                }
                // 变更对象
                CoterieReleaseInfoVo vo = new CoterieReleaseInfoVo();
                BeanUtils.copyProperties(vo, info);
                resultList.add(vo);

                if (null != info.getCreateUserId()) {
                    userIds.add(String.valueOf(info.getCreateUserId()));
                }
                if (null != info.getCoterieId() && 0L != info.getCoterieId()) {
                    coterieIds.add(info.getCoterieId());
                }
            }

            // 获取用户信息集合
            Map<String, UserSimpleVO> userMap = ResponseUtils.getResponseData(userApi.getUserSimple(userIds));
            // 私圈信息集合
            Map<String, UserSimpleVO> coterieMap = null;
            if (null != userMap || null != coterieMap) {
                for (CoterieReleaseInfoVo info : resultList) {
                    if (null == info) {
                        continue;
                    }
                    UserSimpleVO userVo = userMap.get(String.valueOf(info.getCreateUserId()));
                    info.setUser(userVo);

                    CoterieInfo coterieInfo = ResponseUtils
                            .getResponseData(coterieApi.queryCoterieInfo(info.getCoterieId()));
                    if (null != coterieInfo) {
                        info.setCoterieName(coterieInfo.getName());
                    }
                }
            }

            return ResponseUtils.returnObjectSuccess(result);
        } catch (QuanhuException e) {
            return ResponseUtils.returnException(e);
        } catch (Exception e) {
            logger.error("获取私圈文章列表异常！", e);
            return ResponseUtils.returnException(e);
        }
    }

}
