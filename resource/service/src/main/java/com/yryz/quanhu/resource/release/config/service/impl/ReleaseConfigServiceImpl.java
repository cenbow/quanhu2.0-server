package com.yryz.quanhu.resource.release.config.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yryz.framework.core.cache.RedisTemplateBuilder;
import com.yryz.quanhu.resource.release.config.dao.ReleaseConfigDao;
import com.yryz.quanhu.resource.release.config.entity.ReleaseConfig;
import com.yryz.quanhu.resource.release.config.service.ReleaseConfigService;
import com.yryz.quanhu.resource.release.config.vo.ReleaseConfigVo;
import com.yryz.quanhu.resource.release.constants.ReleaseConstants;

/**
* @author wangheng
* @date 2018年1月23日 下午2:22:46
*/
@Service
public class ReleaseConfigServiceImpl implements ReleaseConfigService {

    @Autowired
    private ReleaseConfigDao ReleaseConfigDao;

    protected RedisTemplate<String, ReleaseConfigVo> redisTemplate;
    
    @Autowired
    protected RedisTemplateBuilder redisTemplateBuilder;
    
    @PostConstruct
    public void init(){
        redisTemplate = redisTemplateBuilder.buildRedisTemplate(ReleaseConfigVo.class);
    }
    
    public String getCacheKey(Object key) {
        return "QH:ReleaseConfigVo:template_" + key;
    }
    
    public ReleaseConfigDao getDao() {
        return this.ReleaseConfigDao;
    }

    @Override
    public ReleaseConfigVo getTemplate(Long classifyId) {
        // 查询缓存，若有直接返回
        ReleaseConfigVo rsObj = redisTemplate.opsForValue().get(this.getCacheKey(classifyId));
        if (null != rsObj) {
            return rsObj;
        }

        ReleaseConfigVo vo = null;
        List<ReleaseConfig> cfgList = this.selectByClassifyId(classifyId);
        if (CollectionUtils.isNotEmpty(cfgList)) {
            vo = this.listToCfgVo(cfgList);
        }

        // 查询数据后，放置缓存
        redisTemplate.opsForValue().set(this.getCacheKey(classifyId), vo);
        return vo;
    }

    @Override
    @Transactional
    public int updateTemplate(ReleaseConfigVo releaseConfigVo) {
        List<ReleaseConfig> cfgList = this.cfgVoToList(releaseConfigVo);

        int result = 0;
        Set<Long> classifyIds = new HashSet<>();
        for (ReleaseConfig cfg : cfgList) {
            if (null == cfg || null == cfg.getClassifyId() || null == cfg.getPropertyKey()) {
                continue;
            }
            this.updateByUkSelective(cfg);
            classifyIds.add(cfg.getClassifyId());
            result++;
        }
        
        // 更新数据后，处理缓存
        for(Long classifyId:classifyIds){
            redisTemplate.delete(this.getCacheKey(classifyId));
        }
        return result;
    }

    private List<ReleaseConfig> selectByClassifyId(Long classifyId) {
        return getDao().selectByClassifyId(classifyId);
    }

    private int updateByUkSelective(ReleaseConfig record) {
        return getDao().updateByUkSelective(record);
    }

    /**  
    * @Description: 模板配置 转 属性配置list
    * @author wangheng
    * @param @param cfgVo
    * @param @return
    * @return List<ReleaseConfig>
    * @throws  
    */
    private List<ReleaseConfig> cfgVoToList(ReleaseConfigVo cfgVo) {
        List<ReleaseConfig> cfgList = new ArrayList<>();
        if (null == cfgVo) {
            return cfgList;
        }

        if (null != cfgVo.getCoverPlan()) {
            cfgVo.getCoverPlan().setPropertyKey(ReleaseConstants.PropertyKey.COVERPLAN);
            cfgList.add(cfgVo.getCoverPlan());
        }
        if (null != cfgVo.getTitle()) {
            cfgVo.getTitle().setPropertyKey(ReleaseConstants.PropertyKey.TITLE);
            cfgList.add(cfgVo.getTitle());
        }
        if (null != cfgVo.getDescription()) {
            cfgVo.getDescription().setPropertyKey(ReleaseConstants.PropertyKey.DESCRIPTION);
            cfgList.add(cfgVo.getDescription());
        }
        if (null != cfgVo.getText()) {
            cfgVo.getText().setPropertyKey(ReleaseConstants.PropertyKey.TEXT);
            cfgList.add(cfgVo.getText());
        }
        if (null != cfgVo.getImg()) {
            cfgVo.getImg().setPropertyKey(ReleaseConstants.PropertyKey.IMG);
            cfgList.add(cfgVo.getImg());
        }
        if (null != cfgVo.getAudio()) {
            cfgVo.getAudio().setPropertyKey(ReleaseConstants.PropertyKey.AUDIO);
            cfgList.add(cfgVo.getAudio());
        }
        if (null != cfgVo.getVideo()) {
            cfgVo.getVideo().setPropertyKey(ReleaseConstants.PropertyKey.VIDEO);
            cfgList.add(cfgVo.getVideo());
        }
        if (null != cfgVo.getLabel()) {
            cfgVo.getLabel().setPropertyKey(ReleaseConstants.PropertyKey.LABEL);
            cfgList.add(cfgVo.getLabel());
        }

        return cfgList;
    }

    /**  
    * @Override
    * @Title: configListToDetail  
    * @Description: 多属性配置list 转 分类模板配置
    * @author wangheng
    * @param @param configList
    * @param @return
    * @throws  
    */
    private ReleaseConfigVo listToCfgVo(List<ReleaseConfig> cfgList) {
        if (CollectionUtils.isEmpty(cfgList)) {
            return null;
        }
        ReleaseConfigVo cfgVo = new ReleaseConfigVo();

        // 将纵向数据记录，转换为 约束的数据格式
        for (ReleaseConfig cfg : cfgList) {
            if (null == cfg || null == cfg.getPropertyKey()) {
                continue;
            }
            switch (cfg.getPropertyKey()) {
            case ReleaseConstants.PropertyKey.COVERPLAN:
                cfgVo.setCoverPlan(cfg);

                break;
            case ReleaseConstants.PropertyKey.TITLE:
                cfgVo.setTitle(cfg);

                break;
            case ReleaseConstants.PropertyKey.DESCRIPTION:
                cfgVo.setDescription(cfg);

                break;
            case ReleaseConstants.PropertyKey.TEXT:
                cfgVo.setText(cfg);

                break;
            case ReleaseConstants.PropertyKey.IMG:
                cfgVo.setImg(cfg);

                break;
            case ReleaseConstants.PropertyKey.AUDIO:
                cfgVo.setAudio(cfg);

                break;
            case ReleaseConstants.PropertyKey.VIDEO:
                cfgVo.setVideo(cfg);

                break;
            case ReleaseConstants.PropertyKey.LABEL:
                cfgVo.setLabel(cfg);

                break;
            default:
                break;
            }
        }

        return cfgVo;
    }

}
