package com.example.exception;

public class DataDuplicateException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataDuplicateException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public DataDuplicateException(String code, String message){
		super(code, message);
	}
}
