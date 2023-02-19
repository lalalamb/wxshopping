package com.javaclimb.controller;

import cn.hutool.crypto.SecureUtil;
import com.github.pagehelper.PageInfo;
import com.javaclimb.common.Result;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.UserInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.service.UserInfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户增删查改
 */
@RestController
@RequestMapping("/user")
public class UserInfoController {
    @Resource
    private UserInfoService userInfoService;

    /**
     * 根据用户名分页查询
     * @param name 用户名
     * @param pageNum 第几页
     * @param pageSize 每页条数
     * @return 返回查询信息及状态码
     */
    @GetMapping("/{pageNum}/{name}")
    public Result<PageInfo<UserInfo>> page(@PathVariable String name,
                                           @PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<UserInfo> page = userInfoService.findPage(pageNum, pageSize, name);
        if(page.getSize()==0){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        return Result.success(page);
    }

    /**
     * 分页查询所有用户信息
     * @param pageNum   第几页数据
     * @param pageSize  每页数据条数
     * @return   查询状态机用户信息列表
     */
    @GetMapping("/{pageNum}")
    public Result<PageInfo<UserInfo>> page(@PathVariable Integer pageNum,
                                           @RequestParam(defaultValue = "10") Integer pageSize){
        PageInfo<UserInfo> page = userInfoService.findPage(pageNum, pageSize, null);
        if(page.getSize()==0){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        return Result.success(page);
    }

    /**
     * 根据用户id查询用户信息
     * @param id     用户id
     * @return   id的某个用户信息
     */
    @GetMapping("/someone/{id}")
    public Result<UserInfo> findById(@PathVariable Long id){
        UserInfo userById = userInfoService.getUserById(id);
        if(userById==null){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        return Result.success(userById);
    }

    /**
     * 获取用户总数
     * @return  用户总数
     */
    @GetMapping("/count")
    public Result<Integer> findTotalUser(){
        int totalUser = userInfoService.getTotalUser();
        return Result.success(totalUser);
    }
    /**
     * 添加用户
     * @param userInfo 前端发送用户信息
     * @return  添加结果状态
     */
    @PostMapping
    public Result save(@RequestBody UserInfo userInfo){
        userInfo.setPassword(SecureUtil.md5(userInfo.getPassword()));
        int add = userInfoService.add(userInfo);
        if(add!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }

    /**
     * 修改用户
     * @param userInfo 用户信息
     * @return 修改结果状态
     */
    @PutMapping
    public Result<UserInfo> remove(@RequestBody UserInfo userInfo){
        if(userInfoService.update(userInfo)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success(userInfo);
    }

    /**
     * 修改密码
     * @param userInfo  用户信息
     * @return  修改结果
     */
    @PutMapping("/update")
    public Result changePassword(@RequestBody UserInfo userInfo){
        if(userInfo.getPassword().equals(userInfo.getNewPassword())){
            throw new CustomException("1001","新密码与旧密码相同");
        }
        if(userInfoService.updatePassword(userInfo)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
    /**
     * 删除用户
     * @param id 用户id
     * @return  删除状态
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id){
        if(userInfoService.delete(id)!=1){
            throw new CustomException(ResultCode.ERROR);
        }
        return Result.success();
    }
}
