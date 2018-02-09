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

    /**
     * 被评论人昵称
     */
    private String targetUserNickName;

    /**
     * 多个kid的组合
     */
    private String kids;

    public String getKids() {
        return kids;
    }

    public void setKids(String kids) {
        this.kids = kids;
    }

    public String getTargetUserNickName() {
        return targetUserNickName;
    }

    public void setTargetUserNickName(String targetUserNickName) {
        this.targetUserNickName = targetUserNickName;
    }

    public long getTopId() {
        return topId;
    }

    public void setTopId(long topId) {
        this.topId = topId;
    }

    public long getParentId() {
        return parentId;
    }

    public void setParentId(long parentId) {
        this.parentId = parentId;
    }

    public long getParentUserId() {
        return parentUserId;
    }

    public void setParentUserId(long parentUserId) {
        this.parentUserId = parentUserId;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(long coterieId) {
        this.coterieId = coterieId;
    }

    public long getResourceId() {
        return resourceId;
    }

    public void setResourceId(long resourceId) {
        this.resourceId = resourceId;
    }

    public long getTargetUserId() {
        return targetUserId;
    }

    public void setTargetUserId(long targetUserId) {
        this.targetUserId = targetUserId;
    }

    public String getContentComment() {
        return contentComment;
    }

    public void setContentComment(String contentComment) {
        this.contentComment = contentComment;
    }

    public byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(byte delFlag) {
        this.delFlag = delFlag;
    }

    public byte getRecommend() {
        return recommend;
    }

    public void setRecommend(byte recommend) {
        this.recommend = recommend;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getUserImg() {
        return userImg;
    }

    public void setUserImg(String userImg) {
        this.userImg = userImg;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public byte getLikeFlag() {
        return likeFlag;
    }

    public void setLikeFlag(byte likeFlag) {
        this.likeFlag = likeFlag;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "topId=" + topId +
                ", parentId=" + parentId +
                ", parentUserId=" + parentUserId +
                ", moduleEnum='" + moduleEnum + '\'' +
                ", coterieId=" + coterieId +
                ", resourceId=" + resourceId +
                ", targetUserId=" + targetUserId +
                ", contentComment='" + contentComment + '\'' +
                ", shelveFlag=" + shelveFlag +
                ", delFlag=" + delFlag +
                ", recommend=" + recommend +
                ", tenantId='" + tenantId + '\'' +
                ", revision=" + revision +
                ", nickName='" + nickName + '\'' +
                ", userImg='" + userImg + '\'' +
                ", likeCount=" + likeCount +
                ", likeFlag=" + likeFlag +
                ", targetUserNickName='" + targetUserNickName + '\'' +
                ", kids='" + kids + '\'' +
                '}';
    }
}
