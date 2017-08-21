package com.example.exception;

import java.util.HashMap;
import java.util.Map;

public class BaseException extends RuntimeException{
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String    message = null;
    protected Throwable cause                = null;
    
    
    protected String    code = null;
    protected String    errorContext         = null;
    protected String    errorDescription     = null;
    protected String    errorCorrection      = null;
    protected String    url = null;

    protected Map<String, Object> additionalInfos = new HashMap<String, Object>();

    public BaseException(String code, String message) {
    	super(message);
        this.code = code;
        this.message = message;
    }
    
    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
        this.cause = cause;
    }
    
    public String getUrl() {
		return url;
	}
    
	public void setUrl(String url) {
		this.url = url;
	}
	
	public Throwable getCause() {
		return cause;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	
	public String getErrorContext() {
		return errorContext;
	}

	public void setErrorContext(String errorContext) {
		this.errorContext = errorContext;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public String getErrorCorrection() {
		return errorCorrection;
	}

	public void setErrorCorrection(String errorCorrection) {
		this.errorCorrection = errorCorrection;
	}

	public Map<String, Object> getParameters() {
		return additionalInfos;
	}

	public void setParameters(Map<String, Object> parameters) {
		this.additionalInfos = parameters;
	}	
	
	public void addParameters(String key, Object value) {
		this.additionalInfos.put(key, value);
	}	
	
	public String getDevLogString() {
		StringBuilder sb = new StringBuilder();

		if (cause != null) {
			sb.append(String.format("1. Cause:    %n"));
			sb.append(String.format("	class:    %s%n", cause.getClass()
					.getName()));
			sb.append(String.format("	trace:  %s%n", cause.getStackTrace()));
		}

		if (code != null) {
			sb.append(String.format("2. Code: %s%n", code));
		}
		
		if (message != null) {
			sb.append(String.format("3. Message: %s%n", message));
		}
		
		if (url != null) {
			sb.append(String.format("4. Url:  %s%n",
					url));
		}
		
		if (errorContext != null) {
			sb.append(String.format("5. errorContext:      %s%n",
					errorContext));
		}
		
		if (errorDescription != null) {
			sb.append(String.format("6. ErrorDescription:      %s%n",
					errorDescription));
		}
		if (errorCorrection != null) {
			sb.append(String.format("7. ErrorCorrection:       %s%n",
					errorCorrection));
		}
		if (!additionalInfos.isEmpty()) {
			sb.append(String.format("8. AddtionalInfos:%n"));
			for (String s : additionalInfos.keySet()) {
				String v = additionalInfos.get(s).toString();
				sb.append(String.format("    name=[%s] value=[%s]%n", s, v));
			}
		}

		return sb.toString();
	}
	
	
	
}
