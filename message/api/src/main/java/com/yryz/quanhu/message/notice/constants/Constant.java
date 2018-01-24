package com.yryz.quanhu.message.notice.constants;

public class Constant {

    public static enum ConfigType {
        /**
         * 抽奖池
         */
        LOTTERY("lottery"),
        /**
         * 首页广告
         */
        INDEXADVERT("indexAdvert"),
        /**
         * ios强制升级配置
         */
        UPGRADE("upgrade_ios"),
        /**
         * 安卓强制升级配置
         */
        UPGRADEANDROID("upgrade_android"),
        /**
         * 抽奖实物奖品有效期
         */
        LOTTOAWARDVALITIME("lotto_award_vali_time"),

        /**
         * 客服工作时间配置
         */
        CUSTOM_WORK_TIME("custom_work_time");

        private String name;

        private ConfigType(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }
    }

    /**
     * 配置基本信息
     */
    public final static String TABLESPACE_CONFIGINFO = "COFIN";

    /**
     * 按配置类型组合配置信息队列
     */
    public final static String TABLESPACE_CONFIGTYPESORTSET = "COFTPSS";

    //红包领取信息
    public final static String TABLESPACE_REDPACKETRECEIVE = "RRIF";

    //红包基本信息
    public final static String TABLESPACE_REDPACKETINFO = "RPIF";

    //红包领取失败队列
    public final static String TABLESPACE_REDPACKETRECEIVEFAILLIST = "RPRFLS";

    //统计已收发红包总金额
    public final static String TABLESPACE_REDPACKETTOTALAMOUNT = "CRPA";

    //用户是否拆过红包
    public final static String TABLESPACE_REDPACKETUSC = "RCCF";

    //按用户查询已发红包队列
    public final static String TABLESPACE_REDPACKETURP = "RCSR";
    //按用户查询已领取红包队列
    public final static String TABLESPACE_REDPACKETURRP = "RCSRR";

    //用户查询当前红包领取情况队列
    public final static String TABLESPACE_REDPACKETRCS = "RPSR";

    //用户基本信息
    public final static String TABLESPACE_UI = "RCIF";

    /**
     * 场景关联红包ID
     */
    public final static String TABLESPACE_REDPACKETINFOID = "RPIID";

    /**
     * 打赏信息
     */
    public final static String TABLESPACE_REWARDINFO = "RWIF";

    /**
     * 普通打赏列表id队列
     */
    public final static String TABLESPACE_REWARDLIST = "RWLS";

    /**
     * 我的打赏
     */
    public final static String TABLESPACE_REWARDMYLIST = "RWMLT";
    /**
     * 统计圈子打赏次数
     */
    public final static String TABLESPACE_REWARDTOTALCOUNT = "RWTC";

    /**
     * 礼物信息
     */
    public final static String TABLESPACE_GIFTINFO = "GFIN";

    /**
     * 礼物id队列
     */
    public final static String TABLESPACE_GIFTLIST = "GFLIST";
    /**
     * 公告信息
     */
    public final static String TABLESPACE_NOTICEINFO = "NTIF";
    /**
     * 公告id队列
     */
    public final static String TABLESPACE_NOTICELIST = "NTLT";

    /**
     * 抢红包券信息
     */
    public final static String TABLESPACE_REDPACKETVOUCHERINFO = "RPVI";

    /**
     * 点赞信息
     */
    public final static String TABLESPACE_GOODINFO = "GDIF";
    /**
     * 对象点赞id队列
     */
    public final static String TABLESPACE_GOODLIST = "GDLS";

    /**
     * 对象点赞用户队列
     */
    public final static String TABLESPACE_GOODCUSTLIST = "GDCUSTLS";
    /**
     * 评论信息
     */
    public final static String TABLESPACE_COMMENTINFO = "CMIN";
    /**
     * 评论id队列
     */
    public final static String TABLESPACE_COMMENTLIST = "CMLS";

