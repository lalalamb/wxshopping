package com.javaclimb.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.entity.TypeInfo;
import com.javaclimb.mapper.TypeInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *商品类别相关
 */
@Service
public class TypeInfoService {
    private final TypeInfoMapper typeInfoMapper;
    @Autowired(required = false)
    public TypeInfoService(TypeInfoMapper typeInfoMapper) {
        this.typeInfoMapper = typeInfoMapper;
    }
    /**
     * 分页模糊查询商品类别
     * @param pageNum   第几页
     * @param pageSize  每页数据数
     * @param name  商品类别名称
     * @return  查询到的所有用户信息
     */
    public PageInfo<TypeInfo> findPage(Integer pageNum,Integer pageSize,String name){
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<TypeInfo> queryWrapper = new LambdaQueryWrapper<>();
        if(!StrUtil.isBlank(name)){
            queryWrapper.like( TypeInfo::getName,name);
        }
        List<TypeInfo> advertisers = typeInfoMapper.selectList(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 获取所有商品类别
     */
    public List<TypeInfo> findType(){
         return typeInfoMapper.selectList(null);
    }

    /**
     * 添加商品类别
     * @param typeInfo 商品类别信息
     * @return  数据库改变条数
     */
    public int add(TypeInfo typeInfo){
        return typeInfoMapper.insert(typeInfo);
    }

    /**
     * 删除商品类别
     * @param id    商品类别id
     * @return  数据库修改条数
     */
    public int delete(Long id){
        return typeInfoMapper.deleteById(id);
    }

    /**
     * 更新商品类别
     * @param typeInfo    商品类别信息
     * @return  修改条数
     */
    public int update (TypeInfo typeInfo){
        return typeInfoMapper.updateById(typeInfo);
    }
}
