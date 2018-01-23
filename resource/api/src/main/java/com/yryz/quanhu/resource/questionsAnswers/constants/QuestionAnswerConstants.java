package com.yryz.quanhu.resource.questionsAnswers.constants;

/**
* @Title: QuestionAnswerConstants.java
* @Description: 问答模块 业务常量类
* @Package com.yryz.activeii.coterie.question.answer.constants
* @author wangheng
* @date 2017年12月4日 下午4:35:15
*/
public class QuestionAnswerConstants {
	
	public static final String payPassword = "123456";
	
	public static final int VALID_HOUR = 48;
	
	public static final String ANONYMOUS_USER_NAME = "匿名用户";
	
	/**  
	* @ClassName: questionType  
	* @Description: 问题类型:10一对一 11:一对多
	* @author wangheng
	* @date 2017年12月4日 下午4:51:23  
	*    
	*/
	public static class questionType {
		/**  
		* 一对一
		*/
		public static final Byte ONE_TO_ONE = 10;

		/**  
		* 一对多
		*/
		public static final Byte ONE_TO_MANY = 11;
	}

	/**  
	* @ClassName: showType  
	* @Description: 显示类型：10 所有人可见 11 仅自己可见
	* @author wangheng
	* @date 2017年12月4日 下午4:53:08  
	*    
	*/
	public static class showType {
		/**  
		*所有人可见
		*/
		public static final Byte ALL =10;

		/**  
		* 仅自己可见
		*/
		public static final Byte ONESELF = 11;
	}

	/**  
	* @ClassName: anonymity  
	* @Description:  10 匿名 11 非匿名
	* @author wangheng
	* @date 2017年12月4日 下午4:54:06  
	*    
	*/
	public static class anonymityType {
		/**  
		*匿名
		*/
		public static final Byte YES = 10;

		/**  
		* 非匿名
		*/
		public static final Byte NO = 11;
	}

	/**  
	* @ClassName: validType  
	* @Description: 10 无效 11 有效
	* @author wangheng
	* @date 2017年12月4日 下午4:55:24  
	*    
	*/
	public static class validType {
		/**  
		*有效
		*/
		public static final Byte YES = 10;

		/**  
		* 无效
		*/
		public static final Byte NO = 11;
	}

	/**  
	* @ClassName: OrderType
	* @Description: 10 待付款 11 已付款  12 待退款 13已退款
	* @author wangheng
	* @date 2017年12月4日 下午4:56:25  
	*    
	*/
	public static class OrderType {

		/**
		 * 未付款
		 */
		public static final Byte Not_paid=10;

		/**
		 * 已付款
		 */
		public static final Byte paid=11;


		/**  
		* 待退款
		*/
		public static final Byte For_refund = 12;

		/**  
		* 已退款  
		*/
		public static final Byte Have_refund = 13;
	}


	/**
	 * 问题是否回答标记：10未回答，11已回答，12 已拒绝
	 */
	public static  class AnswerdFlag{
		/**
		 * 未回答
		 */
		public static final Byte NOt_ANSWERED=10;

		/**
		 * 已回答
		 */
		public static final Byte ANSWERED=11;

		/**
		 * 已拒绝
		 */
		public static final Byte REJECT_ANSWERED=12;
	}

}
