package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.bean.MessageEnum;
import com.example.bean.ResponseObject;
import com.example.bean.User;
import com.example.service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

//    @GetMapping("/{userId}")
//    public User findUser(@PathVariable Long userId){
//        User user = userService.findUser(userId);
//        return user;
//    }
    
  //if you want to return a specific format of return data, we can implements the ResponseEntity like this
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseObject findUser(@PathVariable Long userId){
        User user = userService.findUser(userId);
        return User.toResponseObject(user, MessageEnum.S101001.getCode(), MessageEnum.S101001.getMessage());
    }
}
