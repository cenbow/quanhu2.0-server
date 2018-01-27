package com.yryz.quanhu.behavior.collection.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.common.response.ResponseUtils;
import com.yryz.quanhu.behavior.collection.api.CollectionInfoApi;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.service.CollectionInfoService;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@Service(interfaceClass=CollectionInfoApi.class)
public class CollectionInfoProvider implements CollectionInfoApi {

    private static final Logger logger = LoggerFactory.getLogger(CollectionInfoProvider.class);

    @Autowired
    private CollectionInfoService collectionInfoService;

    /**
     * 收藏
     * @param   collectionInfoDto
     * @return
     * */
    public Response single(CollectionInfoDto collectionInfoDto) {
        try {
            Assert.notNull(collectionInfoDto.getResourceId(), "resourceId不能为空");
            Assert.hasText(collectionInfoDto.getModuleEnum(), "moduleEnum不能为空");
            collectionInfoService.single(collectionInfoDto);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("收藏 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 取消收藏
     * @param   collectionInfoDto
     * @return
     * */
    public Response del(CollectionInfoDto collectionInfoDto) {
        try {
            Assert.notNull(collectionInfoDto.getResourceId(), "resourceId不能为空");
            Assert.hasText(collectionInfoDto.getModuleEnum(), "moduleEnum不能为空");
            collectionInfoService.del(collectionInfoDto);
            return ResponseUtils.returnSuccess();
        } catch (Exception e) {
            logger.error("取消收藏 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 收藏列表
     * @param   collectionInfoDto
     * @return
     * */
    public Response<PageList<CollectionInfoVo>> list(CollectionInfoDto collectionInfoDto) {
        try {
            return ResponseUtils.returnObjectSuccess(collectionInfoService.list(collectionInfoDto));
        } catch (Exception e) {
            logger.error("收藏列表 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

    /**
     * 收藏状态
     * @param   collectionInfoDto
     * @return
     * */
    public Response<Integer> collectionStatus(CollectionInfoDto collectionInfoDto) {
        try {
            Assert.notNull(collectionInfoDto.getResourceId(), "resourceId不能为空");
            Assert.hasText(collectionInfoDto.getModuleEnum(), "moduleEnum不能为空");
            return ResponseUtils.returnObjectSuccess(collectionInfoService.collectionStatus(collectionInfoDto));
        } catch (Exception e) {
            logger.error("收藏状态 失败", e);
            return ResponseUtils.returnException(e);
        }
    }

}
