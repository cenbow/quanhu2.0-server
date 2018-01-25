package com.yryz.quanhu.support.illegalwords.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import com.google.common.collect.Sets;
import com.yryz.common.utils.GsonUtils;

/**
 * 敏感词工具类
 * @author Looly
 *
 */
public final class SensitiveUtil {
	//private static final Logger log = LoggerFactory.getLogger(SensitiveUtil.class);
	public static final char DEFAULT_SEPARATOR = ',';
	
	private static WordTree sensitiveTree = new WordTree();
	
	private SensitiveUtil() {}  
	   
	/**
	 * @return 是否已经被初始化
	 */
	public static boolean isInited(){
		return !sensitiveTree.isEmpty();
	}
	
	/**
	 * 初始化敏感词树
	 * @param sensitiveWords 敏感词列表
	 */
	public static void init(Collection<String> sensitiveWords){
		WordTree newTree = new WordTree();
		WordTree.endWordMap.clear();
		newTree.addWords(sensitiveWords);
		sensitiveTree.clear();
		sensitiveTree = newTree;
		//log.debug("Sensitive init finished, sensitives: {}", sensitiveWords);
	}
	
	/**
	 * 初始化敏感词树
	 * @param sensitiveWords 敏感词列表组成的字符串
	 * @param isAsync 是否异步初始化
	 * @param separator 分隔符
	 */
	public static void init(String sensitiveWords, char separator, boolean isAsync){
		if(StringUtils.isNotBlank(sensitiveWords)){
			String[] arr=sensitiveWords.split(String.valueOf(separator));
			;
			init(Arrays.asList(arr));
		}
	}
	
	/**
	 * 初始化敏感词树，使用逗号分隔每个单词
	 * @param sensitiveWords 敏感词列表组成的字符串
	 * @param isAsync 是否异步初始化
	 */
	public static void init(String sensitiveWords, boolean isAsync){
		init(sensitiveWords, DEFAULT_SEPARATOR, isAsync);
	}
	
	/**
	 * 是否包含敏感词
	 * @param text 文本
	 * @return 是否包含
	 */
	public static boolean containsSensitive(String text){
		return sensitiveTree.isMatch(text);
	}
	
	/**
	 * 是否包含敏感词
	 * @param obj bean，会被转为JSON字符串
	 * @return 是否包含
	 */
	public static boolean containsSensitive(Object obj){
		return sensitiveTree.isMatch(GsonUtils.parseJson(obj));
	}
	
	/**
	 * 替换找到的敏感词
	 * @author danshiyu
	 * @date 2017年9月12日
	 * @param text
	 * @Description 找到全部敏感词然后替换成*
	 */
	public static String replaceFindedSensitive(String text){
		return sensitiveTree.matchAllAndReplace(text, null);
	}
	
	/**
	 * 替换找到的敏感词
	 * @author danshiyu
	 * @date 2017年9月12日
	 * @param text
	 * @param replaceWord
	 * @Description 找到全部敏感词然后替换成replaceWord
	 */
	public static String replaceFindedSensitive(String text,String replaceWord){
		return sensitiveTree.matchAllAndReplace(text, replaceWord);
	}
	
	/**
	 * 查找敏感词，返回找到的第一个敏感词
	 * @param text 文本
	 * @return 敏感词
	 */
	public static String getFindedFirstSensitive(String text){
		return sensitiveTree.match(text);
	}
	
	/**
	 * 查找敏感词，返回找到的第一个敏感词
	 * @param obj bean，会被转为JSON字符串
	 * @return 敏感词
	 */
	public static String getFindedFirstSensitive(Object obj){
		return sensitiveTree.match(GsonUtils.parseJson(obj));
	}
	
	/**
	 * 查找敏感词，返回找到的所有敏感词
	 * @param text 文本
	 * @return 敏感词
	 */
	public static List<String> getFindedAllSensitive(String text){
		return sensitiveTree.matchAll(text);
	}
	
	/**
	 * 查找敏感词，返回找到的所有敏感词<br>
	 * 密集匹配原则：假如关键词有 ab,b，文本是abab，将匹配 [ab,b,ab]<br>
	 * 贪婪匹配（最长匹配）原则：假如关键字a,ab，最长匹配将匹配[a, ab]
	 * 
	 * @param text 文本
	 * @param isDensityMatch 是否使用密集匹配原则
	 * @param isGreedMatch 是否使用贪婪匹配（最长匹配）原则
	 * @return 敏感词
	 */
	public static List<String> getFindedAllSensitive(String text, boolean isDensityMatch, boolean isGreedMatch){
		return sensitiveTree.matchAll(text, -1, isDensityMatch, isGreedMatch);
	}
	
	/**
	 * 查找敏感词，返回找到的所有敏感词
	 * @param bean 对象，会被转为JSON
	 * @return 敏感词
	 */
	public static List<String> getFindedAllSensitive(Object bean){
		return sensitiveTree.matchAll(GsonUtils.parseJson(bean));
	}
	
	/**
	 * 查找敏感词，返回找到的所有敏感词<br>
	 * 密集匹配原则：假如关键词有 ab,b，文本是abab，将匹配 [ab,b,ab]<br>
	 * 贪婪匹配（最长匹配）原则：假如关键字a,ab，最长匹配将匹配[a, ab]
	 * 
	 * @param bean 对象，会被转为JSON
	 * @param isDensityMatch 是否使用密集匹配原则
	 * @param isGreedMatch 是否使用贪婪匹配（最长匹配）原则
	 * @return 敏感词
	 */
	public static List<String> getFindedAllSensitive(Object bean, boolean isDensityMatch, boolean isGreedMatch){
		return getFindedAllSensitive(GsonUtils.parseJson(bean), isDensityMatch, isGreedMatch);
	}
	
	
	public static void main(String[] args){
		Set<String> set = Sets.newHashSet("你妹的");
		SensitiveUtil.init(set);
		System.out.println(SensitiveUtil.sensitiveTree.toString());
		SensitiveUtil.init(set);
		System.out.println(SensitiveUtil.sensitiveTree.toString());
	}
}
