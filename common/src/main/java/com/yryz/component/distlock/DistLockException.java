package com.yryz.component.distlock;

public class DistLockException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4251510652484107480L;
	
	public DistLockException(String message){
		super(message);
	}
	
	public DistLockException(String message , Throwable cause){
		super(message , cause);
	}

}
