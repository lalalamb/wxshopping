package com.javaclimb.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.common.ResultCode;
import com.javaclimb.entity.UserInfo;
import com.javaclimb.exception.CustomException;
import com.javaclimb.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户相关
 */
@Service
public class UserInfoService {
    private final UserInfoMapper userInfoMapper;
    @Autowired(required = false)
    public UserInfoService(UserInfoMapper userInfoMapper) {
        this.userInfoMapper = userInfoMapper;
    }



    /**
     * 登录
     * @param name  用户民
     * @param password  密码
     * @return  数据库中查询到的第一个用户信息
     */
    public UserInfo login(String name,String password){
        UserInfo userInfo = userExist(name);
        if(userInfo==null){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        if(!SecureUtil.md5(password).equals(userInfo.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        return userInfo;
    }

    /**
     * 跟具用户名 id重置密码
     * @param id  用户id
     * @param name 用户名
     * @return  数据库修改条数
     */
    public int resetPassword(Long id,String name){
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id",id);
        queryWrapper.eq("name",name);
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        if(ObjectUtil.isEmpty(userInfos.get(0))){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        userInfos.get(0).setPassword(SecureUtil.md5("2"));
        return userInfoMapper.updateById(userInfos.get(0));
    }

    /**
     * 分页查询
     * @param pageNum   第几页
     * @param pageSize  每页数据数
     * @param name  姓名
     * @return  查询到的所有用户信息
     */
    public PageInfo<UserInfo> findPage(Integer pageNum,Integer pageSize,String name){
        PageHelper.startPage(pageNum,pageSize);
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        if(!StrUtil.isBlank(name)){
            queryWrapper.eq( "name",name);
        }
        List<UserInfo> users = userInfoMapper.selectList(queryWrapper);
        return PageInfo.of(users);
    }

    /**
     * 添加用户
     * @param userInfo 用户信息
     * @return  数据库改变条数
     */
    public int add(UserInfo userInfo){
        if(!ObjectUtil.isEmpty(userExist(userInfo.getName()))){
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        return userInfoMapper.insert(userInfo);
    }

    /**
     * 修改密码
     * @param userInfo  用户信息
     * @return  修改记录条数
     */
    public int updatePassword(UserInfo userInfo){
        UserInfo user = userInfoMapper.selectById(userInfo.getId());
        if(user==null){
            throw new CustomException(ResultCode.USER_NOT_EXIST_ERROR);
        }
        if(!SecureUtil.md5(userInfo.getPassword()).equals(user.getPassword())){
            throw new CustomException(ResultCode.USER_ACCOUNT_ERROR);
        }
        userInfo.setPassword(SecureUtil.md5(userInfo.getNewPassword()));
        return userInfoMapper.updateById(userInfo);
    }
    /**
     * 通过用户id查找用户信息
     * @param id    用户id
     * @return  用户信息
     */
    public UserInfo getUserById(Long id){
        return userInfoMapper.selectById(id);
    }

    /**
     * 支付及退款
     * @param totalPrice    支付为正退款为负
     * @param id    用户id
     */
    public void setBalance(Long id ,Double totalPrice){
        UserInfo userById = getUserById(id);
        userById.setBalance(userById.getBalance()-totalPrice);
        update(userById);
    }
    /**
     * 用户通过用户名查询是否存在
     * @param name  用户名
     * @return  存在返回用户信息,不存在返回null
     */
    public UserInfo userExist (String name){
        QueryWrapper<UserInfo> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("name",name);
        List<UserInfo> userInfos = userInfoMapper.selectList(queryWrapper);
        if(CollectionUtil.isEmpty(userInfos)){
            return null;
        }
        return userInfos.get(0);
    }

    /**
     * 更新用户信息
     * @param userInfo  用户信息
     * @return  修改条数
     */
    public int update (UserInfo userInfo){
        return userInfoMapper.updateById(userInfo);
    }

    /**
     * 删除用户
     * @param id    用户id
     * @return  删除用户个数
     */
    public int delete(Long id){
        return userInfoMapper.deleteById(id);   }

    /**
     * 获取用户总数
     * @return  用户总数
     */
    public int getTotalUser(){
        return userInfoMapper.selectCount(null);
    }

}
