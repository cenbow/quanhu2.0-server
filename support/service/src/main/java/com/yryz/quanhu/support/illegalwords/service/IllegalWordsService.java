package com.yryz.quanhu.support.illegalwords.service;

import java.util.Set;

import com.github.pagehelper.Page;
import com.yryz.quanhu.support.illegalwords.entity.BasicIllegalWords;

/**
 * 违规词服务
 */
public interface IllegalWordsService {

	/**
	 * 获取内容里面的敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @param text
	 * @return
	 * @Description 检查内容是否存在敏感词并返回内容里面的敏感词
	 */
	public Set<String> getIllegalWords(String text);
	
	/**
	 * 检查内容是否存在敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @param text
	 * @return
	 * @Description 检查内容是否存在敏感词
	 */
	public boolean checkIllegalWords(String text);
	
	/**
	 * 替换存在的敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @param text
	 * @param replaceWord
	 * @Description 找到敏感词并替换成replaceWord,replaceWord为空就使用*代替
	 */
	public String relpaceIllegalWord(String text,String replaceWord);
	
	/**
	 * 删除敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param id
	 * @return
	 * @Description 删除不需要的敏感词
	 */
	int delete(Long id);

	/**
	 * 保存敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 新增敏感词
	 */
	int save(BasicIllegalWords record);

	/**
	 * 查询敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param word
	 * @param pageNo
	 * @param pageSize
	 * @return
	 * @Description 查询敏感词
	 */
	Page<BasicIllegalWords> listByParam(Integer pageNo,Integer pageSize,String word);

	/**
	 * 更新敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 更新敏感词
	 */
	int update(BasicIllegalWords record);
}
