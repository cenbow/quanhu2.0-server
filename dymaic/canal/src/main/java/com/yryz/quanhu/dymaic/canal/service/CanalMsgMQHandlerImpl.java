package com.yryz.quanhu.dymaic.canal.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.Entry;
import com.alibaba.otter.canal.protocol.CanalEntry.EntryType;
import com.alibaba.otter.canal.protocol.CanalEntry.EventType;
import com.alibaba.otter.canal.protocol.CanalEntry.RowChange;
import com.alibaba.otter.canal.protocol.CanalEntry.RowData;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionBegin;
import com.alibaba.otter.canal.protocol.CanalEntry.TransactionEnd;
import com.google.protobuf.InvalidProtocolBufferException;
import com.yryz.quanhu.dymaic.canal.entity.CanalChangeInfo;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsg;
import com.yryz.quanhu.dymaic.canal.entity.CanalMsgContent;
import com.yryz.quanhu.dymaic.canal.rabbitmq.MessageSender;

/**
 * 利用MQ处理canal消息处理
 *
 * @author jk
 */
@Service
public class CanalMsgMQHandlerImpl implements CanalMsgHandler {
    private static Logger logger = LoggerFactory.getLogger(CanalMsgMQHandlerImpl.class);
    protected static final String SEP = SystemUtils.LINE_SEPARATOR;
    protected static String row_format = null;
	protected static String transaction_format = null;
	
    @Autowired
    private MessageSender messageSender;

    static {
    	row_format = SEP + "----------------> binlog[{}:{}] , name[{},{}] , eventType : {} , executeTime : {} , delay : {}ms" + SEP;
		transaction_format = SEP + "================> binlog[{}:{}] , executeTime : {} , delay : {}ms" + SEP;
	}
    
    @Override
	public Boolean sendMq(CanalMsg canalMsg) {
		return messageSender.sendMessage(canalMsg.getKey(), canalMsg.getCanalMsgContent());
	}
    
	@Override
	public List<CanalMsg> convert(List<Entry> entries) {
		List<CanalMsg> msgList = new ArrayList<CanalMsg>();
        CanalMsgContent canalMsgContent = null;
        
		for (Entry entry : entries) {
			long executeTime = entry.getHeader().getExecuteTime();
			long delayTime = new Date().getTime() - executeTime;
			if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN || entry.getEntryType() == EntryType.TRANSACTIONEND) {
				printTransactionInfo(entry);
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
				logger.info(row_format,
						new Object[] { entry.getHeader().getLogfileName(),
								String.valueOf(entry.getHeader().getLogfileOffset()), entry.getHeader().getSchemaName(),
								entry.getHeader().getTableName(), eventType,
								String.valueOf(entry.getHeader().getExecuteTime()), String.valueOf(delayTime) });
				
				if (eventType == EventType.QUERY || rowChage.getIsDdl()) {
					logger.info(" sql ----> " + rowChage.getSql() + SEP);
					continue;
				}

				canalMsgContent = new CanalMsgContent();
	            canalMsgContent.setBinLogFile(entry.getHeader().getLogfileName());
	            canalMsgContent.setBinlogOffset(entry.getHeader().getLogfileOffset());
	            canalMsgContent.setDbName(entry.getHeader().getSchemaName());
	            canalMsgContent.setTableName(entry.getHeader().getTableName());
	            canalMsgContent.setEventType(eventType.toString().toLowerCase());
	            
				for (RowData rowData : rowChage.getRowDatasList()) {
					canalMsgContent.setDataBefore(convertToCanalChangeInfoList(rowData.getBeforeColumnsList()));
	                canalMsgContent.setDataAfter(convertToCanalChangeInfoList(rowData.getAfterColumnsList()));
	                CanalMsg canalMsg = new CanalMsg(canalMsgContent);
	                msgList.add(canalMsg);
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
	
	private void printTransactionInfo(Entry entry){
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
}
