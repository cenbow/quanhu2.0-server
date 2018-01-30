package com.yryz.quanhu.resource.questionsAnswers.vo;

/**
 * ${DESCRIPTION}
 *
 * @author wanght
 * @create 2018-01-29 16:13
 **/
public class MessageBusinessVo {

   private  Long kid;

   private  Long fromUserId;

   private Long tosendUserId;

    private String title;

    private Byte isAnonymity;

    private String coterieId;

    private Long amount;

    private String imgUrl;

    private String moduleEnum;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
    }

    public Long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(Long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public Long getTosendUserId() {
        return tosendUserId;
    }

    public void setTosendUserId(Long tosendUserId) {
        this.tosendUserId = tosendUserId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Byte getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(Byte isAnonymity) {
        this.isAnonymity = isAnonymity;
    }

    public String getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(String coterieId) {
        this.coterieId = coterieId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }
}
