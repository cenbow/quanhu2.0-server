package com.yryz.quanhu.behavior.like.contants;

/**
 * @Author:sun
 * @version:2.0
 * @Description:点赞
 * @Date:Created in 13:48 2018/1/24
 */
public class LikeContants {

    public static final String QH_LIKE="qh_like";
    
    /**
     * 点赞状态
     * @author Administrator
     *
     */
    public enum LikeFlag{
    	/** 未点赞*/
    	FALSE(10),
    	/** 已点赞 */
    	TRUE(11);
    	private int flag;
    	
    	LikeFlag(int flag) {
			this.flag = flag;
		}
    	public int getFlag(){
    		return this.flag;
    	}
    }
}
