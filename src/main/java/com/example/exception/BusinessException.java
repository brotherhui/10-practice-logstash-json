package com.example.exception;

public class BusinessException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BusinessException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public BusinessException(String code, String message){
		super(code, message);
	}
}
