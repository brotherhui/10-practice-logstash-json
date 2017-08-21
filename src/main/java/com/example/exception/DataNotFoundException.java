package com.example.exception;

public class DataNotFoundException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataNotFoundException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public DataNotFoundException(String code, String message){
		super(code, message);
	}
}
