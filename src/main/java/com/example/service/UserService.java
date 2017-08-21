package com.example.service;

import java.net.SocketTimeoutException;

import org.springframework.stereotype.Service;

import com.example.bean.MessageEnum;
import com.example.bean.User;
import com.example.exception.BusinessException;
import com.example.exception.ServiceException;

@Service
public class UserService {

    public User findUser(Long userId){
    	try{
    		if(userId == 2){
    			throw new SocketTimeoutException();
    		}
    		else {
    			BusinessException ex = new BusinessException(MessageEnum.E101003.getCode(),MessageEnum.E101003.getMessage());
        		ex.setErrorContext("com.example.service"+".UserService"+".findUser");
        		ex.setErrorDescription("Check User ID failed");
        		ex.setErrorCorrection("Please check user ID whether it is 2,(2 will throw sockettimeout)");
        		ex.addParameters("url", "http://www.baidu.com");
        		ex.addParameters("userid", userId);
    			throw ex;
    		}
    	}catch(SocketTimeoutException e){
    		//retry first
    		ServiceException ex =new ServiceException(MessageEnum.E101002.getCode(),String.format(MessageEnum.E101002.getMessage(), userId), e);
    		ex.setErrorContext("com.example.service"+".UserService"+".findUser");
    		ex.setErrorDescription("Connect to 3rd part service to get user timeout");
    		ex.setErrorCorrection("Please check 3rd part service");
    		ex.addParameters("url", "http://www.baidu.com");
    		ex.addParameters("userid", userId);
    		throw ex;
    	}
    }
}
