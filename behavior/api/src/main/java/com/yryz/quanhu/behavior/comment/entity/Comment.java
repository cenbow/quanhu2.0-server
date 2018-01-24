package com.yryz.quanhu.behavior.comment.entity;

import com.yryz.common.entity.GenericEntity;


/**
 * @Author:sun
 * @version:2.0
 * @Descripton:评论
 * @Date:Created in 13:38 2018/1/23
 */
public class Comment extends GenericEntity {

    /**
     * 根评论ID
     */
    private long topId;

    /**
     * 父级评论ID
     */
    private long parentId;

    /**
     * 父级评论用户ID
     */
    private long parentUserId;

    /**
     * 目标源功能ID
     */
    private String moduleEnum;

    /**
     * 私圈ID
     */
    private long coterieId;

    /**
     * 目标源/资源Id
     */
    private long resourceId;

    /**
     * 目标源用户ID
     */
    private long targetUserId;

    /**
     * 评论内容(文字)
     */
    private String contentComment;

    /**
     * 上下架状态(10上架 11下架)
     */
    private byte shelveFlag;

    /**
     * 删除标志(10正常 11已删除)
     */
    private byte delFlag;

    /**
     *推荐状态（ 10非推荐 11 推荐）
     */
    private byte recommend;

    /**
     *租户ID
     */
    private String tenantId;

    /**
     * 数据更新版本
     */
    private int revision;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 用户头像
     */
    private String userImg;

    /**
     *点赞数
     */
    private int likeCount;

    /**
     *是否点过赞
     */
    private byte likeFlag;

    public byte getLikeFlag() {
        return likeFlag;
    }

    public Comment setLikeFlag(byte likeFlag) {
        this.likeFlag = likeFlag;
        return this;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public Comment setLikeCount(int likeCount) {
        this.likeCount = likeCount;
        return this;
    }



    public String getUserImg() {
        return userImg;
    }

    public Comment setUserImg(String userImg) {
        this.userImg = userImg;
        return this;
    }

    public long getTopId() {
        return topId;
    }

    public Comment setTopId(long topId) {
        this.topId = topId;
        return this;
    }

    public long getParentId() {
        return parentId;
    }

    public Comment setParentId(long parentId) {
        this.parentId = parentId;
        return this;
    }

    public long getParentUserId() {
        return parentUserId;
    }

    public Comment setParentUserId(long parentUserId) {
        this.parentUserId = parentUserId;
        return this;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public Comment setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
        return this;
    }

    public long getCoterieId() {
        return coterieId;
    }

    public Comment setCoterieId(long coterieId) {
        this.coterieId = coterieId;
        return this;
    }

    public long getResourceId() {
        return resourceId;
    }

    public Comment setResourceId(long resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public Comment setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
        return this;
    }

    public String getContentComment() {
        return contentComment;
    }

    public Comment setContentComment(String contentComment) {
        this.contentComment = contentComment;
        return this;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public Comment setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
        return this;
    }

    public byte getDelFlag() {
        return delFlag;
    }

    public Comment setDelFlag(byte delFlag) {
        this.delFlag = delFlag;
        return this;
    }

    public byte getRecommend() {
        return recommend;
    }

    public Comment setRecommend(byte recommend) {
        this.recommend = recommend;
        return this;
    }

    public String getTenantId() {
        return tenantId;
    }

    public Comment setTenantId(String tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public int getRevision() {
        return revision;
    }

    public Comment setRevision(int revision) {
        this.revision = revision;
        return this;
    }

    public String getNickName() {
        return nickName;
    }

    public Comment setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }


}
