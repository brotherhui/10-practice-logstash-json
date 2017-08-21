package com.example.exception;

public class ServiceException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ServiceException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public ServiceException(String code, String message){
		super(code, message);
	}
}
