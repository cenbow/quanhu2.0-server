package com.yryz.quanhu.dymaic.canal.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionBegin;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.google.protobuf.InvalidProtocolBufferException;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;

import org.apache.commons.lang.SystemUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.yryz.common.entity.CanalChangeInfo;
import com.yryz.common.entity.CanalMsgContent;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.rabbitmq.MessageSender;

public abstract class AbstractCanalService {
	protected final static Logger logger = LoggerFactory.getLogger(AbstractCanalService.class);
	protected volatile boolean running = false;
	protected Thread thread = null;
	protected CanalConnector connector;
	protected String destination;
	protected static final String SEP = SystemUtils.LINE_SEPARATOR;
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static String context_format = null;
    protected static String row_format = null;
	protected static String transaction_format = null;
	
    @Autowired
    private MessageSender messageSender;
	
	static {
		context_format = SEP + "****************************************************" + SEP;
		context_format += "* Batch Id: [{}] ,count : [{}] , memsize : [{}] , Time : {}" + SEP;
		context_format += "* Start : [{}] " + SEP;
		context_format += "* End : [{}] " + SEP;
		context_format += "****************************************************" + SEP;
		row_format = SEP + "----------------> binlog[{}:{}] , name[{},{}] , eventType : {} , executeTime : {} , delay : {}ms" + SEP;
		transaction_format = SEP + "================> binlog[{}:{}] , executeTime : {} , delay : {}ms" + SEP;
	}
	
	protected Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread t, Throwable e) {
			logger.error("canal parse events has an error", e);
		}
	};

	protected void start() {
		Assert.notNull(connector, "connector is null");
		thread = new Thread(new Runnable() {
			public void run() {
				process();
			}
		});

		thread.setUncaughtExceptionHandler(handler);
		thread.start();
		running = true;
	}

	protected void stop() {
		if (!running) {
			return;
		}
		running = false;
		if (thread != null) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

	private void process() {
		int batchSize = CommonConstant.BATCH_SIZE;
		while (running) {
			try {
				connector.connect();
				connector.subscribe();//"quanhu\\..*"  客户端不设置以服务端为准，客户端设置了已客户端为准
				connector.rollback();
				while (running) {
					Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
					long batchId = message.getId();
					int size = message.getEntries().size();
					if (batchId == -1 || size == 0) {
						logger.debug("empty canal message");
					} else {
						//printSummary(message, batchId, size);
						processEntry(message.getEntries());
					}

					connector.ack(batchId); // 提交确认
					// connector.rollback(batchId); // 处理失败, 回滚数据
				}
			} catch (Exception e) {
				logger.error("process error!", e);
			} finally {
				connector.disconnect();
			}
		}
	}

	private void processEntry(List<Entry> entrys) {
		List<CanalMsgContent> msgList=convert(entrys);
		if(!CollectionUtils.isEmpty(msgList)){
			for (int i = 0; i < msgList.size(); i++) {
				CanalMsgContent msg=msgList.get(i);
				messageSender.sendMessage(msg);
			}
		}
	}
	
	private List<CanalMsgContent> convert(List<Entry> entries) {
		List<CanalMsgContent> msgList = new ArrayList<CanalMsgContent>();
        
		for (Entry entry : entries) {
			long executeTime = entry.getHeader().getExecuteTime();
			long delayTime = new Date().getTime() - executeTime;
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
				//printTransactionInfo(entry);
				continue;
			}

			if (entry.getEntryType() == EntryType.ROWDATA) {
				RowChange rowChage = null;
				try {
					rowChage = RowChange.parseFrom(entry.getStoreValue());
				} catch (Exception e) {
					throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
				}

				EventType eventType = rowChage.getEventType();
				if (eventType == EventType.QUERY || rowChage.getIsDdl()) {
//					logger.info(" sql ----> " + rowChage.getSql() + SEP);
					continue;
				}
				
				logger.info(row_format,
						new Object[] { entry.getHeader().getLogfileName(),
								String.valueOf(entry.getHeader().getLogfileOffset()), entry.getHeader().getSchemaName(),
								entry.getHeader().getTableName(), eventType,
								String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
				for (RowData rowData : rowChage.getRowDatasList()) {
					CanalMsgContent canalMsgContent = new CanalMsgContent();
		            canalMsgContent.setDbName(entry.getHeader().getSchemaName().toLowerCase());
		            canalMsgContent.setTableName(entry.getHeader().getTableName().toLowerCase());
		            canalMsgContent.setEventType(eventType.toString().toLowerCase());
					canalMsgContent.setDataBefore(convertToCanalChangeInfoList(rowData.getBeforeColumnsList()));
	                canalMsgContent.setDataAfter(convertToCanalChangeInfoList(rowData.getAfterColumnsList()));
	                msgList.add(canalMsgContent);
				}
			}
		}
		
		return msgList;
	}

	private List<CanalChangeInfo> convertToCanalChangeInfoList(List<CanalEntry.Column> columnList) {
        List<CanalChangeInfo> canalChangeInfoList = new ArrayList<CanalChangeInfo>();
        for (CanalEntry.Column column : columnList) {
        		CanalChangeInfo canalChangeInfo = new CanalChangeInfo();
                canalChangeInfo.setName(column.getName());
                canalChangeInfo.setValue(column.getValue());
                canalChangeInfo.setUpdate(column.getUpdated());
                canalChangeInfoList.add(canalChangeInfo);
        }

        return canalChangeInfoList;
    }
	
	protected void printTransactionInfo(Entry entry){
		long executeTime = entry.getHeader().getExecuteTime();
		long delayTime = new Date().getTime() - executeTime;
		
		if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN) {
			TransactionBegin begin = null;
			try {
				begin = TransactionBegin.parseFrom(entry.getStoreValue());
			} catch (InvalidProtocolBufferException e) {
				throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
			}
			// 打印事务头信息，执行的线程id，事务耗时
			logger.info(transaction_format,
					new Object[] { entry.getHeader().getLogfileName(),
							String.valueOf(entry.getHeader().getLogfileOffset()),
							String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
			logger.info(" BEGIN ----> Thread id: {}", begin.getThreadId());
		} else if (entry.getEntryType() == EntryType.TRANSACTIONEND) {
			TransactionEnd end = null;
			try {
				end = TransactionEnd.parseFrom(entry.getStoreValue());
			} catch (InvalidProtocolBufferException e) {
				throw new RuntimeException("parse event has an error , data:" + entry.toString(), e);
			}
			// 打印事务提交信息，事务id
			logger.info("----------------\n");
			logger.info(" END ----> transaction id: {}", end.getTransactionId());
			logger.info(transaction_format,
					new Object[] { entry.getHeader().getLogfileName(),
							String.valueOf(entry.getHeader().getLogfileOffset()),
							String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
		}
	}
	
	protected void printSummary(Message message, long batchId, int size) {
		long memsize = 0;
		for (Entry entry : message.getEntries()) {
			memsize += entry.getHeader().getEventLength();
		}

		String startPosition = null;
		String endPosition = null;
		if (!CollectionUtils.isEmpty(message.getEntries())) {
			startPosition = buildPositionForDump(message.getEntries().get(0));
			endPosition = buildPositionForDump(message.getEntries().get(message.getEntries().size() - 1));
		}

		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		logger.info(context_format,
				new Object[] { batchId, size, memsize, format.format(new Date()), startPosition, endPosition });
	}
	
	private String buildPositionForDump(Entry entry) {
		long time = entry.getHeader().getExecuteTime();
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
				+ entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
	}

	public void setConnector(CanalConnector connector) {
		this.connector = connector;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
	
}
