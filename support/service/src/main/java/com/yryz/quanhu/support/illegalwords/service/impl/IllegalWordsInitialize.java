package com.yryz.quanhu.support.illegalwords.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.support.illegalwords.common.SensitiveUtil;
import com.yryz.quanhu.support.illegalwords.dao.BasicIllegalWordsDao;

@Component
@EnableScheduling
public class IllegalWordsInitialize {
	private static final Logger logger = LoggerFactory.getLogger(IllegalWordsInitialize.class);
	private String lastUpdateTime="";
	
	@Autowired
	private BasicIllegalWordsDao persistenceDao;
	
	@PostConstruct
	private void initialize() {
		SensitiveUtil.init(listAllWords());
	}
	
	@Scheduled(fixedRate=60*60*1000)
	public void initWords() {
		String last=persistenceDao.selectMaxLastUpdateTime();
		if("".equals(lastUpdateTime) && last!=null){
			lastUpdateTime=last;
		}
		if(!lastUpdateTime.equals(last)){
			SensitiveUtil.init(listAllWords());
			lastUpdateTime=last;
		}
	}
	
	private List<String> listAllWords(){
		try {
			return persistenceDao.listAllWords();
		} catch (Exception e) {
			logger.error("BasicIllegalWordsDao.listAllWords",e);
			throw new MysqlOptException(e);
		}
	}

}
