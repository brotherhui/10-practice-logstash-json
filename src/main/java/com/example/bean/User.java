package com.example.bean;

import java.io.Serializable;


public class User implements Serializable {

    private Long userId;
    private String name;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static ResponseObject toResponseObject(User user, String code, String message){
    	ResponseObject obj = new ResponseObject();
    	obj.setCode(code);
    	obj.setMessage(message);
    	obj.setData(user.toString());
      	return obj;
    }
}
