package com.javaclimb.controller;

import cn.hutool.core.util.StrUtil;
import com.javaclimb.common.Common;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.UserInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.CommonDataSource;

/**
 * 登录退出控制
 */
@RestController
@RequestMapping("/account")
public class AccountController {
    @Autowired
    private  UserInfoService userInfoService;

    /**
     * 登录
     * @param userInfo  用户信息
     * @param request   HttpServletRequest
     * @return  登录结果
     */
    @PostMapping
    public Result<UserInfo> login(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword());
        if(login.getLevel()!=3){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,login);
        session.setMaxInactiveInterval(60*2);
        return Result.success(login);
    }
    @PostMapping("/login")
    public Result<UserInfo> loginBack(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        UserInfo login = userInfoService.login(userInfo.getName(), userInfo.getPassword());
        if(login.getLevel()==3){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,login);
        session.setMaxInactiveInterval(60*2);
        return Result.success(login);
    }
    /**
     * 根据id,name重置密码
     * @param userInfo  用户信息
     * @return  修改结果
     */
    @PostMapping("/reset")
    public Result resetPW(@RequestBody UserInfo userInfo){
        return userInfoService.resetPassword(userInfo.getId(),userInfo.getName())==1?Result.success():Result.error();
    }

    /**
     * 安全退出登录
     * @param request   HttpServletRequest
     * @return  退出结果
     */
    @GetMapping
    public Result logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute(Common.USER_INFO,null);
        session.invalidate();
        return Result.success();
    }
    @PostMapping("/sign")
    public Result<UserInfo> signUp(@RequestBody UserInfo userInfo){
        if(StrUtil.isBlank(userInfo.getName())||StrUtil.isBlank(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        userInfoService.add(userInfo);
        return Result.success(userInfo);
    }
}
