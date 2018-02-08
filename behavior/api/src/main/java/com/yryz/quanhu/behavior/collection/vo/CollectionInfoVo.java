package com.yryz.quanhu.behavior.collection.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.yryz.quanhu.user.vo.UserSimpleVO;

import java.io.Serializable;

/**
 * @ClassName: CollectionInfoVo
 * @Description: CollectionInfoVo
 * @author jiangzhichao
 * @date 2018-01-26 17:57:44
 *
 */
public class CollectionInfoVo implements Serializable {

    /**
     * 唯一id
     * */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long kid;

    /**
     * 资源ID
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private  Long resourceId;

    /**
     * 资源类型
     */
    private  String moduleEnum;

    /**
     * 私圈Id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private  Long coterieId;

    /**
     * 私圈名字
     * */
    private String coterieName;

    /**
     * 发布用户id
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private  Long userId;

    /**
     * 标题
     */
    private  String title;

    /**
     * 内容
     */
    private  String content;

    /**
     * 扩展字段，仅供展示使用，由前端的发布方和列表解析方解决
     */
    private String extJson;

    /**
     * 0正常，1已删除
     */
    private  Integer delFlag;

    /**
     * appId
     */
    private  String appId;

    /**
     * 用户对象
     * */
    private UserSimpleVO user;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public String getCoterieName() {
        return coterieName;
    }

    public void setCoterieName(String coterieName) {
        this.coterieName = coterieName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtJson() {
        return extJson;
    }

    public void setExtJson(String extJson) {
        this.extJson = extJson;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }
}
