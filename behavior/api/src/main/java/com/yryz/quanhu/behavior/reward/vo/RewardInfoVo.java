package com.yryz.quanhu.behavior.reward.vo;

import com.yryz.quanhu.behavior.reward.entity.RewardInfo;
import com.yryz.quanhu.resource.vo.ResourceVo;
import com.yryz.quanhu.user.vo.UserSimpleVO;

/**
* @author wangheng
* @date 2018年1月26日 下午8:27:15
*/
public class RewardInfoVo extends RewardInfo {

    private static final long serialVersionUID = 1L;

    /**  
    * @Fields : 打赏用户信息
    */
    private UserSimpleVO user;

    private ResourceVo resourceVo;

    /**
    * 礼物名称
    */
    private String giftName;

    /**
    * 礼物图片
    */
    private String giftImage;

    /**  
    * @Fields : 用户真实 打赏金额
    */
    private Long rewardAmount;

    public UserSimpleVO getUser() {
        return user;
    }

    public void setUser(UserSimpleVO user) {
        this.user = user;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public String getGiftImage() {
        return giftImage;
    }

    public void setGiftImage(String giftImage) {
        this.giftImage = giftImage;
    }

    public ResourceVo getResourceVo() {
        return resourceVo;
    }

    public void setResourceVo(ResourceVo resourceVo) {
        this.resourceVo = resourceVo;
    }

    public Long getRewardAmount() {
        return rewardAmount;
    }

    public void setRewardAmount(Long rewardAmount) {
        this.rewardAmount = rewardAmount;
    }
}