    /**
     * 评论custId队列
     */
    public final static String TABLESPACE_COMMENTCUSTLIST = "CMCUSTLS";
    /**
     * 用户评论数统计
     */
    public final static String TABLESPACE_COMMENTSTAT = "CMCST";

    /**
     * 日期基数权重
     */
    public final static double SCOREEXE = 100000000000000d;

    /**
     * 排序方式
     */
    public static enum Sort {
        DESC, ASC
    }

    public final static String ZREO = "0";

    public final static String ONE = "1";

    public final static String TWO = "2";

    public final static String THREE = "3";

    public final static double MAXPERCENT = 3000;

    /**
     * 公告模板文件名
     */
    public static final String NOTICETHTML = "notice";
    /**
     * oss文件路径名称
     */
    public static final String ADSPATH = "ads/";


    /**
     * 红包类型
     *
     * @author danshiyu
     */
    public static class PacketType {
        //拼手气红包
        public static final int LUCK = 0;
        //普通红包
        public static final int NORMAL = 1;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) LUCK:
                    strReturn = "拼手气红包";
                    break;
                case (int) NORMAL:
                    strReturn = "普通红包";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 红包场景
     *
     * @author danshiyu
     */
    public static class PacketScene {
        /**
         * 公共圈子红包
         */
        public static final int PUBLIC_PACKET = 0;

        /**
         * 广告红包
         */
        public static final int AD_PACKET = 1;

        /**
         * 私密红包
         */
        public static final int SECRET_PACKET = 2;

        /**
         * 群红包
         */
        public static final int GROUP_PACKET = 3;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) PUBLIC_PACKET:
                    strReturn = "公共圈子";
                    break;
                case (int) AD_PACKET:
                    strReturn = "广告";
                    break;

                case (int) SECRET_PACKET:
                    strReturn = "私密";
                    break;
                case (int) GROUP_PACKET:
                    strReturn = "群";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 红包状态
     *
     * @author danshiyu
     */
    public static class PacketStatus {
        /**
         * 有效
         */
        public static final int EFFECT = 0;

        /**
         * 过期
         */
        public static final int EXPIRE = 1;

        /**
         * 已领完
         */
        public static final int RECEIVEOVER = 2;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) EFFECT:
                    strReturn = "有效";
                    break;
                case (int) EXPIRE:
                    strReturn = "过期";
                    break;

                case (int) RECEIVEOVER:
                    strReturn = "已抢光";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 红包领取状态
     *
     * @author danshiyu
     */
    public static class ReceiveStatus {
        /**
         * 未领取
         */
        public static final int NORECEIVE = 0;
        /**
         * 已领取
         */
        public static final int RECEIVED = 1;
        /**
         * 已过期
         */
        public static final int EXPIRE = 2;
        /**
         * 已退回
         */
        public static final int RETURN = 3;


        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) NORECEIVE:
                    strReturn = "未领取";
                    break;
                case (int) RECEIVED:
                    strReturn = "已领取";
                    break;

                case (int) RETURN:
                    strReturn = "过期";
                    break;
                case (int) EXPIRE:
                    strReturn = "已退回";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 红包审核状态
     *
     * @author danshiyu
     */
    public static class AduitStatus {
        /**
         * 未审核
         */
        public static final int NOAUDIT = 0;
        /**
         * 审核通过
         */
        public static final int AUDITSUCCESS = 1;
        /**
         * 审核不通过
         */
        public static final int AUDITFAIL = 2;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) NOAUDIT:
                    strReturn = "未审核";
                    break;
                case (int) AUDITSUCCESS:
                    strReturn = "审核成功";
                    break;

