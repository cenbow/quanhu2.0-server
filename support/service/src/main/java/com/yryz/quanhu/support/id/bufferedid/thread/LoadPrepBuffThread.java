package com.yryz.quanhu.support.id.bufferedid.thread;


import com.yryz.quanhu.support.id.bufferedid.entity.KeyInfo;
import com.yryz.quanhu.support.id.bufferedid.service.PrimaryKeyService;

public class LoadPrepBuffThread extends Thread {

	private PrimaryKeyService primaryKeyService;
	private final KeyInfo keyInfo;

	public LoadPrepBuffThread(PrimaryKeyService primaryKeyService,
			final KeyInfo keyInfo) {
		this.primaryKeyService = primaryKeyService;
		this.keyInfo = keyInfo;
	}

	@Override
	public void run() {
		primaryKeyService.loadPrepBuff(keyInfo);
	}
	
}
