package com.yryz.quanhu.support.illegalwords.service.impl;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yryz.common.entity.CanalMsgContent;
import com.yryz.common.exception.MysqlOptException;
import com.yryz.quanhu.support.illegalwords.common.AmqpConstant;
import com.yryz.quanhu.support.illegalwords.common.SensitiveUtil;
import com.yryz.quanhu.support.illegalwords.dao.BasicIllegalWordsDao;

@Component
public class IllegalWordsInitialize {
	private static final Logger logger = LoggerFactory.getLogger(IllegalWordsInitialize.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private boolean change=false;//判断是否应该更新的开关
	private long lastInitTime=0;
	private long thresholdTime=1000*60;//初始化时间间隔不低于1分钟
	
	@Autowired
	private BasicIllegalWordsDao persistenceDao;
	
	@PostConstruct
	private void initialize() {
		SensitiveUtil.init(listAllWords());
		setChange(false);
		lastInitTime=new Date().getTime();
	}
	
	public void initWords() {
		long t=new Date().getTime()-lastInitTime;
		if(change && t>thresholdTime){
			SensitiveUtil.init(listAllWords());
			setChange(false);
			lastInitTime=new Date().getTime();
		}
	}
	
	/**
	 * canal服务监听qh_illegal_words表变化时发通知
	 * QueueBinding: exchange和queue的绑定
	 * Queue:队列声明
	 * Exchange:声明exchange
	 * @param data
	 */
	@RabbitListener(bindings = @QueueBinding(
			value= @Queue(value=AmqpConstant.CANAL_ILLEGAL_WORDS_QUEUE,durable="true"),
			exchange=@Exchange(value=AmqpConstant.CANAL_FANOUT_EXCHANGE,ignoreDeclarationExceptions="true",type=ExchangeTypes.FANOUT))
	)
	public void handleMessage(String data){
		try {
			CanalMsgContent canalMsg=MAPPER.readValue(data, CanalMsgContent.class);
			if("quanhu".equals(canalMsg.getDbName()) && "qh_illegal_words".equals(canalMsg.getTableName())){
				setChange(true);
			}
		} catch (IOException e) {
			e.printStackTrace();
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

	private synchronized void setChange(boolean change) {
		this.change = change;
		if(!change){//初始化完成后设置为false
			this.lastInitTime=new Date().getTime();
		}	
	}

}