                case (int) AUDITFAIL:
                    strReturn = "审核不通过";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 性别
     */
    public static class CustSex {
        /**
         * 男
         */
        public static final int Male = 1;
        /**
         * 女
         */
        public static final int Female = 2;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case (int) Male:
                    strReturn = "男";
                    break;
                case (int) Female:
                    strReturn = "女";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 红包返回状态码
     *
     * @author
     */
    public static class RedpacketResponseCode {
        /**
         * 广告红包没有审核
         */
        public static final int NO_AUDIT = 20;
        /**
         * 红包过期
         */
        public static final int EXPIRE = 21;
        /**
         * 已领取
         */
        public static final int RECEIVED = 22;
        /**
         * 已抢光
         */
        public static final int EMPTY = 23;
        /**
         * 自己的红包
         */
        public static final int MYPACKET = 24;
        /**
         * 没有抢红包券
         */
        public static final int NOVOUCHER = 25;

        public static final String getName(int iCode) {

            String strReturn = "";
            switch ((int) iCode) {
                case RECEIVED:
                    strReturn = "已抢过";
                    break;
                case EXPIRE:
                    strReturn = "已过期";
                    break;
                case EMPTY:
                    strReturn = "已抢光";
                    break;
                case MYPACKET:
                    strReturn = "本人红包查看详情";
                    break;
                case NO_AUDIT:
                    strReturn = "广告红包未审核或者审核不通过";
                    break;
            }
            return strReturn;
        }
    }

    /**
     * 审核状态
     *
     * @author danshiyu
     */
    public static class AuditStatus {
        /**
         * 审核中
         */
        public static final int AUDITING = 0;
        /**
         * 审核成功
         */
        public static final int AUDITSUCCESS = 1;
        /**
         * 审核失败
         */
        public static final int AUDITFAIL = 2;

        public static String getName(int code) {
            String name = null;

            switch (code) {
                case AUDITING:
                    name = "审核中";
                case AUDITSUCCESS:
                    name = "审核通过";
                case AUDITFAIL:
                    name = "不通过";
            }
            return name;
        }
    }

    public static class RewardResponseCode {
        /**
         * 余额不足
         */
        public static final int NOSUFF = 24;

        public static final String NOERRORMSG = "账户余额不足，请充值";
        /**
         * 支付密码错误
         */
        public static final int PWDERROR = 25;

        public static final String PWERRORMSG = "支付密码错误";

        public static String getText(int code) {
            String text = null;
            switch (code) {
                case 24:
                    text = NOERRORMSG;
                    break;
                case 25:
                    text = PWERRORMSG;
                    break;
                case 3:
                    text = "fail";
                    break;
                default:
                    text = "success";
                    break;
            }
            return text;
        }
    }

    public static class NoticeType {
        public static final int NORMAL = 0;

        public static final int ACTIVE = 1;

        public static String getName(int code) {
            String strReturn = "";
            switch ((int) code) {
                case (int) NORMAL:
                    strReturn = "公告";
                    break;
                case (int) ACTIVE:
                    strReturn = "活动";
                    break;

            }
            return strReturn;
        }
    }

    /**
     * 公告链接类型
     *
     * @author danshiyu
     * @version 1.0
     * @date 2017年10月17日 下午4:17:19
     */
    public static enum NoticeLinkType {
        /**
         * 内链
         */
        INNER(0),
        /**
         * 外链
         */
        OUT(1);
        private int type;

        /**
         * @throws
         */
        NoticeLinkType(int type) {
            this.type = type;
        }

        public int getType() {
            return this.type;
        }
    }

    /**
     * 作品类型
     *
     * @author danshiyu
     */
    public static enum InfoType {
        ALLTEXT(0),
        TEXT(1),
        VIDEO(2),
        VOICE(3);
        private final int value;

        InfoType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public String getText() {
            String text = null;
            switch (value) {
                case 0:
                    text = "纯文本";
                    break;
                case 1:
                    text = "图文";
                    break;
                case 2:
                    text = "视频";
                    break;
                case 3:
                    text = "音频";
                    break;
            }
            return text;
        }
    }
}
