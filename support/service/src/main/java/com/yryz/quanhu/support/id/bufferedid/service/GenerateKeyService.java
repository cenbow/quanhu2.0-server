package com.yryz.quanhu.support.id.bufferedid.service;


import com.yryz.quanhu.support.id.bufferedid.entity.KeyInfo;

public interface GenerateKeyService {

    /**
     * 从KeyInfo获取下一个主键 只能从keyInfo中取，但是不能改变本身
     * 
     * @param keyInfo
     * @return
     */
    long nextPrimaryKey(final KeyInfo keyInfo);
    
    /**
     * 从KeyInfo获取下一个主键 只能从keyInfo中取，但是不能改变本身
     * @param keyInfo
     * @param inc 增加的步长
     * @return 主键
     */
    long nextPrimaryKey(final KeyInfo keyInfo, Integer inc);    

    /**
     * 从数据库中加载对应的 Key 信息
     * 
     * @param primaryKeyName 主键名称
     * @return 主键信息
     */
    KeyInfo loadKeyInfo(final String primaryKeyName);

}
