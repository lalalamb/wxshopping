package com.javaclimb.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.javaclimb.entity.AdvertiserInfo;
import com.javaclimb.mapper.AdvertiserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *公告相关
 */
@Service
public class AdvertiserInfoService {
    private final AdvertiserInfoMapper advertiserInfoMapper;
    @Autowired(required = false)
    public AdvertiserInfoService(AdvertiserInfoMapper advertiserInfoMapper) {
        this.advertiserInfoMapper = advertiserInfoMapper;
    }
    /**
     * 分页模糊查询
     * @param pageNum   第几页
     * @param pageSize  每页数据数
     * @param name  公告名称
     * @return  查询到的所有用户信息
     */
    public PageInfo<AdvertiserInfo> findPage(Integer pageNum,Integer pageSize,String name){
        PageHelper.startPage(pageNum,pageSize);
        LambdaQueryWrapper<AdvertiserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AdvertiserInfo::getId,AdvertiserInfo::getCreateTime,AdvertiserInfo::getName);
        if(!StrUtil.isBlank(name)){
            queryWrapper.like( AdvertiserInfo::getName,name);
        }
        List<AdvertiserInfo> advertisers = advertiserInfoMapper.selectList(queryWrapper);
        return PageInfo.of(advertisers);
    }

    /**
     * 获取公告内容
     * @param id    公告id
     * @return  公告内容
     */
    public String getContent(Long id){
        LambdaQueryWrapper<AdvertiserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(AdvertiserInfo::getContent);
        queryWrapper.eq(AdvertiserInfo::getId,id);
        return advertiserInfoMapper.selectList(queryWrapper).get(0).getContent();
    }
    /**
     * 添加公告
     * @param advertiserInfo 公告信息
     * @return  数据库改变条数
     */
    public int add(AdvertiserInfo advertiserInfo){
        advertiserInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return advertiserInfoMapper.insert(advertiserInfo);
    }

    /**
     * 删除公告
     * @param id    公告id
     * @return  数据库修改条数
     */
    public int delete(Long id){
        return advertiserInfoMapper.deleteById(id);
    }

    /**
     * 更新公告
     * @param advertiserInfo    公告信息
     * @return  修改条数
     */
    public int update (AdvertiserInfo advertiserInfo){
        advertiserInfo.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return advertiserInfoMapper.updateById(advertiserInfo);
    }
}
