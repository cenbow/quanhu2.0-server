package com.yryz.quanhu.behavior.report.contants;

/**
 * @Author:sun
 * @version:
 * @Description:
 * @Date:Created in 15:53 2018/1/22
 */
public class ReportConstatns {

    public static final String QH_INFORM="qh_inform";



    public static enum SOLUTION{

        WAIT        ("WAIT","待处理"),
        KEEP        ("KEEP","维持现状"),
        SHELVE      ("SHELVE","资源下架"),
        MUTE        ("MUTE","用户禁言"),
        WARN        ("WARN","用户警告"),
        FREEZE      ("FREEZE","用户冻结"),
        LOCK        ("LOCK","用户锁定"),
        ;

        private String type;
        private String desc;

        SOLUTION(String type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
