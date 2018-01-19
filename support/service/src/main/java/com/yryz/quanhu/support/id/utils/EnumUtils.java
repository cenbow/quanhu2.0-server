
package com.yryz.quanhu.support.id.utils;

import org.springframework.util.Assert;

/**
 * EnumUtils provides the operations for {@link ValuedEnum} such as Parse, value of...
 * 
 * @author
 */
public abstract class EnumUtils {

    /**
     * Parse the bounded value into ValuedEnum
     * 
     * @param clz
     * @param value
     * @return
     */
    public static <T extends ValuedEnum<V>, V> T parse(Class<T> clz, V value) {
        Assert.notNull(clz, "clz can not be null");
        if (value == null) {
            return null;
        }

        for (T t : clz.getEnumConstants()) {
            if (value.equals(t.value())) {
                return t;
            }
        }
        return null;
    }

    /**
     * Null-safe valueOf function
     * 
     * @param <T>
     * @param enumType
     * @param name
     * @return
     */
    public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
        if (name == null) {
            return null;
        }

        return Enum.valueOf(enumType, name);
    }

}
