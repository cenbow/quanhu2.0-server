package com.yryz.quanhu.support.id.bufferedid.thread;


import com.yryz.quanhu.support.id.bufferedid.entity.KeyInfo;
import com.yryz.quanhu.support.id.bufferedid.service.PrimaryKeyService;

public class ExpandThread extends Thread {

	private PrimaryKeyService primaryKeyService;
	private final KeyInfo keyInfo;

	public ExpandThread(PrimaryKeyService primaryKeyService,
			final KeyInfo keyInfo) {
		this.primaryKeyService = primaryKeyService;
		this.keyInfo = keyInfo;
	}

	@Override
	public void run() {
		primaryKeyService.syncExpandKey(keyInfo);
	}
	
}
