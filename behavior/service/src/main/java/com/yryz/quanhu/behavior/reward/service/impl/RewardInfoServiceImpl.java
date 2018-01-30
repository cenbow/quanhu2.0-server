package com.yryz.quanhu.behavior.reward.service.impl;

import java.util.List;

import com.yryz.common.constant.ExceptionEnum;
import com.yryz.common.exception.QuanhuException;
import com.yryz.quanhu.behavior.reward.vo.RewardFlowVo;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.behavior.reward.dao.RewardInfoDao;
import com.yryz.quanhu.behavior.reward.dto.RewardInfoDto;
import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.behavior.reward.service.RewardInfoService;
import com.yryz.quanhu.behavior.reward.vo.RewardInfoVo;

/**
* @author wangheng
* @date 2018年1月27日 上午11:47:30
*/
@Service
public class RewardInfoServiceImpl implements RewardInfoService {

    private Logger logger = LoggerFactory.getLogger(RewardInfoServiceImpl.class);

    @Autowired
    private RewardInfoDao rewardInfoDao;

    private RewardInfoDao getDao() {
        return this.rewardInfoDao;
    }

    @Override
    public int insertSelective(RewardInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public List<RewardInfoVo> selectByCondition(RewardInfoDto dto) {
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public PageList<RewardInfoVo> pageByCondition(RewardInfoDto dto, boolean isCount) {
        PageList<RewardInfoVo> pageList = new PageList<>();
        pageList.setCurrentPage(dto.getCurrentPage());
        pageList.setPageSize(dto.getPageSize());

        PageUtils.startPage(dto.getCurrentPage(), dto.getPageSize(), false);
        List<RewardInfoVo> list = this.getDao().selectByCondition(dto);
        pageList.setEntities(list);

        if (isCount && CollectionUtils.isNotEmpty(list)) {
            pageList.setCount(this.getDao().countByCondition(dto));
        }

        return pageList;
    }

    @Override
    public RewardInfo selectByKid(Long id) {
        return this.getDao().selectByKid(id);
    }

    @Override
    public int updateByKid(RewardInfo record) {
        return this.getDao().updateByKid(record);
    }

    @Override
    public PageList<RewardFlowVo> selectRewardFlow(Long userId, Integer currentPage, Integer pageSize) {
        if (null == userId) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "用户ID不能为空");
        }
        if (null == currentPage) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "页码不能为空");
        }
        if (null == pageSize) {
            throw new QuanhuException(ExceptionEnum.ValidateException.getCode(),
                    ExceptionEnum.ValidateException.getShowMsg(), "每页条数不能为空");
        }
        PageList<RewardFlowVo> pageList = new PageList<>();
        pageList.setCurrentPage(currentPage);
        pageList.setPageSize(pageSize);
        //开启分页
        PageUtils.startPage(currentPage, pageSize);
        pageList.setEntities(this.getDao().selectRewardFlow(userId));
        return pageList;
    }

}
