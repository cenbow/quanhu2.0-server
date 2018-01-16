package com.yryz.component.distlock.spi.provider;

import java.io.Serializable;

import com.yryz.component.distlock.DistLockConfig;
import com.yryz.component.distlock.DistLockStatus;
import com.yryz.component.distlock.spi.DistLockProvider;

public class DistLockProviderDemo implements DistLockProvider {

	@Override
	public DistLockStatus doLock(DistLockConfig<? extends Serializable>.DistLockConfigEntry<?> entry) {
		// TODO Auto-generated method stub
		return DistLockStatus.Locked;
	}

	@Override
	public void doUnLock(DistLockConfig<? extends Serializable>.DistLockConfigEntry<?> entry) {
		// TODO Auto-generated method stub
		
	}

}
