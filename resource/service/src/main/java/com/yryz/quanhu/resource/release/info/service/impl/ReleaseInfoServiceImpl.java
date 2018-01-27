package com.yryz.quanhu.resource.release.info.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageUtils;
import com.yryz.quanhu.resource.release.config.entity.ReleaseConfig;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;
import com.yryz.quanhu.resource.release.info.dao.ReleaseInfoDao;
import com.yryz.quanhu.resource.release.info.dto.ReleaseInfoDto;
import com.yryz.quanhu.resource.release.info.entity.ReleaseInfo;
import com.yryz.quanhu.resource.release.info.service.ReleaseInfoService;
import com.yryz.quanhu.resource.release.info.vo.ReleaseInfoVo;

/**
* @author wangheng
* @date 2018年1月22日 下午8:16:49
*/
@Service
public class ReleaseInfoServiceImpl implements ReleaseInfoService {

    Logger logger = LoggerFactory.getLogger(ReleaseInfoServiceImpl.class);

    @Autowired
    ReleaseInfoDao releaseInfoDao;

    private ReleaseInfoDao getDao() {
        return this.releaseInfoDao;
    }

    @Override
    public int insertSelective(ReleaseInfo record) {
        return this.getDao().insertSelective(record);
    }

    @Override
    public ReleaseInfoVo selectByKid(Long kid) {
        return this.getDao().selectByKid(kid);
    }

    @Override
    public List<ReleaseInfoVo> selectByCondition(ReleaseInfoDto dto) {
        PageUtils.startPage(dto.getPageNo(), dto.getPageSize());
        return this.getDao().selectByCondition(dto);
    }

    @Override
    public long countByCondition(ReleaseInfoDto dto) {
        return this.getDao().countByCondition(dto);
    }

    @Override
    public PageList<ReleaseInfoVo> pageByCondition(ReleaseInfoDto dto, boolean isCount) {
        PageList<ReleaseInfoVo> pageList = new PageList<>();
        pageList.setCurrentPage(dto.getPageNo());
        pageList.setPageSize(dto.getPageSize());

        List<ReleaseInfoVo> list = this.selectByCondition(dto);
        pageList.setEntities(list);

        if (!isCount || CollectionUtils.isEmpty(list)) {
            pageList.setCount(0L);
        } else {
            pageList.setCount(this.countByCondition(dto));
        }

        // TODO 缓存添加

        return pageList;
    }

    @Override
    public int updateByUkSelective(ReleaseInfo record) {
        return this.getDao().updateByUkSelective(record);
    }

    @Override
    public int updateByCondition(ReleaseInfo record, ReleaseInfoDto dto) {
        return this.getDao().updateByCondition(record, dto);
    }

