package com.yryz.common.constant;

import org.apache.commons.lang.StringUtils;

/**
 * 
 * @author jiangkun
 * @version 1.0
 * @date 2017年10月18日 上午9:44:17
 * @Description
 */
public class Constants {
	private static final String MOBILE = "Mobile";
	/**
	 * 应用ID
	 */
	public static final String APP_ID="appId";
	/**
	 * 应用安全码
	 */
	public static final String APP_SECRET="appSecret";
	/**
	 *  设备ID
	 */
	public static final String DEVICE_ID="devId";
	
	/**
	 * 请求来源 1-IOS 2-Android 3-PC
	 */
	public static final String DEV_TYPE = "devType";
	
	public static final String TOKEN = "token";
	
	public static final String CUST_ID = "custId";
	
	public static final String APP_VERSION = "appVersion";
	
	public static final String DEV_NAME = "devName";
	
	public static final String USER_AGENT = "User-Agent";
	
	public static final String DITCH_CODE = "ditchCode";
	
	/**
	 * 注册类型
	 *
	 */
	public enum RegType{
		WEIXIN(1,"Weixin"),
		SINA(2,"sina"),
		QQ(3,"QQ"),
		PHONE(4,"Phone");
		
		private int type;
		private String text;
		RegType(int type,String text) {
			this.type = type;
			this.text = text;
		}
		public int getType(){
			return this.type;
		}
		public String getText(){
			return this.text;
		}
		public static RegType getEnumByTye(int type){
			RegType regType = RegType.PHONE;
			switch (type) {
			case 1:
				regType = RegType.WEIXIN;
				break;
			case 2:
				regType = RegType.SINA;
				break;
			case 3:
				regType = RegType.QQ;
				break;
			case 4:
				regType = RegType.PHONE;
				break;
			default:
				break;
			}
			return regType;
		}
		
		/**
		 * 根据注册类型别名得到注册类型枚举对象
		 * @param text
		 * @return
		 */
		public static RegType getEnumByText(String text){
			RegType regType = null;
			switch (text) {
			case "Weixin":
			case "weixin":	
				regType = RegType.WEIXIN;
				break;
			case "Sina":
			case "weibo":
				regType = RegType.SINA;
				break;
			case "QQ":
			case "qq":
				regType = RegType.QQ;
				break;
			case "Phone":
				regType = RegType.PHONE;
				break;
			default:
				break;
			}
			return regType;
		}
	}
	
	/**
	 * 设备类型
	 *
	 */
	public enum DevType{
		/** ios */
		IOS("1","APP"),
		/** android */
		ANDROID("2","APP"),
		/** wap */
		WAP("3","Wap"),
		/** web */
		WEB("4","Web");
		
		private String type;
		private String label;
		
		DevType(String type,String label) {
			this.type = type;
			this.label = label;		
		}
		
		public String getType(){
			return type;
		}
		public String getLabel(){
			return label;
		}
		
		/**
		 * 根据devType得到设备类型枚举<br/>
		 * type=3时，根据userAgent判断是否WAP
		 * @param type
		 * @return
		 */
		public static DevType getEnumByType(String type,String userAgent){
			DevType devType = DevType.IOS;
			if(StringUtils.isBlank(type)){
				type = DevType.IOS.getType();
			}
			switch(type){
			case "1":
				devType = DevType.IOS;
				break;
			case "2":
				devType = DevType.ANDROID;
				break;
			case "3":
				if(StringUtils.contains(userAgent, MOBILE)){
					devType = DevType.WAP;
				} else{
					devType = DevType.WEB;
				}
				break;
			default:
				break;
			}
			return devType;
		}
		
		/**
		 * 根据devType label得到设备类型枚举<br/>
		 * @param label
		 * @return
		 */
		public static DevType getEnumByLable(String label){
			DevType devType = DevType.IOS;
			if(StringUtils.isBlank(label)){
				label = DevType.IOS.getLabel();
			}
			switch(label){
			case "APP":
				devType = DevType.IOS;
				break;
			case "Wap":
				devType = DevType.WAP;
				break;
			case "Web":
				devType = DevType.WEB;
				break;
			default:
				break;
			}
			return devType;
		}
	}
}
