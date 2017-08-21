package com.example.exception.handler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bean.ResponseObject;
import com.example.exception.BaseException;
import com.example.exception.BusinessException;
import com.example.exception.ParameterException;
import com.example.exception.DataDuplicateException;
import com.example.exception.DataNotFoundException;
import com.example.exception.ServiceException;
import com.example.exception.ServiceTimeoutException;
import static net.logstash.logback.marker.Markers.*;

@RestControllerAdvice
public class CustomExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static StackTraceElement[] DUMMY_ELEMENT = new StackTraceElement[0];
	
	@ExceptionHandler(value = BusinessException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseObject doexc(HttpServletRequest request, BusinessException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2User(request, e);
		return body;
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(value = DataNotFoundException.class)
	public ResponseObject handleNotFound(HttpServletRequest request, DataNotFoundException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2User(request, e);
		return body;
	}
	
	@ExceptionHandler(value = DataDuplicateException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseObject handleNotFound(HttpServletRequest request, DataDuplicateException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2User(request, e);
		return body;
	}
	
	@ExceptionHandler(value = ParameterException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseObject handleNotFound(HttpServletRequest request, ParameterException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2User(request, e);
		return body;
	}
	
	@ExceptionHandler(value = ServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject handleNotFound(HttpServletRequest request, ServiceException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2Admin(request, e);
		return body;
	}
	
	@ExceptionHandler(value = ServiceTimeoutException.class)
	@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
	public ResponseObject handleNotFound(HttpServletRequest request, ServiceTimeoutException e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(),e.getCode(),e.getMessage());
		log2Admin(request, e);
		return body;
	}
	
	@ExceptionHandler(value = Throwable.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseObject doexc(HttpServletRequest request, Throwable e) throws Exception {
		
		ResponseObject body = new ResponseObject(request.getRequestURI(), "",e.getMessage());
		log2Admin(request, e);
		return body;
	}
	
	private void log2Admin(HttpServletRequest request, BaseException e){
		//to log the different message ()
		e.setUrl(request.getRequestURI());
//		logger.error(e.getDevLogString());
		e.setStackTrace(DUMMY_ELEMENT);//remove the duplicate stacktrace, remain the ones in cause
		logger.error(append("errorDetails", e), "log exception");
	}

	
	private void log2Admin(HttpServletRequest request, Throwable e){
		//to log the different message ()
		e.setStackTrace(DUMMY_ELEMENT);//remove the duplicate stacktrace, remain the ones in cause
		logger.error(append("errorDetails", e), "log exception");
	}
	
	private void log2User(HttpServletRequest request, BaseException e){
		//to log the different message ()
		//Don't need to log the stack trace and cause
		e.setStackTrace(DUMMY_ELEMENT);
		e.getCause().setStackTrace(DUMMY_ELEMENT);
		e.setUrl(request.getRequestURI());
		logger.warn(append("warningDetails", e), "log warning");
	}
}