    /**  
    * @Description: 发布内容校验(根据配置信息)
    * @author wangheng
    * @param @param record
    * @param @param configDetail
    * @param @return
    * @return boolean
    * @throws  
    */
    @Override
    public boolean releaseInfoCheck(ReleaseInfo record, ReleaseConfigVo cfgVo) {
        if (null == cfgVo) {
            logger.error("分类发布模板为空，ID为：" + record.getClassifyId());
            return false;
        }

        propertyCheck(record.getCoverPlanUrl(), ReleaseConstants.PropertyVerifyType.number, cfgVo.getCoverPlan());
        propertyCheck(record.getTitle(), ReleaseConstants.PropertyVerifyType.length, cfgVo.getTitle());
        propertyCheck(record.getDescription(), ReleaseConstants.PropertyVerifyType.length, cfgVo.getDescription());
        propertyCheck(record.getContent(), ReleaseConstants.PropertyVerifyType.length, cfgVo.getText());
        propertyCheck(record.getImgUrl(), ReleaseConstants.PropertyVerifyType.number, cfgVo.getImg());
        propertyCheck(record.getAudioUrl(), ReleaseConstants.PropertyVerifyType.number, cfgVo.getAudio());
        propertyCheck(record.getVideoUrl(), ReleaseConstants.PropertyVerifyType.number, cfgVo.getVideo());
        propertyCheck(record.getContentLabel(), ReleaseConstants.PropertyVerifyType.length, cfgVo.getLabel());

        // 若属性不存在 需要将属性值置为空(不置为空，对进入动态 展示规则会有影响)
        if (null != cfgVo.getCoverPlan()
                && (null == cfgVo.getCoverPlan().getEnabled()
                        || cfgVo.getCoverPlan().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getCoverPlanUrl()) {
            record.setCoverPlanUrl(null);
        }
        if (null != cfgVo.getTitle()
                && (null == cfgVo.getTitle().getEnabled()
                        || cfgVo.getTitle().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getTitle()) {
            record.setTitle(null);
        }
        if (null != cfgVo.getDescription()
                && (null == cfgVo.getDescription().getEnabled()
                        || cfgVo.getDescription().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getDescription()) {
            record.setDescription(null);
        }
        if (null != cfgVo.getText()
                && (null == cfgVo.getText().getEnabled()
                        || cfgVo.getText().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getContent()) {
            record.setContent(null);
        }
        if (null != cfgVo.getImg()
                && (null == cfgVo.getImg().getEnabled()
                        || cfgVo.getImg().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getImgUrl()) {
            record.setImgUrl(null);
        }
        if (null != cfgVo.getAudio()
                && (null == cfgVo.getAudio().getEnabled()
                        || cfgVo.getAudio().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getAudioUrl()) {
            record.setAudioUrl(null);
        }
        if (null != cfgVo.getVideo()
                && (null == cfgVo.getVideo().getEnabled()
                        || cfgVo.getVideo().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getVideoUrl()) {
            record.setVideoUrl(null);
        }
        if (null != cfgVo.getLabel()
                && (null == cfgVo.getLabel().getEnabled()
                        || cfgVo.getLabel().getEnabled() == ReleaseConstants.EnabledType.not_exist)
                && null != record.getContentLabel()) {
            record.setContentLabel(null);
        }

        Assert.isTrue(!(StringUtils.isNotBlank(record.getImgUrl()) && StringUtils.isNotBlank(record.getVideoUrl())),
                "图片和视屏互斥，不能同时存在！");

        return true;
    }

    /**  
    * @Description: 发布内容属性校验(根据属性配置信息)
    * @author wangheng
    * @param value
    * @param type 校验类型：1：
    * @param config
    * @param @return
    * @return boolean
    * @throws  
    */
    private boolean propertyCheck(String value, byte type, ReleaseConfig config) {

        if (null == config || null == config.getEnabled()
                || ReleaseConstants.EnabledType.not_exist == config.getEnabled()) {
            return true;
        }

        String propertyKey = config.getPropertyKey();

        if (null != config.getRequired()) {
            // 校验必填
            if (ReleaseConstants.RequiredType.required == config.getRequired() && StringUtils.isBlank(value)) {
                throw new IllegalArgumentException("参数必填校验不通过，属性 key：" + propertyKey);
            }
            // 校验非必填，属性为空
            else if (ReleaseConstants.RequiredType.not_required == config.getRequired() && StringUtils.isBlank(value)) {
                return true;
            }
        }

        // 上限
        int upperLimit = null == config.getUpperLimit() ? -1 : config.getUpperLimit();
        // 下限
        int lowerLimit = null == config.getLowerLimit() ? -1 : config.getLowerLimit();

        switch (type) {
        case ReleaseConstants.PropertyVerifyType.length:
            // 资源长度
            int resourceLen = StringUtils.length(value);

            // 校验上限
            if (upperLimit > 0 && upperLimit >= lowerLimit && resourceLen > upperLimit) {
                throw new IllegalArgumentException(
                        "参数上限校验不通过(" + upperLimit + ")，属性 key：" + propertyKey + "，实际字数为：" + resourceLen);
            }
            // 校验下限
            if (lowerLimit > 0 && lowerLimit <= upperLimit && resourceLen < lowerLimit) {
                throw new IllegalArgumentException(
                        "参数下限校验不通过(" + lowerLimit + ")，属性 key：" + propertyKey + "，实际字数为：" + resourceLen);
            }
            break;
        case ReleaseConstants.PropertyVerifyType.number:
            // 资源个数
            int resourceNum = StringUtils.defaultString(value).split(ReleaseConstants.RESOURCE_SPLIT_REGEX).length;

            // 校验上限
            if (upperLimit > 0 && upperLimit >= lowerLimit && resourceNum > upperLimit) {
                throw new IllegalArgumentException(
                        "参数上限校验不通过(" + upperLimit + ")，属性 key：" + propertyKey + "，实际数量为：" + resourceNum);
            }
            // 校验下限
            if (lowerLimit > 0 && lowerLimit <= upperLimit && resourceNum < lowerLimit) {
                throw new IllegalArgumentException(
                        "参数下限校验不通过(" + lowerLimit + ")，属性 key：" + propertyKey + "，实际数量为：" + resourceNum);
            }
            break;
        case ReleaseConstants.PropertyVerifyType.other:
            break;
        default:
            throw new IllegalArgumentException("参数校验类型错误");
        }

        return true;

    }

    @Override
    public void resourcePropertiesEmpty(ReleaseInfo record) {
        record.setAudioUrl(null);
        record.setContent(null);
        record.setContentSource(null);
        record.setCoverPlanUrl(null);
        record.setDescription(null);
        record.setExtend(null);
        record.setImgUrl(null);
        record.setVideoThumbnailUrl(null);
        record.setVideoUrl(null);
    }

	@Override
	public List<Long> getKidByCreatedate(String startDate, String endDate) {
		return releaseInfoDao.selectKidByCreatedate(startDate, endDate);
	}

	@Override
	public List<ReleaseInfoVo> getByKids(List<Long> kidList) {
		return releaseInfoDao.selectByKids(kidList);
	}
}
