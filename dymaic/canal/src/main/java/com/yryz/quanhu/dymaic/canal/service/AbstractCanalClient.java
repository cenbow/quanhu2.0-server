package com.yryz.quanhu.dymaic.canal.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.protocol.CanalEntry.Column;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.Message;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionBegin;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;

import org.apache.commons.lang.SystemUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.stereotype.Component;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsg;

@Component
public class AbstractCanalClient {
	protected final static Logger logger = LoggerFactory.getLogger(AbstractCanalClient.class);
	protected volatile boolean running = false;
	protected Thread thread = null;
	protected CanalConnector connector;
	protected String destination;
	protected static final String SEP = SystemUtils.LINE_SEPARATOR;
	protected static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    protected static String context_format = null;
	
	static {
		context_format = SEP + "****************************************************" + SEP;
		context_format += "* Batch Id: [{}] ,count : [{}] , memsize : [{}] , Time : {}" + SEP;
		context_format += "* Start : [{}] " + SEP;
		context_format += "* End : [{}] " + SEP;
		context_format += "****************************************************" + SEP;
	}
	
	protected Thread.UncaughtExceptionHandler handler = new Thread.UncaughtExceptionHandler() {
		public void uncaughtException(Thread t, Throwable e) {
			logger.error("canal parse events has an error", e);
		}
	};

	public AbstractCanalClient(String destination) {
		this(destination, null);
	}

	public AbstractCanalClient(String destination, CanalConnector connector) {
		this.destination = destination;
		this.connector = connector;
	}

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
		MDC.remove("destination");
	}

	protected void process() {
		int batchSize = CommonConstant.BATCH_SIZE;
		while (running) {
			try {
				MDC.put("destination", destination);
				connector.connect();
				connector.subscribe(".*\\..*");
				connector.rollback();
				while (running) {
					Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
					long batchId = message.getId();
					int size = message.getEntries().size();
					if (batchId == -1 || size == 0) {
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e) {
						}
					} else {
						printSummary(message, batchId, size);
						processEntry(message.getEntries());
					}

					connector.ack(batchId); // 提交确认
					// connector.rollback(batchId); // 处理失败, 回滚数据
				}
			} catch (Exception e) {
				logger.error("process error!", e);
			} finally {
				connector.disconnect();
				MDC.remove("destination");
			}
		}
	}

	protected void processEntry(List<Entry> entrys) {
		
	}
	
	private void printSummary(Message message, long batchId, int size) {
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
	
	protected String buildPositionForDump(Entry entry) {
		long time = entry.getHeader().getExecuteTime();
		Date date = new Date(time);
		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		return entry.getHeader().getLogfileName() + ":" + entry.getHeader().getLogfileOffset() + ":"
				+ entry.getHeader().getExecuteTime() + "(" + format.format(date) + ")";
	}

	public void setConnector(CanalConnector connector) {
		this.connector = connector;
	}
}
