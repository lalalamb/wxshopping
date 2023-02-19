package com.javaclimb.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.javaclimb.entity.GoodsInfo;
import com.javaclimb.entity.OrderGoodsRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderGoodsRelMapper extends BaseMapper<OrderGoodsRel> {
    <T> List<GoodsInfo> getTotalPrice(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
    <T> List<GoodsInfo> getCount(@Param(Constants.WRAPPER) Wrapper<T> queryWrapper);
}
