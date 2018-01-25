package com.yryz.quanhu.support.illegalwords.common;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

/**
 * DFA（Deterministic Finite Automaton 确定有穷自动机）
 * DFA单词树（以下简称单词树），常用于在某大段文字中快速查找某几个关键词是否存在。<br>
 * 单词树使用group区分不同的关键字集合，不同的分组可以共享树枝，避免重复建树。<br>
 * 单词树使用树状结构表示一组单词。<br>
 * 例如：红领巾，红河构建树后为：<br>
 *                 红                    <br>
 *              /      \                 <br>
 *           领         河             <br>
 *          /                            <br>
 *        巾                            <br>
 *其中每个节点都是一个WordTree对象，查找时从上向下查找。<br>
 * @author Looly
 *
 */
public class WordTree extends HashMap<Character, WordTree>{
	private static final long serialVersionUID = -4646423269465809276L;
	public static Map<String,Set<String>> endWordMap = new HashMap<>();
	/**
	 * 敏感词字符末尾标识，用于标识单词末尾字符
	 */
	private Set<Character> endCharacterSet = new HashSet<>();
	//--------------------------------------------------------------------------------------- Constructor start
	/**
	 * 默认构造
	 */
	public WordTree() {
	}
	//--------------------------------------------------------------------------------------- Constructor start
	
	//------------------------------------------------------------------------------- add word
	
	/**
	 * 增加一组单词
	 * @param words 单词集合
	 */
	public void addWords(Collection<String> words){
		if(CollectionUtils.isEmpty(words)){
			words = new HashSet<>(0);
		}
		else if(false == (words instanceof Set)){
			words = new HashSet<>(words);
		}
		for (String word : words) {
			addWord(word);
		}
	}
	
	/**
	 * 增加一组单词
	 * @param words 单词数组
	 */
	public void addWords(String... words){
		HashSet<String> wordsSet = new HashSet<String>();
		for (String word : wordsSet) {
			addWord(word);
		}
	}
	
	/**
	 * 添加单词，使用默认类型
	 * @param word 单词
	 */
	public void addWord(String word) {
		WordTree parent = null;
		WordTree current = this;
		WordTree child = null;
		char currentChar = (char) -1;
		int length = word.length();
		for(int i = 0; i < length; i++){
			currentChar = word.charAt(i);
			if(false == StopChar.isStopChar(currentChar)){//只处理合法字符
				child = current.get(currentChar);				
				if(child == null){
					//无子类，新建一个子节点后存放下一个字符
					child = new WordTree();
					current.put(currentChar, child);
				}
				
				parent = current;
				current = child;
			}
		}
		if(null != parent){
			parent.setEnd(currentChar,word);
		}
	}
	
	//------------------------------------------------------------------------------- match
	/**
	 * 指定文本是否包含树中的词
	 * @param text 被检查的文本
	 * @return 是否包含
	 */
	public boolean isMatch(String text){
		if(null == text){
			return false;
		}
		return null != match(text);
	}
	
	/**
	 * 获得第一个匹配的关键字
	 * @param text 被检查的文本
	 * @return 匹配到的关键字
	 */
	public String match(String text){
		if(null == text){
			return null;
		}
		List<String> matchAll = matchAll(text, 1);
		if(CollectionUtils.isNotEmpty(matchAll)){
			return matchAll.get(0);
		}
		return null;
	}
	
	//------------------------------------------------------------------------------- match all
	/**
	 * 找出所有匹配的关键字
	 * @param text 被检查的文本
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text) {
		return matchAll(text, -1);
	}
	
	/**
	 * 找出所有匹配的关键字
	 * @param text 被检查的文本
	 * @param limit 限制匹配个数
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text, int limit) {
		return matchAll(text, limit, false, false);
	}
	
	/**
	 * 找出所有匹配的关键字<br>
	 * 密集匹配原则：假如关键词有 ab,b，文本是abab，将匹配 [ab,b,ab]<br>
	 * 贪婪匹配（最长匹配）原则：假如关键字a,ab，最长匹配将匹配[a, ab]
	 * 
	 * @param text 被检查的文本
	 * @param limit 限制匹配个数
	 * @param isDensityMatch 是否使用密集匹配原则
	 * @param isGreedMatch 是否使用贪婪匹配（最长匹配）原则
	 * @return 匹配的词列表
	 */
	public List<String> matchAll(String text, int limit, boolean isDensityMatch, boolean isGreedMatch) {
		if(null == text){
			return null;
		}
		
		List<String> findedWords = new ArrayList<String>();
		WordTree current = this;
		int length = text.length();
		StringBuilder wordBuffer;//存放查找到的字符缓存。完整出现一个词时加到findedWords中，否则清空
		char currentChar;
		for (int i = 0; i < length; i++) {
			wordBuffer = new StringBuilder();
			for (int j = i; j < length; j++) {
				currentChar = text.charAt(j);
//				Console.log("i: {}, j: {}, currentChar: {}", i, j, currentChar);
				if(StopChar.isStopChar(currentChar)){
					if(wordBuffer.length() > 0){
						//做为关键词中间的停顿词被当作关键词的一部分被返回
						wordBuffer.append(currentChar);
					}else{
						//停顿词做为关键词的第一个字符时需要跳过
						i++;
					}
					continue;
				}else if(false == current.containsKey(currentChar)){
					//非关键字符被整体略过，重新以下个字符开始检查
					break;
				}
				wordBuffer.append(currentChar);
				if(current.isEnd(currentChar,wordBuffer.toString())){
					//到达单词末尾，关键词成立，从此词的下一个位置开始查找
					findedWords.add(wordBuffer.toString());
					if(limit > 0 && findedWords.size() >= limit){
						//超过匹配限制个数，直接返回
						return findedWords;
					}
					if(false == isDensityMatch){
						//如果非密度匹配，跳过匹配到的词
						i = j;
					}
					if(false == isGreedMatch){
						//如果懒惰匹配（非贪婪匹配）。当遇到第一个结尾标记就结束本轮匹配
						break;
					}
				}
				current = current.get(currentChar);
				if(null == current){
					break;
				}
			}
			current = this;
		}
		return findedWords;
	}
	
	
	/**
	 * 替换掉敏感词
	 * @param text 被检查的文本
	 * @param replaceWord 把敏感词替换为该字符
	 * @return 
	 */
	public String matchAllAndReplace(String text, String replaceWord) {
		if(StringUtils.isBlank(replaceWord)){
			replaceWord = "*";
		}
		return matchAllAndReplace(text,replaceWord, false, false);
	}
	
