package com.example.demo.controller;

import com.example.demo.model.demo1.UserInfo1;
import com.example.demo.model.demo2.UserInfo2;
import com.example.demo.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/test")
public class HelloWorld {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping(value = "/helloworld")
    public String test(HttpServletRequest request) {
        UserInfo1 userInfo1 = userInfoService.listUseInfoFromDemo1(request);
        UserInfo2 userInfo2 = userInfoService.listUseInfoFromDemo2(request);
        return "hello world";
    }

}
