package com.yryz.quanhu.behavior.collection.service;

import com.yryz.common.response.PageList;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;

public interface CollectionInfoService {

    /**
     * 收藏
     * @param   collectionInfoDto
     * @return
     * */
    void single(CollectionInfoDto collectionInfoDto);

    /**
     * 取消收藏
     * @param   collectionInfoDto
     * @return
     * */
    void del(CollectionInfoDto collectionInfoDto);

    /**
     * 收藏列表
     * @param   collectionInfoDto
     * @return
     * */
    PageList<CollectionInfoVo> list(CollectionInfoDto collectionInfoDto);

    /**
     * 收藏状态
     * @param   collectionInfoDto
     * @return
     * */
    int collectionStatus(CollectionInfoDto collectionInfoDto);

}
