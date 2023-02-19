package com.javaclimb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.javaclimb.entity.OrderInfo;
import com.javaclimb.vo.EchartsData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderInfoMapper extends BaseMapper<OrderInfo> {
    <T> Double selectTotalPrice(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
