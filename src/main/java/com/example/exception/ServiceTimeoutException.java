package com.example.exception;

public class ServiceTimeoutException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceTimeoutException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public ServiceTimeoutException(String code, String message){
		super(code, message);
	}
}
