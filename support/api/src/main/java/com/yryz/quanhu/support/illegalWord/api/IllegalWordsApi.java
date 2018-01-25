package com.yryz.quanhu.support.illegalWord.api;

import java.util.Set;

import com.yryz.common.response.PageList;
import com.yryz.common.response.Response;
import com.yryz.quanhu.support.illegalWord.entity.BasicIllegalWordsInfo;

/**
 * 违规词处理
 * @author jk
 */
public interface IllegalWordsApi {
	/**
	 * 检测违规词并返回敏感词
	 * @param text
	 * @return {@link Set}
	 */
	public Response<Set<String>> matchIllegalWords(String text);
	
	/**
	 * 检查内容是否存在敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @param text
	 * @return {@link Boolean}
	 * @Description 只检查敏感词不返回
	 */
	public Response<Boolean> checkIllegalWords(String text);
	
	/**
	 * 替换存在的敏感词
	 * @author jk
	 * @date 2017年9月12日
	 * @param text
	 * @param replaceWord
	 * @return
	 * @Description 找到敏感词并替换成replaceWord,replaceWord为空就使用*代替
	 */
	public Response<String> replaceIllegalWords(String text,String replaceWord);
	
	/**
	 * 删除敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param id
	 * @return
	 * @Description 删除不需要的敏感词
	 */
	Response<Integer> delete(Long id);

	/**
	 * 保存敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 新增敏感词
	 */
	Response<Integer> save(BasicIllegalWordsInfo record);
	
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
	Response<PageList<BasicIllegalWordsInfo>> listByParam(Integer pageNo,Integer pageSize,String word);

	/**
	 * 更新敏感词
	 * 
	 * @author jk
	 * @date 2017年9月12日
	 * @param record
	 * @return
	 * @Description 更新敏感词
	 */
	Response<Integer> update(BasicIllegalWordsInfo record);
}
