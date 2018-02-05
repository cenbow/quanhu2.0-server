package com.yryz.quanhu.behavior.report.vo;

import com.yryz.quanhu.behavior.report.entity.Report;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 15:50 2018/2/3
 */
public class ReportVoForAdmin extends Report {

    private String nickName;
    private String createDateStr;
    private String updateDateStr;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public String getUpdateDateStr() {
        return updateDateStr;
    }

    public void setUpdateDateStr(String updateDateStr) {
        this.updateDateStr = updateDateStr;
    }
}
