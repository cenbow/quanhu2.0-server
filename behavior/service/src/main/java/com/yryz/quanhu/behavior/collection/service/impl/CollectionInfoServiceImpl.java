package com.yryz.quanhu.behavior.collection.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.utils.DateUtils;
import com.yryz.common.utils.StringUtils;
import com.yryz.quanhu.behavior.collection.dao.CollectionInfoDao;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.entity.CollectionInfo;
import com.yryz.quanhu.behavior.collection.service.CollectionInfoService;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;
import com.yryz.quanhu.behavior.count.api.CountApi;
import com.yryz.quanhu.behavior.count.contants.BehaviorEnum;
import com.yryz.quanhu.coterie.coterie.service.CoterieApi;
import com.yryz.quanhu.coterie.coterie.vo.Coterie;
import com.yryz.quanhu.resource.api.ResourceApi;
import com.yryz.quanhu.resource.enums.ResourceEnum;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.score.service.EventAPI;
import com.yryz.quanhu.score.vo.EventInfo;
import com.yryz.quanhu.support.id.api.IdAPI;
import com.yryz.quanhu.user.service.UserApi;
import com.yryz.quanhu.user.vo.UserSimpleVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
public class CollectionInfoServiceImpl implements CollectionInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CollectionInfoServiceImpl.class);

    @Reference
    IdAPI idAPI;

    @Reference
    UserApi userApi;

    @Autowired
    CollectionInfoDao collectionInfoDao;

    @Reference
    ResourceApi resourceApi;

    @Reference
    CountApi countApi;

    @Reference
    CoterieApi coterieApi;

    @Reference(check = false, timeout = 30000)
    EventAPI eventAPI;

    /**
     * 收藏
     * @param   collectionInfoDto
     * @return
     * */
    public void single(CollectionInfoDto collectionInfoDto) {
        Response<ResourceVo> result = null;
        try {
            //获取资源实体
            result = resourceApi.getResourcesById(collectionInfoDto.getResourceId().toString());
            if(!result.success()) {
                throw new QuanhuException(ExceptionEnum.SysException);
            }
        } catch (Exception e) {
            logger.error("获取资源失败", e);
            throw QuanhuException.busiError("资源不存在或者已删除");
        }
        ResourceVo resourceVo = result.getData();
        if(resourceVo == null || ResourceEnum.DEL_FLAG_TRUE.equals(resourceVo.getDelFlag())) {
            throw QuanhuException.busiError("资源不存在或者已删除");
        }
        Response<Long> idResult = idAPI.getSnowflakeId();
        if(!idResult.success()) {
            throw new QuanhuException(ExceptionEnum.SysException);
        }
        CollectionInfo collectionInfo = new CollectionInfo();
        collectionInfo.setKid(idResult.getData());
        collectionInfo.setResourceId(collectionInfoDto.getResourceId());
        collectionInfo.setModuleEnum(resourceVo.getModuleEnum() == null ? "" : String.valueOf(resourceVo.getModuleEnum()));
        collectionInfo.setCoterieId(StringUtils.isBlank(resourceVo.getCoterieId()) ? 0L : Long.valueOf(resourceVo.getCoterieId()));
        collectionInfo.setUserId(resourceVo.getUserId());
        collectionInfo.setCreateUserId(collectionInfoDto.getCreateUserId());
        collectionInfo.setDelFlag(ResourceEnum.DEL_FLAG_FALSE);
        if(collectionInfoDao.insertByPrimaryKeySelective(collectionInfo) == 0) {
            throw QuanhuException.busiError("已收藏不能重复收藏");
        }
        //提交事件
        this.sendEvent(collectionInfo, resourceVo);
        try {
            //递增收藏数
            countApi.commitCount(BehaviorEnum.Collection, collectionInfoDto.getResourceId(), null, 1L);
            //递增用户收藏数
            countApi.commitCount(BehaviorEnum.Collection, collectionInfoDto.getCreateUserId(), null, 1L);
        } catch (Exception e) {
            logger.error("递增收藏数 失败", e);
        }
    }

    /**
     * 取消收藏
     * @param   collectionInfoDto
     * @return
     * */
    public void del(CollectionInfoDto collectionInfoDto) {
        int i = collectionInfoDao.deleteByResourceId(collectionInfoDto.getResourceId(),
                collectionInfoDto.getModuleEnum(), collectionInfoDto.getCreateUserId());
        if(i > 0) {
            try {
                //递减收藏数
                countApi.commitCount(BehaviorEnum.Collection, collectionInfoDto.getResourceId(), null, -1L);
                //递减用户收藏数
                countApi.commitCount(BehaviorEnum.Collection, collectionInfoDto.getCreateUserId(), null, -1L);
            } catch (Exception e) {
                logger.error("递减收藏数 失败", e);
            }
        }
    }

    /**
     * 收藏列表
     * @param   collectionInfoDto
     * @return
     * */
    public PageList<CollectionInfoVo> list(CollectionInfoDto collectionInfoDto) {
        Page<Object> page = PageHelper.startPage(collectionInfoDto.getCurrentPage(), collectionInfoDto.getPageSize());
        List<CollectionInfoVo> list = collectionInfoDao.selectList(collectionInfoDto);
        if(!CollectionUtils.isEmpty(list)) {
            Set<String> userSet = new HashSet<>();
            Set<String> resourceSet = new HashSet<>();
            List<Long> coterieIdList = new ArrayList<>();
            for(CollectionInfoVo collectionInfoVo : list) {
                if(collectionInfoVo.getUserId() != null) {
                    userSet.add(collectionInfoVo.getUserId().toString());
                }
                if(collectionInfoVo.getResourceId() != null) {
                    resourceSet.add(collectionInfoVo.getResourceId().toString());
                }
                if(collectionInfoVo.getCoterieId() != null) {
                    coterieIdList.add(collectionInfoVo.getCoterieId());
                }
                collectionInfoVo.setDelFlag(ResourceEnum.DEL_FLAG_TRUE);
            }
            //设置用户
            this.setUserInfo(list, userSet);
            //设置资源
            this.setResourceInfo(list, resourceSet);
            //设置私圈名字
            if(!CollectionUtils.isEmpty(coterieIdList)) {
                this.setCoterieInfo(list, coterieIdList);
            }
        }
        PageList<CollectionInfoVo> pageList = new PageList<>();
        pageList.setCurrentPage(collectionInfoDto.getCurrentPage());
        pageList.setPageSize(collectionInfoDto.getPageSize());
        pageList.setEntities(list);
        pageList.setCount(page.getTotal());
        return pageList;
    }

    /**
     * 收藏状态
     * @param   collectionInfoDto
     * @return
     * */
    public int collectionStatus(CollectionInfoDto collectionInfoDto) {
        return collectionInfoDao.selectCount(collectionInfoDto.getResourceId(),
                collectionInfoDto.getModuleEnum(),
                collectionInfoDto.getCreateUserId()) > 0 ? 11 : 10;
    }

    private void setUserInfo(List<CollectionInfoVo> list, Set<String> set) {
        try {
            Response<Map<String, UserSimpleVO>> result = userApi.getUserSimple(set);
            if(result.success()) {
                Map<String, UserSimpleVO> simple = result.getData();
                list.stream()
                        .filter(detailVo -> detailVo.getUserId() != null)
                        .forEach(detailVo -> detailVo.setUser(simple.get(detailVo.getUserId().toString())));
            }
        } catch (Exception e) {
            logger.error("获取用户信息 失败", e);
        }
    }

    private void setResourceInfo(List<CollectionInfoVo> list, Set<String> set) {
        try {
            Response<Map<String, ResourceVo>> result = resourceApi.getResourcesByIds(set);
            if(result.success()) {
                Map<String, ResourceVo> resourceVoMap = result.getData();
                list.stream()
                        .filter(detailVo -> detailVo.getResourceId() != null)
                        .forEach(detailVo -> {
                            ResourceVo resourceVo = resourceVoMap.get(detailVo.getResourceId().toString());
                            if(resourceVo != null) {
                                detailVo.setDelFlag(resourceVo.getDelFlag());
                                detailVo.setTitle(resourceVo.getTitle());
                                detailVo.setContent(resourceVo.getContent());
                                detailVo.setExtJson(resourceVo.getExtJson());
                            }
                        });

            }
        } catch (Exception e) {
            logger.error("获取资源信息 失败", e);
        }
    }

    private void setCoterieInfo(List<CollectionInfoVo> list, List<Long> coterieList) {
        try {
            Response<List<Coterie>> result = coterieApi.getByKids(coterieList);
            if(result.success() && result.getData() != null) {
                Map<Long, Coterie> resourceVoMap = new HashMap<>();
                List<Coterie> data = result.getData();
                data.stream().forEach(coterie -> resourceVoMap.put(coterie.getCoterieId(), coterie));
                list.stream()
                        .filter(collectionInfoVo -> collectionInfoVo.getCoterieId() != null)
                        .forEach(collectionInfoVo -> {
                            Coterie coterie = resourceVoMap.get(collectionInfoVo.getCoterieId());
                            if(coterie != null) {
                                collectionInfoVo.setCoterieName(coterie.getName());
                            }
                        });
            }
        } catch (Exception e) {
            logger.error("获取私圈信息 失败", e);
        }
    }

    /**
     * 提交event
     * @param   collectionInfo
     * @param   resourceVo
     * */
    private void sendEvent(CollectionInfo collectionInfo, ResourceVo resourceVo) {
        try {
            //提交事件
            EventInfo event = new EventInfo();
            event.setEventCode("23");
            event.setUserId(collectionInfo.getCreateUserId().toString());
            event.setResourceId(collectionInfo.getResourceId().toString());
            event.setOwnerId(resourceVo.getUserId() != null ? resourceVo.getUserId().toString() : null);
            event.setCreateTime(DateUtils.formatDateTime(Calendar.getInstance().getTime()));
            if (StringUtils.isNotEmpty(resourceVo.getCoterieId()) ) {
                event.setCoterieId(resourceVo.getCoterieId());
            }
            eventAPI.commit(event);
        } catch (Exception e) {
            logger.error("提交event 失败", e);
        }
    }

}
