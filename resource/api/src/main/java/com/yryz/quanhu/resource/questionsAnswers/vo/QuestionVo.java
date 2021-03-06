package com.yryz.quanhu.resource.questionsAnswers.vo;



import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import com.yryz.quanhu.user.vo.UserSimpleVO;

public class QuestionVo  implements Serializable {

    
    private Long kid;

    private String title;

    private String content;

    private Byte questionType;

    private Byte shelveFlag;

    private Byte delFlag;

    private Date createDate;

    private Integer validTime;
    
    private Long coterieId;

    private Long chargeAmount;

    private Byte isOnlyShowMe;

    private Byte isAnonymity;

    private Byte isValid;

    private Byte answerdFlag;

    private String contentSource;

    private UserSimpleVO user;

    private String moduleEnum;

    private String orderId;


    /**  
    * @Fields : 服务器时间
    */
    private Date serviceCurrentDate;

    /**  
    * @Fields : 失效时间
    */
    private Date failureDate;
    /**
     * 阅读数状态
     */
    private Map<String, Long> statistics;

    private  UserSimpleVO targetUser;

    public Long getKid() {
        return kid;
    }

    public void setKid(Long kid) {
        this.kid = kid;
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

    public Byte getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Byte questionType) {
        this.questionType = questionType;
    }

    public Byte getShelveFlag() {
        return shelveFlag;
    }

    public void setShelveFlag(Byte shelveFlag) {
        this.shelveFlag = shelveFlag;
    }

    public Byte getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Byte delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getValidTime() {
        return validTime;
    }

    public void setValidTime(Integer validTime) {
        this.validTime = validTime;
    }

    public Long getCoterieId() {
        return coterieId;
    }

    public void setCoterieId(Long coterieId) {
        this.coterieId = coterieId;
    }

    public Long getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Long chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Byte getIsOnlyShowMe() {
        return isOnlyShowMe;
    }

    public void setIsOnlyShowMe(Byte isOnlyShowMe) {
        this.isOnlyShowMe = isOnlyShowMe;
    }

    public Byte getIsAnonymity() {
        return isAnonymity;
    }

    public void setIsAnonymity(Byte isAnonymity) {
        this.isAnonymity = isAnonymity;
    }

    public Byte getIsValid() {
        return isValid;
    }

    public void setIsValid(Byte isValid) {
        this.isValid = isValid;
    }

    public Byte getAnswerdFlag() {
        return answerdFlag;
    }

    public void setAnswerdFlag(Byte answerdFlag) {
        this.answerdFlag = answerdFlag;
    }

    public String getContentSource() {
        return contentSource;
    }

    public void setContentSource(String contentSource) {
        this.contentSource = contentSource;
    }

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }

    public String getModuleEnum() {
        return moduleEnum;
    }

    public void setModuleEnum(String moduleEnum) {
        this.moduleEnum = moduleEnum;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Map<String, Long> getStatistics() {
        return statistics;
    }

    public void setStatistics(Map<String, Long> statistics) {
        this.statistics = statistics;
    }

    public UserSimpleVO getTargetUser() {
        return targetUser;
    }

    public void setTargetUser(UserSimpleVO targetUser) {
        this.targetUser = targetUser;
    }

    public Date getServiceCurrentDate() {
        return serviceCurrentDate;
    }

    public void setServiceCurrentDate(Date serviceCurrentDate) {
        this.serviceCurrentDate = serviceCurrentDate;
    }

    public Date getFailureDate() {
        return failureDate;
    }

    public void setFailureDate(Date failureDate) {
        this.failureDate = failureDate;
    }
}
