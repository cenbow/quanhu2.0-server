package com.yryz.quanhu.behavior.collection.api;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.behavior.collection.dto.CollectionInfoDto;
import com.yryz.quanhu.behavior.collection.vo.CollectionInfoVo;

public interface CollectionInfoApi {

    /**
     * 收藏
     * @param   collectionInfoDto
     * @return
     * */
    Response single(CollectionInfoDto collectionInfoDto);

    /**
     * 取消收藏
     * @param   collectionInfoDto
     * @return
     * */
    Response del(CollectionInfoDto collectionInfoDto);

    /**
     * 收藏列表
     * @param   collectionInfoDto
     * @return
     * */
    Response<PageList<CollectionInfoVo>> list(CollectionInfoDto collectionInfoDto);

    /**
     * 收藏状态
     * @param   collectionInfoDto
     * @return
     * */
    Response<Integer> collectionStatus(CollectionInfoDto collectionInfoDto);

}
