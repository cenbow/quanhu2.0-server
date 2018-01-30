package com.yryz.quanhu.behavior.report.vo;


import java.io.Serializable;

/**
 * @Author:sun
 * @version:2.0
 * @Description:举报
 * @Date:Created in 16:16 2018/1/20
 */
public class ReportVo implements Serializable{

    private static final long serialVersionUID = 1611039581135205855L;

    private String informDesc;

    public String getInformDesc() {
        return informDesc;
    }

    public void setInformDesc(String informDesc) {
        this.informDesc = informDesc;
    }
}
