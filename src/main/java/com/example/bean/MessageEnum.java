package com.example.bean;

public enum MessageEnum {
    S101001("101001","Successful message"),
    E101002("101002","Get userid=%s information timeout"),
	E101003("101003","User ID is invalid");

    private String code;
    private String message;

    MessageEnum(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }
    public String getMessage() {
        return message;
    }
}

