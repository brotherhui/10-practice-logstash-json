package com.example.exception;

public class ParameterException extends BaseException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ParameterException(String code, String message, Throwable cause){
		super(code, message, cause);
	}
	
	public ParameterException(String code, String message){
		super(code, message);
	}
}
