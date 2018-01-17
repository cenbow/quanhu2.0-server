package com.yryz.quanhu.dymaic.canal.entity;

import com.yryz.quanhu.dymaic.canal.constant.CommonConstant;

/**
 * canal消息
 *
 * @author jk
 */
public class CanalMsg {

    private String key;

    private CanalMsgContent canalMsgContent;

    public CanalMsg(CanalMsgContent canalMsgContent) {
        this.key = CommonConstant.CANAL_MSG_KEY_PREFIX + CommonConstant.KEY_SEPARATOR + canalMsgContent.getDbName() + CommonConstant.KEY_SEPARATOR + canalMsgContent.getTableName();
        this.canalMsgContent = canalMsgContent;
    }

    public CanalMsg() {
    }

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public CanalMsgContent getCanalMsgContent() {
		return canalMsgContent;
	}

	public void setCanalMsgContent(CanalMsgContent canalMsgContent) {
		this.canalMsgContent = canalMsgContent;
	}

}
