/**
 * author:XXX
 */
package com.yryz.quanhu.behavior.read.entity;


import com.yryz.common.utils.DateUtils;

import java.util.Date;
import java.util.List;

public class ReadCountRule {

    private int dayRange;

    private List<HotTime> hotTimeList;

    private List<DayRule> dayRuleList;

    private Date beginRangeDay;

    public int getDayRange() {
        return dayRange;
    }

    public void setDayRange(int dayRange) {
        this.dayRange = dayRange;
    }

    public List<HotTime> getHotTimeList() {
        return hotTimeList;
    }

    public void setHotTimeList(List<HotTime> hotTimeList) {
        this.hotTimeList = hotTimeList;
    }

    public List<DayRule> getDayRuleList() {
        return dayRuleList;
    }

    public void setDayRuleList(List<DayRule> dayRuleList) {
        this.dayRuleList = dayRuleList;
    }

    public Date getBeginRangeDay() {
        try{
            return DateUtils.addHours(new Date(),0-dayRange);
        }catch (Exception e){
            return beginRangeDay;
        }
    }

    public void setBeginRangeDay(Date beginRangeDay) {
        this.beginRangeDay = beginRangeDay;
    }
}