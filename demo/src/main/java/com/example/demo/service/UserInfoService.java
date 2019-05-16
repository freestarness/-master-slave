package com.example.demo.service;

import com.example.demo.annotation.TargetDataSource;
import com.example.demo.model.demo1.UserInfo1;
import com.example.demo.model.demo2.UserInfo2;

import javax.servlet.http.HttpServletRequest;

public interface UserInfoService {

    /**
     * demo
     * @return
     */
    @TargetDataSource("demo2DataSource")
    UserInfo1 listUseInfoFromDemo1(HttpServletRequest request);
    @TargetDataSource("demo1DataSource")
    UserInfo2 listUseInfoFromDemo2(HttpServletRequest request);
}
