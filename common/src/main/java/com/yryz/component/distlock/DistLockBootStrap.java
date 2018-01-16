package com.yryz.component.distlock;

import com.yryz.component.distlock.spi.provider.DistLockProviderDemo;

@EnableDistLock(provider = DistLockProviderDemo.class)
public class DistLockBootStrap {

}