	/**
	 * 替换掉敏感词<br>
	 * 密集匹配原则：假如关键词有 ab,b，文本是abab，将匹配 [ab,b,ab]<br>
	 * 贪婪匹配（最长匹配）原则：假如关键字a,ab，最长匹配将匹配[a, ab]
	 * 
	 * @param text 被检查的文本
	 * @param isDensityMatch 是否使用密集匹配原则
	 * @param isGreedMatch 是否使用贪婪匹配（最长匹配）原则
	 * @param replaceWord 把敏感词替换为该字符
	 * @return 
	 */
	public String matchAllAndReplace(String text,String replayWord,boolean isDensityMatch, boolean isGreedMatch) {
		if(null == text){
			return null;
		}
		String newText = null;
		WordTree current = this;
		int length = text.length();
		StringBuilder wordBuffer;//存放查找到的字符缓存。完整出现一个词时加到findedWords中，否则清空
		char currentChar;
		for (int i = 0; i < length; i++) {
			wordBuffer = new StringBuilder();
			for (int j = i; j < length; j++) {
				currentChar = text.charAt(j);
//				Console.log("i: {}, j: {}, currentChar: {}", i, j, currentChar);
				if(StopChar.isStopChar(currentChar)){
					if(wordBuffer.length() > 0){
						//做为关键词中间的停顿词被当作关键词的一部分被返回
						wordBuffer.append(currentChar);
					}else{
						//停顿词做为关键词的第一个字符时需要跳过
						i++;
					}
					continue;
				}else if(false == current.containsKey(currentChar)){
					//非关键字符被整体略过，重新以下个字符开始检查
					break;
				}
				wordBuffer.append(currentChar);
				if(current.isEnd(currentChar,wordBuffer.toString())){
					StringBuffer replaceWords = new StringBuffer();
					for(int l = 0 ; l < wordBuffer.length() ; l ++){
						replaceWords.append(replayWord);
					}
					//到达单词末尾，关键词成立，从此词的下一个位置开始查找
					text = text.replaceAll(wordBuffer.toString(), replaceWords.toString());

					if(false == isDensityMatch){
						//如果非密度匹配，跳过匹配到的词
						i = j;
					}
					if(false == isGreedMatch){
						//如果懒惰匹配（非贪婪匹配）。当遇到第一个结尾标记就结束本轮匹配
						break;
					}
				}
				current = current.get(currentChar);
				if(null == current){
					break;
				}
			}
			current = this;
		}
		newText = text;
		return newText;
	}
	
	//--------------------------------------------------------------------------------------- Private method start
	/**
	 * 是否末尾
	 * @param c 检查的字符
	 * @return 是否末尾
	 */
	private boolean isEnd(Character c,String word){
		if(MapUtils.isEmpty(WordTree.endWordMap)){
			return false;
		}
		Set<String> endWordSet = WordTree.endWordMap.get(String.valueOf(c));
		if(CollectionUtils.isEmpty(endWordSet)){
			return false;
		}
		return this.endCharacterSet.contains(c) && endWordSet.contains(word);
	}
	
	/**
	 * 设置是否到达末尾
	 * @param c 设置结尾的字符
	 */
	private void setEnd(Character c,String word){
		if(null != c){
			this.endCharacterSet.add(c);
			Set<String> endWordSet = WordTree.endWordMap.get(String.valueOf(c));
			if(CollectionUtils.isEmpty(endWordSet)){
				endWordSet = new HashSet<>();
			}
			endWordSet.add(word);
			WordTree.endWordMap.put(String.valueOf(c), endWordSet);
		}
	}
	//--------------------------------------------------------------------------------------- Private method end

}
