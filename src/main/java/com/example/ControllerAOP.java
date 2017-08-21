package com.example;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.example.exception.BaseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Stopwatch;

import net.logstash.logback.marker.LogstashMarker;

import static net.logstash.logback.marker.Markers.*;

@Aspect
@Component
public class ControllerAOP {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final String STATUS_COMPLETED = "COMPLETED";
	private static final String STATUS_FAILED = "FAILED";
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static final String[] IP_HEADERS = { "x-forwarded-for", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR" };

	@Autowired
	private CounterService counterService;

	@Autowired
	private GaugeService gaugeService;

	@Autowired
	ObjectMapper objectMapper;

	@Pointcut("execution(public * com.example.controller..*.*(..))")
	public void webLog() {
	}

	@Around("webLog()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable {
		String methodName = pjp.getSignature().getName();
		counterService.increment(methodName);
		Object result = null;
		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		SimpleDateFormat df = new SimpleDateFormat(DATE_FORMAT);
		String timestamp = df.format(new Date());
		LogstashMarker logMarker = append("timestamp", timestamp);
		logMarker.and(append("appChannel", request.getHeader("appChannel")));
		logMarker.and(append("sessionId", request.getHeader("sessionId")));
		logMarker.and(append("messageId", request.getHeader("messageId")));
		logMarker.and(append("description", request.getHeader("description")));
		logMarker.and(append("URL", request.getRequestURL().toString()));
		logMarker.and(append("IP", this.getIpAddress(request)));
		logMarker.and(append("CLASS", pjp.getSignature().getDeclaringTypeName() + "." + pjp.getSignature().getName()));
		logMarker.and(append("METHOD", request.getMethod()));
		logMarker.and(append("ARGS", Arrays.toString(pjp.getArgs())));

		try {
			Stopwatch stopwatch = Stopwatch.createStarted();
			result = pjp.proceed(pjp.getArgs());
			stopwatch.stop();
			logMarker.and(append("status", STATUS_COMPLETED));
			long executeTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			logMarker.and(append("executeTime", executeTime + "ms"));
			gaugeService.submit("timer." + methodName, executeTime);
		} catch (BaseException ex) {
			BaseException exception = (BaseException) ex;
			String errorCode = exception.getCode();
			String reason = exception.getMessage();
			logMarker.and(append("status", STATUS_FAILED));
			logMarker.and(append("errorCode", errorCode));
			logMarker.and(append("reason", reason));
			throw ex;
		} catch (Exception ex) {
			logMarker.and(append("status", STATUS_FAILED));
			logMarker.and(append("errorCode", null));
			logMarker.and(append("reason", ex.getMessage()));
			throw ex;
		} finally {
			logger.info(logMarker, "log controller ");
		}
		return result;
	}

	private String getIpAddress(HttpServletRequest request) {
		String result = null;
		for (String header : IP_HEADERS) {
			result = request.getHeader(header);
			if (!StringUtils.isEmpty(result) && !"unknown".equalsIgnoreCase(result)) {
				break;
			}
		}

		if (StringUtils.isEmpty(result) || "unknown".equalsIgnoreCase(result)) {
			result = request.getRemoteAddr();
		}

		return result;
	}

}
