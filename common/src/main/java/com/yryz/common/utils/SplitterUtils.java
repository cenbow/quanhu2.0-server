package com.yryz.common.utils;

import com.google.common.base.Splitter;

/**
 * @author zhangkun
 * @version 1.0
 * @date 2018/2/1
 * @description
 */
public class SplitterUtils {

    public static final Splitter ID_SPLITTER = Splitter.on(",").omitEmptyStrings();

    public static final Splitter WAVY_LINE_SPLITTER = Splitter.on("~").omitEmptyStrings();

    public static final Splitter.MapSplitter MAP_SPLITTER = Splitter.on(";").omitEmptyStrings().withKeyValueSeparator(":");

}
