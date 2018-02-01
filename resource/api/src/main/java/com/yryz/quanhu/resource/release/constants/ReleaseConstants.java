package com.yryz.quanhu.resource.release.constants;

import java.util.Arrays;
import java.util.List;

/**
* @Description: 通用发布 常量集合
* @author wangheng
* @date 2018年1月22日 下午2:45:52
*/
public final class ReleaseConstants {

    /**  
    * @Fields : APP 平台发布模板 默认分类ID
    */
    public static final Long APP_DEFAULT_CLASSIFY_ID = -1L;
    /**  
    * @Fields : 私圈 发布模板 默认分类ID
    */
    public static final Long COTERIE_DEFAULT_CLASSIFY_ID = -2L;

    /**  
    * @Fields resource_split_char : 资源分割符号（支持正则）
    */
    public static final String RESOURCE_SPLIT_REGEX = ",";

    /**  
    * @ClassName: PropertyKey  
    * @Description: 配置表属性 key
    * @author wangheng
    * @date 2017年5月31日 下午4:11:20  
    *    
    */
    public static final class PropertyKey {
        /**  
        * @Fields COVERPLAN : 封面图
        */
        public static final String COVERPLAN = "coverPlan";
        /**  
        * @Fields TITLE : 标题
        */
        public static final String TITLE = "title";
        /**  
        * @Fields DESCRIPTION : 简介
        */
        public static final String DESCRIPTION = "description";
        /**  
        * @Fields TEXT : 文字
        */
        public static final String TEXT = "text";
        /**  
        * @Fields IMG : 图片
        */
        public static final String IMG = "img";
        /**  
        * @Fields AUDIO : 音频
        */
        public static final String AUDIO = "audio";
        /**  
        * @Fields VIDEO : 视频
        */
        public static final String VIDEO = "video";
        /**  
        * @Fields LABEL : 标签
        */
        public static final String LABEL = "label";

        /**  
        * @Fields Propertys : propertyKey 集合
        */
        public static final List<String> propertys = Arrays.asList(COVERPLAN, TITLE, DESCRIPTION, TEXT, IMG, AUDIO,
                VIDEO, LABEL);
    }

    /**  
     * @ClassName: ConfigVerifyType  
     * @Description: 属性校验类型
     * @author wangheng
     * @date 2017年6月1日 下午6:28:11  
     *    
     */
    public static final class PropertyVerifyType {

        /**  
        * @Fields length : 长度
        */
        public static final byte length = 1;

        /**  
        * @Fields number : 个数
        */
        public static final byte number = 2;

        /**  
        * @Fields scope : 范围
        */
        public static final byte scope = 3;

        /**  
        * @Fields other : 其他
        */
        public static final byte other = -1;

    }

    /**  
    * @ClassName: EnabledType  
    * @Description: 属性存在类型
    * @author wangheng
    * @date 2017年6月1日 下午6:44:14  
    *    
    */
    public static final class EnabledType {
        /**  
        * @Fields exist : 存在
        */
        public static final byte exist = 1;

        /**  
        * @Fields notExist : 不存在
        */
        public static final byte not_exist = 0;
    }

    /**  
    * @ClassName: RequiredType  
    * @Description: 属性必填类型
    * @author wangheng
    * @date 2017年6月1日 下午6:49:34  
    *    
    */
    public static final class RequiredType {
        /**  
        * @Fields exist : 非必填
        */
        public static final byte not_required = 0;

        /**  
        * @Fields required : 必填
        */
        public static final byte required = 1;
    }

    /**  
    * @Description: 列表排序类型
    * @author wangheng
    * @date 2018年1月22日 下午5:09:03  
    *    
    */
    public static final class OrderType {
        /**  
        * @Fields newest : 时间最新
        */
        public static final byte time_new = 1;

        /**  
        * @Fields first : 时间最早
        */
        public static final byte time_old = 2;
    }

    /**  
    * @Description: 可读标识
    * @author wangheng
    * @date 2018年1月24日 下午5:27:26  
    *    
    */
    public static final class CanReadType {
        /**  
        * @Fields : 不可读
        */
        public static final Byte NO = 10;

        /**  
        * @Fields : 可读
        */
        public static final Byte YES = 11;

    }

    /**  
    * @Fields : 每次操作触发（每日前两次发布文章正文内容超过100字时触发，每次记20分，最多记40分）
    */
    public static final int release_context_length_event = 100;
}
