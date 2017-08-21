package com.example.bean;

/**

 *
 * Created on 07.02.17.
 */
public class ResponseObject {
    private String url;
    private String code;
    private String message;
    private Object data;
    private int total;

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
	
	public ResponseObject() {
    }
	
	public ResponseObject(String code, String message) {
        this.code = code;
        this.message = message;
    }

	public ResponseObject(String url, String code, String message) {
        this.url = url;
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "url=" + url +
                "\n, code=" + code +
                "\n, message='" + message + '\'' +
                "\n, data='" + data + '\'' +
                "\n, total='" + total + '\'' +
                '}';
    }
}

