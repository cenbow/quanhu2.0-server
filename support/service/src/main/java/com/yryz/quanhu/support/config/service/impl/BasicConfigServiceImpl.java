package com.yryz.quanhu.support.config.service.impl;

import com.github.pagehelper.PageHelper;
import com.yryz.common.response.PageList;
import com.yryz.common.utils.PageModel;
import com.yryz.quanhu.support.config.dao.BasicConfigDao;
import com.yryz.quanhu.support.config.dto.BasicConfigDto;
import com.yryz.quanhu.support.config.service.BasicConfigService;
import com.yryz.quanhu.support.id.service.IIdService;
import com.yryz.quanhu.support.id.service.impl.IdServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * <p>
 * Created on 2018/1/27 15:56
 * Created by huangxy
 */
@Service
public class BasicConfigServiceImpl implements BasicConfigService {

    private static final String TABLE_NAME = "qh_basic_config";
    /**
     * 配置redis前缀
     */
    @Value("basic.config.redis.prefix")
    private String redisPrefix;
    /**
     * 配置失效天数
     */
    @Value("basic.config.redis.expireDays")
    private String redisExpireDays;

    @Value("appId")
    private String appId;

    @Autowired
    private BasicConfigDao basicConfigDao;

    @Autowired
    private IIdService iIdService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public String getValue(String key) {
        //查询缓存
        String value = redisTemplate.opsForValue().get(key);
        if (value==null){
            //查询数据库
            BasicConfigDto dto = basicConfigDao.selectByKey(BasicConfigDto.class,key);
            value = dto.getConfigValue();
            //更新缓存
            redisTemplate.opsForValue().set(key,value,Integer.parseInt(redisExpireDays), TimeUnit.DAYS);
        }
        return value;
    }

    @Override
    public Map<String, String> getValues(String key) {

        Map<String,String> allMap = new HashMap<>();
        BasicConfigDto dto = basicConfigDao.selectByKey(BasicConfigDto.class,key);

        if(dto!=null){
            List<BasicConfigDto> list = basicConfigDao.selectChildByKid(BasicConfigDto.class,dto.getKid());

            //有子节点，返回子节点信息，没有返回当前节点信息
            if(!CollectionUtils.isEmpty(list)){
                for (int i = 0 ; i < list.size() ;i++){
                    BasicConfigDto _dto = list.get(i);
                    allMap.put(_dto.getConfigKey(),_dto.getConfigValue());
                }
            }else{
                allMap.put(dto.getConfigKey(),dto.getConfigValue());
            }
        }
        return allMap;
    }

    @Override
    public Boolean save(BasicConfigDto dto) {

        //设置默认值
        if(StringUtils.isBlank(dto.getConfigValue())){
            dto.setConfigValue("");
        }
        if(StringUtils.isBlank(dto.getConfigValue())){
            dto.setConfigDesc("");
        }
        if(StringUtils.isBlank(dto.getConfigType())){
            dto.setConfigType("");
        }

        int updateCount = 0;
        try {
            /**
             * 新增，或更新
             */
            if(dto.getKid() == null){

                dto.setAppId(appId);
//                dto.setKid(iIdService.getKid(TABLE_NAME));
                dto.setKid(System.currentTimeMillis());
                updateCount = basicConfigDao.insert(dto);
            }else{
                updateCount = basicConfigDao.update(dto);
            }
        }finally {

            //数据库更新成功,并生效
            if(updateCount==1&&dto.getStatus()==1){
                redisTemplate.opsForValue().set(dto.getConfigKey(),dto.getConfigValue(),Integer.parseInt(redisExpireDays), TimeUnit.DAYS);
            }
        }
        return updateCount==1?true:false;
    }

    public Boolean delete(BasicConfigDto dto) {

        /**
         * 查询参数节点，及所有子节点
         * 删除所有节点
         * 删除redis数据
         */
        dto = this.get(dto);
        if(dto==null){
            return false;
        }
        //转换获取kid,key
        Set<Long> kids = new HashSet<>();
        Set<String> keys = new HashSet<>();

        this.builTreeData(kids,keys,dto);

        //删除数据库
        int updateCount = basicConfigDao.deleteChildByKid(BasicConfigDto.class,kids);

        //删除缓存
        redisTemplate.delete(keys);

        return updateCount>0?true:false;
    }


    private void builTreeData(Set<Long> existKids,Set<String> existKeys,BasicConfigDto dto){

        existKids.add(dto.getKid());
        existKeys.add(dto.getConfigKey());

        List<BasicConfigDto> list = basicConfigDao.selectChildByKid(BasicConfigDto.class,dto.getKid());
        if(!CollectionUtils.isEmpty(list)){
            for (int i = 0 ; i < list.size() ; i++){
                builTreeData(existKids,existKeys,list.get(i));
            }
        }
    }



    @Override
    public Boolean updateKeyStatus(BasicConfigDto dto) {

        //修改
        int updateCount = basicConfigDao.updateKeyStatus(BasicConfigDto.class,dto.getKid());
        //查询
        BasicConfigDto dbDto = basicConfigDao.selectByKid(BasicConfigDto.class,dto.getKid());

        //成功，更新缓存
        if(updateCount == 1&&dbDto!=null){
            /**
             * 删除，或开启
             */
            if(dbDto.getStatus()==1){   //生效
                redisTemplate.opsForValue().set(dbDto.getConfigKey(),dbDto.getConfigValue());
            }else{                      //失效
                redisTemplate.delete(dbDto.getConfigKey());
            }
        }
        return true;
    }

    @Override
    public BasicConfigDto get(BasicConfigDto dto) {
        return basicConfigDao.selectByKid(BasicConfigDto.class,dto.getKid());
    }

    @Override
    public List<BasicConfigDto> list(BasicConfigDto dto) {
        return basicConfigDao.getList(dto);
    }

    @Override
    public PageList<BasicConfigDto> pages(BasicConfigDto dto) {

        PageHelper.startPage(dto.getPageNo(),dto.getPageSize());
        List<BasicConfigDto> list  = basicConfigDao.getList(dto);
        PageHelper.clearPage();

        return new PageModel<BasicConfigDto>().getPageList(list);
    }
}
