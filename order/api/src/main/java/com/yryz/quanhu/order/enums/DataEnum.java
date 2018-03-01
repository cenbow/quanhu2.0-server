package com.yryz.quanhu.order.enums;


import com.yryz.quanhu.order.vo.IOSPayConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 手续费、苹果内购数据枚举
 *
 * @author yehao
 */
public class DataEnum {
    /**
     * 苹果数据枚举类
     */
    private static Map<String, IOSPayConfig> iosProductEnum;

    /**
     * 手续费计算枚举
     */
    private static List<Map<String, Object>> payCharge;

    static {

		/* 生成苹果内购产品对应金额的枚举 */
        iosProductEnum = new HashMap<>();
        IOSPayConfig config = new IOSPayConfig("com.rzw.circlehu.020", 128, 90, 38);
        iosProductEnum.put("com.rzw.circlehu.020", config);
        iosProductEnum.put("ios.amount." + 128, config);
        IOSPayConfig config1 = new IOSPayConfig("com.rzw.circlehu.002", 12, 9, 3);
        iosProductEnum.put("com.rzw.circlehu.002", config1);
        iosProductEnum.put("ios.amount." + 12, config1);
        IOSPayConfig config2 = new IOSPayConfig("com.rzw.circlehu.030", 198, 138, 60);
        iosProductEnum.put("com.rzw.circlehu.030", config2);
        iosProductEnum.put("ios.amount." + 198, config2);
        IOSPayConfig config3 = new IOSPayConfig("com.rzw.circlehu.004", 25, 18, 7);
        iosProductEnum.put("com.rzw.circlehu.004", config3);
        iosProductEnum.put("ios.amount." + 25, config3);
        IOSPayConfig config4 = new IOSPayConfig("com.rzw.circlehu.008", 50, 35, 15);
        iosProductEnum.put("com.rzw.circlehu.008", config4);
        iosProductEnum.put("ios.amount." + 50, config4);
        IOSPayConfig config5 = new IOSPayConfig("com.rzw.circlehu.013", 68, 48, 20);
        iosProductEnum.put("com.rzw.circlehu.013", config5);
        iosProductEnum.put("ios.amount." + 68, config5);
        IOSPayConfig config6 = new IOSPayConfig("com.rzw.circlehu.001", 8, 6, 2);
        iosProductEnum.put("com.rzw.circlehu.001", config6);
        iosProductEnum.put("ios.amount." + 8, config6);
        IOSPayConfig config7 = new IOSPayConfig("com.rzw.circlehu.016", 98, 68, 30);
        iosProductEnum.put("com.rzw.circlehu.016", config7);
        iosProductEnum.put("ios.amount." + 98, config7);

		/* 生成手续费计算公式枚举 */
        payCharge = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("bankId", OrderConstant.PAY_WAY_ALIPAY);
        map.put("type", 1);
        map.put("bankName", "支付宝");
        map.put("prop", 0.6);
        payCharge.add(map);
        Map<String, Object> map1 = new HashMap<>();
        map1.put("bankId", OrderConstant.PAY_WAY_UNIONPAY);
        map1.put("type", 1);
        map1.put("bankName", "银联");
        map1.put("lowest", 8);
        map1.put("prop", 0.45);
        payCharge.add(map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("bankId", OrderConstant.PAY_WAY_WXPAY);
        map2.put("type", 1);
        map2.put("bankName", "微信");
        map2.put("prop", 0.6);
        payCharge.add(map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("bankId", OrderConstant.PAY_WAY_UNIONPAY);
        map3.put("type", 0);
        map3.put("bankName", "银联");
        map3.put("prop", 0);
        map3.put("regulate", 200);
        payCharge.add(map3);
    }

    public static List<Map<String, Object>> getPayCharge() {
        return payCharge;
    }

    /**
     * 获取最小额
     *
     * @return
     */
    public static Map<String, Object> getMinChargeAmount() {
        Map<String, Object> map = new HashMap<>(1);
        map.put("num", 10);
        return map;
    }

    /**
     * 计算手续费
     *
     * @param bankId
     * @param amount
     * @return
     */
    public static long countFee(String bankId, long amount) {
        List<Map<String, Object>> list = getPayCharge();
        for (Map<String, Object> map : list) {
            if ((int) map.get("type") == 1 && map.get("bankId").equals(bankId)) {
                double prop = (double) map.get("prop");
                long num = (long) (amount * prop / 100);
                return num;
            }
        }
        return 0;
    }

    /**
     * 苹果支付产品对应数据金额
     *
     * @param productId
     * @return
     */
    public static IOSPayConfig getIosProductConfig(String productId) {
        return iosProductEnum.get(productId);
    }

    /**
     * 计算实际金额
     *
     * @param amount
     * @return
     */
    public static IOSPayConfig getIosProductConfig(long amount) {
        IOSPayConfig config = iosProductEnum.get("ios.amount." + amount);
        return config;
    }

}
