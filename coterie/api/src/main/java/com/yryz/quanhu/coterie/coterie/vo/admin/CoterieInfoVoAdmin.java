package com.yryz.quanhu.coterie.coterie.vo.admin;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyunfei
 */
public class CoterieInfoVoAdmin extends CoterieInfoAdmin implements Serializable {

    /**
     * 圈主信息   ownerId
     */
    @ApiModelProperty("用户图标")
    private String userIcon;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("姓名")
    private String ownerName;

    @ApiModelProperty("个人简介")
    private String ownerIntro;

    @ApiModelProperty("达人描述")
    private String starDesc;

    public String getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(String userIcon) {
        this.userIcon = userIcon;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerIntro() {
        return ownerIntro;
    }

    public void setOwnerIntro(String ownerIntro) {
        this.ownerIntro = ownerIntro;
    }

    public String getStarDesc() {
        return starDesc;
    }

    public void setStarDesc(String starDesc) {
        this.starDesc = starDesc;
    }
}
