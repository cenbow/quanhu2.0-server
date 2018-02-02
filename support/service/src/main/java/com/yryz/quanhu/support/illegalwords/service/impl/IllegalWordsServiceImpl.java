/**
 * Copyright (c) 2017-2018 Wuhan Yryz Network Company LTD.
 * All rights reserved.
 * 
 * Created on 2017年9月12日
 * Id: IllegalWordsServiceImpl.java, 2017年9月12日 上午11:03:46 Administrator
 */
package com.yryz.quanhu.support.illegalwords.service.impl;

import java.util.Date;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.Sets;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.support.illegalwords.common.SensitiveUtil;
import com.yryz.quanhu.support.illegalwords.dao.BasicIllegalWordsDao;
import com.yryz.quanhu.support.illegalwords.entity.BasicIllegalWords;
import com.yryz.quanhu.support.illegalwords.service.IllegalWordsService;

/**
 * @author jk
 * @version 1.0
 * @date 2017年9月12日 上午11:03:46
 * @Description 敏感词管理
 */
@Component
public class IllegalWordsServiceImpl implements IllegalWordsService {
	private static final Logger logger = LoggerFactory.getLogger(IllegalWordsServiceImpl.class);
	
	@Autowired
	private BasicIllegalWordsDao persistenceDao;
	
	@Override
	public Set<String> getIllegalWords(String text) {
		return Sets.newHashSet(SensitiveUtil.getFindedAllSensitive(text));
	}

	@Override
	public boolean checkIllegalWords(String text) {
		return SensitiveUtil.containsSensitive(text);
	}
	
	@Override
	public String relpaceIllegalWord(String text, String replaceWord) {
		return SensitiveUtil.replaceFindedSensitive(text, replaceWord);
	}
	
	@Override
	@Transactional
	public int delete(Long id) {
		try {
			int result = persistenceDao.delete(id);
			return result;
		} catch (Exception e) {
			logger.error("BasicIllegalWordsDao.delete",e);
			throw new MysqlOptException(e);
		}	
	}

	@Override
	@Transactional
	public int save(BasicIllegalWords record) {
		record.setCreateDate(new Date());
		record.setLastUpdateDate(record.getCreateDate());
		try {
			int result = persistenceDao.save(record);
			return result;
		} catch (Exception e) {
			logger.error("BasicIllegalWordsDao.save",e);
			throw new MysqlOptException(e);
		}
		
	}

	@Override
	public Page<BasicIllegalWords> listByParam(Integer pageNo, Integer pageSize, String word) {
		Page<BasicIllegalWords> page = PageHelper.startPage(pageNo, pageSize);
		try {
			persistenceDao.listByParam(word);
		} catch (Exception e) {
			logger.error("BasicIllegalWordsDao.listByParam",e);
			throw new MysqlOptException(e);
		}
		return page;
	}

	@Override
	@Transactional
	public int update(BasicIllegalWords record) {
		record.setLastUpdateDate(new Date());
		try {
			int result = persistenceDao.update(record);
			return result;
		} catch (Exception e) {
			logger.error("BasicIllegalWordsDao.update",e);
			throw new MysqlOptException(e);
		}
	}
	
}
