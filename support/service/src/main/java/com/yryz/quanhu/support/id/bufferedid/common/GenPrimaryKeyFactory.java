package com.yryz.quanhu.support.id.bufferedid.common;

import com.yryz.quanhu.support.id.bufferedid.entity.KeyInfo;
import com.yryz.quanhu.support.id.bufferedid.exception.GenPrimaryKeyException;
import com.yryz.quanhu.support.id.bufferedid.exception.LoadPrimaryKeyException;
import com.yryz.quanhu.support.id.bufferedid.exception.UndeclaredPrimaryKeyException;
import com.yryz.quanhu.support.id.bufferedid.service.GenerateKeyService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class GenPrimaryKeyFactory {

    private static final Logger log = LoggerFactory.getLogger(GenPrimaryKeyFactory.class);

    private static final Map<String, KeyInfo> keyMap = new HashMap<String, KeyInfo>();

    private static GenerateKeyService generateKeyService;

    // 编辑keyMapLock的同步锁
    private static final byte[] keyMapLock = new byte[0];

//    @PostConstruct
//    public void init() {
//        GenPrimaryKeyFactory.init(Arrays.asList(PrimaryUtils.PRIMARY_ORDER));
//    }

    /**
     * 获取next主键ID
     *
     * @param primaryKeyName 主键名称
     * @return 主键值
     * @throws UndeclaredPrimaryKeyException 入参key值在数据库中未配置
     * @throws LoadPrimaryKeyException       从数据库中加载key值失败
     * @throws GenPrimaryKeyException        获取key值失败
     */
    public static long nextPrimaryKey(final String primaryKeyName)
            throws UndeclaredPrimaryKeyException, LoadPrimaryKeyException, GenPrimaryKeyException {
        return nextPrimaryKey(primaryKeyName, GenIdConstant.INC_DEFAULT);
    }

    /**
     * 获取next主键ID
     *
     * @param primayKeyName 主键名称
     * @param inc           增加的步长
     * @return 主键值
     * @throws UndeclaredPrimaryKeyException 入参key值在数据库中未配置
     * @throws LoadPrimaryKeyException       从数据库中加载key值失败
     * @throws GenPrimaryKeyException        获取key值失败
     */
    public static long nextPrimaryKey(final String primaryKeyName, Integer inc)
            throws UndeclaredPrimaryKeyException, LoadPrimaryKeyException, GenPrimaryKeyException {
        // 主键名称不能为空
        if (StringUtils.isBlank(primaryKeyName)) {
            throw new UndeclaredPrimaryKeyException("主键名称为空。primayKeyName is null");
        }

        // 通过主键名称获取对应的主键信息
        final KeyInfo keyInfo = loadKeyInfo(primaryKeyName);
        // 从主键信息中获取next主键
        return generateKeyService.nextPrimaryKey(keyInfo, inc);
    }

    /**
     * 获取primayKeyName 对应的key信息
     */
    private static KeyInfo loadKeyInfo(final String primaryKeyName) {
        // 首先从keyMap中取，如果取不到则从数据库中取
        KeyInfo keyInfo = keyMap.get(primaryKeyName);
        // 如果取不到，则从数据库中取
        if (keyInfo == null) {
            synchronized (keyMapLock) {
                keyInfo = keyMap.get(primaryKeyName);
                if (keyInfo == null) {
                    keyInfo = generateKeyService.loadKeyInfo(primaryKeyName);
                    keyMap.put(primaryKeyName, keyInfo);
                }
            }
        }
        return keyInfo;
    }

//    public static void init(List<String> initKeys) {
//        if (CollectionUtils.isNotEmpty(initKeys)) {
//            for (String key : initKeys) {
//                synchronized (keyMapLock) {
//                    try {
//                        KeyInfo keyInfo = generateKeyService.loadKeyInfo(key);
//                        keyMap.put(key, keyInfo);
//                    } catch (Exception e) {
//                        log.error("init key:" + key + " exception", e);
//                    }
//                }
//            }
//        }
//    }

    @Autowired
    public void setGenerateKeyService(GenerateKeyService generateKeyService) {
        GenPrimaryKeyFactory.generateKeyService = generateKeyService;
    }
}
