package com.javaclimb.xshopping;


import com.javaclimb.entity.UserInfo;
import com.javaclimb.service.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class XshoppingApplicationTests {
    @Autowired
    private UserInfoService userInfoService;
    @Test
    void addUser(){
        UserInfo userInfo = new UserInfo("一", "2", "测试1", "男", 14, "2002-01-12", "北京", "", "13623423423", "132131211132133112", 1, 9999.9);
        userInfoService.add(userInfo);
        userInfo = new UserInfo("二", "2", "测试2", "女", 12, "2000-01-12", "上海", "", "13623420023", "132131200032133112", 2, 9999.9);
        userInfoService.add(userInfo);
        userInfo = new UserInfo("三", "2", "测试3", "女", 53, "1988-01-12", "青岛", "", "13623420023", "132131200032133112", 3, 9999.9);
        userInfoService.add(userInfo);
    }
}
